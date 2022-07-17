package com.example.geektrust.services;

import com.example.geektrust.constants.*;
import com.example.geektrust.dtos.RenewalDetailsDTO;
import com.example.geektrust.dtos.RenewalReminderDTO;
import com.example.geektrust.entities.Plan;
import com.example.geektrust.entities.Subscription;
import com.example.geektrust.exceptions.InvalidRequestException;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SubscriptionService {

    private Subscription subscription;

    public SubscriptionService() {
        subscription = new Subscription(SubscriptionStatus.UNACTIVATED);
    }

    public Subscription getSubscription() {
        return subscription;
    }

    private void checkInvalidSubscriptionStatus() throws InvalidRequestException {
        if (this.subscription.getStatus().equals(SubscriptionStatus.UNACTIVATED)) {
            throw new InvalidRequestException("SUBSCRIPTIONS_NOT_FOUND");
        }
        if (this.subscription.getStatus().equals(SubscriptionStatus.INVALID_DATE)) {
            throw new InvalidRequestException("INVALID_DATE");
        }
    }

    public void startSubscription(String[] inputParams) throws InvalidRequestException {
        if (subscription.getStatus().equals(SubscriptionStatus.ACTIVATED)) {
            throw new InvalidRequestException("INVALID_REQUEST");
        }
        try {
            String dateString = inputParams[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(dateString, formatter);
            subscription.setStartDate(date);
            subscription.setStatus(SubscriptionStatus.ACTIVATED);
            subscription.setNoOfUsers(Constants.FIRST_USER);
        } catch (DateTimeParseException e) {
            this.subscription.setStatus(SubscriptionStatus.INVALID_DATE);
            throw new InvalidRequestException("INVALID_DATE");
        } catch (RuntimeException e) {
            throw new InvalidRequestException("PARSE_ERROR");
        }
    }

    public void addSubscription(String[] inputParams) throws InvalidRequestException {
        try {
            checkInvalidSubscriptionStatus();
            String subscriptionCategoryString = inputParams[1];
            String planNameString = inputParams[2];
            SubscriptionPlanChart subscriptionPlanChart = getSubscriptionPlanChart(subscriptionCategoryString, planNameString);
            checkIfSameCategoryPlanExists(subscriptionPlanChart);
            LocalDate renewalDate = getRenewalDate(subscriptionPlanChart);
            Plan plan = new Plan(subscriptionPlanChart, renewalDate);
            this.subscription.getPlans().add(plan);
            this.subscription.setAmount(this.subscription.getAmount() + subscriptionPlanChart.getPlanCost());
            if (Objects.equals(this.subscription.getNoOfUsers(), Constants.INITIAL_NO_OF_USERS)) {
                this.subscription.setNoOfUsers(Constants.FIRST_USER);
            }
        } catch (InvalidRequestException e) {
            throw new InvalidRequestException(CommandType.ADD_SUBSCRIPTION_FAILED.getType() + " " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InvalidRequestException("PARSE_ERROR");
        }
    }

    private SubscriptionPlanChart getSubscriptionPlanChart(String subscriptionCategoryString, String planNameString) throws InvalidRequestException {
        SubscriptionCategory subscriptionCategory = SubscriptionCategory.getSubscriptionCategory(subscriptionCategoryString);
        PlanName planName = PlanName.getPlanName(planNameString);
        if (ObjectUtils.isEmpty(subscriptionCategory) || ObjectUtils.isEmpty(planName)) {
            throw new InvalidRequestException("INVALID_SUBSCRIPTION_DETAILS");
        }
        return SubscriptionPlanChart.getSubscriptionPlanChart(subscriptionCategory, planName);
    }

    private void checkIfSameCategoryPlanExists(SubscriptionPlanChart subscriptionPlanChart) throws InvalidRequestException {
        List<Plan> plans = this.subscription.getPlans();
        for (Plan plan : plans) {
            if (plan.getPlanChart().getCategory().equals(subscriptionPlanChart.getCategory())) {
                throw new InvalidRequestException("DUPLICATE_CATEGORY");
            }
        }
    }

    private LocalDate getRenewalDate(SubscriptionPlanChart subscriptionPlanChart) {
        Integer planDuration = subscriptionPlanChart.getPlanDuration();
        LocalDate startDate = this.subscription.getStartDate();
        return startDate.plusMonths(planDuration).minusDays(Constants.MINUS_TEN_DAYS);
    }

    public void addTopup(String[] inputParams) throws InvalidRequestException {
        try {
            checkInvalidSubscriptionStatus();
            checkIfPlansExist();
            String topUpTypeString = inputParams[1];
            Integer noOfMonths = Integer.parseInt(inputParams[2]);
            TopUpType topUpType = TopUpType.getTopUpType(topUpTypeString);
            addTopUpToPlans(topUpType, noOfMonths);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("INVALID_NO_OF_MONTHS");
        } catch (InvalidRequestException e) {
            throw new InvalidRequestException(CommandType.ADD_TOPUP_FAILED.getType() + " " + e.getMessage());
        } catch (RuntimeException e) {
            throw new InvalidRequestException("PARSE_ERROR");
        }
    }

    private void checkIfPlansExist() throws InvalidRequestException {
        if (ObjectUtils.isEmpty(this.subscription.getPlans())) {
            throw new InvalidRequestException("SUBSCRIPTIONS_NOT_FOUND");
        }
    }

    private void addTopUpToPlans(TopUpType topUpType, Integer noOfMonths) throws InvalidRequestException {
        checkIfTopUpTypeValid(topUpType);
        List<Plan> plans = this.subscription.getPlans();
        Integer newUserCount = topUpType.getNoOfUsers() - this.subscription.getNoOfUsers();
        Integer topUpAmount = noOfMonths * topUpType.getCost();
//        for (Plan plan : plans) {
//            plan.setRenewalDate(plan.getRenewalDate().plusMonths(noOfMonths).minusDays(Constants.MINUS_TEN_DAYS));
//        }
        this.subscription.setAmount(this.subscription.getAmount() + topUpAmount);
        this.subscription.setNoOfUsers(this.subscription.getNoOfUsers() + newUserCount);
    }

    private void checkIfTopUpTypeValid(TopUpType topUpType) throws InvalidRequestException {
        Integer proposedNoOfUsers = topUpType.getNoOfUsers();
        Integer actualNoOfUsers = this.subscription.getNoOfUsers();
        if (actualNoOfUsers >= proposedNoOfUsers) {
            throw new InvalidRequestException("DUPLICATE_TOPUP");
        }
    }

    public RenewalDetailsDTO printRenewalDetails() throws InvalidRequestException {
        checkIfSubscriptionActivated();
        checkIfPlansExist();
        List<RenewalReminderDTO> renewalReminderDTOS = getAllRenewalInfo();
        Integer amount = this.subscription.getAmount();
        return new RenewalDetailsDTO(renewalReminderDTOS, amount);
    }

    private void checkIfSubscriptionActivated() throws InvalidRequestException {
        if (!this.subscription.getStatus().equals(SubscriptionStatus.ACTIVATED)) {
            throw new InvalidRequestException("SUBSCRIPTIONS_NOT_FOUND");
        }
    }

    private List<RenewalReminderDTO> getAllRenewalInfo() {
        List<Plan> plans = this.subscription.getPlans();
        List<RenewalReminderDTO> renewalReminderDTOS = new ArrayList<>();
        for (Plan plan : plans) {
            renewalReminderDTOS.add(new RenewalReminderDTO(CommandType.RENEWAL_REMINDER,
                    plan.getPlanChart().getCategory(), plan.getRenewalDate()));
        }
        return renewalReminderDTOS;
    }

}

package com.example.geektrust.services;

import com.example.geektrust.dtos.RenewalDetailsDTO;
import com.example.geektrust.entities.Subscription;
import com.example.geektrust.exceptions.InvalidRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class SubscriptionServiceTest {

    private SubscriptionService subscriptionService;

    SubscriptionServiceTest() {
        subscriptionService = new SubscriptionService();
    }

    @Test
    public void startSubscriptionPositiveTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        Subscription subscription = subscriptionService.getSubscription();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(inputParams[1], formatter);
        Assertions.assertEquals(date, subscription.getStartDate());
    }

    @Test
    public void startSubscriptionNegativeTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 09-19-2022";
        String[] inputParams = param.split(" ");
        Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.startSubscription(inputParams));
    }

    @Test
    public void startSubscriptionNegativeTest2() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "START_SUBSCRIPTION 19-11-2022";
        String[] secondParams = param.split(" ");
        Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.startSubscription(secondParams));
    }

    @Test
    public void addSubscriptionPositiveTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        Subscription subscription = subscriptionService.getSubscription();
        Assertions.assertEquals(1, subscription.getPlans().size());
    }

    @Test
    public void addSubscriptionPositiveTest2() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION VIDEO PREMIUM";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        Subscription subscription = subscriptionService.getSubscription();
        Assertions.assertEquals(2, subscription.getPlans().size());
    }

    @Test
    public void addSubscriptionPositiveTest3() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION VIDEO PREMIUM";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        Subscription subscription = subscriptionService.getSubscription();
        Assertions.assertEquals(600, subscription.getAmount());
    }

    @Test
    public void addSubscriptionNegativeTest1() {
        String param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        String[] inputParams = param.split(" ");
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.addSubscription(inputParams));
        Assertions.assertEquals("ADD_SUBSCRIPTION_FAILED SUBSCRIPTIONS_NOT_FOUND", exception.getMessage());
    }

    @Test
    public void addSubscriptionNegativeTest2() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC FAMILY";
        String[] secondParams = param.split(" ");
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.addSubscription(secondParams));
        Assertions.assertEquals("ADD_SUBSCRIPTION_FAILED INVALID_SUBSCRIPTION_DETAILS", exception.getMessage());
    }

    @Test
    public void addSubscriptionNegativeTest3() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PREMIUM";
        String[] secondParams = param.split(" ");
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.addSubscription(secondParams));
        Assertions.assertEquals("ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY", exception.getMessage());
    }

    @Test
    public void addTopupPositiveTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_TOPUP FOUR_DEVICE 2";
        inputParams = param.split(" ");
        subscriptionService.addTopup(inputParams);
        Subscription subscription = subscriptionService.getSubscription();
        Integer amount = subscription.getAmount();
        Assertions.assertEquals(200, amount);
    }

    @Test
    public void addTopupPositiveTest2() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION VIDEO PREMIUM";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION PODCAST FREE";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_TOPUP FOUR_DEVICE 3";
        inputParams = param.split(" ");
        subscriptionService.addTopup(inputParams);
        Subscription subscription = subscriptionService.getSubscription();
        Integer amount = subscription.getAmount();
        Assertions.assertEquals(750, amount);
        Assertions.assertEquals(4, subscription.getNoOfUsers());
        Assertions.assertEquals(3, subscription.getPlans().size());
    }

    @Test
    public void addTopupNegativeTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_TOPUP TEN_DEVICE 2";
        inputParams = param.split(" ");
        subscriptionService.addTopup(inputParams);
        param = "ADD_TOPUP FOUR_DEVICE 2";
        String[] secondParams = param.split(" ");
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.addTopup(secondParams));
        Assertions.assertEquals("ADD_TOPUP_FAILED DUPLICATE_TOPUP", exception.getMessage());
    }

    @Test
    public void addTopupNegativeTest2() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_TOPUP FOUR_DEVICE 2";
        String[] secondParams = param.split(" ");
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.addTopup(secondParams));
        Assertions.assertEquals("ADD_TOPUP_FAILED SUBSCRIPTIONS_NOT_FOUND", exception.getMessage());
    }

    @Test
    public void printRenewalDetailsPositiveTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION VIDEO PREMIUM";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_SUBSCRIPTION PODCAST FREE";
        inputParams = param.split(" ");
        subscriptionService.addSubscription(inputParams);
        param = "ADD_TOPUP FOUR_DEVICE 3";
        inputParams = param.split(" ");
        subscriptionService.addTopup(inputParams);
        param = "PRINT_RENEWAL_DETAILS";
        RenewalDetailsDTO renewalDetailsDTO = subscriptionService.printRenewalDetails();
        Assertions.assertEquals(750, renewalDetailsDTO.getRenewalAmount());
    }

    @Test
    public void printRenewalDetailsNegativeTest1() throws InvalidRequestException {
        String param = "START_SUBSCRIPTION 19-09-2022";
        String[] inputParams = param.split(" ");
        subscriptionService.startSubscription(inputParams);
        param = "PRINT_RENEWAL_DETAILS";
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.printRenewalDetails());
        Assertions.assertEquals("SUBSCRIPTIONS_NOT_FOUND", exception.getMessage());
    }

    @Test
    public void printRenewalDetailsNegativeTest2() throws InvalidRequestException {
        String param = "PRINT_RENEWAL_DETAILS";
        InvalidRequestException exception = Assertions.assertThrows(InvalidRequestException.class, () -> subscriptionService.printRenewalDetails());
        Assertions.assertEquals("SUBSCRIPTIONS_NOT_FOUND", exception.getMessage());
    }

}
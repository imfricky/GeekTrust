package com.example.geektrust.entities;

import com.example.geektrust.constants.Constants;
import com.example.geektrust.constants.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    private LocalDate startDate;

    private SubscriptionStatus status;

    private List<Plan> plans;

    private Integer amount;

    private Integer noOfUsers;

    public Subscription(SubscriptionStatus status) {
        this.status = status;
        this.plans = new ArrayList<>();
        this.amount = Constants.INITIAL_AMOUNT;
        this.noOfUsers = Constants.INITIAL_NO_OF_USERS;
    }

}

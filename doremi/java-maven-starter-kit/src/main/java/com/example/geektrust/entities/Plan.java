package com.example.geektrust.entities;

import com.example.geektrust.constants.SubscriptionPlanChart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Plan {

    private SubscriptionPlanChart planChart;

    private LocalDate renewalDate;

}

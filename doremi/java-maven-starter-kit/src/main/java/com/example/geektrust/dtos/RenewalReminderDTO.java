package com.example.geektrust.dtos;

import com.example.geektrust.constants.CommandType;
import com.example.geektrust.constants.SubscriptionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class RenewalReminderDTO {

    private CommandType commandType;

    private SubscriptionCategory subscriptionCategory;

    private LocalDate renewalDate;

    @Override
    public String toString() {
        String renewalDateString = this.renewalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return commandType.getType() + " " + subscriptionCategory.getType() + " " + renewalDateString;
    }
}

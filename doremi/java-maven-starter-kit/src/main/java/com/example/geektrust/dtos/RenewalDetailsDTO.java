package com.example.geektrust.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
public class RenewalDetailsDTO {

    private List<RenewalReminderDTO> renewalReminderDTOS;

    private Integer renewalAmount;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (RenewalReminderDTO renewalReminderDTO : this.renewalReminderDTOS) {
            String renewalDateString = renewalReminderDTO.getRenewalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            sb.append(renewalReminderDTO.getCommandType().getType()).append(" ").append(renewalReminderDTO.getSubscriptionCategory().getType()).append(" ").append(renewalDateString);
            sb.append("\n");
        }
        sb.append("RENEWAL_AMOUNT ").append(renewalAmount);
        return sb.toString();
    }
}

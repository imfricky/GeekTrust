package com.example.geektrust.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanBalanceDTO {

    private String bankName;

    private String borrowerName;

    private Integer amountPaid;

    private Integer noOfEmiLeft;

    @Override
    public String toString() {
        return bankName + " " + borrowerName + " " + amountPaid + " " + noOfEmiLeft;
    }
}

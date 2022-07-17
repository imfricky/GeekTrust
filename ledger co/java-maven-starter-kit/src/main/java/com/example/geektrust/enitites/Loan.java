package com.example.geektrust.enitites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    private String id;

    private String bankName;

    private String borrowerName;

    private Integer principalAmount;

    private Integer tenureInYears;

    private Integer Interest;

    private Integer emiAmount;

    private Integer totalPayableAmount;

}

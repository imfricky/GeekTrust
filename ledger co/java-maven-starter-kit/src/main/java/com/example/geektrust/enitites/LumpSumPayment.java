package com.example.geektrust.enitites;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LumpSumPayment {

    private String loanId;

    private Integer previousEmiNo;

    private Integer lumpSumAmount;

}

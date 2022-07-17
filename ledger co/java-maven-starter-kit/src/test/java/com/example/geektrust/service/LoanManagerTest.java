package com.example.geektrust.service;

import com.example.geektrust.dtos.LoanBalanceDTO;
import com.example.geektrust.enitites.LumpSumPayment;
import com.example.geektrust.exceptions.InvalidRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LoanManagerTest {

    private LoanManager loanManager;

    public LoanManagerTest() {
        loanManager = new LoanManager();
    }

    @Test
    public void createNewLoanPositiveTest1() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 5 4";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        Assertions.assertEquals(1, loanManager.loanBook.size());
    }

    @Test
    public void createNewLoanNegativeTest1() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 1000A 5 4";
        String[] inputParams = input.split(" ");
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.createNewLoan(inputParams));
    }

    @Test
    public void createNewLoanNegativeTest2() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 4";
        String[] inputParams = input.split(" ");
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.createNewLoan(inputParams));
    }

    @Test
    public void getLumpSumPaymentPositiveTest1() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 5 4";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "PAYMENT IDIDI Dale 1000 5";
        inputParams = input.split(" ");
        loanManager.addLumpSumPayment(inputParams);
        List<LumpSumPayment> lumpSumPayments = loanManager.lumpSumPayment.get("IDIDI_Dale");
        Assertions.assertEquals(1, lumpSumPayments.size());
    }

    @Test
    public void getLumpSumPaymentPositiveTest2() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 5 4";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "PAYMENT IDIDI Dale 1000 5";
        inputParams = input.split(" ");
        loanManager.addLumpSumPayment(inputParams);
        input = "PAYMENT IDIDI Dale 100 6";
        inputParams = input.split(" ");
        loanManager.addLumpSumPayment(inputParams);
        List<LumpSumPayment> lumpSumPayments = loanManager.lumpSumPayment.get("IDIDI_Dale");
        Assertions.assertEquals(2, lumpSumPayments.size());
    }

    @Test
    public void getLumpSumPaymentNegativeTest1() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 5 4";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "PAYMENT IDIDI Dale 1000A 5";
        inputParams = input.split(" ");
        String[] paymentParams = inputParams;
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.addLumpSumPayment(paymentParams));
    }

    @Test
    public void getLumpSumPaymentNegativeTest2() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 5 4";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "PAYMENT IDIDI Dale 10000";
        inputParams = input.split(" ");
        String[] paymentParams = inputParams;
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.addLumpSumPayment(paymentParams));
    }

    @Test
    public void getLumpSumPaymentNegativeTest3() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 10000 5 4";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "PAYMENT MBI Harry 5000 10";
        inputParams = input.split(" ");
        String[] paymentParams = inputParams;
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.addLumpSumPayment(paymentParams));
    }

    @Test
    public void getLoanBalancePositive1() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 5000 1 6";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "BALANCE IDIDI Dale 3";
        inputParams = input.split(" ");
        LoanBalanceDTO loanBalance = loanManager.getLoanBalance(inputParams);
        Assertions.assertEquals(1326, loanBalance.getAmountPaid());
    }

    @Test
    public void getLoanBalancePositive2() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 5000 1 6";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "PAYMENT IDIDI Dale 1000 5";
        inputParams = input.split(" ");
        loanManager.addLumpSumPayment(inputParams);
        input = "BALANCE IDIDI Dale 3";
        inputParams = input.split(" ");
        LoanBalanceDTO loanBalance = loanManager.getLoanBalance(inputParams);
        Assertions.assertEquals(1326, loanBalance.getAmountPaid());
        input = "BALANCE IDIDI Dale 6";
        inputParams = input.split(" ");
        loanBalance = loanManager.getLoanBalance(inputParams);
        Assertions.assertEquals(3652, loanBalance.getAmountPaid());
    }

    @Test
    public void getLoanBalanceNegative1() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 5000 1 6";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "BALANCE IDIDI Dale 3A";
        inputParams = input.split(" ");
        String[] loanParams = inputParams;
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.getLoanBalance(loanParams));
    }

    @Test
    public void getLoanBalanceNegative2() throws InvalidRequestException {
        String input = "LOAN IDIDI Dale 5000 1 6";
        String[] inputParams = input.split(" ");
        loanManager.createNewLoan(inputParams);
        input = "BALANCE IDIDI Dale";
        inputParams = input.split(" ");
        String[] loanParams = inputParams;
        Assertions.assertThrows(InvalidRequestException.class, () -> loanManager.getLoanBalance(loanParams));
    }

}
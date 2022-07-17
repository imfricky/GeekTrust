package com.example.geektrust.service;

import com.example.geektrust.dtos.LoanBalanceDTO;
import com.example.geektrust.enitites.Loan;
import com.example.geektrust.enitites.LumpSumPayment;
import com.example.geektrust.exceptions.InvalidRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanManager {

    Map<String, Loan> loanBook;
    Map<String, List<LumpSumPayment>> lumpSumPayment;

    public LoanManager() {
        loanBook = new HashMap<>();
        lumpSumPayment = new HashMap<>();
    }

    public void createNewLoan(String[] inputParams) throws InvalidRequestException {
        try {
            String bankName = inputParams[1];
            String borrowerName = inputParams[2];
            Integer principal = Integer.parseInt(inputParams[3]);
            Integer tenureInYears = Integer.parseInt(inputParams[4]);
            Integer interest = Integer.parseInt(inputParams[5]);
            String loanId = bankName + "_" + borrowerName;
            Double totalInterest = (double) (principal * tenureInYears * interest) / 100;
            Double totalAmount = principal + totalInterest;
            Integer emi = (int) Math.ceil(totalAmount / (tenureInYears * 12));
            Loan loan = new Loan(loanId, bankName, borrowerName, principal, tenureInYears, interest, emi, totalAmount.intValue());
            loanBook.put(loanId, loan);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Error while parsing principal, tenureInYears, interest");
        } catch (RuntimeException e) {
            throw new InvalidRequestException("Error while creating new Loan, error: " + e.getMessage());
        }
    }

    public void addLumpSumPayment(String[] inputParams) throws InvalidRequestException {
        try {
            String bankName = inputParams[1];
            String borrowerName = inputParams[2];
            Integer lumpSumAmount = Integer.parseInt(inputParams[3]);
            Integer emiNo = Integer.parseInt(inputParams[4]);
            String loanId = bankName + "_" + borrowerName;
            checkIfLoanExists(bankName, borrowerName, loanId);
            List<LumpSumPayment> lumpSumPayments;
            if (lumpSumPayment.containsKey(loanId)) {
                lumpSumPayments = lumpSumPayment.get(loanId);
            } else {
                lumpSumPayments = new ArrayList<>();
            }
            lumpSumPayments.add(new LumpSumPayment(loanId, emiNo, lumpSumAmount));
            lumpSumPayment.put(loanId, lumpSumPayments);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Error while parsing lumpsum amount, emi no");
        } catch (RuntimeException e) {
            throw new InvalidRequestException("Error while getting lump sum payment for Loan, error: " + e.getMessage());
        }
    }

    private void checkIfLoanExists(String bankName, String borrowerName, String loanId) throws InvalidRequestException {
        if (!loanBook.containsKey(loanId)) {
            throw new InvalidRequestException("No Loan find with bank name: " + bankName + " and borrower name: " + borrowerName);
        }
    }

    public LoanBalanceDTO getLoanBalance(String[] inputParams) throws InvalidRequestException {
        try {
            String bankName = inputParams[1];
            String borrowerName = inputParams[2];
            Integer emiNo = Integer.parseInt(inputParams[3]);
            String loanId = bankName + "_" + borrowerName;
            return getLoanBalanceDTO(bankName, borrowerName, emiNo, loanId);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Error while parsing emi no");
        } catch (RuntimeException e) {
            throw new InvalidRequestException("Error while getting balance for Loan, error: " + e.getMessage());
        }
    }

    private LoanBalanceDTO getLoanBalanceDTO(String bankName, String borrowerName, Integer emiNo, String loanId) throws InvalidRequestException {
        checkIfLoanExists(bankName, borrowerName, loanId);
        Loan loan = loanBook.get(loanId);
        Integer amountPaid = emiNo * loan.getEmiAmount();
        Integer lumpSumPaymentAmount = getLumpSumPaymentAmount(loanId, emiNo);
        amountPaid += lumpSumPaymentAmount;
        if (amountPaid > loan.getTotalPayableAmount()) amountPaid = loan.getTotalPayableAmount();
        Integer noOfEmiLeft = getNoOfEmiLeft(loan.getTotalPayableAmount(), amountPaid, loan.getEmiAmount());
        return new LoanBalanceDTO(bankName, borrowerName, amountPaid, noOfEmiLeft);
    }

    private Integer getLumpSumPaymentAmount(String loanId, Integer emiNo) {
        Integer lumpSumAmount = 0;
        if (lumpSumPayment.containsKey(loanId)) {
            List<LumpSumPayment> lumpSumPayments = lumpSumPayment.get(loanId);
            for (LumpSumPayment lumpSumPayment : lumpSumPayments) {
                if (lumpSumPayment.getPreviousEmiNo() <= emiNo) {
                    lumpSumAmount += lumpSumPayment.getLumpSumAmount();
                }
            }
        }
        return lumpSumAmount;
    }

    private Integer getNoOfEmiLeft(Integer totalPayableAmount, Integer totalAmountPaid, Integer emiAmount) {
        Double noOfEmiLeft = Math.ceil((double) (totalPayableAmount - totalAmountPaid) / (double) emiAmount);
        return noOfEmiLeft.intValue();
    }

}

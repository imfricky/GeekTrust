package com.example.geektrust.service;

import com.example.geektrust.constants.CommandType;
import com.example.geektrust.dtos.LoanBalanceDTO;
import com.example.geektrust.exceptions.InvalidRequestException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;

@Log4j2
public class LedgerManager {

    private final LoanManager loanManager;

    public LedgerManager() {
        loanManager = new LoanManager();
    }

    public void processInput(String input) {
        String[] inputParams = input.split(" ");
        String command = inputParams[0];
        try {
            CommandType commandType = getCommandType(command);
            switch (commandType) {
                case LOAN:
                    loanManager.createNewLoan(inputParams);
                    break;

                case PAYMENT:
                    loanManager.addLumpSumPayment(inputParams);
                    break;

                case BALANCE:
                    LoanBalanceDTO loanBalance = loanManager.getLoanBalance(inputParams);
                    System.out.println(loanBalance);
                    break;

                default:
                    break;
            }
        } catch (InvalidRequestException e) {
            log.error((e.getMessage()));
        }
    }

    public CommandType getCommandType(String type) throws InvalidRequestException {
        CommandType commandType = CommandType.getValue(type);
        if (ObjectUtils.isEmpty(commandType)) {
            throw new InvalidRequestException("Invalid Command type: " + type);
        }
        return commandType;
    }

}

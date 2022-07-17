package com.example.geektrust.service;

import com.example.geektrust.constants.CommandType;
import com.example.geektrust.exceptions.InvalidRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LedgerManagerTest {

    private LedgerManager ledgerManager;

    public LedgerManagerTest() {
        ledgerManager = new LedgerManager();
    }

    @Test
    public void getCommandTypePositiveTest() throws InvalidRequestException {
        CommandType commandType = ledgerManager.getCommandType("LOAN");
        Assertions.assertEquals(CommandType.LOAN, commandType);
    }

    @Test
    public void getCommandTypeNegativeTest() throws InvalidRequestException {
        Assertions.assertThrows(InvalidRequestException.class, () -> ledgerManager.getCommandType("CHEQUE"));
    }

    @Test
    public void processInputPositiveTest1() {
        String input = "LOAN IDIDI Dale 5000 1 6";
        ledgerManager.processInput(input);
    }

    @Test
    public void processInputPositiveTest2() {
        String input = "LOAN IDIDI Dale 5000 1 6";
        ledgerManager.processInput(input);
        input = "PAYMENT IDIDI Dale 1000 5";
        ledgerManager.processInput(input);
    }

    @Test
    public void processInputPositiveTest3() {
        String input = "LOAN IDIDI Dale 5000 1 6";
        ledgerManager.processInput(input);
        input = "BALANCE IDIDI Dale 3";
        ledgerManager.processInput(input);
    }

    @Test
    public void processInputNegativeTest1() {
        String input = "LOAD IDIDI Dale 5000 1 6";
        ledgerManager.processInput(input);
    }

}
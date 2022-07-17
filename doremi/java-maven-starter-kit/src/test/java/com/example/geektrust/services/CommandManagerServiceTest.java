package com.example.geektrust.services;

import org.junit.jupiter.api.Test;

class CommandManagerServiceTest {

    private CommandManagerService commandManagerService;

    CommandManagerServiceTest() {
        this.commandManagerService = new CommandManagerService();
    }

    @Test
    public void parseInputPositiveTest() {
        String param = "START_SUBSCRIPTION 19-09-2022";
        commandManagerService.parseInput(param);
        param = "ADD_SUBSCRIPTION MUSIC PERSONAL";
        commandManagerService.parseInput(param);
        param = "ADD_SUBSCRIPTION VIDEO PREMIUM";
        commandManagerService.parseInput(param);
        param = "ADD_SUBSCRIPTION PODCAST FREE";
        commandManagerService.parseInput(param);
        param = "ADD_TOPUP FOUR_DEVICE 3";
        commandManagerService.parseInput(param);
        param = "PRINT_RENEWAL_DETAILS";
        commandManagerService.parseInput(param);
    }

}
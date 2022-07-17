package com.example.geektrust.constants;

import lombok.Getter;

public enum CommandType {

    START_SUBSCRIPTION("START_SUBSCRIPTION"),
    ADD_SUBSCRIPTION("ADD_SUBSCRIPTION"),
    ADD_TOPUP("ADD_TOPUP"),
    PRINT_RENEWAL_DETAILS("PRINT_RENEWAL_DETAILS"),
    RENEWAL_REMINDER("RENEWAL_REMINDER"),
    RENEWAL_AMOUNT("RENEWAL_AMOUNT"),
    ADD_TOPUP_FAILED("ADD_TOPUP_FAILED"),
    ADD_SUBSCRIPTION_FAILED("ADD_SUBSCRIPTION_FAILED");

    @Getter
    private String type;

    CommandType(String type) {
        this.type = type;
    }

    public static CommandType getCommandType(String type) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.type.equals(type)) {
                return commandType;
            }
        }
        return null;
    }

}

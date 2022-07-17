package com.example.geektrust.constants;

public enum CommandType {

    LOAN("LOAN"),
    PAYMENT("PAYMENT"),
    BALANCE("BALANCE");

    private String type;

    CommandType(String type) {
        this.type = type;
    }

    public static CommandType getValue(String type) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.type.equals(type)) {
                return commandType;
            }
        }
        return null;
    }

}

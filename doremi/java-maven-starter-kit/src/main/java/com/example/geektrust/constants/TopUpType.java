package com.example.geektrust.constants;

import lombok.Getter;

public enum TopUpType {

    FOUR_DEVICE("FOUR_DEVICE", 4, 50),
    TEN_DEVICE("TEN_DEVICE", 10, 100);

    @Getter
    private String type;

    @Getter
    private Integer noOfUsers;

    @Getter
    private Integer cost;

    TopUpType(String type, Integer noOfUsers, Integer cost) {
        this.type = type;
        this.noOfUsers = noOfUsers;
        this.cost = cost;
    }

    public static TopUpType getTopUpType(String type) {
        for (TopUpType topUpType : TopUpType.values()) {
            if (topUpType.type.equals(type)) {
                return topUpType;
            }
        }
        return null;
    }

}

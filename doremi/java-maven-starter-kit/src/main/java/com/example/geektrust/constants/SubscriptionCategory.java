package com.example.geektrust.constants;

import lombok.Getter;

public enum SubscriptionCategory {

    MUSIC("MUSIC"),
    VIDEO("VIDEO"),
    PODCAST("PODCAST");

    @Getter
    private String type;

    SubscriptionCategory(String type) {
        this.type = type;
    }

    public static SubscriptionCategory getSubscriptionCategory(String type) {
        for (SubscriptionCategory subscriptionCategory : SubscriptionCategory.values()) {
            if (subscriptionCategory.type.equals(type)) {
                return subscriptionCategory;
            }
        }
        return null;
    }

}

package com.example.geektrust.constants;

public enum PlanName {

    FREE("FREE"),
    PERSONAL("PERSONAL"),
    PREMIUM("PREMIUM");

    private String type;

    PlanName(String type) {
        this.type = type;
    }

    public static PlanName getPlanName(String type) {
        for (PlanName planName : PlanName.values()) {
            if (planName.type.equals(type)) {
                return planName;
            }
        }
        return null;
    }

}

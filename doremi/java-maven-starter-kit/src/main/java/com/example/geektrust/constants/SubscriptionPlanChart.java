package com.example.geektrust.constants;

import lombok.Getter;

public enum SubscriptionPlanChart {

    MUSIC_FREE(SubscriptionCategory.MUSIC, PlanName.FREE, 0, 1),
    MUSIC_PERSONAL(SubscriptionCategory.MUSIC, PlanName.PERSONAL, 100, 1),
    MUSIC_PREMIUM(SubscriptionCategory.MUSIC, PlanName.PREMIUM, 250, 3),
    VIDEO_FREE(SubscriptionCategory.VIDEO, PlanName.FREE, 0, 1),
    VIDEO_PERSONAL(SubscriptionCategory.VIDEO, PlanName.PERSONAL, 200, 1),
    VIDEO_PREMIUM(SubscriptionCategory.VIDEO, PlanName.PREMIUM, 500, 3),
    PODCAST_FREE(SubscriptionCategory.PODCAST, PlanName.FREE, 0, 1),
    PODCAST_PERSONAL(SubscriptionCategory.PODCAST, PlanName.PERSONAL, 100, 1),
    PODCAST_PREMIUM(SubscriptionCategory.PODCAST, PlanName.PREMIUM, 300, 3);

    @Getter
    private SubscriptionCategory category;

    @Getter
    private PlanName planName;

    @Getter
    private Integer planCost;

    @Getter
    private Integer planDuration;

    SubscriptionPlanChart(SubscriptionCategory category, PlanName planName, Integer planCost, Integer planDuration) {
        this.category = category;
        this.planName = planName;
        this.planCost = planCost;
        this.planDuration = planDuration;
    }

    public static SubscriptionPlanChart getSubscriptionPlanChart(SubscriptionCategory category, PlanName planName) {
        for (SubscriptionPlanChart planChart : SubscriptionPlanChart.values()) {
            if (planChart.category.equals(category) && planChart.planName.equals(planName)) {
                return planChart;
            }
        }
        return null;
    }

}

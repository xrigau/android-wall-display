package com.xrigau.walldisplay.wall;

import android.graphics.Color;
import android.view.View;

enum JobColor {

    FAILED("red"),
    FAILED_IN_PROGRESS("red_anime"),
    UNSTABLE("yellow"),
    UNSTABLE_IN_PROGRESS("yellow_anime"),
    SUCCESS("blue"),
    SUCCESS_IN_PROGRESS("blue_anime"),
    PENDING("grey"),
    PENDING_IN_PROGRESS("grey_anime"),
    DISABLED("disabled"),
    DISABLED_IN_PROGRESS("disabled_anime"),
    ABORTED("aborted"),
    ABORTED_IN_PROGRESS("aborted_anime"),
    NO_BUILT("nobuilt"),
    NO_BUILT_IN_PROGRESS("nobuilt_anime");

    private static final String ANIME = "anime";

    private final String color;

    private JobColor(String color) {
        this.color = color;
    }

    static JobColor from(Job job) {
        String jobColor = job.getColor();
        for (JobColor value : JobColor.values()) {
            if (value.color.equals(jobColor)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid color");
    }

    void into(View view) {
        view.setBackgroundColor(parseColor());
    }

    private int parseColor() {
        switch (this) {
            case FAILED:
                return Color.parseColor("#FF2719");
            case FAILED_IN_PROGRESS:
                return Color.parseColor("#FF2719");
            case UNSTABLE:
                return Color.parseColor("#FFD100");
            case UNSTABLE_IN_PROGRESS:
                return Color.parseColor("#FFD100");
            case SUCCESS:
                return Color.parseColor("#14CC20");
            case SUCCESS_IN_PROGRESS:
                return Color.parseColor("#14CC20");
            case PENDING:
                return Color.parseColor("#4071EF");
            case PENDING_IN_PROGRESS:
                return Color.parseColor("#4071EF");
            case DISABLED:
                return Color.parseColor("#6F706F");
            case DISABLED_IN_PROGRESS:
                return Color.parseColor("#6F706F");
            case ABORTED:
                return Color.parseColor("#D59493");
            case ABORTED_IN_PROGRESS:
                return Color.parseColor("#D59493");
            case NO_BUILT:
                return Color.parseColor("#E2E3E2");
            case NO_BUILT_IN_PROGRESS:
                return Color.parseColor("#E2E3E2");
        }
        throw new IllegalArgumentException("Invalid color");
    }

    public boolean inProgress() {
        return color.endsWith(ANIME);
    }
}

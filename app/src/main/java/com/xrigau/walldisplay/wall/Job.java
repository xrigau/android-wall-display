package com.xrigau.walldisplay.wall;

class Job {

    private static final String DISABLED = "disabled";

    private final String name;
    private final String url;
    private final String color;

    Job(String name, String url, String color) {
        this.name = name;
        this.url = url;
        this.color = color;
    }

    String getName() {
        return name;
    }

    String getColor() {
        return color;
    }

    public boolean isEnabled() {
        return !isDisabled();
    }

    private boolean isDisabled() {
        return DISABLED.equals(color);
    }

}

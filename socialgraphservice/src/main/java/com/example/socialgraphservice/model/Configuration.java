package com.example.socialgraphservice.model;

public class Configuration {

    public static final int DISCOVERABLE_TO_ALL = 0;
    public static final int NOT_DISCOVERABLE = 1;
    public static final int DISCOVERABLE_TO_FOLLOWED = 2;

    private int setting;

    public Configuration(int setting) {
        this.setting = setting;
    }

    public int getSetting() {
        return setting;
    }

    public void setSetting(int setting) {
        this.setting = setting;
    }
}

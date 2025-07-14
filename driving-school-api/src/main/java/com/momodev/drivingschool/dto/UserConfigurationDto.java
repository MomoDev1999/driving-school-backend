package com.momodev.drivingschool.dto;

public class UserConfigurationDto {
    private boolean darkMode;

    public UserConfigurationDto() {
    }

    public UserConfigurationDto(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}

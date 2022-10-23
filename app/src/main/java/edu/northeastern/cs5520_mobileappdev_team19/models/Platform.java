package edu.northeastern.cs5520_mobileappdev_team19.models;

import androidx.annotation.NonNull;

public enum Platform {
    PC("pc"),
    BROWSER("browser"),
    ALL("all");

    private final String name;

    Platform(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}

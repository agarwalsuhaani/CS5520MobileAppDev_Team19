package edu.northeastern.cs5520_mobileappdev_team19.models;

import androidx.annotation.NonNull;

public enum SortBy {
    ALPHABETICAL("alphabetical"),
    RELEASE_DATE("release-date"),
    POPULARITY("popularity"),
    RELEVANCE("relevance");

    private final String name;

    SortBy(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}

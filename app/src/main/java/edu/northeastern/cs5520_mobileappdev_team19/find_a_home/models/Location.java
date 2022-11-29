package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("latitude")
    private final double latitude;

    @SerializedName("longitude")
    private final double longitude;

    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

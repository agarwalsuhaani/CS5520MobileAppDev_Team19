package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import com.google.gson.annotations.SerializedName;

public class Amenity {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private final String name;

    public Amenity(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

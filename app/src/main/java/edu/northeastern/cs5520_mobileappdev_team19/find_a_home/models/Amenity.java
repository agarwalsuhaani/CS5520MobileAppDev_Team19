package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amenity amenity = (Amenity) o;
        return Objects.equals(id, amenity.id) && Objects.equals(name, amenity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

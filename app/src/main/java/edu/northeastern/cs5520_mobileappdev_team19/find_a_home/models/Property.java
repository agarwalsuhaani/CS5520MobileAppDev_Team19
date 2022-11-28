package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Property {
    private final String id;
    private final String userId;
    private int bedCount;
    private int bathCount;
    private boolean isStudio;
    private double latitude;
    private double longitude;
    private String streetAddress;
    private String city;
    private String state;
    private int zipcode;
    private double rent;
    private double areaInSquareFeet;
    private long availableFromUTC;
    private long availableToUTC;
    private final List<Amenity> amenities;
    private final List<String> imageIds;

    public Property(String userId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.amenities = new ArrayList<>();
        this.imageIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getBedCount() {
        return bedCount;
    }

    public int getBathCount() {
        return bathCount;
    }

    public boolean isStudio() {
        return isStudio;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public double getRent() {
        return rent;
    }

    public double getAreaInSquareFeet() {
        return areaInSquareFeet;
    }

    public Date getAvailableFrom() {
        return new Date(availableFromUTC);
    }

    public Date getAvailableTo() {
        return new Date(availableToUTC);
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public List<String> getImageIds() {
        return imageIds;
    }
}

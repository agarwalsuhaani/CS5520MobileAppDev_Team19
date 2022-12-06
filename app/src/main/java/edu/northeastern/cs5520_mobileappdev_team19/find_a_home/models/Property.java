package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Property {
    @SerializedName("id")
    private String id;

    // TODO : Add to model
    @SerializedName("user")
    private final User user;

    @SerializedName("bed_count")
    private final int bedCount;

    @SerializedName("bath_count")
    private final int bathCount;

    @SerializedName("is_studio")
    private final boolean isStudio;

    @SerializedName("location")
    private final Location location;

    @SerializedName("street_address")
    private final String streetAddress;

    @SerializedName("city")
    private final String city;

    @SerializedName("state")
    private final String state;

    @SerializedName("zipcode")
    private final int zipcode;

    @SerializedName("rent")
    private final double rent;

    @SerializedName("area_sqft")
    private final double areaInSquareFeet;

    @SerializedName("available_from")
    private final long availableFromUTC;

    @SerializedName("available_to")
    private final long availableToUTC;

    @SerializedName("amenities")
    private final List<Amenity> amenities;

    public Property(
            User user,
            int bedCount,
            int bathCount,
            boolean isStudio,
            double latitude,
            double longitude,
            String streetAddress,
            String city,
            String state,
            int zipcode,
            double rent,
            double areaInSquareFeet,
            Date availableFrom,
            Date availableTo,
            List<Amenity> amenities) {
        this.user = user;
        this.bedCount = bedCount;
        this.bathCount = bathCount;
        this.isStudio = isStudio;
        this.location = new Location(latitude, longitude);
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.rent = rent;
        this.areaInSquareFeet = areaInSquareFeet;
        this.availableFromUTC = availableFrom.getTime();
        this.availableToUTC = availableTo.getTime();
        this.amenities = amenities;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public Location getLocation() {
        return location;
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
}

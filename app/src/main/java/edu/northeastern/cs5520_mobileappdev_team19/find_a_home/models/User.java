package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {
    @SerializedName("id")
    private final String id;

    @SerializedName("first_name")
    private final String firstName;

    @SerializedName("last_name")
    private final String lastName;

    @SerializedName("email")
    private final String email;

    private final long contactNumber;

    public User(String firstName, String lastName, String email, long contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}

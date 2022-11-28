package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models;

import java.util.UUID;

public class User {
    private final String id;
    private String firstName;
    private String lastName;
    private String email;
    private long contactNumber;

    public User() {
        this.id = UUID.randomUUID().toString();
    }
}

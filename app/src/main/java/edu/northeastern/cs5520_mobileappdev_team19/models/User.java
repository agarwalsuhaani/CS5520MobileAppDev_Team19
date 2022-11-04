package edu.northeastern.cs5520_mobileappdev_team19.models;

import java.util.UUID;

public class User {
    private String id;
    private String username;

    public User() {}

    public User(String username) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

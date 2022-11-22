package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;

public class PropertyService {
    private static PropertyService instance;

    private PropertyService() {
    }

    public static PropertyService getInstance() {
        if (instance == null) {
            instance = new PropertyService();
        }
        return instance;
    }

    public void add(Property property) {}

    public void update(Property property) {}

    public void delete(String id) {}

    public void get(String id) {}

    public void getAll() {}
}

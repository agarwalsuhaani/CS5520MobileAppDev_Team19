package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;
import retrofit2.Call;
import retrofit2.http.GET;

interface IAmenityAPI {
    @GET("amenities")
    Call<List<Amenity>> getAll();
}

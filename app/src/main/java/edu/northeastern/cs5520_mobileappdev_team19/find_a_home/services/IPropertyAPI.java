package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface IPropertyAPI {
    @GET("properties")
    Call<List<Property>> getAll();

    @GET("properties")
    Call<List<Property>> getAllWithinRadius(@Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") double radiusInMeters);

    @POST("properties")
    Call<Property> create(@Body Property property);
}

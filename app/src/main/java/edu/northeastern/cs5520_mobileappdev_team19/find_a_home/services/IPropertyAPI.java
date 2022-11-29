package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface IPropertyAPI {
    @GET("properties")
    Call<List<Property>> getAll();

    @POST("properties")
    Call<Property> create(@Body Property property);
}

package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface IPropertyAPI {
    @GET("properties")
    Call<List<Property>> getAll();

    @GET("properties/nearby/{center}")
    Call<List<Property>> getNearby(@Path("center") String center, @Query("distance") double distanceInKMs);

    @GET("properties/{id}")
    Call<Property> getById(@Path("id") String id);

    @POST("properties")
    Call<Property> create(@Body Property property);

    @DELETE("properties/{id}")
    Call<Void> delete(@Path("id") String id);

    @GET("properties/my")
    Call<List<Property>> getMy();
}

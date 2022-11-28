package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.User;
import retrofit2.Call;
import retrofit2.http.GET;

interface IUserAPI {
    @GET("users")
    Call<List<User>> getAll();
}

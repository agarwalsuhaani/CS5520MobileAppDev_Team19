package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;


import androidx.annotation.NonNull;

import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.common.Constants;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private static UserService instance;
    private final IUserAPI api;

    private UserService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IUserAPI.class);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void getAll(Consumer<List<User>> callback) {
        api.getAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, Throwable t) {
                // TODO : Handle failure
            }
        });
    }
}

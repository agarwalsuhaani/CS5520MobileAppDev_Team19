package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.common.Constants;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmenityService {
    private static AmenityService instance;
    private final IAmenityAPI api;

    private AmenityService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IAmenityAPI.class);
    }

    public static AmenityService getInstance() {
        if (instance == null) {
            instance = new AmenityService();
        }
        return instance;
    }

    public void getAll(Consumer<List<Amenity>> callback) {
        api.getAll().enqueue(new Callback<List<Amenity>>() {
            @Override
            public void onResponse(@NonNull Call<List<Amenity>> call, @NonNull Response<List<Amenity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Amenity>> call, @NonNull Throwable t) {
                // TODO : Handle failure
            }
        });
    }
}

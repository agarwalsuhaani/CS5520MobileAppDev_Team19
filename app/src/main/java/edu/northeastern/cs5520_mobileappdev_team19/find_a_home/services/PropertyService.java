package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.common.FirebaseUtil;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyService {
    private static PropertyService instance;

    private PropertyService() {}

    public static PropertyService getInstance() {
        if (instance == null) {
            instance = new PropertyService();
        }
        return instance;
    }

    public void add(Property property) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> {
            api.create(property).enqueue(new Callback<Property>() {
                @Override
                public void onResponse(@NonNull Call<Property> call, @NonNull Response<Property> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // TODO : Handle success
                        System.out.println("Property created");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Property> call, @NonNull Throwable t) {
                    // TODO : Handle failure
                    System.out.println(t.getMessage());
                }
            });
        });
    }

    public void update(Property property) {}

    public void delete(String id) {}

    public void get(String id) {}

    public void getAll(Consumer<List<Property>> callback) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> {
            api.getAll().enqueue(new Callback<List<Property>>() {
                @Override
                public void onResponse(@NonNull Call<List<Property>> call, @NonNull Response<List<Property>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.accept(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Property>> call, @NonNull Throwable t) {
                    // TODO : Handle failure
                }
            });
        });
    }
}

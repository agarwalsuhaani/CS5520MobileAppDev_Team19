package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services;

import android.annotation.SuppressLint;

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

    private PropertyService() {
    }

    public static PropertyService getInstance() {
        if (instance == null) {
            instance = new PropertyService();
        }
        return instance;
    }

    public void add(Property property, Consumer<Boolean> isSuccessful) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> api.create(property).enqueue(new Callback<Property>() {
            @Override
            public void onResponse(@NonNull Call<Property> call, @NonNull Response<Property> response) {
                if (response.isSuccessful() && response.body() != null) {
                    isSuccessful.accept(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Property> call, @NonNull Throwable t) {
                isSuccessful.accept(false);
            }
        }));
    }

    public void get(String id, Consumer<Property> callback, Consumer<String> failureCallback) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> api.getById(id).enqueue(setAPICallback(callback, failureCallback)));
    }

    public void getAll(Consumer<List<Property>> callback, Consumer<String> failureCallback) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> api.getAll().enqueue(setAPICallback(callback, failureCallback)));
    }

    public void delete(String id, Consumer<Void> callback, Consumer<String> failureCallback) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> api.delete(id).enqueue(setAPICallback(callback, failureCallback)));
    }

    public void getAll(double latitude, double longitude, double distanceInKMs, Consumer<List<Property>> callback, Consumer<String> failureCallback) {
        String center = String.format("%s,%s", latitude, longitude);
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> api.getNearby(center, distanceInKMs)
                .enqueue(setAPICallback(callback, failureCallback)));
    }

    public void getMy(Consumer<List<Property>> callback, Consumer<String> failureCallback) {
        FirebaseUtil.getAuthAPI(IPropertyAPI.class, api -> api.getMy().enqueue(setAPICallback(callback, failureCallback)));
    }

    private <T> Callback<T> setAPICallback(Consumer<T> callback, Consumer<String> failureCallback) {
        return new Callback<T>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    callback.accept(response.body());
                } else {
                    failureCallback.accept(String.format("%d: %s", response.code(), "The operation was not successful"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                // TODO : Handle failure
            }
        };
    }
}

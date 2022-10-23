package edu.northeastern.cs5520_mobileappdev_team19.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GamesService {
    private final IGameService api;

    private static GamesService instance;

    public static GamesService getInstance() {
        if (instance != null) {
            return instance;
        }
        return new GamesService();
    }

    private GamesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.freetogame.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IGameService.class);
    }

    public IGameService api() {
        return api;
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.services;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.GameDetailedInfo;
import edu.northeastern.cs5520_mobileappdev_team19.models.GameInfo;
import edu.northeastern.cs5520_mobileappdev_team19.models.Category;
import edu.northeastern.cs5520_mobileappdev_team19.models.Platform;
import edu.northeastern.cs5520_mobileappdev_team19.models.SortBy;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGameService {
    @GET("games")
    Call<List<GameInfo>> getAll();

    @GET("games")
    Call<List<GameInfo>> getByPlatform(@Query("platform") Platform platform);

    @GET("games")
    Call<List<GameInfo>> getByCategory(@Query("category") Category category);

    @GET("games")
    Call<List<GameInfo>> getSortedBy(@Query("sort-by") SortBy sortBy);

    @GET("games")
    Call<List<GameInfo>> getByPlatformCategorySorted(@Query("platform") Platform platform,
                                                      @Query("category") Category category,
                                                      @Query("sort-by") SortBy sortBy);

    @GET("games")
    Call<List<GameInfo>> getByTags(@Query("tag") String tags,
                                    @Query("platform") Platform platform,
                                    @Query("sort-by") SortBy sortBy);


    @GET("game")
    Call<GameDetailedInfo> getById(@Query("id") int id);
}

package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.common;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class AuthRetrofitClient {
    static Retrofit get(String token) {
        OkHttpClient authHttpClient = getAuthHttpClient(token);
        return new Retrofit.Builder()
                .client(authHttpClient)
                .baseUrl(Constants.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getAuthHttpClient(String token) {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }).build();
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.common;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.Consumer;

public class FirebaseUtil {

    public static <T> void getAuthAPI(Class<T> apiType, Consumer<T> callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            throw new IllegalStateException("User not signed in");
        }
        getToken(user, token -> {
            T api = getClient(apiType, token);
            callback.accept(api);
        });
    }

    private static <T> T getClient(Class<T> apiType, String token) {
        return AuthRetrofitClient.get(token).create(apiType);
    }

    private static void getToken(FirebaseUser user, Consumer<String> callback) {
        user.getIdToken(false).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.accept(task.getResult().getToken());
            }
        });
    }
}

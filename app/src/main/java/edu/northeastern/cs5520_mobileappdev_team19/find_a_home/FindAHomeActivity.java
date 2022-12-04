package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.UserService;

public class FindAHomeActivity extends AppCompatActivity {
    private FirebaseUser user;
    private BottomNavigationView bottomNavigationView;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_home);

        userService = UserService.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            requestSignIn(user -> {
                this.user = user;
                initialize();
            });
        } else {
            initialize();
        }
    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationViewOnItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.property_list_menu_item);
    }

    private void requestSignIn(Consumer<FirebaseUser> callback) {
        ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        IdpResponse response = result.getIdpResponse();

                        if (response != null && response.isNewUser()) {
                            String[] name = user.getDisplayName().split(" ");

                            userService.registerUser(new User(user.getUid(), name[0], name[1], user.getEmail()), (user) -> {
                                System.out.println("User created successfully!");
                            });
                        }
                        callback.accept(user);
                    }
                }
        );

        List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final BottomNavigationView.OnItemSelectedListener bottomNavigationViewOnItemSelectedListener = item -> {
        if (bottomNavigationView == null || bottomNavigationView.getSelectedItemId() == item.getItemId()) {
            return true;
        }

        if (item.getItemId() == R.id.property_list_menu_item) {
            // TODO : Set PropertyListFragment
        } else if (item.getItemId() == R.id.map_view_menu_item) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MapViewFragment()).commit();
        } else if (item.getItemId() == R.id.messages_menu_item) {
            // TODO : Set MessagesFragment
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ChatUserListFragment()).commit();
        } else if (item.getItemId() == R.id.profile_menu_item) {
            // TODO : Set ProfileFragment
        }

        return true;
    };
}
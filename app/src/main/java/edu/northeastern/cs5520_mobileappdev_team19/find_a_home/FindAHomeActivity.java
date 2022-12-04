package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class FindAHomeActivity extends AppCompatActivity {
    private FirebaseUser user;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_home);

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
        } else if (item.getItemId() == R.id.profile_menu_item) {
            // TODO : Set ProfileFragment
        }

        return true;
    };
}

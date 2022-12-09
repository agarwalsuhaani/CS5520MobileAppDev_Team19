package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        setMainContainerFragment(new PropertyListFragment());
    }

    private void requestSignIn(Consumer<FirebaseUser> callback) {
        ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        IdpResponse response = result.getIdpResponse();

                        if (response != null && response.isNewUser()) {
                            String[] name = Objects.requireNonNull(user.getDisplayName()).split(" ");

                            userService.registerUser(new User(user.getUid(), name[0], name[1], user.getEmail()), (user) -> {
                                System.out.println("User created successfully!");
                            });
                        }
                        callback.accept(user);
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Sign in required", Toast.LENGTH_LONG).show();
                        this.onBackPressed();
                    }
                }
        );

        List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final BottomNavigationView.OnItemSelectedListener bottomNavigationViewOnItemSelectedListener = item -> {
        if (bottomNavigationView == null || bottomNavigationView.getSelectedItemId() == item.getItemId()) {
            return true;
        }

        if (item.getItemId() == R.id.property_list_menu_item) {
            setMainContainerFragment(new PropertyListFragment());
        } else if (item.getItemId() == R.id.map_view_menu_item) {
            setMainContainerFragment(new MapViewFragment());
        } else if (item.getItemId() == R.id.messages_menu_item) {
            setMainContainerFragment(new ChatUserListFragment());
        } else if (item.getItemId() == R.id.profile_menu_item) {
            setMainContainerFragment(new ProfileFragment());
        }
        return true;
    };

    private void setMainContainerFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.find_a_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Leaving already?")
                    .setMessage("Are you sure that you want to sign out?")
                    .setPositiveButton("Yes", (dialog, which) ->
                            AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> onBackPressed()))
                    .setNegativeButton("No", (dialog, which) -> {});
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
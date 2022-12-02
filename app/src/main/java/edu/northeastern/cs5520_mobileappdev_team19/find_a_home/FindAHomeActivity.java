package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyListViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerSentViewAdapter;

public class FindAHomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private RecyclerView propertyListRecyclerView;
    private RecyclerView.Adapter propertyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Property> propertyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_home);

        this.propertyListRecyclerView = findViewById(R.id.rv_propertyList);

        this.propertyList = new ArrayList<Property>();

        this.layoutManager =  new GridLayoutManager(this, 2);

        propertyListRecyclerView.setLayoutManager(layoutManager);
        propertyAdapter = new PropertyListViewAdapter(propertyList, this);
        propertyListRecyclerView.setAdapter(propertyAdapter);

        // TODO: backend call remaining for recycler view

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            requestSignIn(user -> {
                this.user = user;
                // TODO : Sign-in successful. Perform next steps...
            });
        }
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
}
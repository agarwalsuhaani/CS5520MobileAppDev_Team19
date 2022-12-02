package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;

public class FindAHomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    // TODO : We can make this configurable
    private static final int MAP_VIEW_SEARCH_RADIUS_IN_METERS = 10000;
    private static final int MAP_PADDING = 100;
    private FirebaseUser user;
    private GoogleMap mapView;
    SearchView locationSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            requestSignIn(user -> {
                this.user = user;
                // TODO : Sign-in successful. Perform next steps...
            });
            return;
        }

        initializeMapView();
    }

    private void initializeMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapView = googleMap;
        initializeLocationSearchView();

        PropertyService.getInstance().getAll(properties -> {
            setPropertiesOnMap(mapView, properties);
        });
    }

    private void initializeLocationSearchView() {
        locationSearchView = findViewById(R.id.location_search_view);
        locationSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = locationSearchView.getQuery().toString();
                Geocoder geocoder = new Geocoder(FindAHomeActivity.this);
                try {
                    List<Address> addressList = geocoder.getFromLocationName(location, 1);
                    if (!addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        PropertyService.getInstance().getAll(
                                address.getLatitude(),
                                address.getLongitude(),
                                MAP_VIEW_SEARCH_RADIUS_IN_METERS,
                                properties -> setPropertiesOnMap(mapView, properties));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

    private static void setPropertiesOnMap(GoogleMap map, List<Property> properties) {
        if (properties.isEmpty()) {
            return;
        }

        map.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Property property : properties) {
            LatLng location = new LatLng(property.getLocation().getLatitude(), property.getLocation().getLongitude());
            MarkerOptions marker = new MarkerOptions().position(location);
            map.addMarker(marker);
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING));
    }
}
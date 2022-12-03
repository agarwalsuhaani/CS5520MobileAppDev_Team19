package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {
    // TODO : We can make this configurable
    private static final int MAP_VIEW_SEARCH_RADIUS_IN_KMS = 10;
    private static final int MAP_PADDING = 100;
    private GoogleMap mapView;
    private SearchView locationSearchView;
    private View propertyInfoWindowView;

    public MapViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        propertyInfoWindowView = inflater.inflate(R.layout.property_info_window, container, false);
        initializeMapView();
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapView = googleMap;
        initializeLocationSearchView();
        PropertyService.getInstance().getAll(properties -> setPropertiesOnMap(mapView, properties));
    }

    private void initializeMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void initializeLocationSearchView() {
        locationSearchView = requireView().findViewById(R.id.location_search_view);
        locationSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = locationSearchView.getQuery().toString();
                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(location, 1);
                    if (!addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        PropertyService.getInstance().getAll(
                                address.getLatitude(),
                                address.getLongitude(),
                                MAP_VIEW_SEARCH_RADIUS_IN_KMS,
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

    private void setPropertiesOnMap(GoogleMap map, List<Property> properties) {
        if (properties.isEmpty()) {
            return;
        }

        map.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Property property : properties) {
            LatLng location = new LatLng(property.getLocation().getLatitude(), property.getLocation().getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(location).title(property.getStreetAddress());
            Marker marker = map.addMarker(markerOptions);
            Objects.requireNonNull(marker).setTag(property.getId());
            builder.include(markerOptions.getPosition());
        }
        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING));

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                setInfoWindow(marker);
                return null;
            }

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                setInfoWindow(marker);
                return null;
            }
        });

        map.setOnInfoWindowClickListener(marker -> {
            Intent propertyDetail = new Intent(getActivity(), PropertyDetailActivity.class);
            propertyDetail.putExtra(PropertyDetailActivity.PROPERTY_ID, String.valueOf(marker.getTag()));
            requireActivity().startActivity(propertyDetail);
        });
    }

    private void setInfoWindow(Marker marker) {
        TextView propertyInfoWindowStreetAddress = propertyInfoWindowView.findViewById(R.id.property_info_window_street_address);
        propertyInfoWindowStreetAddress.setText(marker.getTitle());
    }
}
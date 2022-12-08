package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.DateUtils;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyFilterDialog;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyListViewAdapter;

public class PropertyListFragment extends Fragment {
    private PropertyListViewAdapter propertyAdapter;
    private final PropertyFilterDialog.FilterParams filterParams;

    PropertyListFragment() {
        this.filterParams = new PropertyFilterDialog.FilterParams(Calendar.getInstance(), Calendar.getInstance(), new HashSet<>());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_list, container, false);
        RecyclerView propertyListRecyclerView = view.findViewById(R.id.rv_property_list);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        propertyListRecyclerView.setLayoutManager(layoutManager);
        propertyAdapter = new PropertyListViewAdapter(getContext());
        propertyListRecyclerView.setAdapter(propertyAdapter);

        PropertyService.getInstance().getAll(properties -> {
            if (properties != null && !properties.isEmpty()) {
                propertyAdapter.setPropertyList(properties);
            }
        });

        FloatingActionButton filterPropertiesButton = view.findViewById(R.id.filter_properties_button);
        filterPropertiesButton.setOnClickListener(button -> {
            PropertyFilterDialog propertyFilterDialog = new PropertyFilterDialog(getContext(), filterParams);
            propertyFilterDialog.setButton(PropertyFilterDialog.BUTTON_POSITIVE, "Filter", (dialog, which) -> filterProperties());
            propertyFilterDialog.setButton(PropertyFilterDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {});
            propertyFilterDialog.show();
        });

        return view;
    }

    private void filterProperties() {
        // TODO : Consider fetching filtered list through an API
        PropertyService.getInstance().getAll(properties -> {
            if (properties != null && !properties.isEmpty()) {
                List<Property> filteredProperties = properties.stream().filter(property -> {
                    LocalDate atLeastAvailableFrom = DateUtils.toLocalDate(filterParams.getAvailableFrom()).plusDays(1);
                    LocalDate atLeastAvailableTo = DateUtils.toLocalDate(filterParams.getAvailableTo()).minusDays(1);
                    return property.getAvailableFrom().isBefore(atLeastAvailableFrom)
                            && property.getAvailableTo().isAfter(atLeastAvailableTo)
                            && property.getAmenities().containsAll(filterParams.getSelectedAmenities());
                }).collect(Collectors.toList());
                propertyAdapter.setPropertyList(filteredProperties);
            }
        });
    }
}
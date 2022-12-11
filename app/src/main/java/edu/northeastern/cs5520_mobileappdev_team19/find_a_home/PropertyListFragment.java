package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
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
    private ProgressBar progressBar;

    public PropertyListFragment() {
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
        SwipeRefreshLayout propertyListSwipeRefresh = view.findViewById(R.id.property_list_swipe_refresh);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        propertyListRecyclerView.setLayoutManager(layoutManager);
        propertyAdapter = new PropertyListViewAdapter(getContext());
        propertyListRecyclerView.setAdapter(propertyAdapter);

        propertyListSwipeRefresh.setOnRefreshListener(() -> {
            fetchProperties(res -> {
                propertyListSwipeRefresh.setRefreshing(false);
            });
        });

        progressBar = view.findViewById(R.id.progress_bar_property_list);

        fetchProperties((res) -> {
        });

        FloatingActionButton filterPropertiesButton = view.findViewById(R.id.filter_properties_button);
        filterPropertiesButton.setOnClickListener(button -> {
            ViewGroup vg = view.findViewById(android.R.id.content);
            PropertyFilterDialog propertyFilterDialog = new PropertyFilterDialog(getContext(), vg, filterParams);
            propertyFilterDialog.setButton(PropertyFilterDialog.BUTTON_POSITIVE, "Filter", (dialog, which) -> filterProperties());
            propertyFilterDialog.setButton(PropertyFilterDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {
            });
            propertyFilterDialog.setButton(PropertyFilterDialog.BUTTON_NEUTRAL, "Reset", (dialog, which) -> {
                propertyFilterDialog.resetFilters();
                fetchProperties((res) -> {
                });
            });
            propertyFilterDialog.show();
        });

        return view;
    }

    private void fetchProperties(Consumer<Void> callback) {
        progressBar.setVisibility(View.VISIBLE);
        PropertyService.getInstance().getAll(properties -> {
            if (properties != null && !properties.isEmpty()) {
                propertyAdapter.setPropertyList(properties);
            }
            progressBar.setVisibility(View.GONE);
            callback.accept(null);
        }, failure -> {
            Toast.makeText(getActivity(), "Unable to fetch properties", Toast.LENGTH_SHORT).show();
        });
    }

    private void filterProperties() {
        progressBar.setVisibility(View.VISIBLE);
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
            progressBar.setVisibility(View.GONE);
        }, failure -> {
            Toast.makeText(getActivity(), "Unable to fetch properties", Toast.LENGTH_SHORT).show();
        });
    }
}
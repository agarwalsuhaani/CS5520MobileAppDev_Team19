package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyFilterDialog;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyListViewAdapter;

public class PropertyListFragment extends Fragment {
    private PropertyListViewAdapter propertyAdapter;

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
            PropertyFilterDialog propertyFilterDialog = new PropertyFilterDialog(getContext(), filterParams -> {
                // TODO : Use params to filter list
            });
            propertyFilterDialog.show();
        });

        return view;
    }
}
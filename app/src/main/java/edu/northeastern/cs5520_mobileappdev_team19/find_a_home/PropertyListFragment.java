package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyListViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyListViewHolder;

public class PropertyListFragment extends Fragment {

    private final List<Property> propertyList;

    public PropertyListFragment() {
        this.propertyList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_list, container, false);
        RecyclerView propertyListRecyclerView = view.findViewById(R.id.rv_propertyList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        propertyListRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter<PropertyListViewHolder> propertyAdapter = new PropertyListViewAdapter(propertyList);
        propertyListRecyclerView.setAdapter(propertyAdapter);

        PropertyService.getInstance().getAll(properties -> {
            if (properties != null && !properties.isEmpty()) {
                propertyList.clear();
                propertyList.addAll(properties);
                propertyAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
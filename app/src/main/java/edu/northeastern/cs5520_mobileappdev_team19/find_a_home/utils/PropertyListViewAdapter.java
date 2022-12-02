package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;

public class PropertyListViewAdapter extends RecyclerView.Adapter<PropertyListViewHolder> {
    private List<Property> propertyList;
    private final Context context;

    public PropertyListViewAdapter(List<Property> propertyList, Context context) {
        this.propertyList = propertyList;
        this.context = context;
    }


    @NonNull
    @Override
    public PropertyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;

public class PropertyListViewAdapter extends RecyclerView.Adapter<PropertyListViewHolder> {
    private final List<Property> propertyList;
    private final Context context;

    public PropertyListViewAdapter(Context context) {
        this.context = context;
        this.propertyList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PropertyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false);
        return new PropertyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyListViewHolder holder, int position) {
        holder.tv_propertyAddress.setText(propertyList.get(position).getStreetAddress());
        holder.tv_propertyName.setText(String.valueOf(propertyList.get(position).getBathCount()));
        // TODO: Property image
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList.clear();
        this.propertyList.addAll(propertyList);
        this.notifyDataSetChanged();
    }
}

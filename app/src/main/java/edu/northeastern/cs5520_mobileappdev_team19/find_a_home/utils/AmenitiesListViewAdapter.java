package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;

public class AmenitiesListViewAdapter extends RecyclerView.Adapter<AmenitiesListViewHolder> {
    private final List<Amenity> amenityList;
    private final Context context;

    public AmenitiesListViewAdapter(List<Amenity> amenityList, Context context) {
        this.amenityList = amenityList;
        this.context = context;
    }

    @NonNull
    @Override
    public AmenitiesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.property_detail_item, parent, false);
        return new AmenitiesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesListViewHolder holder, int position) {
        holder.amenityName.setText(amenityList.get(position).getName());
        holder.amenityIcon.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return amenityList.size();
    }
}

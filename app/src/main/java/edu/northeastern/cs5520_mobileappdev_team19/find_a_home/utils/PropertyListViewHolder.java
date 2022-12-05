package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class PropertyListViewHolder extends RecyclerView.ViewHolder {
    final ImageView propertyImage;
    final TextView propertyAddress;
    final TextView propertyBedCount;
    final TextView propertyBathCount;
    final TextView propertyRent;
    final TextView propertyAreaInSquareFeet;

    public PropertyListViewHolder(@NonNull View itemView) {
        super(itemView);
        propertyImage = itemView.findViewById(R.id.iv_property_image);
        propertyAddress = itemView.findViewById(R.id.tv_property_address);
        propertyBedCount = itemView.findViewById(R.id.tv_property_bed_count);
        propertyBathCount = itemView.findViewById(R.id.tv_property_bath_count);
        propertyRent = itemView.findViewById(R.id.tv_property_rent);
        propertyAreaInSquareFeet = itemView.findViewById(R.id.tv_property_area_square_feet);
    }
}

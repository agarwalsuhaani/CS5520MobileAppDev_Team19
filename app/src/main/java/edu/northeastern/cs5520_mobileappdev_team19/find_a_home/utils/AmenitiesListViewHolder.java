package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class AmenitiesListViewHolder  extends RecyclerView.ViewHolder{
    final TextView amenityName;
    final ImageView amenityIcon;

    public AmenitiesListViewHolder(@NonNull View itemView) {
        super(itemView);
        amenityName = itemView.findViewById(R.id.property_item_text);
        amenityIcon = itemView.findViewById(R.id.property_item_icon);
    }
}

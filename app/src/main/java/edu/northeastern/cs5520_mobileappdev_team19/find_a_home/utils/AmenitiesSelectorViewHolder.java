package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class AmenitiesSelectorViewHolder extends RecyclerView.ViewHolder {
    final CheckBox selectAmenityCheckbox;
    final TextView amenityName;

    public AmenitiesSelectorViewHolder(@NonNull View itemView) {
        super(itemView);
        selectAmenityCheckbox = itemView.findViewById(R.id.select_amenity_checkbox);
        amenityName = itemView.findViewById(R.id.select_amenity_name);
    }
}

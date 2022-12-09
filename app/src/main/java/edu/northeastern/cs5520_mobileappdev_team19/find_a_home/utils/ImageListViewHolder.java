package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class ImageListViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    ImageView dismiss;

    public ImageListViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image_list_item);
        dismiss = itemView.findViewById(R.id.image_list_dismiss);
    }
}

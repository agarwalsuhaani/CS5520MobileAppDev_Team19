package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class CarouselViewHolder extends RecyclerView.ViewHolder {
    final ImageView carouselImageView;
    public CarouselViewHolder(@NonNull View itemView) {
        super(itemView);
        carouselImageView = itemView.findViewById(R.id.carousel_item_image);
    }
}

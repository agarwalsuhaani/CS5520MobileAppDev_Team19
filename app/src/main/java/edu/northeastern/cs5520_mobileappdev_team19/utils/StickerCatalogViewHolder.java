package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class StickerCatalogViewHolder extends RecyclerView.ViewHolder {
    final ImageView stickerImage;

    StickerCatalogViewHolder(@NonNull View itemView) {
        super(itemView);
        this.stickerImage = itemView.findViewById(R.id.sticker_image);
    }
}

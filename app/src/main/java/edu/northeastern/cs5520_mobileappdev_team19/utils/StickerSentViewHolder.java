package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class StickerSentViewHolder extends RecyclerView.ViewHolder {
    final ImageView stickerSent;

    public StickerSentViewHolder(@NonNull View itemView) {
        super(itemView);
        this.stickerSent = itemView.findViewById(R.id.iv_stickerSent);
    }
}

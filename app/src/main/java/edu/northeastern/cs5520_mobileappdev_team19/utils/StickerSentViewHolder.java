package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class StickerSentViewHolder extends RecyclerView.ViewHolder {
    final ImageView stickerSent;
    final TextView stickerSentCount;

    public StickerSentViewHolder(@NonNull View itemView) {
        super(itemView);
        this.stickerSent = itemView.findViewById(R.id.iv_stickerSent);
        this.stickerSentCount = itemView.findViewById(R.id.sticker_sent_count);
    }
}

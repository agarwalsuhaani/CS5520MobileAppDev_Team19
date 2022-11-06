package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Message;

public class MessagesViewHolder extends RecyclerView.ViewHolder {
    final TextView messageTimestamp;
    final ImageView messageSticker;

    MessagesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.messageTimestamp = itemView.findViewById(R.id.message_timestamp);
        this.messageSticker = itemView.findViewById(R.id.message_sticker);
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class StickerReceivedViewHolder extends RecyclerView.ViewHolder {
    final TextView receivedFrom;
    final TextView timestamp;
    final ImageView sticker;

    public StickerReceivedViewHolder(@NonNull View itemView) {
        super(itemView);
        this.receivedFrom = itemView.findViewById(R.id.tv_ReceivedFrom);
        this.timestamp = itemView.findViewById(R.id.tv_TimeStamp);
        this.sticker = itemView.findViewById(R.id.iv_stickerReceived);
    }
}

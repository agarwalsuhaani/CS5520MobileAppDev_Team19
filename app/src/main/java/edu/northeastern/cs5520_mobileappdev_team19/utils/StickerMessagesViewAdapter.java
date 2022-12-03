package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;

public class StickerMessagesViewAdapter extends MessagesViewAdapter<Long> {
    public StickerMessagesViewAdapter(Context context, String currentUserId, StickerService stickerService) {
        super(context, currentUserId, stickerService);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        AbstractMessage<Long> stickerMessage = stickerMessages.get(position);
        holder.messageSticker.setImageResource(stickerService.getById(Integer.parseInt(String.valueOf(stickerMessage.getContent()))).getId());
        holder.messageTimestamp.setText(stickerMessage.getTimestampAsString());
    }
}

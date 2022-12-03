package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;

public class StickerMessagesViewAdapter extends MessagesViewAdapter<Long> {
    private final StickerService stickerService;

    public StickerMessagesViewAdapter(Context context, String currentUserId) {
        super(context, currentUserId);
        this.stickerService = StickerService.getInstance(context);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        AbstractMessage<Long> stickerMessage = messages.get(position);
        holder.messageSticker.setImageResource(stickerService.getById(Integer.parseInt(String.valueOf(stickerMessage.getContent()))).getId());
        holder.messageTimestamp.setText(stickerMessage.getTimestampAsString());
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;

public class ChatMessagesViewAdapter extends MessagesViewAdapter<String>{

    public ChatMessagesViewAdapter(Context context, String currentUserId) {
        super(context, currentUserId);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        AbstractMessage<String> message = messages.get(position);
        holder.chatMessageContent.setText(message.getContent());
        holder.messageTimestamp.setText(message.getTimestampAsString());
        holder.messageSticker.setVisibility(View.GONE);
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.Message;

public class StickerReceivedAdapter extends RecyclerView.Adapter<StickerReceivedViewHolder> {
    private final List<Message> messages;
    private final Context context;
    private final String currentUserId;

    public StickerReceivedAdapter(Context context, String currentUserId) {
        this.messages = new ArrayList<>();
        this.context = context;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public StickerReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerReceivedViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerReceivedViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.sticker.setImageResource(message.getStickerId());
        holder.receivedFrom.setText(message.getRecipientId());
        holder.timestamp.setText(message.getTimestampAsString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


}

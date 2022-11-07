package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Message;
import edu.northeastern.cs5520_mobileappdev_team19.services.UserService;

public class StickerReceivedAdapter extends RecyclerView.Adapter<StickerReceivedViewHolder> {
    private final List<Message> messages;
    private final Context context;
    private final UserService userService;

    public StickerReceivedAdapter(Context context) {
        this.messages = new ArrayList<>();
        this.context = context;
        this.userService = new UserService();
    }

    @NonNull
    @Override
    public StickerReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerReceivedViewHolder(LayoutInflater.from(context).inflate(R.layout.sticker_received_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerReceivedViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.sticker.setImageResource(message.getStickerId());
        userService.getUser(message.getSenderId(), (user) -> holder.receivedFrom.setText(user.getUsername()), (error) -> {});
        holder.timestamp.setText(message.getTimestampAsString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        this.notifyDataSetChanged();
    }
}

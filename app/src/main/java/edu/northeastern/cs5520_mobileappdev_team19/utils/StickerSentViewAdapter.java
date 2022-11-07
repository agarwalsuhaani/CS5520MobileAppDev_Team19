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

public class StickerSentViewAdapter extends RecyclerView.Adapter<StickerSentViewHolder> {
    private final List<Message> messages;
    private final Context context;

    public StickerSentViewAdapter(Context context) {
        this.messages = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public StickerSentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerSentViewHolder(LayoutInflater.from(context).inflate(R.layout.sticker_sent_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerSentViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.stickerSent.setImageResource(message.getStickerId());
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

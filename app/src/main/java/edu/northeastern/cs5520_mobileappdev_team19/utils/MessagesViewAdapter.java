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
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;

public class MessagesViewAdapter extends RecyclerView.Adapter<MessagesViewHolder> {
    private final List<Message> messages;
    private final Context context;
    private final String currentUserId;
    private final StickerService stickerService;

    public MessagesViewAdapter(Context context, String currentUserId, StickerService stickerService) {
//        this.messages = messages.stream().sorted(Comparator.comparingLong(Message::getTimestampUTC)).collect(Collectors.toList());
        this.messages = new ArrayList<>();
        this.context = context;
        this.currentUserId = currentUserId;
        this.stickerService = stickerService;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentUserId)) {
            return R.layout.message_item_self;
        } else {
            return R.layout.message_item_nonself;
        }
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageSticker.setImageResource(stickerService.getById(message.getStickerId()).getId());
        holder.messageTimestamp.setText(message.getTimestampAsString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void newMessage(Message message) {
        if (messages.stream().noneMatch(message1 -> message1.getId().equals(message.getId()))) {
            int position = 0;
            for (Message m : messages) {
                if (m.getTimestampUTC() > message.getTimestampUTC()) {
                    break;
                }
                position++;
            }

            this.messages.add(position, message);
            notifyItemInserted(position);
        }
    }
}

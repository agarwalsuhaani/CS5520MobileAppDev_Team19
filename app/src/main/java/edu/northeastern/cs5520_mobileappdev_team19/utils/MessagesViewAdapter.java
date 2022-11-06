package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Message;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;
import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;

public class MessagesViewAdapter extends RecyclerView.Adapter<MessagesViewHolder> {
    private final List<Message> messages;
    private final Context context;
    private final String currentUserId;

    public MessagesViewAdapter(Context context, String currentUserId) {
//        this.messages = messages.stream().sorted(Comparator.comparingLong(Message::getTimestampUTC)).collect(Collectors.toList());
        this.messages = new ArrayList<>();
        this.context = context;
        this.currentUserId = currentUserId;
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
        holder.messageSticker.setImageResource(message.getStickerId());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        holder.messageTimestamp.setText(sdf.format(new Date(message.getTimestampUTC())));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void newMessage(Message message) {
        if (messages.stream().noneMatch(message1 -> message1.getId().equals(message.getId()))) {
            int position = 0;
            for (Message m: messages) {
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

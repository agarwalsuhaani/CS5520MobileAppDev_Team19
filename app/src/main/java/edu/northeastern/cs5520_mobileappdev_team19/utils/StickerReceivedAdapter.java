package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;
import edu.northeastern.cs5520_mobileappdev_team19.models.StickerMessage;
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;
import edu.northeastern.cs5520_mobileappdev_team19.services.UserService;

public class StickerReceivedAdapter extends RecyclerView.Adapter<StickerReceivedViewHolder> {
    private final List<AbstractMessage<Integer>> stickerMessages;
    private final Context context;
    private final UserService userService;
    private StickerService stickerService;

    public StickerReceivedAdapter(Context context, StickerService stickerService) {
        this.stickerMessages = new ArrayList<>();
        this.context = context;
        this.stickerService = stickerService;
        this.userService = new UserService();
    }

    @NonNull
    @Override
    public StickerReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerReceivedViewHolder(LayoutInflater.from(context).inflate(R.layout.sticker_received_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerReceivedViewHolder holder, int position) {
        AbstractMessage<Integer> stickerMessage = stickerMessages.get(position);
        holder.sticker.setImageResource(stickerService.getById(stickerMessage.getContent()).getId());
        userService.getUser(stickerMessage.getSenderId(), (user) -> holder.receivedFrom.setText(user.getUsername()), (error) -> {});
        holder.timestamp.setText(stickerMessage.getTimestampAsString());
    }

    @Override
    public int getItemCount() {
        return stickerMessages.size();
    }

    public void setMessages(List<AbstractMessage<Integer>> stickerMessages) {
        this.stickerMessages.clear();
        this.stickerMessages.addAll(stickerMessages);
        this.notifyDataSetChanged();
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Message;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;

public class StickerCatalogViewAdapter extends RecyclerView.Adapter<StickerCatalogViewHolder> {
    private final List<Sticker> stickers;
    private final Context context;
    private final MessageService messageService;
    private final String senderId;
    private final String recipientId;

    public StickerCatalogViewAdapter(List<Sticker> stickers, Context context, String senderId, String recipientId) {
        this.stickers = stickers;
        this.context = context;
        this.messageService = new MessageService();
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    @NonNull
    @Override
    public StickerCatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerCatalogViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sticker, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerCatalogViewHolder holder, int position) {
        holder.stickerImage.setImageResource(stickers.get(position).getResId());
        holder.itemView.setOnClickListener(view -> {
            messageService.send(recipientId, new Message(stickers.get(position).getResId(), senderId));
        });
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}

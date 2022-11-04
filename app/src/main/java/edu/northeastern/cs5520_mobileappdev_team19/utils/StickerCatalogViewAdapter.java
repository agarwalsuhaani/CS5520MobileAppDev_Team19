package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;

public class StickerCatalogViewAdapter extends RecyclerView.Adapter<StickerCatalogViewHolder> {
    private final List<Sticker> stickers;
    private final Context context;

    public StickerCatalogViewAdapter(List<Sticker> stickers, Context context) {
        this.stickers = stickers;
        this.context = context;
    }

    @NonNull
    @Override
    public StickerCatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerCatalogViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sticker, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerCatalogViewHolder holder, int position) {
        holder.stickerImage.setImageResource(stickers.get(position).getId());
        holder.itemView.setOnClickListener(view -> {
            // TODO : Send sticker
        });
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}

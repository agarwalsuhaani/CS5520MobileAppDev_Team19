package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;

public class StickerSentViewAdapter extends RecyclerView.Adapter<StickerSentViewHolder> {
    private final List<Map.Entry<Sticker, Long>> stickerCount;
    private final Context context;

    public StickerSentViewAdapter(Context context) {
        this.stickerCount = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public StickerSentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerSentViewHolder(LayoutInflater.from(context).inflate(R.layout.sticker_sent_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerSentViewHolder holder, int position) {
        Map.Entry<Sticker, Long> stickerCountInfo = stickerCount.get(position);
        holder.stickerSent.setImageResource(stickerCountInfo.getKey().getId());
        holder.stickerSentCount.setText(String.valueOf(stickerCountInfo.getValue()));
    }

    @Override
    public int getItemCount() {
        return stickerCount.size();
    }

    public void setStickerCount(Map<Sticker, Long> stickerCount) {
        this.stickerCount.clear();
        this.stickerCount.addAll(stickerCount.entrySet());
        this.notifyDataSetChanged();
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.GameInfo;

public class GameViewAdapter extends RecyclerView.Adapter<GameViewHolder> {
    private final List<GameInfo> games;
    private final Context context;

    public GameViewAdapter(List<GameInfo> games, Context context) {
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameViewHolder(LayoutInflater.from(context).inflate(R.layout.item_game, null));
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        // TODO : Add fields
        holder.title.setText(games.get(position).getTitle());
        Picasso.get().load(Uri.parse(games.get(position).getThumbnail())).error(R.mipmap.ic_launcher).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return this.games.size();
    }
}
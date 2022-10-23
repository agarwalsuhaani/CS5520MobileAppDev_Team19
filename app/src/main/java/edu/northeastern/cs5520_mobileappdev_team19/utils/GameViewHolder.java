package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

class GameViewHolder extends RecyclerView.ViewHolder {

    final TextView title;
    final ImageView thumbnail;

    GameViewHolder(@NonNull View itemView) {
        super(itemView);
        // TODO : Add fields
        this.title = itemView.findViewById(R.id.game_title);
        this.thumbnail = itemView.findViewById(R.id.game_thumbnail);
    }
}
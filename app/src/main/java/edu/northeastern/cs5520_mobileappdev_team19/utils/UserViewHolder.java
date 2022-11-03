package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    final TextView username;

    UserViewHolder(@NonNull View itemView) {
        super(itemView);
        this.username = itemView.findViewById(R.id.user_username);
    }
}

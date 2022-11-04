package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.ChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.User;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final List<User> users;
    private final Context context;

    public UserViewAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.username.setText(users.get(position).getUsername());
        holder.itemView.setOnClickListener(view -> {
            Intent chatActivity = new Intent(context, ChatActivity.class);
            context.startActivity(chatActivity);
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public void addUser(User user) {
        this.users.add(user);
        notifyItemInserted(getItemCount());
    }
}

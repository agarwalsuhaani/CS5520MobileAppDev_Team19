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
    private final User loggedInUser;

    public UserViewAdapter(List<User> users, Context context, User loggedInUser) {
        this.users = users;
        this.context = context;
        this.loggedInUser = loggedInUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.username.setText(users.get(position).getUsername());
        holder.itemView.setOnClickListener(view -> {
            Intent chatActivity = new Intent(context, ChatActivity.class);
            chatActivity.putExtra(ChatActivity.SENDER_ID, loggedInUser.getId());
            chatActivity.putExtra(ChatActivity.RECIPIENT_ID, users.get(position).getId());
            chatActivity.putExtra(ChatActivity.RECIPIENT_USERNAME, users.get(position).getUsername());
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

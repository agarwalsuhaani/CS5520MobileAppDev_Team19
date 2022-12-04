package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.MessageChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.User;


public class ChatUserViewAdapter extends RecyclerView.Adapter<ChatUserViewHolder> {
    private final List<User> users;
    private final Context context;
    private final FirebaseUser loggedInUser;

    public ChatUserViewAdapter(List<User> users, Context context, FirebaseUser loggedInUser) {
        this.users = users;
        this.context = context;
        this.loggedInUser = loggedInUser;
    }

    @NonNull
    @Override
    public ChatUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatUserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserViewHolder holder, int position) {
        holder.username.setText(users.get(position).getFullName());
        holder.itemView.setOnClickListener(view -> {
            Intent chatActivity = new Intent(context, MessageChatActivity.class);
            chatActivity.putExtra(MessageChatActivity.SENDER_ID, loggedInUser.getUid());
            chatActivity.putExtra(MessageChatActivity.RECIPIENT_ID, users.get(position).getId());
            chatActivity.putExtra(MessageChatActivity.RECIPIENT_USERNAME, users.get(position).getFullName());
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

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}

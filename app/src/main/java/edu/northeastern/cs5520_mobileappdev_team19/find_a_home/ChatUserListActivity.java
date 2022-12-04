package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.stream.Collectors;

import edu.northeastern.cs5520_mobileappdev_team19.MessageChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.UserService;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.ChatUserViewAdapter;


public class ChatUserListActivity extends AppCompatActivity {
    private List<User> users;
    private RecyclerView userRecyclerView;
    private ChatUserViewAdapter userViewAdapter;
    private UserService userService;
    private MessageService<String> messageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);
        userRecyclerView = findViewById(R.id.chat_user_list_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();

        if (loggedInUser == null) {
            finishActivity(0);
            return;
        }

        userService = UserService.getInstance();
        messageService = new MessageService<>(MessageChatActivity.MESSAGES_KEY);
        messageService.getUsersWithConversations(loggedInUser.getUid(), (List<String> userIds) -> {
            userService.getAll((users) -> {
                // Only shows those users who we have interacted with before.
                this.users = users.stream().filter(u -> userIds.contains(u.getId())).collect(Collectors.toList());
                userViewAdapter = new ChatUserViewAdapter(this.users, this, loggedInUser);
                userRecyclerView.setAdapter(userViewAdapter);
            });
        });
    }
}
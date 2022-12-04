package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class ChatUserListFragment extends Fragment {
    private List<User> users;
    private RecyclerView userRecyclerView;
    private ChatUserViewAdapter userViewAdapter;
    private UserService userService;
    private MessageService<String> messageService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat_user_list, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        userRecyclerView = view.findViewById(R.id.chat_user_list_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();

        if (loggedInUser == null) {
            return;
        }

        userService = UserService.getInstance();
        messageService = new MessageService<>(MessageChatActivity.MESSAGES_KEY);
        messageService.getUsersWithConversations(loggedInUser.getUid(), (List<String> userIds) -> {
            userService.getAll((users) -> {
                // Only shows those users who we have interacted with before.
                this.users = users.stream().filter(u -> userIds.contains(u.getId())).collect(Collectors.toList());
                userViewAdapter = new ChatUserViewAdapter(this.users, getActivity(), loggedInUser);
                userRecyclerView.setAdapter(userViewAdapter);
            });
        });
    }
}
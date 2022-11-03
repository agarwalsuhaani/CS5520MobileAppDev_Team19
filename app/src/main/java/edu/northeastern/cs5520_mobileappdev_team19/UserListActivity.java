package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.services.UserService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

public class UserListActivity extends AppCompatActivity {

    private List<User> users;
    private UserViewAdapter userViewAdapter;
    private RecyclerView userRecyclerView;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<>();
        userRecyclerView = findViewById(R.id.user_list_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userViewAdapter = new UserViewAdapter(users, this);
        userRecyclerView.setAdapter(userViewAdapter);

        userService = new UserService(userViewAdapter);
        ProgressBar spinner = findViewById(R.id.progress_bar_user_list);
        spinner.setVisibility(View.GONE);
    }
}
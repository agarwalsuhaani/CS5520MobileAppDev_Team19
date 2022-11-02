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
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

public class UserListActivity extends AppCompatActivity {

    private List<User> users;
    private UserViewAdapter userViewAdapter;
    private RecyclerView userRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<>();
        userRecyclerView = findViewById(R.id.user_list_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userViewAdapter = new UserViewAdapter(users, this);
        userRecyclerView.setAdapter(userViewAdapter);

        fetchUsers();
    }

    private void fetchUsers() {
        // TODO : Firebase call to fetch users
        ProgressBar spinner = findViewById(R.id.progress_bar_user_list);
        spinner.setVisibility(View.GONE);
    }
}
package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
    private User loggedInUser;
    private static final String LOGGED_IN_USER_ID = "LOGGED_IN_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<>();
        userRecyclerView = findViewById(R.id.user_list_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userService = new UserService();
        initialize();
    }

    private void initialize() {
        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        String loggedInUserId = preferences.getString(LOGGED_IN_USER_ID, null);
        if (loggedInUserId != null) {
            userService.getUser(loggedInUserId, user -> {
                if (user != null) {
                    loadRecipients(user);
                } else {
                    showLoginDialog();
                }
            }, exception -> {
                Log.e("firebase", "Error getting user", exception);
                showLoginDialog();
            });
        } else {
            showLoginDialog();
        }
    }

    private void loadRecipients(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        userViewAdapter = new UserViewAdapter(users, this, loggedInUser);
        userRecyclerView.setAdapter(userViewAdapter);
        userService.fetchUsers(userViewAdapter, loggedInUser);
        ProgressBar spinner = findViewById(R.id.progress_bar_user_list);
        spinner.setVisibility(View.GONE);
    }

    private void storeInPreferences(String key, String value) {
        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login using username");
        View userLoginView = LayoutInflater.from(getBaseContext()).inflate(R.layout.user_login, null);
        final EditText usernameEditText = userLoginView.findViewById(R.id.login_username);
        builder.setView(userLoginView);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            loggedInUser = new User(usernameEditText.getText().toString());
            userService.registerUser(loggedInUser);
            storeInPreferences(LOGGED_IN_USER_ID, loggedInUser.getId());
            loadRecipients(loggedInUser);
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!TextUtils.isEmpty(usernameEditText.getText().toString()));
            }
        };
        usernameEditText.addTextChangedListener(watcher);
        usernameEditText.addTextChangedListener(watcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_stickers_sent_received, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stickersSent:
                startActivity(new Intent(UserListActivity.this, StickerSentActivity.class));
                break;
            case R.id.stickersReceived:
                startActivity(new Intent(UserListActivity.this, StickerReceivedActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
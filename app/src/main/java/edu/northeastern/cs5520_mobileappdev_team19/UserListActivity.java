package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.services.UserService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

public class UserListActivity extends AppCompatActivity {

    private List<User> users;
    private UserViewAdapter userViewAdapter;
    private RecyclerView userRecyclerView;
    private UserService userService;
    private User loggedInUser;
    private static final String LOGGED_IN_USER_ID = "LOGGED_IN_USER_ID";
    private static int notificationId = 1;
    private MessageService messageService;
    private long activityStartupTime = Instant.now().toEpochMilli();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<>();
        userRecyclerView = findViewById(R.id.user_list_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userService = new UserService();
        messageService = new MessageService();
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

        setupMessageNotifications(loggedInUser);
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
        usernameEditText.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
                        return String.valueOf(source).toLowerCase();
                    }
                }
        });

        builder.setView(userLoginView);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            userService.registerUser(usernameEditText.getText().toString().toLowerCase(Locale.ROOT), (user) -> {
                this.loggedInUser = user;
                storeInPreferences(LOGGED_IN_USER_ID, loggedInUser.getId());
                loadRecipients(loggedInUser);
            });
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!TextUtils.isEmpty(usernameEditText.getText().toString()));
            }
        };
        usernameEditText.addTextChangedListener(watcher);
        usernameEditText.addTextChangedListener(watcher);
    }

    private void setupMessageNotifications(User user) {
        messageService.handleMessageReceivedNotifications(user.getId(), message -> {
            if (message.getTimestampUTC() < activityStartupTime) {
                return;
            }
            List<User> users = userViewAdapter.getUsers();
            User sender = users.stream().filter(u -> u.getId().equals(message.getSenderId())).findFirst().orElse(null);
            if (sender == null) {
                return;
            }
            Bitmap icon = BitmapFactory.decodeResource(getResources(), message.getStickerId());


            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(ChatActivity.SENDER_ID, user.getId());
            intent.putExtra(ChatActivity.RECIPIENT_ID, sender.getId());

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(intent);

            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ChatActivity.NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_custom_foreground)
                    .setLargeIcon(icon)
                    .setContentTitle(String.format("%s sent you a sticker", sender.getUsername()))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(resultPendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(notificationId++, builder.build());
        });
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
                Intent stickerSentActivity = new Intent(UserListActivity.this, StickerSentActivity.class);
                stickerSentActivity.putExtra(StickerSentActivity.SENDER_ID, this.loggedInUser.getId());
                startActivity(stickerSentActivity);
                break;
            case R.id.stickersReceived:
                Intent stickerReceivedActivity = new Intent(UserListActivity.this, StickerReceivedActivity.class);
                stickerReceivedActivity.putExtra(StickerReceivedActivity.RECIPIENT_ID, this.loggedInUser.getId());
                startActivity(stickerReceivedActivity);
        }
        return super.onOptionsItemSelected(item);
    }
}
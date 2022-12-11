package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.ChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.MessageChatActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.UserService;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;

public class FindAHomeActivity extends AppCompatActivity {
    private int notificationId = 0;
    private FirebaseUser user;
    private BottomNavigationView bottomNavigationView;
    private UserService userService;
    private MessageService<String> messageService;
    private long activityStartupTime = Instant.now().toEpochMilli();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_a_home);

        userService = UserService.getInstance();
        messageService = new MessageService<>(MessageChatActivity.MESSAGES_KEY);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            requestSignIn(user -> {
                this.user = user;
                initialize();
            });
        } else {
            initialize();
        }
    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationViewOnItemSelectedListener);
        setMainContainerFragment(new PropertyListFragment());
        setupMessageNotifications(this.user);
    }

    private void requestSignIn(Consumer<FirebaseUser> callback) {
        ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        IdpResponse response = result.getIdpResponse();

                        if (response != null && response.isNewUser()) {
                            String[] name = Objects.requireNonNull(user.getDisplayName()).split(" ");

                            userService.registerUser(new User(user.getUid(), name[0], name[1], user.getEmail()), (user) -> {
                                System.out.println("User created successfully!");
                            });
                        }
                        callback.accept(user);
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Sign in required", Toast.LENGTH_LONG).show();
                        this.onBackPressed();
                    }
                }
        );

        List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final BottomNavigationView.OnItemSelectedListener bottomNavigationViewOnItemSelectedListener = item -> {
        if (bottomNavigationView == null || bottomNavigationView.getSelectedItemId() == item.getItemId()) {
            return true;
        }

        if (item.getItemId() == R.id.property_list_menu_item) {
            setMainContainerFragment(new PropertyListFragment());
        } else if (item.getItemId() == R.id.map_view_menu_item) {
            setMainContainerFragment(new MapViewFragment());
        } else if (item.getItemId() == R.id.messages_menu_item) {
            setMainContainerFragment(new ChatUserListFragment());
        } else if (item.getItemId() == R.id.profile_menu_item) {
            setMainContainerFragment(new ProfileFragment());
        }
        return true;
    };

    private void setMainContainerFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.find_a_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Leaving already?")
                    .setMessage("Are you sure that you want to sign out?")
                    .setPositiveButton("Yes", (dialog, which) ->
                            AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> onBackPressed()))
                    .setNegativeButton("No", (dialog, which) -> {});
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupMessageNotifications(FirebaseUser user) {
        messageService.handleMessageReceivedNotifications(user.getUid(), message -> {
            if (message.getTimestampUTC() < activityStartupTime) {
                return;
            }
            userService.getAll((users) -> {
                User sender = users.stream().filter(u -> u.getId().equals(message.getSenderId())).findFirst().orElse(null);
                if (sender == null) {
                    return;
                }
                String messageText =  message.getContent();


                Intent intent = new Intent(this, MessageChatActivity.class);
                intent.putExtra(ChatActivity.SENDER_ID, user.getUid());
                intent.putExtra(ChatActivity.RECIPIENT_ID, sender.getId());

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(intent);

                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0,
                                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ChatActivity.NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_custom_foreground)
                        .setContentTitle(String.format("%s sent you a message", sender.getFullName()))
                        .setContentText(messageText)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(resultPendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                notificationManager.notify(notificationId++, builder.build());
            });
        });
    }
}
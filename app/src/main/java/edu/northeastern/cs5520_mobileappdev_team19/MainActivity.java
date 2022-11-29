package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.FindAHomeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }

    public void atYourServiceClick(View v) {
        startActivity(new Intent(MainActivity.this, GameListActivity.class));
    }

    public void stickItToEmClick(View v) {
        startActivity(new Intent(MainActivity.this, UserListActivity.class));
    }

    public void groupProjectClick(View v) {
        startActivity(new Intent(MainActivity.this, FindAHomeActivity.class));
    }

    public void aboutClick(View v) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }
    
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = "Chat notifications";
        String description = "Notifications for any stickers sent to you";
        NotificationChannel channel = new NotificationChannel(ChatActivity.NOTIFICATION_CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);

        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
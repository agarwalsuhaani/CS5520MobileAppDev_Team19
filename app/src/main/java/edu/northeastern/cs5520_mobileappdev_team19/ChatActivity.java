package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.Message;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.MessagesViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerCatalogViewAdapter;

public class ChatActivity extends AppCompatActivity {

    public static String NOTIFICATION_CHANNEL_ID = "Chat notifications";
    private StickerService stickerService;
    private StickerCatalogViewAdapter stickerCatalogViewAdapter;
    private RecyclerView stickerCatalogRecyclerView;
    public static final String SENDER_ID = "SENDER_ID";
    public static final String RECIPIENT_ID = "RECIPIENT_ID";
    public static final String RECIPIENT_USERNAME = "RECIPIENT_NAME";
    private String senderId;
    private String recipientId;
    private MessagesViewAdapter messagesViewAdapter;
    private RecyclerView messagesRecyclerView;
    private MessageService messageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.senderId = getIntent().getStringExtra(SENDER_ID);
        this.recipientId = getIntent().getStringExtra(RECIPIENT_ID);
        stickerService = StickerService.getInstance(this);
        messageService = new MessageService();

        stickerCatalogRecyclerView = findViewById(R.id.sticker_catalog_recycler_view);
        stickerCatalogRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        stickerCatalogViewAdapter = new StickerCatalogViewAdapter(stickerService.getAll(), this, senderId, recipientId);

        stickerCatalogRecyclerView.setAdapter(stickerCatalogViewAdapter);


        messagesRecyclerView = findViewById(R.id.messages_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesViewAdapter = new MessagesViewAdapter(this, senderId);
        messagesRecyclerView.setAdapter(messagesViewAdapter);

        messageService.handleMessageReceived(messagesRecyclerView, messagesViewAdapter, senderId, recipientId);

        configureStickerCatalogView();

        this.setTitle(getIntent().getStringExtra(RECIPIENT_USERNAME));
    }

    private void configureStickerCatalogView() {
        RecyclerView stickerCatalog = findViewById(R.id.sticker_catalog_recycler_view);
        ToggleButton stickerCatalogToggleButton = findViewById(R.id.sticker_catalog_toggle_button);
        stickerCatalogToggleButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                stickerCatalog.setVisibility(View.VISIBLE);
            } else {
                stickerCatalog.setVisibility(View.GONE);
            }
        });
    }
}
package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerReceivedAdapter;

public class StickerReceivedActivity extends AppCompatActivity {
    private String recipientId;
    public static final String RECIPIENT_ID = "RECIPIENT_ID";
    private RecyclerView stickerReceivedRecyclerView;
    private StickerReceivedAdapter stickerReceivedAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MessageService messageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_received);
        this.recipientId = getIntent().getStringExtra(RECIPIENT_ID);

        stickerReceivedRecyclerView = findViewById(R.id.rv_stickerReceived);

        layoutManager = new LinearLayoutManager(this);
        stickerReceivedRecyclerView.setLayoutManager(layoutManager);

        messageService = new MessageService();

        stickerReceivedAdapter = new StickerReceivedAdapter(this);
        stickerReceivedRecyclerView.setAdapter(stickerReceivedAdapter);

        this.setTitle("Stickers Received");

        messageService.getMessagesReceivedBy(recipientId, (messages -> stickerReceivedAdapter.setMessages(messages)));
    }
}
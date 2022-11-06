package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;

import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.MessagesViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerReceivedAdapter;

public class StickerReceivedActivity extends AppCompatActivity {
    private String recipientId;
    public static final String RECIPIENT_ID = "RECIPIENT_ID";
    private RecyclerView stickerReceivedRecyclerView;
    private StickerReceivedAdapter adapterStickerReceived;
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

        adapterStickerReceived = new StickerReceivedAdapter(this, recipientId);
        stickerReceivedRecyclerView.setAdapter(adapterStickerReceived);

        this.setTitle("Stickers Received");
    }
}
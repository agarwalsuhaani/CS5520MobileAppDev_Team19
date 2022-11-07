package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerSentViewAdapter;

public class StickerSentActivity extends AppCompatActivity {

    public static final String SENDER_ID = "SENDER_ID";
    private String senderId;
    private StickerSentViewAdapter stickerSentViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MessageService messageService;
    private RecyclerView stickerSentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_sent);

        stickerSentRecyclerView = findViewById(R.id.rv_stickerSent);

        messageService = new MessageService();

        stickerSentViewAdapter = new StickerSentViewAdapter(this);
        stickerSentRecyclerView.setAdapter(stickerSentViewAdapter);

        layoutManager = new GridLayoutManager(this, 2);

        stickerSentRecyclerView.setLayoutManager(layoutManager);

        messageService.getMessagesSentBy(senderId, (messages -> stickerSentViewAdapter.setMessages(messages)));
    }
}
package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.northeastern.cs5520_mobileappdev_team19.models.ChatMessage;
import edu.northeastern.cs5520_mobileappdev_team19.models.StickerMessage;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.ChatMessagesViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.utils.MessagesViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerMessagesViewAdapter;

public class MessageChatActivity extends AppCompatActivity {
    public static String NOTIFICATION_CHANNEL_ID = "Chat notifications";
    public static String MESSAGES_KEY = "chat_messages";
    public static final String SENDER_ID = "SENDER_ID";
    public static final String RECIPIENT_ID = "RECIPIENT_ID";
    public static final String RECIPIENT_USERNAME = "RECIPIENT_NAME";
    private MessagesViewAdapter<String> messagesViewAdapter;
    private RecyclerView messagesRecyclerView;
    private MessageService<String> messageService;
    private String senderId;
    private String recipientId;
    private EditText chatMessageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
        this.senderId = getIntent().getStringExtra(SENDER_ID);
        this.recipientId = getIntent().getStringExtra(RECIPIENT_ID);
        messageService = new MessageService<>(MESSAGES_KEY);

        messagesRecyclerView = findViewById(R.id.chat_messages_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesViewAdapter = new ChatMessagesViewAdapter(this, senderId);
        messagesRecyclerView.setAdapter(messagesViewAdapter);

        messageService.handleMessageReceived(messagesRecyclerView, messagesViewAdapter, senderId, recipientId);

        chatMessageInput = findViewById(R.id.chat_message_input);
        this.setTitle(getIntent().getStringExtra(RECIPIENT_USERNAME));
    }

    public void sendMessage(View view) {
        messageService.send(new ChatMessage(senderId, recipientId, chatMessageInput.getText().toString()));
    }
}
package edu.northeastern.cs5520_mobileappdev_team19.services;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.models.Message;

public class MessageService {
    private final DatabaseReference messagesDatabase;
    private static final String MESSAGES = "messages";
    private static final String SENDER_ID_KEY_NAME = "senderId";
    private static final String RECIPIENT_ID_KEY_NAME = "recipientId";
    private static final String SENDER_RECIPIENT_PAIR_KEY_NAME = "senderRecipientPairKey";

    public MessageService() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        messagesDatabase = database.child(MESSAGES);
    }

    public void send(Message message) {
        messagesDatabase.child(message.getId()).setValue(message);
    }

    private ValueEventListener getValueEventListenerForMessages(Consumer<List<Message>> callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Message> messages = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Message message = item.getValue(Message.class);
                        messages.add(message);
                    }
                    callback.accept(messages);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    private void getFilteredMessages(String key, String value, Consumer<List<Message>> callback) {
        messagesDatabase.orderByChild(key).equalTo(value)
                .addListenerForSingleValueEvent(getValueEventListenerForMessages(callback));
    }

    public void getMessagesReceivedBy(String recipientId, Consumer<List<Message>> callback) {
        getFilteredMessages(RECIPIENT_ID_KEY_NAME, recipientId, callback);
    }

    public void getMessagesSentBy(String senderId, Consumer<List<Message>> callback) {
        getFilteredMessages(SENDER_ID_KEY_NAME, senderId, callback);
    }

    public void getMessages(String senderId, String recipientId, Consumer<List<Message>> callback) {
        messagesDatabase
                .orderByChild(SENDER_RECIPIENT_PAIR_KEY_NAME).equalTo(Message.getSenderRecipientPairKey(senderId, recipientId))
                .addListenerForSingleValueEvent(getValueEventListenerForMessages(callback));
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.services;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.models.Message;
import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.utils.MessagesViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

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
                List<Message> messages = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Message message = item.getValue(Message.class);
                        messages.add(message);
                    }
                }
                callback.accept(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void handleMessageReceived(RecyclerView rv, MessagesViewAdapter messagesViewAdapter, String currentUserId, String otherUserId) {
        messagesDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                if (message != null && ((message.getRecipientId().equals(currentUserId) && message.getSenderId().equals(otherUserId))
                        || (message.getRecipientId().equals(otherUserId) && message.getSenderId().equals(currentUserId)))) {
                    messagesViewAdapter.newMessage(message);
                    rv.smoothScrollToPosition(messagesViewAdapter.getItemCount() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

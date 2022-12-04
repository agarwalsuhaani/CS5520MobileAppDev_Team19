package edu.northeastern.cs5520_mobileappdev_team19.services;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;
import edu.northeastern.cs5520_mobileappdev_team19.utils.MessagesViewAdapter;

public class MessageService<T> {
    private final DatabaseReference messagesDatabase;
    private static final String MESSAGES = "messages";
    private static final String SENDER_ID_KEY_NAME = "senderId";
    private static final String RECIPIENT_ID_KEY_NAME = "recipientId";
    private static final String SENDER_RECIPIENT_PAIR_KEY_NAME = "senderRecipientPairKey";
    private ChildEventListener handleMessageReceivedNotifications;
    private final GenericTypeIndicator<AbstractMessage<T>> typeIndicator;
    public MessageService() {
        this(MESSAGES);
    }

    public MessageService(String messagesKey) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        messagesDatabase = database.child(messagesKey);
        this.typeIndicator = new GenericTypeIndicator<AbstractMessage<T>>() {};
    }

    public void send(AbstractMessage<T> stickerMessage) {
        messagesDatabase.child(stickerMessage.getId()).setValue(stickerMessage);
    }

    private ValueEventListener getValueEventListenerForMessages(Consumer<List<AbstractMessage<T>>> callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AbstractMessage<T>> stickerMessages = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        AbstractMessage<T> stickerMessage = item.getValue(typeIndicator);
                        stickerMessages.add(stickerMessage);
                    }
                }
                callback.accept(stickerMessages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public void handleMessageReceivedNotifications(String currentUserId, Consumer<AbstractMessage<T>> callback) {
        if (handleMessageReceivedNotifications != null) {
            messagesDatabase.removeEventListener(handleMessageReceivedNotifications);
            handleMessageReceivedNotifications = null;
        }
        handleMessageReceivedNotifications = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AbstractMessage<T> stickerMessage = snapshot.getValue(typeIndicator);
                if (stickerMessage != null && stickerMessage.getRecipientId().equals(currentUserId)) {
                    // Call message received callback.
                    callback.accept(stickerMessage);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName) {

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
        };

        messagesDatabase.addChildEventListener(handleMessageReceivedNotifications);
    }

    public void handleMessageReceived(RecyclerView rv, MessagesViewAdapter<T> messagesViewAdapter, String currentUserId, String otherUserId) {
        messagesDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AbstractMessage<T> stickerMessage = snapshot.getValue(typeIndicator);
                if (stickerMessage != null && ((stickerMessage.getRecipientId().equals(currentUserId) && stickerMessage.getSenderId().equals(otherUserId))
                        || (stickerMessage.getRecipientId().equals(otherUserId) && stickerMessage.getSenderId().equals(currentUserId)))) {
                    messagesViewAdapter.newMessage(stickerMessage);
                    rv.smoothScrollToPosition(messagesViewAdapter.getItemCount() - 1);

                    if (stickerMessage.getRecipientId().equals(currentUserId)) {
                        // Call message received callback.
                    }
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

    private void getFilteredMessages(String key, String value, Consumer<List<AbstractMessage<T>>> callback) {
        messagesDatabase.orderByChild(key).equalTo(value)
                .addListenerForSingleValueEvent(getValueEventListenerForMessages(callback));
    }

    public void getMessagesReceivedBy(String recipientId, Consumer<List<AbstractMessage<T>>> callback) {
        getFilteredMessages(RECIPIENT_ID_KEY_NAME, recipientId, callback);
    }

    public void getMessagesSentBy(String senderId, Consumer<List<AbstractMessage<T>>> callback) {
        getFilteredMessages(SENDER_ID_KEY_NAME, senderId, callback);
    }

    public void getUsersWithConversations(String userId, Consumer<List<String>> callback) {
        getMessagesReceivedBy(userId, (List<AbstractMessage<T>> receivedMessages) -> {
            getMessagesSentBy(userId, (List<AbstractMessage<T>> sentMessages) -> {
                List<AbstractMessage<T>> messages = new ArrayList<>();
                messages.addAll(receivedMessages);
                messages.addAll(sentMessages);

                Set<String> userIds = new HashSet<>();

                for (AbstractMessage<T> message: messages) {
                    userIds.add(message.getRecipientId());
                    userIds.add(message.getSenderId());
                }

                callback.accept(new ArrayList<>(userIds));
            });
        });
    }

    public void getMessages(String senderId, String recipientId, Consumer<List<AbstractMessage<T>>> callback) {
        messagesDatabase
                .orderByChild(SENDER_RECIPIENT_PAIR_KEY_NAME).equalTo(AbstractMessage.getSenderRecipientPairKey(senderId, recipientId))
                .addListenerForSingleValueEvent(getValueEventListenerForMessages(callback));
    }
}

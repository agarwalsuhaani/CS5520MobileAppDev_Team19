package edu.northeastern.cs5520_mobileappdev_team19.services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.cs5520_mobileappdev_team19.models.Message;

public class MessageService {
    private final DatabaseReference messagesDatabase;
    private static final String MESSAGES = "messages";

    public MessageService() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        messagesDatabase = database.child(MESSAGES);
    }

    public void send(String recipientId, Message message) {
        messagesDatabase.child(recipientId).push().setValue(message);
    }
}

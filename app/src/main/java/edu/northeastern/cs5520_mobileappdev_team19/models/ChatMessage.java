package edu.northeastern.cs5520_mobileappdev_team19.models;

public class ChatMessage extends AbstractMessage<String>{
    public ChatMessage() {
        super();
    }

    public ChatMessage(String senderId, String recipientId, String message) {
        super(senderId, recipientId, message);
    }
}

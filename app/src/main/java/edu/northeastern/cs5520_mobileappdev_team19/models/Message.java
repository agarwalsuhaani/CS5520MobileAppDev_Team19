package edu.northeastern.cs5520_mobileappdev_team19.models;

import com.google.firebase.database.Exclude;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class Message {
    private String id;
    private String senderId;
    public String recipientId;
    private long timestampUTC;
    private int stickerId;

    public Message() {}

    public Message(String senderId, String recipientId, int stickerId) {
        id = UUID.randomUUID().toString();
        timestampUTC = Instant.now().toEpochMilli();
        this.stickerId = stickerId;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public int getStickerId() {
        return stickerId;
    }

    public long getTimestampUTC() {
        return timestampUTC;
    }

    @Exclude
    public LocalDateTime getReceivedOn() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampUTC), ZoneId.systemDefault());
    }

    public String getSenderRecipientPairKey() {
        return getSenderRecipientPairKey(senderId, recipientId);
    }

    public static String getSenderRecipientPairKey(String senderId, String recipientId) {
        return String.format("sender/%s/recipient/%s", senderId, recipientId);
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.models;

import com.google.firebase.database.Exclude;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class Message {
    private String id;
    private String senderId;
    private long timestampUTC;
    private int stickerId;

    public Message() {}

    public Message(int stickerId, String senderId) {
        id = UUID.randomUUID().toString();
        timestampUTC = Instant.now().toEpochMilli();
        this.stickerId = stickerId;
        this.senderId = senderId;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
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
}

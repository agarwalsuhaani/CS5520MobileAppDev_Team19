package edu.northeastern.cs5520_mobileappdev_team19.models;

import android.annotation.SuppressLint;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class AbstractMessage<T> implements IMessage<T> {
    protected String id;
    protected String senderId;
    protected String recipientId;
    protected long timestampUTC;
    protected T content;

    public AbstractMessage(String senderId, String recipientId, T content) {
        id = UUID.randomUUID().toString();
        timestampUTC = Instant.now().toEpochMilli();
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
    }

    public AbstractMessage() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSenderId() {
        return senderId;
    }

    @Override
    public String getRecipientId() {
        return recipientId;
    }

    @Override
    public long getTimestampUTC() {
        return timestampUTC;
    }

    @Exclude
    @Override
    public String getTimestampAsString() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        return sdf.format(new Date(getTimestampUTC()));
    }

    @Override
    public String getSenderRecipientPairKey() {
        return getSenderRecipientPairKey(senderId, recipientId);
    }

    @Override
    public T getContent() {
        return content;
    }

    public static String getSenderRecipientPairKey(String senderId, String recipientId) {
        return String.format("sender/%s/recipient/%s", senderId, recipientId);
    }
}

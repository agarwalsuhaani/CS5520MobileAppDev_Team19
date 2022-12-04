package edu.northeastern.cs5520_mobileappdev_team19.models;

import android.annotation.SuppressLint;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class StickerMessage extends AbstractMessage<Integer> {
    public StickerMessage() {
        super();
    }

    public StickerMessage(String senderId, String recipientId, int stickerId) {
        super(senderId, recipientId, stickerId);
    }
}

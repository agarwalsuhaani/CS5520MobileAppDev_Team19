package edu.northeastern.cs5520_mobileappdev_team19.models;

public interface IMessage<T> {
    String getId();
    String getSenderId();
    String getRecipientId();
    long getTimestampUTC();
    String getTimestampAsString();
    String getSenderRecipientPairKey();
    T getContent();
}

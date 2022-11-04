package edu.northeastern.cs5520_mobileappdev_team19.models;

import android.graphics.drawable.Drawable;

import java.util.UUID;

public class Sticker {
    private final String name;
    private final Drawable drawable;
    private final int id;

    public Sticker(String name, Drawable drawable, int id) {
        this.name = name;
        this.drawable = drawable;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public int getId() {
        return id;
    }
}

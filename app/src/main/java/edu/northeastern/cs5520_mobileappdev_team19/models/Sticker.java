package edu.northeastern.cs5520_mobileappdev_team19.models;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import java.util.UUID;

public class Sticker {
    private final String name;
    private final int id;

    public Sticker(String name, @DrawableRes int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

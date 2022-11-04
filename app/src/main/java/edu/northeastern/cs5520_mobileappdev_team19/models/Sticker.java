package edu.northeastern.cs5520_mobileappdev_team19.models;

import android.media.Image;

import androidx.annotation.DrawableRes;

public class Sticker {
    private final int resId;

    public Sticker(@DrawableRes int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }
}

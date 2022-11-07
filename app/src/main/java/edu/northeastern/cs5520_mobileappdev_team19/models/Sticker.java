package edu.northeastern.cs5520_mobileappdev_team19.models;

import androidx.annotation.DrawableRes;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sticker sticker = (Sticker) o;
        return id == sticker.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

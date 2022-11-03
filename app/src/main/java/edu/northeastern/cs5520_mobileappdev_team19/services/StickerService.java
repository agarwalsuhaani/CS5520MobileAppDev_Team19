package edu.northeastern.cs5520_mobileappdev_team19.services;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class StickerService {
    private final Context context;

    public StickerService(Context context) {
        this.context = context;
    }

    public List<Drawable> getAll() {
        Field[] drawablesFields = R.drawable.class.getFields();
        List<Drawable> drawables = new ArrayList<>();

        for (Field field : drawablesFields) {
            if (field.getName().startsWith("sticker")) {
                try {
                    Log.i("LOG_TAG", "Drawable Name: " + field.getName());
                    drawables.add(ContextCompat.getDrawable(context, field.getInt(null)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return drawables;
    }
}

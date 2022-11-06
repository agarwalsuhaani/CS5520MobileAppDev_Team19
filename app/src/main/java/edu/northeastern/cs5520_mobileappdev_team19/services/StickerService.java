package edu.northeastern.cs5520_mobileappdev_team19.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;

public class StickerService {
    private final Context context;
    private final List<Sticker> stickers;
    private Sticker defaultSticker;
    @SuppressLint("StaticFieldLeak")
    private static StickerService stickerService;

    private StickerService(Context context) {
        this.context = context;
        this.stickers = parseAll();
    }

    public static StickerService getInstance(Context context) {
        if (stickerService == null) {
            stickerService = new StickerService(context);
        }
        return stickerService;
    }

    private List<Sticker> parseAll() {
        Field[] drawablesFields = R.drawable.class.getFields();
        List<Sticker> stickers = new ArrayList<>();

        for (Field field : drawablesFields) {
            if (field.getName().startsWith("sticker")) {
                try {
                    Log.i("LOG_TAG", "Drawable Name: " + field.getName());
//                    Drawable drawable = ContextCompat.getDrawable(context, field.getInt(null));

                    if (field.getName().equals("sticker_default_unavailable")) {
                        this.defaultSticker = new Sticker("Unavailable", R.drawable.sticker_default_unavailable);
                    } else {
                        stickers.add(new Sticker(field.getName(), field.getInt(null)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return stickers;
    }

    public List<Sticker> getAll() {
        return new ArrayList<>(this.stickers);
    }

    public Sticker getById(int id) {
        return this.stickers.stream().filter(s -> s.getId() == id).findFirst().orElse(this.defaultSticker);
    }

    public Drawable getDrawableById(int id) {
        Sticker sticker = getById(id);

        return ContextCompat.getDrawable(context, sticker.getId());
    }
}

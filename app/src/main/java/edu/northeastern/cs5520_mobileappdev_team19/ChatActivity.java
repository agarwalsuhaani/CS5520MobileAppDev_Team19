package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerCatalogViewAdapter;

public class ChatActivity extends AppCompatActivity {

    private List<Sticker> stickersCatalog;
    private StickerCatalogViewAdapter stickerCatalogViewAdapter;
    private RecyclerView stickerCatalogRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        stickersCatalog = new ArrayList<>();

        // TODO : Remove
        loadDummyStickers();

        stickerCatalogRecyclerView = findViewById(R.id.sticker_catalog_recycler_view);
        stickerCatalogRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        stickerCatalogViewAdapter = new StickerCatalogViewAdapter(stickersCatalog, this);
        stickerCatalogRecyclerView.setAdapter(stickerCatalogViewAdapter);

        configureStickerCatalogView();
    }

    private void configureStickerCatalogView() {
        RecyclerView stickerCatalog = findViewById(R.id.sticker_catalog_recycler_view);
        ToggleButton stickerCatalogToggleButton = findViewById(R.id.sticker_catalog_toggle_button);
        stickerCatalogToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    stickerCatalog.setVisibility(View.VISIBLE);
                } else {
                    stickerCatalog.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadDummyStickers() {
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
        stickersCatalog.add(new Sticker(R.drawable.ic_windows));
    }
}
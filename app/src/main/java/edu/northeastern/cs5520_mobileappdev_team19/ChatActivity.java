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
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerCatalogViewAdapter;

public class ChatActivity extends AppCompatActivity {

    private StickerService stickerService;
    private StickerCatalogViewAdapter stickerCatalogViewAdapter;
    private RecyclerView stickerCatalogRecyclerView;
    public static final String SENDER_ID = "SENDER_ID";
    public static final String RECIPIENT_ID = "RECIPIENT_ID";
    private String senderId;
    private String recipientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.senderId = getIntent().getStringExtra(SENDER_ID);
        this.recipientId = getIntent().getStringExtra(RECIPIENT_ID);
        stickerService = StickerService.getInstance(this);

        stickerCatalogRecyclerView = findViewById(R.id.sticker_catalog_recycler_view);
        stickerCatalogRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        stickerCatalogViewAdapter = new StickerCatalogViewAdapter(stickerService.getAll(), this, senderId, recipientId);
        
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
}
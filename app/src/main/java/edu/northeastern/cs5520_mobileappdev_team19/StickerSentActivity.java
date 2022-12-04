package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;
import edu.northeastern.cs5520_mobileappdev_team19.models.StickerMessage;
import edu.northeastern.cs5520_mobileappdev_team19.models.Sticker;
import edu.northeastern.cs5520_mobileappdev_team19.services.MessageService;
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.StickerSentViewAdapter;

public class StickerSentActivity extends AppCompatActivity {

    public static final String SENDER_ID = "SENDER_ID";
    private String senderId;
    private StickerSentViewAdapter stickerSentViewAdapter;
    private StickerService stickerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_sent);
        this.senderId = getIntent().getStringExtra(SENDER_ID);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        RecyclerView stickerSentRecyclerView = findViewById(R.id.rv_stickerSent);
        stickerSentRecyclerView.setLayoutManager(layoutManager);
        stickerSentViewAdapter = new StickerSentViewAdapter(this);
        stickerSentRecyclerView.setAdapter(stickerSentViewAdapter);

        MessageService<Integer> messageService = new MessageService<Integer>();
        stickerService = StickerService.getInstance(this);
        messageService.getMessagesSentBy(senderId, (messages -> {
            Map<Sticker, Long> stickerCount = getStickerCount(messages);
            stickerSentViewAdapter.setStickerCount(stickerCount);
        }));
    }

    private Map<Sticker, Long> getStickerCount(List<AbstractMessage<Integer>> stickerMessages) {
        Map<Sticker, Long> stickerCount = new HashMap<>();
        for (AbstractMessage<Integer> stickerMessage : stickerMessages) {
            Sticker sticker = stickerService.getById(stickerMessage.getContent());
            if (!stickerCount.containsKey(sticker)) {
                stickerCount.put(sticker, 0L);
            }
            stickerCount.put(sticker, stickerCount.get(sticker) + 1);
        }

        return stickerCount;
    }
}

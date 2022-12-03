package edu.northeastern.cs5520_mobileappdev_team19.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.AbstractMessage;
import edu.northeastern.cs5520_mobileappdev_team19.models.StickerMessage;
import edu.northeastern.cs5520_mobileappdev_team19.services.StickerService;

public abstract class MessagesViewAdapter<T> extends RecyclerView.Adapter<MessagesViewHolder> {
    protected final List<AbstractMessage<T>> stickerMessages;
    private final Context context;
    private final String currentUserId;
    protected final StickerService stickerService;

    public MessagesViewAdapter(Context context, String currentUserId, StickerService stickerService) {
        this.stickerMessages = new ArrayList<>();
        this.context = context;
        this.currentUserId = currentUserId;
        this.stickerService = stickerService;
    }

    @Override
    public int getItemViewType(int position) {
        AbstractMessage<T> stickerMessage = stickerMessages.get(position);
        if (stickerMessage.getSenderId().equals(currentUserId)) {
            return R.layout.message_item_self;
        } else {
            return R.layout.message_item_nonself;
        }
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public int getItemCount() {
        return stickerMessages.size();
    }

    public void newMessage(AbstractMessage<T> stickerMessage) {
        if (stickerMessages.stream().noneMatch(message1 -> message1.getId().equals(stickerMessage.getId()))) {
            int position = 0;
            for (AbstractMessage<T> m : stickerMessages) {
                if (m.getTimestampUTC() > stickerMessage.getTimestampUTC()) {
                    break;
                }
                position++;
            }

            this.stickerMessages.add(position, stickerMessage);
            notifyItemInserted(position);
        }
    }
}

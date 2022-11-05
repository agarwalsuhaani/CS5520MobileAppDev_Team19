package edu.northeastern.cs5520_mobileappdev_team19.services;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.models.Message;
import edu.northeastern.cs5520_mobileappdev_team19.models.User;

public class MessageService {
    private final DatabaseReference messagesDatabase;
    private static final String MESSAGES = "messages";

    public MessageService() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        messagesDatabase = database.child(MESSAGES);
    }

    public void send(String recipientId, Message message) {
        messagesDatabase.child(recipientId).push().setValue(message);
    }

    public List<Message> getMessages(String senderId, String receiverId) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(R.drawable.sticker_angry_face, senderId, receiverId));
        messages.add(new Message(R.drawable.sticker_crying_sad_face, receiverId, senderId));
        messages.add(new Message(R.drawable.sticker_cool_shades, receiverId, senderId));
        messages.add(new Message(R.drawable.sticker_astonished_face, senderId, receiverId));
        messages.add(new Message(R.drawable.sticker_cool_shades, receiverId, senderId));
        messages.add(new Message(R.drawable.sticker_astonished_face, senderId, receiverId));
        messages.add(new Message(R.drawable.sticker_cool_shades, receiverId, senderId));
        messages.add(new Message(R.drawable.sticker_astonished_face, senderId, receiverId));
        return messages;
    }
}

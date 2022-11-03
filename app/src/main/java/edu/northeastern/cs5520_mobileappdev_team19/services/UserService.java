package edu.northeastern.cs5520_mobileappdev_team19.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

public class UserService {
    private final DatabaseReference database;
    private static final String USERS = "users";

    public UserService() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void fetchUsers(UserViewAdapter userViewAdapter, User loggedInUser) {
        database.child(USERS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user == null || user.getId().equals(loggedInUser.getId())) {
                    return;
                }
                userViewAdapter.addUser(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void registerUser(User user) {
        database.child(USERS).child(user.getId()).setValue(user);
    }

    public void getUser(String id, Consumer<User> onSuccess, Consumer<Exception> onError) {
        database.child(USERS).child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onSuccess.accept(task.getResult().getValue(User.class));
            } else {
                onError.accept(task.getException());
            }
        });
    }
}

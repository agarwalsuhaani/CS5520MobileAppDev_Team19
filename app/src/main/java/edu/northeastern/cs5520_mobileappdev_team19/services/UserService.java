package edu.northeastern.cs5520_mobileappdev_team19.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

public class UserService {
    private final DatabaseReference userDatabase;
    private static final String USERS = "users";
    private static final String USERNAME_KEY = "username";

    public UserService() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        userDatabase = database.child(USERS);
    }

    public void fetchUsers(UserViewAdapter userViewAdapter, User loggedInUser) {
        userDatabase.addChildEventListener(new ChildEventListener() {
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

    public void registerUser(String username, Consumer<User> onComplete) {
        userDatabase.orderByChild(USERNAME_KEY).equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = null;
                if (snapshot.exists()) {
                    for (DataSnapshot userItem : snapshot.getChildren()) {
                        user = userItem.getValue(User.class);
                    }
                }
                if (user == null) {
                    user = new User(username);
                    userDatabase.child(user.getId()).setValue(user);
                }
                onComplete.accept(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUser(String id, Consumer<User> onSuccess, Consumer<Exception> onError) {
        userDatabase.child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onSuccess.accept(task.getResult().getValue(User.class));
            } else {
                onError.accept(task.getException());
            }
        });
    }
}

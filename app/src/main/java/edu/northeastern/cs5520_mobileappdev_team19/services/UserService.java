package edu.northeastern.cs5520_mobileappdev_team19.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.cs5520_mobileappdev_team19.models.User;
import edu.northeastern.cs5520_mobileappdev_team19.utils.UserViewAdapter;

public class UserService {
    private final UserViewAdapter userViewAdapter;
    private final DatabaseReference database;
    private static final String USERS = "users";

    public UserService(UserViewAdapter userViewAdapter) {
        this.userViewAdapter = userViewAdapter;
        database = FirebaseDatabase.getInstance().getReference();
        fetchUsers();
    }

    public void fetchUsers() {
        database.child(USERS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                userViewAdapter.addUser(snapshot.getValue(User.class));
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
        database.child(USERS).child(user.getUsername()).setValue(user);
    }
}

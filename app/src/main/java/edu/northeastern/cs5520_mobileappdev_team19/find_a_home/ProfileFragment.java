package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.PropertyListViewAdapter;

public class ProfileFragment extends Fragment {
    private RecyclerView userPropertyRecyclerView;
    private TextView tv_userName, tv_userEmail;
    private PropertyListViewAdapter userPropertyListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userPropertyRecyclerView = view.findViewById(R.id.user_property_recycler_view);
        tv_userEmail = view.findViewById(R.id.tv_email);
        tv_userName = view.findViewById(R.id.tv_username);

        tv_userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        tv_userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        userPropertyRecyclerView.setLayoutManager(layoutManager);
        userPropertyListAdapter = new PropertyListViewAdapter(getContext());
        userPropertyRecyclerView.setAdapter(userPropertyListAdapter);

        fetchProperties((res) -> {
        });

        FloatingActionButton addPropertiesButton = view.findViewById(R.id.fab_add_property);
        addPropertiesButton.setOnClickListener(button -> {
            Intent intent = new Intent(getActivity(), AddPropertyActivity.class);
            startActivity(intent);
        });
        return view;
    }

    private void fetchProperties(Consumer<Void> callback) {
        PropertyService.getInstance().getMy(properties -> {
            if (properties != null && !properties.isEmpty()) {
                userPropertyListAdapter.setPropertyList(properties);
            }
            callback.accept(null);
        }, failure -> {
            Toast.makeText(getActivity(), "Unable to fetch properties", Toast.LENGTH_SHORT).show();
        });
    }
}
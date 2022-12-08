package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton addPropertiesButton = view.findViewById(R.id.fab_add_property);
        addPropertiesButton.setOnClickListener(button -> {
            Intent intent = new Intent(getActivity(), AddPropertyActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
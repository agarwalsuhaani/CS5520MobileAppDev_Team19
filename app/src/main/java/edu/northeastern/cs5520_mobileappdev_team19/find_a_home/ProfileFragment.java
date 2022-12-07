package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.northeastern.cs5520_mobileappdev_team19.AboutActivity;
import edu.northeastern.cs5520_mobileappdev_team19.MainActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton addPropertiesButton = view.findViewById(R.id.fab_addProperty);
        addPropertiesButton.setOnClickListener(button -> {
            Intent intent = new Intent(getActivity(), AddProperty.class);
            startActivity(intent);
        });
        return view;
    }
}
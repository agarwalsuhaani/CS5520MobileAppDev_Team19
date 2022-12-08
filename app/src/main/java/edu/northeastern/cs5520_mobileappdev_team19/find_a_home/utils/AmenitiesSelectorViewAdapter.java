package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;

public class AmenitiesSelectorViewAdapter  extends RecyclerView.Adapter<AmenitiesSelectorViewHolder> {
    private final List<Amenity> amenities;
    private final Set<Amenity> selectedAmenities;
    private final Context context;

    public AmenitiesSelectorViewAdapter(Context context) {
        this(context, new HashSet<>());
    }

    public AmenitiesSelectorViewAdapter(Context context, Set<Amenity> selectedAmenities) {
        this.context = context;
        this.amenities = new ArrayList<>();
        this.selectedAmenities = selectedAmenities;
    }

    @NonNull
    @Override
    public AmenitiesSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_amenity, parent, false);
        return new AmenitiesSelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesSelectorViewHolder holder, int position) {
        holder.amenityName.setText(amenities.get(position).getName());
        holder.selectAmenityCheckbox.setChecked(selectedAmenities.contains(amenities.get(position)));
        holder.selectAmenityCheckbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                selectedAmenities.add(amenities.get(position));
            } else {
                selectedAmenities.remove(amenities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return amenities.size();
    }

    public List<Amenity> getSelectedAmenities() {
        return new ArrayList<>(selectedAmenities);
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities.clear();
        this.amenities.addAll(amenities);
        this.notifyDataSetChanged();
    }
}

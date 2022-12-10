package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import static java.lang.String.format;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.PropertyDetailActivity;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.FirebaseStorageService;

public class PropertyListViewAdapter extends RecyclerView.Adapter<PropertyListViewHolder> {
    private final static String BED_COUNT_FORMAT = "%s bed";
    private final static String BATH_COUNT_FORMAT = "%s bath";
    private final static String RENT_FORMAT = "$%s USD";
    private final static String AREA_FORMAT = "%s sq ft";

    private final List<Property> propertyList;
    private final Context context;

    public PropertyListViewAdapter(Context context) {
        this.context = context;
        this.propertyList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PropertyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false);
        return new PropertyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyListViewHolder holder, int position) {
        // TODO: Set Property image
        holder.propertyAddress.setText(propertyList.get(position).getStreetAddress());
        holder.propertyBedCount.setText(format(Locale.getDefault(), BED_COUNT_FORMAT, propertyList.get(position).getBedCount()));
        holder.propertyBathCount.setText(format(Locale.getDefault(), BATH_COUNT_FORMAT, propertyList.get(position).getBathCount()));
        holder.propertyRent.setText(format(Locale.getDefault(), RENT_FORMAT, Math.round(propertyList.get(position).getRent())));
        holder.propertyAreaInSquareFeet.setText(format(Locale.getDefault(), AREA_FORMAT, Math.round(propertyList.get(position).getAreaInSquareFeet())));
        holder.itemView.setOnClickListener(view -> {
            Intent propertyDetail = new Intent(context, PropertyDetailActivity.class);
            propertyDetail.putExtra(PropertyDetailActivity.PROPERTY_ID, propertyList.get(position).getId());
            context.startActivity(propertyDetail);
        });
        List<String> fileIds = propertyList.get(position).getImages();
        if (fileIds != null && !fileIds.isEmpty()) {
            FirebaseStorageService.getInstance().get(Collections.singletonList(fileIds.get(0)),
                    imageUris -> Picasso.get().load(imageUris.get(0)).fit().centerCrop().into(holder.propertyImage));
        } else {
            holder.propertyImage.setImageResource(R.drawable.no_image);
        }
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList.clear();
        this.propertyList.addAll(propertyList);
        this.notifyDataSetChanged();
    }
}

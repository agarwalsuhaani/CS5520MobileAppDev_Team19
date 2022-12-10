package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class CarouselViewAdapter extends RecyclerView.Adapter<CarouselViewHolder> {

    private final Context context;
    private final List<Uri> imageURIs;

    public CarouselViewAdapter(Context context, List<Uri> imageURIs) {
        this.context = context;
        this.imageURIs = imageURIs;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Picasso.get().load(imageURIs.get(position)).fit().centerCrop().into(holder.carouselImageView);
    }

    @Override
    public int getItemCount() {
        return imageURIs.size();
    }
}

package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;
import edu.northeastern.cs5520_mobileappdev_team19.models.User;

public class ImageListViewAdapter extends RecyclerView.Adapter<ImageListViewHolder> {
    private final List<Uri> imageList;
    private final Context context;
    private final boolean isDismissable;

    public ImageListViewAdapter(List<Uri> imageList, Context context, boolean isDismissable) {
        this.imageList = imageList;
        this.context = context;
        this.isDismissable = isDismissable;
    }

    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
        return new ImageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListViewHolder holder, int position) {
        holder.image.setImageURI(imageList.get(position));
        if (!isDismissable) {
            holder.dismiss.setVisibility(View.GONE);
        } else {
            holder.dismiss.setOnClickListener((view) -> {
                deleteItem(position);
            });
        }
    }

    private void deleteItem(int position) {
        imageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imageList.size());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setImages(List<Uri> imageList) {
        this.imageList.clear();
        this.imageList.addAll(imageList);
        this.notifyDataSetChanged();
    }

    public void addImage(Uri uri) {
        this.imageList.add(uri);
        notifyItemInserted(getItemCount());
    }

    public List<Uri> getImages() {
        return new ArrayList<>(imageList);
    }
}

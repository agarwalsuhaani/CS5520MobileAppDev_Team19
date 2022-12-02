package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class PropertyListViewHolder extends RecyclerView.ViewHolder {
    private ImageView iv_propertyImage;
    private TextView tv_propertyName;
    private TextView tv_propertyAddress;

    public PropertyListViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_propertyImage = itemView.findViewById(R.id.iv_propertyList);
        tv_propertyName = itemView.findViewById(R.id.tv_propertyName);
        tv_propertyAddress = itemView.findViewById(R.id.tv_propertyAddress);
    }
}

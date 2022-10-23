package edu.northeastern.cs5520_mobileappdev_team19.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Screenshot {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
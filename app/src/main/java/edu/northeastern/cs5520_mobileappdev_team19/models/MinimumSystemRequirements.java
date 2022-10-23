package edu.northeastern.cs5520_mobileappdev_team19.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MinimumSystemRequirements {

    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("processor")
    @Expose
    private String processor;
    @SerializedName("memory")
    @Expose
    private String memory;
    @SerializedName("graphics")
    @Expose
    private String graphics;
    @SerializedName("storage")
    @Expose
    private String storage;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

}

package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sagar Virvani on 10-03-2018.
 */

public class HolidayGalleryList implements Serializable {
    @SerializedName("Id")
    @Expose
    private Integer Id;

    @SerializedName("ImagePath")
    @Expose
    private String ImagePath;

    @SerializedName("Caption")
    @Expose
    private String Caption;

    @SerializedName("Description")
    @Expose
    private String Description;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

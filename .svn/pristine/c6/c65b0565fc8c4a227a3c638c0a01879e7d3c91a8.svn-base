package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BanersListEcom implements Serializable {


    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("ImageFile")
    @Expose
    private String imageFile;

    @SerializedName("HQImageFile")
    @Expose
    private String hdImageFile;

    @SerializedName("Description")
    @Expose
    private String description;

    private boolean isSelected =false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHdImageFile() {
        return hdImageFile;
    }

    public void setHdImageFile(String hdImageFile) {
        this.hdImageFile = hdImageFile;
    }


    /* @Override
    public String toString() {
        return "BanersListEcom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", description='" + description + '\'' +
                '}';
    }*/


    @Override
    public String toString() {
        return "BanersListEcom{" +
                "id=" + id +
                '}';
    }
}

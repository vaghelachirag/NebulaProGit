package com.nebulacompanies.ibo.model.GetOfferImageResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOfferImageData {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

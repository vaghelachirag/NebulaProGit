package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BanersList {


    @SerializedName("Id")
    @Expose
    private Integer imgageId;

    @SerializedName("ImageFile")
    @Expose
    private String uRL;

    @SerializedName("Name")
    @Expose
    private String title;

    @SerializedName("Description")
    @Expose
    private String description;

    public Integer getImgageId() {
        return imgageId;
    }

    public void setImgageId(Integer imgageId) {
        this.imgageId = imgageId;
    }

    public String getuRL() {
        return uRL;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Itinerary {

    @SerializedName("itineraryId")
    @Expose
    private Integer itineraryId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("nameDay")
    @Expose
    private String nameDay;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ThumbnailImage")
    @Expose
    private String thumbnailImage;
    @SerializedName("HeaderImage")
    @Expose
    private String headerImage;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameDay() {
        return nameDay;
    }

    public void setNameDay(String nameDay) {
        this.nameDay = nameDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}

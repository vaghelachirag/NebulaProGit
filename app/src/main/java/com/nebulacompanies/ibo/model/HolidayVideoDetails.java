package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IBOBackOffice-1 on 10/26/2017.
 */

public class HolidayVideoDetails {

    @SerializedName("VideoId")
    @Expose
    private Integer videoId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Detail")
    @Expose
    private Object detail;
    @SerializedName("Size")
    @Expose
    private String size;
    @SerializedName("ThumbnailImage")
    @Expose
    private String thumbnailImage;
    @SerializedName("VideoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("Createdate")
    @Expose
    private Integer createdate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;
    @SerializedName("PublishedBy")
    @Expose
    private String publishedBy;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }


}

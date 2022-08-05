package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectBanersList {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("ProjectId")
    @Expose
    private Integer projectId;

    @SerializedName("WebURL")
    @Expose
    private String webUrl;

    @SerializedName("projectName")
    @Expose
    private String projectName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

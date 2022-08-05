package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunityGrowthModel
{
    @SerializedName("StatusCode")
    @Expose
    private Integer statuscode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<CommunityGrowthIncome> data = null;

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CommunityGrowthIncome> getData() {
        return data;
    }

    public void setData(List<CommunityGrowthIncome> data) {
        this.data = data;
    }
}

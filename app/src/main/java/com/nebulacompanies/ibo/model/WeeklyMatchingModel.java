package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeeklyMatchingModel
{
    @SerializedName("StatusCode")
    @Expose
    private Integer statuscode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<WeeklyMatching> data = null;

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

    public List<WeeklyMatching> getData() {
        return data;
    }

    public void setData(List<WeeklyMatching> data) {
        this.data = data;
    }
}

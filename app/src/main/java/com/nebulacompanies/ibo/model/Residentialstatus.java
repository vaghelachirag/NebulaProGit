package com.nebulacompanies.ibo.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Residentialstatus {

    @SerializedName("ResidentialStatusId")
    @Expose
    private Integer residentialStatusId;
    @SerializedName("ResidentialStatusName")
    @Expose
    private String residentialStatusName;

    public Integer getResidentialStatusId() {
        return residentialStatusId;
    }

    public void setResidentialStatusId(Integer residentialStatusId) {
        this.residentialStatusId = residentialStatusId;
    }

    public String getResidentialStatusName() {
        return residentialStatusName;
    }

    public void setResidentialStatusName(String residentialStatusName) {
        this.residentialStatusName = residentialStatusName;
    }

}

package com.nebulacompanies.ibo.ecom.model.unicommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatusRequestModel {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("facilityCodes")
    @Expose
    private List<String> facilityCodes;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getFacilityCodes() {
        return facilityCodes;
    }

    public void setFacilityCodes(List<String> facilityCodes) {
        this.facilityCodes = facilityCodes;
    }
}

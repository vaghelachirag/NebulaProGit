package com.nebulacompanies.ibo.dwarkaPackage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nebulacompanies.ibo.ecom.model.MyCartData;

import java.util.List;

/**
 * Created by Palak Mehta on 13-Jan-18.
 */

public class PackageListModelDwarka {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List< PackageData> data = null;

    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List <PackageData> getData() {return data;}
    public void setData(List <PackageData> data) {
        this.data=data;
    }

}

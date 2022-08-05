package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nebulacompanies.ibo.dwarkaPackage.model.PaymentSuccessResult;

public class CancelOrderModel {

    @SerializedName("StatusCode")
    @Expose
    private int StatusCode;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private CancelOrderResult data = null;


    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CancelOrderResult getData() {
        return data;
    }

    public void setData(CancelOrderResult data) {
        this.data = data;
    }
}

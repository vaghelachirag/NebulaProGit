package com.nebulacompanies.ibo.ecom.model.unicommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatusModel {

    @SerializedName("successful")
    @Expose
    private boolean successful;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("saleOrderDTO")
    @Expose
    private SaleOrderDTODetailsModel data = null;


    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SaleOrderDTODetailsModel getData() {
        return data;
    }

    public void setData(SaleOrderDTODetailsModel data) {
        this.data = data;
    }
}

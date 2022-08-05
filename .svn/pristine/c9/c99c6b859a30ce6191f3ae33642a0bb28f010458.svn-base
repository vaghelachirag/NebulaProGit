package com.nebulacompanies.ibo.ecom.model.unicommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nebulacompanies.ibo.ecom.model.AddressModel;

import java.util.List;

public class InventoryModel {

    @SerializedName("successful")
    @Expose
    private boolean successful;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("inventorySnapshots")
    @Expose
    private List<InventoryDetailsModel> data = null;


    public boolean getSuccessful() {
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

    public List<InventoryDetailsModel> getData() {
        return data;
    }

    public void setData(List<InventoryDetailsModel> data) {
        this.data = data;
    }
}

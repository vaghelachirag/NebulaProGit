package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickUpAddressModel {

    @SerializedName("Id")
    @Expose
    private Integer ID;

    @SerializedName("Address")
    @Expose
    private String pickUpAddress;

    @SerializedName("Facility")
    @Expose
    private String pickUpAddressFacility;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }


    public String getPickUpAddressFacility() {
        return pickUpAddressFacility;
    }

    public void setPickUpAddressFacility(String pickUpAddressFacility) {
        this.pickUpAddressFacility = pickUpAddressFacility;
    }
}

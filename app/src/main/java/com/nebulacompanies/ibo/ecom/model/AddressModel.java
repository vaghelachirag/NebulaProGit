package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @SerializedName("Id")
    @Expose
    private Integer ID;

    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("DeviceId")
    @Expose
    private String deviceId;

    @SerializedName("FullName")
    @Expose
    private String fullName;

    @SerializedName("AddressLine1")
    @Expose
    private String addressLineOne;

    @SerializedName("AddressLine2")
    @Expose
    private String addressLineTwo;

    @SerializedName("Landmark")
    @Expose
    private String addressLandMark;

    @SerializedName("City")
    @Expose
    private String addressCity;

    @SerializedName("State")
    @Expose
    private String addressState;

    @SerializedName("AddressType")
    @Expose
    private String addressType;

    @SerializedName("PinCode")
    @Expose
    private String addressPincode;


    @SerializedName("IsServiceable")
    @Expose
    private boolean addressServiceable;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getAddressLandMark() {
        return addressLandMark;
    }

    public void setAddressLandMark(String addressLandMark) {
        this.addressLandMark = addressLandMark;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressPincode() {
        return addressPincode;
    }

    public void setAddressPincode(String addressPincode) {
        this.addressPincode = addressPincode;
    }

    public boolean isAddressServiceable() {
        return addressServiceable;
    }

    public void setAddressServiceable(boolean addressServiceable) {
        this.addressServiceable = addressServiceable;
    }
}

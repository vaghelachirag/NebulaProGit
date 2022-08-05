package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPinDetails {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("IBOKeyID")
    @Expose
    private String iBOKeyID;
    @SerializedName("FromDate")
    @Expose
    private Integer fromDate;
    @SerializedName("Todate")
    @Expose
    private Integer todate;
    @SerializedName("PinNumber")
    @Expose
    private String pinNumber;
    @SerializedName("VerifyStatus")
    @Expose
    private Boolean verifyStatus;
    @SerializedName("VerifyDate")
    @Expose
    private String verifyDate;
    @SerializedName("PopUpCloseDateTime")
    @Expose
    private long popUpCloseDateTime;
    @SerializedName("CurrentDate")
    @Expose
    private long currentDate;
    @SerializedName("IsKYC")
    @Expose
    private String IsKYC;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getIBOKeyID() {
        return iBOKeyID;
    }

    public void setIBOKeyID(String iBOKeyID) {
        this.iBOKeyID = iBOKeyID;
    }

    public Integer getFromDate() {
        return fromDate;
    }

    public void setFromDate(Integer fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getTodate() {
        return todate;
    }

    public void setTodate(Integer todate) {
        this.todate = todate;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public Boolean getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Boolean verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public long getPopUpCloseDateTime() {
        return popUpCloseDateTime;
    }

    public void setPopUpCloseDateTime(long popUpCloseDateTime) {
        this.popUpCloseDateTime = popUpCloseDateTime;
    }

    public long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }

    public String getIsKYC() {
        return IsKYC;
    }

    public void setIsKYC(String isKYC) {
        IsKYC = isKYC;
    }
}

package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostPinList {
    @SerializedName("PaymentSummaryID")
    @Expose
    private Integer paymentSummaryID;
    @SerializedName("VerifyStatus")
    @Expose
    private Boolean verifyStatus;
    @SerializedName("VerifyDate")
    @Expose
    private String verifyDate;
    @SerializedName("IBOKeyID")
    @Expose
    private String iBOKeyID;

    public Integer getPaymentSummaryID() {
        return paymentSummaryID;
    }

    public void setPaymentSummaryID(Integer paymentSummaryID) {
        this.paymentSummaryID = paymentSummaryID;
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

    public String getIBOKeyID() {
        return iBOKeyID;
    }

    public void setIBOKeyID(String iBOKeyID) {
        this.iBOKeyID = iBOKeyID;
    }
}


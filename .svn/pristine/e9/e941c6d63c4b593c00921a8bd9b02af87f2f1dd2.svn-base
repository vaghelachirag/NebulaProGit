package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletFreezeModel {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Data data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("EwalletBalance")
        @Expose
        private Double ewalletBalance;
        @SerializedName("IsEwalletfreeze")
        @Expose
        private Boolean isEwalletfreeze;

        public Double getEwalletBalance() {
            return ewalletBalance;
        }

        public void setEwalletBalance(Double ewalletBalance) {
            this.ewalletBalance = ewalletBalance;
        }

        public Boolean getIsEwalletfreeze() {
            return isEwalletfreeze;
        }

        public void setIsEwalletfreeze(Boolean isEwalletfreeze) {
            this.isEwalletfreeze = isEwalletfreeze;
        }

    }
}



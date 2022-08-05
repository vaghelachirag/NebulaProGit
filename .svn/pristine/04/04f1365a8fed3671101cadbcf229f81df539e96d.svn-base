package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletHistory {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("IBOKeyID")
        @Expose
        private String iBOKeyID;
        @SerializedName("Amount")
        @Expose
        private Double amount;
        @SerializedName("Balance")
        @Expose
        private Double balance;
        @SerializedName("Transactiontype")
        @Expose
        private String transactiontype;
        @SerializedName("Remark")
        @Expose
        private String remark;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("longCreatedOn")
        @Expose
        private Long longcreatedOn;


        public String getIBOKeyID() {
            return iBOKeyID;
        }

        public void setIBOKeyID(String iBOKeyID) {
            this.iBOKeyID = iBOKeyID;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Double getBalance() {
            return balance;
        }

        public void setBalance(Double balance) {
            this.balance = balance;
        }

        public String getTransactiontype() {
            return transactiontype;
        }

        public void setTransactiontype(String transactiontype) {
            this.transactiontype = transactiontype;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Long getLongcreatedOn() {
            return longcreatedOn;
        }

        public void setLongcreatedOn(Long longcreatedOn) {
            this.longcreatedOn = longcreatedOn;
        }
    }
}

package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerSupportOrder
{
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

        @SerializedName("OrderMasterID")
        @Expose
        private Integer orderMasterID;
        @SerializedName("OrderNumber")
        @Expose
        private String orderNumber;
        @SerializedName("OrderDate")
        @Expose
        private String orderDate;
        @SerializedName("OrderDateInLong")
        @Expose
        private Integer orderDateInLong;

        public Integer getOrderMasterID() {
            return orderMasterID;
        }

        public void setOrderMasterID(Integer orderMasterID) {
            this.orderMasterID = orderMasterID;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public Integer getOrderDateInLong() {
            return orderDateInLong;
        }

        public void setOrderDateInLong(Integer orderDateInLong) {
            this.orderDateInLong = orderDateInLong;
        }

    }
}

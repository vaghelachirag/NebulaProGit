package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsListModel {

    @SerializedName("DetailsId")
    @Expose
    private Integer orderDetailsDetailsId;

    @SerializedName("OrderMasterId")
    @Expose
    private Integer orderDetailsOrderMasterId;

    @SerializedName("ProductId")
    @Expose
    private Integer orderDetailsProductId;

    @SerializedName("ProductName")
    @Expose
    private String orderDetailsProductName;

    @SerializedName("ImageURL")
    @Expose
    private String orderDetailsImageURL;

    @SerializedName("Price")
    @Expose
    private Integer orderDetailsPrice;

    @SerializedName("Quantity")
    @Expose
    private Integer orderDetailsQuantity;

    @SerializedName("Discount")
    @Expose
    private Integer orderDetailsDiscount;

    @SerializedName("Total")
    @Expose
    private Integer orderDetailsTotal;

    @SerializedName("ShippingDate")
    @Expose
    private String orderDetailsShippingDate;

    @SerializedName("BillingDate")
    @Expose
    private String orderDetailsBillingDate;

    public Integer getOrderDetailsDetailsId() {
        return orderDetailsDetailsId;
    }

    public void setOrderDetailsDetailsId(Integer orderDetailsDetailsId) {
        this.orderDetailsDetailsId = orderDetailsDetailsId;
    }

    public Integer getOrderDetailsOrderMasterId() {
        return orderDetailsOrderMasterId;
    }

    public void setOrderDetailsOrderMasterId(Integer orderDetailsOrderMasterId) {
        this.orderDetailsOrderMasterId = orderDetailsOrderMasterId;
    }

    public Integer getOrderDetailsProductId() {
        return orderDetailsProductId;
    }

    public void setOrderDetailsProductId(Integer orderDetailsProductId) {
        this.orderDetailsProductId = orderDetailsProductId;
    }

    public String getOrderDetailsProductName() {
        return orderDetailsProductName;
    }

    public void setOrderDetailsProductName(String orderDetailsProductName) {
        this.orderDetailsProductName = orderDetailsProductName;
    }

    public String getOrderDetailsImageURL() {
        return orderDetailsImageURL;
    }

    public void setOrderDetailsImageURL(String orderDetailsImageURL) {
        this.orderDetailsImageURL = orderDetailsImageURL;
    }

    public Integer getOrderDetailsPrice() {
        return orderDetailsPrice;
    }

    public void setOrderDetailsPrice(Integer orderDetailsPrice) {
        this.orderDetailsPrice = orderDetailsPrice;
    }

    public Integer getOrderDetailsQuantity() {
        return orderDetailsQuantity;
    }

    public void setOrderDetailsQuantity(Integer orderDetailsQuantity) {
        this.orderDetailsQuantity = orderDetailsQuantity;
    }

    public Integer getOrderDetailsDiscount() {
        return orderDetailsDiscount;
    }

    public void setOrderDetailsDiscount(Integer orderDetailsDiscount) {
        this.orderDetailsDiscount = orderDetailsDiscount;
    }

    public Integer getOrderDetailsTotal() {
        return orderDetailsTotal;
    }

    public void setOrderDetailsTotal(Integer orderDetailsTotal) {
        this.orderDetailsTotal = orderDetailsTotal;
    }

    public String getOrderDetailsShippingDate() {
        return orderDetailsShippingDate;
    }

    public void setOrderDetailsShippingDate(String orderDetailsShippingDate) {
        this.orderDetailsShippingDate = orderDetailsShippingDate;
    }

    public String getOrderDetailsBillingDate() {
        return orderDetailsBillingDate;
    }

    public void setOrderDetailsBillingDate(String orderDetailsBillingDate) {
        this.orderDetailsBillingDate = orderDetailsBillingDate;
    }
}

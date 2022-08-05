package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("Id")
    @Expose
    private Integer productID;

    @SerializedName("ProjectId")
    @Expose
    private Integer productProjectId;

    @SerializedName("ProductId")
    @Expose
    private Integer productProductId;

    @SerializedName("Name")
    @Expose
    private String productName;

    @SerializedName("Description")
    @Expose
    private String productDescription;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;


    @SerializedName("MRP")
    @Expose
    private Integer productMRP;

    @SerializedName("SalePrice")
    @Expose
    private Integer productSalePrice;

    @SerializedName("Saving")
    @Expose
    private String productSaving;

    @SerializedName("Quantity")
    @Expose
    private Integer productQuantity;

    @SerializedName("MainImage")
    @Expose
    private String productImage;

    @SerializedName("ReturnPolicy")
    @Expose
    private String productReturnPolicy;

    @SerializedName("Warranty")
    @Expose
    private String productWarranty;

    @SerializedName("MaxSaleQuantity")
    @Expose
    private Integer productMaxSaleQuantity;

    @SerializedName("ShortDescription")
    @Expose
    private String productShortDescription;

    /*public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }*/

    /*@SerializedName("ReferenceId")
    @Expose
    private String refId;*/
    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getProductProjectId() {
        return productProjectId;
    }

    public void setProductProjectId(Integer productProjectId) {
        this.productProjectId = productProjectId;
    }

    public Integer getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(Integer productProductId) {
        this.productProductId = productProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public float getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(Integer productMRP) {
        this.productMRP = productMRP;
    }

    public float getProductSalePrice() {
        return productSalePrice;
    }

    public void setProductSalePrice(Integer productSalePrice) {
        this.productSalePrice = productSalePrice;
    }

    public String getProductSaving() {
        return productSaving;
    }

    public void setProductSaving(String productSaving) {
        this.productSaving = productSaving;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }


    public String getProductReturnPolicy() {
        return productReturnPolicy;
    }

    public void setProductReturnPolicy(String productReturnPolicy) {
        this.productReturnPolicy = productReturnPolicy;
    }

    public String getProductWarranty() {
        return productWarranty;
    }

    public void setProductWarranty(String productWarranty) {
        this.productWarranty = productWarranty;
    }


    public Integer getProductMaxSaleQuantity() {
        return productMaxSaleQuantity;
    }

    public void setProductMaxSaleQuantity(Integer productMaxSaleQuantity) {
        this.productMaxSaleQuantity = productMaxSaleQuantity;
    }

    public String getProductShortDescription() {
        return productShortDescription;
    }

    public void setProductShortDescription(String productShortDescription) {
        this.productShortDescription = productShortDescription;
    }
}

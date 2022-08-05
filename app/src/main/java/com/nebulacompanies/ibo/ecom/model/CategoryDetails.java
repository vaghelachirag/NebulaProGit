package com.nebulacompanies.ibo.ecom.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class CategoryDetails implements Comparable<CategoryDetails> {

    @SerializedName("Id")
    @Expose
    private Integer categoryDetailsID;

    @SerializedName("ProjectId")
    @Expose
    private Integer categoryDetailsProjectID;

    @SerializedName("ProductId")
    @Expose
    private Integer categoryDetailsProductID;

    @SerializedName("CategoryId")
    @Expose
    private Integer categoryDetailsCategoryID;

    @SerializedName("CategoryName")
    @Expose
    private String categoryDetailsCategoryName;


    @SerializedName("Name")
    @Expose
    private String categoryDetailsName;

    @SerializedName("Description")
    @Expose
    private String categoryDetailsDescription;

    @SerializedName("MRP")
    @Expose
    private Integer categoryDetailsMRP;

    @SerializedName("SalePrice")
    @Expose
    private Integer categoryDetailsSalePrice;

    @SerializedName("Saving")
    @Expose
    private String categoryDetailsSaving;

    @SerializedName("Quantity")
    @Expose
    private Integer categoryDetailsQuantity;

    @SerializedName("MaxSaleQuantity")
    @Expose
    private Integer categoryDetailsMaxSaleQuantity;

    @SerializedName("ReturnPolicy")
    @Expose
    private String categoryDetailsReturnPolicy;

    @SerializedName("Warranty")
    @Expose
    private String categoryDetailsWarranty;

    @SerializedName("MainImage")
    @Expose
    private String categoryDetailsMainImage;

    @SerializedName("InStock")
    @Expose
    private boolean categoryDetailsInStock;

    @SerializedName("SKU")
    @Expose
    private String categoryDetailsSKU;

    @SerializedName("Weight")
    @Expose
    private String categoryDetailsWeight;


    @SerializedName("Dimension")
    @Expose
    private String categoryDetailsDimension;


    @SerializedName("Manufacturer")
    @Expose
    private String categoryDetailsManufacturer;

    @SerializedName("VolWt")
    @Expose
    private String categoryDetailsVolWt;


    @SerializedName("PV")
    @Expose
    private Integer categoryDetailsPV;


    @SerializedName("BV")
    @Expose
    private Integer categoryDetailsBV;

    @SerializedName("NV")
    @Expose
    private Integer categoryDetailsNV;


    public Integer getCategoryDetailsID() {
        return categoryDetailsID;
    }

    public void setCategoryDetailsID(Integer categoryDetailsID) {
        this.categoryDetailsID = categoryDetailsID;
    }

    public Integer getCategoryDetailsProjectID() {
        return categoryDetailsProjectID;
    }

    public void setCategoryDetailsProjectID(Integer categoryDetailsProjectID) {
        this.categoryDetailsProjectID = categoryDetailsProjectID;
    }

    public Integer getCategoryDetailsProductID() {
        return categoryDetailsProductID;
    }

    public void setCategoryDetailsProductID(Integer categoryDetailsProductID) {
        this.categoryDetailsProductID = categoryDetailsProductID;
    }

    public Integer getCategoryDetailsCategoryID() {
        return categoryDetailsCategoryID;
    }

    public void setCategoryDetailsCategoryID(Integer categoryDetailsCategoryID) {
        this.categoryDetailsCategoryID = categoryDetailsCategoryID;
    }

    public String getCategoryDetailsCategoryName() {
        return categoryDetailsCategoryName;
    }

    public void setCategoryDetailsCategoryName(String categoryDetailsCategoryName) {
        this.categoryDetailsCategoryName = categoryDetailsCategoryName;
    }

    public String getCategoryDetailsName() {
        return categoryDetailsName;
    }

    public void setCategoryDetailsName(String categoryDetailsName) {
        this.categoryDetailsName = categoryDetailsName;
    }

    public String getCategoryDetailsDescription() {
        return categoryDetailsDescription;
    }

    public void setCategoryDetailsDescription(String categoryDetailsDescription) {
        this.categoryDetailsDescription = categoryDetailsDescription;
    }

    public Integer getCategoryDetailsMRP() {
        return categoryDetailsMRP;
    }

    public void setCategoryDetailsMRP(Integer categoryDetailsMRP) {
        this.categoryDetailsMRP = categoryDetailsMRP;
    }

    public Integer getCategoryDetailsSalePrice() {
        return categoryDetailsSalePrice;
    }

    public void setCategoryDetailsSalePrice(Integer categoryDetailsSalePrice) {
        this.categoryDetailsSalePrice = categoryDetailsSalePrice;
    }

    public String getCategoryDetailsSaving() {
        return categoryDetailsSaving;
    }

    public void setCategoryDetailsSaving(String categoryDetailsSaving) {
        this.categoryDetailsSaving = categoryDetailsSaving;
    }

    public Integer getCategoryDetailsQuantity() {
        return categoryDetailsQuantity;
    }

    public void setCategoryDetailsQuantity(Integer categoryDetailsQuantity) {
        this.categoryDetailsQuantity = categoryDetailsQuantity;
    }

    public Integer getCategoryDetailsMaxSaleQuantity() {
        return categoryDetailsMaxSaleQuantity;
    }

    public void setCategoryDetailsMaxSaleQuantity(Integer categoryDetailsMaxSaleQuantity) {
        this.categoryDetailsMaxSaleQuantity = categoryDetailsMaxSaleQuantity;
    }

    public String getCategoryDetailsReturnPolicy() {
        return categoryDetailsReturnPolicy;
    }

    public void setCategoryDetailsReturnPolicy(String categoryDetailsReturnPolicy) {
        this.categoryDetailsReturnPolicy = categoryDetailsReturnPolicy;
    }

    public String getCategoryDetailsWarranty() {
        return categoryDetailsWarranty;
    }

    public void setCategoryDetailsWarranty(String categoryDetailsWarranty) {
        this.categoryDetailsWarranty = categoryDetailsWarranty;
    }

    public String getCategoryDetailsMainImage() {
        return categoryDetailsMainImage;
    }

    public void setCategoryDetailsMainImage(String categoryDetailsMainImage) {
        this.categoryDetailsMainImage = categoryDetailsMainImage;
    }

    public boolean isCategoryDetailsInStock() {
        return categoryDetailsInStock;
    }

    public void setCategoryDetailsInStock(boolean categoryDetailsInStock) {
        this.categoryDetailsInStock = categoryDetailsInStock;
    }

    public String getCategoryDetailsSKU() {
        return categoryDetailsSKU;
    }

    public void setCategoryDetailsSKU(String categoryDetailsSKU) {
        this.categoryDetailsSKU = categoryDetailsSKU;
    }

    public String getCategoryDetailsWeight() {
        return categoryDetailsWeight;
    }

    public void setCategoryDetailsWeight(String categoryDetailsWeight) {
        this.categoryDetailsWeight = categoryDetailsWeight;
    }

    public String getCategoryDetailsDimension() {
        return categoryDetailsDimension;
    }

    public void setCategoryDetailsDimension(String categoryDetailsDimension) {
        this.categoryDetailsDimension = categoryDetailsDimension;
    }

    public String getCategoryDetailsManufacturer() {
        return categoryDetailsManufacturer;
    }

    public void setCategoryDetailsManufacturer(String categoryDetailsManufacturer) {
        this.categoryDetailsManufacturer = categoryDetailsManufacturer;
    }

    public String getCategoryDetailsVolWt() {
        return categoryDetailsVolWt;
    }

    public void setCategoryDetailsVolWt(String categoryDetailsVolWt) {
        this.categoryDetailsVolWt = categoryDetailsVolWt;
    }

    public Integer getCategoryDetailsPV() {
        return categoryDetailsPV;
    }

    public void setCategoryDetailsPV(Integer categoryDetailsPV) {
        this.categoryDetailsPV = categoryDetailsPV;
    }

    public Integer getCategoryDetailsBV() {
        return categoryDetailsBV;
    }

    public void setCategoryDetailsBV(Integer categoryDetailsBV) {
        this.categoryDetailsBV = categoryDetailsBV;
    }

    public Integer getCategoryDetailsNV() {
        return categoryDetailsNV;
    }

    public void setCategoryDetailsNV(Integer categoryDetailsNV) {
        this.categoryDetailsNV = categoryDetailsNV;
    }


    //    public int compare(Products a, Products b) {
//        return a.getPrice().compareTo(b.getPrice());
//    }

//    public int compareTo(CategoryDetails o1, CategoryDetails o2) {
//        return o1.getCategoryDetailsSalePrice().compareTo(o2.getCategoryDetailsSalePrice());
//    }

//    public int compareTo(CategoryDetails CategoryDetail) {
//        Log.d("CategoryDetailcompare", "CategoryDetailcompare: ");
//        return this.getCategoryDetailsName().compareTo(CategoryDetail.getCategoryDetailsName());
////        return this.categoryDetailsSalePrice.compareTo(CategoryDetail.categoryDetailsSalePrice);
////        return this.getCategoryDetailsSalePrice() - this.getCategoryDetailsSalePrice();
////        return new Date(this.transactionDate).compareTo(new Date(transaction.transactionDate));
//    }

    @Override
    public int compareTo(@NonNull CategoryDetails categoryDetailsName) {
        return this.categoryDetailsName.compareTo(categoryDetailsName.categoryDetailsName);
    }


}

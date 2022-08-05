package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewCategoryProductList {

    @SerializedName("TotalRecord")
    @Expose
    private Integer TotalRecord;

    @SerializedName("Products")
    @Expose
    private List<SubCategoryProductList> data= null;

    public Integer getTotalRecord() {
        return TotalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        TotalRecord = totalRecord;
    }

    public List<SubCategoryProductList> getData() {
        return data;
    }

    public void setData(List<SubCategoryProductList> data) {
        this.data = data;
    }
}

package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nebulacompanies.ibo.model.IBOList;

import java.util.List;

/**
 * Created by Palak Mehta on 26-Feb-18.
 */

public class SearchModelEcom {

    @SerializedName("StatusCode")
    @Expose
    private Integer StatusCode;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("Data")
    @Expose
    private List<SearchModelDetails> data = null;


    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<SearchModelDetails> getData() {
        return data;
    }

    public void setData(List<SearchModelDetails> data) {
        this.data = data;
    }
}

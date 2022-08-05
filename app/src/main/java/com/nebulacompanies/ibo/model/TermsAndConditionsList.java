package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Palak Mehta on 04-Nov-17.
 */

public class TermsAndConditionsList {
    @SerializedName("StatusCode")
    @Expose
    private Integer statuscode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<TermsAndConditionsDetails> data = null;

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TermsAndConditionsDetails> getData() {
        return data;
    }

    public void setData(List<TermsAndConditionsDetails> data) {
        this.data = data;
    }
}

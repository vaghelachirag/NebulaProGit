package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak Mehta on 04-Nov-17.
 */

public class TermsAndConditionsDetails {
    @SerializedName("TermsID")
    @Expose
    private Integer termsID;
    @SerializedName("Conditions")
    @Expose
    private String conditions;

    public Integer getTermsID() {
        return termsID;
    }

    public void setTermsID(Integer termsID) {
        this.termsID = termsID;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}

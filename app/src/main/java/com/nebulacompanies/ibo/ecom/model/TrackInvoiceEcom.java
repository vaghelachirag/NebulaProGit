package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Palak Mehta on 13-Jan-18.
 */

public class TrackInvoiceEcom {

    @SerializedName("facility")
    @Expose
    private String facility;

    @SerializedName("invoicecode")
    @Expose
    private String invoicecode;


    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getInvoicecode() {
        return invoicecode;
    }

    public void setInvoicecode(String invoicecode) {
        this.invoicecode = invoicecode;
    }

}

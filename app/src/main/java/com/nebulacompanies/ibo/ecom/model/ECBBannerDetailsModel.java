
package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ECBBannerDetailsModel {

    @SerializedName("Id")
    @Expose
    private Integer ebcID;

    @SerializedName("ImageFile")
    @Expose
    private String ebcImageFile;

    public Integer getEbcID() {
        return ebcID;
    }

    public void setEbcID(Integer ebcID) {
        this.ebcID = ebcID;
    }

    public String getEbcImageFile() {
        return ebcImageFile;
    }

    public void setEbcImageFile(String ebcImageFile) {
        this.ebcImageFile = ebcImageFile;
    }
}

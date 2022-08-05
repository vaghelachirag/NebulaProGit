package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityGrowthIncome
{
    @SerializedName("FromDate")
    @Expose
    private String FromDate;

    @SerializedName("ToDate")
    @Expose
    private String ToDate;

    @SerializedName("FromDateString")
    @Expose
    private String FromDateString;

    @SerializedName("ToDateString")
    @Expose
    private String ToDateString;

    @SerializedName("PowerLegPVstring")
    @Expose
    private String PVF1;

    @SerializedName("OtherLegPVstring")
    @Expose
    private String PVF2;

    @SerializedName("PowerLegCarryForward")
    @Expose
    private String PowerLegCarryForward;

    @SerializedName("Matchingstring")
    @Expose
    private String Matching;

    @SerializedName("TotalAmountstring")
    @Expose
    private String TotalAmount;

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getFromDateString() {
        return FromDateString;
    }

    public void setFromDateString(String fromDateString) {
        FromDateString = fromDateString;
    }

    public String getToDateString() {
        return ToDateString;
    }

    public void setToDateString(String toDateString) {
        ToDateString = toDateString;
    }

    public String getPVF1() {
        return PVF1;
    }

    public void setPVF1(String PVF1) {
        this.PVF1 = PVF1;
    }

    public String getPVF2() {
        return PVF2;
    }

    public void setPVF2(String PVF2) {
        this.PVF2 = PVF2;
    }

    public String getPowerLegCarryForward() {
        return PowerLegCarryForward;
    }

    public void setPowerLegCarryForward(String powerLegCarryForward) {
        PowerLegCarryForward = powerLegCarryForward;
    }

    public String getMatching() {
        return Matching;
    }

    public void setMatching(String matching) {
        Matching = matching;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}

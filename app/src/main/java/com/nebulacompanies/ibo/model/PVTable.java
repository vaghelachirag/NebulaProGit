package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PVTable
{

    @SerializedName("Families")
    @Expose
    private String Families;

    @SerializedName("Rank")
    @Expose
    private String Rank;
    @SerializedName("PVTodaystring")
    @Expose
    private String PVToday;
    @SerializedName("WeeklyPVstring")
    @Expose
    private String WeeklyPV ;
    @SerializedName("LifetimePVstring")
    @Expose
    private String LifetimePV;


    public String getFamilies() {
        return Families;
    }

    public void setFamilies(String families) {
        Families = families;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getPVToday() {
        return PVToday;
    }

    public void setPVToday(String PVToday) {
        this.PVToday = PVToday;
    }

    public String getWeeklyPV() {
        return WeeklyPV;
    }

    public void setWeeklyPV(String weeklyPV) {
        WeeklyPV = weeklyPV;
    }

    public String getLifetimePV() {
        return LifetimePV;
    }

    public void setLifetimePV(String lifetimePV) {
        LifetimePV = lifetimePV;
    }




}

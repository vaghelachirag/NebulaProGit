package com.nebulacompanies.ibo.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak Mehta on 19-Feb-18.
 */

public class IncomeHistoryList implements Comparable<IncomeHistoryList> {

    @SerializedName("IncomeDate")
    @Expose
    private long IncomeDate;
    @SerializedName("TotalAmount")
    @Expose
    private Integer TotalAmount;

    public IncomeHistoryList(long IncomeDate,  Integer TotalAmount){
        this.IncomeDate = IncomeDate;
        this.TotalAmount = TotalAmount;
    }

    public long getIncomeDate() {
        return IncomeDate;
    }

    public void setIncomeDate(long incomeDate) {
        IncomeDate = incomeDate;
    }

    public Integer getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        TotalAmount = totalAmount;
    }


    @Override
    public int compareTo(@NonNull IncomeHistoryList incomeHistoryList) {

        //return 0;
        //return 1;

        /*if(this.getTotalAmount() != incomeHistoryList.getTotalAmount())
            return this.getTotalAmount() - incomeHistoryList.getTotalAmount();*/
        if(this.getIncomeDate() != incomeHistoryList.getIncomeDate())
            return Long.compare(this.getIncomeDate(), incomeHistoryList.getIncomeDate());
        return 0;
    }
}
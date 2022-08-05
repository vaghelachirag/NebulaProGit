package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoCodeModel {
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("PromoMasterId")
        @Expose
        private Integer promoMasterId;
        @SerializedName("IBOMaxUsageCount")
        @Expose
        private Integer iBOMaxUsageCount;
        @SerializedName("CouponCode")
        @Expose
        private String couponCode;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("SortDescription")
        @Expose
        private String sortDescription;
        @SerializedName("LongDescription")
        @Expose
        private String longDescription;
        @SerializedName("ImageFile")
        @Expose
        private Object imageFile;
        @SerializedName("StartDate")
        @Expose
        private String startDate;
        @SerializedName("EndDate")
        @Expose
        private String endDate;
        @SerializedName("MaxUsageCount")
        @Expose
        private Integer maxUsageCount;
        @SerializedName("DiscountAmt")
        @Expose
        private Float discountAmt;
        @SerializedName("DiscountPercentage")
        @Expose
        private Float discountPercentage;
        @SerializedName("MaxDiscountAmount")
        @Expose
        private Float maxDiscountAmount;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("IsDelete")
        @Expose
        private Boolean isDelete;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("TotalUsedPromocode")
        @Expose
        private Integer totalUsedPromocode;

        public Integer getPromoMasterId() {
            return promoMasterId;
        }

        public void setPromoMasterId(Integer promoMasterId) {
            this.promoMasterId = promoMasterId;
        }

        public Integer getIBOMaxUsageCount() {
            return iBOMaxUsageCount;
        }

        public void setIBOMaxUsageCount(Integer iBOMaxUsageCount) {
            this.iBOMaxUsageCount = iBOMaxUsageCount;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSortDescription() {
            return sortDescription;
        }

        public void setSortDescription(String sortDescription) {
            this.sortDescription = sortDescription;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public Integer getMaxUsageCount() {
            return maxUsageCount;
        }

        public void setMaxUsageCount(Integer maxUsageCount) {
            this.maxUsageCount = maxUsageCount;
        }

        public Float getDiscountAmt() {
            return discountAmt;
        }

        public void setDiscountAmt(Float discountAmt) {
            this.discountAmt = discountAmt;
        }

        public Float getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(Float discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public Float getMaxDiscountAmount() {
            return maxDiscountAmount;
        }

        public void setMaxDiscountAmount(Float maxDiscountAmount) {
            this.maxDiscountAmount = maxDiscountAmount;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Boolean getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Boolean isDelete) {
            this.isDelete = isDelete;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Integer getTotalUsedPromocode() {
            return totalUsedPromocode;
        }

        public void setTotalUsedPromocode(Integer totalUsedPromocode) {
            this.totalUsedPromocode = totalUsedPromocode;
        }
    }
}

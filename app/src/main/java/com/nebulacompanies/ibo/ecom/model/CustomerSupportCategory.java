package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerSupportCategory
{
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<SupportList> data = null;

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

    public List<SupportList> getData() {
        return data;
    }

    public void setData(List<SupportList> data) {
        this.data = data;
    }

    public class SupportList
    {

        @SerializedName("Id")
        @Expose
        private Integer categoryId;

        @SerializedName("SettingKey")
        @Expose
        private String settingKey;

        @SerializedName("Name")
        @Expose
        private String categoryName;

        @SerializedName("Description")
        @Expose
        private String categoryDescription;

        @SerializedName("DisplayOrder")
        @Expose
        private String displayOrder;


        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getSettingKey() {
            return settingKey;
        }

        public void setSettingKey(String settingKey) {
            this.settingKey = settingKey;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryDescription() {
            return categoryDescription;
        }

        public void setCategoryDescription(String categoryDescription) {
            this.categoryDescription = categoryDescription;
        }

        public String getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(String displayOrder) {
            this.displayOrder = displayOrder;
        }
    }
}



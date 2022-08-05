package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtherProducts {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;
    public List<Integer> getSelIDs() {
        return selIDs;
    }

    public void setSelIDs(List<Integer> selIDs) {
        this.selIDs = selIDs;
    }

    public List<Integer> selIDs ;
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

        @SerializedName("MainKey")
        @Expose
        private String mainKey;
        @SerializedName("EcomAttributeValueList")
        @Expose
        private List<EcomAttributeValue> ecomAttributeValueList = null;
        private String mainValue="-";
        public String getMainKey() {
            return mainKey;
        }

        public void setMainKey(String mainKey) {
            this.mainKey = mainKey;
        }

        public List<EcomAttributeValue> getEcomAttributeValueList() {
            return ecomAttributeValueList;
        }

        public void setEcomAttributeValueList(List<EcomAttributeValue> ecomAttributeValueList) {
            this.ecomAttributeValueList = ecomAttributeValueList;
        }


        public String getMainValue() {
            return mainValue;
        }

        public void setMainValue(String mainValue) {
            this.mainValue = mainValue;
        }
    }
    public class EcomAttributeValue {

        @SerializedName("AttributeName")
        @Expose
        private String attributeName;

        public String getAttributeColor() {
            return attributeColor;
        }

        public void setAttributeColor(String attributeColor) {
            this.attributeColor = attributeColor;
        }

        @SerializedName("AttributeColor")
        @Expose
        private String attributeColor;
        @SerializedName("EcomAttributeSKUList")
        @Expose
        private List<Integer> ecomAttributeSKUList = null;


        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public List<Integer> getEcomAttributeSKUList() {
            return ecomAttributeSKUList;
        }

        public void setEcomAttributeSKUList(List<Integer> ecomAttributeSKUList) {
            this.ecomAttributeSKUList = ecomAttributeSKUList;
        }

    }
}

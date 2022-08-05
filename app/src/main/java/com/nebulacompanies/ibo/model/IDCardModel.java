package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IDCardModel {
    @SerializedName("StatusCode")
    @Expose
    private int statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Data data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("Photo")
        @Expose
        private String photo;
        @SerializedName("IBOID")
        @Expose
        private String iboid;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("DOJString")
        @Expose
        private String dOJString;
        @SerializedName("DOJ")
        @Expose
        private String doj;
        @SerializedName("DOBString")
        @Expose
        private String dOBString;
        @SerializedName("DOB")
        @Expose
        private Object dob;
        @SerializedName("AddressWithCityState")
        @Expose
        private String addressWithCityState;
        @SerializedName("Signature")
        @Expose
        private String signature;
        @SerializedName("KYCverified")
        @Expose
        private boolean kYCverified;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getIboid() {
            return iboid;
        }

        public void setIboid(String iboid) {
            this.iboid = iboid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDOJString() {
            return dOJString;
        }

        public void setDOJString(String dOJString) {
            this.dOJString = dOJString;
        }

        public String getDoj() {
            return doj;
        }

        public void setDoj(String doj) {
            this.doj = doj;
        }

        public String getDOBString() {
            return dOBString;
        }

        public void setDOBString(String dOBString) {
            this.dOBString = dOBString;
        }

        public Object getDob() {
            return dob;
        }

        public void setDob(Object dob) {
            this.dob = dob;
        }

        public String getAddressWithCityState() {
            return addressWithCityState;
        }

        public void setAddressWithCityState(String addressWithCityState) {
            this.addressWithCityState = addressWithCityState;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public boolean isKYCverified() {
            return kYCverified;
        }

        public void setKYCverified(boolean kYCverified) {
            this.kYCverified = kYCverified;
        }

    }

}

package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DwarkaUpiModel {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class UPIList {

        @SerializedName("PackageType")
        @Expose
        private String packageType;
        @SerializedName("PackageName")
        @Expose
        private String packageName;
        @SerializedName("ImageFile")
        @Expose
        private String imageFile;

        public String getPackageType() {
            return packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }

    }

    public class Data {

        @SerializedName("UPIList")
        @Expose
        private List<UPIList> uPIList = null;
        @SerializedName("UPIGateWay")
        @Expose
        private String uPIGateWay;

        public List<UPIList> getUPIList() {
            return uPIList;
        }

        public void setUPIList(List<UPIList> uPIList) {
            this.uPIList = uPIList;
        }

        public String getUPIGateWay() {
            return uPIGateWay;
        }

        public void setUPIGateWay(String uPIGateWay) {
            this.uPIGateWay = uPIGateWay;
        }

    }
}

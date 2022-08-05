package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardData {

    @SerializedName("Key")
    @Expose
    private String shippingKey;


    @SerializedName("Amount")
    @Expose
    private String shippingAmount;

    @SerializedName("Currency")
    @Expose
    private String shippingCurrency;

    @SerializedName("Name")
    @Expose
    private String shippingName;

    @SerializedName("Description")
    @Expose
    private String shippingDescription;

    @SerializedName("Image")
    @Expose
    private String shippingImage;

    @SerializedName("OrderId")
    @Expose
    private String shippingOrderId;

    @SerializedName("Email")
    @Expose
    private String shippingEmail;

    @SerializedName("MobileNo")
    @Expose
    private String shippingMobileNo;

    @SerializedName("Address")
    @Expose
    private String shippingAddress;

    @SerializedName("Theme")
    @Expose
    private String shippingTheme;

    @SerializedName("ReceiptId")
    @Expose
    private String receiptId;



    @SerializedName("UPIGateWay")
    @Expose
    private String razorpay;


    @SerializedName("Version")
    @Expose
    private String version;

    @SerializedName("UPIModelList")
    @Expose
    private List<UPIModelList> uPIModelList = null;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @SerializedName("signature")
    @Expose
    private String signature;
    public String getTxnToken() {
        return txnToken;
    }

    public String getPaytmGatewayURL() {
        return paytmGatewayURL;
    }

    public void setPaytmGatewayURL(String paytmGatewayURL) {
        this.paytmGatewayURL = paytmGatewayURL;
    }

    @SerializedName("PaytmGatewayURL")
    @Expose
    private String paytmGatewayURL;
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @SerializedName("MID")
    @Expose
    private String mid;
    public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
    }

    @SerializedName("txnToken")
    @Expose
    private String txnToken;

    @SerializedName("txnid")
    @Expose
    private String txnid;

    @SerializedName("hash")
    @Expose
    private String hash;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("productinfo")
    @Expose
    private String productinfo;


    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    @SerializedName("firstname")
    @Expose
    private String firstname;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getShippingKey() {
        return shippingKey;
    }

    public void setShippingKey(String shippingKey) {
        this.shippingKey = shippingKey;
    }

    public String getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getShippingCurrency() {
        return shippingCurrency;
    }

    public void setShippingCurrency(String shippingCurrency) {
        this.shippingCurrency = shippingCurrency;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingDescription() {
        return shippingDescription;
    }

    public void setShippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    public String getShippingImage() {
        return shippingImage;
    }

    public void setShippingImage(String shippingImage) {
        this.shippingImage = shippingImage;
    }

    public String getShippingOrderId() {
        return shippingOrderId;
    }

    public void setShippingOrderId(String shippingOrderId) {
        this.shippingOrderId = shippingOrderId;
    }

    public String getShippingEmail() {
        return shippingEmail;
    }

    public void setShippingEmail(String shippingEmail) {
        this.shippingEmail = shippingEmail;
    }

    public String getShippingMobileNo() {
        return shippingMobileNo;
    }

    public void setShippingMobileNo(String shippingMobileNo) {
        this.shippingMobileNo = shippingMobileNo;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingTheme() {
        return shippingTheme;
    }

    public void setShippingTheme(String shippingTheme) {
        this.shippingTheme = shippingTheme;
    }


    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getRazorpay() {
        return razorpay;
    }

    public void setRazorpay(String razorpay) {
        this.razorpay = razorpay;
    }
    public List<UPIModelList> getUPIModelList() {
        return uPIModelList;
    }

    public void setUPIModelList(List<UPIModelList> uPIModelList) {
        this.uPIModelList = uPIModelList;
    }
    public class UPIModelList {

        @SerializedName("PackageType")
        @Expose
        private String packageType;
        @SerializedName("PackageName")
        @Expose
        private String packageName;

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }

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

    }
}

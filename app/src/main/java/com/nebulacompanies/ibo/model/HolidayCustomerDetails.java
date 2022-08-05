package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nebula_18 on 3/6/2018.
 */

public class HolidayCustomerDetails {

    @SerializedName("IBOKeyID")
    @Expose
    private String iBOKeyID;
    @SerializedName("IBOID")
    @Expose
    private String iBOID;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("InvestmentAmount")
    @Expose
    private Integer investmentAmount;
    @SerializedName("InstallmentAmount")
    @Expose
    private Integer installmentAmount;
    @SerializedName("DOB")
    @Expose
    private Integer dOB;
    @SerializedName("Createdate")
    @Expose
    private Integer createdate;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("ProductSaleID")
    @Expose
    private Integer productSaleID;
    @SerializedName("ArrivalDate")
    @Expose
    private Integer arrivalDate;
    @SerializedName("PassportNo")
    @Expose
    private Object passportNo;
    @SerializedName("PassportExpiryDate")
    @Expose
    private Integer passportExpiryDate;
    @SerializedName("EmergencyContactName")
    @Expose
    private Object emergencyContactName;
    @SerializedName("EmergencyContact")
    @Expose
    private Object emergencyContact;
    @SerializedName("RoomSharing")
    @Expose
    private Object roomSharing;
    @SerializedName("DoubleBed")
    @Expose
    private Object doubleBed;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("ClearDate")
    @Expose
    private Integer clearDate;
    @SerializedName("MessageStatus")
    @Expose
    private Object messageStatus;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("IsDelete")
    @Expose
    private Boolean isDelete;

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getIBOKeyID() {
        return iBOKeyID;
    }

    public void setIBOKeyID(String iBOKeyID) {
        this.iBOKeyID = iBOKeyID;
    }

    public String getIBOID() {
        return iBOID;
    }

    public void setIBOID(String iBOID) {
        this.iBOID = iBOID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(Integer investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public Integer getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Integer installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Integer getDOB() {
        return dOB;
    }

    public void setDOB(Integer dOB) {
        this.dOB = dOB;
    }

    public Integer getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getProductSaleID() {
        return productSaleID;
    }

    public void setProductSaleID(Integer productSaleID) {
        this.productSaleID = productSaleID;
    }

    public Integer getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Integer arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Object getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(Object passportNo) {
        this.passportNo = passportNo;
    }

    public Integer getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(Integer passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public Object getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(Object emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public Object getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(Object emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Object getRoomSharing() {
        return roomSharing;
    }

    public void setRoomSharing(Object roomSharing) {
        this.roomSharing = roomSharing;
    }

    public Object getDoubleBed() {
        return doubleBed;
    }

    public void setDoubleBed(Object doubleBed) {
        this.doubleBed = doubleBed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getClearDate() {
        return clearDate;
    }

    public void setClearDate(Integer clearDate) {
        this.clearDate = clearDate;
    }

    public Object getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Object messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Pincode")
    @Expose
    private String pincode;
}

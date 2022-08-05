
package com.nebulacompanies.ibo.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

public class IBOGeoLocationInfo implements ClusterItem {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("IBOKeyId")
    @Expose
    private String iBOKeyId;
    @SerializedName("IBOId")
    @Expose
    private String iBOId;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("RankId")
    @Expose
    private Integer rankId;
    @SerializedName("ProfilePic")
    @Expose
    private String profilePic;
    @SerializedName("RankIcon")
    @Expose
    private String rankIcon;
    @SerializedName("IBOUser")
    @Expose
    private String iBOUser;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Country")
    @Expose
    private Object country;
    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIBOKeyId() {
        return iBOKeyId;
    }

    public void setIBOKeyId(String iBOKeyId) {
        this.iBOKeyId = iBOKeyId;
    }

    public String getIBOId() {
        return iBOId;
    }

    public void setIBOId(String iBOId) {
        this.iBOId = iBOId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getRankIcon() {
        return rankIcon;
    }

    public void setRankIcon(String rankIcon) {
        this.rankIcon = rankIcon;
    }

    public String getIBOUser() {
        return iBOUser;
    }

    public void setIBOUser(String iBOUser) {
        this.iBOUser = iBOUser;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
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

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLatitude(), getLongitude());
    }

    @Override
    public String getTitle() {
        return getIBOUser();
    }

    @Override
    public String getSnippet() {
        return getAddressLine1() + " ," + getCity();
    }

}

package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Palak Mehta on 13-Jan-18.
 */

public class TrackListModelEcom {

   /* @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<TrackListModel> data = null;

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
    public List<TrackListModel> getData() {return data;}
    public void setData(List<TrackListModel> data) {
        this.data=data;
    }
*/


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

    public class Data {

        @SerializedName("tracking_data")
        @Expose
        private TrackingData trackingData;

        public TrackingData getTrackingData() {
            return trackingData;
        }

        public void setTrackingData(TrackingData trackingData) {
            this.trackingData = trackingData;
        }

    }

    public class TrackingData {

        @SerializedName("track_url")
        @Expose
        private String trackUrl;

        @SerializedName("shipping_provider")
        @Expose
        private String shippingProvider;

        @SerializedName("awb_no")
        @Expose
        private String awbNo;
        public String getTrackUrl() {
            return trackUrl;
        }

        public void setTrackUrl(String trackUrl) {
            this.trackUrl = trackUrl;
        }

        public String getShippingProvider() {
            return shippingProvider;
        }

        public void setShippingProvider(String shippingProvider) {
            this.shippingProvider = shippingProvider;
        }

        public String getAwbNo() {
            return awbNo;
        }

        public void setAwbNo(String awbNo) {
            this.awbNo = awbNo;
        }
    }
}

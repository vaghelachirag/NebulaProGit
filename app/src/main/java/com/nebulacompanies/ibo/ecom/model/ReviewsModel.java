package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsModel {

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

        @SerializedName("ProductId")
        @Expose
        private Integer productId;
        @SerializedName("AverageRating")
        @Expose
        private Double averageRating;
        @SerializedName("TotalReviews")
        @Expose
        private Integer totalReviews;
        @SerializedName("UserAbleToReview")
        @Expose
        private Boolean userAbleToReview;
        @SerializedName("ReviewsList")
        @Expose
        private List<Reviews> reviewsList = null;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }

        public Integer getTotalReviews() {
            return totalReviews;
        }

        public void setTotalReviews(Integer totalReviews) {
            this.totalReviews = totalReviews;
        }

        public Boolean getUserAbleToReview() {
            return userAbleToReview;
        }

        public void setUserAbleToReview(Boolean userAbleToReview) {
            this.userAbleToReview = userAbleToReview;
        }

        public List<Reviews> getReviewsList() {
            return reviewsList;
        }

        public void setReviewsList(List<Reviews> reviewsList) {
            this.reviewsList = reviewsList;
        }

    }
    public class Reviews {

        @SerializedName("ProductId")
        @Expose
        private Integer productId;
        @SerializedName("UserId")
        @Expose
        private String userId;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("Rating")
        @Expose
        private Double rating;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("RatingDate")
        @Expose
        private String ratingDate;
        @SerializedName("RatingDateString")
        @Expose
        private String ratingDateString;

        @SerializedName("RatingDatelong")
        @Expose
        private long ratingDateLong;


        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRatingDate() {
            return ratingDate;
        }

        public void setRatingDate(String ratingDate) {
            this.ratingDate = ratingDate;
        }

        public String getRatingDateString() {
            return ratingDateString;
        }

        public void setRatingDateString(String ratingDateString) {
            this.ratingDateString = ratingDateString;
        }

        public long getRatingDateLong() {
            return ratingDateLong;
        }

        public void setRatingDateLong(long ratingDateLong) {
            this.ratingDateLong = ratingDateLong;
        }
    }

}

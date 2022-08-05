package com.nebulacompanies.ibo.ecom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerSupportDetailTicket {

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

        @SerializedName("TicketMasterId")
        @Expose
        private Integer ticketMasterId;
        @SerializedName("IBOKeyId")
        @Expose
        private String iBOKeyId;
        @SerializedName("IBOID")
        @Expose
        private Object iBOID;
        @SerializedName("TicketCategoryId")
        @Expose
        private Integer ticketCategoryId;
        @SerializedName("TicketCategoryName")
        @Expose
        private String ticketCategoryName;
        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("CreatedOnString")
        @Expose
        private String createdOnString;
        @SerializedName("CreatedByName")
        @Expose
        private String createdByName;
        @SerializedName("ClosedOn")
        @Expose
        private String closedOn;
        @SerializedName("ClosedByName")
        @Expose
        private String closedByName;
        @SerializedName("Feedback")
        @Expose
        private Object feedback;
        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("LastResponseDate")
        @Expose
        private String lastResponseDate;
        @SerializedName("CreatedOnINLong")
        @Expose
        private Integer createdOnINLong;
        @SerializedName("ClosedOnINLong")
        @Expose
        private Integer closedOnINLong;
        @SerializedName("OrderNumber")
        @Expose
        private String orderNumber;
        @SerializedName("OrderDate")
        @Expose
        private String orderDate;
        @SerializedName("OrderDateINLong")
        @Expose
        private Integer orderDateINLong;
        @SerializedName("Detail")
        @Expose
        private List<Detail> detail = null;

        public Integer getTicketMasterId() {
            return ticketMasterId;
        }

        public void setTicketMasterId(Integer ticketMasterId) {
            this.ticketMasterId = ticketMasterId;
        }

        public String getIBOKeyId() {
            return iBOKeyId;
        }

        public void setIBOKeyId(String iBOKeyId) {
            this.iBOKeyId = iBOKeyId;
        }

        public Object getIBOID() {
            return iBOID;
        }

        public void setIBOID(Object iBOID) {
            this.iBOID = iBOID;
        }

        public Integer getTicketCategoryId() {
            return ticketCategoryId;
        }

        public void setTicketCategoryId(Integer ticketCategoryId) {
            this.ticketCategoryId = ticketCategoryId;
        }

        public String getTicketCategoryName() {
            return ticketCategoryName;
        }

        public void setTicketCategoryName(String ticketCategoryName) {
            this.ticketCategoryName = ticketCategoryName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getCreatedOnString() {
            return createdOnString;
        }

        public void setCreatedOnString(String createdOnString) {
            this.createdOnString = createdOnString;
        }

        public String getCreatedByName() {
            return createdByName;
        }

        public void setCreatedByName(String createdByName) {
            this.createdByName = createdByName;
        }

        public String getClosedOn() {
            return closedOn;
        }

        public void setClosedOn(String closedOn) {
            this.closedOn = closedOn;
        }

        public String getClosedByName() {
            return closedByName;
        }

        public void setClosedByName(String closedByName) {
            this.closedByName = closedByName;
        }

        public Object getFeedback() {
            return feedback;
        }

        public void setFeedback(Object feedback) {
            this.feedback = feedback;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getLastResponseDate() {
            return lastResponseDate;
        }

        public void setLastResponseDate(String lastResponseDate) {
            this.lastResponseDate = lastResponseDate;
        }

        public Integer getCreatedOnINLong() {
            return createdOnINLong;
        }

        public void setCreatedOnINLong(Integer createdOnINLong) {
            this.createdOnINLong = createdOnINLong;
        }

        public Integer getClosedOnINLong() {
            return closedOnINLong;
        }

        public void setClosedOnINLong(Integer closedOnINLong) {
            this.closedOnINLong = closedOnINLong;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public Integer getOrderDateINLong() {
            return orderDateINLong;
        }

        public void setOrderDateINLong(Integer orderDateINLong) {
            this.orderDateINLong = orderDateINLong;
        }

        public List<Detail> getDetail() {
            return detail;
        }

        public void setDetail(List<Detail> detail) {
            this.detail = detail;
        }

    }

    public class Detail {

        @SerializedName("MasterId")
        @Expose
        private Integer masterId;
        @SerializedName("DetailsId")
        @Expose
        private Integer detailsId;
        @SerializedName("AttachmentFile")
        @Expose
        private String attachmentFile;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("CreatedByName")
        @Expose
        private String createdByName;
        @SerializedName("CreatedOnLong")
        @Expose
        private Integer createdOnLong;
        @SerializedName("IsSentByUs")
        @Expose
        private Boolean isSentByUs;

        public Integer getMasterId() {
            return masterId;
        }

        public void setMasterId(Integer masterId) {
            this.masterId = masterId;
        }

        public Integer getDetailsId() {
            return detailsId;
        }

        public void setDetailsId(Integer detailsId) {
            this.detailsId = detailsId;
        }

        public String getAttachmentFile() {
            return attachmentFile;
        }

        public void setAttachmentFile(String attachmentFile) {
            this.attachmentFile = attachmentFile;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreatedByName() {
            return createdByName;
        }

        public void setCreatedByName(String createdByName) {
            this.createdByName = createdByName;
        }

        public Integer getCreatedOnLong() {
            return createdOnLong;
        }

        public void setCreatedOnLong(Integer createdOnLong) {
            this.createdOnLong = createdOnLong;
        }

        public Boolean getIsSentByUs() {
            return isSentByUs;
        }

        public void setIsSentByUs(Boolean isSentByUs) {
            this.isSentByUs = isSentByUs;
        }

    }
}

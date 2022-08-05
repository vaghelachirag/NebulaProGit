package com.nebulacompanies.ibo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Palak Mehta on 07-Nov-17.
 */

public class FAQsDetails {
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("Faqs")
    @Expose
    private List<FAQsQA> faqs;

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<FAQsQA> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<FAQsQA> faqs) {
        this.faqs = faqs;
    }

    /*class FAQsQA{
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("Answer")
        @Expose
        private String answer;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

    }*/
}

package com.example.frontend.model;

public class UserSubcategory {

    private Integer userSubId;
    private Integer userId;
    private String subName;
    private Integer subcategoryId;
    private Integer status;


    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getUserSubId() {
        return userSubId;
    }

    public void setUserSubId(Integer userSubId) {
        this.userSubId = userSubId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserSubcategory{" +
                "userSubId=" + userSubId +
                ", userId=" + userId +
                ", subcategoryId=" + subcategoryId +
                ", status=" + status +
                '}';
    }
}

package com.example.frontend.model;

public class CategoryRequest {
    private Integer categoryId;
    private String nameCategory;
    private String photoCategory;

    public CategoryRequest() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getPhotoCategory() {
        return photoCategory;
    }

    public void setPhotoCategory(String photoCategory) {
        this.photoCategory = photoCategory;
    }

    @Override
    public String toString() {
        return "CategoryRequest{" +
                "categoryId=" + categoryId +
                ", nameCategory='" + nameCategory + '\'' +
                ", photoCategory='" + photoCategory + '\'' +
                '}';
    }
}

package com.example.frontend.model;

public class CategoryRequest {
    private Integer categoryId;
    private String nameCategory;
    private Integer photoId;
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photo_id) {
        this.photoId = photo_id;
    }

    @Override
    public String toString() {
        return "CategoryRequest{" +
                "categoryId=" + categoryId +
                ", nameCategory='" + nameCategory + '\'' +
                ", photo_id=" + photoId +
                ", path='" + path + '\'' +
                '}';
    }
}

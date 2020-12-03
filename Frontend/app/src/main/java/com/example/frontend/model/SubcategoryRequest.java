package com.example.frontend.model;

public class SubcategoryRequest {
    private String nameSubcategory;
    private String nameCategory;

    public SubcategoryRequest() {
    }

    public String getNameSubcategory() {
        return nameSubcategory;
    }

    public void setNameSubcategory(String nameSubcategory) {
        this.nameSubcategory = nameSubcategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "SubcategoryRequest{" +
                "nameSubcategory='" + nameSubcategory + '\'' +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }
}

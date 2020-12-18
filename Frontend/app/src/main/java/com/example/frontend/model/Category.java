package com.example.frontend.model;


import java.util.Date;

public class Category {
    private Integer categoryId;
    private String name;
    private Integer photoId;
    private String path;
    private Integer status;



    // Setters Y Getters generados para los demas atributos de la clase Category
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {return status;}
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPhotoId() {return photoId;}
    public void setPhotoId(Integer photoID) {this.photoId = photoID;}

    // Funcion toString generado para imprimir el objeto en una cadena String


    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", photoId=" + photoId +
                ", path='" + path + '\'' +
                ", status=" + status +
                '}';
    }
}

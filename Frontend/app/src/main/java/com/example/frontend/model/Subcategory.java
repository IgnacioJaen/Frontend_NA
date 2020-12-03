package com.example.frontend.model;

import java.util.Date;

public class Subcategory {
    private Integer subcategoryId;
    private Integer categoryId;
    private String name;
    private Integer status;



    // Setters Y Getters generados para los demas atributos de la clase Subcategory
    public Integer getSubcategoryId() {
        return subcategoryId;
    }
    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }


    // Funcion toString generado para imprimir el objeto en una cadena String
    @Override
    public String toString() {
        return "Subcategory{" +
                "subcategoryId=" + subcategoryId +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", status=" + status + '\'' +
                '}';
    }
}

package com.example.frontend.model;

public class ReportOpRequest {
    private Integer reportOpId;
    private String description;

    // Constructor vacio de la clase ReportOpRequest
    public ReportOpRequest() {
    }

    // Setters Y Getters generados para los demas atributos de la clase ReportOpRequest
    public Integer getReportOpId() {
        return reportOpId;
    }
    public void setReportOpId(Integer reportOpId) {
        this.reportOpId = reportOpId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Funcion toString generado para imprimir el objeto en una cadena String
    @Override
    public String toString() {
        return "ReportOptions{" +
                ", reportOpId=" + reportOpId +
                ", description='" + description + '\'' +
                '}';
    }
}

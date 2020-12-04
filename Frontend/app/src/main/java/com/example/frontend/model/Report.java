package com.example.frontend.model;

import java.util.Date;

public class Report {
    private Integer reportId;
    private Integer reportOpId;
    private Integer chatId;
    private String date;
    private Integer reportedUserId;
    private Integer userId;
    private Integer status;


    // Constructor de la clase Report, instanciando el objeto Transaction
    public Report() {};

    //SETERS DE LOS ATRIBUTOS DEL OBJETO TRANSACTION **************

    // Setters Y Getters generados para los demas atributos de la clase Report
    public Integer getReportId() {
        return reportId;
    }
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getChatId() {
        return chatId;
    }
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReportOpId() {
        return reportOpId;
    }
    public void setReportOpId(Integer reportOpId) {
        this.reportOpId = reportOpId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Integer reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Funcion toString generado para imprimir el objeto en una cadena String

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reportOpId=" + reportOpId +
                ", chatId=" + chatId +
                ", date='" + date + '\'' +
                ", reportedUserId=" + reportedUserId +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}

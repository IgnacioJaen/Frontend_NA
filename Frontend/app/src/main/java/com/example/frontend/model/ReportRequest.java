package com.example.frontend.model;

import java.util.Date;

public class ReportRequest {
    private Integer reportId;
    private String reportOp;
    private String date;
    private String reportedUser;
    private String user;
    private Integer status;

    // Setters Y Getters generados para los demas atributos de la clase Report
    public Integer getReportId() {
        return reportId;
    }
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportOp() {
        return reportOp;
    }

    public void setReportOp(String reportOp) {
        this.reportOp = reportOp;
    }

    public String getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(String reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    // Funcion toString generado para imprimir el objeto en una cadena String

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reportOpId=" + reportOp +
                ", date='" + date + '\'' +
                ", reportedUserId=" + reportedUser +
                ", userId=" + user +
                ", status=" + status +
                '}';
    }
}

package com.example.frontend.api;

import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.Report;
import com.example.frontend.model.ReportRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ReportApi {

    @POST("post")
    Call<Report> generateReport(@Body Report report);

    @Headers({"Accept: application/json"})
    @GET("reports")
    Call<ArrayList<ReportRequest>> reports();

    @GET("reportid/")
    Call<ReportRequest> getReportById(@Query("reportId") int reportId);
}

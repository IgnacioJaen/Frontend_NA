package com.example.frontend.api;

import com.example.frontend.model.Report;
import com.example.frontend.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportApi {
    @POST("post")
    Call<Report> generateReport(@Body Report report);
}

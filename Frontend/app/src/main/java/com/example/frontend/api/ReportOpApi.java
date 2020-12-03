package com.example.frontend.api;

import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.ReportOpRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.ArrayList;

public interface ReportOpApi {
    @Headers({"Accept: application/json"})
    @GET("reports")
    Call<ArrayList<ReportOpRequest>> getReportOptions();
}

package com.example.frontend.api;

import com.example.frontend.model.CategoryRequest;
import com.example.frontend.model.Chat;
import com.example.frontend.model.ChatRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.ArrayList;

public interface CategoryApi {
    @Headers({"Accept: application/json"})
    @GET("categories")
    Call<ArrayList<CategoryRequest>> getCategories();

}
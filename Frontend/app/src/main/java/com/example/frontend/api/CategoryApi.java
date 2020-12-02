package com.example.frontend.api;

import com.example.frontend.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface CategoryApi {
    @Headers({"Accept: application/json"})
    @GET("categories")
    Call<ArrayList<CategoryRequest>> getCategories();

    @POST("category")
    Call<Category> createCategory(@Body Category category);

}
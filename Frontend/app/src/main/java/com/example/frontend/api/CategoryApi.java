package com.example.frontend.api;

import com.example.frontend.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface CategoryApi {
    @Headers({"Accept: application/json"})
    @GET("allCategories")
    Call<ArrayList<CategoryRequest>> getCategories();

    @POST("category")
    Call<Category> createCategory(@Body Category category);

    @GET("category/categories/")
    Call<Category> getCategory(@Query("categoryId") int categoryId);

    @PUT("category")
    Call<Category> editCategory(@Body Category category);

    @PUT("category/delete")
    Call<Category> deleteCategory(@Body Category category);




}
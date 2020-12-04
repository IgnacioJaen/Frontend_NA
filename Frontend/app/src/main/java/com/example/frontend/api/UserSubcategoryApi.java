package com.example.frontend.api;

import com.example.frontend.model.Subcategory;
import com.example.frontend.model.UserSubcategory;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface UserSubcategoryApi {
    @Headers({"Accept: application/json"})

    @GET("userSubcategory/userSubcategories/")
    Call<UserSubcategory> getUserSubcategory(@Query("userId") int userId);

    @POST("userSubcategory")
    Call<UserSubcategory> insertUserSubcategory(@Body UserSubcategory userSubcategory);

}
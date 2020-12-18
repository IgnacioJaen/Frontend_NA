package com.example.frontend.api;

import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.Subcategory;
import com.example.frontend.model.User;
import com.example.frontend.model.UserSubcategory;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface UserSubcategoryApi {
    @Headers({"Accept: application/json"})

    @GET("userSubcategory/userSubMatch/")
    Call<ArrayList<UserSubcategory>> getUserMatch(@Query("userId") int userId);

    @GET("userSubcategory/userSubMatchPremium/")
    Call<ArrayList<UserSubcategory>> getUserMatchPremium(@Query("userId") int userId);

    @GET("userSubcategory/userSubcategories/")
    Call<ArrayList<UserSubcategory>> getUserSubcategory(@Query("userId") int userId);

    @POST("userSubcategory")
    Call<UserSubcategory> insertUserSubcategory(@Body UserSubcategory userSubcategory);

}
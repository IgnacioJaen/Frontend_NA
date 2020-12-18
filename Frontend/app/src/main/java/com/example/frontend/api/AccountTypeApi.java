package com.example.frontend.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AccountTypeApi {

    @Headers("Content-Type: text/html")
    @GET("userTypeById")
    Call<String> getUserTypeById(@Query("userId") Integer userId);
}

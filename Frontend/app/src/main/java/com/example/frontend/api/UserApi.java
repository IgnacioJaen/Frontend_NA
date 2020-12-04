package com.example.frontend.api;

import com.example.frontend.model.Category;
import com.example.frontend.model.User;
import com.example.frontend.model.UserRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface UserApi {

    //@Headers({"Accept: application/json"})

    @GET("userRequest/login")
    Call<Integer> getId(@Query("email") String email, @Query("password") String password);

    @GET("receiverid")
    Call<Integer> getReceiverId(@Query("userId") Integer userId, @Query("chatId") Integer chatId);

    @POST("post")
    Call<User> createUser(@Body User user);

    @GET("userRequest")
    Call<User> getUser(@Query("userId") Integer userId);

    @Headers("Content-Type: text/html")
    @GET("userType")
    Call<String> getUserType(@Query("email") String email, @Query("password") String password);

    @PUT("update")
    Call<User> updateUser(@Body User user);


}

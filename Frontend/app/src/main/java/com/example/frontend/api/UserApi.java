package com.example.frontend.api;

import com.example.frontend.model.Category;
import com.example.frontend.model.User;
import com.example.frontend.model.UserRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface UserApi {

    @GET("userRequest/login")
    Call<Integer> getId(@Query("email") String email, @Query("password") String password);

    @POST("post")
    Call<User> createUser(@Body User user);

    @GET("userRequest")
    Call<User> getUser(@Query("userId") Integer userId);

    @PUT("update")
    Call<User> updateUser(@Body User user);


}

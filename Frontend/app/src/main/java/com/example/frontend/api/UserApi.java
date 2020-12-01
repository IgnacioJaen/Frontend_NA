package com.example.frontend.api;

import com.example.frontend.model.User;
import com.example.frontend.model.UserRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface UserApi {

    @GET("userRequest/login")
    Call<Integer> getId(@Query("email") String email, @Query("password") String password);

    /*
    @FormUrlEncoded
    @POST()
    Call<User> createUser(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("userType") Integer userType,
            @Field("userType") Integer userType,
            @Field("userType") Integer userType,
            @Field("userType") Integer userType,

            );*/

}

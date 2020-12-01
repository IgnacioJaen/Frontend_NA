package com.example.frontend.api;

import com.example.frontend.model.User;
import com.example.frontend.model.UserRequest;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.ArrayList;

public interface UserApi {

    @GET("/userRequest/login?email=jps@gmail.com&password=vdsvds44")
    Call<Integer> getId();

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

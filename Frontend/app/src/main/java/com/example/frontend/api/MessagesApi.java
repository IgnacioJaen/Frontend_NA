package com.example.frontend.api;

import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.Messages;
import com.example.frontend.model.MessagesRequest;
import com.example.frontend.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface MessagesApi {
    @Headers({"Accept: application/json"})
    @GET("messagesList/")
    Call<ArrayList<MessagesRequest>> getMessages(@Query("userId") int userId, @Query("chatId") int chatId, @Query("recUserId") int recUserId);

    @POST("post")
    Call<Messages> sendMessage(@Body Messages messages);

}

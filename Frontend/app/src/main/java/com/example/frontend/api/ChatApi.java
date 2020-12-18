package com.example.frontend.api;

import com.example.frontend.model.Chat;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.Subcategory;
import com.example.frontend.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ChatApi {
    @Headers({"Accept: application/json"})
    @GET("chats/")
    Call<ArrayList<ChatRequest>> getChats(@Query("userId") int userId);

    @GET("{chatId}")
    Call<ArrayList<Chat>> getChatById(@Path("chatId") Integer chatId);

    @GET("lastChatId")
    Call<Integer> lastChatId();

    @DELETE("delete/")
    Call<Integer> deleteChat(@Query("chatId") int chatId);

    @POST("post")
    Call<Chat> createChat(@Body Chat chat);
}
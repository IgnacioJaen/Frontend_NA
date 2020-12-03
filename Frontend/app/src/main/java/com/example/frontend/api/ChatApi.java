package com.example.frontend.api;

import com.example.frontend.model.Chat;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.Subcategory;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ChatApi {
    @Headers({"Accept: application/json"})
    @GET("chats/")
    Call<ArrayList<ChatRequest>> getChats(@Query("userId") int userId);

    @GET("{chatId}")
    Call<ArrayList<Chat>> getChatById(@Path("chatId") Integer chatId);

    @DELETE("delete/")
    Call<Integer> deleteChat(@Query("chatId") int chatId);

}
package com.example.frontend.api;

import com.example.frontend.model.Chat;
import com.example.frontend.model.ChatRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ChatApi {
    @Headers({"Accept: application/json"})
    @GET("chats?userId=1")
    Call<ArrayList<ChatRequest>> getChats();

    @GET("{chatId}")
    Call<ArrayList<Chat>> getChatById(@Path("chatId") Integer chatId);

}
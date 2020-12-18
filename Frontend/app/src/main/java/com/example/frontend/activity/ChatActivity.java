package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.api.ChatApi;
import com.example.frontend.model.ChatRequest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
        private RecyclerView chatView;
        private ChatAdapter chatAdapter;
        private RecyclerView.LayoutManager chatLayout;
        private ArrayList<ChatRequest> chats;
        int userId;
        private Button btnBack;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            userId = getIntent().getIntExtra("userId", 0);

            btnBack = findViewById(R.id.btnBack);
            chatView = findViewById(R.id.chatRecView);
            chatView.setHasFixedSize(true);
            chatLayout = new LinearLayoutManager(this);
            chatView.setLayoutManager(chatLayout);

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (ChatActivity.this, MainActivity.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }
            });

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

            Retrofit retrofit=new Retrofit.Builder()
                    //.baseUrl("https://jsonplaceholder.typicode.com/")
                    //.baseUrl("http://192.168.0.15:8080/v1/chat/")
                    .baseUrl("http://192.168.1.10:8081/v1/chat/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            ChatApi chatApi= retrofit.create(ChatApi.class);

            Call<ArrayList<ChatRequest>> call = chatApi.getChats(userId);

            call.enqueue(new Callback<ArrayList<ChatRequest>>() {

                @Override
                public void onResponse(Call<ArrayList<ChatRequest>> call, Response<ArrayList<ChatRequest>> response) {
                    if (!response.isSuccessful()) {
                        //textViewResult.setText("Code: " + response.code());
                        ChatRequest cr = new ChatRequest();
                        cr.setUser2UserName("Code: "+response.code());
                        chats.add(cr);
                        chatAdapter = new ChatAdapter(chats);
                        chatView.setAdapter(chatAdapter);
                        return;
                    }

                    chats = response.body();
                    chatAdapter = new ChatAdapter(chats);
                    chatView.setAdapter(chatAdapter);

                    chatAdapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            changeItem(position,"Clicked");
                        }
                    });
                }

                @Override
                public void onFailure(Call<ArrayList<ChatRequest>> call, Throwable t) {
                    //textViewResult.setText(t.getMessage());
                    ChatRequest cr = new ChatRequest();
                    cr.setUser2UserName("Code: "+t.getMessage());
                    chats.add(cr);
                    chatAdapter = new ChatAdapter(chats);
                    chatView.setAdapter(chatAdapter);
                }

            });
        }
        public void changeItem(int position, String text){
            Intent intent = new Intent (ChatActivity.this, MessagesActivity.class);
            intent.putExtra("userId",userId);
            intent.putExtra("chatId",chats.get(position).getChatId());
            startActivity(intent);
        }

    }

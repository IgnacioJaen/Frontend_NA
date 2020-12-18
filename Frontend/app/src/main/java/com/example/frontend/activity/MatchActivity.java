package com.example.frontend.activity;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.adapter.MatchAdapter;
import com.example.frontend.adapter.ProfileSubcategoryAdapter;
import com.example.frontend.api.ChatApi;
import com.example.frontend.api.MessagesApi;
import com.example.frontend.api.UserApi;
import com.example.frontend.api.UserSubcategoryApi;
import com.example.frontend.model.*;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MatchActivity extends Fragment {
    private RecyclerView matchView;
    private MatchAdapter subcategoryAdapter;
    private RecyclerView.LayoutManager subcategoryLayout;
    private ArrayList<UserSubcategory> subcategory;
    private Integer userId;
    private String userType;
    private Integer userAcceptedId;
    private Integer lastChatId;
    private Integer clicks=0;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.activity_fragment_match, container, false);
        userId = getArguments().getInt("userIdFromMainActivity");
        userType = getArguments().getString("accountType");
        subcategory=new ArrayList<>();
        matchView=vista.findViewById(R.id.matchRecView);
        if (userType.equals("Basico")) {
            matches();
        }else{
            matchesPremium();
        }
        //Toast.makeText(getActivity(),":"+userType,Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                subcategoryAdapter = new MatchAdapter(subcategory);
                matchView.setAdapter(subcategoryAdapter);
                matchView.setLayoutManager(new LinearLayoutManager(getContext()));
                subcategoryAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //userName();
                                //Toast.makeText(getActivity(),"ChatId: "+chat.getChatId(),Toast.LENGTH_SHORT).show();
                                createMessage();
                            }
                        }, 100);
                        userAcceptedId=subcategory.get(position).getUserId();
                        //Toast.makeText(getContext(),"userAccId"+userAcceptedId, Toast.LENGTH_SHORT).show();
                        createChat();
                    }
                });

            }
        }, 3000);

        return vista;
    }

    private void matchesPremium() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://192.168.0.15:8080/v1/")
                .baseUrl("http://192.168.1.10:8081/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        UserSubcategoryApi userSubcategoryApi= retrofit.create(UserSubcategoryApi.class);

        Call<ArrayList<UserSubcategory>> call = userSubcategoryApi.getUserMatchPremium(userId);

        call.enqueue(new Callback<ArrayList<UserSubcategory>>() {
            @Override
            public void onResponse(Call<ArrayList<UserSubcategory>> call, Response<ArrayList<UserSubcategory>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    return;
                }

                subcategory = response.body();
                subcategoryAdapter = new MatchAdapter(subcategory);
            }


            @Override
            public void onFailure(Call<ArrayList<UserSubcategory>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void matches() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://192.168.0.15:8080/v1/")
                .baseUrl("http://192.168.1.10:8081/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        UserSubcategoryApi userSubcategoryApi= retrofit.create(UserSubcategoryApi.class);

        Call<ArrayList<UserSubcategory>> call = userSubcategoryApi.getUserMatch(userId);

        call.enqueue(new Callback<ArrayList<UserSubcategory>>() {
            @Override
            public void onResponse(Call<ArrayList<UserSubcategory>> call, Response<ArrayList<UserSubcategory>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    return;
                }

                subcategory = response.body();
                subcategoryAdapter = new MatchAdapter(subcategory);

            }


            @Override
            public void onFailure(Call<ArrayList<UserSubcategory>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createChat() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.1.10:8081/v1/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ChatApi chatApi= retrofit.create(ChatApi.class);

        Chat chat = new Chat();
        chat.setUser1Id(userId);
        chat.setUser2Id(userAcceptedId);

        Call<Chat> call = chatApi.createChat(chat);

        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(getActivity(),"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                getLastChatId();
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.d("code","Code: " + t.getMessage());
                Toast.makeText(getActivity(),"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    private void getLastChatId() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.1.10:8081/v1/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ChatApi chatApi= retrofit.create(ChatApi.class);

        Call<Integer> call = chatApi.lastChatId();

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(getActivity(),"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                lastChatId = response.body();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //userName();
                        //Toast.makeText(getActivity(),"ChatId: "+chat.getChatId(),Toast.LENGTH_SHORT).show();
                        createMessage();
                    }
                }, 800);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("code","Code: " + t.getMessage());
                Toast.makeText(getActivity(),"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    private void createMessage() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.1.10:8081/v1/messages/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        MessagesApi messageApi= retrofit.create(MessagesApi.class);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        Messages messages=new Messages();
        messages.setChatId(lastChatId);
        messages.setDate(date);
        messages.setContent("Ahora podemos chatear!");
        messages.setReceiverUserId(userAcceptedId);
        messages.setTransmitterUserId(userId);

        Call<Messages> call = messageApi.sendMessage(messages);

        //Call<User> call = userApi.createUser(userTypeSelected, accountTypeSelected, name.getText().toString(),surname.getText().toString(), etBirthdate.getText().toString(), genderSelected, email.getText().toString(), password.getText().toString(),"URL");

        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    return;
                }
               // etMessage.setText("");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent (getActivity(), ChatActivity.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }
                }, 800);
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.d("code","Code: " + t.getMessage());
                return;
            }
        });
    }
}

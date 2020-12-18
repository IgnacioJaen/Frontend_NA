package com.example.frontend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.ProfileSubcategoryAdapter;
import com.example.frontend.adapter.UserSubcategoryAdapter;
import com.example.frontend.api.SubcategoryApi;
import com.example.frontend.api.UserApi;
import com.example.frontend.api.UserSubcategoryApi;
import com.example.frontend.model.Subcategory;
import com.example.frontend.model.User;
import com.example.frontend.model.UserRequest;
import com.example.frontend.model.UserSubcategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView subcategoryView;
    private ProfileSubcategoryAdapter subcategoryAdapter;
    private RecyclerView.LayoutManager subcategoryLayout;
    private ArrayList<UserSubcategory> subcategory;
    public int subcategoryId;
    Button btnBack;
    Context context;
    User user;
    Integer userId, chatId, recId;
    TextView name, birthdate, gender;
    public ArrayList<Integer> cbList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userId = getIntent().getIntExtra("userId", 0);
        chatId = getIntent().getIntExtra("chatId", 0);
        recId = getIntent().getIntExtra("receiver", 0);

        subcategoryView = findViewById(R.id.subProfileRecView);

        name = findViewById(R.id.userNameProfile);
        birthdate = findViewById(R.id.birthdateProfile);

        btnBack = findViewById(R.id.btnBackProfile);

        subcategoryView.setHasFixedSize(true);
        subcategoryLayout = new LinearLayoutManager(this);
        subcategoryView.setLayoutManager(subcategoryLayout);

        context = this;

        userNameAndBirthdate();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                subcategories();;
                //mensajes();
            }
        }, 4000);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileActivity.this, ChatActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("chatId",chatId);
                startActivity(intent);
            }
        });

    }

    private void subcategories() {
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

        Call<ArrayList<UserSubcategory>> call = userSubcategoryApi.getUserSubcategory(recId);

        call.enqueue(new Callback<ArrayList<UserSubcategory>>() {
            @Override
            public void onResponse(Call<ArrayList<UserSubcategory>> call, Response<ArrayList<UserSubcategory>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    return;
                }

                subcategory = response.body();
                subcategoryAdapter = new ProfileSubcategoryAdapter(subcategory);
                subcategoryView.setAdapter(subcategoryAdapter);

            }


            @Override
            public void onFailure(Call<ArrayList<UserSubcategory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userNameAndBirthdate() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://192.168.0.15:8080/v1/user/")
                .baseUrl("http://192.168.1.10:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);

        Call<User> call = userApi.getUser(recId);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Log.d("Code","Code: "+response.code());
                    return;
                }

                user=new User();

                user = response.body();
                name.setText(user.getName());
                birthdate.setText(user.getBirthdate());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Log.d("Code","Code: "+t.getMessage());
            }

        });
    }



}

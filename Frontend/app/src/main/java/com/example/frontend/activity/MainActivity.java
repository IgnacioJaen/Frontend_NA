package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.example.frontend.adapter.MatchAdapter;
import com.example.frontend.api.AccountTypeApi;
import com.example.frontend.api.UserApi;
import com.example.frontend.api.UserSubcategoryApi;
import com.example.frontend.model.UserSubcategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private Button chatButton;
    private Button btnCategories;
    private String userType;
    int userId;
    private View bottomView;
    //int userId = getIntent().getExtras().getInt("userId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomView = findViewById(R.id.navigationView);
        bottomView.setOnNavigationItemSelectedListener(navListener);

        userId = getIntent().getIntExtra("userId", 0);

        findUserTypeById();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //userName();
                Bundle args = new Bundle();

                args.putInt("userIdFromMainActivity", userId);

                args.putString("accountType", userType);

                MatchActivity fragment=new MatchActivity();
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        }, 2000);

        /*chatButton = findViewById(R.id.chat_button);
        chatButton.setOnClickListener(this);

        btnCategories = findViewById(R.id.category_button);
        btnCategories.setOnClickListener(this);

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, CategoryActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });*/
    }

    private void findUserTypeById() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://192.168.0.15:8080/v1/")
                .baseUrl("http://192.168.1.10:8081/v1/accountType/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        AccountTypeApi userApi= retrofit.create(AccountTypeApi.class);

        Call<String> call = userApi.getUserTypeById(userId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ERROR on Response", Toast.LENGTH_SHORT).show();
                    return;
                }

                userType = response.body();
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                     if(item.getItemId() == R.id.chat_button){
                         Intent intent = new Intent (MainActivity.this, ChatActivity.class);
                         intent.putExtra("userId",userId);
                         startActivity(intent);
                     }
                     if(item.getItemId() == R.id.category_button){
                         Intent intent = new Intent (MainActivity.this, CategoryActivity.class);
                         intent.putExtra("userId",userId);
                         startActivity(intent);
                     }
                    if(item.getItemId() == R.id.user_button){
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }

                    if(item.getItemId() == R.id.logout_button){
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }

                    if(item.getItemId() == R.id.warning_button){
                        Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }

                     return true;
                }
            };

    /*@Override
    public void onClick(View v) {
        Intent intent = new Intent (MainActivity.this, ChatActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }*/


}
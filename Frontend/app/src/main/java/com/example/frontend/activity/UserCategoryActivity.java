package com.example.frontend.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.CategoryAdapter;
import com.example.frontend.api.CategoryApi;
import com.example.frontend.model.CategoryRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class UserCategoryActivity extends AppCompatActivity {

    private RecyclerView categoryView;

    private CategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager categoryLayout;
    private ArrayList<CategoryRequest> category;
    Integer categoryId, userId;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_category);
        btnBack = findViewById(R.id.btnBack);
        userId = getIntent().getIntExtra("userId", 0);
        categoryId = getIntent().getIntExtra("categoryId", 0);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Categorias agregadas correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (UserCategoryActivity.this, MainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (UserCategoryActivity.this, MainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        categoryView = findViewById(R.id.categoryRecView);
        categoryView.setHasFixedSize(true);
        categoryLayout = new LinearLayoutManager(this);
        categoryView.setLayoutManager(categoryLayout);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.0.10:8080/v1/category/")
                //.baseUrl("http://192.168.31.148:8081/v1/category/")
                //.baseUrl("http://localhost:8081/v1/category/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        CategoryApi categoryApi= retrofit.create(CategoryApi.class);
        Call<ArrayList<CategoryRequest>> call = categoryApi.getCategories();
        call.enqueue(new Callback<ArrayList<CategoryRequest>>() {

            @Override
            public void onResponse(Call<ArrayList<CategoryRequest>> call, Response<ArrayList<CategoryRequest>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    CategoryRequest cr = new CategoryRequest();
                    cr.setNameCategory("Code: "+response.code());
                    category.add(cr);
                    categoryAdapter = new CategoryAdapter(category);
                    categoryView.setAdapter(categoryAdapter);
                    return;
                }

                category = response.body();
                categoryAdapter = new CategoryAdapter(category);
                categoryView.setAdapter(categoryAdapter);

                categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        categoryId=category.get(position).getCategoryId();
                        Intent intent = new Intent (UserCategoryActivity.this, UserSubcategoryActivity.class);
                        intent.putExtra("categoryId",categoryId);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryRequest>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                CategoryRequest cr = new CategoryRequest();
                cr.setNameCategory("Code: "+t.getMessage());
                Log.d("codigo: ", ""+t.getMessage());
                category.add(cr);
                categoryAdapter = new CategoryAdapter(category);
                categoryView.setAdapter(categoryAdapter);
            }


        });

    }
}
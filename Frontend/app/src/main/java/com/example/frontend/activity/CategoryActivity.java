package com.example.frontend.activity;

import android.content.Intent;
import android.view.View;
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

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager categoryLayout;
    private ArrayList<CategoryRequest> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(CategoryActivity.this, MainActivity.class);
                CategoryActivity.this.startActivity(activityChangeIntent);
            }
        });


        int userId = getIntent().getIntExtra("userId", 0);

        categoryView = findViewById(R.id.chatRecView);
        categoryView.setHasFixedSize(true);
        categoryLayout = new LinearLayoutManager(this);
        categoryView.setLayoutManager(categoryLayout);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.0.15:8080/v1/category/")
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
                        changeItem(position,"Clicked");
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryRequest>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                CategoryRequest cr = new CategoryRequest();
                cr.setNameCategory("Code: "+t.getMessage());
                category.add(cr);
                categoryAdapter = new CategoryAdapter(category);
                categoryView.setAdapter(categoryAdapter);
            }


        });

    }
    public void changeItem(int position, String text){
        category.get(position).setNameCategory("CLICK");
        categoryAdapter.notifyItemChanged(position);
    }
}
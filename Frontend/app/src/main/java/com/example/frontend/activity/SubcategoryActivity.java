package com.example.frontend.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.CategoryAdapter;
import com.example.frontend.adapter.SubcategoryAdapter;
import com.example.frontend.api.CategoryApi;
import com.example.frontend.api.SubcategoryApi;
import com.example.frontend.model.Category;
import com.example.frontend.model.CategoryRequest;
import com.example.frontend.model.Subcategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    private RecyclerView subcategoryView;
    private SubcategoryAdapter subcategoryAdapter;
    private RecyclerView.LayoutManager subcategoryLayout;
    private ArrayList<Subcategory> subcategory;
    private int categoryId, subcategoryId;
    Button btnBack;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        categoryId = getIntent().getIntExtra("categoryId", 1);

        subcategoryView = findViewById(R.id.subcategoryRecView);

        btnBack = findViewById(R.id.btnBack);

        subcategoryView.setHasFixedSize(true);
        subcategoryLayout = new LinearLayoutManager(this);
        subcategoryView.setLayoutManager(subcategoryLayout);

        context = this;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.0.15:8080/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        SubcategoryApi subcategoryApi= retrofit.create(SubcategoryApi.class);

        Call<ArrayList<Subcategory>> call = subcategoryApi.getSubcategoryList(categoryId);

        call.enqueue(new Callback<ArrayList<Subcategory>>() {
            @Override
            public void onResponse(Call<ArrayList<Subcategory>> call, Response<ArrayList<Subcategory>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    return;
                }
                subcategory = response.body();
                subcategoryAdapter = new SubcategoryAdapter(subcategory);
                subcategoryView.setAdapter(subcategoryAdapter);

                subcategoryAdapter.setOnItemClickListener(new SubcategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        subcategoryId=subcategory.get(position).getSubcategoryId();
                        Intent intent = new Intent (SubcategoryActivity.this, EditSubcategoryActivity.class);
                        intent.putExtra("subcategoryId",subcategoryId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Subcategory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SubcategoryActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SubcategoryActivity.this, EditSubcategoryActivity.class);
                startActivity(intent);
            }
        });





    }
}
package com.example.frontend.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.api.CategoryApi;
import com.example.frontend.model.Category;
import com.example.frontend.model.ChatRequest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditCategoryActivity extends AppCompatActivity {

    Integer categoryId, userId;
    EditText name;
    Button btnBack, btnNext, btnDelete, btnSub, btnUpload, btnCamera;
    ImageView ivCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        categoryId = getIntent().getIntExtra("categoryId", 0);
        userId = getIntent().getIntExtra("userId", 0);
        name = findViewById(R.id.etName);

        btnNext = findViewById(R.id.btnNext);
        btnSub = findViewById(R.id.btnSub);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.0.15:8080/v1/")
                //.baseUrl("http://192.168.31.148:8081/v1/")
                //.baseUrl("http://localhost:8080/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        CategoryApi categoryApi= retrofit.create(CategoryApi.class);

        Call<Category> call2 = categoryApi.getCategory(categoryId);

        call2.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call2, Response<Category> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    return;
                }
                Category category = new Category();
                category = response.body();
                name.setText(category.getName());
                return;
            }

            @Override
            public void onFailure(Call<Category> call2, Throwable t) {
                Log.d("code","Code: " + t.getMessage());
                return;
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (EditCategoryActivity.this, CategoryActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (EditCategoryActivity.this, SubcategoryActivity.class);
                intent.putExtra("categoryId",categoryId);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Category category = new Category();
                category.setCategoryId(categoryId);
                CategoryApi categoryApi3= retrofit.create(CategoryApi.class);
                Call<Category> call3 = categoryApi3.deleteCategory(category);

                call3.enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call3, Response<Category> response) {
                        if (!response.isSuccessful()) {
                            Log.d("code","Code: " + response.code());
                            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Categoria eliminada exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (EditCategoryActivity.this, CategoryActivity.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        Log.d("code","Code: " + t.getMessage());
                        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar el nombre de la categoria", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Category category = new Category();
                    category.setCategoryId(categoryId);
                    category.setName(name.getText().toString());
                    category.setPhotoId(2);

                    CategoryApi categoryApi2= retrofit.create(CategoryApi.class);
                    Call<Category> call = categoryApi2.editCategory(category);

                    call.enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            if (!response.isSuccessful()) {
                                Log.d("code","Code: " + response.code());
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Categoria editada exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (EditCategoryActivity.this, MainActivity.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable t) {
                            Log.d("code","Code: " + t.getMessage());
                            return;
                        }
                    });
                }

            }
        });



    }
}
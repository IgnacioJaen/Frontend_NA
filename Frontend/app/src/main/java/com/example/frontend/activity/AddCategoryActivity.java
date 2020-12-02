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
import com.example.frontend.api.CategoryApi;
import com.example.frontend.model.Category;
import com.example.frontend.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCategoryActivity extends AppCompatActivity {

    EditText name;
    Button btnBack, btnNext, btnUpload, btnCamera;
    ImageView ivCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);


        name = findViewById(R.id.etName);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddCategoryActivity.this, CategoryActivity.class);
                startActivity(intent);
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
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

                    Retrofit retrofit=new Retrofit.Builder()
                            //.baseUrl("https://jsonplaceholder.typicode.com/")
                            //.baseUrl("http://192.168.0.15:8080/v1/")
                            .baseUrl("http://192.168.31.148:8081/v1/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient)
                            .build();
                    CategoryApi categoryApi= retrofit.create(CategoryApi.class);

                    Category category = new Category();
                    category.setName(name.getText().toString());
                    category.setPhotoId(1);

                    Call<Category> call = categoryApi.createCategory(category);

                    call.enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            if (!response.isSuccessful()) {
                                Log.d("code","Code: " + response.code());
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Categoria agregada exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (AddCategoryActivity.this, CategoryActivity.class);
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
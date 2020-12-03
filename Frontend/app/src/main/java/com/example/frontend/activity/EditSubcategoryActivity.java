package com.example.frontend.activity;


import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontend.R;
import com.example.frontend.api.CategoryApi;
import com.example.frontend.api.SubcategoryApi;
import com.example.frontend.model.Category;
import com.example.frontend.model.Subcategory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditSubcategoryActivity extends AppCompatActivity {

    Integer userId;
    EditText etName;
    Button btnSave, btnCancel, btnDelete;
    TextView tvSubcategory;
    Integer subcategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subcategory);
        subcategoryId = getIntent().getIntExtra("subcategoryId", 1);
        etName = findViewById(R.id.etName);
        userId = getIntent().getIntExtra("userId", 0);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.0.15:8080/v1/")
                //.baseUrl("http://localhost:8080/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        SubcategoryApi subcategoryApi= retrofit.create(SubcategoryApi.class);

        Call<Subcategory> call = subcategoryApi.getSubcategory(subcategoryId);

        call.enqueue(new Callback<Subcategory>() {
            @Override
            public void onResponse(Call<Subcategory> call, Response<Subcategory> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(getApplicationContext(),"Is not succesful Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                Subcategory subcategory = new Subcategory();
                subcategory = response.body();
                etName.setText(subcategory.getName());
                return;

            }

            @Override
            public void onFailure(Call<Subcategory> call, Throwable t) {
                Log.d("code","Code: " + t.getMessage());
                Toast.makeText(getApplicationContext(),"Error onFailure", Toast.LENGTH_SHORT).show();
                return;
            }
        });






        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar el nombre de la subcategoria", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setSubcategoryId(subcategoryId);
                    subcategory.setName(etName.getText().toString());

                    SubcategoryApi subcategoryApi2= retrofit.create(SubcategoryApi.class);
                    Call<Subcategory> call = subcategoryApi2.editSubcategory(subcategory);

                    call.enqueue(new Callback<Subcategory>() {
                        @Override
                        public void onResponse(Call<Subcategory> call, Response<Subcategory> response) {
                            if (!response.isSuccessful()) {
                                Log.d("code","Code: " + response.code());
                                Toast.makeText(getApplicationContext(),"Is not succesful Error", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(getApplicationContext(), "Subcategoria editada exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (EditSubcategoryActivity.this, CategoryActivity.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<Subcategory> call, Throwable t) {
                            Log.d("code","Code: " + t.getMessage());
                            Toast.makeText(getApplicationContext(),"Error onFailure", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });

                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (EditSubcategoryActivity.this, CategoryActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subcategory subcategory = new Subcategory();
                subcategory.setSubcategoryId(subcategoryId);

                SubcategoryApi subcategoryApi2= retrofit.create(SubcategoryApi.class);
                Call<Subcategory> call = subcategoryApi2.deleteSubcategory(subcategory);

                call.enqueue(new Callback<Subcategory>() {
                    @Override
                    public void onResponse(Call<Subcategory> call, Response<Subcategory> response) {
                        if (!response.isSuccessful()) {
                            Log.d("code","Code: " + response.code());
                            Toast.makeText(getApplicationContext(),"Is not succesful Error", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "Subcategoria Eliminada exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (EditSubcategoryActivity.this, CategoryActivity.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Subcategory> call, Throwable t) {
                        Log.d("code","Code: " + t.getMessage());
                        Toast.makeText(getApplicationContext(),"Error onFailure", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

            }
        });




    }


}
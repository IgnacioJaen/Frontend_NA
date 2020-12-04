package com.example.frontend.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.SubcategoryAdapter;
import com.example.frontend.adapter.UserSubcategoryAdapter;
import com.example.frontend.api.CategoryApi;
import com.example.frontend.api.SubcategoryApi;
import com.example.frontend.api.UserSubcategoryApi;
import com.example.frontend.model.Category;
import com.example.frontend.model.Subcategory;
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

public class UserSubcategoryActivity extends AppCompatActivity {

    private RecyclerView subcategoryView;
    private UserSubcategoryAdapter subcategoryAdapter;
    private RecyclerView.LayoutManager subcategoryLayout;
    private ArrayList<Subcategory> subcategory;
    public int subcategoryId;
    Button btnBack;
    Context context;
    Integer userId, categoryId;
    public ArrayList<Integer> cbList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_subcategory);

        categoryId = getIntent().getIntExtra("categoryId", 0);
        userId = getIntent().getIntExtra("userId", 0);

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
                subcategoryAdapter = new UserSubcategoryAdapter(subcategory);
                subcategoryView.setAdapter(subcategoryAdapter);

                subcategoryAdapter.setOnItemClickListener(new UserSubcategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        subcategoryId=subcategory.get(position).getSubcategoryId();
                        if(cbList.size()==0){
                            cbList.add(subcategoryId);
                        }
                        else{
                            boolean n1=comprobarExiste();
                            Toast.makeText(getApplicationContext(), "Bool: "+n1, Toast.LENGTH_SHORT).show();
                            if(n1==true) {
                                for (int i=0; i<cbList.size() ; i++)
                                {
                                    if(cbList.get(i)==subcategoryId)
                                    {
                                        cbList.remove(i);
                                    }
                                }
                            }
                            else
                            {
                                cbList.add(subcategoryId);
                            }
                        }

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
                Intent intent = new Intent (UserSubcategoryActivity.this, UserCategoryActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<cbList.size() ; i++)
                {
                    UserSubcategoryApi userSubcategoryApi= retrofit.create(UserSubcategoryApi.class);
                    UserSubcategory userSubcategory = new UserSubcategory();
                    userSubcategory.setSubcategoryId(cbList.get(i));
                    userSubcategory.setUserId(userId);
                    userSubcategory.setStatus(1);

                    Call<UserSubcategory> call = userSubcategoryApi.insertUserSubcategory(userSubcategory);

                    call.enqueue(new Callback<UserSubcategory>() {
                        @Override
                        public void onResponse(Call<UserSubcategory> call, Response<UserSubcategory> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "ERROR is not succesful", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Subcategoria(s) agregada a tus favoritos", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<UserSubcategory> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "ERROR onFailure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Intent intent = new Intent (UserSubcategoryActivity.this, UserCategoryActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });




    }

    private boolean comprobarExiste() {
        int size= cbList.size();
        boolean flag=false;
        for (int i=0; i<size ; i++)
        {
            if(cbList.get(i)==subcategoryId)
            {
                flag=true;
            }
        }
        return flag;
    }
}
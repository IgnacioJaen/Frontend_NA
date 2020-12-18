package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.example.frontend.api.ChatApi;
import com.example.frontend.api.UserApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;
    private Button loginButton;
    private Button signupButton;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        etName = findViewById(R.id.email_edit);
        etPassword = findViewById(R.id.password_edit);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().isEmpty()||etName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }else{
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

                    Call<Integer> call = userApi.getId(etName.getText().toString(),etPassword.getText().toString());

                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (!response.isSuccessful()) {
                                etName.setText("Code: " + response.code());
                                return;
                            }
                            userId = response.body();
                            Intent intent = new Intent (LogInActivity.this, MainActivity.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            etName.setText(t.getMessage());
                            return;
                        }
                    });
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}

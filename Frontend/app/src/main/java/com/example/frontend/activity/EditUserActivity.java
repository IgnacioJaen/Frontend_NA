package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.api.ChatApi;
import com.example.frontend.api.UserApi;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Calendar;

public class EditUserActivity extends AppCompatActivity {
    EditText name, surname, email, password, passwordConfir;
    RadioButton female, male;
    RadioButton basic, premium;
    Button updateButton;
    String genderSelected;
    Integer  accountTypeSelected;
    User user, updatedUser;
    String finalUserType;
    String finalBirthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        updateButton = findViewById(R.id.updateProfileButton);
        name = findViewById(R.id.etName);
        surname = findViewById(R.id.etSurname);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        female = findViewById(R.id.radioFemale);
        male = findViewById(R.id.radioMale);
        basic = findViewById(R.id.radioBasic);
        premium = findViewById(R.id.radioPremium);
        passwordConfir = findViewById(R.id.etPasswordConfirmation);

        int userId = getIntent().getIntExtra("userId", 0);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://192.168.0.15:8080/v1/user/")
                .baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);

        Call<User> call = userApi.getUser(userId);

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
                             surname.setText(user.getSurname());
                             email.setText(user.getEmail());
                             password.setText(user.getPassword());
                             finalUserType = user.getUserType();
                             finalBirthdate = user.getBirthdate();

                         }

                         @Override
                         public void onFailure(Call<User> call, Throwable t) {
                             //textViewResult.setText(t.getMessage());
                             Log.d("Code","Code: "+t.getMessage());
                         }

                     });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(password.getText().toString()).equals(passwordConfir.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }else if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (surname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu apellido", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debes ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!female.isChecked() && !male.isChecked()){
                    Toast.makeText(getApplicationContext(), "Debes indicar tu género", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!basic.isChecked() && !premium.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Debes escoger tu plan", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

                    Retrofit retrofit=new Retrofit.Builder()
                            //.baseUrl("https://jsonplaceholder.typicode.com/")
                            //.baseUrl("http://192.168.31.148:8081/v1/user/")
                            .baseUrl("http://192.168.0.15:8080/v1/user/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient)
                            .build();
                    UserApi userApi= retrofit.create(UserApi.class);

                    if(male.isChecked()){
                        genderSelected = male.getText().toString();
                    }else{
                        genderSelected = female.getText().toString();
                    }

                    if(basic.isChecked()){
                        accountTypeSelected = 1;
                    }else{
                        accountTypeSelected = 2;
                    }


                    updatedUser = new User();
                    updatedUser.setUserId(userId);
                    updatedUser.setAccountTypeId(accountTypeSelected);
                    updatedUser.setName(name.getText().toString());
                    updatedUser.setSurname(surname.getText().toString());
                    updatedUser.setUserType(finalUserType);
                    updatedUser.setBirthdate(finalBirthdate);
                    updatedUser.setEmail(email.getText().toString());
                    updatedUser.setPassword(password.getText().toString());
                    updatedUser.setGender(genderSelected);
                    updatedUser.setUserPhoto("URL");

                    Call<User> call = userApi.updateUser(updatedUser);

                    call.enqueue(new Callback<User>() {

                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (!response.isSuccessful()) {
                                //textViewResult.setText("Code: " + response.code());
                                Log.d("code","Code: " + response.code());
                                return;
                            }
                            Intent intent = new Intent (EditUserActivity.this, UserActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            //textViewResult.setText(t.getMessage());
                            Log.d("code","Code: " + t.getMessage());
                            return;
                        }

                    });
                }
            }
        });
    }
}

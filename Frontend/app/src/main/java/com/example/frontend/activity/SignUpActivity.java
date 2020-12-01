package com.example.frontend.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.example.frontend.api.UserApi;
import com.example.frontend.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    EditText etBirthdate;
    EditText name, surname, email, password;
    RadioButton female, male;
    RadioButton basic, premium;
    RadioButton client, admi;
    String genderSelected, userTypeSelected;
    Integer  accountTypeSelected;
    Calendar calendar = Calendar.getInstance();
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etBirthdate = findViewById(R.id.etBirthdate);
        registerButton = findViewById(R.id.registerButton);
        name = findViewById(R.id.etName);
        surname = findViewById(R.id.etSurname);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        female = findViewById(R.id.radioFemale);
        male = findViewById(R.id.radioMale);
        basic = findViewById(R.id.radioBasic);
        premium = findViewById(R.id.radioPremium);
        client = findViewById(R.id.radioClient);
        admi = findViewById(R.id.radioAdmi);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etBirthdate.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu fecha de nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                } else if (name.getText().toString().isEmpty()) {
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
                } else if (!admi.isChecked() && !client.isChecked()){
                    Toast.makeText(getApplicationContext(), "Debes seleccionar tu tipo de usuario", Toast.LENGTH_SHORT).show();
                    return;
                } else if (admi.isChecked()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);

                    alert.setTitle("Administrador");
                    alert.setMessage("Para registrar un usuario de tipo administrador, debe ingresar la contraseña" +
                            " correspondiente a esta accion");

                    final EditText input = new EditText(SignUpActivity.this);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (input.getText().toString().equals("admi3454")){
                                Toast.makeText(getApplicationContext(), "Password Correcto", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                Toast.makeText(getApplicationContext(), "Password Incorrecto", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });

                    alert.show();
                }else {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

                    Retrofit retrofit=new Retrofit.Builder()
                            //.baseUrl("https://jsonplaceholder.typicode.com/")
                            .baseUrl("http://192.168.31.148:8081/v1/user/")
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

                    if(client.isChecked()){
                        userTypeSelected = "Client";
                    }else{
                        userTypeSelected = "Admi";
                    }

                    User user = new User();
                    user.setAccountTypeId(accountTypeSelected);
                    user.setName(name.getText().toString());
                    user.setSurname(surname.getText().toString());
                    user.setBirthdate(etBirthdate.getText().toString());
                    user.setUserType(userTypeSelected);
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setGender(genderSelected);
                    user.setUserPhoto("URL");

                    Call<User> call = userApi.createUser(user);

                    //Call<User> call = userApi.createUser(userTypeSelected, accountTypeSelected, name.getText().toString(),surname.getText().toString(), etBirthdate.getText().toString(), genderSelected, email.getText().toString(), password.getText().toString(),"URL");

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (!response.isSuccessful()) {
                                Log.d("code","Code: " + response.code());
                                return;
                            }
                            Intent intent = new Intent (SignUpActivity.this, LogInActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("code","Code: " + t.getMessage());
                            return;
                        }
                    });
                }
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                actualizarInput();
            }
        };

        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void actualizarInput() {
        String formatoDeFecha = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);
        etBirthdate.setText(sdf.format(calendar.getTime()));
    }
}


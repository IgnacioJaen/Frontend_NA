package com.example.frontend.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    EditText etBirthdate;
    EditText name, surname, email, password;
    RadioButton female, male;
    RadioButton basic, premium;
    Calendar calendar = Calendar.getInstance();
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etBirthdate = findViewById(R.id.etBirthdate);
        registerButton = findViewById(R.id.signup_button);
        name = findViewById(R.id.etName);
        surname = findViewById(R.id.etSurname);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        female = findViewById(R.id.radioFemale);
        male = findViewById(R.id.radioMale);
        basic = findViewById(R.id.radioBasic);
        premium = findViewById(R.id.radioPremium);

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


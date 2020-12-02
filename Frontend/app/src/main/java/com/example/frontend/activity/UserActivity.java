package com.example.frontend.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity  {
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userId = getIntent().getIntExtra("userId", 0);
    }
}

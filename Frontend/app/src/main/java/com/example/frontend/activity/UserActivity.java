package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.frontend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity  {
    int userId;
    CardView profileCard;
    CardView categoryCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userId = getIntent().getIntExtra("userId", 0);

        profileCard = findViewById(R.id.profileCardView);
        categoryCard = findViewById(R.id.categoriesCardView);

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (UserActivity.this, EditUserActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

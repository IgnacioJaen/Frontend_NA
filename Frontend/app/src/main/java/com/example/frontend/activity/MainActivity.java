package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private Button chatButton;
    private Button btnCategories;
    int userId;
    private View bottomView;
    //int userId = getIntent().getExtras().getInt("userId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomView = findViewById(R.id.navigationView);
        bottomView.setOnNavigationItemSelectedListener(navListener);

        userId = getIntent().getIntExtra("userId", 0);

        /*chatButton = findViewById(R.id.chat_button);
        chatButton.setOnClickListener(this);

        btnCategories = findViewById(R.id.category_button);
        btnCategories.setOnClickListener(this);

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, CategoryActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });*/
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                     if(item.getItemId() == R.id.chat_button){
                         Intent intent = new Intent (MainActivity.this, ChatActivity.class);
                         intent.putExtra("userId",userId);
                         startActivity(intent);
                     }
                     if(item.getItemId() == R.id.category_button){
                         Intent activityChangeIntent = new Intent(MainActivity.this, CategoryActivity.class);
                         MainActivity.this.startActivity(activityChangeIntent);
                     }

                     return true;
                }
            };

    /*@Override
    public void onClick(View v) {
        Intent intent = new Intent (MainActivity.this, ChatActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }*/


}
package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewResult;
    private Button chatButton;
    private Button btnCategories;
    int userId;
    //int userId = getIntent().getExtras().getInt("userId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = getIntent().getIntExtra("userId", 0);

        textViewResult= findViewById(R.id.text_view_result);

        chatButton = findViewById(R.id.chat_button);
        chatButton.setOnClickListener(this);

        btnCategories = findViewById(R.id.btnCategories);
        btnCategories.setOnClickListener(this);

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, CategoryActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent (MainActivity.this, ChatActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }


}
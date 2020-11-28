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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult= findViewById(R.id.text_view_result);
        chatButton = findViewById(R.id.chat_button);

        chatButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent (v.getContext(), ChatActivity.class);
        startActivityForResult(intent, 0);
    }
}
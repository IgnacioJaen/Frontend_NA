package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.adapter.ReportAdapter;
import com.example.frontend.api.ChatApi;
import com.example.frontend.api.ReportApi;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.ReportRequest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    private RecyclerView reportView;
    private ReportAdapter reportAdapter;
    private RecyclerView.LayoutManager reportLayout;
    private ArrayList<ReportRequest> reports;
    int userId;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        userId = getIntent().getIntExtra("userId", 0);

        btnBack = findViewById(R.id.btnBack);
        reportView = findViewById(R.id.reportRecView);
        reportView.setHasFixedSize(true);
        reportLayout = new LinearLayoutManager(this);
        reportView.setLayoutManager(reportLayout);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ReportActivity.this, MainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("http://192.168.0.15:8080/v1/chat/")
                .baseUrl("http://192.168.1.10:8081/v1/report/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ReportApi reportApi= retrofit.create(ReportApi.class);

        Call<ArrayList<ReportRequest>> call = reportApi.reports();

        call.enqueue(new Callback<ArrayList<ReportRequest>>() {

            @Override
            public void onResponse(Call<ArrayList<ReportRequest>> call, Response<ArrayList<ReportRequest>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    ReportRequest cr = new ReportRequest();
                    cr.setReportedUser("Code: "+response.code());
                    reports.add(cr);
                    reportAdapter = new ReportAdapter(reports);
                    reportView.setAdapter(reportAdapter);
                    return;
                }

                reports = response.body();
                reportAdapter = new ReportAdapter(reports);
                reportView.setAdapter(reportAdapter);

                reportAdapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        changeItem(position,"Clicked");
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<ReportRequest>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                ReportRequest cr = new ReportRequest();
                cr.setReportedUser("Code: "+t.getMessage());
                reports.add(cr);
                reportAdapter = new ReportAdapter(reports);
                reportView.setAdapter(reportAdapter);
            }

        });
    }
    public void changeItem(int position, String text){
        Intent intent = new Intent (ReportActivity.this, ReportInfoActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("reportId",reports.get(position).getReportId());
        startActivity(intent);
    }
}


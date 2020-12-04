package com.example.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.ReportAdapter;
import com.example.frontend.api.ReportApi;
import com.example.frontend.model.ReportRequest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class ReportInfoActivity extends AppCompatActivity {
    private ReportAdapter reportAdapter;
    private ReportRequest reports;
    int userId, reportId;
    private TextView tvReported, tvUser, tvDate, tvDescription;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);

        userId = getIntent().getIntExtra("userId", 0);
        reportId = getIntent().getIntExtra("reportId", 0);

        tvReported = findViewById(R.id.reportedName);
        tvDate = findViewById(R.id.reportedDate);
        tvDescription = findViewById(R.id.reportedOp);
        tvUser = findViewById(R.id.user);

        btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ReportInfoActivity.this, ReportActivity.class);
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
                .baseUrl("http://192.168.31.148:8081/v1/report/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ReportApi reportApi= retrofit.create(ReportApi.class);

        Call<ReportRequest> call = reportApi.getReportById(reportId);

        call.enqueue(new Callback<ReportRequest>() {

            @Override
            public void onResponse(Call<ReportRequest> call, Response<ReportRequest> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }

                reports = response.body();

                tvDate.setText(reports.getDate());
                tvUser.setText(reports.getUser());
                tvReported.setText(reports.getReportedUser());
                tvDescription.setText(reports.getReportOp());
            }

            @Override
            public void onFailure(Call<ReportRequest> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                ReportRequest cr = new ReportRequest();
                cr.setReportedUser("Code: "+t.getMessage());
                //reports.add(cr);
                //reportAdapter = new ReportAdapter(reports);
                //reportView.setAdapter(reportAdapter);
            }

        });
    }
}
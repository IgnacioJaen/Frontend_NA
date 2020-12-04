package com.example.frontend.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.api.ChatApi;
import com.example.frontend.api.ReportApi;
import com.example.frontend.api.ReportOpApi;
import com.example.frontend.api.UserApi;
import com.example.frontend.model.ChatRequest;
import com.example.frontend.model.Report;
import com.example.frontend.model.ReportOpRequest;
import com.example.frontend.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MessagesActivity extends AppCompatActivity {

    int userId, chatId, reportOpId, receiverUserId;
    public ArrayList<ReportOpRequest> reportOptions;
    public ArrayList<String> reportItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        userId = getIntent().getIntExtra("userId", 0);
        chatId = getIntent().getIntExtra("chatId", 0);

        getReceiverUserId(userId, chatId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenuitems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemProfile:
                Toast.makeText(this, "Perfil del usuario", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemReport:
                showAlertDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MessagesActivity.this);
        alertDialog.setTitle("Reportar Usuario");
        //getReportOptions();
        /*String items[] = new String[reportOptions.size()];
        for(int i=0; i<items.length; i++){
            items[i] = "java";
        }*/
        //String [] items = reportItems.toArray(new String[0]);
        String[] items = {"Mal Vocabulario","No Busca Amistad","Hostigador"};
        int checkedItem = 0;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        reportOpId = 1;
                        Toast.makeText(MessagesActivity.this, "Mal vocabulario", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        reportOpId = 2;
                        Toast.makeText(MessagesActivity.this, "No busca amistad", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        reportOpId = 3;
                        Toast.makeText(MessagesActivity.this, "Hostigador", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        alertDialog.setPositiveButton("Reportar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                generateReport();
                //Toast.makeText(MessagesActivity.this, "Positive Button", Toast.LENGTH_LONG).show();
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                reportOpId = 0;
                //Toast.makeText(MessagesActivity.this, "Negative Button", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void generateReport() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.31.148:8081/v1/report/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        ReportApi reportApi= retrofit.create(ReportApi.class);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        //String date = "2020-10-29 22:12:10";

        Report report = new Report();

        //report.setReportId(report.getReportId());
        report.setChatId(chatId);
        report.setReportOpId(reportOpId);
        report.setDate(date);
        report.setReportedUserId(receiverUserId);
        report.setUserId(userId);

        Call<Report> call = reportApi.generateReport(report);

        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (!response.isSuccessful()) {
                    //Log.d("code on response ","Code: " + response.code());
                    Toast.makeText(MessagesActivity.this, "OnResponse: "+response.toString()+" receiverId: "+receiverUserId, Toast.LENGTH_LONG).show();
                    return;
                }
                deleteChat(chatId);
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                Toast.makeText(MessagesActivity.this, "OnFailure: "+t.getMessage()+" receiverId: "+receiverUserId, Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void getReceiverUserId(Integer userId, Integer chatId) {
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

        Call<Integer> call = userApi.getReceiverId(userId, chatId);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                receiverUserId = response.body();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                return;
            }
        });
    }

    private void deleteChat(int chatId) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.31.148:8081/v1/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ChatApi chatApi= retrofit.create(ChatApi.class);

        Call<Integer> call = chatApi.deleteChat(chatId);

        call.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }

        });

        Intent intent = new Intent (MessagesActivity.this, ChatActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    /*
    private void getReportOptions() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.31.148:8081/v1/reportOptions/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ReportOpApi reportOpApi = retrofit.create(ReportOpApi.class);

        Call<ArrayList<ReportOpRequest>> call = reportOpApi.getReportOptions();

        call.enqueue(new Callback<ArrayList<ReportOpRequest>>() {

            @Override
            public void onResponse(Call<ArrayList<ReportOpRequest>> call, Response<ArrayList<ReportOpRequest>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Log.d("On response ", "code " + response.code());
                    return;
                }

                reportOptions = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<ReportOpRequest>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Log.d("On Failure ", "code " + t.getMessage());
            }

        });

    }*/
}

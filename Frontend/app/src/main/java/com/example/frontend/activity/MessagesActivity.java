package com.example.frontend.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frontend.R;
import com.example.frontend.adapter.ChatAdapter;
import com.example.frontend.adapter.MessagesAdapter;
import com.example.frontend.api.*;
import com.example.frontend.model.*;
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

    private RecyclerView messageView;
    private MessagesAdapter messagesAdapter;
    private RecyclerView.LayoutManager messagesLayout;
    private ArrayList<MessagesRequest> messages;
    public LinearLayout llCardMessage;
    int userId, chatId, reportOpId, receiverUserId;
    public ArrayList<ReportOpRequest> reportOptions;
    public ArrayList<String> reportItems;
    private Button sendButton, updateButton;
    private EditText etMessage;
    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        llCardMessage = findViewById(R.id.linLayMessageCard);
        sendButton = findViewById(R.id.sendMessageButton);
        updateButton = findViewById(R.id.updateMessges);
        etMessage = findViewById(R.id.etMessage);
        tvUserName = findViewById(R.id.textViewUserName);

        userId = getIntent().getIntExtra("userId", 0);
        chatId = getIntent().getIntExtra("chatId", 0);

        messageView = findViewById(R.id.rvMessages);
        messageView.setHasFixedSize(true);
        messagesLayout = new LinearLayoutManager(this);
        messageView.setLayoutManager(messagesLayout);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajes();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        getReceiverUserId(userId, chatId);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                userName();
               //mensajes();
            }
        }, 2000);

    }

    private void userName() {
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

        Call<User> call = userApi.getUser(receiverUserId);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Log.d("Code","Code: "+response.code());
                    return;
                }

                User user=new User();

                user = response.body();
                tvUserName.setText(user.getName());

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //userName();
                        mensajes();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
                Log.d("Code","Code: "+t.getMessage());
            }

        });
    }

    private void sendMessage() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.31.148:8081/v1/messages/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        MessagesApi messageApi= retrofit.create(MessagesApi.class);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        Messages messages=new Messages();
        messages.setChatId(chatId);
        messages.setDate(date);
        messages.setContent(etMessage.getText().toString());
        messages.setReceiverUserId(receiverUserId);
        messages.setTransmitterUserId(userId);

        Call<Messages> call = messageApi.sendMessage(messages);

        //Call<User> call = userApi.createUser(userTypeSelected, accountTypeSelected, name.getText().toString(),surname.getText().toString(), etBirthdate.getText().toString(), genderSelected, email.getText().toString(), password.getText().toString(),"URL");

        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    return;
                }
                etMessage.setText("");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //userName();
                        mensajes();
                    }
                }, 800);
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.d("code","Code: " + t.getMessage());
                return;
            }
        });
    }

    private void mensajes() {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl("https://jsonplaceholder.typicode.com/")
                    .baseUrl("http://192.168.31.148:8081/v1/messages/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            MessagesApi messagesApi = retrofit.create(MessagesApi.class);

            Call<ArrayList<MessagesRequest>> call = messagesApi.getMessages(userId, chatId, receiverUserId);

            call.enqueue(new Callback<ArrayList<MessagesRequest>>() {

                @Override
                public void onResponse(Call<ArrayList<MessagesRequest>> call, Response<ArrayList<MessagesRequest>> response) {
                    if (!response.isSuccessful()) {
                        //textViewResult.setText("Code: " + response.code());
                        MessagesRequest cr = new MessagesRequest();
                        messages.add(cr);
                        messagesAdapter = new MessagesAdapter(messages);
                        messageView.setAdapter(messagesAdapter);
                        return;
                    }

                    messages = response.body();
                    messagesAdapter = new MessagesAdapter(messages);
                    messageView.setAdapter(messagesAdapter);

                    messagesAdapter.setOnItemClickListener(new MessagesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //
                        }
                    });
                }

                @Override
                public void onFailure(Call<ArrayList<MessagesRequest>> call, Throwable t) {
                    //textViewResult.setText(t.getMessage());
                    ChatRequest cr = new ChatRequest();
                    cr.setUser2UserName("Code: " + t.getMessage());
                }

            });
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
                Intent intent = new Intent (MessagesActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("receiver",receiverUserId);
                intent.putExtra("chatId",chatId);
                startActivity(intent);
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

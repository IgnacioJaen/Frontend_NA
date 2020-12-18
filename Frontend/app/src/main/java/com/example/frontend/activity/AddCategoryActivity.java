package com.example.frontend.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontend.R;
import com.example.frontend.api.CategoryApi;
import com.example.frontend.api.SubcategoryApi;
import com.example.frontend.model.Category;
import com.example.frontend.model.Subcategory;
import com.example.frontend.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity {

    EditText name;
    Button btnBack, btnNext, btnUpload, btnCamera;
    ImageView imageView;
    Integer userId;
    String currentPhotoPath;
    Bitmap decoded;

    RequestQueue requestQueue;
    static final int REQUEST_PERMISSION_CAMERA = 100;
    static final int REQUEST_TAKE_PHOTO = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        userId = getIntent().getIntExtra("userId", 0);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.etName);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        btnUpload = findViewById(R.id.btnUpload);
        btnCamera = findViewById(R.id.btnCamera);
        requestQueue = Volley.newRequestQueue(this);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AddCategoryActivity.this, CategoryActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar el nombre de la categoria", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (name.getText().toString().equals("-"))
                {
                    Toast.makeText(getApplicationContext(), "Debes ingresar el nombre de la categoria", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(AddCategoryActivity.this);
                    dialogo1.setTitle("AGREGAR");
                    dialogo1.setMessage("¿Esta seguro que desea agregar esta subcategoria?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

                            Retrofit retrofit=new Retrofit.Builder()
                                    //.baseUrl("https://jsonplaceholder.typicode.com/")
                                    .baseUrl("http://192.168.0.10:8080/v1/")
                                    //.baseUrl("http://192.0.10.148:8081/v1/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(httpClient)
                                    .build();
                            CategoryApi categoryApi= retrofit.create(CategoryApi.class);

                            Category category = new Category();
                            category.setName(name.getText().toString());
                            category.setPhotoId(1);

                            Call<Category> call = categoryApi.createCategory(category);

                            call.enqueue(new Callback<Category>() {
                                @Override
                                public void onResponse(Call<Category> call, Response<Category> response) {
                                    if (!response.isSuccessful()) {
                                        Log.d("code","Code: " + response.code());
                                        return;
                                    }
                                    Toast.makeText(getApplicationContext(), "Categoria agregada exitosamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent (AddCategoryActivity.this, CategoryActivity.class);
                                    intent.putExtra("userId",userId);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Category> call, Throwable t) {
                                    Log.d("code","Code: " + t.getMessage());
                                    return;
                                }
                            });

                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            Toast.makeText(getApplicationContext(), "Operacion Cancelada", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (AddCategoryActivity.this, CategoryActivity.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                        }
                    });
                    dialogo1.show();

                }

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la app"), 10);
            }
        });


    }
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){

            File photofile = null;

            try {
                photofile = createImageFile();
            }catch (IOException e){
                e.getMessage();
            }

            if (photofile!=null)
            {
                Uri photoUri = FileProvider.getUriForFile(
                        this,
                        "com.android.camera",
                        photofile
                );

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }

        }
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                takePicture();
            }
            else
            {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_CAMERA
                );
            }
        }
        else{
            takePicture();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO){
            //imageView.setImageResource(R.drawable.bg1);
            if (resultCode == Activity.RESULT_OK ){
                try {
                    File file = new File(currentPhotoPath);
                    Uri uri = Uri.fromFile(file);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(),
                            uri
                    );
                    setToImageView(getToResizedBitmap(bitmap, 480));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (resultCode == RESULT_OK){
            Uri path=data.getData();
            imageView.setImageURI(path);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getToResizedBitmap(Bitmap bitmap, int maxSize) {

        int width = bitmap.getWidth();
        int heigth = bitmap.getHeight();
        if (width <= maxSize && heigth <= maxSize)
        {
            return bitmap;
        }
        float bitmapRatio = (float) width / (float) heigth;

        if(bitmapRatio > 1)
        {
            width=maxSize;
            heigth=(int)(width/bitmapRatio);
        }
        else
        {
            heigth = maxSize;
            width = (int) (heigth * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, 400, 400, true);

    }

    private void setToImageView(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imageView.setImageBitmap(decoded);
    }

    private void uploadImage(){
        String url = "http://192.168.0.10/camera/upload.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddCategoryActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    }
                },
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("path", getStringImage(decoded));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private String getStringImage (Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (permissions.length == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                takePicture();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
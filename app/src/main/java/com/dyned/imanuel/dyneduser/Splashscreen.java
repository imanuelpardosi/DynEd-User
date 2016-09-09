package com.dyned.imanuel.dyneduser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Splashscreen extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Context myContext;
    ProgressDialog progress;

    private ApiInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        myContext = getApplicationContext();
        progress = ProgressDialog.show(Splashscreen.this, "Inisialisasi Data", "Sedang Mengunduh Data Untuk Aplikasi", true);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<User>> call = apiService.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {
                    List<User> listUser = response.body();
                    UserController userController = new UserController(myContext);
                    userController.open();
                    userController.deleteData();
                    for (int i = 0; i < listUser.size(); i++) {
                        User result = listUser.get(i);
                        userController.insertData(result.getId(),result.getName(), result.getUsername(), result.getEmail());
                    }
                    Intent sendIntent = new Intent(myContext, MainActivity.class);
                    startActivity(sendIntent);
                    finish();
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error "+e);
                    Toast.makeText(getApplicationContext(), "Data is not available", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("onFailure", t.toString());
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Access to server failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}

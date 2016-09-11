package com.dyned.imanuel.dyneduser.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dyned.imanuel.dyneduser.API.ApiClient;
import com.dyned.imanuel.dyneduser.API.ApiInterface;
import com.dyned.imanuel.dyneduser.Model.User;
import com.dyned.imanuel.dyneduser.R;
import com.dyned.imanuel.dyneduser.Controller.UserController;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splashscreen extends AppCompatActivity {
    Context myContext;
    ProgressDialog progress;
    String[] quote = {"Programming is Art!",
            "It’s not a bug – it’s an undocumented feature.",
            "Deleted code is debugged code.",
            "Nine people can't make a baby in a month.",
            "Simplicity carried to the extreme becomes elegance."};
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        myContext = getApplicationContext();

        int idx = random.nextInt(quote.length + 1);
        progress = ProgressDialog.show(Splashscreen.this, "Initialize Data", quote[idx], true);

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
                        Log.d("onResponse", "city " + result.getAddress().getCity());
                        Log.d("onResponse", "lat " + result.getAddress().getGeo().getLat());
                        Log.d("onResponse", "company name " + result.getCompany().getName());
                        userController.insertData(result.getId(), result.getName(), result.getUsername(),
                                result.getEmail(), result.address.getStreet(), result.address.getSuite(),
                                result.address.getCity(), result.address.getZipcode(), result.address.geo.getLat(),
                                result.address.geo.getLng(), result.getPhone(), result.getWebsite(),
                                result.company.getName(), result.company.getCatchPhrase(), result.company.getBs());
                    }
                    Intent sendIntent = new Intent(myContext, MainActivity.class);
                    startActivity(sendIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } catch (Exception e) {
                    Log.d("onResponse Exception", "There is an error " + e);
                    Toast.makeText(getApplicationContext(), "Data is not available", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    Button btn_retry = (Button) findViewById(R.id.btn_retry);
                    btn_retry.setVisibility(View.VISIBLE);
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("onFailure", t.toString());
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Access to server failed. Please check your network.", Toast.LENGTH_LONG).show();
                Button btn_retry = (Button) findViewById(R.id.btn_retry);
                btn_retry.setVisibility(View.VISIBLE);
            }
        });
    }

    public void Retry(View v){
        Intent retry = new Intent(this, Splashscreen.class);
        startActivity(retry);
        this.finish();
    }
}

package com.dyned.imanuel.dyneduser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UserDetail extends AppCompatActivity {

    public TextView name;
    public TextView username;
    public TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent mIntent = getIntent();
        int position = mIntent.getIntExtra("position", 0);

        UserController myData = new UserController( this);
        myData.open();

        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);

        name.setText(myData.getData().get(position).getName());
        username.setText(myData.getData().get(position).getUsername());
        email.setText(myData.getData().get(position).getEmail());
    }
}

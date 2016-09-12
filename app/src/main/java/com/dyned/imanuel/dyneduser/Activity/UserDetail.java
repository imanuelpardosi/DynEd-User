package com.dyned.imanuel.dyneduser.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyned.imanuel.dyneduser.R;
import com.dyned.imanuel.dyneduser.Controller.UserController;

import java.util.Locale;

public class UserDetail extends AppCompatActivity {

    private ImageView photo_profile;

    private TextView name;
    private TextView username;
    private TextView email;
    private TextView phone;
    private TextView website;
    private TextView address;
    private TextView zipcode;
    private TextView geo;
    private TextView company;

    private double lat = 0.0;
    private double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent mIntent = getIntent();
        int position = mIntent.getIntExtra("position", 0);

        UserController myData = new UserController(this);
        myData.open();

        photo_profile = (ImageView) findViewById(R.id.user_profile_photo);
        photo_profile.bringToFront();

        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        website = (TextView) findViewById(R.id.website);
        address = (TextView) findViewById(R.id.address);
        zipcode = (TextView) findViewById(R.id.zipcode);
        geo = (TextView) findViewById(R.id.geo);
        company = (TextView) findViewById(R.id.company);

        name.setText(myData.getData().get(position).getName());
        username.setText(myData.getData().get(position).getUsername());
        email.setText(myData.getData().get(position).getEmail());

        phone.setText(myData.getData().get(position).getPhone());
        website.setText(myData.getData().get(position).getWebsite());
        address.setText(myData.getData().get(position).getAddress().getStreet() + ", " +
                myData.getData().get(position).getAddress().getSuite()
                + ", " + myData.getData().get(position).getAddress().getCity());
        zipcode.setText(myData.getData().get(position).getAddress().getZipcode());
        geo.setText("• " + myData.getData().get(position).getAddress().getGeo().getLat() +
                "   • " + myData.getData().get(position).getAddress().getGeo().getLng());
        company.setText(Html.fromHtml("<b>" + myData.getData().get(position).getCompany().getName() +
                "</b><br/>" + myData.getData().get(position).getCompany().getCatchPhrase()
                + "<br/>" + myData.getData().get(position).getCompany().getBs()));

        lat = Double.parseDouble(myData.getData().get(position).getAddress().getGeo().getLat());
        lng = Double.parseDouble(myData.getData().get(position).getAddress().getGeo().getLng());

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email.getText().toString(), null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null));
                startActivity(intent);
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://"+website.getText().toString()));
                startActivity(intent);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        geo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
    }

}

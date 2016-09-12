package com.dyned.imanuel.dyneduser.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyned.imanuel.dyneduser.Model.User;
import com.dyned.imanuel.dyneduser.R;

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

        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra("user");

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

        name.setText(user.getName());
        username.setText(user.getUsername());
        email.setText(user.getEmail());

        phone.setText(user.getPhone());
        website.setText(user.getWebsite());
        address.setText(user.getAddress().getStreet() + ", " + user.getAddress().getSuite()
                + ", " + user.getAddress().getCity());
        zipcode.setText(user.getAddress().getZipcode());
        geo.setText("• " + user.getAddress().getGeo().getLat() + "   • " + user.getAddress().getGeo().getLng());
        company.setText(Html.fromHtml("<b>" + user.getCompany().getName() + "</b><br/>" +
                user.getCompany().getCatchPhrase() + "<br/>" + user.getCompany().getBs()));

        lat = Double.parseDouble(user.getAddress().getGeo().getLat());
        lng = Double.parseDouble(user.getAddress().getGeo().getLng());

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
                intent.setData(Uri.parse("http://" + website.getText().toString()));
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

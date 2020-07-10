package com.app.oroodaqariyah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class calling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);


        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.


        Intent call = new Intent(Intent.ACTION_DIAL,
                Uri.parse(value));
        startActivity(call);

        this.finish();


    }
}
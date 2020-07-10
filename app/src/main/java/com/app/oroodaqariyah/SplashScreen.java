package com.app.oroodaqariyah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);






    }




    public void next(View view) {

        if (!DetectConnection.checkInternetConnection(SplashScreen.this)) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.noconnectionmsg), Snackbar.LENGTH_LONG)
                    .setAction("موافق",null)
                    .show();

        } else {
            Intent myIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }

    }






}
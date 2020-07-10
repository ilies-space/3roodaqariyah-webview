package com.app.oroodaqariyah;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check Network if not exist go back to splash screen (after choosing try again):
        if (!DetectConnection.checkInternetConnection(MainActivity.this)) {

            Intent myIntent = new Intent(MainActivity.this, SplashScreen.class);
            startActivity(myIntent);
            this.finish();

        }

        //Load Components :
        initialize();


        // Handle User interaction ( Keep all links execute on the webView + handle call seller action
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {



                if (!DetectConnection.checkInternetConnection(MainActivity.this)) {
                error(mWebView);
                }else {

                    if (url.startsWith("tel:")) {

                        try {
                            view.stopLoading();
                        } catch (Exception e) {
                        }
                        Intent myIntent = new Intent(MainActivity.this, calling.class);
                        myIntent.putExtra("key", url); //Optional parameters
                        MainActivity.this.startActivity(myIntent);

                        return false ;
                    }


                    if (url.startsWith("http:") || url.startsWith("https:")) {


                        view.loadUrl(url);


                    }
                }

                return true;
            }
        });

        //First time launching app , Show Loading webView Dialog :
        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                dialog.setMessage(" \n الرجاء الإنتظار ... \n"+ progress +" % ");
                if(progress == 100){
                    dialog.dismiss();
                }
            }
        });

    }

    //Initialisation :
    @SuppressLint("SetJavaScriptEnabled")
    public void initialize()
    {
        //Loading Dialog
        dialog = new ProgressDialog(this);
        dialog.setMessage("الرجاء الإنتظار...");
        dialog.setCancelable(false);
        dialog.show();

        //webView :
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://3roodaqariyah.com");


    }

    // Handle back button is pressed
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            // Let the system handle the back button

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("الخروج")
                    .setMessage("الرجاء التأكيد للخروج من التطبيق?")
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("لا", null)
                    .show();
        }
    }


    // error function when no network found :


    /*

//Handle errors and connection corruption :
                mWebView.setWebViewClient(new WebViewClient() {
                    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {

                        error(webView);
                    }
                });

     */
    public void error(WebView webView )
    {
        try {
            webView.stopLoading();
        } catch (Exception e) {
        }

        if (webView.canGoBack()) {
            webView.goBack();
        }

        webView.loadUrl("about:blank");
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("خطأ");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("تم فصل التطبيق عن الأنترنت . الرجاء الإتصال بالأنترنت و المحاولة مجددا ");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "إعادة المحاولة", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());
            }
        });

        alertDialog.show();
    }

}





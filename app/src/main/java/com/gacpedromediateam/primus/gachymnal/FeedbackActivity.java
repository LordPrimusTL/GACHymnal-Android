package com.gacpedromediateam.primus.gachymnal;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Utility;

public class FeedbackActivity extends AppCompatActivity {

    WebView webView;
    NetworkHelper nh = new NetworkHelper(this);
    ProgressDialog pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("Feedback");
        pBar = new ProgressDialog(this);
        if(nh.isConnected()){
            pBar.show();
            pBar.setMessage("Loading...");
            webView = findViewById(R.id.feedbackWebview);
            String url = "http://www.gacpedro.com.ng/review/" + new Utility(this).AndroidID();
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    pBar.dismiss();
                }
            });
            webView.loadUrl(url);
        }else {
            Toast.makeText(this,"No internet connection, Please try again", Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(pBar != null){
            pBar.dismiss();
        }
    }
}

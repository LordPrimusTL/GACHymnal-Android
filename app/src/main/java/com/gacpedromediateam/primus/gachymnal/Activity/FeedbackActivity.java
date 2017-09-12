package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Adapters.AppListAdapter;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Utility;
import com.gacpedromediateam.primus.gachymnal.Helper.hymn;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {

    SharedPreferences getPrefs;
    Integer Language;
    public WebView webView;
    NetworkHelper nh = new NetworkHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        getPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        supportActionBar.setTitle("Feedback");
        if(nh.isConnected())
        {
            webView = (WebView) findViewById(R.id.FeedbackWeb);
            webView.getSettings().setJavaScriptEnabled(true);
            String url = "http://gacserver.000webhostapp.com/review/" + new Utility(this).AndroidID().toString();
            Log.i("WebURL",url);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
        else
        {
            Toast.makeText(this,"No internet connection, Please try again",Toast.LENGTH_LONG);
            onBackPressed();
            finish();
        }
    }
}

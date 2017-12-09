package com.gacpedromediateam.primus.gachymnal.Helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Primus on 7/15/2017.
 */

public class NetworkHelper {
    public Context context;
    public NetworkHelper(Context context) {
        this.context = context;
    }

    public boolean isConnected(){
       try{
           ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
           NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
           if (networkInfo != null && networkInfo.isConnected())
           {
               Log.i("Network Check","Connected");
               return true;
           }
           else
           {
               Log.i("Network Check","Not Connected");
               return false;
           }
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
           return false;
       }
    }
}

package com.gacpedromediateam.primus.gachymnal.Helper;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;

import com.gacpedromediateam.primus.gachymnal.Activity.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Primus on 7/9/2017.
 */

public class Utility {

    public Context context;

    public Utility(Context context)
    {
        this.context = context;
    }

    public String AndroidUUID()
    {
        String uniqueID = UUID.randomUUID().toString();
        String AndroiID = Settings.Secure.ANDROID_ID;

        Log.e("Android ID", AndroiID);
        return uniqueID;
    }

    public String AndroidID()
    {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public String PhoneType()
    {
        return Build.BRAND + " " + Build.MODEL + " " +  Build.ID + " " + Build.DEVICE + " " + Build.PRODUCT;
    }

    public boolean FirstRunActivity(Context context, User user) {
        //All Firstrun stuff comes here
       try
       {
           DbHelper db = new DbHelper(context);
           db.open();
           user.UUID = AndroidUUID();
           //db.AddUser(user);
           db.close();
           return true;
       }
       catch (Exception ex)
       {
           Log.v("Data Create",ex.toString());
           return false;
       }

    }





}


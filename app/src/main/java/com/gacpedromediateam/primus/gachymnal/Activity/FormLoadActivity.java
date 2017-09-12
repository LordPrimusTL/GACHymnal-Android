package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.User;
import com.gacpedromediateam.primus.gachymnal.Helper.Utility;
import com.gacpedromediateam.primus.gachymnal.R;
import com.gacpedromediateam.primus.gachymnal.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FormLoadActivity extends AppCompatActivity {

    public boolean json;

    public String Defaulthost = "http://10.0.2.2:8000/";
    //public String GetMainHymnList = "http://10.0.2.2:8000/API/getmainhymn";
    //public String GetAppHymnList = "http://10.0.2.2:8000/API/getapphymn";
    //public String GetMainVerseList = "http://10.0.2.2:8000/API/getmainverse";
    //public String GetAppVerseList = "http://10.0.2.2:8000/API/getappverse";
    //public String PostInstallations = "http://10.0.2.2:8000/API/installations";
    public String Defaulthostx = "http://10.0.3.2:8000/";
    public String GetMainHymnListX = "http://10.0.3.2:8000/API/getmainhymn";
    public String GetAppHymnListX = "http://10.0.3.2:8000/API/getapphymn";
    public String GetMainVerseListX = "http://10.0.3.2:8000/API/getmainverse";
    public String GetAppVerseListX = "http://10.0.3.2:8000/API/getappverse";
    public String PostInstallationsX = "http://10.0.3.2:8000/API/installations";
    public String PostInstallations = "http://gacserver.000webhostapp.com/API/installations";
    public String GetMainHymnList = "http://gacserver.000webhostapp.com/API/getmainhymn";
    public String GetMainVerseList = "http://gacserver.000webhostapp.com/API/getmainverse";
    public String GetAppHymnList = "http://gacserver.000webhostapp.com/API/getapphymn";
    public String GetAppVerseList = "http://gacserver.000webhostapp.com/API/getappverse";

    Context context = this;
    public ProgressBar pg = null;
    public ProgressBar pgs = null;
    public String full_name;
    public String branch;
    public RadioButton eng;
    public RadioButton yor;
    public int count = 0;
    public Boolean AtFirstRun;
    NetworkHelper nh = new NetworkHelper(context);


    public DbHelper db = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        db.open();
        pg = (ProgressBar) findViewById(R.id.progressBar);
        pgs = (ProgressBar) findViewById(R.id.progressBar3);
        pgs.setMax(100);
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = getPrefs.edit();
        AtFirstRun = getPrefs.getBoolean("AtFirstRun",true);
        if(AtFirstRun)
        {

            eng = (RadioButton) findViewById(R.id.EngLang);
            yor = (RadioButton) findViewById(R.id.YorLang);
            Button SubmitBtn = (Button) findViewById(R.id.form_submit);
            Button SkipBtn = (Button) findViewById(R.id.form_skip);
            SubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    full_name = ((EditText)findViewById(R.id.full_name)).getText().toString();
                    branch = ((EditText)findViewById(R.id.branch)).getText().toString();
                    if(full_name.length() == 0)
                    {
                        ((EditText)findViewById(R.id.full_name)).setError("This Field Can't be Empty");
                    }

                    else if(branch.length() == 0)
                    {
                        ((EditText)findViewById(R.id.branch)).setError("This Field Can't be Empty");
                    }
                    else
                    {
                        (findViewById(R.id.form_linearlayout)).setVisibility(View.GONE);
                        pg.setVisibility(View.VISIBLE);
                        pgs.setVisibility(View.VISIBLE);
                        (findViewById(R.id.form_submit)).setVisibility(View.GONE);
                        (findViewById(R.id.form_skip)).setVisibility(View.GONE);
                        findViewById(R.id.loadTextview).setVisibility(View.VISIBLE);
                        if(eng.isChecked())
                        {
                            Do(1);
                        }

                        if(yor.isChecked())
                        {
                            Do(0);
                        }
                    }

                }
            });

            SkipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    full_name = "user";
                    branch = "user_branch";
                    if(full_name.length() == 0)
                    {
                        ((EditText)findViewById(R.id.full_name)).setError("This Field Can't be Empty");
                    }

                    else if(branch.length() == 0)
                    {
                        ((EditText)findViewById(R.id.branch)).setError("This Field Can't be Empty");
                    }
                    else
                    {
                        (findViewById(R.id.form_linearlayout)).setVisibility(View.GONE);
                        pg.setVisibility(View.VISIBLE);
                        pgs.setVisibility(View.VISIBLE);
                        (findViewById(R.id.form_submit)).setVisibility(View.GONE);
                        (findViewById(R.id.form_skip)).setVisibility(View.GONE);
                        findViewById(R.id.loadTextview).setVisibility(View.VISIBLE);
                        if(eng.isChecked())
                        {
                            Do(1);
                        }

                        if(yor.isChecked())
                        {
                            Do(0);
                        }
                    }

                }
            });
        }
        else
        {   (findViewById(R.id.form_linearlayout)).setVisibility(View.GONE);
            pg.setVisibility(View.VISIBLE);
            pgs.setVisibility(View.VISIBLE);
            (findViewById(R.id.form_submit)).setVisibility(View.GONE);
            (findViewById(R.id.form_skip)).setVisibility(View.GONE);
             findViewById(R.id.loadTextview).setVisibility(View.VISIBLE);
             DO();
        }
    }

    //public void validate


    private void Do(final Integer i) {
        try
        {
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor e = getPrefs.edit();
            e.putInt("Language",i).apply();
            //e.putInt("Language",i).putBoolean("AtFirstRun",false).apply();
            if(new Utility(this).FirstRunActivity(context,new User(full_name,branch)))
            {
                try{
                    if(nh.isConnected())
                    {
                        db.Truncate();
                        FetchMainHymn();
                        FetchAppHymn();
                        FetchMainVerse();
                        FetchAppVerse();
                        SendPost();
                    }
                    else
                    {
                        pg.setVisibility(View.INVISIBLE);
                        pgs.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("NO INTERNET CONNECTION")
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        count = 0;
                                        DO();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.exit(0);
                                    }
                                })
                                .setCancelable(false)

                                .show();
                    }
                }
                catch(Exception ex)
                {
                    Log.i("Call Async", ex.toString());
                }
            }
        }
        catch(Exception ex)
        {
            Log.v("DoMethod",ex.toString());
        }
    }
    private void DO() {
        try
        {
            if(new Utility(this).FirstRunActivity(context,new User(full_name,branch)))
            {
                try{
                    if(nh.isConnected())
                    {
                        pg.setVisibility(View.VISIBLE);
                        pgs.setVisibility(View.VISIBLE);
                        db.Truncate();
                        FetchMainHymn();
                        FetchAppHymn();
                        FetchMainVerse();
                        FetchAppVerse();
                    }
                    else
                    {
                        pg.setVisibility(View.INVISIBLE);
                        pgs.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("NO INTERNET CONNECTION")
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        pg.setVisibility(View.VISIBLE);
                                        pgs.setVisibility(View.VISIBLE);
                                        count = 0;
                                        DO();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                       System.exit(0);
                                    }
                                })
                                .setCancelable(false)

                                .show();
                    }


                }
                catch(Exception ex)
                {
                    Log.i("Call Async",ex.toString());
                }
            }
        }
        catch(Exception ex)
        {
            Log.v("DoMethod",ex.toString());
        }
    }
    public void FetchMainHymn() {
        Log.i("MainHymn", "Attempting To Pull Main Hymn Form Server: " +  GetMainHymnList);
        jsonArrayRequestMH = new JsonArrayRequest(Request.Method.GET, GetMainHymnList, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                try{
                    Log.i("Main Hymn","Done");
                    SP(20);
                }
                catch(Exception ex)
                {
                    Log.i("VolleyGetMainHymn",ex.toString());
                    SP(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("VolleyGetMainHymn",volleyError.toString());
                SP(false);
            }


        }){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                Log.i("Response",response.data.toString());

                try {
                    String jsonString = new String(response.data, "UTF-8");
                    Log.i("Response",jsonString);
                    JSONArray jarray = new JSONArray(jsonString);
                    //DbHelper db = new DbHelper(context);
                    //db.open();
                    db.AddMainHymn(jarray);
                    //db.close();
                    Log.i("Json Response",jarray.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    SP(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    SP(false);
                }
                return super.parseNetworkResponse(response);
            }
        };
        jsonArrayRequestMH.setRetryPolicy(new DefaultRetryPolicy(
                (int) SECONDS.toMillis(20),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonArrayRequestMH);
    }
    public void FetchMainVerse() {
        Log.i("Main Verse", "Attempting To Pull Main Verse Form Server "+ GetMainVerseList);
        jsonArrayRequestMV = new JsonArrayRequest(Request.Method.GET, GetMainVerseList, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try{
                    Log.i("MainVerse", "Done");
                    SP(40);
                }
                catch(Exception ex)
                {
                    Log.i("VolleyGetMainVerse",ex.toString());
                    SP(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("VolleyGetMainVerse",volleyError.toString());
                SP(false);
            }
        }){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                Log.i("Response",response.data.toString());

                try {
                    String jsonString = new String(response.data, "UTF-8");
                    Log.i("Response",jsonString);
                    JSONArray jarray = new JSONArray(jsonString);
                    //DbHelper db = new DbHelper(context);
                    //db.open();
                    db.AddMainVerse(jarray);
                    //db.close();
                    Log.i("Json Response",jarray.toString());

                }  catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    SP(false);
                } catch (JSONException e) {
                    Log.i("JsonSend", e.toString());
                    SP(false);
                }
                return super.parseNetworkResponse(response);
            }
        };

        jsonArrayRequestMV.setRetryPolicy(new DefaultRetryPolicy(
                (int) SECONDS.toMillis(20),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonArrayRequestMV);
    }
    public void FetchAppHymn() {
        Log.i("APPHymn", "Attempting To Pull Appendix Hymn Form Server");
        jsonArrayRequestAH = new JsonArrayRequest(Request.Method.GET, GetAppHymnList, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                try{
                    Log.i("App Hymn","Done");
                    SP(10);
                }
                catch(Exception ex)
                {
                    Log.i("VolleyGetMainVerse",ex.toString());
                    SP(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("VolleyGetMainVerse",volleyError.toString());
                SP(false);
            }
        }){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                Log.i("Response",response.data.toString());

                try {
                    String jsonString = new String(response.data, "UTF-8");
                    Log.i("Response",jsonString);
                    JSONArray jarray = new JSONArray(jsonString);
                    //DbHelper db = new DbHelper(context);
                    //db.open();
                    db.AddAppHymn(jarray);
                    //db.close();
                    Log.i("Json Response",jarray.toString());

                }  catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    SP(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    SP(false);
                }
                return super.parseNetworkResponse(response);
            }
        };
        jsonArrayRequestAH.setRetryPolicy(new DefaultRetryPolicy(
                (int) SECONDS.toMillis(20),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonArrayRequestAH);
    }
    public void FetchAppVerse() {
        Log.i("APP Verse", "Attempting To Pull Appendix Verse Form Server");
        jsonArrayRequestAV = new JsonArrayRequest(Request.Method.GET, GetAppVerseList, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                try{
                    Log.i("AppVerse", "Done");
                    SP(20);
                }
                catch(Exception ex)
                {
                    Log.i("VolleyGetAppVerse",ex.toString());
                    SP(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("VolleyGetAppVerse",volleyError.toString());
                SP(false);
            }
        }){
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, "UTF-8");
                    Log.i("Response",jsonString);
                    JSONArray jarray = new JSONArray(jsonString);
                    //DbHelper db = new DbHelper(context);
                    db.AddAppVerse(jarray);
                    //db.close();
                    Log.i("Json Response",jarray.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    SP(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    SP(false);
                }
                return super.parseNetworkResponse(response);
            }
        };
        jsonArrayRequestAV.setRetryPolicy(new DefaultRetryPolicy(
                (int) SECONDS.toMillis(20),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonArrayRequestAV);
    }
    public void SendPost() throws JSONException {
        Log.i("Sending Data", "Attempting To SendInstallation to Server");
        JSONObject jb = new JSONObject();
        jb.accumulate("Name",full_name);
        jb.accumulate("Branch",branch);
        jb.accumulate("UUID",new Utility(context).AndroidUUID());
        jb.accumulate("PhoneType", new Utility(context).PhoneType());
        jb.accumulate("AndroidID", new Utility(context).AndroidID());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, PostInstallations, jb, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonArray) {
                try{
                    Log.i("SendData","success " + jsonArray.toString());
                    Log.i("SendData","Done");
                    SP(10);
                }
                catch(Exception ex)
                {
                    Log.i("VolleySendData",ex.toString());
                    SP(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("VolleySendData",volleyError.toString());
                SP(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", full_name);
                params.put("Branch",branch);
                params.put("UUID",new Utility(getApplicationContext()).AndroidUUID());
                params.put("PhoneType", new Utility(getApplicationContext()).PhoneType());
                params.put("AndroidID", new Utility(getApplicationContext()).AndroidID());

                return params;
            }
        };


        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void SP(int i) {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor e = getPrefs.edit();
        boolean ft = getPrefs.getBoolean("AtFirstRun",true);
        count  = count + i;
        pgs.setProgress(count);
        Log.i("Value of I", String.valueOf(i));
        if(count == 100 || ((count == 90) && !ft))
        {
            e.putBoolean("AtFirstRun",false).apply();
            db.close();
            pg.setVisibility(View.INVISIBLE);
            pgs.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Updated!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(FormLoadActivity.this,MainActivity.class));
            finish();
        }
    }
    private void SP(Boolean status) {
        if(!status)
        {
            if(jsonArrayRequest != null)
                jsonArrayRequest.cancel();
            if(jsonArrayRequestMH != null)
                jsonArrayRequestMH.cancel();
            if(jsonArrayRequestMV != null)
                jsonArrayRequestMV.cancel();
            if(jsonArrayRequestAH != null)
                jsonArrayRequestAH.cancel();
            if(jsonArrayRequestAV != null)
                jsonArrayRequestAV.cancel();
            if(!DialogUP)
            {
                pg.setVisibility(View.INVISIBLE);
                DialogUP = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("An Error Occured. Please check your connection")
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                count = 0;
                                DialogUP = false;
                                pg.setVisibility(View.VISIBLE);
                                DO();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.close();
                                System.exit(0);
                            }
                        })
                        .setCancelable(false)

                        .show();
                DialogUP = true;
            }
        }
    }
    public JsonArrayRequest jsonArrayRequestMH;
    public JsonArrayRequest jsonArrayRequestAH;
    public JsonArrayRequest jsonArrayRequestMV;
    public JsonArrayRequest jsonArrayRequestAV;
    public JsonObjectRequest jsonArrayRequest;
    public boolean DialogUP = false;
}

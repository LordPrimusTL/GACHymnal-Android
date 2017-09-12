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
    public String Defaulthost = "http://10.0.3.2:8000/";
    public String GetMainHymnList = "http://10.0.3.2:8000/API/getmainhymn";
    public String GetAppHymnList = "http://10.0.3.2:8000/API/getapphymn";
    public String GetMainVerseList = "http://10.0.3.2:8000/API/getmainverse";
    public String GetAppVerseList = "http://10.0.3.2:8000/API/getappverse";
    public String PostInstallations = "http://10.0.3.2:8000/API/installations";
    public String PostInstallationsAPI = "http://gacserver.000webhostapp.com/API/installations";
    public String GetMainHymnListAPI = "http://gacserver.000webhostapp.com/API/getmainhymn";
    public String GetMainVerseListAPI = "http://gacserver.000webhostapp.com/API/getmainverse";
    public String GetAppHymnListAPI = "http://gacserver.000webhostapp.com/API/getapphymn";
    public String GetAppVerseListAPI = "http://gacserver.000webhostapp.com/API/getappverse";
    public Context context;
    public NetworkHelper(Context context)
    {
        this.context = context;
    }



    public boolean GetHymns() throws IOException, JSONException,ConnectException {
        if(GetMainHymn())
        {
            if(GetMainVerse())
            {
                if(GetAppHymn())
                {
                    if(GetAppVerse())
                    {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    private boolean GetMainHymn()throws IOException, JSONException {
        try
        {
            URL url = null;
            try {
                url = new URL(GetMainHymnList);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpurl = (HttpURLConnection)url.openConnection();
            httpurl.setRequestMethod("GET");
            httpurl.connect();
            int r = httpurl.getResponseCode();
            Log.i("Response Code", String.valueOf(r));
            if(r == 200)
            {
                InputStream is = httpurl.getInputStream();

                int bytechar;
                String result="";
                while((bytechar = is.read()) != -1)
                {
                    result += (char)bytechar;
                }

                Log.i("Before json conversion",result.toString());
                JSONArray jarray = new JSONArray(result);
                Log.i("json array conversion",jarray.toString());

                JSONObject jsondata = jarray.getJSONObject(0);

                DbHelper db = new DbHelper(context);
                db.open();
                db.AddMainHymn(jarray);
                db.close();
                Log.i("json object conversion",jsondata.toString());

                Log.i("json conversion", String.valueOf(jsondata.length()));

                Log.i("json conversion", String.valueOf(jarray.length()));


                Log.i("Json Data Success", "Got the json data");
                return true;
            }
            else
            {
                Log.i("GetMainHymn", "No Response");
                return false;
            }

        }
        catch(Exception e)
        {
            Log.i("Get Main Hymn Error", e.toString());
            return false;
        }
    }
    private boolean GetMainVerse()throws IOException, JSONException {
        try
        {
            URL url = null;
            try {
                url = new URL(GetMainVerseList);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpurl = (HttpURLConnection)url.openConnection();
            httpurl.setRequestMethod("GET");
            httpurl.connect();
            int r = httpurl.getResponseCode();
            Log.i("Response Code", String.valueOf(r));
            if(r == 200)
            {
                InputStream is = httpurl.getInputStream();

                int bytechar;
                String result="";
                while((bytechar = is.read()) != -1)
                {
                    result += (char)bytechar;
                }

                Log.i("Before json conversion",result.toString());
                JSONArray jarray = new JSONArray(result);
                Log.i("json array conversion",jarray.toString());

                JSONObject jsondata = jarray.getJSONObject(0);

                DbHelper db = new DbHelper(context);
                db.open();
                db.AddMainVerse(jarray);
                db.close();
                Log.i("json object conversion",jsondata.toString());

                Log.i("json conversion", String.valueOf(jsondata.length()));

                Log.i("json conversion", String.valueOf(jarray.length()));


                Log.i("Json Data Success", "Got the json data");
                return true;
            }
            else
            {
                Log.i("GetMainHymn", "No Internet Service");
                return false;
            }

        }
        catch(Exception e)
        {
            Log.i("Get Main Hymn Error", e.toString());
            return false;
        }
    }
    private boolean GetAppHymn()throws IOException, JSONException {
        try
        {
            URL url = null;
            try {
                url = new URL(GetAppHymnList);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpurl = (HttpURLConnection)url.openConnection();
            httpurl.setRequestMethod("GET");
            httpurl.connect();
            int r = httpurl.getResponseCode();
            Log.i("Response Code", String.valueOf(r));
            if(r == 200)
            {
                InputStream is = httpurl.getInputStream();
                int bytechar;
                String result="";
                while((bytechar = is.read()) != -1)
                {
                    result += (char)bytechar;
                }

                Log.i("Before json conversion",result.toString());
                JSONArray jarray = new JSONArray(result);
                Log.i("json array conversion",jarray.toString());

                JSONObject jsondata = jarray.getJSONObject(0);

                DbHelper db = new DbHelper(context);
                db.open();
                db.AddAppHymn(jarray);
                db.close();
                Log.i("json object conversion",jsondata.toString());

                Log.i("json conversion", String.valueOf(jsondata.length()));

                Log.i("json conversion", String.valueOf(jarray.length()));


                Log.i("Json Data Success", "Got the json data");
                return true;
            }
            else
            {
                Log.i("GetAppHymn", "No Internet Service");
                return false;
            }

        }
        catch(Exception e)
        {
            Log.i("Get Main Hymn Error", e.toString());
            return false;
        }
    }
    private boolean GetAppVerse()throws IOException, JSONException {
        try
        {
            URL url = null;
            try {
                url = new URL(GetAppVerseList);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpurl = (HttpURLConnection)url.openConnection();
            httpurl.setRequestMethod("GET");
            httpurl.connect();
            int r = httpurl.getResponseCode();
            Log.i("Response Code", String.valueOf(r));
            if(r == 200)
            {
                InputStream is = httpurl.getInputStream();
                int bytechar;
                String result="";
                while((bytechar = is.read()) != -1)
                {
                    result += (char)bytechar;
                }

                Log.i("Before json conversion",result.toString());
                JSONArray jarray = new JSONArray(result);
                Log.i("json array conversion",jarray.toString());

                JSONObject jsondata = jarray.getJSONObject(0);

                DbHelper db = new DbHelper(context);
                db.open();
                db.AddAppVerse(jarray);
                db.close();
                Log.i("json object conversion",jsondata.toString());

                Log.i("json conversion", String.valueOf(jsondata.length()));

                Log.i("json conversion", String.valueOf(jarray.length()));


                Log.i("Json Data Success", "Got the json data");
                return true;
            }
            else
            {
                Log.i("GetAppHymn", "No Internet Service");
                return false;
            }

        }
        catch(Exception e)
        {
            Log.i("Get Main Hymn Error", e.toString());
            return false;
        }
    }
    public boolean PostRequest(String full_name, String branch) throws IOException {
        try
        {
            URL url = null;
            try {
                url = new URL(PostInstallations);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpurl = (HttpURLConnection)url.openConnection();
            httpurl.setRequestMethod("POST");
            httpurl.setRequestProperty("Content-Type","application/json");
            JSONObject jb = new JSONObject();
            jb.accumulate("Name",full_name);
            jb.accumulate("Branch",branch);
            jb.accumulate("UUID",new Utility(context).AndroidUUID());
            jb.accumulate("PhoneType", new Utility(context).PhoneType());
            jb.accumulate("AndroidID", new Utility(context).AndroidID());
            Log.i("Data Sending", jb.toString());
            httpurl.setDoOutput(true);
            Log.i("Data Output", "Seeeeeeeeeeeeeeeeeeeeeeee");

            DataOutputStream wr = new DataOutputStream(httpurl.getOutputStream());
            wr.writeBytes(jb.toString());
            wr.flush();
            wr.close();

            int rcode = httpurl.getResponseCode();
            Log.i("Post Respong code", String.valueOf(rcode));
            System.out.println("--------------------------------------------------------------------------------------");

            InputStream is = httpurl.getInputStream();

            int bytechar;
            String result="";
            while((bytechar = is.read()) != -1)
            {
                result += (char)bytechar;
            }

            Log.i("Before json conversion",result.toString());

            return true;

        }
        catch(Exception e)
        {
            Log.i("Errorrrrr","=============================================");
            Log.i("Errorrrrr",e.toString());
        }
        return  false;

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

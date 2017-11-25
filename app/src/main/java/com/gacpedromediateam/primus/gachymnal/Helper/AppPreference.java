package com.gacpedromediateam.primus.gachymnal.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by micheal on 11/11/2017.
 */

public class AppPreference {
    Context context;
    SharedPreferences spref;
    Gson gson = new Gson();
    public AppPreference(Context context){
        this.context = context;
        spref = context.getSharedPreferences(context.getPackageName(),context.MODE_PRIVATE);

    }

    public void setAtFirstRun(boolean value)
    {
        SharedPreferences.Editor editor = spref.edit();
        editor.putBoolean("atFirstRun", value).apply();
    }

    public boolean getAtFirstRun()
    {
        return spref.getBoolean("atFirstRun", true);
    }

    //0 for Yoruba, 1 for English
    public void setLanguage(int value)
    {
        SharedPreferences.Editor editor = spref.edit();
        editor.putInt("language", value).apply();
    }

    public int getLanguage()
    {
        return spref.getInt("language",1);
    }


    public String getMainHymn()
    {
        return spref.getString("mainHymn", null);
    }

    public void setMainHymn(String value){
        SharedPreferences.Editor editor = spref.edit();
        editor.putString("mainHymn", value).apply();
    }

    public String getAppHymn()
    {
        return spref.getString("appHymn", null);
    }

    public void setAppHymn(String value){
        SharedPreferences.Editor editor = spref.edit();
        editor.putString("appHymn", value).apply();
    }

    public String getMainVerse()
    {
        return spref.getString("mainVerse", null);
    }

    public void setMainVerse(String value){
        SharedPreferences.Editor editor = spref.edit();
        editor.putString("mainVerse", value).apply();
    }
    public String getAppVerse()
    {
        return spref.getString("appVerse", null);
    }

    public void setAppVerse(String value){
        SharedPreferences.Editor editor = spref.edit();
        editor.putString("appVerse", value).apply();
    }

    public void setSentDetails(boolean value)
    {
        SharedPreferences.Editor editor = spref.edit();
        editor.putBoolean("sentDetails", value).apply();
    }

    public boolean getSentDetails()
    {
        return spref.getBoolean("sentDetails", false);
    }
}

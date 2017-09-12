package com.gacpedromediateam.primus.gachymnal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.gacpedromediateam.primus.gachymnal.Activity.IntroActivity;
import com.gacpedromediateam.primus.gachymnal.Activity.MainActivity;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new LongOperation().execute();
    }


    class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep((int) TimeUnit.SECONDS.toMillis(3));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean isFirstRun = getPrefs.getBoolean("AtFirstRun",true);
            if(isFirstRun)
            {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}

package com.gacpedromediateam.primus.gachymnal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Activity.MainActivity;
import com.gacpedromediateam.primus.gachymnal.Helper.AppPreference;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Hymn;
import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Utility;
import com.gacpedromediateam.primus.gachymnal.Helper.verse;
import com.gacpedromediateam.primus.gachymnal.Http.RetrofitClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    AppPreference appPreference;
    Context context;
    String TAG = "SplashActivity";
    CoordinatorLayout cord;
    RetrofitClient retrofitClient;
    ProgressDialog pBar;
    DbHelper db;
    NetworkHelper nh;
    boolean request = false;
    int dataCount = 0;
    Utility util;
    boolean error = false;
    boolean dialogShown = false;
    boolean shown;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = new AppPreference(this);
        context = SplashActivity.this;
        cord = findViewById(R.id.splash_layout);
        pBar = new ProgressDialog(context);
        pBar.setMessage("Please Wait...");
        db = new DbHelper(context);
        nh = new NetworkHelper(context);
        util = new Utility(this);
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, util.AndroidUUID());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, util.PhoneType());
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            Thread.sleep((int) TimeUnit.SECONDS.toMillis(3));
            new LongOperation().execute();
            Log.e(TAG, "onCreate: Sleep Finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void selectLanguage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final BottomSheetDialog mBottomSheet = new BottomSheetDialog(SplashActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.user_lang_select, null);
                mBottomSheet.setContentView(sheetView);
                LinearLayout english = sheetView.findViewById(R.id.english_lang);
                LinearLayout yoruba = sheetView.findViewById(R.id.yoruba_lang);
                english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appPreference.setLanguage(1);
                        mBottomSheet.dismiss();
                        if(!request)
                            pBar.show();
                        getHymnFromServer();
                    }
                });

                yoruba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appPreference.setLanguage(0);
                        mBottomSheet.dismiss();
                        if(!request)
                            pBar.show();
                        getHymnFromServer();
                    }
                });
                mBottomSheet.setCanceledOnTouchOutside(false);
                mBottomSheet.setCancelable(false);
                mBottomSheet.show();
                shown = true;
            }
        });
    }

    private void getHymnFromServer() {
        //pBar.show();
        try{
            if(nh.isConnected())
            {
                db.open().Truncate();
                Log.e(TAG, "getHymnFromServer: truncate");
                callGetMainHymn();
                callGetAppHymn();
                callGetMainVerse();
                callGetAppVerse();
                callPostApi();
                Log.e(TAG, "getHymnFromServer: Main called");

            }
            else{
                if(pBar.isShowing())
                {
                    pBar.dismiss();
                }
                showAlert("No Internet");
            }
        }catch (Exception ex){
            FirebaseCrash.logcat(Log.ERROR, TAG, ex.toString());
            FirebaseCrash.report(ex);
        }
    }
    public void showAlert(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(pBar.isShowing())
                {
                    pBar.dismiss();
                }
                if(!dialogShown)
                {
                    AlertDialog.Builder ad = new AlertDialog.Builder(SplashActivity.this);
                    ad.setTitle("Error")
                            .setMessage(message)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialogShown = false;
                                    System.exit(0);
                                }
                            }).setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pBar.show();
                            dialogShown = false;
                            getHymnFromServer();
                        }
                    }).show();
                    dialogShown = true;
                }
                //getHymnFromServer();

            }
        });
    }


    private void callGetMainHymn() {
        retrofitClient = new RetrofitClient(context,RetrofitClient.Defaulthost);
        retrofitClient.getApiService().getMainHymn()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Hymn>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        error = true;
                        showAlert("No Internet!");
                        Log.e("Error",throwable.getMessage().toString());
                        FirebaseCrash.report(throwable);
                    }

                    @Override
                    public void onNext(final List<Hymn> hymns) {
                        if(hymns.size() ==  400)
                            appPreference.setMainHymn(new Gson().toJson(hymns));

                        db.addMainHymnList(hymns);
                        checkCount();
                        Log.e(TAG, "onNext: Main Hymn Thread Started");
                        pBar.dismiss();
                    }
                });
    }
    private void callGetAppHymn() {
        retrofitClient = new RetrofitClient(context,RetrofitClient.Defaulthost);
        retrofitClient.getApiService().getAppHymn()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Hymn>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        error = true;
                        showAlert("No Internet");
                        Log.e("Error",throwable.getMessage().toString());
                        FirebaseCrash.report(throwable);

                    }

                    @Override
                    public void onNext(final List<Hymn> hymns) {
                        if(hymns.size() ==  100)
                            appPreference.setAppHymn(new Gson().toJson(hymns));

                        db.addAppHymnList(hymns);
                        checkCount();
                        Log.e(TAG, "onNext: App Hymn Thread Started");
                        //pBar.dismiss();
                    }
                });
    }
    private void callGetMainVerse() {
        retrofitClient = new RetrofitClient(context, RetrofitClient.Defaulthost);
        retrofitClient.getApiService().getMainVerse()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<verse>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: Completed Mainverse");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        error = true;
                        showAlert("No Internet");
                        Log.e(TAG, "onError: Main verse + " + throwable);
                        FirebaseCrash.report(throwable);
                    }

                    @Override
                    public void onNext(final List<verse> verses) {
                        db.addMainVerse(verses);
                        checkCount();
                        Log.e(TAG, "onNext: main Verse Thread Started");

                    }
                });
    }
    private void callGetAppVerse() {
        retrofitClient = new RetrofitClient(context, RetrofitClient.Defaulthost);
        retrofitClient.getApiService().getAppVerse()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<verse>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: Completed Mainverse");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        showAlert("No Internet");
                        Log.e(TAG, "onError: App verse + " + throwable);
                        FirebaseCrash.report(throwable);
                    }

                    @Override
                    public void onNext(final List<verse> verses) {
                        db.addAppVerse(verses);
                        checkCount();
                        Log.e(TAG, "onNext: App Verse Thread Start");
                    }
                });
    }
    private void callPostApi() {
        retrofitClient = new RetrofitClient(context,RetrofitClient.Defaulthost);
        retrofitClient.getApiService().postInstallations(util.AndroidUUID(), util.PhoneType(), util.AndroidID())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        //showAlert(throwable.toString());
                        Log.e(TAG, "onError: +" + throwable.toString());
                        FirebaseCrash.report(throwable);
                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("1")) {
                            appPreference.setSentDetails(true);
                        }
                        Log.e(TAG, "onNext: Post" + s );
                    }
                });
    }
    private void checkCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataCount++;
                if(dataCount == 4)
                {
                    request = true;
                    pBar.dismiss();
                    db.close();
                    appPreference.setAtFirstRun(false);
                    Toast.makeText(context, "Completed!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                    finish();

                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        if(pBar != null)
            pBar.dismiss();
        super.onBackPressed();

    }


    private class LongOperation extends AsyncTask<String, Void, String> {
         @Override
         protected String doInBackground(String... params) {
            try {
                Thread.sleep((int) TimeUnit.SECONDS.toMillis(3));
                if(appPreference.getAtFirstRun()){
                    selectLanguage();
                }else
                {
                    if(!appPreference.getSentDetails())
                    {
                        if(nh.isConnected())
                        {
                            callPostApi();
                        }
                    }
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

                 @Override
         protected void onPostExecute(String result) {}

                 @Override
         protected void onPreExecute() {}

                 @Override
         protected void onProgressUpdate(Void... values) {}
     }
}

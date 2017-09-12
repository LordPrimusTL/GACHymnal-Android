package com.gacpedromediateam.primus.gachymnal.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gacpedromediateam.primus.gachymnal.Fragments.MainFragment;
import com.gacpedromediateam.primus.gachymnal.Fragments.AppFragment;
import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Utility;
import com.gacpedromediateam.primus.gachymnal.R;
import com.gacpedromediateam.primus.gachymnal.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MainActivity extends AppCompatActivity {
    public Context context = this;
    NetworkHelper nh = new NetworkHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isFirstRun = getPrefs.getBoolean("AtFirstRun",true);
        if(isFirstRun)
        {
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
        }
        else
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            toolbar.setTitle("GAC Hymn Book");
            //toolbar.setLogo(R.drawable.download2png);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                //VectorDrawableCompat indicator
                //      = VectorDrawableCompat.create(getResources(), R.drawable.ic_action_nam, getTheme());
                //indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
                //supportActionBar.setHomeAsUpIndicator(indicator);
                //supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle("GAC E-Hymn");
            }
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            DoVoid(getPrefs.getInt("Language",1));

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    final SharedPreferences.Editor e = getPrefs.edit();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Change Default Language")
                            .setTitle("Language Change")
                            .setPositiveButton("English", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    e.putInt("Language",1).apply();
                                    Snackbar.make(view, "English Language", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    DoVoid(1);
                                }
                            })
                            .setNegativeButton("Yoruba", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    e.putInt("Language",0).apply();
                                    Snackbar.make(view, "Ede Yoruba", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    DoVoid(0);
                                }
                            })
                            .setCancelable(false)

                            .show();
                }
            });
        }

    }

    private void DoVoid(int language) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager,language);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager, int Language) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        if(Language == 1)
        {
            adapter.addFragment(new MainFragment(), "Main");
            adapter.addFragment(new AppFragment(), "Appendix");
        }

        if(Language  == 0)
        {
            adapter.addFragment(new MainFragment(), "Iwe Orin");
            adapter.addFragment(new AppFragment(), "Akokun");
        }

        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you wan to check for update?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this,FormLoadActivity.class));

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)

                        .show();
                //Toast.makeText(this,"You selected Refresh",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_feedback:
                //Toast.makeText(this,"You selected Feedback",Toast.LENGTH_SHORT).show();
                if(nh.isConnected()) {
                    startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                }
                else{
                    Toast.makeText(this,"No internet connection, Please try again",Toast.LENGTH_LONG).show();
                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }

        //noinspection SimplifiableIfStatement
    }


    public StringRequest StringRequestGU;
    public String GetUpdateKey = "http://10.0.2.2:8000/API/getUpdateKey";
}

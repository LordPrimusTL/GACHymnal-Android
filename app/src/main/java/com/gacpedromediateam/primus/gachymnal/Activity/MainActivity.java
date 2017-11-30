package com.gacpedromediateam.primus.gachymnal.Activity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.LinearLayout;
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
import com.gacpedromediateam.primus.gachymnal.Helper.AppPreference;
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
    AppPreference appPreference;
    CoordinatorLayout cord;
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cord = (CoordinatorLayout) findViewById(R.id.main_content);
        appPreference = new AppPreference(MainActivity.this);
        toolbar.setTitle("    GAC Hymnal");
        ActionBar supportActionBar = getSupportActionBar();
        language = appPreference.getLanguage();
        if (supportActionBar != null) {
            supportActionBar.setTitle("      GAC Hymnal");
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final BottomSheetDialog mBottomSheet = new BottomSheetDialog(MainActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.user_lang_select, null);
                mBottomSheet.setContentView(sheetView);
                LinearLayout english = sheetView.findViewById(R.id.english_lang);
                LinearLayout yoruba = sheetView.findViewById(R.id.yoruba_lang);
                english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appPreference.setLanguage(1);
                        language = 1;
                        mBottomSheet.dismiss();
                        DoVoid();

                    }
                });
                yoruba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appPreference.setLanguage(0);
                        language = 0;
                        mBottomSheet.dismiss();
                        DoVoid();
                    }
                });
                mBottomSheet.show();
            }
        });

        DoVoid();


    }

    private void DoVoid() {
        ViewPager viewPager = findViewById(R.id.container);
        setupViewPager(viewPager,language);
        // Set Tabs inside Toolbar
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager, int language) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        if(language == 1)
        {
            adapter.addFragment(new MainFragment(), "Main");
            adapter.addFragment(new AppFragment(), "Appendix");
        }

        if(language  == 0)
        {
            adapter.addFragment(new MainFragment(), "Iwe Orin");
            adapter.addFragment(new AppFragment(), "Akokun");
        }

        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {
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
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}

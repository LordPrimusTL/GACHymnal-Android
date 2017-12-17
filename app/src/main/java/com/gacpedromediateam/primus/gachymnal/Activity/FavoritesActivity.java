package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.gacpedromediateam.primus.gachymnal.Fragments.AppFragment;
import com.gacpedromediateam.primus.gachymnal.Fragments.MainFragment;
import com.gacpedromediateam.primus.gachymnal.Helper.AppPreference;
import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    public Context context = this;
    NetworkHelper nh = new NetworkHelper(this);
    AppPreference appPreference;
    CoordinatorLayout cord;
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        cord = (CoordinatorLayout) findViewById(R.id.fmain_content);
        appPreference = new AppPreference(FavoritesActivity.this);
        toolbar.setTitle("    GAC Hymnal");
        ActionBar supportActionBar = getSupportActionBar();
        language = appPreference.getLanguage();
        if (supportActionBar != null) {
            supportActionBar.setTitle("      Favorites");
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        FloatingActionButton fab = findViewById(R.id.ffab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final BottomSheetDialog mBottomSheet = new BottomSheetDialog(FavoritesActivity.this);
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
        ViewPager viewPager = findViewById(R.id.fcontainer);
        setupViewPager(viewPager,language);
        // Set Tabs inside Toolbar
        TabLayout tabs = findViewById(R.id.ftabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager, int language) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        if(language == 1)
        {
            adapter.addFragment(new MainFragment("fave"), "Main");
            adapter.addFragment(new AppFragment("fave"), "Appendix");
        }

        if(language  == 0)
        {
            adapter.addFragment(new MainFragment("fave"), "Iwe Orin");
            adapter.addFragment(new AppFragment("fave"), "Akokun");
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

}

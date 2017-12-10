package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Adapters.HymnViewAdapter;
import com.gacpedromediateam.primus.gachymnal.Helper.AppPreference;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Hymn;
import com.gacpedromediateam.primus.gachymnal.Helper.verse;
import com.gacpedromediateam.primus.gachymnal.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;

public class ViewActivity extends AppCompatActivity {
    public String ID;
    public String Title;
    public String HymnType;
    public Integer language;
    public ZoomView zoomView;
    AppPreference appPreference;
    String TAG = "ViewActivity";
    CoordinatorLayout cord;
    private Hymn payload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        appPreference = new AppPreference(ViewActivity.this);
        cord = findViewById(R.id.view_container);
        Toolbar toolbar = findViewById(R.id.vhtoolbar);
        toolbar.setTitle("Am Here");
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_hymn_layout, null, false);
        v.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        zoomView = new ZoomView(this);
        zoomView.addView(v);

        RelativeLayout main_container =  findViewById(R.id.relativezoomId);
        main_container.addView(zoomView);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        language = appPreference.getLanguage();

        if(getIntent().getExtras()!= null)
        {
            //Log.e(TAG, "onCreate: " +getIntent().getStringExtra("Hymn"));
            if(getIntent().getExtras().containsKey("title") || getIntent().getExtras().containsKey("hymnType") || getIntent().getExtras().containsKey("HymnID") )
            {
                ID = getIntent().getStringExtra("HymnID");
                Title = getIntent().getStringExtra("title");
                HymnType = getIntent().getStringExtra("hymnType");

                payload = new Gson().fromJson(getIntent().getStringExtra("Hymn"), Hymn.class);
            }
            else
            {
                Toast.makeText(this, "Error receiving data, Please restart app", Toast.LENGTH_SHORT).show();
            }

        }

        if (supportActionBar != null) {
            if(Integer.parseInt(HymnType) == 0)
            {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle((language == 0 ? "Iwe Orin " : "Hymn ") + ID);
            }
            if(Integer.parseInt(HymnType) == 1)
            {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle((language == 0 ? "Akokun " : "Appendix ") + ID);
            }

        }

        populateList();
    }

    private void populateList() {

        if (getSupportActionBar() != null) {
            if(Integer.parseInt(HymnType) == 0)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle((language == 0 ? "Iwe Orin " : "Hymn ") + ID);
            }
            if(Integer.parseInt(HymnType) == 1)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle((language == 0 ? "Akokun " : "Appendix ") + ID);
            }

        }
        //Log.e(TAG, "populateList: "+ payload);
        ((TextView)findViewById(R.id.viewHymnTitle)).setText(language == 0 ? payload.getYoruba() : payload.getEnglish());
        ArrayList<verse> GetVerse = GetVerse(ID);
        ListView listView = findViewById(R.id.view_hymn_list);
        listView.setAdapter(new HymnViewAdapter(this,GetVerse,language));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    private ArrayList<verse> GetVerse(String id) {
        ArrayList<verse> verseChars = new ArrayList<>();
        DbHelper db = new DbHelper(this);
        db.open();
        Cursor res = db.GetVerse(id,HymnType);
        if(res == null)
        {
            Toast.makeText(this,"{No Data}",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        else
        {

            while(res.moveToNext()) {
                verseChars.add(new verse(res.getInt(1), res.getInt(2), res.getString(3),String.valueOf(res.getString(4))));
            }

            //Log.e(TAG, "GetVerse: " + verseChars);
            ((TextView)findViewById(R.id.AmenAmin)).setText(language == 0 ? "Amin." : "Amen.");

        }
        return verseChars;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_lang:
                final BottomSheetDialog mBottomSheet = new BottomSheetDialog(ViewActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.user_lang_select, null);
                mBottomSheet.setContentView(sheetView);
                LinearLayout english = sheetView.findViewById(R.id.english_lang);
                LinearLayout yoruba = sheetView.findViewById(R.id.yoruba_lang);
                english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = 1;
                        appPreference.setLanguage(1);
                        mBottomSheet.dismiss();
                        populateList();

                    }
                });
                yoruba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = 0;
                        appPreference.setLanguage(0);
                        mBottomSheet.dismiss();
                        populateList();
                    }
                });
                mBottomSheet.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

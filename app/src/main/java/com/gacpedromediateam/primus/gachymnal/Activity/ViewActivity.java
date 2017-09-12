package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Adapters.HymnViewAdapter;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.verse;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;

import static com.gacpedromediateam.primus.gachymnal.R.id.fab;

public class ViewActivity extends AppCompatActivity {
    public String ID;
    public String Title;
    public String HymnType;
    public Integer Language;
    public ZoomView zoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.vhtoolbar);
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_hymn_layout, null, false);
        v.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        zoomView = new ZoomView(this);
        zoomView.addView(v);

        RelativeLayout main_container = (RelativeLayout) findViewById(R.id.relativezoomId);
        main_container.addView(zoomView);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Language = getPrefs.getInt("Language",1);

        if(getIntent().getExtras()!= null)
        {
            if(getIntent().getExtras().containsKey("title") || getIntent().getExtras().containsKey("hymnType") || getIntent().getExtras().containsKey("HymnID") )
            {
                ID = getIntent().getStringExtra("HymnID");
                Title = getIntent().getStringExtra("title");
                HymnType = getIntent().getStringExtra("hymnType");
            }
            else
            {
                Toast.makeText(this, "Error receiving data, Please restart app", Toast.LENGTH_SHORT);
            }

        }

        if (supportActionBar != null) {
            //VectorDrawableCompat indicator
            //      = VectorDrawableCompat.create(getResources(), R.drawable.ic_action_nam, getTheme());
            //indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            //supportActionBar.setHomeAsUpIndicator(indicator);
            if(Integer.parseInt(HymnType) == 0)
            {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle("Hymn " + ID);
            }
            if(Integer.parseInt(HymnType) == 1)
            {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle("Appendix " + ID);
            }

        }

        ((TextView)findViewById(R.id.viewHymnTitle)).setText(Title);
        ArrayList<verse> GetVerse = GetVerse(ID);
        ListView listView = (ListView)findViewById(R.id.view_hymn_list);
        listView.setAdapter(new HymnViewAdapter(this,GetVerse));
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
            if(Language == 1)
            {
                while(res.moveToNext()) {
                    verseChars.add(new verse((res.getInt(2)), String.valueOf(res.getString(3))));
                }

                ((TextView)findViewById(R.id.AmenAmin)).setText("Amen.");

            }

            if(Language == 0)
            {
                while(res.moveToNext()) {
                    verseChars.add(new verse((res.getInt(2)), String.valueOf(res.getString(4))));
                }
                ((TextView)findViewById(R.id.AmenAmin)).setText("Amin.");
            }
        }
        return verseChars;
    }


}

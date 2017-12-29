package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Adapters.ContributorAdapter;
import com.gacpedromediateam.primus.gachymnal.FeedbackActivity;
import com.gacpedromediateam.primus.gachymnal.Helper.NetworkHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.Team;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;
import java.util.List;

public class ContributorActivity extends AppCompatActivity {

    public List<Team> teams;
    String TAG = "Contributor";
    ContributorAdapter adapter;
    NetworkHelper nh = new NetworkHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contributors");
        teams = new ArrayList<>();
        teams.add(new Team(1,"Abimbola Akinwonmi - Director"));
        teams.add(new Team(2,"Micheal Akinwonmi"));
        teams.add(new Team(3,"Alonge Victor"));
        teams.add(new Team(4,"Onalaja Olamide"));
        teams.add(new Team(5,"Oreoluwa Osundina"));
        Log.e(TAG, "onCreate: " + teams);
        adapter = new ContributorAdapter(this, teams);
        final ListView listView = findViewById(R.id.contri_view);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_fave:
                startActivity(new Intent(ContributorActivity.this, FavoritesActivity.class));
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                return true;
            case R.id.action_feedback:
                if(nh.isConnected()){
                    startActivity(new Intent(ContributorActivity.this, FeedbackActivity.class));
                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }else{
                    Toast.makeText(this,"No internet connection, Please try again", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.gacpedromediateam.primus.gachymnal.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Activity.ViewActivity;
import com.gacpedromediateam.primus.gachymnal.Adapters.AppListAdapter;
import com.gacpedromediateam.primus.gachymnal.Adapters.MainListAdapter;
import com.gacpedromediateam.primus.gachymnal.Adapters.SearchAdapter;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.hymn;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public Integer Language;
    public MainListAdapter adapter;
    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Language = getPrefs.getInt("Language",1);
        ArrayList<hymn> GetHymns = GetHymnList(Language);
        view = inflater.inflate(R.layout.fragment_main_hymn,container,false);
        final ListView listView  = view.findViewById(R.id.main_list_view);

                //final ListView listView = (ListView) view.findViewById(R.id.main_list_view);
        adapter = new MainListAdapter(this.getActivity(), GetHymns);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                hymn fullObject = (hymn) o;
                Log.i("Hymn Details", fullObject.getTitle() + String.valueOf(fullObject.getID()));
                //Toast.makeText(getActivity(), "You have chosen: " + " " + fullObject.getID(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), ViewActivity.class)
                        .putExtra("HymnID",String.valueOf(fullObject.getID()))
                        .putExtra("title",fullObject.getTitle())
                        .putExtra("hymnType",String.valueOf(0)));
            }
        });
        SearchView inputText = view.findViewById(R.id.search_view);
        inputText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return view;
    }

    private ArrayList<hymn> GetHymnList(Integer Language) {
        ArrayList<hymn> hymnChars = new ArrayList<>();
        DbHelper db = new DbHelper(getActivity());
        db.open();
        Cursor res = db.GetAllMainHymnList();
        if(res == null)
        {
            Toast.makeText(this.getActivity(),"{No Data}", Toast.LENGTH_LONG).show();
            return  null;
        }
        else{
            if(Language == 1)
            {
                while(res.moveToNext()) {
                    hymnChars.add(new hymn(res.getInt(1), res.getString(2)));
                }

            }

            if(Language == 0)
            {
                while(res.moveToNext()) {
                    hymnChars.add(new hymn(res.getInt(1), res.getString(3)));
                }
            }
            return hymnChars;
        }

    }

}

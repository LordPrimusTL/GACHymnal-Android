package com.gacpedromediateam.primus.gachymnal.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.gacpedromediateam.primus.gachymnal.Activity.ViewActivity;
import com.gacpedromediateam.primus.gachymnal.Adapters.MainListAdapter;
import com.gacpedromediateam.primus.gachymnal.Helper.AppPreference;
import com.gacpedromediateam.primus.gachymnal.Helper.DbHelper;
import com.gacpedromediateam.primus.gachymnal.Helper.hymn;
import com.gacpedromediateam.primus.gachymnal.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public Integer language;
    public MainListAdapter adapter;
    AppPreference appPreference;
    String TAG = "MainFragment";
    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appPreference = new AppPreference(getContext());
        View view = null;
        language = appPreference.getLanguage();
        //Log.e(TAG, "onCreateView: " + appPreference.getLanguage());
        ArrayList<hymn> GetHymns = GetHymnList();
        view = inflater.inflate(R.layout.fragment_main_hymn,container,false);
        final ListView listView  = view.findViewById(R.id.main_list_view);

                //final ListView listView = (ListView) view.findViewById(R.id.main_list_view);
        adapter = new MainListAdapter(this.getActivity(), GetHymns, language);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                hymn fullObject = (hymn) o;
                Log.i("Hymn Details", fullObject.getTitle() + String.valueOf(fullObject.getID()));
                //Toast.makeText(getActivity(), "You have chosen: " + " " + fullObject.getID(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), ViewActivity.class)
                        .putExtra("hymn", new Gson().toJson(fullObject))
                        .putExtra("HymnID",String.valueOf(fullObject.getID()))
                        .putExtra("title",fullObject.getTitle())
                        .putExtra("hymnType",String.valueOf(0)));
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);

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

    private ArrayList<hymn> GetHymnList() {
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
            while(res.moveToNext()) {
                hymnChars.add(new hymn(res.getInt(1), res.getString(2), res.getString(3)));
            }
            return hymnChars;
        }

    }

}

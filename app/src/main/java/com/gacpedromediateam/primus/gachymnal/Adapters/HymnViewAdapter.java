package com.gacpedromediateam.primus.gachymnal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gacpedromediateam.primus.gachymnal.Helper.verse;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;

/**
 * Created by Primus on 7/11/2017.
 */

public class HymnViewAdapter extends BaseAdapter {
    private static ArrayList<verse> verseChar;

    private LayoutInflater mInflater;

    public HymnViewAdapter(Context context, ArrayList<verse> results) {
        verseChar = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return verseChar.size();
    }

    public Object getItem(int position) {
        return verseChar.get(position);
    }

    public long getItemId(int position) {
        return verseChar.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_hymn_row, null);
            holder = new ViewHolder();
            holder.ID = (TextView) convertView.findViewById(R.id.VerseID);
            holder.Title = (TextView) convertView
                    .findViewById(R.id.HymnVerse);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ID.setText(String.valueOf(verseChar.get(position).getVerse_id()));
        holder.Title.setText(verseChar.get(position).getWord());

        return convertView;
    }

    static class ViewHolder {
        TextView ID;
        TextView Title;
    }
}

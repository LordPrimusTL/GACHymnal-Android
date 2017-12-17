package com.gacpedromediateam.primus.gachymnal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gacpedromediateam.primus.gachymnal.Helper.Hymn;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;

/**
 * Created by Primus on 7/15/2017.
 */

public class SearchAdapter extends BaseAdapter implements Filterable{
    private static ArrayList<Hymn> hymnChar;
    private static ArrayList<Hymn> filteredData;
    private LayoutInflater mInflater;
    private HymnFilter mFilter;

    public SearchAdapter(Context context, ArrayList<Hymn> results) {
        this.hymnChar = results;
        this.mInflater = LayoutInflater.from(context);
        this.filteredData = results;
    }

    public int getCount() {
        return hymnChar.size();
    }

    public Object getItem(int position) {
        return hymnChar.get(position);
    }

    public long getItemId(int position) {
        return hymnChar.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_row, null);
            holder = new MainListAdapter.ViewHolder();
            holder.ID = convertView.findViewById(R.id.SearchTxtID);
            holder.Title =  convertView
                    .findViewById(R.id.SearchTxtTitle);
            convertView.setTag(holder);
        } else {
            holder = (MainListAdapter.ViewHolder) convertView.getTag();
        }
        holder.ID.setText(String.valueOf(hymnChar.get(position).getID()));
        holder.Title.setText(hymnChar.get(position).getTitle());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(mFilter == null){
            mFilter = new HymnFilter();
        }
        return mFilter;
    }

    static class ViewHolder {
        TextView ID;
        TextView Title;
    }

    private class HymnFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0)
            {
                ArrayList<Hymn> filterList = new ArrayList<>();
                for(int i = 0; i < filteredData.size(); i++)
                {
                    if((filteredData.get(i).getTitle().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (String.valueOf(filteredData.get(i).getID()).toUpperCase()).contains(constraint.toString().toUpperCase())){
                        Hymn hh = new Hymn(filteredData.get(i).getID(),filteredData.get(i).getEnglish(), filteredData.get(i).getYoruba(),filteredData.get(i).getFave());
                        filterList.add(hh);
                    }
                }

                results.count = filterList.size();
                results.values = filterList;
            }
            else {
                results.count = filteredData.size();
                results.values = filteredData;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            hymnChar = (ArrayList<Hymn>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

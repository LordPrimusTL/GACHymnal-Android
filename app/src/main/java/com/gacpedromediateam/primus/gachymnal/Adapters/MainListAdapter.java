package com.gacpedromediateam.primus.gachymnal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gacpedromediateam.primus.gachymnal.Helper.hymn;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.ArrayList;

/**
 * Created by Primus on 7/10/2017.
 */

public class MainListAdapter extends BaseAdapter implements Filterable{
    private static ArrayList<hymn> hymnChar;
    private static ArrayList<hymn> filteredData;
    private LayoutInflater mInflater;
    private HymnFilter mFilter;
    private Integer lang;


    public MainListAdapter(Context context, ArrayList<hymn> results, Integer lang) {
        this.hymnChar = results;
        this.mInflater = LayoutInflater.from(context);
        this.lang = lang;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.main_hymn_row, null);
            holder = new ViewHolder();
            holder.ID = (TextView) convertView.findViewById(R.id.txtID);
            holder.Title = (TextView) convertView
                    .findViewById(R.id.txtTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(this.lang == 1){
            holder.ID.setText(String.valueOf(hymnChar.get(position).getID()));
            holder.Title.setText(hymnChar.get(position).getEnglish());
        }else{
            holder.ID.setText(String.valueOf(hymnChar.get(position).getID()));
            holder.Title.setText(hymnChar.get(position).getYoruba());
        }

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
                ArrayList<hymn> filterList = new ArrayList<>();
                for(int i = 0; i < filteredData.size(); i++)
                {
                    if(lang == 1){
                        if((filteredData.get(i).getEnglish().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                                (String.valueOf(filteredData.get(i).getID()).toUpperCase()).contains(constraint.toString().toUpperCase())){
                            hymn hh = new hymn(filteredData.get(i).getID(),filteredData.get(i).getEnglish(), filteredData.get(i).getYoruba());
                            filterList.add(hh);
                        }
                    }else{
                        if((filteredData.get(i).getYoruba().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                                (String.valueOf(filteredData.get(i).getID()).toUpperCase()).contains(constraint.toString().toUpperCase())){
                            hymn hh = new hymn(filteredData.get(i).getID(),filteredData.get(i).getEnglish(), filteredData.get(i).getYoruba());
                            filterList.add(hh);
                        }
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
            hymnChar = (ArrayList<hymn>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}

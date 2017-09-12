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

public class AppListAdapter extends BaseAdapter implements Filterable{
    private static ArrayList<hymn> hymnChar;
    private static ArrayList<hymn> filteredData;
    private LayoutInflater mInflater;
    private HymnFilter mFilter;

    public AppListAdapter(Context context, ArrayList<hymn> results) {
        hymnChar = results;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.appendix_hymn_row, null);
            holder = new ViewHolder();
            holder.ID = (TextView) convertView.findViewById(R.id.AID);
            holder.Title = (TextView) convertView
                    .findViewById(R.id.ATitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
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

    private class HymnFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<hymn> filterList = new ArrayList<>();
                for (int i = 0; i < filteredData.size(); i++) {
                    if ((filteredData.get(i).getTitle().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (String.valueOf(filteredData.get(i).getID()).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        hymn hh = new hymn(filteredData.get(i).getID(), filteredData.get(i).getTitle());
                        filterList.add(hh);
                    }
                }

                results.count = filterList.size();
                results.values = filterList;
            } else {
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

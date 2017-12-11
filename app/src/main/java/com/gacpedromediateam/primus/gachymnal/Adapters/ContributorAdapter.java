package com.gacpedromediateam.primus.gachymnal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gacpedromediateam.primus.gachymnal.Helper.Team;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.List;

/**
 * Created by micheal on 10/12/2017.
 */

public class ContributorAdapter extends BaseAdapter {
    private List<Team> teams;
    private Context context;
    private LayoutInflater mInflater;


    public ContributorAdapter(Context context, List<Team> teams){
        this.context = context;
        this.teams = teams;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return teams.size();
    }

    @Override
    public Object getItem(int position) {
        return teams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return teams.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contri_view, null);
            holder = new ViewHolder();
            //holder.ID = convertView.findViewById(R.id.cId);
            holder.Name = convertView
                    .findViewById(R.id.cName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.ID.setText("");
        //holder.ID.setText(String.valueOf(teams.get(position).getId()));
        holder.Name.setText(teams.get(position).getName());

        return convertView;    }

    static class ViewHolder {
        TextView ID;
        TextView Name;
    }
}

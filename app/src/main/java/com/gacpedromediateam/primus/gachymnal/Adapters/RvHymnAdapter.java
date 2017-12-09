package com.gacpedromediateam.primus.gachymnal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gacpedromediateam.primus.gachymnal.Helper.Hymn;
import com.gacpedromediateam.primus.gachymnal.R;

import java.util.List;

/**
 * Created by micheal on 09/12/2017.
 */

public class RvHymnAdapter extends RecyclerView.Adapter<RvHymnAdapter.ViewHolder> {

    Context context;
    List<Hymn> hymns;
    int lang;
    public  String TAG = "RVB";
    public RvHymnAdapter(Context context, List<Hymn> hymns, int lang){
        this.context = context;
        this.hymns = hymns;
        this.lang = lang;
    }
    @Override
    public RvHymnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.rv_hymn_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvHymnAdapter.ViewHolder viewHolder, final int i) {
        Hymn hymn = hymns.get(i);
        Log.e(TAG, "onBindViewHolder: " + hymn);
        viewHolder.hymn_id.setText(String.valueOf(hymn.getID()));
        viewHolder.title.setText(lang == 1 ? hymn.getEnglish() : hymn.getYoruba());
        if(hymn.getFave() == 1){
            viewHolder.fave.setImageResource(R.drawable.ic_action_fave);
        }else{
            viewHolder.fave.setImageResource(R.drawable.ic_action_no_fave);
            viewHolder.fave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: " + i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return hymns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hymn_id, title;
        public ImageView fave;
        public ViewHolder(View itemView) {
            super(itemView);
            hymn_id =itemView.findViewById(R.id.txtRID);
            title = itemView.findViewById(R.id.txtRTitle);
            fave = itemView.findViewById(R.id.imgFave);
        }
    }
}

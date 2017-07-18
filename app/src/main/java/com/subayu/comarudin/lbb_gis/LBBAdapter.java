package com.subayu.comarudin.lbb_gis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by black4v on 02/06/2017.
 */

public class LBBAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    RelativeLayout rr;
    private List<DataLBB> data= Collections.emptyList(),filterList;
    DataLBB current;
    int currentPos=0;

    public LBBAdapter(Context context, List<DataLBB> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.filterList = new ArrayList<DataLBB>();
        this.filterList.addAll(this.data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_lbb, parent,false);
        LBBAdapter.MyHolder holder = new LBBAdapter.MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        LBBAdapter.MyHolder myHolder= (LBBAdapter.MyHolder) holder;
        current = filterList.get(position);
        myHolder.nama.setText(current.nama);
        myHolder.alamat.setText(current.alamat);
        myHolder.notel.setText(current.notel);
        myHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataLBB db = new DataLBB();
                db = data.get(position);
                Intent intent = new Intent(context.getApplicationContext(),Detail.class);
                intent.putExtra("no",db.no);
                intent.putExtra("nama",db.nama);
                intent.putExtra("alamat",db.alamat);
                intent.putExtra("notel",db.notel);
                intent.putExtra("lat",db.latlbb);
                intent.putExtra("lng",db.longlbb);
                context.startActivity(intent);
                //Snackbar.make(rr,String.valueOf(position),Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView nama;
        TextView alamat;
        TextView notel;
        CardView cv;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            rr = (RelativeLayout)itemView.findViewById(R.id.rr);
            nama= (TextView) itemView.findViewById(R.id.namalbb);
            alamat = (TextView) itemView.findViewById(R.id.alamatlbb);
            notel = (TextView) itemView.findViewById(R.id.notellbb);
            cv = (CardView)itemView.findViewById(R.id.cv_main);
        }

    }
    // Do Search...
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(data);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (DataLBB item : data) {
                        if (item.nama.toLowerCase().contains(text.toLowerCase()) ||
                                item.alamat.toLowerCase().contains(text.toLowerCase())||
                                item.notel.toLowerCase().contains(text.toLowerCase())){
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }
}

package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;


/**
 * Created by Sagar Virvani on 17-11-2017.
 */

public class BoosterPayoutAdapter extends BaseAdapter {
    private static ArrayList sr,date,payout;
    Activity activity;
    public BoosterPayoutAdapter(Activity activity, ArrayList sr, ArrayList date, ArrayList payout) {
        super();
        this.activity = activity;
        this.sr=sr;
        this.date=date;
        this.payout=payout;

    }
    @Override
    public int getCount() {
        return sr.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        MyTextView srMyTextView,dateMyTextView,payoutMyTextView;
        LinearLayout lnBoosterPayout;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_booster_payout, parent,false);
            holder = new ViewHolder();
            holder.srMyTextView=(MyTextView) convertView.findViewById(R.id.sr_txt);
            holder.lnBoosterPayout=(LinearLayout) convertView.findViewById(R.id.ln_booster_payout);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position < sr.size()) {
            /*if(position %2 == 1)
            {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            }
            else
            {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }*/
            if (position % 2 == 1) {
                // Set a background color for linearlayout regular row/item
                holder.lnBoosterPayout.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                holder.lnBoosterPayout.setBackgroundResource(R.color.table_bg_even);
            }

            Log.i("INFO","call for inforamtion sr no:-"+sr.get(position).toString());
            holder.srMyTextView.setText(sr.get(position).toString());
        }
        return convertView;
    }
    public void clearData() {
        // clear the data
           sr.clear();
           date.clear();
           payout.clear();
    }

}

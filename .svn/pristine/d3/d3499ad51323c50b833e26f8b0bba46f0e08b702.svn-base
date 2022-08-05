package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

/**
 * Created by Sagar Virvani on 16-11-2017.
 */

public class CarProgramAdapter extends BaseAdapter{
    private static ArrayList date,particular,payment;
    Activity activity;

    public CarProgramAdapter(Activity activity, ArrayList date, ArrayList particular, ArrayList payment) {
        super();
        this.activity = activity;
        this.particular=particular;

    }
    @Override
    public int getCount() {
        return particular.size();
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
        MyTextView dateMyTextView,particularMyTextView,paymentMyTextView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_subooster_carprog_income, parent,false);
            holder = new ViewHolder();
            holder.dateMyTextView=(MyTextView) convertView.findViewById(R.id.date_txt);
            holder.particularMyTextView=(MyTextView) convertView.findViewById(R.id.income_no_txt);
            holder.paymentMyTextView=(MyTextView) convertView.findViewById(R.id.payment_txt);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position < particular.size()) {
            if(position %2 == 1)
            {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            }
            else
            {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }
            Log.i("INFO","call for inforamtion:-"+particular.get(position).toString());
            holder.particularMyTextView.setText(particular.get(position).toString());
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        //date.clear();
        particular.clear();
        //payment.clear();
    }
}

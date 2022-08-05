package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

/**
 * Created by Sagar Virvani on 21-11-2017.
 */

public class MainLagSuBoosterAdapter extends BaseAdapter{
    private static ArrayList iboid, iboname, receipt;
    Activity activity;

    public MainLagSuBoosterAdapter(Activity activity, ArrayList iboid, ArrayList iboname, ArrayList receipt) {
        super();
        this.activity = activity;
        this.iboid=iboid;
        this.iboname=iboname;
        this.receipt=receipt;
    }
    @Override
    public int getCount()
    {
        return iboid.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    private class ViewHolder {
        MyTextView iboidMyTextView,ibonameMyTextView,receiptMyTextView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_su_booster_lags, parent,false);
            holder = new ViewHolder();
            holder.iboidMyTextView=(MyTextView) convertView.findViewById(R.id.ibo_id_txt);
            holder.ibonameMyTextView = (MyTextView) convertView.findViewById(R.id.ibo_name_txt);
            holder.receiptMyTextView = (MyTextView) convertView.findViewById(R.id.receipt_txt);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    public void clearData() {
        // clear the data
        iboid.clear();
        iboname.clear();
        receipt.clear();
    }
}

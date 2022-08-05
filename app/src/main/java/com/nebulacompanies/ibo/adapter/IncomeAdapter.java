package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.IncomeHistoryList;
import com.nebulacompanies.ibo.model.IncomeListDetails;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.view.MyTextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import cn.jzvd.AVLoadingIndicatorView;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

public class IncomeAdapter  extends BaseAdapter {

    Activity activity;
    ArrayList<IncomeHistoryList> incomeHistoryListArrayList = new ArrayList<>();

    public IncomeAdapter(Activity activity, ArrayList<IncomeHistoryList> incomeHistoryLists) {
        super();
        this.activity = activity;
        incomeHistoryListArrayList.clear();
        incomeHistoryListArrayList.addAll(incomeHistoryLists);
    }
    @Override
    public int getCount() {
        return incomeHistoryListArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return incomeHistoryListArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        MyTextView Date, dv;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {

            view = mInflater1.inflate(R.layout.list_dream_volume, null);
            holder1 = new ViewHolder();
            holder1.Date = (MyTextView) view.findViewById(R.id.date_txt);
            holder1.dv = (MyTextView) view.findViewById(R.id.dv_txt);
            view.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder) view.getTag();
        }
        if(i < incomeHistoryListArrayList.size()) {
            if (i % 2 == 1) {
                // Set a background color for ListView regular row/item
                view.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                view.setBackgroundResource(R.color.table_bg_even);
            }

            holder1.Date.setText(SetGmtTime(incomeHistoryListArrayList.get(i).getIncomeDate()));
            holder1.dv.setText(" "+ Config.formatter.format(incomeHistoryListArrayList.get(i).getTotalAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));
        }
        return view;
    }

    public void clearData() {
        // clear the data
        incomeHistoryListArrayList.clear();
    }
}
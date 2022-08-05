package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.DownlineIBORankList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;


/**
 * Created by Sagar Virvani on 01-03-2018.
 */

public class DownlineRanksAdapter extends BaseAdapter {
    private Activity activity;

    ArrayList<DownlineIBORankList> arrayListDownlineRankDetailsList=new ArrayList<>();

    public DownlineRanksAdapter(Activity activity, ArrayList<DownlineIBORankList> DownlineRankLists) {
        this.activity = activity;
        arrayListDownlineRankDetailsList.clear();
        arrayListDownlineRankDetailsList.addAll(DownlineRankLists);
    }

    @Override
    public int getCount() {
       // return 0;
        return arrayListDownlineRankDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListDownlineRankDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListDownlineRankDetailsList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        MyTextView iboid, name, city, bv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_downline_rank_list, null);
            holder1 = new ViewHolder1();
            holder1.iboid = (MyTextView) convertView.findViewById(R.id.ibo_id_text);
            holder1.name = (MyTextView) convertView.findViewById(R.id.name_text);
            holder1.city = (MyTextView) convertView.findViewById(R.id.city_text);
            holder1.bv = (MyTextView) convertView.findViewById(R.id.bv_text);

            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if(position < arrayListDownlineRankDetailsList.size()) {

            holder1.iboid.setText(arrayListDownlineRankDetailsList.get(position).getUserName());
            holder1.name.setText(arrayListDownlineRankDetailsList.get(position).getFullName());

            holder1.city.setText(arrayListDownlineRankDetailsList.get(position).getCity());
            holder1.bv.setText(String.valueOf(arrayListDownlineRankDetailsList.get(position).getBVPercent()));
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListDownlineRankDetailsList.clear();
    }
}

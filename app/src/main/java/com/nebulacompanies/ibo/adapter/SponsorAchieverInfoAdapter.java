package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.AchieverclosingIncomeIBODetails;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;


/**
 * Created by Palak Mehta on 13-Apr-18.
 */

public class SponsorAchieverInfoAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<AchieverclosingIncomeIBODetails> achieverclosingIncomeIBODetails = new ArrayList<>();

    public SponsorAchieverInfoAdapter(Activity activity, ArrayList<AchieverclosingIncomeIBODetails> achieverclosingIncomeIBODetails_) {
        super();
        this.activity = activity;
        achieverclosingIncomeIBODetails.clear();
        achieverclosingIncomeIBODetails.addAll(achieverclosingIncomeIBODetails_);
    }

    private class ViewHolder1 {
        MyTextView date, iboId, iboName;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder1 holder1;
        LayoutInflater mInflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_popup_achiever_income_info, null);
            holder1 = new ViewHolder1();
            holder1.date = (MyTextView) convertView.findViewById(R.id.achiever_date_);
            holder1.iboId = (MyTextView) convertView.findViewById(R.id.achiever_iboid_);
            holder1.iboName = (MyTextView) convertView.findViewById(R.id.achiever_iboname_);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if (position % 2 == 1) {
            // Set a background color for ListView regular row/item
            convertView.setBackgroundResource(R.color.table_bg_odd);
        } else {
            // Set the background color for alternate row/item
            convertView.setBackgroundResource(R.color.table_bg_even);
        }

        if (position < achieverclosingIncomeIBODetails.size()) {
            holder1.date.setText(SetGmtTime(achieverclosingIncomeIBODetails.get(position).getLongClearDate()));
            holder1.iboId.setText(achieverclosingIncomeIBODetails.get(position).getIBOID());
            holder1.iboName.setText(achieverclosingIncomeIBODetails.get(position).getName());

            return convertView;
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return achieverclosingIncomeIBODetails.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        // clear the data
        achieverclosingIncomeIBODetails.clear();
    }

}
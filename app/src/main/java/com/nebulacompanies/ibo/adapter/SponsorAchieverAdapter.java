package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.AchieverIncomeList;
import com.nebulacompanies.ibo.model.AchieverclosingIncomeIBODetails;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;


/**
 * Created by Palak Mehta on 12-Apr-18.
 */

public class SponsorAchieverAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<AchieverIncomeList> achieverIncomeDetails = new ArrayList<>();
    ArrayList<AchieverclosingIncomeIBODetails> achieverclosingIncomeIBODetails = new ArrayList<>();
    ArrayList<AchieverclosingIncomeIBODetails> details = new ArrayList<>();
    ArrayList<AchieverclosingIncomeIBODetails> customDetails = new ArrayList<>();
    SponsorAchieverInfoAdapter sponsorAchieverInfoAdapter;

    public SponsorAchieverAdapter(Activity activity, ArrayList<AchieverIncomeList> achieverIncomeDetails_, ArrayList<AchieverclosingIncomeIBODetails> achieverclosingIncomeIBODetails_,
                                  ArrayList<AchieverclosingIncomeIBODetails> details_) {
        super();
        this.activity = activity;
        achieverIncomeDetails.clear();
        achieverclosingIncomeIBODetails.clear();
        details.clear();
        achieverIncomeDetails.addAll(achieverIncomeDetails_);
        achieverclosingIncomeIBODetails.addAll(achieverclosingIncomeIBODetails_);
        details.addAll(details_);
    }

    private class ViewHolder1 {
        MyTextView date, amount;
        LinearLayout linearLayout;
    }

    private class ViewHolder2 {
        MyTextView dateTextView, idTextView, nameTextView;
        View view;
        ListView listView;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder1 holder1;
        LayoutInflater mInflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_achiever_income, null);
            holder1 = new ViewHolder1();
            holder1.linearLayout = (LinearLayout) convertView.findViewById(R.id.achiever_income_layout);
            holder1.date = (MyTextView) convertView.findViewById(R.id.achiever_date);
            holder1.amount = (MyTextView) convertView.findViewById(R.id.achiever_amount);
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

            if (position < achieverIncomeDetails.size()) {
                holder1.date.setText(SetGmtTime(achieverIncomeDetails.get(position).getClosingDate()));

                //holder1.amount.setText(String.valueOf(achieverIncomeDetails.get(position).getAmount()));
                holder1.amount.setText(""+ Config.formatter.format(achieverIncomeDetails.get(position).getAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                final int masterId = achieverIncomeDetails.get(position).getHolidayAchieveIBOMasterID();

            holder1.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_achiever_income_info, null);
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                        holder2 = new ViewHolder2();
                        holder2.listView = (ListView) convertView1.findViewById(R.id.achiever_info_listview);

                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    customDetails.clear();

                    for(int j = 0; j< details.size(); j++){
                        if(masterId == details.get(j).getHolidayAchieveIBOMasterID() && details.get(j).getCustomerType().equals("IBO")){
                            customDetails.add(details.get(j));
                        }
                    }

                    sponsorAchieverInfoAdapter = new SponsorAchieverInfoAdapter(activity, customDetails);
                    holder2.listView.setAdapter(sponsorAchieverInfoAdapter);
                    sponsorAchieverInfoAdapter.notifyDataSetChanged();

                    dialog1.show();
                }
            });

                return convertView;
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return achieverIncomeDetails.size();
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
        achieverIncomeDetails.clear();
        achieverclosingIncomeIBODetails.clear();
        details.clear();
        customDetails.clear();
    }

}
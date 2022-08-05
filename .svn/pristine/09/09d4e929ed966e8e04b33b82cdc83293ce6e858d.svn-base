package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.SponsorIncomeList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 13-07-2018.
 */

public class SponsorAdapter extends BaseAdapter {
    Activity activity;
    /* ArrayList<SalesValues> arrayList;*/
    ArrayList<SponsorIncomeList> arrayListSponsorIncomeList=new ArrayList<>();

    public SponsorAdapter(Activity activity, ArrayList<SponsorIncomeList> SponsorIncome) {
        this.activity = activity;
        arrayListSponsorIncomeList.clear();
        arrayListSponsorIncomeList.addAll(SponsorIncome);
    }
    @Override
    public int getCount() {
        return arrayListSponsorIncomeList.size();   }

    @Override
    public Object getItem(int position) {
        return arrayListSponsorIncomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListSponsorIncomeList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        LinearLayout linearLayout;
        View view;
        MyTextView date, unit,rankBonus;

    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_rank_bonus_income, null);
            holder1 = new ViewHolder1();
            holder1.linearLayout = (LinearLayout) convertView.findViewById(R.id.tablelayout16);
            holder1.view = (View) convertView.findViewById(R.id.apartment_view1);
            holder1.date = (MyTextView) convertView.findViewById(R.id.date);
            holder1.unit = (MyTextView) convertView.findViewById(R.id.unit);
            holder1.rankBonus = (MyTextView) convertView.findViewById(R.id.Rankbonus);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }



        if(position < arrayListSponsorIncomeList.size()) {
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
            holder1.date.setText(SetGmtTime(arrayListSponsorIncomeList.get(position).getDate()));
            holder1.unit.setText(arrayListSponsorIncomeList.get(position).getUnit());
            holder1.rankBonus.setText(" "+ Config.formatter.format(arrayListSponsorIncomeList.get(position).getSponsorIncome()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));



        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListSponsorIncomeList.clear();
    }

}

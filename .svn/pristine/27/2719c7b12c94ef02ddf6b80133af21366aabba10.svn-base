package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.SpotIncomeList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 7/4/2016.
 */
public class SpotIncomeListViewAdapter extends BaseAdapter{

   /* private static ArrayList purDate, member, product, amountPaid, spotIncome;*/
    Activity activity;
    private ArrayList<SpotIncomeList> arrayListSpotIncomeList = new ArrayList<>();
    public SpotIncomeListViewAdapter(Activity activity, ArrayList<SpotIncomeList> SpotIncomeLists ) {
        super();
        this.activity = activity;
        arrayListSpotIncomeList.clear();
        arrayListSpotIncomeList.addAll(SpotIncomeLists);
    }

    @Override
    public int getCount() {
        return arrayListSpotIncomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        MyTextView travelname, packagestatus;
        LinearLayout lnSpotVolume;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_dream_volume, null);
            holder = new ViewHolder();
            holder.travelname = (MyTextView) convertView.findViewById(R.id.date_txt);
            holder.packagestatus = (MyTextView) convertView.findViewById(R.id.dv_txt);
            holder.lnSpotVolume = (LinearLayout) convertView.findViewById(R.id.ln_spot_volume);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position < arrayListSpotIncomeList.size()) {
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
                holder.lnSpotVolume.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                holder.lnSpotVolume.setBackgroundResource(R.color.table_bg_even);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            /*holder.pdate.setText(SetDateFormat(purDate.get(position).toString()));
            holder.member_.setText(member.get(position).toString());
            holder.amount_paid1.setText((activity.getResources().getString(R.string.Rs))+" "+amountPaid.get(position).toString());
            holder.sIncome.setText((activity.getResources().getString(R.string.Rs))+" "+spotIncome.get(position).toString());*/

            /*holder.pdate.setText(SetGmtTime(arrayListSpotIncomeList.get(position).getDate()));
            holder.member_.setText(arrayListSpotIncomeList.get(position).getIBOName());
            holder.amount_paid1.setText((activity.getResources().getString(R.string.Rs))+" "+String.valueOf(arrayListSpotIncomeList.get(position).getAmountPaid()));
            holder.sIncome.setText((activity.getResources().getString(R.string.Rs))+" "+String.valueOf(arrayListSpotIncomeList.get(position).getSpotIncome()));*/
            holder.packagestatus.setText("" + Config.formatter.format(arrayListSpotIncomeList.get(position).getAmount()).replace(".00", "").replace("Rs.", ""));
            holder.travelname.setText(SetGmtTime(arrayListSpotIncomeList.get(position).getDate()));
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListSpotIncomeList.clear();
    }
}


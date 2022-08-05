package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.BonanzaTopTenList;

import java.util.ArrayList;


/**
 * Created by Palak Mehta on 11-Apr-18.
 */

public class BonanzaTopTenAdapter extends BaseAdapter {

    Activity activity;
    private ArrayList<BonanzaTopTenList> bonanzaTopTenLists = new ArrayList<>();

    public BonanzaTopTenAdapter(Activity activity, ArrayList<BonanzaTopTenList> topTenLists) {
        super();
        this.activity = activity;
        bonanzaTopTenLists.clear();
        bonanzaTopTenLists.addAll(topTenLists);
    }

    @Override
    public int getCount() {
        return bonanzaTopTenLists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder1 {
        TextView leg, iboId, iboName, sales;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_minibonanza_topten, null);
            holder1 = new ViewHolder1();
            holder1.leg = (TextView) convertView.findViewById(R.id.mini_bonanza_leg);
            holder1.iboId = (TextView) convertView.findViewById(R.id.mini_bonanza_iboid);
            holder1.iboName = (TextView) convertView.findViewById(R.id.mini_bonanza_iboname);
            holder1.sales = (TextView) convertView.findViewById(R.id.mini_bonanza_sales);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if (position < bonanzaTopTenLists.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }

            holder1.leg.setText(String.valueOf(bonanzaTopTenLists.get(position).getLeg()));
            holder1.iboId.setText(bonanzaTopTenLists.get(position).getIBOID());
            holder1.iboName.setText(bonanzaTopTenLists.get(position).getIBOUser());
            holder1.sales.setText(String.valueOf(bonanzaTopTenLists.get(position).getSaleCount()));
        }

        return convertView;
    }

    public void clearData() {
        // clear the data
        bonanzaTopTenLists.clear();
    }

}

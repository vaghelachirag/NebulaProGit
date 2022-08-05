package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.MiniBonanzaIncomeList;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 10-Apr-18.
 */

public class MiniBonanzaIncomeAdapter extends BaseAdapter {

    Activity activity;
    private ArrayList<MiniBonanzaIncomeList> miniBonanzaIncomeLists = new ArrayList<>();

    public MiniBonanzaIncomeAdapter(Activity activity, ArrayList<MiniBonanzaIncomeList> bonanzaIncomeLists) {
        super();
        this.activity = activity;
        miniBonanzaIncomeLists.clear();
        miniBonanzaIncomeLists.addAll(bonanzaIncomeLists);
    }

    @Override
    public int getCount() {
        return miniBonanzaIncomeLists.size();
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
        TextView date, income;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_minibonanza_income, null);
            holder1 = new ViewHolder1();
            holder1.date = (TextView) convertView.findViewById(R.id.mini_bonanza_date);
            holder1.income = (TextView) convertView.findViewById(R.id.mini_bonanza_income);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if (position < miniBonanzaIncomeLists.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }

            holder1.date.setText(SetGmtTime(miniBonanzaIncomeLists.get(position).getLongToDate()));
            holder1.income.setText(miniBonanzaIncomeLists.get(position).getTotalAmount());
        }

        return convertView;
    }

    public void clearData() {
        // clear the data
        miniBonanzaIncomeLists.clear();
    }
}

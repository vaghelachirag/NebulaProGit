package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.CarProgramIncomeLegList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;


/**
 * Created by Sagar Virvani on 16-11-2017.
 */

public class SuBoosterIncomeAdapter extends BaseAdapter {

    /*private static ArrayList ibo_id, ibo, receipt;*/
    Activity activity;
    ArrayList<CarProgramIncomeLegList> arrayListCarProgramIncomeLeg = new ArrayList<>();
    public SuBoosterIncomeAdapter(Activity activity, ArrayList<CarProgramIncomeLegList> CarProgramIncomeLegs) {
        super();
        this.activity = activity;
        arrayListCarProgramIncomeLeg.clear();
        arrayListCarProgramIncomeLeg.addAll(CarProgramIncomeLegs);
    }

    @Override
    public int getCount() {
        return arrayListCarProgramIncomeLeg.size();
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
        MyTextView idTextView, iboTextView, receiptTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_subooster_carprog_income, parent,false);
            holder = new ViewHolder();
            holder.idTextView = (MyTextView) convertView.findViewById(R.id.date_txt);
            holder.iboTextView = (MyTextView) convertView.findViewById(R.id.income_no_txt);
            holder.receiptTextView = (MyTextView) convertView.findViewById(R.id.payment_txt);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position < arrayListCarProgramIncomeLeg.size()) {
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

           /* holder.idTextView.setText(ibo_id.get(position).toString());
            holder.iboTextView.setText(ibo.get(position).toString());
            holder.receiptTextView.setText(receipt.get(position).toString());*/
            holder.idTextView.setText(arrayListCarProgramIncomeLeg.get(position).getIBOID());
            holder.iboTextView.setText(arrayListCarProgramIncomeLeg.get(position).getIBOName());
            holder.receiptTextView.setText(Integer.toString((int) arrayListCarProgramIncomeLeg.get(position).getPercentage()));
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
       arrayListCarProgramIncomeLeg.clear();
    }

}

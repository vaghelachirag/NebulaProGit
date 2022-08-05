package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.MySalesList;
import com.nebulacompanies.ibo.model.SalesTelecallerList;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 28-02-2018.
 */

public class MySalesHolidayAdapter extends BaseAdapter {
    Activity activity;
    //ArrayList date,dv;
    ArrayList<MySalesList> MySalesHolidayList = new ArrayList<>();
    ArrayList<SalesTelecallerList> salesTelecallerLists = new ArrayList<>();

    public MySalesHolidayAdapter(Activity activity, ArrayList<MySalesList> MySalesLists) {
        super();
        this.activity = activity;
        MySalesHolidayList.clear();
        MySalesHolidayList.addAll(MySalesLists);

    }

    @Override
    public int getCount() {
        return MySalesHolidayList.size();
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
        MyTextView travelname, packagestatus, tv_amount, tv_date,tvPackageName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = mInflater1.inflate(R.layout.list_my_sales_holidays, null);
            holder1 = new ViewHolder1();
            holder1.travelname = (MyTextView) convertView.findViewById(R.id.date_txt);
            holder1.packagestatus = (MyTextView) convertView.findViewById(R.id.dv_txt);
            holder1.tv_amount = (MyTextView) convertView.findViewById(R.id.tv_amount);
            holder1.tv_date = (MyTextView) convertView.findViewById(R.id.tv_date);
            holder1.tvPackageName = (MyTextView) convertView.findViewById(R.id.dv_package_name);

            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }
        if (position < MySalesHolidayList.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }

           /* holder1.Date.setText(SetDateFormat(date.get(position).toString()));
            holder1.dv.setText(dv.get(position).toString());*/

            salesTelecallerLists.addAll(MySalesHolidayList.get(position).getPaymentSchedule());

            holder1.travelname.setText(MySalesHolidayList.get(position).getCustomerName());
            holder1.packagestatus.setText(salesTelecallerLists.get(position).getPackageStatus());
            holder1.tv_amount.setText("" + Config.formatter.format(MySalesHolidayList.get(position).getReceipt()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));
            holder1.tv_date.setText(SetGmtTime(MySalesHolidayList.get(position).getBookingDate()));
            holder1.tvPackageName.setText(MySalesHolidayList.get(position).getProductName());
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        MySalesHolidayList.clear();
    }
}
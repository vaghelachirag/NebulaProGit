package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.MySalesList;
import com.nebulacompanies.ibo.model.SalesTelecallerList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 28-Apr-18.
 */

public class SalesTelecallerAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<MySalesList> salesLists = new ArrayList<>();
    ArrayList<SalesTelecallerList> salesTelecallerLists = new ArrayList<>();

    public SalesTelecallerAdapter(Activity activity, ArrayList<SalesTelecallerList> salesTelecallerLists1) {
        this.activity = activity;
        salesTelecallerLists.clear();
        salesTelecallerLists.addAll(salesTelecallerLists1);
    }

    @Override
    public int getCount() {
        return salesTelecallerLists.size();   }

    @Override
    public Object getItem(int position) {
        return salesTelecallerLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return salesTelecallerLists.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        MyTextView instNo, emiDate, amount, status, bv, nv;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.popup_telecaller_list_details, null);
            holder1 = new ViewHolder1();
            holder1.instNo = (MyTextView) convertView.findViewById(R.id.installment_number1);
            holder1.emiDate = (MyTextView) convertView.findViewById(R.id.emi_date1);
            holder1.amount = (MyTextView) convertView.findViewById(R.id.emi_amount1);
            holder1.status = (MyTextView) convertView.findViewById(R.id.emi_status1);
            holder1.bv = (MyTextView) convertView.findViewById(R.id.bv11);
            holder1.nv = (MyTextView) convertView.findViewById(R.id.nv11);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if(position < salesTelecallerLists.size()) {
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
            //listAnimation(convertView.getContext(), convertView, position);

            if(salesTelecallerLists.get(position).getInstallmentNo() == 0){
                holder1.instNo.setText("Token");
            }
            else if(salesTelecallerLists.get(position).getInstallmentNo() > 0 && salesTelecallerLists.get(position).getPaymentFor().equals("EMI")){
                holder1.instNo.setText(salesTelecallerLists.get(position).getPaymentFor() + " " + String.valueOf(salesTelecallerLists.get(position).getInstallmentNo()));
            }
            else if(salesTelecallerLists.get(position).getInstallmentNo() > 0 && ( salesTelecallerLists.get(position).getPaymentFor().equals("BankLoan") || salesTelecallerLists.get(position).getPaymentFor().equals("SelfFinance")) ){
                holder1.instNo.setText(salesTelecallerLists.get(position).getPaymentFor());
            }

            holder1.emiDate.setText(SetGmtTime(salesTelecallerLists.get(position).getInstallmentDate()));

            holder1.amount.setText(String.valueOf(salesTelecallerLists.get(position).getInstallmentAmount()));
            //holder1.amount.setText(" "+ Config.formatter.format(salesTelecallerLists.get(position).getInstallmentAmount()).replace(".00", ""));

            holder1.status.setText(salesTelecallerLists.get(position).getInstallmentStatus());

            if(salesTelecallerLists.get(position).getBV() == null){
                holder1.bv.setText("0");
            }
            else {
                holder1.bv.setText(String.valueOf(salesTelecallerLists.get(position).getBV()));
            }

            if(salesTelecallerLists.get(position).getNV() == null){
                holder1.nv.setText("0");
            }
            else {
                holder1.nv.setText(String.valueOf(salesTelecallerLists.get(position).getNV()));
            }

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        salesTelecallerLists.clear();
    }

}

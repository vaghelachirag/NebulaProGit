package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.AchieverCustomerList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;


/**
 * Created by Sagar Virvani on 08-03-2018.
 */

public class CustomerSalesAdapter extends BaseAdapter {


    Activity activity;

   // private static ArrayList sr;
   ArrayList<AchieverCustomerList> arrayListachieverCustomerLists = new ArrayList<>();
    public CustomerSalesAdapter(Activity activity, ArrayList<AchieverCustomerList> achieverCustomerLists ) {
        super();
        this.activity = activity;
        arrayListachieverCustomerLists.clear();
        arrayListachieverCustomerLists.addAll(achieverCustomerLists);

    }

    private class ViewHolder1 {
        MyTextView date, customer;
        ImageView mi;
        LinearLayout lnCustomer;
    }

    private class ViewHolder2 {
        MyTextView phoneMyTextView, emailMyTextView, cityMyTextView,phoneTitleMyTextView,emailTitleMyTextView,cityTitleMyTextView;
        View view;
        LinearLayout pincodeLinearLayout;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder1 holder1;
        LayoutInflater mInflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_custmoer_sale_income, null);
            holder1 = new ViewHolder1();
            holder1.date = (MyTextView) convertView.findViewById(R.id.date);
            holder1.customer = (MyTextView) convertView.findViewById(R.id.customer);
            holder1.mi = (ImageView) convertView.findViewById(R.id.more_info1);
            holder1.lnCustomer = (LinearLayout) convertView.findViewById(R.id.ln_customer);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }
        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            convertView.setBackgroundResource(R.color.table_bg_odd);
            holder1.lnCustomer.setBackgroundResource(R.drawable.nebula_effect_white);

        }
        else
        {
            // Set the background color for alternate row/item
            convertView.setBackgroundResource(R.color.table_bg_even);
            holder1.lnCustomer.setBackgroundResource(R.drawable.nebula_effect);
        }
        if(position < arrayListachieverCustomerLists.size()) {
            holder1.date.setText(SetGmtTime(arrayListachieverCustomerLists.get(position).getDate()));
            holder1.customer.setText(arrayListachieverCustomerLists.get(position).getCustomerName());
            holder1.lnCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_customer_info_customer, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.phoneTitleMyTextView=(MyTextView)convertView1.findViewById(R.id.ln_phone);
                        holder2.phoneMyTextView= (MyTextView) convertView1.findViewById(R.id.ln_phone_value);
                        holder2.emailTitleMyTextView=(MyTextView)convertView1.findViewById(R.id.ln_email);
                        holder2.emailMyTextView = (MyTextView) convertView1.findViewById(R.id.ln_email_value);
                        holder2.cityTitleMyTextView=(MyTextView)convertView1.findViewById(R.id.ln_city);
                        holder2.cityMyTextView = (MyTextView) convertView1.findViewById(R.id.ln_city_value);
                       // holder2.pincodeLinearLayout=(LinearLayout)convertView1.findViewById(R.id.pincode_layout);
                       // holder2.pincodeLinearLayout.setVisibility(View.GONE);
                        holder2.phoneTitleMyTextView.setText(R.string.moblie);
                        holder2.emailTitleMyTextView.setText(R.string.email);
                        holder2.cityTitleMyTextView.setText(R.string.city_);
                    }
                    else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }
                    holder2.phoneMyTextView.setText(arrayListachieverCustomerLists.get(position).getPhone());
                    holder2.emailMyTextView.setText(arrayListachieverCustomerLists.get(position).getEmail());
                    holder2.cityMyTextView.setText(arrayListachieverCustomerLists.get(position).getCity());

                    dialog1.show();
                }
            });
            return convertView;
        }
        return convertView;
    }

    @Override
    public int getCount() {
       /* if (arrayListachieverCustomerLists.size()==0){
            return 5;
        }*/
       /* else
        {
            return arrayListachieverCustomerLists.size();
        }*/
       return 5;
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
        arrayListachieverCustomerLists.clear();
    }
}
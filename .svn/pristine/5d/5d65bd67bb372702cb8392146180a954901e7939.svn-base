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
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.ProductMasterList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;


/**
 * Created by Palak Mehta on 9/16/2016.
 */
public class DwarkaListViewAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<ProductMasterList> arrayListProductMaster = new ArrayList<>();

    public DwarkaListViewAdapter(Activity activity, ArrayList<ProductMasterList> productMasters) {
        super();
        this.activity = activity;
        arrayListProductMaster.clear();
        arrayListProductMaster.addAll(productMasters);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListProductMaster.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListProductMaster.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder1 {
        MyTextView name;
        LinearLayout dwarkalayout;
    }

    private class ViewHolder2 {
        MyTextView bvtxt, dvtxt, rttxt, nvtokentxt,nvdptxt,nvbankloantxt,startpoolxt;
        LinearLayout starpoolLinearLayout;
        View starpoolview;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_products_dwarka, null);
            holder1 = new ViewHolder1();
            holder1.name = (MyTextView) convertView.findViewById(R.id.product_name);
            holder1.dwarkalayout=(LinearLayout)convertView.findViewById(R.id.dwarka_layout);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if(position < arrayListProductMaster.size()) {
            //listAnimation(convertView.getContext(), convertView, position);
           /* if(position %2 == 1)
            {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect_white);
            }
            else
            {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect);
            }*/
            if (position % 2 == 1) {
                // Set a background color for linearlayout regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect);
            }

            holder1.name.setText(arrayListProductMaster.get(position).getProductName());

            holder1.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_aavaas_phases_info_dwarka, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.bvtxt = (MyTextView) convertView1.findViewById(R.id.bv_per_value);
                        holder2.rttxt = (MyTextView) convertView1.findViewById(R.id.ret_pro_value);
                        holder2.nvtokentxt = (MyTextView) convertView1.findViewById(R.id.nv_token);
                        holder2.nvdptxt = (MyTextView) convertView1.findViewById(R.id.nv_dp);
                        holder2.nvbankloantxt = (MyTextView) convertView1.findViewById(R.id.nv_bank_loan);
                        holder2.starpoolLinearLayout=(LinearLayout) convertView1.findViewById(R.id.star_pool_layout);
                        holder2.starpoolview=(View)convertView1.findViewById(R.id.star_pool_view);
                        holder2.starpoolview.setVisibility(View.VISIBLE);
                        holder2.starpoolLinearLayout.setVisibility(View.VISIBLE);
                        holder2.startpoolxt=(MyTextView)convertView1.findViewById(R.id.starpool);
                        convertView1.setTag(holder2);
                    }
                    else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    holder2.bvtxt.setText(String.valueOf(arrayListProductMaster.get(position).getbVPer())+ "  ");
                    holder2.rttxt.setText(String.valueOf(arrayListProductMaster.get(position).getRetailProfit())+ " %");
                    holder2.nvtokentxt.setText(String.valueOf(arrayListProductMaster.get(position).getNVToken())+ "  ");
                    holder2.nvdptxt.setText(String.valueOf(arrayListProductMaster.get(position).getNVDP())+ "  ");
                    holder2.nvbankloantxt.setText(String.valueOf(arrayListProductMaster.get(position).getNVBankLoan())+ "  ");
                    holder2.startpoolxt.setText(String.valueOf(arrayListProductMaster.get(position).getStarPoolPer())+ " %");
                    dialog1.show();
                }
            });

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListProductMaster.clear();
    }

}



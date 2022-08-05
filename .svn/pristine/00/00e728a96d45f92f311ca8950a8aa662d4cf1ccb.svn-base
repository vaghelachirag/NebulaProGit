package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.RankBonusList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 12-07-2018.
 */

public class RankBonusAdapter extends BaseAdapter {
    Activity activity;
    /* ArrayList<SalesValues> arrayList;*/
    ArrayList<RankBonusList> arrayListRankBonusList = new ArrayList<>();

    public RankBonusAdapter(Activity activity, ArrayList<RankBonusList> RankBonus) {
        this.activity = activity;
        arrayListRankBonusList.clear();
        arrayListRankBonusList.addAll(RankBonus);
    }

    @Override
    public int getCount() {
        return arrayListRankBonusList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListRankBonusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListRankBonusList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        LinearLayout linearLayout;
        View view;
        MyTextView date, unit, rankBonus;
        LinearLayout lnRankBonus;

    }

    private class ViewHolder2 {
        MyTextView st, ct, mb, pc;
        View mobileView, pincodeView;
        LinearLayout mobileLinearLayout, pincodeLinearLayout;
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
            holder1.lnRankBonus = (LinearLayout) convertView.findViewById(R.id.ln_rank_bonus);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }


        if (position < arrayListRankBonusList.size()) {
           /* if(position %2 == 1)
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
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnRankBonus.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnRankBonus.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.lnRankBonus.setBackgroundResource(R.drawable.nebula_effect);
            }
            holder1.date.setText(SetGmtTime(arrayListRankBonusList.get(position).getClosingDate()));
            holder1.unit.setText(arrayListRankBonusList.get(position).getUnit());

            // holder1.rankBonus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            holder1.rankBonus.setText(" " + Config.formatter.format(arrayListRankBonusList.get(position).getTotalRankBonus()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

            // holder1.rankBonus.setPaintFlags(holder1.rankBonus.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder1.lnRankBonus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_customer_info_rank, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.mobileLinearLayout = (LinearLayout) convertView1.findViewById(R.id.mobile_layout);
                        holder2.pincodeLinearLayout = (LinearLayout) convertView1.findViewById(R.id.pincode_layout);
                        holder2.pincodeView = (View) convertView1.findViewById(R.id.pincode_view);
                        holder2.mobileView = (View) convertView1.findViewById(R.id.mobile_view);
                        holder2.st = (MyTextView) convertView1.findViewById(R.id.state_1);
                        holder2.ct = (MyTextView) convertView1.findViewById(R.id.city_1);
                        holder2.mb = (MyTextView) convertView1.findViewById(R.id.state_title);
                        holder2.pc = (MyTextView) convertView1.findViewById(R.id.city_title);
                        convertView1.setTag(holder2);
                        holder2.mb.setText(R.string.platinumRankBonus);
                        holder2.pc.setText(R.string.starPlatinumRankBonus);
                        holder2.mobileView.setVisibility(View.GONE);
                        holder2.pincodeView.setVisibility(View.GONE);
                        holder2.mobileLinearLayout.setVisibility(View.GONE);
                        holder2.pincodeLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    holder2.st.setText(" " + Config.formatter.format(arrayListRankBonusList.get(position).getPlatinumRankBonus()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));
                    holder2.ct.setText(" " + Config.formatter.format(arrayListRankBonusList.get(position).getStarPlatinumRankBonus()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    dialog1.show();
                }
            });


        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListRankBonusList.clear();
    }

}

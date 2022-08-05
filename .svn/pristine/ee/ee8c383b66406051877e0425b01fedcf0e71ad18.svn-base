package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.SuperBoosterIncomeList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;


import static android.graphics.Paint.UNDERLINE_TEXT_FLAG;

/**
 * Created by Palak Mehta on 10/8/2016.
 */
public class SuperBoosterIncomeAdapter extends BaseAdapter {

    //  private static ArrayList  member_id, member_name, sponsor_id, rank, city, state, mobile, payment_percent;
    Activity activity;
    private ArrayList<SuperBoosterIncomeList> arrayListSuperBoosterIncomeList = new ArrayList<>();

    public SuperBoosterIncomeAdapter(Activity activity, ArrayList<SuperBoosterIncomeList> SuperBoosterIncomeLists) {
        super();
        this.activity = activity;
        arrayListSuperBoosterIncomeList.clear();
        arrayListSuperBoosterIncomeList.addAll(SuperBoosterIncomeLists);

    }

    @Override
    public int getCount() {
        return arrayListSuperBoosterIncomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSuperBoosterIncomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder1 {
        MyTextView indexTextView, idTextView, perTextView;
        ProgressBar progressBar;
    }

    private class ViewHolder2 {
        MyTextView legtxt, memname, sid, rank_, city_, state_, mob;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.list_super_booster_income, null);
            holder1 = new ViewHolder1();
            holder1.indexTextView = (MyTextView) convertView.findViewById(R.id.super_booster_index);
            holder1.idTextView = (MyTextView) convertView.findViewById(R.id.memberid_super);
            holder1.perTextView = (MyTextView) convertView.findViewById(R.id.ahd_super_booster_payment_percent);
            holder1.progressBar = (ProgressBar) convertView.findViewById(R.id.super_booster_bar);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        final Typeface tf1 = Typeface.createFromAsset(activity.getAssets(), Config.FONT_STYLE);

        if (position < arrayListSuperBoosterIncomeList.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            /*holder1.indexTextView.setText(String.valueOf(position+1));
            holder1.idTextView.setText(member_id.get(position).toString());
            holder1.idTextView.setPaintFlags(holder1.idTextView.getPaintFlags() | UNDERLINE_TEXT_FLAG);*/

            holder1.indexTextView.setText(String.valueOf(position + 1));
            holder1.idTextView.setText(arrayListSuperBoosterIncomeList.get(position).getIBOID());
            holder1.idTextView.setPaintFlags(holder1.idTextView.getPaintFlags() | UNDERLINE_TEXT_FLAG);

            holder1.idTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_super_booster_income, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        //holder2.legtxt = (TextView) convertView1.findViewById(R.id.leg_super1);
                        holder2.memname = (MyTextView) convertView1.findViewById(R.id.member_name_super1);
                        holder2.sid = (MyTextView) convertView1.findViewById(R.id.sponsor_id_super1);
                        holder2.rank_ = (MyTextView) convertView1.findViewById(R.id.rank_super1);
                        holder2.city_ = (MyTextView) convertView1.findViewById(R.id.city_super1);
                        holder2.state_ = (MyTextView) convertView1.findViewById(R.id.state_super1);
                        holder2.mob = (MyTextView) convertView1.findViewById(R.id.mobile_super1);
                        convertView1.setTag(holder2);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    // holder2.legtxt.setText(leg.get(position).toString());
                        /*holder2.memname.setText(member_name.get(position).toString());
                        holder2.sid.setText(sponsor_id.get(position).toString());
                        holder2.rank_.setText(rank.get(position).toString());
                        holder2.city_.setText(city.get(position).toString());
                        holder2.state_.setText(state.get(position).toString());
                        holder2.mob.setText(mobile.get(position).toString());*/

                    holder2.memname.setText(arrayListSuperBoosterIncomeList.get(position).getIBOName());
                    holder2.sid.setText(arrayListSuperBoosterIncomeList.get(position).getSponsorID());
                    holder2.rank_.setText(arrayListSuperBoosterIncomeList.get(position).getRank());
                    holder2.city_.setText(arrayListSuperBoosterIncomeList.get(position).getCity());
                    holder2.state_.setText(arrayListSuperBoosterIncomeList.get(position).getState());
                    holder2.mob.setText(arrayListSuperBoosterIncomeList.get(position).getMobile());

                    dialog1.show();
                }
            });

            holder1.progressBar.setProgress(arrayListSuperBoosterIncomeList.get(position).getPercent());
            holder1.perTextView.setText(arrayListSuperBoosterIncomeList.get(position).getPercent() + "%");

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        //leg.clear();
        arrayListSuperBoosterIncomeList.clear();
    }
}

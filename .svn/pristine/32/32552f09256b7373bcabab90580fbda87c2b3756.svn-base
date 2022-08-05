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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.ThreeStarClubIncomeList;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 24-01-2018.
 */

public class ThreeStarClubIncomeAdapter extends BaseAdapter {

    Activity activity;
    private ArrayList<ThreeStarClubIncomeList> arrayListThreeStarClubIncomeList = new ArrayList<>();
    public ThreeStarClubIncomeAdapter(Activity activity, ArrayList<ThreeStarClubIncomeList> ThreeStarClubIncomeLists) {
        super();
        this.activity = activity;
        arrayListThreeStarClubIncomeList.clear();
        arrayListThreeStarClubIncomeList.addAll(ThreeStarClubIncomeLists);
    }

    @Override
    public int getCount() {
        return arrayListThreeStarClubIncomeList.size();
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
        TextView dt, rnk, ga;
        ImageView mi;
        LinearLayout listIncomeDetailsLayout;
    }

    private class ViewHolder2 {
        TextView sa, td, net, shr;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_starclub_income, null);
            holder1 = new ViewHolder1();
            holder1.dt = (TextView) convertView.findViewById(R.id.date_starclub1);
            holder1.rnk = (TextView) convertView.findViewById(R.id.rank_starpool1);
            holder1.ga = (TextView) convertView.findViewById(R.id.gross_amt_starpool1);
            holder1.mi = (ImageView) convertView.findViewById(R.id.more_info_starpool1);
            holder1.listIncomeDetailsLayout = (LinearLayout) convertView.findViewById(R.id.list_income_details_layout);

            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        final Typeface tf1 = Typeface.createFromAsset(activity.getAssets(), Config.FONT_STYLE);

        if(position < arrayListThreeStarClubIncomeList.size()) {
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
             // holder1.listIncomeDetailsLayout.setBackgroundResource(R.color.table_bg_odd);

              convertView.setBackgroundResource(R.color.table_bg_odd);
              holder1.listIncomeDetailsLayout.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
               //holder1.listIncomeDetailsLayout.setBackgroundResource(R.color.table_bg_even);
               convertView.setBackgroundResource(R.color.table_bg_even);
               holder1.listIncomeDetailsLayout.setBackgroundResource(R.drawable.nebula_effect);
            }

            /*holder1.dt.setText(SetDateFormat(date.get(position).toString()));
            holder1.rnk.setText(rank.get(position).toString());
            holder1.ga.setText((activity.getResources().getString(R.string.Rs))+" "+total_amount.get(position).toString());*/

            holder1.dt.setText(SetGmtTime(arrayListThreeStarClubIncomeList.get(position).getDate()));
            holder1.rnk.setText(arrayListThreeStarClubIncomeList.get(position).getRank());

            //holder1.ga.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListThreeStarClubIncomeList.get(position).getTotalIncome());
            holder1.ga.setText("" + Config.formatter.format(arrayListThreeStarClubIncomeList.get(position).getTotalIncome()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

            holder1.listIncomeDetailsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_starclub_income, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.sa = (TextView) convertView1.findViewById(R.id.share_amt_starpool1);
                        holder2.td = (TextView) convertView1.findViewById(R.id.tds1);
                        holder2.net = (TextView) convertView1.findViewById(R.id.net_amt_starpool1);
                        holder2.shr = (TextView) convertView1.findViewById(R.id.shares_starpool1);
                        convertView1.setTag(holder2);
                    }
                    else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                   /* holder2.sa.setText((activity.getResources().getString(R.string.Rs))+" "+share_amount.get(position).toString());
                    holder2.td.setText(tds.get(position).toString());
                    holder2.net.setText((activity.getResources().getString(R.string.Rs))+" "+net_amount.get(position).toString());
                    holder2.shr.setText(shares.get(position).toString());*/

                    //holder2.sa.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListThreeStarClubIncomeList.get(position).getShareAmount());
                    holder2.sa.setText("" + Config.formatter.format(arrayListThreeStarClubIncomeList.get(position).getShareAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.td.setText((activity.getResources().getString(R.string.Rs))+" "+String.valueOf(arrayListThreeStarClubIncomeList.get(position).getTDS()));
                    holder2.td.setText("" + Config.formatter.format(arrayListThreeStarClubIncomeList.get(position).getTDS()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.net.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListThreeStarClubIncomeList.get(position).getNetAmount());
                    holder2.net.setText("" + Config.formatter.format(arrayListThreeStarClubIncomeList.get(position).getNetAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder2.shr.setText(arrayListThreeStarClubIncomeList.get(position).getShares().toString());


                    dialog1.show();
                }
            });

            holder1.rnk.setTypeface(tf1);
            holder1.dt.setTypeface(tf1);
            holder1.ga.setTypeface(tf1);
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListThreeStarClubIncomeList.clear();
    }
}

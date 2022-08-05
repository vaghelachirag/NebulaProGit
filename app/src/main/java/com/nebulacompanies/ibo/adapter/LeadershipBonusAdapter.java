package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.LeadershipBonusList;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 10/27/2016.
 */
public class LeadershipBonusAdapter extends BaseAdapter {

   /* private static ArrayList date, bonus, tds, payout;*/
    Activity activity;
    ArrayList<LeadershipBonusList> arrayListLeadershipBonus = new ArrayList<>();
    public LeadershipBonusAdapter(Activity activity, ArrayList<LeadershipBonusList> LeadershipBonusLists) {
        super();
        this.activity = activity;
        arrayListLeadershipBonus.clear();
        arrayListLeadershipBonus.addAll(LeadershipBonusLists);
    }

    @Override
    public int getCount() {
        return arrayListLeadershipBonus.size();
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
        TextView dt, lbonus, tds_, payout_;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_leadership_bonus, null);
            holder = new ViewHolder();
            holder.dt = (TextView) convertView.findViewById(R.id.date_1);
            holder.lbonus = (TextView) convertView.findViewById(R.id.bonus_1);
            holder.tds_ = (TextView) convertView.findViewById(R.id.tds_1);
            holder.payout_ = (TextView) convertView.findViewById(R.id.payout_1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position < arrayListLeadershipBonus.size()) {
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
            /*holder.dt.setText(SetDateFormat(date.get(position).toString()));
            holder.lbonus.setText((activity.getResources().getString(R.string.Rs))+" "+ bonus.get(position).toString());
            holder.tds_.setText((activity.getResources().getString(R.string.Rs))+" "+ tds.get(position).toString());
            holder.payout_.setText((activity.getResources().getString(R.string.Rs))+" "+ payout.get(position).toString());*/

            holder.dt.setText(SetGmtTime(arrayListLeadershipBonus.get(position).getDate()));
            holder.lbonus.setText((activity.getResources().getString(R.string.Rs))+" "+ arrayListLeadershipBonus.get(position).getBonusPayment());
            holder.tds_.setText((activity.getResources().getString(R.string.Rs))+" "+ arrayListLeadershipBonus.get(position).getTDS());
            holder.payout_.setText((activity.getResources().getString(R.string.Rs))+" "+ arrayListLeadershipBonus.get(position).getPayoutAmount());


        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListLeadershipBonus.clear();
    }
}


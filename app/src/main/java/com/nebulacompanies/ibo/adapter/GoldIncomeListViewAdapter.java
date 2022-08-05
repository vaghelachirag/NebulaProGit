package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.GoldIncomeList;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 7/5/2016.
 */
public class GoldIncomeListViewAdapter extends BaseAdapter{

    Activity activity;
    String outputDate;
    private ArrayList<GoldIncomeList> arrayListGoldIncomeListList = new ArrayList<>();
    public GoldIncomeListViewAdapter(Activity activity, ArrayList<GoldIncomeList> GoldIncomeLists) {
        super();
        this.activity = activity;
        arrayListGoldIncomeListList.clear();
        arrayListGoldIncomeListList.addAll(GoldIncomeLists);
    }

    @Override
    public int getCount() {
        return arrayListGoldIncomeListList.size();
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
        TextView cdate, rank_, goldincome;
        LinearLayout lnGoldIncome;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_gold_income, null);
            holder1 = new ViewHolder1();
            holder1.cdate = (TextView) convertView.findViewById(R.id.closingdate_1);
            holder1.rank_ = (TextView) convertView.findViewById(R.id.rank_1);
            holder1.goldincome = (TextView) convertView.findViewById(R.id.goldincome1);
            holder1.lnGoldIncome = (LinearLayout) convertView.findViewById(R.id.ln_gold_income);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if(position < arrayListGoldIncomeListList.size()) {
          /*  if(position %2 == 1)
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
                holder1.lnGoldIncome.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                holder1.lnGoldIncome.setBackgroundResource(R.color.table_bg_even);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            /*holder1.cdate.setText(closing_date.get(position).toString());
            String dtStart = closing_date.get(position).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            try {
                Date date = format.parse(dtStart);
                outputDate = outputFormat.format(date);
                System.out.println(outputDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

            holder1.cdate.setText(SetGmtTime(arrayListGoldIncomeListList.get(position).getDate()));
            holder1.rank_.setText(arrayListGoldIncomeListList.get(position).getRank());

            //holder1.goldincome.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListGoldIncomeListList.get(position).getIncome().toString());
            holder1.goldincome.setText(""+ Config.formatter.format(arrayListGoldIncomeListList.get(position).getIncome()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListGoldIncomeListList.clear();
    }
}



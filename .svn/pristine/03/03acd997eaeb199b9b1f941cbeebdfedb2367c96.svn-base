package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.MiniBonanzaLegDetails;

import java.util.ArrayList;


/**
 * Created by Palak Mehta on 01-May-18.
 */

public class MiniBonanzaLegDetailsAdapter extends BaseAdapter {

    Activity activity;
    private ArrayList<MiniBonanzaLegDetails> miniBonanzaLegDetails = new ArrayList<>();

    public MiniBonanzaLegDetailsAdapter(Activity activity, ArrayList<MiniBonanzaLegDetails> legDetails) {
        super();
        this.activity = activity;
        miniBonanzaLegDetails.clear();
        miniBonanzaLegDetails.addAll(legDetails);
    }

    @Override
    public int getCount() {
        return miniBonanzaLegDetails.size();
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
        TextView srNo, iboId, iboName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_minibonanza_legdetails, null);
            holder1 = new ViewHolder1();
            holder1.srNo = (TextView) convertView.findViewById(R.id.mini_bonanza_sr);
            holder1.iboId = (TextView) convertView.findViewById(R.id.mini_bonanza_iboid);
            holder1.iboName = (TextView) convertView.findViewById(R.id.mini_bonanza_iboname);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if (position < miniBonanzaLegDetails.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }

            int i = position + 1;
            holder1.srNo.setText("" + i);
            holder1.iboId.setText(miniBonanzaLegDetails.get(position).getIBOID());
            holder1.iboName.setText(miniBonanzaLegDetails.get(position).getFirstName());
            if(miniBonanzaLegDetails.get(position).getLastName() != null){
                holder1.iboName.append(" " + miniBonanzaLegDetails.get(position).getLastName());
            }
        }

        return convertView;
    }

    public void clearData() {
        // clear the data
        miniBonanzaLegDetails.clear();
    }
}

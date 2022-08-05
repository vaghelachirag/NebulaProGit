package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.DreamVolumeList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;


/**
 * Created by Sagar Virvani on 21-11-2017.
 */

public class DreamVolumeAdapter extends BaseAdapter {
    Activity activity;
    //ArrayList date,dv;
    ArrayList<DreamVolumeList> arrayListDreamVolumeList = new ArrayList<>();
    public DreamVolumeAdapter(Activity activity, ArrayList<DreamVolumeList> dreamVolumeLists) {
        super();
        this.activity = activity;
        arrayListDreamVolumeList.clear();
        arrayListDreamVolumeList.addAll(dreamVolumeLists);

    }
    @Override
    public int getCount()
    {
        return arrayListDreamVolumeList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    private class ViewHolder1 {
        MyTextView Date, dv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = mInflater1.inflate(R.layout.list_dream_volume, null);
            holder1 = new ViewHolder1();
            holder1.Date = (MyTextView) convertView.findViewById(R.id.date_txt);
            holder1.dv = (MyTextView) convertView.findViewById(R.id.dv_txt);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }
        if(position < arrayListDreamVolumeList.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }

           /* holder1.Date.setText(SetDateFormat(date.get(position).toString()));
            holder1.dv.setText(dv.get(position).toString());*/
            holder1.Date.setText(SetGmtTime(arrayListDreamVolumeList.get(position).getDate()));
            holder1.dv.setText(arrayListDreamVolumeList.get(position).getTotalDv().toString());
        }
        return convertView;
    }
    public void clearData() {
        // clear the data
       arrayListDreamVolumeList.clear();
    }
}

package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.NewJoineeList;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 22-Feb-18.
 */

public class NewJoineesAdapter extends BaseAdapter {

    Activity activity;
    // private static ArrayList id, name, date, gender;
    private static LayoutInflater inflater = null;
    ArrayList<NewJoineeList> arrayListNewJoineesAdapters = new ArrayList<>();

    public NewJoineesAdapter(Activity activity1, ArrayList<NewJoineeList> newJoineesAdapters) {
        this.activity = activity1;
        arrayListNewJoineesAdapters.clear();
        arrayListNewJoineesAdapters.addAll(newJoineesAdapters);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayListNewJoineesAdapters.size();
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
        TextView memberNameID, joiningDate, place;
        ImageView genderImageView, legImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater1 = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.list_new_joinees, null);
            holder = new ViewHolder();
            holder.genderImageView = (ImageView) convertView.findViewById(R.id.list_item_gender); // name
            holder.legImageView = (ImageView) convertView.findViewById(R.id.list_item_leg_img); // name
            holder.memberNameID = (TextView) convertView.findViewById(R.id.list_item_name_id); // name
            holder.joiningDate = (TextView) convertView.findViewById(R.id.list_item_joining_date); // id
            holder.place = (TextView) convertView.findViewById(R.id.list_item_place); // date
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position < arrayListNewJoineesAdapters.size()) {
            holder.memberNameID.setText(arrayListNewJoineesAdapters.get(position).getIBOID() + " " + arrayListNewJoineesAdapters.get(position).getIBOName());
            holder.joiningDate.setText(SetGmtTime(arrayListNewJoineesAdapters.get(position).getDOJ()));
            if (arrayListNewJoineesAdapters.get(position).getState().equalsIgnoreCase("")) {
                holder.place.setText(arrayListNewJoineesAdapters.get(position).getCity());
            }
            else {

                holder.place.setText(arrayListNewJoineesAdapters.get(position).getCity() + ", " + arrayListNewJoineesAdapters.get(position).getState());
            }

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListNewJoineesAdapters.clear();
    }
}

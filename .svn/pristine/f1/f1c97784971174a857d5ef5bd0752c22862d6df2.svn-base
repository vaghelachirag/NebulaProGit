package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.RowItemInfo;
import com.nebulacompanies.ibo.view.JustifiedTextView;

import java.util.List;

/**
 * Created by Palak Mehta on 10/8/2016.
 */

public class BadgeAdapter extends BaseAdapter {
    Context context;
    List<RowItemInfo> rowItemsinfo;
    int pos;

    public BadgeAdapter(Context context, List<RowItemInfo> items, Integer pos) {
        this.context = context;
        this.rowItemsinfo = items;
        this.pos = pos;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        JustifiedTextView txtTitle, textdec;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_show_myincome, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imgmyincome);
            holder.textdec = (JustifiedTextView) convertView.findViewById(R.id.mysin);
            holder.txtTitle = (JustifiedTextView) convertView.findViewById(R.id.myincomename);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItemInfo rowItemInfo = (RowItemInfo) getItem(position);

        if(position < rowItemsinfo.size()) {
            holder.txtTitle.setText(rowItemInfo.getTitle());
            holder.imageView.setImageResource(rowItemInfo.getImageId());
            holder.textdec.setText(rowItemInfo.getTextdec());


        }

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItemsinfo.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItemsinfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItemsinfo.indexOf(getItem(position));
    }

    private int selectedItem;

    public void setSelectedItem(int position) {
        selectedItem = position;
    }
}

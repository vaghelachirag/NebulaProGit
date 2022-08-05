package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.UpdateList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Palak Mehta on 21-Feb-18.
 */

public class DashboardUpdatesAdapter extends BaseAdapter {

    Context context;
    ArrayList<UpdateList> arrayListUpdates = new ArrayList<>();

    public DashboardUpdatesAdapter(Context context, ArrayList<UpdateList> updateLists) {
        this.context = context;
        arrayListUpdates.clear();
        arrayListUpdates.addAll(updateLists);
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle, txtDesc, txtDate;
        LinearLayout videoLinearLayout;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_updates, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.list_item_label);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.list_item_description);
            //holder.txtDate = (TextView) convertView.findViewById(R.id.list_item_date);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.i("INFO", "Updates adapter: " + arrayListUpdates.size());

        if(position < arrayListUpdates.size()) {
            //if (arrayListUpdates.get(position).getNotificationType().equals("Personal")) {
                holder.txtTitle.setText(arrayListUpdates.get(position).getTitle());
                holder.txtDesc.setText(Html.fromHtml(arrayListUpdates.get(position).getInformation()));
                //holder.txtDate.setText(SetGmtTime(arrayListUpdates.get(position).getCreatedOnInfo()));

            Glide.with( context )
                    .load( arrayListUpdates.get(position).getIcon() ).fitCenter()
                    .diskCacheStrategy( DiskCacheStrategy.ALL)
                    .placeholder( R.drawable.nebula_effect )
                    .into( holder.imageView );



           // }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return arrayListUpdates.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        // clear the data
        arrayListUpdates.clear();
    }

}

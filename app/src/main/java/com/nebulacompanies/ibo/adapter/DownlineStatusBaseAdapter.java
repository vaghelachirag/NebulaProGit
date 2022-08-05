package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.DownlineRankList;
import com.nebulacompanies.ibo.view.MyTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Palak Mehta on 4/11/2016.
 */
public class DownlineStatusBaseAdapter extends BaseAdapter {

    private Activity activity;
   // private static ArrayList count, rank;
    ArrayList<DownlineRankList> arrayListDownlineStatusDetailsList=new ArrayList<>();
    /*private int[] imageArray = {
            R.drawable.member,
            R.drawable.ibo,
            R.drawable.bronze,
            R.drawable.silver,
            R.drawable.gold,
            R.drawable.platinum,
            R.drawable.star_platinum,
            R.drawable.two_star_platinum,
            R.drawable.three_star_platinum,
            R.drawable.four_star_platinum,
            R.drawable.five_star_platinum,
            R.drawable.six_star_platinum,
            R.drawable.seven_star_platinum,
            R.drawable.crown,
            R.drawable.noble_crown,
            R.drawable.royal_crown,
    };*/

    public DownlineStatusBaseAdapter(Activity activity, ArrayList<DownlineRankList> DownlineRankList) {
        this.activity = activity;
        arrayListDownlineStatusDetailsList.clear();
        arrayListDownlineStatusDetailsList.addAll(DownlineRankList);
        //inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.i("INFO", "DownlineStatusBaseAdapter : " + arrayListDownlineStatusDetailsList);
    }

    @Override
    public int getCount() {
        Log.i("INFO", "DownlineStatusBaseAdapter getCount : " + arrayListDownlineStatusDetailsList.size());
        return arrayListDownlineStatusDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i("INFO", "DownlineStatusBaseAdapter getItem : " + position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        Log.i("INFO", "DownlineStatusBaseAdapter getItemId : " + position);
        return position;
    }

    private class ViewHolder{
        MyTextView r,c;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("INFO", "DownlineStatusBaseAdapter getView : ");
        final ViewHolder holder;
      //  ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_downline, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_status);
            holder.r = (MyTextView) convertView.findViewById(R.id.rank); // rank
            holder.c = (MyTextView) convertView.findViewById(R.id.count); // count
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position < arrayListDownlineStatusDetailsList.size()) {
            //listAnimation(convertView.getContext(), convertView, position);
           // holder.i.setBackgroundResource(imageArray[position]);
          //  ViewHolder finalHolder = holder;

            Glide.with( activity )
                            .load( arrayListDownlineStatusDetailsList.get(position).getRankIcon().replaceAll(" ","%20") ).fitCenter()
                            .diskCacheStrategy( DiskCacheStrategy.ALL)
                            .placeholder( R.drawable.nebula_effect )
                            .into( holder.imageView );


            /*Picasso.with(activity)
                    .load(arrayListDownlineStatusDetailsList.get(position).getRankIcon().replaceAll(" ","%20"))
                    .into(holder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imageView.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onError() {

                        }
                    });*/
            holder.r.setText(arrayListDownlineStatusDetailsList.get(position).getRank().toString());
            holder.c.setText(arrayListDownlineStatusDetailsList.get(position).getCount().toString());
        }

        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListDownlineStatusDetailsList.clear();
    }
}

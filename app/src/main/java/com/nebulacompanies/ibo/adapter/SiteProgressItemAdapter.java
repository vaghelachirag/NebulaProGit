package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ShowFullScreenSiteProgress;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.SiteProgressList;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightViewNew;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SiteProgressItemAdapter extends RecyclerView.Adapter<SiteProgressItemAdapter.ItemViewHolder> {

    private List<SiteProgressList> Items;
    OnItemClickListener mItemClickListener;
    Context mContext;
    ArrayList<String> imagepic;
    ArrayList<String> date;
    ArrayList<Integer> count;
    boolean product_type_sub;
    boolean OnBack;
    String name, monthIntext, year,PRODUCT_NAME;
    int productid, month;

    SharedPreferences walkthrough;
    public SiteProgressItemAdapter(Context context, String name, ArrayList<String> imagepic, ArrayList<String> date, ArrayList<Integer> count, String monthIntext, String year, int productid, int month, boolean product_type_sub, boolean OnBack, String PRODUCT_NAME) {
        Items = new ArrayList<>();
        mContext = context;
        this.imagepic=imagepic;
        this.date=date;
        this.count=count;
        this.name=name;
        this.monthIntext=monthIntext;
        this.year=year;
        this.month=month;
        this.product_type_sub=product_type_sub;
        this.OnBack=OnBack;
        this.PRODUCT_NAME=PRODUCT_NAME;
        this.productid=productid;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgUserAvatar;

        public ItemViewHolder(View view) {
            super(view);
            imgUserAvatar = view.findViewById(R.id.picture1);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        SiteProgressList model = Items.get(position);

        if (position==Items.size()-1)
         {
             holder.imgUserAvatar.setVisibility(View.GONE);
         }
         else
         {
             holder.imgUserAvatar.setVisibility(View.VISIBLE);
         }
        Picasso.with(mContext).load(model.getThumbnailImage()).placeholder(R.drawable.nebula_effect).into(holder.imgUserAvatar);
        RecyclerView.ViewHolder finalHolder1 = holder;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            walkthrough = mContext.getSharedPreferences(PrefUtils.prefEventwalk, 0);
            if (position==1 && walkthrough.getBoolean("walkthrough_first_time_site_sup", true)) {
                SharedPreferences skipMainGet = mContext.getSharedPreferences(PrefUtils.prefSkipmain, 0);
                boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                SharedPreferences skipModuleGet = mContext.getSharedPreferences(PrefUtils.prefSiteprogress, 0);
                boolean isSkipModule = skipModuleGet.getBoolean(PrefUtils.prefSiteprogress, false);

                SharedPreferences guideViewSkipSiteProgressBack = mContext.getSharedPreferences(PrefUtils.prefViewspot, 0);
                boolean isSkipModuleSiteProgressBack = guideViewSkipSiteProgressBack.getBoolean("MainSiteProgressViewSpotLight", false);

                if (!isSkipMain && !isSkipModule && !isSkipModuleSiteProgressBack){
                SpotlightViewNew spotLight = new SpotlightViewNew.Builder((Activity) mContext)
                        .introAnimationDuration(400)
                        .enableRevealAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .setTypeface(FontUtil.get(mContext, "fonts/Montserrat-Regular.ttf"))
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(18)
                        .headingTvText("Site Images")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(14)
                        .targetPadding(10)
                        .subHeadingTvText("More about this section")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(finalHolder1.itemView)
                        .lineAnimDuration(400)
                        .targetPadding(12)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(false)
                        .dismissOnBackPress(false)
                        .enableDismissAfterShown(false)
                        .show();

                         holder.itemView.setOnClickListener(view -> {
                             Intent intent = new Intent(mContext, ShowFullScreenSiteProgress.class);
                             intent.putExtra("id", position);
                             intent.putExtra("Product_Name", PRODUCT_NAME);
                             intent.putExtra("Name", name);
                             intent.putExtra("Month", month);
                             intent.putExtra("MonthInText", monthIntext);
                             intent.putExtra("Year",year);
                             intent.putExtra("imagepic",imagepic);
                             intent.putExtra("date",date);
                             intent.putExtra("ProjectId",productid);
                             intent.putExtra("first_time_site_sup", true);

                             SharedPreferences guideViewSkipEventMain = mContext.getSharedPreferences(PrefUtils.prefViewspot, MODE_PRIVATE);
                             SharedPreferences.Editor et = guideViewSkipEventMain.edit();
                             et.putBoolean("MainSiteProgressViewSpotLight", true);
                             et.apply();
                             mContext.startActivity(intent);
                         });
                walkthrough.edit().putBoolean("walkthrough_first_time_site_sup", false).apply();
            }
            }
        }, 3500);



        holder.imgUserAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ShowFullScreenSiteProgress.class);
            intent.putExtra("id", position);
            //intent.putExtra("image_list", arrayListSiteProgressImages);
            intent.putExtra("Product_Name", PRODUCT_NAME);
            intent.putExtra("Name", name);
            intent.putExtra("Month", month);
            intent.putExtra("MonthInText", monthIntext);
            intent.putExtra("Year",year);
            intent.putExtra("imagepic",imagepic);
            intent.putExtra("date",date);
            intent.putExtra("ProjectId",productid);

            intent.putExtra("OnBack", false);
            intent.putExtra("first_time_site_sup", false);
            SharedPreferences guideViewSkipEventMain = mContext.getSharedPreferences(PrefUtils.prefViewspot, MODE_PRIVATE);
            SharedPreferences.Editor et = guideViewSkipEventMain.edit();
            et.putBoolean("MainSiteProgressViewSpotLight", true);
            et.apply();
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setItems(List<SiteProgressList> items) {
        Items = items;
        notifyDataSetChanged();
    }

    public void clearData() {
        // clear the data
        Items.clear();
    }

    public void add(SiteProgressList r) {
        Items.add(r);
        notifyItemInserted(Items.size() - 1);
        // notifyItemInserted(Items.size());
    }

    public void addAll(List<SiteProgressList> moveResults) {
        for (SiteProgressList result : moveResults) {
            if (!moveResults.contains(result.getThumbnailImage()))
            {
                add(result);
            }
            // add(result);
        }
    }

    public void remove(SiteProgressList r) {
        int position = Items.indexOf(r);
        if (position > -1) {
            Items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        Items.clear();
        /*while (getItemCount() > 0) {
            remove(getItem(0));
        }*/
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        add(new SiteProgressList());
    }

    public void removeLoadingFooter() {

        int position = Items.size() - 1;
        SiteProgressList result = getItem(position);

        if (result != null) {
            Items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SiteProgressList getItem(int position) {
        return Items.get(position);
    }
}

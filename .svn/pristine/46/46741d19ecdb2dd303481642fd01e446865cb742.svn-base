package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.SiteProductList;
import com.nebulacompanies.ibo.showcaseview.GuideView;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;


public class SiteProductsAdapter extends RecyclerView.Adapter<SiteProductsAdapter.SiteProductsHolder> {
    private Context context;
    ArrayList<SiteProductList> arrayListSiteProductList = new ArrayList<>();
    int lastPosition = -1;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    String keyPro;



    public SiteProductsAdapter(Activity context, ArrayList<SiteProductList> siteProductLists, String keyPro) {
        this.context = context;
        this.keyPro = keyPro;
        arrayListSiteProductList.clear();
        arrayListSiteProductList.addAll(siteProductLists);
    }

    public class SiteProductsHolder extends RecyclerView.ViewHolder {

        // CardView cardViewPackage;
        MyTextView titletxtname;
        MyTextView detailstxtName;

        public SiteProductsHolder(View convertView) {
            super(convertView);
            context = convertView.getContext();
            //  cardViewPackage = convertView.findViewById(R.id.card_view);
            titletxtname = (MyTextView) convertView.findViewById(R.id.events_list_item1);
            detailstxtName = (MyTextView) convertView.findViewById(R.id.events_list_item2);
        }
    }

    @Override
    public SiteProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_site_products, parent, false);

        return new SiteProductsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SiteProductsHolder holder, final int position) {

        final SiteProductList siteProductList = arrayListSiteProductList.get(position);

        holder.titletxtname.setText(siteProductList.getProjectName());
        holder.detailstxtName.setText(siteProductList.getDetail());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");


        if (position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.item_animation_fall_down);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyPro.equals("Products")) {
                    /*Intent i = new Intent(context, ProductDescriptionAavaas.class);
                    i.putExtra("ProjectId", arrayListSiteProductList.get(position).getProjectId());
                    i.putExtra("ProjectName", arrayListSiteProductList.get(position).getProjectName());
                    i.putExtra("Product_type", false);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    SharedPreferences guideViewSkipEventMain = context.getSharedPreferences("MainProducts", MODE_PRIVATE);
                    SharedPreferences.Editor et = guideViewSkipEventMain.edit();
                    et.putBoolean("guideViewSkipProductsMain", true);
                    et.apply();

                    context.startActivity(i);*/
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                } else if (keyPro.equals("SiteProducts")) {
                    /*Intent i = new Intent(context, SiteProgress.class);
                    i.putExtra("Product_Name", siteProductList.getProjectName());
                    i.putExtra("ProjectId", siteProductList.getProjectId());
                    i.putExtra("Product_type", false);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    SharedPreferences guideViewSkipEventMain = context.getSharedPreferences("MainSite", MODE_PRIVATE);
                    SharedPreferences.Editor et = guideViewSkipEventMain.edit();
                    et.putBoolean("guideViewSkipSiteMain", true);
                    et.apply();

                    context.startActivity(i);*/
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListSiteProductList.size();
    }

    public void clearData() {
        // clear the data
        arrayListSiteProductList.clear();
    }
}
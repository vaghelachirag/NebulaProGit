package com.nebulacompanies.ibo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.customBanner.InfinitePagerAdapter;
import com.nebulacompanies.ibo.customBanner.InfiniteViewPager;
import com.nebulacompanies.ibo.ecom.model.BanersList;
import com.nebulacompanies.ibo.ecom.utils.MyTextView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.model.BanerText;
import com.nebulacompanies.ibo.model.BanersTextList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;

public class DashboardDesignChange extends AppCompatActivity {

    Toolbar toolbar;
    MyTextViewEcom tvToolbarTitle;
    private ArrayList<BanersList> banersList = new ArrayList<>();
    private ArrayList<BanersTextList> banersTextList = new ArrayList<>();
    ImageView img;
    MockPagerBannerTextAdapter pagerBannerTextAdapter;
    InfiniteViewPager mViewPager3;
    APIInterface mAPIInterface;
    SharedPreferences prefss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_design_change);

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        img = toolbar.findViewById(R.id.img_back);
        img.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("Hi, KK Brothers");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getBannersTextList();

    }

    private void getBannersTextList() {
        Call<BanerText> wsCallingBanersTextList = mAPIInterface.getBanerTextList();
        wsCallingBanersTextList.enqueue(new Callback<BanerText>() {
            @Override
            public void onResponse(Call<BanerText> call, Response<BanerText> response) {
                if (response.isSuccessful()) {
                    banersTextList.clear();
                    if (response.code() == 200) {
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            mViewPager3.setVisibility(View.VISIBLE);
                            //  rlSiteProgress.setVisibility(View.VISIBLE);
                            banersTextList.addAll(response.body().getData());
                            pagerBannerTextAdapter = new MockPagerBannerTextAdapter(DashboardDesignChange.this, banersTextList);
                            mViewPager3.setAdapter(pagerBannerTextAdapter);
                            mViewPager3.setAutoScrollTime(6000);
                            mViewPager3.startAutoScroll();

                            prefss = PreferenceManager.getDefaultSharedPreferences(DashboardDesignChange.this);
                            SharedPreferences.Editor editor = prefss.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(banersTextList);
                            editor.putString("banersText", json);
                            editor.apply();


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BanerText> call, Throwable t) {

            }
        });
    }


    public class MockPagerBannerTextAdapter extends InfinitePagerAdapter {

        private LayoutInflater mInflater;
        private Activity mContext;

        private ArrayList<BanersTextList> mList = new ArrayList<>();

        public MockPagerBannerTextAdapter(Activity activity, ArrayList<BanersTextList> banersTextList) {
            super();
            this.mContext = activity;
            this.mList = banersTextList;
            mInflater = LayoutInflater.from(activity);
        }

        @Override
        public View getView(final int position, View view, ViewGroup container) {
            MockPagerBannerTextAdapter.ViewHolder holder;
            if (view != null) {
                holder = (MockPagerBannerTextAdapter.ViewHolder) view.getTag();
            } else {
                view = mInflater.inflate(R.layout.card_layout_text, container, false);
                holder = new MockPagerBannerTextAdapter.ViewHolder(view);
                view.setTag(holder);
            }
            BanersTextList item = mList.get(position);
            holder.position = position;
            holder.appName.setText(item.getDescription());
            if (item.getImage() != null) {
                Glide.with(mContext).load(item.getImage())
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.image);
            }

         /*   holder.rlBannerItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (llSettings.getVisibility() == View.VISIBLE) {
                        holder.rlBannerItem.setClickable(false);
                        holder.rlBannerItem.setEnabled(false);
                        mViewPager3.stopAutoScrollCustom();
                        mViewPager3.setPagingEnabled(false);
                        frameGuest.setClickable(false);
                        frameGuest.setEnabled(false);
                        rlGuestArea.setClickable(false);
                        rlGuestArea.setEnabled(false);
                        rlBanner.setEnabled(false);
                        rlBanner.setClickable(false);
                        mViewPager3.setBackgroundColor(getResources().getColor(R.color.transparent));
                    } else {
                        holder.rlBannerItem.setClickable(true);
                        holder.rlBannerItem.setEnabled(true);
                        rlBanner.setEnabled(true);
                        rlBanner.setClickable(true);
                        mViewPager3.setPagingEnabled(true);
                        frameGuest.setClickable(true);
                        frameGuest.setEnabled(true);
                        rlGuestArea.setClickable(true);
                        rlGuestArea.setEnabled(true);

                        mViewPager3.setBackground(getResources().getDrawable(R.drawable.nebula_text_effect));
                        if (item.getCategory() != null) {
                            if (item.getCategory().equalsIgnoreCase("Site Progress")) {
                                Intent sp = new Intent(DashboardDesignChange.this, SiteProducts.class);
                                sp.putExtra("keyPro", "SiteProducts");
                                sp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(sp);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }
                            if (item.getCategory().equalsIgnoreCase("Event")) {
                                Intent eve = new Intent(DashboardDesignChange.this, NewCompanyEvent.class);
                                eve.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(eve);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }
                            if (item.getCategory().equalsIgnoreCase("Video")) {
                              *//*  Intent vid = new Intent(GuestActivity.this, CompanyVideos.class);
                                vid.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(vid);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*//*

                                Uri uri = Uri.parse(item.getUrl());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setPackage("com.google.android.youtube");
                                startActivity(intent);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }
                            if (item.getCategory().equalsIgnoreCase("Weblink")) {
                                Uri uri = Uri.parse(item.getUrl());
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(browserIntent);
                            }
                            if (item.getCategory().equalsIgnoreCase("EDocuments")) {
                                Intent eb = new Intent(DashboardDesignChange.this, NewEDocuments.class);
                                eb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(eb);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }
                            if (item.getCategory().equalsIgnoreCase("Holidays")) {
                                Intent eb = new Intent(DashboardDesignChange.this, HolidayListActivity.class);
                                eb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(eb);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }

                        }
                    }

                }
            });*/
            return view;
        }


        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }


        private class ViewHolder {
            public int position;
            private MyTextView appName;
            private RelativeLayout rlBannerItem;
            private ImageView image;
            private CardView cardView;

            public ViewHolder(View v) {
                appName = (MyTextView) v.findViewById(R.id.item_tv);
                image = (ImageView) v.findViewById(R.id.image);
                rlBannerItem = (RelativeLayout) v.findViewById(R.id.rl_banner_item_text);
                cardView = (CardView) v.findViewById(R.id.card_view);
            }
        }

    }
}
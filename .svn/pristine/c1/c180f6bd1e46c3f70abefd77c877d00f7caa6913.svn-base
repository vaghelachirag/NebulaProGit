package com.nebulacompanies.ibo.ecom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.fragment.GenerateCouponFragment;
import com.nebulacompanies.ibo.ecom.fragment.PromoCodesFragment;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ReferralsPromotion extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    Session session;
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    MyTextViewEcom tvToolbarTitle;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referrals_promotion);

        initUI();
        initOnClick();
    }

    void initUI() {
        mProgressDialog = findViewById(R.id.progresbar);
        toolbar = findViewById(R.id.toolbar_search);

        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText(R.string.referelsandpromo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imgBackArrow = findViewById(R.id.img_back);
        session = new Session(this);
        viewPager = findViewById(R.id.viewpager_promos);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);

        tabLayout = (TabLayout) findViewById(R.id.tab_ref_promo);
        tabLayout.setupWithViewPager(viewPager);

        MyTextView tabRank = (MyTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabRank.setText(getResources().getString(R.string.generate_coupon));

        MyTextView tabPV = (MyTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabPV.setText(getResources().getString(R.string.promo));



    /*    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });*/
    }

    void initOnClick() {
        imgBackArrow.setOnClickListener(v -> onBackPressed());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GenerateCouponFragment(), getResources().getString(R.string.generate_coupon));
        adapter.addFragment(new PromoCodesFragment(), getResources().getString(R.string.promo));

        //adapter.addFragment(new IBOGeoLocationFragment(), getResources().getString(R.string.prompt_ibo_geo_location));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
/*
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Layout has happened here.
                        // Don't forget to remove your listener when you are done with it.
                        viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });*/
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
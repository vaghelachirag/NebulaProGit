
package com.nebulacompanies.ibo.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.fragments.IBOListPlacementTree;
import com.nebulacompanies.ibo.fragments.IBOListSponsorTree;
import com.nebulacompanies.ibo.view.CustomViewPager;
import com.nebulacompanies.ibo.walk.SpotlightView;

import java.util.ArrayList;
import java.util.List;

public class MyIboListActivityWeb extends AppCompatActivity {
    private TabLayout tabLayoutIBO;
    private CustomViewPager viewPager;
    InputMethodManager imm;
    String PREFS_WALKTHROUGH_IBOLIST = "ibolist";
    SpotlightView spotlightViewTree, spotlightViewSponsorTree;
    Handler handlerSponsorTree,handlerPlacementTree;
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ibo_list);

        init();
    }

    void init() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        txtTitle = findViewById(R.id.toolbar_title1);
        txtTitle.setText("My Downline");
        imgBack = findViewById(R.id.img_back);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager_ibo_list);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        tabLayoutIBO = (TabLayout) findViewById(R.id.tab_ibo_list);
        tabLayoutIBO.setupWithViewPager(viewPager);
        tabLayoutIBO.getTabAt(0).select();
        imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
        spotlightViewTree = new SpotlightView(this);
        spotlightViewSponsorTree = new SpotlightView(this);

        imgBack.setOnClickListener(v -> onBackPressed());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IBOListPlacementTree(), getResources().getString(R.string.PlacementTree));
        adapter.addFragment(new IBOListSponsorTree(), getResources().getString(R.string.SponsorTree));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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


    public void hideSpotLightTree() {
        if (handlerSponsorTree != null || handlerPlacementTree !=null) {
            handlerSponsorTree.removeCallbacksAndMessages(null);
            handlerSponsorTree.removeMessages(0);
            spotlightViewTree.setVisibility(View.GONE);
            spotlightViewSponsorTree.setVisibility(View.GONE);
        }
    }

}

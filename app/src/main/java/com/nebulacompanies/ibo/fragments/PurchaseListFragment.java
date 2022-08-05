package com.nebulacompanies.ibo.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.tabs.TabLayout;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.CustomViewPager;
import com.nebulacompanies.ibo.walk.SpotlightView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by Palak Mehta on 24-Feb-18.
 */

public class PurchaseListFragment extends Fragment {

    private TabLayout tabLayoutIBO;
    private CustomViewPager viewPager;
    InputMethodManager imm;
    String PREFS_WALKTHROUGH_IBOLIST = "ibolist";
    SpotlightView spotlightViewTree, spotlightViewSponsorTree;
    Handler handlerSponsorTree,handlerPlacementTree;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ibo_list, container, false);
        init(view);
        return view;
    }

    void init(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewpager_ibo_list);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        tabLayoutIBO = (TabLayout) view.findViewById(R.id.tab_ibo_list);
        tabLayoutIBO.setupWithViewPager(viewPager);
        tabLayoutIBO.getTabAt(0).select();
        imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
        spotlightViewTree = new SpotlightView(getActivity());
        spotlightViewSponsorTree = new SpotlightView(getActivity());


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new IBOListHolidays(), getResources().getString(R.string.PlacementTree));
        adapter.addFragment(new IBOListRealEstate(), getResources().getString(R.string.SponsorTree));
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

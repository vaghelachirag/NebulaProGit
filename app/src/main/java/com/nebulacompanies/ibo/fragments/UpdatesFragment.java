package com.nebulacompanies.ibo.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.model.UpdateList;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.CustomViewPager;
import com.nebulacompanies.ibo.view.MyTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class UpdatesFragment extends Fragment {

    private TabLayout tabLayout, tabLayout1;
    private CustomViewPager viewPager;
    Boolean  isDisplayTeam = false;
    public static ArrayList<UpdateList> arrayListUpdates = new ArrayList<>();
    View view;
    Session session;
    LinearLayout llMenu, lnRank;//,team_updateDownline;
    LinearLayout updatesLinearLayout;//,llMenu_downline;
    Context mContext = null;
    NestedScrollView nestedScrollView;
    MyTextView tvNextTeamUpdate, tvNextGeneration;
    MaterialProgressBar mProgressDialog;
    public static String REFRESH_JOINEE = "ref_joinee";
    public static String REFRESH_DOWNLINE = "ref_downline";
    public static String REFRESH_UPDATETEAM = "ref_updateteam";
    public static String REFRESH_UPDATE = "ref_update";

    private final BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(BusinessActivity.VISIBLEOthers)) {
                Intent inten = new Intent(REFRESH_UPDATE);
                Objects.requireNonNull(getActivity()).sendBroadcast(inten);
                inten = new Intent(REFRESH_JOINEE);
                getActivity().sendBroadcast(inten);
                inten = new Intent(REFRESH_DOWNLINE);
                getActivity().sendBroadcast(inten);
                inten = new Intent(REFRESH_UPDATETEAM);
                getActivity().sendBroadcast(inten);
            }
        }
    };
    IntentFilter intentFilter;
    ImageView imageUpdate, imageTeamupdate;
    ImageView imageViewNewjoinee;
    ImageView imageViewDownline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.others_fragment, container, false);
        arrayListUpdates.clear();
        mContext = getActivity();
        intentFilter = new IntentFilter();
        intentFilter.addAction(BusinessActivity.VISIBLEOthers);
        (getActivity()).registerReceiver(rankReceiver, intentFilter);
        initView();
        init();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        (getActivity()).unregisterReceiver(rankReceiver);
    }

    private void initView() {
        updatesLinearLayout = view.findViewById(R.id.updates_main_layout);
        viewPager = view.findViewById(R.id.updates_pager);
        tabLayout = view.findViewById(R.id.updates_tabLayout);
        lnRank = (LinearLayout) view.findViewById(R.id.team_update);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
      //team_updateDownline = (LinearLayout) view.findViewById(R.id.team_update_downline);

        tvNextTeamUpdate = view.findViewById(R.id.tv_next_team_update);
        tvNextGeneration = view.findViewById(R.id.tv_next_generation);
        mProgressDialog = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        llMenu = (LinearLayout) view.findViewById(R.id.llMenu);
     // llMenu_downline = (LinearLayout) view.findViewById(R.id.llMenu_downline);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        session = new Session(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    void init() {
        if (getActivity() != null) {

                if (!arrayListUpdates.isEmpty()) {
                    isDisplayTeam = true;
                }
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(1);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            createTabIcons();

            CustomViewPager viewPager1 = view.findViewById(R.id.others_pager);
            tabLayout1 = view.findViewById(R.id.others_tabLayout);
            viewPager1.setCurrentItem(0);
            viewPager1.setOffscreenPageLimit(1);
            setupViewPager1(viewPager1);
            tabLayout1.setupWithViewPager(viewPager1);
            createTabIcons_ranks();

            setOnclikTab();
        }
    }

    private void setOnclikTab() {
        imageUpdate.setOnClickListener(v -> {
            if (imageUpdate.getAnimation() == null &&
                    imageTeamupdate.getAnimation() == null &&
                    imageViewNewjoinee.getAnimation() == null &&
                    imageViewDownline.getAnimation() == null && session.getLogin()) {
                    Intent intent = new Intent();
                    intent.setAction(REFRESH_UPDATE);
                    Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                    startRotateAnim(imageUpdate);
            }
        });
        imageTeamupdate.setOnClickListener(v -> {
            if (imageUpdate.getAnimation() == null &&
                    imageTeamupdate.getAnimation() == null &&
                    imageViewNewjoinee.getAnimation() == null &&
                    imageViewDownline.getAnimation() == null && session.getLogin()) {
                Intent intent = new Intent();
                intent.setAction(REFRESH_UPDATETEAM);
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                startRotateAnim(imageTeamupdate);
            }
        });
        imageViewNewjoinee.setOnClickListener(v -> {
            if (imageUpdate.getAnimation() == null &&
                    imageTeamupdate.getAnimation() == null &&
                    imageViewNewjoinee.getAnimation() == null &&
                    imageViewDownline.getAnimation() == null && session.getLogin()) {
                Intent intent = new Intent();
                intent.setAction(REFRESH_JOINEE);
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                startRotateAnim(imageViewNewjoinee);
            }
        });
        imageViewDownline.setOnClickListener(v -> {
            if (imageUpdate.getAnimation() == null &&
                    imageTeamupdate.getAnimation() == null &&
                    imageViewNewjoinee.getAnimation() == null &&
                    imageViewDownline.getAnimation() == null && session.getLogin()) {
                Intent intent = new Intent();
                intent.setAction(REFRESH_DOWNLINE);
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                startRotateAnim(imageViewDownline);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void createTabIcons() {

        @SuppressLint("InflateParams") LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_tablayout, null);
        MyTextView myTextView = linearLayout.findViewById(R.id.textview);
        myTextView.setText("Your Updates");
        imageUpdate = linearLayout.findViewById(R.id.refresh);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(linearLayout);

        @SuppressLint("InflateParams") LinearLayout linearLayout1 = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_tablayout, null);
        MyTextView myTextView1 = linearLayout1.findViewById(R.id.textview);
        myTextView1.setText("Team Updates");
        imageTeamupdate = linearLayout1.findViewById(R.id.refresh);

        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(linearLayout1);
    }

    @SuppressLint("SetTextI18n")
    private void createTabIcons_ranks() {

        @SuppressLint("InflateParams") LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_tablayout, null);
        MyTextView myTextView = linearLayout.findViewById(R.id.textview);
        myTextView.setText("New Joinees");
        imageViewNewjoinee = linearLayout.findViewById(R.id.refresh);

        Objects.requireNonNull(tabLayout1.getTabAt(0)).setCustomView(linearLayout);

        @SuppressLint("InflateParams") LinearLayout linearLayout1 = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.custom_tablayout, null);
        MyTextView myTextView1 = linearLayout1.findViewById(R.id.textview);
        myTextView1.setText("Downline Ranks");
        imageViewDownline = linearLayout1.findViewById(R.id.refresh);

        Objects.requireNonNull(tabLayout1.getTabAt(1)).setCustomView(linearLayout1);
    }

    private void setupViewPager(ViewPager viewPager_) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PersonalUpdatesFragment(), getResources().getString(R.string.personal_updates));
        adapter.addFragment(new TeamUpdatesFragment(), getResources().getString(R.string.team_updates));

        viewPager_.setAdapter(adapter);
        viewPager_.setCurrentItem(0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusinessActivity.othersTab = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
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

    private void setupViewPager1(ViewPager viewPager_) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NewJoineesFragment(), getResources().getString(R.string.new_joinees));
        adapter.addFragment(new DownlineRanksFragment(), getResources().getString(R.string.downline_ranks));
        viewPager_.setAdapter(adapter);
        viewPager_.setCurrentItem(0);
    }

    private void startRotateAnim(ImageView imgvw) {
        RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(400);
        imgvw.setEnabled(false);
        // Start animating the image
        imgvw.startAnimation(anim);
    }

    public void stopAnim(ImageView imgvw,MyTextView textvw) {
        try {
            imgvw.setEnabled(true);
            if(textvw!=null)
            textvw.setEnabled(true);
            imgvw.setAnimation(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
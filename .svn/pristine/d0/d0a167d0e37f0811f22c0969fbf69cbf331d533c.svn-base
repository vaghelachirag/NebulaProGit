package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.transition.TransitionInflater;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nebulacompanies.ibo.Base2Activity;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.fragments.CompanyEventsFragment;
import com.nebulacompanies.ibo.util.Config;

import java.util.ArrayList;
import java.util.List;

public class NewCompanyEvent extends Base2Activity {
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;
    Boolean isRefreshed = false;
    Boolean isNotificationClicked = false;
    private int type;
    boolean OnBackMain = false;
    SharedPreferences prefsFirstTime;
    Toolbar toolbar;
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_company_event);
        Utils.darkenStatusBar(this,R.color.colorNotification);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            isNotificationClicked = b.getBoolean("Notification_Click");
            OnBackMain = b.getBoolean("OnBack");
        }

        if (isNotificationClicked) {
            Config.NOTIFICATION_COUNT--;
        }

        setActionBar();
        init();
        setupWindowAnimations();
    }


    void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);

        txtTitle.setText("Events");
        imgBack.setOnClickListener(v -> onBackPressed());
    }

    void init() {
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        viewPager = findViewById(R.id.edocs_viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.edocs_tabs);
        tabLayout.setupWithViewPager(viewPager);


        prefsFirstTime = PreferenceManager.getDefaultSharedPreferences(this);
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(tf1);
                    ((TextView) tabViewChild).setTextSize(16);
                }
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Company Event
        adapter.addFragment(new CompanyEventsFragment(), getResources().getString(R.string.prompt_places_to_visit));
        /* adapter.addFragment(new CompanyEventsFragment(), getResources().getString(R.string.video));*/
        viewPager.setAdapter(adapter);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
      super.onBackPressed();
    }

    //Animation stuff
    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        Transition transition;

        if (type == TYPE_PROGRAMMATICALLY) {
        transition = buildEnterTransition();
            getWindow().setEnterTransition(transition);
        } else {
         //   transition= TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
        }
      // getWindow().setEnterTransition(transition);
    }


    @SuppressLint("NewApi")
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide();
        /*enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        enterTransition.setSlideEdge(Gravity.RIGHT);*/
        return enterTransition;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }



}
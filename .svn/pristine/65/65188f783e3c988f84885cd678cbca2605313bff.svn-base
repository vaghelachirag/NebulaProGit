package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nebulacompanies.ibo.Base2Activity;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class NewEDocuments extends Base2Activity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    Boolean isNotificationClicked = false;
    private int type;
    SharedPreferences walkthroughEBrochs, walkthroughBookingForm, walkthroughLeaflets;
    LinearLayout tabStrip;
    boolean OnBackMainEdco = false;
    SharedPreferences prefsFirstTime;
    final String PREFS_WALKTHROUGH_DOCS_BACK = "docsbackwalkthrough";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list_edocuments);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            isNotificationClicked = b.getBoolean("Notification_Click");
            OnBackMainEdco = b.getBoolean("OnBackEdco");
        }

        if (isNotificationClicked) {
            Config.NOTIFICATION_COUNT--;
        }
        prefsFirstTime = getSharedPreferences(PrefUtils.prefDocuments, 0);
        setActionBar();
        init();
        setupWindowAnimations();

        SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
        boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
        if (!isSkipMain) {
            disableScroll();
        }
        tabStrip = ((LinearLayout) tabLayout.getChildAt(0));

        walkthroughEBrochs = getSharedPreferences(PrefUtils.prefMyprefsfile, 0);
        if (walkthroughEBrochs.getBoolean("walkthrough_e_documents", true)) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
                    boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                    SharedPreferences skipModuleGetDocx = getSharedPreferences(PrefUtils.prefSkipEdocument, 0);
                    boolean isSkipModuleDocx = skipModuleGetDocx.getBoolean("guideviewModuleskipEDocuments", false);
                    if (!isSkipMain && !isSkipModuleDocx) {

                        if (!prefsFirstTime.getBoolean("firstTimeEdoc", false)) {
                            // run your one time code here
                            viewPager.setPagingEnabled(false);

                            LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
                            tabStrip.setEnabled(false);
                            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                                tabStrip.getChildAt(i).setClickable(false);
                            }
                            // mark first time has ran.
                            SharedPreferences.Editor editor = prefsFirstTime.edit();
                            editor.putBoolean("firstTimeEdoc", true);
                            editor.apply();
                        }
                    } else {
                        viewPager.setPagingEnabled(true);

                        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
                        tabStrip.setEnabled(true);
                        for (int i = 0; i < tabStrip.getChildCount(); i++) {
                            tabStrip.getChildAt(i).setClickable(true);
                        }
                        SharedPreferences.Editor editor = prefsFirstTime.edit();
                        editor.putBoolean("firstTimeEdoc", true);
                        editor.apply();
                    }
                }
            }, 3500);
        }

    }

    void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.edocuments_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("E-Documents");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void init() {
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        viewPager = (CustomViewPager) findViewById(R.id.edocs_viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.edocs_tabs);
        tabLayout.setupWithViewPager(viewPager);
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                }
                if (position == 1) {  // if you want the second page, for example

                    walkthroughBookingForm = getSharedPreferences(PrefUtils.prefMyprefsfile, 0);
                    if (walkthroughBookingForm.getBoolean("walkthrough_first_booking_form", true)) {
                        // displayTutorialBookingForm();
                        viewPager.setPagingEnabled(true);
                        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
                        tabStrip.setEnabled(true);
                        for (int i = 0; i < tabStrip.getChildCount(); i++) {
                            tabStrip.getChildAt(i).setClickable(true);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        /*adapter.addFragment(new EBrochures(), getResources().getString(R.string.ebro));
        adapter.addFragment(new BookingForms(), getResources().getString(R.string.bookingforms));
        adapter.addFragment(new Leaflets(), getResources().getString(R.string.leaflets));*/
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    //Animation stuff

    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        Transition transition;

        if (type == TYPE_PROGRAMMATICALLY) {
            transition = buildEnterTransition();
        } else {
           // transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
        }
      //  getWindow().setEnterTransition(transition);
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
        Bundle b = getIntent().getExtras();
        if (b != null) {
            isNotificationClicked = b.getBoolean("Notification_Click");
            OnBackMainEdco = b.getBoolean("OnBackEdco");
        }


    }

    private void disableScroll() {

        if (!prefsFirstTime.getBoolean("firstTimeEdocx", false)) {
            // run your one time code here
            tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
            tabStrip.setEnabled(false);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setClickable(false);
            }
            viewPager.setPagingEnabled(false);
            // mark first time has ran.
            SharedPreferences.Editor editor = prefsFirstTime.edit();
            editor.putBoolean("firstTimeEdocx", true);
            editor.apply();
        }
    }
}

package com.nebulacompanies.ibo.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.fragments.MyPurchases;
import com.nebulacompanies.ibo.fragments.MySales;
import com.nebulacompanies.ibo.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Palak Mehta on 7/6/2016.
 */
public class SalesAavaas extends AppCompatActivity {

    //  private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager pager;
    private LinearLayout lnAavaasSales;
    String project_name;
    int project_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            project_name = b.getString("ProjectName");
            project_id = b.getInt("ProjectId");
        }
       /* toolbar = (Toolbar) findViewById(R.id.sales_toolbar);
        setSupportActionBar(toolbar);*/
        setActionBar();

    }

    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(project_name);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
        pager = (CustomViewPager) findViewById(R.id.pager4);
        setupViewPager(pager);
        pager.setCurrentItem(0);
        pager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout) findViewById(R.id.sales_tabs);
        lnAavaasSales = (LinearLayout) findViewById(R.id.ln_aavaas_sales);
        tabLayout.setupWithViewPager(pager);

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
        lnAavaasSales.setVisibility(View.VISIBLE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MySales(), getResources().getString(R.string.my_sales));
        adapter.addFragment(new MyPurchases(), getResources().getString(R.string.my_purchases));
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
            switch (position) {
                case 0:
                    Fragment f1 = new MySales();
                    Bundle args1 = new Bundle();
                    args1.putInt("ProjectId", project_id);
                    args1.putString("ProjectName", project_name);
                    f1.setArguments(args1);
                    return f1;
                case 1:
                    Fragment f2 = new MyPurchases();
                    Bundle args2 = new Bundle();
                    args2.putInt("ProjectId", project_id);
                    args2.putString("ProjectName", project_name);
                    f2.setArguments(args2);
                    return f2;
            }
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
}

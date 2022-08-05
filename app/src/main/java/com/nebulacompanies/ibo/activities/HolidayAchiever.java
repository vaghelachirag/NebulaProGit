package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.fragments.CustomerAchieverFragment;
import com.nebulacompanies.ibo.fragments.CustomerSalesFragment;
import com.nebulacompanies.ibo.fragments.SponsorAchieverFragment;
import com.nebulacompanies.ibo.fragments.SponsorSalesFragment;
import com.nebulacompanies.ibo.model.HolidayAchieverBonus;
import com.nebulacompanies.ibo.model.HolidayAchieverBonusList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.view.CustomViewPager;
import com.nebulacompanies.ibo.view.MyTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;


/**
 * Created by Sagar Virvani on 08-03-2018.
 */

public class HolidayAchiever extends AppCompatActivity {

    /*SwipeRefreshLayout mSwipeRefreshLayout;*/
    CardView cardView1, cardView2, cardView3;
    MyTextView dateTextView;
    ImageView infoAchiever;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    public static final String TAG = "HolidayAchiever";
    ConnectionDetector cd;
    ProgressDialog mProgressDialog;

    private Toolbar toolbar;
    private TabLayout tabLayout, tabLayout1;
    private CustomViewPager viewPager, viewPager1;
    Boolean isRefreshed = false, isDisplayTeam = false;
    private APIInterface mAPIInterface;
    String IncomeName;
    HolidayAchieverBonusList holidayAchieverBonusList;

   /* HolidayAchieverList holidayAchieverList;


    private ArrayList<AchieverIBOList> arrayListAchieverIBOList = new ArrayList<>();

    private ArrayList<AchieverCustomerList> arrayListAchieverCustomerList = new ArrayList<>();*/

    LinearLayout holidayLinearLayout,incomeLinearLayout;
    CheckBox checkBox1, checkBox2;

    int achieverId = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holiday_achiever);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }

        setActionBar();
        init();
        checkBonusEligibility();
    }

   /* void setActionBar(){
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(IncomeName);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }*/


    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(IncomeName);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }


    @SuppressLint("ClickableViewAccessibility")
    void init() {
        cd = new ConnectionDetector(getApplicationContext());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        initError();

        cardView1 = (CardView) findViewById(R.id.bonus_cardview);
        cardView2 = (CardView) findViewById(R.id.sales_cardview);
        cardView3 = (CardView) findViewById(R.id.sales_history_cardview);

        infoAchiever = (ImageView) findViewById(R.id.info_achiever);
        infoAchiever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(HolidayAchiever.this, "dfd", Toast.LENGTH_LONG).show();

                LayoutInflater mInflater1 = (LayoutInflater)getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                final Dialog dialog1 = new Dialog(HolidayAchiever.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View convertView1 = mInflater1.inflate(R.layout.faq_answer_popup, null);
                dialog1.setContentView(convertView1);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog1.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog1.getWindow().setAttributes(lp);
                MyTextView showans = convertView1.findViewById(R.id.faq_ans);
                showans.setBackgroundColor(getResources().getColor(R.color.white));
                showans.setText("Flight ticket cost provided by IBOBackOffice is upto a maximum of Rs. 20,000. Any surplus in flight booking cost is to be borne by the achiever.");
                dialog1.show();

            }
        });

        dateTextView = (MyTextView) findViewById(R.id.eligible_date);

        viewPager = (CustomViewPager) findViewById(R.id.sale_pager);
        tabLayout = (TabLayout) findViewById(R.id.sale_tabLayout);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager1 = (CustomViewPager) findViewById(R.id.sale_history_pager);
        tabLayout1 = (TabLayout) findViewById(R.id.sale_history_tabLayout);
        holidayLinearLayout = (LinearLayout) findViewById(R.id.holiday_sale_layout);
        incomeLinearLayout = (LinearLayout) findViewById(R.id.income_layout);
       /* mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.holiday_achiever_swipe_refresh_layout);*/
        viewPager1.setCurrentItem(0);
        viewPager1.setOffscreenPageLimit(1);
        setupViewPager1(viewPager1);
        tabLayout1.setupWithViewPager(viewPager1);

        checkBox1 = findViewById(R.id.chk1);
        checkBox2 = findViewById(R.id.chk2);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    askBonusRequest("Are you sure you want to select your achiever bonus as flight ticket?", "flight");
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    askBonusRequest("Are you sure you want to select your achiever bonus as ₹17,000/-?", "amount");
                }
            }
        });

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/
    }

    private void checkBonusEligibility() {
        if (Utils.isNetworkAvailable(getApplicationContext()))
        {
            final ProgressDialog mProgressDialog = new ProgressDialog(HolidayAchiever.this, R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<HolidayAchieverBonus> wsCallingHolidayAchieverBonus = mAPIInterface.checkHolidayAchieverBonus();
            wsCallingHolidayAchieverBonus.enqueue(new Callback<HolidayAchieverBonus>() {
                @Override
                public void onResponse(Call<HolidayAchieverBonus> call, Response<HolidayAchieverBonus> response) {
                    if(mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    if (response.isSuccessful()){
                        if (response.code()==200)
                        {
                            if(response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS){

                                holidayAchieverBonusList = response.body().getData();
                                achieverId = holidayAchieverBonusList.getId();
                                cardView1.setVisibility(View.VISIBLE);

                                try {
                                    Date date;
                                    SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
                                    date = dates.parse(SetGmtTime(holidayAchieverBonusList.getLongAchieverDate()));

                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(date);
                                    cal.add(Calendar.DAY_OF_YEAR, 7);

                                    Date departureDate = cal.getTime();
                                    String newDate = dates.format(departureDate);

                                    dateTextView.setText("Select before " + newDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                cardView1.setVisibility(View.GONE);
                            }
                            else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                //serviceUnavailable();
                                cardView1.setVisibility(View.GONE);
                            }

                        }
                        else{
                            cardView1.setVisibility(View.GONE);
                        }
                    }

                }


                @Override
                public void onFailure(Call<HolidayAchieverBonus> call, Throwable t) {
                    mProgressDialog.dismiss();
                    //serviceUnavailable();
                }
            });
        }
        else
        {
            //noInternetConnection();
        }
    }

    void askBonusRequest(String message, final String flag_){
        new AlertDialog.Builder(HolidayAchiever.this, android.R.style.Theme_DeviceDefault_Light_Dialog)
                //.setTitle(R.string.logout)
                .setMessage(message)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        sendBonusRequest(flag_);
                    }
                }).create().show();
    }

    void sendBonusRequest(String flag_){
        if (Utils.isNetworkAvailable(getApplicationContext()))
        {
            final ProgressDialog mProgressDialog = new ProgressDialog(HolidayAchiever.this, R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<JsonObject> wsCallingHolidayAchieverBonus = mAPIInterface.sendHolidayAchieverBonus(achieverId, flag_ );
            wsCallingHolidayAchieverBonus.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    if (response.isSuccessful()){

                        if (response.code()==200)
                        {
                            String responseString = response.body().toString();
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);

                                if (jsonObject.getInt("StatusCode") == Const.REQUEST_STATUS_CODE_SUCCESS)
                                {
                                      Toast toast = Toast.makeText(getApplicationContext(), "You've successfully selected achiever bonus.", Toast.LENGTH_LONG);
                                      toast.show();

                                      cardView1.setVisibility(View.GONE);
                                }
                                else if (jsonObject.getInt("StatusCode") == Const.REQUEST_STATUS_CODE_FAIL || jsonObject.getInt("StatusCode") == Const.REQUEST_STATUS_CODE_ERROR)
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(), "There is an error. Please try again.", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        else{

                        }
                    }

                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.dismiss();
                    //serviceUnavailable();
                }
            });
        }
        else
        {
            //noInternetConnection();
        }
    }

    /*private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            getCustomerSalesList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setupViewPager(ViewPager viewPager_) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SponsorSalesFragment(), getResources().getString(R.string.sponsor_id_sales));
        adapter.addFragment(new CustomerSalesFragment(), getResources().getString(R.string.customer_sales));
        viewPager_.setAdapter(adapter);
        viewPager_.setCurrentItem(0);
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

    private void setupViewPager1(CustomViewPager viewPager_) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SponsorAchieverFragment(), getResources().getString(R.string.sponsor_id_sales));
        adapter.addFragment(new CustomerAchieverFragment(), getResources().getString(R.string.customer_sales));
        viewPager_.setAdapter(adapter);
        viewPager_.setCurrentItem(0);
    }

    void initError(){
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*refreshContent();*/
            }
        });
    }

   /* void serviceUnavailable(){
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        cardView1.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        *//*viewPager.setVisibility(View.GONE);
        viewPager1.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        tabLayout1.setVisibility(View.GONE);
        holidayLinearLayout.setVisibility(View.GONE);
        incomeLinearLayout.setVisibility(View.GONE);*//*
    }

    void noInternetConnection(){
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        cardView1.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        *//*viewPager.setVisibility(View.GONE);
        viewPager1.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        tabLayout1.setVisibility(View.GONE);
        holidayLinearLayout.setVisibility(View.GONE);
        incomeLinearLayout.setVisibility(View.GONE);*//*
    }*/
}

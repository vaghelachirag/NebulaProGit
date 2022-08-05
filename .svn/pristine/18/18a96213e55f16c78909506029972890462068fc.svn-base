package com.nebulacompanies.ibo.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.NewRetailIncomeListDetails;
import com.nebulacompanies.ibo.model.NewSubRetailIncomeListDetails;
import com.nebulacompanies.ibo.model.RetailIncomeList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.GetTime.SetDateFormats;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 16-11-2017.
 */

public class RetailIncome extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    Boolean isRefreshed = false;
    ListView listView;
    // private ArrayList<RetailIncomeListDetails> arrayListIncomeList = new ArrayList<>();
    //private ArrayList<RetailIncomeMoreInfo> arrayListMoreInfo = new ArrayList<>();
    RetailIncomeAdapter retailIncomeAdapter;
    // RetailIncomeInfoAdapter retailIncomeInfoAdapter;
    Session session;
    private APIInterface mAPIInterface;
    public static final String TAG = "RetailIncome";
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    LinearLayout lnGold;

    private ArrayList<NewRetailIncomeListDetails> arrayListRetailIncomeList = new ArrayList<>();
    private ArrayList<NewSubRetailIncomeListDetails> arrayListSubRetailIncomeList = new ArrayList<>();

    MyTextView dateTitleMyTextView, amountTitleMyTextView, moreInfoTitleMyTextView;

    RetailIncomeInfoAdapter retailIncomeInfoAdapter;
    final Handler handler = new Handler();
    Runnable r;
    boolean oneTime = false;
    SharedPreferences prefs;

    SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;

    String IncomeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_retail_income_new);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }

        setActionBar();
        //new
        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(RetailIncome.this);
        String retailList = prefs.getString("retail_income_list", "");
        if (retailList != null && retailList != "") {
            LoadRetailData();
        } else {
            getRetailIncome();
        }


        //old
        //init();
        // getRetailIncome();
        /*mswipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.income_swipe_refresh_layout);
        incomeDetailslist=(ListView) findViewById(R.id.incomedetails);
        mAdapter = new IncomeDetailsBaseAdapters(this,projctname,name,unit,income);
        incomeDetailslist.setAdapter(mAdapter);*/
    }

    /*void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(R.string.retail_income);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
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

    void init() {
        cd = new ConnectionDetector(getApplicationContext());
      //  Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        session = new Session(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.gold_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listview_gold_income);
        dateTitleMyTextView = (MyTextView) findViewById(R.id.closingdate_);
        amountTitleMyTextView = (MyTextView) findViewById(R.id.rank_);
        moreInfoTitleMyTextView = (MyTextView) findViewById(R.id.goldincome);
        lnGold = (LinearLayout) findViewById(R.id.ln_gold);

        dateTitleMyTextView.setText(R.string.date);
        amountTitleMyTextView.setText(R.string.amount);
        moreInfoTitleMyTextView.setText(R.string.more_info);
        moreInfoTitleMyTextView.setVisibility(View.GONE);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (retailIncomeAdapter != null) {
                retailIncomeAdapter.clearData();
                retailIncomeAdapter.notifyDataSetChanged();
            }
            getRetailIncome();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
         /*   Toast toast1 = Toast.makeText(this, R.string.Network_is_not_available, Toast.LENGTH_SHORT);
            toast1.show();*/
            LoadRetailData();
        }
    }

    private void getRetailIncome() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final Call<RetailIncomeList> wsCallingIncomes = mAPIInterface.getRetailIncome("RetailIncome");
            wsCallingIncomes.enqueue(new Callback<RetailIncomeList>() {
                @Override
                public void onResponse(Call<RetailIncomeList> call, Response<RetailIncomeList> response) {
                    /*//if (response.isSuccessful()) {
                        Log.i("INFO", " responseString IncomeList" + response.body().toString());
                        mProgressDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);

                        if (response.body()!=null) {

                            if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {
                                Log.i("INFO", "call for response Successful" + response.body().getData());
                                arrayListIncomeList.clear();
                                arrayListIncomeList.addAll(response.body().getData());

                                retailIncomeAdapter = new RetailIncomeAdapter(RetailIncome.this, arrayListIncomeList);
                                listView.setAdapter(retailIncomeAdapter);
                                retailIncomeAdapter.notifyDataSetChanged();
                            }
                            else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_FAIL) {
                               noRecordsFound();

                            }
                        }else
                        {   Log.i("INFO","call for response Fail"+response.body().getData());
                           // norecordsMyTextView.setVisibility(View.VISIBLE);
                            noRecordsFound();

                        }*/

                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListRetailIncomeList != null) {
                    arrayListRetailIncomeList.clear();

                    if (response.isSuccessful()) {

                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                   // if (arrayListRetailIncomeList != null) {
                                        arrayListRetailIncomeList.addAll(response.body().getData());
                                         prefs = PreferenceManager.getDefaultSharedPreferences(RetailIncome.this);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(arrayListRetailIncomeList);
                                        editor.putString("retail_income_list", json);
                                        editor.apply();
                                    //}
                                    retailIncomeAdapter = new RetailIncomeAdapter(RetailIncome.this, arrayListRetailIncomeList);
                                    listView.setAdapter(retailIncomeAdapter);
                                    retailIncomeAdapter.notifyDataSetChanged();
                                    lnGold.setVisibility(View.VISIBLE);
                                } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }
                                if (arrayListRetailIncomeList.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            lnGold.setVisibility(View.VISIBLE);
                            serviceUnavailable();
                        }
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(RetailIncome.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<RetailIncomeList> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    lnGold.setVisibility(View.VISIBLE);
                    serviceUnavailable();
                }
            });
        } else {
           // if (arrayListRetailIncomeList != null) {
                lnGold.setVisibility(View.VISIBLE);
                LoadRetailData();
            /*}else {
                lnGold.setVisibility(View.VISIBLE);
                noInternetConnection();
            }*/
        }
    }

    public class RetailIncomeAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<NewRetailIncomeListDetails> retailIncomeListDetails;

        public RetailIncomeAdapter(Activity activity, ArrayList<NewRetailIncomeListDetails> retailIncomeListDetails) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.retailIncomeListDetails = retailIncomeListDetails;
        }

        @Override
        public int getCount() {
          //  return retailIncomeListDetails.size();
         //   return (retailIncomeListDetails == null) ? 0 : retailIncomeListDetails.size();
            return (retailIncomeListDetails == null) ? 0 : retailIncomeListDetails.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        private class ViewHolder {
            MyTextView dateMyTextView, amountMyTextView;
            ImageView moreInfoImageView;
            LinearLayout mainLinearLayout;
        }

        private class ViewHolder2 {
            //TextView dateTextView, particularTextView, trxnoTextView, statusTextView;
            ListView infoListView;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            LayoutInflater mInflater = (LayoutInflater)
                    getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (view == null) {
                view = mInflater.inflate(R.layout.listview_row_new_retail_income, null);
                holder = new ViewHolder();
                holder.dateMyTextView = (MyTextView) view.findViewById(R.id.closingdate);
                holder.amountMyTextView = (MyTextView) view.findViewById(R.id.rank);
                holder.moreInfoImageView = (ImageView) view.findViewById(R.id.retail_info_1);
                holder.mainLinearLayout = (LinearLayout) view.findViewById(R.id.list_income_details_layout);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (i < retailIncomeListDetails.size()) {

                if (i % 2 == 1) {
                    // Set a background color for linearlayout regular row/item
                    //holder.mainLinearLayout.setBackgroundResource(R.color.table_bg_odd);
                    view.setBackgroundResource(R.color.table_bg_odd);
                    holder.mainLinearLayout.setBackgroundResource(R.drawable.nebula_effect_white);
                } else {
                    // Set the background color for alternate row/item
                    //holder.mainLinearLayout.setBackgroundResource(R.color.table_bg_even);
                    view.setBackgroundResource(R.color.table_bg_even);
                    holder.mainLinearLayout.setBackgroundResource(R.drawable.nebula_effect);
                }


                holder.dateMyTextView.setText(SetGmtTime(retailIncomeListDetails.get(i).getClosingDate()));
                holder.amountMyTextView.setText("" + Config.formatter.format(retailIncomeListDetails.get(i).getAmount()).replace(".00", "").replace("Rs.", ""));
                holder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arrayListRetailIncomeList.get(i).getData().size() > 0) {
                            ViewHolder2 holder2 = null;
                            LayoutInflater mInflater2 = (LayoutInflater)
                                    activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                            Dialog dialog = new Dialog(activity);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            View convertView1 = null;
                            if (convertView1 == null) {
                                //convertView1 = mInflater2.inflate(R.layout.list_retail_income_project_info, null);
                                convertView1 = mInflater2.inflate(R.layout.new_retail_income_details, null);
                                //dialog.setContentView(convertView1);
                                dialog = new Dialog(RetailIncome.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setContentView(convertView1);
                                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                                holder2 = new ViewHolder2();
                                holder2.infoListView = (ListView) convertView1.findViewById(R.id.list_project_info);

                                convertView1.setTag(holder2);
                            } else {
                                holder2 = (ViewHolder2) convertView1.getTag();
                            }
                            arrayListSubRetailIncomeList.clear();
                            arrayListSubRetailIncomeList.addAll(arrayListRetailIncomeList.get(i).getData());
                            retailIncomeInfoAdapter = new RetailIncomeInfoAdapter(RetailIncome.this, arrayListSubRetailIncomeList);
                            holder2.infoListView.setAdapter(retailIncomeInfoAdapter);
                            retailIncomeAdapter.notifyDataSetChanged();
                            dialog.show();
                        } else {
                            Toast.makeText(RetailIncome.this, R.string.no_records_found, Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
            return view;

        }

        public void clearData() {
            // clear the data
            retailIncomeListDetails.clear();
        }
    }

    public class RetailIncomeInfoAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<NewSubRetailIncomeListDetails> retailIncomeMoreInfo;

        public RetailIncomeInfoAdapter(Activity activity, ArrayList<NewSubRetailIncomeListDetails> retailIncomeMoreInfo) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.retailIncomeMoreInfo = retailIncomeMoreInfo;
        }

        @Override
        public int getCount() {
            return retailIncomeMoreInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout linearLayout;
            public MyTextView dateMyTextView, custMyTextView, unitMyTextView, retailMyTextView, emiMyTextView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            LayoutInflater mInflater = (LayoutInflater)
                    getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_retail_income_project_info, null);
                holder = new ViewHolder();
                holder.dateMyTextView = (MyTextView) convertView.findViewById(R.id.emi_date_txt);
                holder.custMyTextView = (MyTextView) convertView.findViewById(R.id.cust_name_txt);
                holder.unitMyTextView = (MyTextView) convertView.findViewById(R.id.unit_txt);
                holder.emiMyTextView = (MyTextView) convertView.findViewById(R.id.emi_txt);
                holder.retailMyTextView = (MyTextView) convertView.findViewById(R.id.retail_income_txt);
                holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.retail_info_layout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position < retailIncomeMoreInfo.size()) {

                if (position % 2 == 1) {
                    // Set a background color for linearlayout regular row/item
                    holder.linearLayout.setBackgroundResource(R.color.table_bg_odd);
                } else {
                    // Set the background color for alternate row/item
                    holder.linearLayout.setBackgroundResource(R.color.table_bg_even);
                }

                holder.dateMyTextView.setText(SetGmtTime(retailIncomeMoreInfo.get(position).getClearDate()));
                holder.custMyTextView.setText(retailIncomeMoreInfo.get(position).getCustomerName());
                holder.unitMyTextView.setText(retailIncomeMoreInfo.get(position).getUnit());
                holder.emiMyTextView.setText(retailIncomeMoreInfo.get(position).getInstrumentNo());
                holder.retailMyTextView.setText("" + Config.formatter.format(retailIncomeMoreInfo.get(position).getAmount()).replace(".00", "").replace("Rs.", ""));
            }
            return convertView;
        }
    }















    /*public class RetailIncomeAdapter extends BaseAdapter {

        private  Activity activity;
        private ArrayList<RetailIncomeListDetails> retailIncomeListDetails;

        public RetailIncomeAdapter(Activity activity, ArrayList<RetailIncomeListDetails> retailIncomeListDetails) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.retailIncomeListDetails = retailIncomeListDetails;
        }

       @Override
        public int getCount() {
            return retailIncomeListDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            public MyTextView unitMyTextView, projectMyTextView, customernameMyTextView,retailincomeMyTextView;
            public ImageView infoIncomeDetailsImageView;
            LinearLayout incomedetailsLinearLayout;
        }
        private class ViewHolder2 {
            //TextView dateTextView, particularTextView, trxnoTextView, statusTextView;
            ListView infoListView;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            LayoutInflater mInflater = (LayoutInflater)
                    getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_income_details, null);
                holder = new ViewHolder();
                holder.projectMyTextView = (MyTextView) convertView.findViewById(R.id.project_income_details);
                holder.customernameMyTextView = (MyTextView) convertView.findViewById(R.id.customer_income_details);
                holder.unitMyTextView = (MyTextView) convertView.findViewById(R.id.unit_name_income_details);
                holder.retailincomeMyTextView=(MyTextView)convertView.findViewById(R.id.customer_retail_income_details);
                holder.infoIncomeDetailsImageView=(ImageView)convertView.findViewById(R.id.more_info_income_details);
                holder.incomedetailsLinearLayout=(LinearLayout)convertView.findViewById(R.id.list_income_details_layout);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(position < retailIncomeListDetails.size()) {

                if (position % 2 == 1) {
                    // Set a background color for linearlayout regular row/item
                    holder.incomedetailsLinearLayout.setBackgroundResource(R.color.table_bg_odd);
                } else {
                    // Set the background color for alternate row/item
                    holder.incomedetailsLinearLayout.setBackgroundResource(R.color.table_bg_even);
                }

                holder.projectMyTextView.setText(arrayListIncomeList.get(position).getRetailIncomeProject());
                holder.customernameMyTextView.setText(arrayListIncomeList.get(position).getRetailIncomeCustomerName());
               *//* if (!arrayListIncomeList.get(position).getRetailIncomeProject().equals("Hawthorn dwarka")){
                    holder.unitMyTextView.setText(arrayListIncomeList.get(position).getRetailIncomeUnitNumber());
                }*//*
     *//*  else
                {
                    Log.i("INFO","call for project else name:-"+arrayListIncomeList.get(position).getRetailIncomeProject());

                }*//*
                 holder.unitMyTextView.setText(arrayListIncomeList.get(position).getRetailIncomeUnitNumber());
                holder.retailincomeMyTextView.setText((activity.getResources().getString(R.string.Rs))+" " +arrayListIncomeList.get(position).getRetailIncomeAmount());

                holder.infoIncomeDetailsImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewHolder2 holder2 = null;
                        LayoutInflater mInflater2 = (LayoutInflater)
                                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                        Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        View convertView1 = null;
                        if (convertView1 == null) {
                            //convertView1 = mInflater2.inflate(R.layout.list_retail_income_project_info, null);
                            convertView1 = mInflater2.inflate(R.layout.retail_income_details, null);
                            //dialog.setContentView(convertView1);
                            dialog = new Dialog(RetailIncome.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(convertView1);
                            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                            holder2 = new ViewHolder2();
                            holder2.infoListView = (ListView) convertView1.findViewById(R.id.list_project_info);

                            dialog.show();
                            convertView1.setTag(holder2);
                        }
                        else {
                            holder2 = (ViewHolder2) convertView1.getTag();
                        }

                        arrayListMoreInfo.clear();
                        if (arrayListMoreInfo!=null){
                            arrayListMoreInfo.addAll(arrayListIncomeList.get(position).getRetailIncomeMoreInfo());
                            retailIncomeInfoAdapter = new RetailIncomeInfoAdapter(RetailIncome.this, arrayListMoreInfo);
                            holder2.infoListView.setAdapter(retailIncomeInfoAdapter);
                            retailIncomeAdapter.notifyDataSetChanged();
                        }




                        dialog.show();
                    }
                });
            }
                return convertView;

        }

        public void clearData() {
            // clear the data
            retailIncomeListDetails.clear();
        }
    }

    public class RetailIncomeInfoAdapter extends BaseAdapter {

        private  Activity activity;
        private ArrayList<RetailIncomeMoreInfo> retailIncomeMoreInfo;

        public RetailIncomeInfoAdapter(Activity activity, ArrayList<RetailIncomeMoreInfo> retailIncomeMoreInfo) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.retailIncomeMoreInfo = retailIncomeMoreInfo;
        }

        @Override
        public int getCount() {
            return retailIncomeMoreInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout linearLayout;
            public MyTextView dateMyTextView, emiMyTextView, cleardataMyTextView, statusMyTextView,amountMyTextView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            LayoutInflater mInflater = (LayoutInflater)
                    getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_retail_income_project_info, null);
                holder = new ViewHolder();
                holder.dateMyTextView = (MyTextView) convertView.findViewById(R.id.emi_date_txt);
                holder.emiMyTextView = (MyTextView) convertView.findViewById(R.id.emi_txt);
                holder.cleardataMyTextView = (MyTextView) convertView.findViewById(R.id.clear_data__txt);
                holder.amountMyTextView=(MyTextView)convertView.findViewById(R.id.amount_status_txt);
                holder.statusMyTextView=(MyTextView)convertView.findViewById(R.id.status_txt);
                holder.linearLayout=(LinearLayout) convertView.findViewById(R.id.retail_info_layout);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(position < retailIncomeMoreInfo.size()) {

                if (position % 2 == 1) {
                    // Set a background color for linearlayout regular row/item
                    holder.linearLayout.setBackgroundResource(R.color.table_bg_odd);
                } else {
                    // Set the background color for alternate row/item
                    holder.linearLayout.setBackgroundResource(R.color.table_bg_even);
                }

                holder.emiMyTextView.setText(retailIncomeMoreInfo.get(position).getParticular());


                *//*if (retailIncomeMoreInfo.get(position).getDate_().equals("0001-01-01T00:00:00")){
                    Log.i("INFO","call for project if name:-"+retailIncomeMoreInfo.get(position).getDate_());
                    holder.dateMyTextView.setText("");
                    //holder.statusMyTextView.setText("Pending");
                }
                else
                {*//*
                    holder.dateMyTextView.setText(SetGmtTime(retailIncomeMoreInfo.get(position).getDate_()));
                    //holder.statusMyTextView.setText(retailIncomeMoreInfo.get(position).getStatus());
                //}
                *//*if (retailIncomeMoreInfo.get(position).getCleardate_().equals("0001-01-01T00:00:00")){
                    Log.i("INFO","call for project if name:-"+retailIncomeMoreInfo.get(position).getCleardate_());
                    holder.cleardataMyTextView.setText("");
                }
                else
                {*//*
                    holder.cleardataMyTextView.setText(SetGmtTime(retailIncomeMoreInfo.get(position).getCleardate_()));
               // }

                 holder.amountMyTextView.setText((activity.getResources().getString(R.string.Rs))+" " +retailIncomeMoreInfo.get(position).getAmount());
               // holder.amountMyTextView.setText(retailIncomeMoreInfo.get(position).getStatus());
                holder.statusMyTextView.setText(retailIncomeMoreInfo.get(position).getStatus());
            }
            return convertView;

        }

        public void clearData() {
            // clear the data

        }
    }*/

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

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        lnGold.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        lnGold.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        lnGold.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
      /*  r = new Runnable() {
            public void run() {
                Calendar cala = Calendar.getInstance();
                SimpleDateFormat simpledat = new SimpleDateFormat("HH:mm:ss");
                String Dat = simpledat.format(cala.getTime());
                String[] Time = Dat.split(":");
                int minutes = Integer.parseInt(Time[0]);
                if (minutes >= SetDateFormats(session.getIboDate())) {
                    if (!oneTime) {
                        Intent i = new Intent(RetailIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(RetailIncome.this);
                        editor = mySPrefs.edit();
                        editor.remove("rankAndVolume");
                        editor.remove("bv_lists");
                        editor.remove("income_list");
                        editor.remove("personal_list");
                        editor.remove("team_list");
                        editor.remove("New_joinees");
                        editor.remove("DownlineRank");
                        editor.remove("projects_list");
                        editor.remove("my_sales_list");
                        editor.remove("income");
                        editor.remove("incomeList");
                        editor.remove("download_list");
                        editor.remove("placement");
                        editor.remove("sponsorList");
                        editor.remove("SalesHoliday");
                        editor.remove("ProductList");

                        editor.remove("retail_income_list");
                        editor.remove("star_club_income_list");
                        editor.remove("three_star_income_list");
                        editor.remove("task_lists");
                        editor.remove("gold_income_list");
                        editor.remove("spot_income_list");
                        editor.remove("booster_income_list");
                        editor.remove("super_booster_income_list");
                        editor.remove("carProgramIncome");
                        editor.remove("rank_income_list");
                        editor.remove("sponsor_income_list");
                        editor.apply();
                        finish();
                        startActivity(i);
                        oneTime = true;
                    }
                }
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);*/
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }


    public void LoadRetailData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(RetailIncome.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("retail_income_list", null);
        Type type = new TypeToken<ArrayList<NewRetailIncomeListDetails>>() {
        }.getType();
        arrayListRetailIncomeList = gson.fromJson(json, type);
        if (json!=null) {
            if (retailIncomeAdapter == null)
                retailIncomeAdapter = new RetailIncomeAdapter(RetailIncome.this, arrayListRetailIncomeList);
            listView.setAdapter(retailIncomeAdapter);
            retailIncomeAdapter.notifyDataSetChanged();
            lnGold.setVisibility(View.VISIBLE);

            Log.i("INFO", "Call For arrayList:-" + arrayListRetailIncomeList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListRetailIncomeList);
        }else {
            lnGold.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }
}

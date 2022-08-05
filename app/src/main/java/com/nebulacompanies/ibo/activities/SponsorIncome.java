package com.nebulacompanies.ibo.activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.SponsorAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.SponsorIncomeDetails;
import com.nebulacompanies.ibo.model.SponsorIncomeList;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;

/**
 * Created by Sagar Virvani on 13-07-2018.
 */

public class SponsorIncome extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    String IncomeName;
    ConnectionDetector cd;
    private APIInterface mAPIInterface;

    MyTextView dateMyTextView, unitMyTextView, RankBounsMyTextView;
    Boolean isRefreshed = false;
    ListView lview;
    private ArrayList<SponsorIncomeList> arrayListSponsorIncomeList = new ArrayList<>();
    final Handler handler = new Handler();
    Runnable r;
   // boolean oneTime = false;
    Session session;
    SponsorAdapter sponsorAdapter;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    private LinearLayout lnSponsorIncome;

    SharedPreferences prefs;
    /*SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rank_bonus_income);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }
        setActionBar();
        init();


        prefs = PreferenceManager.getDefaultSharedPreferences(SponsorIncome.this);
        String rankBonusIncome = prefs.getString("sponsor_income_list", "");
        if (rankBonusIncome != null && !rankBonusIncome.equals("")) {
            LoadSponsorData();
        } else {
            GetSponsor();
        }
    }

    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        session = new Session(this);
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
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        dateMyTextView = (MyTextView) findViewById(R.id.cust_name_);
        unitMyTextView = (MyTextView) findViewById(R.id.apartment_);
        RankBounsMyTextView = (MyTextView) findViewById(R.id.sale_info_);
        lnSponsorIncome = (LinearLayout) findViewById(R.id.ln_rank_bonus);

        dateMyTextView.setText(R.string.date);
        unitMyTextView.setText(R.string.product_name);
        RankBounsMyTextView.setText(R.string.Sponsor_Income);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sales_swipe_refresh_layout);

        lview = (ListView) findViewById(R.id.listview_my_sales);
        lview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lview.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(lview.getFirstVisiblePosition() == 0 && lview.getChildAt(0).getTop() == 0);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshContent);
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            GetSponsor();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            //noInternetConnection();
            LoadSponsorData();
        }
    }

    private void GetSponsor() {
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
            Call<SponsorIncomeDetails> wsCallingSponsorIncome = mAPIInterface.getSponsorIncome("SponsorIncome");
            wsCallingSponsorIncome.enqueue(new Callback<SponsorIncomeDetails>() {
                @Override
                public void onResponse(Call<SponsorIncomeDetails> call, Response<SponsorIncomeDetails> response) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListSponsorIncomeList != null) {
                        arrayListSponsorIncomeList.clear();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListSponsorIncomeList.addAll(response.body().getData());
                                    prefs = PreferenceManager.getDefaultSharedPreferences(SponsorIncome.this);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListSponsorIncomeList);
                                    editor.putString("sponsor_income_list", json);
                                    editor.apply();
                                    sponsorAdapter = new SponsorAdapter(SponsorIncome.this, arrayListSponsorIncomeList);
                                    lview.setAdapter(sponsorAdapter);
                                    sponsorAdapter.notifyDataSetChanged();
                                    lnSponsorIncome.setVisibility(View.VISIBLE);
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    lnSponsorIncome.setVisibility(View.VISIBLE);
                                    serviceUnavailable();
                                }
                                if (arrayListSponsorIncomeList.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    lview.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.VISIBLE);
                                    lview.setVisibility(View.GONE);
                                }
                            } else {
                                lnSponsorIncome.setVisibility(View.VISIBLE);
                                serviceUnavailable();
                            }
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<SponsorIncomeDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    lnSponsorIncome.setVisibility(View.VISIBLE);
                    serviceUnavailable();
                }
            });

        } else {
            // noInternetConnection();
            LoadSponsorData();
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

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(v -> refreshContent());
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        lnSponsorIncome.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        lview.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        lnSponsorIncome.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        lnSponsorIncome.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
       /* r = new Runnable() {
            public void run() {
                Calendar cala = Calendar.getInstance();
                SimpleDateFormat simpledat = new SimpleDateFormat("HH:mm:ss");
                String Dat = simpledat.format(cala.getTime());
                String[] Time = Dat.split(":");
                int minutes = Integer.parseInt(Time[0]);
                if (minutes >= SetDateFormats(session.getIboDate())) {
                    if (!oneTime) {
                        Intent i = new Intent(SponsorIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(SponsorIncome.this);
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


    public void LoadSponsorData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(SponsorIncome.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("sponsor_income_list", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<SponsorIncomeList>>() {
            }.getType();
            arrayListSponsorIncomeList = gson.fromJson(json, type);
            if (sponsorAdapter == null)
                sponsorAdapter = new SponsorAdapter(SponsorIncome.this, arrayListSponsorIncomeList);
            lview.setAdapter(sponsorAdapter);
            sponsorAdapter.notifyDataSetChanged();
            lnSponsorIncome.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListSponsorIncomeList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListSponsorIncomeList);
        } else {
            lnSponsorIncome.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }
}

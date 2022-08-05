package com.nebulacompanies.ibo.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.MainLagSuBoosterAdapter;
import com.nebulacompanies.ibo.adapter.SuBoosterIncomeAdapter;
import com.nebulacompanies.ibo.adapter.SuBoosterIncomeAdapter_;
import com.nebulacompanies.ibo.model.CarProgramIncomeLegList;
import com.nebulacompanies.ibo.model.SuBoosterIncomeDetails;
import com.nebulacompanies.ibo.model.SuBoosterIncomeList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.GetTime.SetDateFormats;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.PopupEmptyView.DisplayEmptyDialog;

/**
 * Created by Sagar Virvani on 15-11-2017.
 */

public class SuBoosterIncome extends AppCompatActivity implements View.OnClickListener {

    MyTextView eligibleMyTextView, titleoneMyTextView, titletwoMyTextView, titlethreeMyTextView, mainlagMyTextView, otherlagMyTextView,
            maintotalMyTextView, mainavgMyTextView, othertotalMyTextView, otheravgMyTextView;
    Dialog dialog1;
    ListView SuBoosterIncomeListView;
    int listItemShow = 20;
    LinearLayout mainlagLinearLayout, otherlagLinearLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MainLagSuBoosterAdapter mainLagSuBoosterAdapter;
    Session session;
    Boolean isRefreshed = false;
    final Handler handler = new Handler();
    Runnable r;
    boolean oneTime = false;

    SpannableString content1, content2;
    int mainLegCount, mainAvgCount;
    String mainLegAvgPer;
    int otherLegCount;
    String otherLegAvgPer;
    String eligibleMessage;
    SharedPreferences.Editor editor;

    SuBoosterIncomeAdapter suBoosterIncomeAdapter;
    SuBoosterIncomeAdapter_ suBoosterIncomeAdapter_;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    private APIInterface mAPIInterface;
    public static final String TAG = "SuBoosterIncome";
    ConnectionDetector cd;
    ProgressDialog mProgressDialog;

    SuBoosterIncomeList suBoosterIncomeList;

    private ArrayList<CarProgramIncomeLegList> arrayListSuBoosterMainLeg = new ArrayList<>();
    private ArrayList<CarProgramIncomeLegList> arrayListSuBoosterOtherLeg = new ArrayList<>();
    private LinearLayout lnSuBooster;
    SharedPreferences prefs;
    SharedPreferences mySPrefs;

    String IncomeName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.su_booster_income);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }

        setActionBar();
        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(SuBoosterIncome.this);
        String suBoosterIncome = prefs.getString("carProgramIncome", "");
        if (suBoosterIncome != null && suBoosterIncome != "") {
            LoadData();
        } else {
            getSuBoosterList();
        }

    }

    /*void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setText(R.string.su_booster);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
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
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        session = new Session(this);
        eligibleMyTextView = (MyTextView) findViewById(R.id.eligible_income_text);
        mainlagLinearLayout = (LinearLayout) findViewById(R.id.main_leg_income);
        lnSuBooster = (LinearLayout) findViewById(R.id.ln_su_booster);
        otherlagLinearLayout = (LinearLayout) findViewById(R.id.other_leg_income);
        titleoneMyTextView = (MyTextView) findViewById(R.id.title_one_txt);
        titletwoMyTextView = (MyTextView) findViewById(R.id.title_two_txt);
        titlethreeMyTextView = (MyTextView) findViewById(R.id.title_three_txt);
        maintotalMyTextView = (MyTextView) findViewById(R.id.main_total_text);
        mainavgMyTextView = (MyTextView) findViewById(R.id.main_avg_text);
        othertotalMyTextView = (MyTextView) findViewById(R.id.other_total_text);
        otheravgMyTextView = (MyTextView) findViewById(R.id.other_avg_text);
        SuBoosterIncomeListView = (ListView) findViewById(R.id.listview_income);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.booster_swipe_refresh_layout);

        titleoneMyTextView.setText(R.string.date);
        titletwoMyTextView.setText("Installment No");
        titlethreeMyTextView.setText("Payment");

        mainlagLinearLayout.setOnClickListener(this);
        otherlagLinearLayout.setOnClickListener(this);

        SuBoosterIncomeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (SuBoosterIncomeListView.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(SuBoosterIncomeListView.getFirstVisiblePosition() == 0 && SuBoosterIncomeListView.getChildAt(0).getTop() == 0);
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

    private void getSuBoosterList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<SuBoosterIncomeDetails> wsCallingSuBoosterIncomeDetails = mAPIInterface.getSuBoosterIncome("SuBoosterIncome");
            wsCallingSuBoosterIncomeDetails.enqueue(new Callback<SuBoosterIncomeDetails>() {
                @Override
                public void onResponse(Call<SuBoosterIncomeDetails> call, Response<SuBoosterIncomeDetails> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListSuBoosterMainLeg != null && arrayListSuBoosterOtherLeg != null) {
                        arrayListSuBoosterMainLeg.clear();
                        arrayListSuBoosterOtherLeg.clear();

                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    suBoosterIncomeList = response.body().getData();
                                    arrayListSuBoosterMainLeg.addAll(suBoosterIncomeList.getMainLeg());
                                    arrayListSuBoosterOtherLeg.addAll(suBoosterIncomeList.getOtherLeg());
                                     prefs = PreferenceManager.getDefaultSharedPreferences(SuBoosterIncome.this);
                                    editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(response.body().getData());
                                    editor.putString("carProgramIncome", json);
                                    editor.apply();
                                    eligibleMyTextView.setText(suBoosterIncomeList.getEligibleMessage());

                                    //mainLge show
                                    //  mainLegCount= (int) suBoosterIncomeList.getMainLegAverage();
                                    maintotalMyTextView.setText(Integer.toString(suBoosterIncomeList.getMainLegCount()));
                                    maintotalMyTextView.setPaintFlags(maintotalMyTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                    mainavgMyTextView.setText("(Avg%: " + (int) suBoosterIncomeList.getMainLegAverage() + "%)");

                                    //otherLeg show
                                    //otherLegCount=(int)suBoosterIncomeList.getOtherLegAverage();
                                    othertotalMyTextView.setText(Integer.toString(suBoosterIncomeList.getOtherLegCount()));
                                    otheravgMyTextView.setText("(Avg%: " + (int) suBoosterIncomeList.getOtherLegAverage() + "%)");
                                    othertotalMyTextView.setPaintFlags(othertotalMyTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                    //Mainleg show
                                    suBoosterIncomeAdapter = new SuBoosterIncomeAdapter(SuBoosterIncome.this, arrayListSuBoosterMainLeg);

                                    //otherLeg show
                                    suBoosterIncomeAdapter_ = new SuBoosterIncomeAdapter_(SuBoosterIncome.this, arrayListSuBoosterOtherLeg);
                                    lnSuBooster.setVisibility(View.VISIBLE);
                                    noRecordsFound();
                                    SuBoosterIncomeListView.setVisibility(View.GONE);

                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }

                            } else {
                                serviceUnavailable();
                            }
                        }
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(SuBoosterIncome.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<SuBoosterIncomeDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            //noInternetConnection();
            LoadData();
        }
    }
    /*private void callAPI(){
        if(session != null) {
            SU_BOOSTER_INCOME_API = Config.NEB_API + "/API/SubIncomes/ShowSubIncomes?IncomeId=9&MemberID=" + session.getLoginID();
            asyncComplex = new AsyncComplex(this, SU_BOOSTER_INCOME_API, "SU_Booster_Income", isRefreshed);
            asyncComplex.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex);
        }
    }*/

    /*private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if(suBoosterIncomeAdapter != null) {
                suBoosterIncomeAdapter.clearData();
                suBoosterIncomeAdapter.notifyDataSetChanged();
            }
            if(suBoosterIncomeAdapter_ != null) {
                suBoosterIncomeAdapter_.clearData();
                suBoosterIncomeAdapter_.notifyDataSetChanged();
            }
            maintotalMyTextView.setText(null);
            mainavgMyTextView.setText(null);
            othertotalMyTextView.setText(null);
            otheravgMyTextView.setText(null);
            eligibleMyTextView.setText(null);
            mSwipeRefreshLayout.setRefreshing(true);
            getSuBoosterList();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }*/

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            editor.remove("carProgramIncome").commit();
            if (suBoosterIncomeAdapter != null) {
                suBoosterIncomeAdapter.clearData();
                suBoosterIncomeAdapter.notifyDataSetChanged();
            }
            if (suBoosterIncomeAdapter_ != null) {
                suBoosterIncomeAdapter_.clearData();
                suBoosterIncomeAdapter_.notifyDataSetChanged();
            }
            maintotalMyTextView.setText(null);
            mainavgMyTextView.setText(null);
            othertotalMyTextView.setText(null);
            otheravgMyTextView.setText(null);
            eligibleMyTextView.setText(null);
            mSwipeRefreshLayout.setRefreshing(true);
            getSuBoosterList();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            /*noInternetConnection();*/
            arrayListSuBoosterMainLeg.clear();
            arrayListSuBoosterOtherLeg.clear();
            LoadData();
        }
    }

   /* @Override
    public void processFinish(String output, String Tag) {
        if (output == null) {
            output = "THERE WAS AN ERROR";
           *//* noRecordsTextView.setVisibility(View.VISIBLE);
            noRecordsTextView.setText("Network Error");*//*
        }

        String response = output.toString();
        Log.i("INFO", "response :" + response);

        if (Tag.equals("SU_Booster_Income")) {
            try {
                JSONObject object = new JSONObject(response);
                String status_code = object.getString("Statuscode");

                if (status_code.equals("1")) {

                    JSONObject jsonObject = object.getJSONObject("Data");
                    //JSONArray new_array = object.getJSONArray("Data");

                    Log.i("INFO", "response new_array :" + jsonObject);

                    if (jsonObject.isNull("MainLegCount")) {
                        mainLegCount = 0;
                    } else {
                        mainLegCount = jsonObject.getInt("MainLegCount");
                    }

                    if (jsonObject.isNull("MainLegAverage")) {
                        mainLegAvgPer = "";
                    } else {

                       // mainLegAvgPer = jsonObject.getString("MainLegAverage").toString();
                        int a = (int) Math.floor(Double.parseDouble(jsonObject.getString("MainLegAverage")));
                        Log.i("INFO","call for a:-"+a);
                        mainLegAvgPer= String.valueOf(a);
                    }

                    if (jsonObject.isNull("OtherLegCount")) {
                        otherLegCount = 0;
                    } else {
                        otherLegCount = jsonObject.getInt("OtherLegCount");
                    }

                    if (jsonObject.isNull("OtherLegAverage")) {
                        otherLegAvgPer = "";
                    } else {
                       //otherLegAvgPer = jsonObject.getString("OtherLegAverage").toString();
                        int b = (int) Math.floor(Double.parseDouble(jsonObject.getString("OtherLegAverage")));
                        Log.i("INFO","call for a:-"+b);
                        otherLegAvgPer= String.valueOf(b);
                    }

                    eligibleMessage = jsonObject.getString("eligibleMessage").toString();

                    content1 = new SpannableString(Integer.toString(mainLegCount));
                    content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
                    maintotalMyTextView.setText(content1);
                    //maintotalMyTextView.setText(Integer.toString(mainLegCount));
                    mainavgMyTextView.setText("(Avg%: " + mainLegAvgPer + "%)");

                    content2 = new SpannableString(Integer.toString(otherLegCount));
                    content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
                    othertotalMyTextView.setText(content2);
                    //othertotalMyTextView.setText(Integer.toString(otherLegCount));
                    otheravgMyTextView.setText("(Avg%: " + otherLegAvgPer + "%)");

                    eligibleMyTextView.setText(eligibleMessage);

                    JSONArray new_array_main_leg = jsonObject.getJSONArray("MainLeg");

                    for (int i = 0; i < new_array_main_leg.length(); i++) {
                        try {
                            JSONObject jsonObject1 = new_array_main_leg.getJSONObject(i);

                            iboId.add(jsonObject1.getString("IBOKeyID").toString());
                            ibo.add(jsonObject1.getString("IBO").toString());
                            receipt.add(jsonObject1.getInt("Percentage"));

                           // suBoosterIncomeAdapter = new SuBoosterIncomeAdapter(this, iboId, ibo, receipt);
                           *//* listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            suboosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);*//*
                            //noRecordsTextView.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray new_array_other_leg = jsonObject.getJSONArray("OtherLeg");

                    for (int i = 0; i < new_array_other_leg.length(); i++) {
                        try {
                            JSONObject jsonObject1 = new_array_other_leg.getJSONObject(i);

                            iboId_.add(jsonObject1.getString("IBOKeyID").toString());
                            ibo_.add(jsonObject1.getString("IBO").toString());
                            receipt_.add(jsonObject1.getInt("Percentage"));

                            //suBoosterIncomeAdapter_ = new SuBoosterIncomeAdapter_(this, iboId_, ibo_, receipt_);
                           *//* listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            suboosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);*//*
                            //noRecordsTextView.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    *//*noRecordsTextView.setVisibility(View.VISIBLE);*//*
                }
                else if(status_code.equals("0")){
                    // NO Data Found
                    *//*noRecordsTextView.setVisibility(View.VISIBLE);
                    noRecordsTextView.setText("No records found..!");*//*
                }
                else if(status_code.equals("-1")){
                    // Please Reload, There is an error.
                    *//*noRecordsTextView.setVisibility(View.VISIBLE);
                    noRecordsTextView.setText("Please reload..!");*//*
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(AsyncComplex task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_leg_income:
                if (suBoosterIncomeAdapter != null) {
                    if (suBoosterIncomeList.getMainLegCount() == 0) {
                        DisplayEmptyDialog(SuBoosterIncome.this, 0);
                    } else {
                        ShowMainLegInfo(suBoosterIncomeAdapter);
                    }

                }
                break;
            case R.id.other_leg_income:
                if (suBoosterIncomeAdapter_ != null) {
                    if (suBoosterIncomeList.getOtherLegCount() == 0) {
                        DisplayEmptyDialog(SuBoosterIncome.this, 0);
                    } else {
                        ShowOtherLegInfo(suBoosterIncomeAdapter_);
                    }
                }
                break;
        }
    }

    private void ShowMainLegInfo(SuBoosterIncomeAdapter suBoosterIncomeAdapter) {
        LayoutInflater mInflater1 = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView1 = mInflater1.inflate(R.layout.su_booster_lags_income, null);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog1.setContentView(convertView1);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);

        ListView listView1 = (ListView) convertView1.findViewById(R.id.listview_su_booster);
        listView1.setAdapter(suBoosterIncomeAdapter);
        suBoosterIncomeAdapter.notifyDataSetChanged();

        dialog1.show();
    }

    private void ShowOtherLegInfo(SuBoosterIncomeAdapter_ suBoosterIncomeAdapter) {
        LayoutInflater mInflater1 = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView1 = mInflater1.inflate(R.layout.su_booster_lags_income, null);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog1.setContentView(convertView1);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);

        ListView listView1 = (ListView) convertView1.findViewById(R.id.listview_su_booster);
        listView1.setAdapter(suBoosterIncomeAdapter);
        suBoosterIncomeAdapter.notifyDataSetChanged();

        dialog1.show();
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
        lnSuBooster.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        SuBoosterIncomeListView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        lnSuBooster.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        SuBoosterIncomeListView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        lnSuBooster.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        SuBoosterIncomeListView.setVisibility(View.GONE);
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
                        Intent i = new Intent(SuBoosterIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(SuBoosterIncome.this);
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


    private void LoadData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(SuBoosterIncome.this);
        Gson gson = new Gson();
        String json = prefs.getString("carProgramIncome", null);
        if (json != null) {
            SuBoosterIncomeList suBoosterIncomeList = gson.fromJson(json, SuBoosterIncomeList.class);

            arrayListSuBoosterMainLeg.addAll(suBoosterIncomeList.getMainLeg());
            arrayListSuBoosterOtherLeg.addAll(suBoosterIncomeList.getOtherLeg());

            eligibleMyTextView.setText(suBoosterIncomeList.getEligibleMessage());

            //mainLge show
            //  mainLegCount= (int) suBoosterIncomeList.getMainLegAverage();
            maintotalMyTextView.setText(Integer.toString(suBoosterIncomeList.getMainLegCount()));
            maintotalMyTextView.setPaintFlags(maintotalMyTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            mainavgMyTextView.setText("(Avg%: " + (int) suBoosterIncomeList.getMainLegAverage() + "%)");

            //otherLeg show
            //otherLegCount=(int)suBoosterIncomeList.getOtherLegAverage();
            othertotalMyTextView.setText(Integer.toString(suBoosterIncomeList.getOtherLegCount()));
            otheravgMyTextView.setText("(Avg%: " + (int) suBoosterIncomeList.getOtherLegAverage() + "%)");
            othertotalMyTextView.setPaintFlags(othertotalMyTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            //Mainleg show
            suBoosterIncomeAdapter = new SuBoosterIncomeAdapter(SuBoosterIncome.this, arrayListSuBoosterMainLeg);

            //otherLeg show
            suBoosterIncomeAdapter_ = new SuBoosterIncomeAdapter_(SuBoosterIncome.this, arrayListSuBoosterOtherLeg);
            lnSuBooster.setVisibility(View.VISIBLE);
        } else {
            lnSuBooster.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }
}

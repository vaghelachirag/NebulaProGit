package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
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

import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.CarProgramAdapter;
import com.nebulacompanies.ibo.adapter.SuBoosterIncomeAdapter;
import com.nebulacompanies.ibo.adapter.SuBoosterIncomeAdapter_;
import com.nebulacompanies.ibo.model.CarProgramIncomeDetails;
import com.nebulacompanies.ibo.model.CarProgramIncomeLegList;
import com.nebulacompanies.ibo.model.CarProgramIncomeList;
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
 * Created by Palak Mehta on 29-11-2017.
 */

public class CarProgramIncome extends AppCompatActivity implements View.OnClickListener/*, AsyncResponse */ {

    MyTextView eligibleMyTextView, titleoneMyTextView, titletwoMyTextView, titlethreeMyTextView, mainlagMyTextView, otherlagMyTextView,
            maintotalMyTextView, mainavgMyTextView, otherCartotalMyTextView, otheravgMyTextView;

    ListView incomeListView;
    private boolean isLoadMore = false;
    LinearLayout mainlagLinearLayout, otherlagLinearLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Session session;
    Boolean isRefreshed = false;

    SpannableString content1, content2;
    int mainLegCount;
    int otherLegCount;
    final Handler handler = new Handler();
    Runnable r;
    boolean oneTime = false;
    SuBoosterIncomeAdapter suBoosterIncomeAdapter;
    SuBoosterIncomeAdapter_ suBoosterIncomeAdapter_;

    ArrayList<String> particular = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> payment = new ArrayList<>();
    CarProgramAdapter carProgramAdapter;

    private APIInterface mAPIInterface;
    public static final String TAG = "CarProgramIncome";
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    CarProgramIncomeList carProgramIncomeList;
    LinearLayout lnCarProgram;

    private ArrayList<CarProgramIncomeLegList> arrayListCarProgramIncomeLeg = new ArrayList<>();
    private ArrayList<CarProgramIncomeLegList> arrayListCarProgramIncomeLegs = new ArrayList<>();

    SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;

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
        getCarProgramList();
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
        tv.setText(R.string.car_program);
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

    void init() {
        cd = new ConnectionDetector(getApplicationContext());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        session = new Session(this);

        eligibleMyTextView = (MyTextView) findViewById(R.id.eligible_income_text);
        mainlagLinearLayout = (LinearLayout) findViewById(R.id.main_leg_income);
        otherlagLinearLayout = (LinearLayout) findViewById(R.id.other_leg_income);
        titleoneMyTextView = (MyTextView) findViewById(R.id.title_one_txt);
        titletwoMyTextView = (MyTextView) findViewById(R.id.title_two_txt);
        titlethreeMyTextView = (MyTextView) findViewById(R.id.title_three_txt);
        maintotalMyTextView = (MyTextView) findViewById(R.id.main_total_text);
        mainavgMyTextView = (MyTextView) findViewById(R.id.main_avg_text);
        otherCartotalMyTextView = (MyTextView) findViewById(R.id.other_total_text);
        otheravgMyTextView = (MyTextView) findViewById(R.id.other_avg_text);
        incomeListView = (ListView) findViewById(R.id.listview_income);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.booster_swipe_refresh_layout);
        lnCarProgram = (LinearLayout) findViewById(R.id.ln_su_booster);

        titleoneMyTextView.setText(R.string.date);
        titletwoMyTextView.setText("Particular");
        titlethreeMyTextView.setText("Payment");


        mainlagLinearLayout.setOnClickListener(this);
        otherlagLinearLayout.setOnClickListener(this);


        incomeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (incomeListView.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(incomeListView.getFirstVisiblePosition() == 0 && incomeListView.getChildAt(0).getTop() == 0);
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

    private void getCarProgramList() {
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
            Call<CarProgramIncomeDetails> wsCallingCarProgramIncomeDetails = mAPIInterface.getCarProgramIncome("CarProgramIncome");
            wsCallingCarProgramIncomeDetails.enqueue(new Callback<CarProgramIncomeDetails>() {
                @Override
                public void onResponse(Call<CarProgramIncomeDetails> call, Response<CarProgramIncomeDetails> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    arrayListCarProgramIncomeLeg.clear();
                    arrayListCarProgramIncomeLegs.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                carProgramIncomeList = response.body().getData();
                                arrayListCarProgramIncomeLeg.addAll(carProgramIncomeList.getMainLeg());
                                arrayListCarProgramIncomeLegs.addAll(carProgramIncomeList.getOtherLeg());


                                eligibleMyTextView.setText(carProgramIncomeList.getEligibleMessage());

                                //main leg show
                                maintotalMyTextView.setText(Integer.toString(carProgramIncomeList.getMainLegCount()));
                                maintotalMyTextView.setPaintFlags(maintotalMyTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                mainavgMyTextView.setText("(Avg%: " + (int) carProgramIncomeList.getMainLegAverage() + "%)");

                                //other leg show
                            /*otherLegCount=(Integer) carProgramIncomeList.getOtherLegCount();
                            content2 = new SpannableString(Integer.toString(otherLegCount));
                            content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
                            othertotalMyTextView.setText(content2);*/
                                otherCartotalMyTextView.setText(Integer.toString(carProgramIncomeList.getOtherLegCount()));
                                otherCartotalMyTextView.setPaintFlags(otherCartotalMyTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                otheravgMyTextView.setText("(Avg%: " + (int) carProgramIncomeList.getOtherLegAverage() + "%)");

                                setParticulars();

                                //Mainleg show
                                suBoosterIncomeAdapter = new SuBoosterIncomeAdapter(CarProgramIncome.this, arrayListCarProgramIncomeLeg);

                                //otherleg show
                                suBoosterIncomeAdapter_ = new SuBoosterIncomeAdapter_(CarProgramIncome.this, arrayListCarProgramIncomeLegs);
                                lnCarProgram.setVisibility(View.VISIBLE);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                lnCarProgram.setVisibility(View.VISIBLE);
                                serviceUnavailable();
                            }
                        } else {
                            lnCarProgram.setVisibility(View.VISIBLE);
                            serviceUnavailable();
                        }
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(CarProgramIncome.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        lnCarProgram.setVisibility(View.VISIBLE);
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<CarProgramIncomeDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            lnCarProgram.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }

    private void setParticulars() {
        particular.add("Down Payment");
        particular.add("EMI 1");
        particular.add("EMI 2");
        particular.add("EMI 3");
        particular.add("EMI 4");
        particular.add("EMI 5");
        particular.add("EMI 6");
        particular.add("EMI 7");
        particular.add("EMI 8");
        particular.add("EMI 9");
        particular.add("EMI 10");
        particular.add("EMI 11");
        particular.add("EMI 12");
        particular.add("EMI 13");
        particular.add("EMI 14");
        particular.add("EMI 15");
        particular.add("EMI 16");
        particular.add("EMI 17");
        particular.add("EMI 18");
        particular.add("EMI 19");
        particular.add("EMI 20");
        particular.add("EMI 21");
        particular.add("EMI 22");
        particular.add("EMI 23");
        particular.add("EMI 24");
        particular.add("EMI 25");
        particular.add("EMI 26");
        particular.add("EMI 27");
        particular.add("EMI 28");
        particular.add("EMI 29");
        particular.add("EMI 30");
        particular.add("EMI 31");
        particular.add("EMI 32");
        particular.add("EMI 33");
        particular.add("EMI 34");
        particular.add("EMI 35");
        particular.add("EMI 36");
        particular.add("EMI 37");
        particular.add("EMI 38");
        particular.add("EMI 39");
        particular.add("EMI 40");
        particular.add("EMI 41");
        particular.add("EMI 42");
        particular.add("EMI 43");
        particular.add("EMI 44");
        particular.add("EMI 45");
        particular.add("EMI 46");
        particular.add("EMI 47");
        particular.add("EMI 48");
        carProgramAdapter = new CarProgramAdapter(this, date, particular, payment);
        incomeListView.setAdapter(carProgramAdapter);
        carProgramAdapter.notifyDataSetChanged();
    }

   /* private void callAPI(){
        if(session != null) {
            CAR_PROGRAM_INCOME_API = Config.NEB_API + "/API/SubIncomes/ShowSubIncomes?IncomeId=10&MemberID=" + session.getLoginID();
            asyncComplex = new AsyncComplex(this, CAR_PROGRAM_INCOME_API, "Car_Program_Income", isRefreshed);
            asyncComplex.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex);
        }
    }*/

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (suBoosterIncomeAdapter != null) {
                suBoosterIncomeAdapter.clearData();
                suBoosterIncomeAdapter.notifyDataSetChanged();
            }
            if (suBoosterIncomeAdapter_ != null) {
                suBoosterIncomeAdapter_.clearData();
                suBoosterIncomeAdapter_.notifyDataSetChanged();
            }
            if (carProgramAdapter != null) {
                carProgramAdapter.clearData();
                carProgramAdapter.notifyDataSetChanged();
            }
            maintotalMyTextView.setText(null);
            mainavgMyTextView.setText(null);
            otherCartotalMyTextView.setText(null);
            otheravgMyTextView.setText(null);
            eligibleMyTextView.setText(null);
            getCarProgramList();
            mSwipeRefreshLayout.setRefreshing(true);
            showRecords();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    /*@Override
    public void processFinish(String output, String Tag) {
        if (output == null) {
            output = "THERE WAS AN ERROR";
            noRecordsTextView.setVisibility(View.VISIBLE);
            noRecordsTextView.setText("Network Error");
        }

        String response = output.toString();
        Log.i("INFO", "response :" + response);

        if (Tag.equals("Car_Program_Income")) {
            try {
                JSONObject object = new JSONObject(response);
                String status_code = object.getString("Statuscode");

                if (status_code.equals("1")) {

                    JSONObject jsonObject = object.getJSONObject("Data");

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
                       // otherLegAvgPer = jsonObject.getString("OtherLegAverage").toString();
                        int b = (int) Math.floor(Double.parseDouble(jsonObject.getString("OtherLegAverage")));
                        Log.i("INFO","call for b:-"+b);
                        otherLegAvgPer= String.valueOf(b);
                    }

                    eligibleMessage = jsonObject.getString("eligibleMessage").toString();

                    content1 = new SpannableString(Integer.toString(mainLegCount));
                    content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
                    maintotalMyTextView.setText(content1);
                    //maintotalMyTextView.setText(Integer.toString(mainLegCount));
                    //mainavgMyTextView.setText("(Avg%: " + Integer.toString(mainLegAvgPer) + "%)");
                    mainavgMyTextView.setText("(Avg%: " + mainLegAvgPer + "%)");

                    content2 = new SpannableString(Integer.toString(otherLegCount));
                    content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
                    othertotalMyTextView.setText(content2);
                    //othertotalMyTextView.setText(Integer.toString(otherLegCount));
                    //otheravgMyTextView.setText("(Avg%: " + Integer.toString(otherLegAvgPer) + "%)");
                    otheravgMyTextView.setText("(Avg%: " + otherLegAvgPer + "%)");

                    eligibleMyTextView.setText(eligibleMessage);

                    JSONArray new_array_main_leg = jsonObject.getJSONArray("MainLeg");

                    for (int i = 0; i < new_array_main_leg.length(); i++) {
                        try {
                            JSONObject jsonObject1 = new_array_main_leg.getJSONObject(i);

                            iboId.add(jsonObject1.getString("IBOKeyID").toString());
                            ibo.add(jsonObject1.getString("IBO").toString());
                            receipt.add(jsonObject1.getInt("Percentage"));

                            suBoosterIncomeAdapter = new SuBoosterIncomeAdapter(this, iboId, ibo, receipt);

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

                            suBoosterIncomeAdapter_ = new SuBoosterIncomeAdapter_(this, iboId_, ibo_, receipt_);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    setParticulars();
                }
                else if(status_code.equals("0")){
                    // NO Data Found
                    noRecordsTextView.setVisibility(View.VISIBLE);
                    noRecordsTextView.setText("No records found..!");
                }
                else if(status_code.equals("-1")){
                    // Please Reload, There is an error.
                    noRecordsTextView.setVisibility(View.VISIBLE);
                    noRecordsTextView.setText("Please reload..!");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
*/
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

    /* @TargetApi(Build.VERSION_CODES.HONEYCOMB)
     private void StartAsyncTaskInParallel(AsyncComplex task) {
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
             task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
         else
             task.execute();
     }*/
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_leg_income:
                if (suBoosterIncomeAdapter != null) {
                    if (carProgramIncomeList.getMainLegCount() == 0) {
                        DisplayEmptyDialog(CarProgramIncome.this, 0);
                    } else {
                        ShowMainLegInfo(suBoosterIncomeAdapter);
                    }
                }
                break;
            case R.id.other_leg_income:
                if (suBoosterIncomeAdapter_ != null) {
                    if (carProgramIncomeList.getOtherLegCount() == 0) {
                        DisplayEmptyDialog(CarProgramIncome.this, 0);
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
        dialog1.setContentView(convertView1);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
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
        lnCarProgram.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        incomeListView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        lnCarProgram.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        incomeListView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        lnCarProgram.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        incomeListView.setVisibility(View.GONE);
    }

    void showRecords() {
        //txtErrorTitle.setText(R.string.error_msg_network);
        //imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        incomeListView.setVisibility(View.VISIBLE);
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
                        Intent i = new Intent(CarProgramIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(CarProgramIncome.this);
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
}

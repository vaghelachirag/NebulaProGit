package com.nebulacompanies.ibo.activities;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.GenerationIncomeListViewAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.GenerationIncomeDetails;
import com.nebulacompanies.ibo.model.GenerationIncomeList;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
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

/**
 * Created by Palak Mehta on 7/4/2016.
 */
public class GenerationIncome extends AppCompatActivity /*implements AsyncResponse*/ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    TextView closingdateTextView, mybvperTextView, totalBVCommTextView, moreInfoTextView;

    /*ArrayList<String> closing_date = new ArrayList<>();
    ArrayList<Integer> my_bv__= new ArrayList<>();
    ArrayList<Integer> my_bv= new ArrayList<>();
    ArrayList<Integer> bv_from_sales = new ArrayList<>();
    ArrayList<Integer> fl_bv = new ArrayList<>();
    ArrayList<Integer> dbv = new ArrayList<>();
    ArrayList<Integer> my_bv_commission= new ArrayList<>();
    ArrayList<Integer> dbv_commission= new ArrayList<>();
    ArrayList<Integer> total_bv_commission= new ArrayList<>();*/

    GenerationIncomeListViewAdapter adapter;
    Session session;
    Boolean isRefreshed = false;

    private APIInterface mAPIInterface;
    public static final String TAG = "GenerationIncome";
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    private LinearLayout lnGeneration;
    final Handler handler = new Handler();
    Runnable r;
    boolean oneTime = false;
    SharedPreferences prefs;
    SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;
    private ArrayList<GenerationIncomeList> arrayListGenerationIncomeList = new ArrayList<>();
    String IncomeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generation_income);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }

        setActionBar();
        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(GenerationIncome.this);
        String generationIncome = prefs.getString("task_lists", "");
        if (generationIncome != null && generationIncome != "") {
            LoadData();
        } else {
            getGenerationIncomeList();
        }

    }

    /*void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(R.string.generation_income_); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

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
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.generation_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listview_generation_income);
        closingdateTextView = (TextView) findViewById(R.id.closingdate_geninc);
        mybvperTextView = (TextView) findViewById(R.id.mybv_per_geninc);
        totalBVCommTextView = (TextView) findViewById(R.id.total_bv_comm_geninc);
        moreInfoTextView = (TextView) findViewById(R.id.more_info);
        lnGeneration = (LinearLayout) findViewById(R.id.ln_generation);

        session = new Session(this);

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

    private void getGenerationIncomeList() {
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
            Call<GenerationIncomeDetails> wsCallingGenerationIncomeDetails = mAPIInterface.getGenerationIncome("GenerationIcome");
            wsCallingGenerationIncomeDetails.enqueue(new Callback<GenerationIncomeDetails>() {
                @Override
                public void onResponse(Call<GenerationIncomeDetails> call, Response<GenerationIncomeDetails> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListGenerationIncomeList != null) {
                        arrayListGenerationIncomeList.clear();
                        if (response.isSuccessful()) {

                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListGenerationIncomeList.addAll(response.body().getData());
                                    prefs = PreferenceManager.getDefaultSharedPreferences(GenerationIncome.this);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListGenerationIncomeList);
                                    editor.putString("task_lists", json);
                                    editor.apply();
                                    adapter = new GenerationIncomeListViewAdapter(GenerationIncome.this, arrayListGenerationIncomeList);
                                    listView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    lnGeneration.setVisibility(View.VISIBLE);

                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    lnGeneration.setVisibility(View.VISIBLE);
                                    serviceUnavailable();
                                }
                                if (arrayListGenerationIncomeList.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            } else {
                                lnGeneration.setVisibility(View.VISIBLE);
                                serviceUnavailable();
                            }
                        }
                    } else {
                       /* if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(GenerationIncome.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<GenerationIncomeDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    lnGeneration.setVisibility(View.VISIBLE);
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });

        } else {
            /* noInternetConnection();*/
            LoadData();
        }
    }


    /*private void callAPI(){
        if(session != null) {
            //GENERATION_INCOME_API = Config.NEB_API + "/API/Income/GetGenerationIncome?MemberIDGeneration=" + session.getLoginID();
            GENERATION_INCOME_API = Config.NEB_API + "/API/SubIncomes/ShowSubIncomes?IncomeId=4&MemberID=" + session.getLoginID();
            asyncComplex2 = new AsyncComplex(this, GENERATION_INCOME_API, "Generation_Income", isRefreshed);
            asyncComplex2.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex2);
        }
    }*/

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (adapter != null) {
                adapter.clearData();
                adapter.notifyDataSetChanged();
            }
            getGenerationIncomeList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            /* noInternetConnection();*/
            LoadData();
        }
    }

    /*@Override
    public void processFinish(String output, String Tag) {
        if(output == null) {
            output = "THERE WAS AN ERROR";
            noRecordsTextView.setVisibility(View.VISIBLE);
            noRecordsTextView.setText("Network Error");
        }

        String response = output.toString();
        Log.i("INFO", "Response : Generation Income : " + response);

        if (Tag.equals("Generation_Income")) {
            try {
                JSONObject object = new JSONObject(response);
                String status_code = object.getString("Statuscode");

                if (status_code.equals("1")) {
                    JSONArray new_array = object.getJSONArray("Data");

                    for (int i = 0; i < new_array.length(); i++) {
                        try {
                            JSONObject jsonObject = new_array.getJSONObject(i);

                            closing_date.add(jsonObject.getString("GenerationIncomeClosingDate").toString());
                            my_bv__.add(jsonObject.getInt("GenerationIncomeMyBv"));
                            my_bv.add(jsonObject.getInt("GenerationIncomeMyBvPercentage"));
                            bv_from_sales.add(jsonObject.getInt("GenerationIncomeBV_from_Sales"));
                            fl_bv.add(jsonObject.getInt("GenerationIncomeFLBV"));
                            dbv.add(jsonObject.getInt("GenerationIncomeDBV"));
                            my_bv_commission.add(jsonObject.getInt("GenerationIncomeMyBVCommission"));
                            dbv_commission.add(jsonObject.getInt("GenerationIncomeDBVCommission"));
                            total_bv_commission.add(jsonObject.getInt("GenerationIncomeTotalBVCommission"));

                            adapter = new GenerationIncomeListViewAdapter(this, closing_date, my_bv__, my_bv, bv_from_sales, fl_bv, dbv, my_bv_commission, dbv_commission, total_bv_commission);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            noRecordsTextView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
    }*/


   /* @Override
    public void processFinish(String output, String Tag) {
        if(output == null) {
            output = "THERE WAS AN ERROR";
        }

        String response = output.toString();
        Log.i("INFO", "Response : Generation Income : " + response);

        if(Tag.equals("Generation_Income") && !response.contains("No Data Found")) {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray new_array = object.getJSONArray("Data");

                for (int i = 0; i < new_array.length(); i++) {
                    try {
                        JSONObject jsonObject = new_array.getJSONObject(i);
                        if (jsonObject.getString("Closing_Date").toString().equals("0")) {
                        } else {
                            closing_date.add(jsonObject.getString("Closing_Date").toString());
                            my_bv__.add(jsonObject.getInt("My_BV__"));
                            my_bv.add(jsonObject.getInt("My_BV"));
                            bv_from_sales.add(jsonObject.getInt("BV_from_Sales"));
                            fl_bv.add(jsonObject.getInt("FLBV"));
                            dbv.add(jsonObject.getInt("DBV"));
                            my_bv_commission.add(jsonObject.getInt("My_BV_Commission"));
                            dbv_commission.add(jsonObject.getInt("DBV_Commission"));
                            total_bv_commission.add(jsonObject.getInt("Total_BV_Commission"));
                        }
                        adapter = new GenerationIncomeListViewAdapter(this, closing_date, my_bv__, my_bv, bv_from_sales, fl_bv, dbv, my_bv_commission, dbv_commission, total_bv_commission);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        noRecordsTextView.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            noRecordsTextView.setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }*/

    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
    public void onBackPressed() {
        super.onBackPressed();
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
        lnGeneration.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        lnGeneration.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        lnGeneration.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    public void LoadData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(GenerationIncome.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("task_lists", null);
        Type type = new TypeToken<ArrayList<GenerationIncomeList>>() {
        }.getType();
        arrayListGenerationIncomeList = gson.fromJson(json, type);
        if (json != null) {
            if (adapter == null)
                adapter = new GenerationIncomeListViewAdapter(GenerationIncome.this, arrayListGenerationIncomeList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            lnGeneration.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListGenerationIncomeList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListGenerationIncomeList);
        } else {
            lnGeneration.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
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
                        Intent i = new Intent(GenerationIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(GenerationIncome.this);
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
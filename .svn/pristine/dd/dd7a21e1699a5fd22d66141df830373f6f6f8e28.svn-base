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
import com.nebulacompanies.ibo.adapter.GoldIncomeListViewAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.GoldIncomeDetails;
import com.nebulacompanies.ibo.model.GoldIncomeList;
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

import static com.nebulacompanies.ibo.util.Config.FONT_STYLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.GetTime.SetDateFormats;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 7/4/2016.
 */
public class GoldIncome extends AppCompatActivity /*implements AsyncResponse */ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    GoldIncomeListViewAdapter adapter;

    Session session;
    Boolean isRefreshed = false;

    private APIInterface mAPIInterface;
    public static final String TAG = "GoldIncome";
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    private LinearLayout lnGold;
    final Handler handler = new Handler();
    Runnable r;
    boolean oneTime = false;
    private ArrayList<GoldIncomeList> arrayListGoldIncomeList = new ArrayList<>();
    SharedPreferences prefs;

    SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;

    String IncomeName;
    MyTextView  goldincometxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gold_income);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }

        setActionBar();
        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(GoldIncome.this);
        String goldIncome = prefs.getString("gold_income_list", "");
        if (goldIncome != null && goldIncome != "") {
            LoadGoldData();
        } else {
            getGoldIncomeList();
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
        tv.setText(R.string.gold_income_); // ActionBar title text
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
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(IncomeName); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }




    void init() {
        cd = new ConnectionDetector(getApplicationContext());
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.gold_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listview_gold_income);
        lnGold = (LinearLayout) findViewById(R.id.ln_gold);


        goldincometxt = (MyTextView) findViewById(R.id.goldincome);
        goldincometxt.setText(IncomeName);

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

    private void getGoldIncomeList() {
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
            Call<GoldIncomeDetails> wsCallingGoldIncomeDetails = mAPIInterface.getGoldIncome("GoldIncome");
            wsCallingGoldIncomeDetails.enqueue(new Callback<GoldIncomeDetails>() {
                @Override
                public void onResponse(Call<GoldIncomeDetails> call, Response<GoldIncomeDetails> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListGoldIncomeList != null) {
                        arrayListGoldIncomeList.clear();

                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListGoldIncomeList.addAll(response.body().getData());
                                     prefs = PreferenceManager.getDefaultSharedPreferences(GoldIncome.this);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListGoldIncomeList);
                                    editor.putString("gold_income_list", json);
                                    editor.apply();

                                    adapter = new GoldIncomeListViewAdapter(GoldIncome.this, arrayListGoldIncomeList);
                                    listView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    lnGold.setVisibility(View.VISIBLE);
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }
                                if (arrayListGoldIncomeList.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            } else {
                                lnGold.setVisibility(View.VISIBLE);
                                serviceUnavailable();
                            }
                        }
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(GoldIncome.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        lnGold.setVisibility(View.VISIBLE);
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<GoldIncomeDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    lnGold.setVisibility(View.VISIBLE);
                    serviceUnavailable();
                }
            });

        } else {
            lnGold.setVisibility(View.VISIBLE);
            // noInternetConnection();
            LoadGoldData();
        }
    }

    /*private void callAPI(){
        if(session != null) {
            GOLD_INCOME_API = Config.NEB_API + "/API/SubIncomes/ShowSubIncomes?IncomeId=5&MemberID=" + session.getLoginID();
            asyncComplex3 = new AsyncComplex(this, GOLD_INCOME_API, "Gold_Income", isRefreshed);
            asyncComplex3.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex3);
        }
    }*/

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (adapter != null) {
                adapter.clearData();
                adapter.notifyDataSetChanged();
            }
            getGoldIncomeList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            //noInternetConnection();
            LoadGoldData();
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
        if (Tag.equals("Gold_Income")) {
            try {
                JSONObject object = new JSONObject(response);
                String status_code = object.getString("Statuscode");

                if (status_code.equals("1")) {

                    JSONArray new_array = object.getJSONArray("Data");

                    for (int i = 0; i < new_array.length(); i++) {
                        try {
                            JSONObject jsonObject = new_array.getJSONObject(i);

                            closing_date.add(jsonObject.getString("GoldIncomeClosingDate").toString());
                            rank.add(jsonObject.getString("GoldIncomeRank").toString());
                            gold_income.add(jsonObject.getInt("GoldIncome_"));

                            adapter = new GoldIncomeListViewAdapter(this, closing_date, rank, gold_income);
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
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(AsyncComplex task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
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
        lnGold.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        lnGold.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        lnGold.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
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
                        Intent i = new Intent(GoldIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(GoldIncome.this);
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

    public void LoadGoldData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(GoldIncome.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("gold_income_list", null);
        Type type = new TypeToken<ArrayList<GoldIncomeList>>() {
        }.getType();
        arrayListGoldIncomeList = gson.fromJson(json, type);
        if (json!=null) {
            if (adapter == null)
                adapter = new GoldIncomeListViewAdapter(GoldIncome.this, arrayListGoldIncomeList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            lnGold.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListGoldIncomeList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListGoldIncomeList);
        }else {
            lnGold.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }
}

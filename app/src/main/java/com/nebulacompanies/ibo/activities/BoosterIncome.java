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
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.BoosterIncomeAdapter;
import com.nebulacompanies.ibo.adapter.BoosterPayoutAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.BoosterIncomeDetails;
import com.nebulacompanies.ibo.model.BoosterIncomeList;
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
 * Created by Sagar Virvani on 21-11-2017.
 */

public class BoosterIncome extends AppCompatActivity /*implements AsyncResponse*/ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView, boosterPayoutListView;
    MyTextView boosterPayouttitleTextView;
    BoosterIncomeAdapter adapter;
    Session session;
    Boolean isRefreshed = false;
    LinearLayout boosterpayoutmainLinearLayout, boosterpayoutLinearLayout;
    BoosterPayoutAdapter boosterPayoutAdapter;
    ArrayList<String> sr = new ArrayList<>();
    ArrayList<String> booster_date = new ArrayList<>();
    ArrayList<String> payout = new ArrayList<>();
    ScrollView boosterincomeScrollView;
    private APIInterface mAPIInterface;
    public static final String TAG = "BoosterIncome";
    ConnectionDetector cd;
    //  LinearLayout lnBoosterIncome;

    //Error View
    private LinearLayout llEmpty, lnBoosPayDetials;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    private ArrayList<BoosterIncomeList> arrayListBoosterIncomeList = new ArrayList<>();
    final Handler handler = new Handler();
    Runnable r;
    boolean oneTime = false;
    SharedPreferences prefs;

    SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;

    String IncomeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_booster_income);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }
        setActionBar();
        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(BoosterIncome.this);
        String boosterIncome = prefs.getString("booster_income_list", "");
        if (boosterIncome != null && boosterIncome != "") {
            LoadBoosterData();
        } else {
            getBoosterIncomeList();
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
        tv.setText(R.string.booster_income); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

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
      //  Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        session = new Session(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.ahd_booster_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.ahd_booster_income);
        //  lnBoosterIncome = (LinearLayout) findViewById(R.id.ln_booster_income);

        LinearLayout footerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.booster_payout, null);
        listView.addFooterView(footerLayout);

        boosterpayoutmainLinearLayout = (LinearLayout) findViewById(R.id.boos_pay_detials);
        boosterpayoutLinearLayout = (LinearLayout) findViewById(R.id.booster_payout_title);
        lnBoosPayDetials = (LinearLayout) findViewById(R.id.ln_boos_pay_detials);
        boosterPayoutListView = (ListView) findViewById(R.id.booster_payout);
        boosterincomeScrollView = (ScrollView) findViewById(R.id.booster_Scroling);


        boosterPayouttitleTextView = (MyTextView) findViewById(R.id.booster_income_title);
        boosterPayouttitleTextView.setText(R.string.Booster_Income_AavaasAhmedabad);
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

    private void getBoosterIncomeList() {
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
            Call<BoosterIncomeDetails> wsCallingBoosterIncomeDetails = mAPIInterface.getBoosterIncome("BoosterIncome");
            wsCallingBoosterIncomeDetails.enqueue(new Callback<BoosterIncomeDetails>() {
                @Override
                public void onResponse(Call<BoosterIncomeDetails> call, Response<BoosterIncomeDetails> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListBoosterIncomeList != null) {
                        arrayListBoosterIncomeList.clear();

                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    boosterincome();
                                    arrayListBoosterIncomeList.addAll(response.body().getData());

                                     prefs = PreferenceManager.getDefaultSharedPreferences(BoosterIncome.this);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListBoosterIncomeList);
                                    editor.putString("booster_income_list", json);
                                    editor.apply();


                                    if(arrayListBoosterIncomeList.size() > 0) {
                                        adapter = new BoosterIncomeAdapter(BoosterIncome.this, arrayListBoosterIncomeList);
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        boosterincomeScrollView.setVisibility(View.VISIBLE);
                                    } else{
                                        noRecordsFound();
                                    }
                                    boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
                                    boosterpayoutLinearLayout.setVisibility(View.VISIBLE);
                                    boosterPayoutListView.setVisibility(View.VISIBLE);
                                    boosterincomeScrollView.setVisibility(View.VISIBLE);
                                    boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
                                    lnBoosPayDetials.setVisibility(View.VISIBLE);

                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    boosterincomeScrollView.setVisibility(View.VISIBLE);
                                    serviceUnavailable();
                                }
                                if (arrayListBoosterIncomeList.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            } else {
                                boosterincomeScrollView.setVisibility(View.VISIBLE);
                                serviceUnavailable();
                            }
                        }
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(BoosterIncome.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        boosterincomeScrollView.setVisibility(View.VISIBLE);
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<BoosterIncomeDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    boosterincomeScrollView.setVisibility(View.VISIBLE);
                    serviceUnavailable();
                }
            });
        } else {
            boosterincomeScrollView.setVisibility(View.VISIBLE);
            //noInternetConnection();
            LoadBoosterData();
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if (listItem != null) {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    /*private void callAPI(){
        if(session != null) {
            BOOSTER_INCOME_API = Config.NEB_API + "/API/SubIncomes/ShowSubIncomes?IncomeId=7&MemberID=" + session.getLoginID();
            asyncComplex = new AsyncComplex(BoosterIncome.this, BOOSTER_INCOME_API, "Booster_Income", isRefreshed);
            asyncComplex.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex);
        }
    }*/
    private void boosterincome() {





        if(arrayListBoosterIncomeList.size() > 0) {
            sr.add("1");
            sr.add("2");
            sr.add("3");
            sr.add("4");
            boosterPayoutAdapter = new BoosterPayoutAdapter(BoosterIncome.this, sr, booster_date, payout);
            boosterPayoutListView.setAdapter(boosterPayoutAdapter);
            boosterPayoutAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(boosterPayoutListView);
            boosterincomeScrollView.setVisibility(View.VISIBLE);
        } else{
            noRecordsFound();
        }
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (adapter != null) {
                adapter.clearData();
                boosterpayoutmainLinearLayout.setVisibility(View.GONE);
                boosterpayoutLinearLayout.setVisibility(View.GONE);
                boosterPayoutListView.setVisibility(View.GONE);
                sr.clear();
                adapter.notifyDataSetChanged();
            }
            getBoosterIncomeList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            sr.clear();
            LoadBoosterData();
        }
    }

    /*  @Override
      public void processFinish(String output, String Tag) {
          if (output == null) {
              output = "THERE WAS AN ERROR";
              noRecordsTextView.setVisibility(View.VISIBLE);
              noRecordsTextView.setText("Network Error");
          }
          String response = output.toString();
          if (Tag.equals("Booster_Income")) {
              try {
                  JSONObject object = new JSONObject(response);
                  String status_code = object.getString("Statuscode");

                  if (status_code.equals("1")) {

                      JSONArray new_array = object.getJSONArray("Data");

                      for (int i = 0; i < new_array.length(); i++) {
                          try {
                              JSONObject jsonObject = new_array.getJSONObject(i);

                              flat_no.add(jsonObject.getString("flatno").toString());
                              block_no.add(jsonObject.getString("blockno").toString());
                              customer_id.add(jsonObject.getString("ID").toString());
                              customer_name.add(jsonObject.getString("Name").toString());
                              payment_plan.add(jsonObject.getString("PaymentOptions").toString());
                              amount_received.add(jsonObject.getInt("Receipt"));
                              pending_amount.add(jsonObject.getInt("PendingAmount"));
                              installment_amount.add(jsonObject.getInt("installmentamount"));
                              product_name.add(jsonObject.getString("Product").toString());
                              type.add(jsonObject.getString("Productoption").toString());
                              payment_percent.add(jsonObject.getInt("percent"));

                              adapter = new BoosterIncomeAdapter(BoosterIncome.this, flat_no, block_no, customer_id, customer_name, payment_plan, amount_received, pending_amount, installment_amount, product_name, type, payment_percent);
                              listView.setAdapter(adapter);
                              adapter.notifyDataSetChanged();
                              boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
                              boosterpayoutLinearLayout.setVisibility(View.VISIBLE);
                              boosterPayoutListView.setVisibility(View.VISIBLE);
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
        listView.setVisibility(View.GONE);
        boosterincomeScrollView.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        boosterincomeScrollView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        boosterincomeScrollView.setVisibility(View.VISIBLE);
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
                        Intent i = new Intent(BoosterIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        Config.CUSTOMER_LOGIN_ID = "";
                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(BoosterIncome.this);
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

    public void LoadBoosterData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(BoosterIncome.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("booster_income_list", null);
        Type type = new TypeToken<ArrayList<BoosterIncomeList>>() {
        }.getType();
        arrayListBoosterIncomeList = gson.fromJson(json, type);
        if (json != null) {
            if (adapter == null)
                adapter = new BoosterIncomeAdapter(BoosterIncome.this, arrayListBoosterIncomeList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            boosterincome();
            boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
            boosterpayoutLinearLayout.setVisibility(View.VISIBLE);
            boosterPayoutListView.setVisibility(View.VISIBLE);
            boosterincomeScrollView.setVisibility(View.VISIBLE);
            boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
            lnBoosPayDetials.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListBoosterIncomeList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListBoosterIncomeList);
        } else {
            boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
            boosterpayoutLinearLayout.setVisibility(View.VISIBLE);
            boosterPayoutListView.setVisibility(View.VISIBLE);
            boosterincomeScrollView.setVisibility(View.VISIBLE);
            boosterpayoutmainLinearLayout.setVisibility(View.VISIBLE);
            lnBoosPayDetials.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }
}


package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.AavaasPhase1ListViewAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.ProductMaster;
import com.nebulacompanies.ibo.model.ProductMasterList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.ID_HOLIDAY;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class ProductListHoliday extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    MyTextView proNameTextView, retPriceTextView, moreInfoTextView;
    private APIInterface mAPIInterface;
    Boolean isRefreshed = false;
    int ProjectId;
    String ProjectName;
    public static ArrayList<ProductMasterList> arrayListProductMaster = new ArrayList<>();
    public static final String TAG = "ProductListDwarka";
    AavaasPhase1ListViewAdapter aavaasPhase1ListViewAdapter;
    LinearLayout lnAavaas;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_aavaas_phase1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setActionBar();
        init();


        prefs = PreferenceManager.getDefaultSharedPreferences(ProductListHoliday.this);
        String productListHolidays = prefs.getString("ProductList", "");
        if (productListHolidays != null && productListHolidays != "") {
            LoadData();
        } else {
            getHyderabadList();
        }
    }

    void setActionBar() {
        MyTextView tv = new MyTextView(getApplicationContext());
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText("Holiday");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);

        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));

    }

    void init() {
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.phase1_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listview_products1);
        proNameTextView = (MyTextView) findViewById(R.id.proname);
        lnAavaas = (LinearLayout) findViewById(R.id.ln_aaavaas);

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
            if (aavaasPhase1ListViewAdapter != null) {
                aavaasPhase1ListViewAdapter.clearData();
                aavaasPhase1ListViewAdapter.notifyDataSetChanged();
            }
            getHyderabadList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            LoadData();
        }
    }

    private void getHyderabadList() {
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
            Call<ProductMaster> wsCallingProductMaster = mAPIInterface.getProductMaster(ID_HOLIDAY);
            wsCallingProductMaster.enqueue(new Callback<ProductMaster>() {
                @Override
                public void onResponse(Call<ProductMaster> call, Response<ProductMaster> response) {

                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    arrayListProductMaster.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            lnAavaas.setVisibility(View.VISIBLE);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListProductMaster.addAll(response.body().getData());
                                prefs = PreferenceManager.getDefaultSharedPreferences(ProductListHoliday.this);
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(arrayListProductMaster);
                                editor.putString("ProductList", json);
                                editor.apply();
                                aavaasPhase1ListViewAdapter = new AavaasPhase1ListViewAdapter(ProductListHoliday.this, arrayListProductMaster);
                                listView.setAdapter(aavaasPhase1ListViewAdapter);
                                aavaasPhase1ListViewAdapter.notifyDataSetChanged();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                            if (arrayListProductMaster.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            }
                        } else {
                            serviceUnavailable();
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<ProductMaster> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            LoadData();
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
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    public void LoadData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(ProductListHoliday.this);
        Gson gson = new Gson();
        String json = prefs.getString("ProductList", null);
        Type type = new TypeToken<ArrayList<ProductMasterList>>() {
        }.getType();
        arrayListProductMaster = gson.fromJson(json, type);
        if (json != null) {
            lnAavaas.setVisibility(View.VISIBLE);
            aavaasPhase1ListViewAdapter = new AavaasPhase1ListViewAdapter(ProductListHoliday.this, arrayListProductMaster);
            listView.setAdapter(aavaasPhase1ListViewAdapter);
            aavaasPhase1ListViewAdapter.notifyDataSetChanged();
        } else {
            lnAavaas.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            noInternetConnection();
        }
    }
}
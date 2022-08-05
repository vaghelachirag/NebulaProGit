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
import com.nebulacompanies.ibo.adapter.DwarkaListViewAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.ProductMaster;
import com.nebulacompanies.ibo.model.ProductMasterList;
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
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 9/16/2016.
 */
public class ProductListDwarka extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    DwarkaListViewAdapter dwarkaListViewAdapter;
    ListView listView;
    MyTextView proNameTextView;
    private APIInterface mAPIInterface;
    Boolean isRefreshed = false;
    int ProjectId;
    public static ArrayList<ProductMasterList> arrayListProductMaster = new ArrayList<>();
    public static final String TAG = "ProductListDwarka";

    //Error View
    private LinearLayout llEmpty, lnProjectDwarka;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_dwarka);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            ProjectId = b.getInt("ProjectId");
        }

        setActionBar();
        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(ProductListDwarka.this);
        String projectList = prefs.getString("product_list_dwarka", "");
        if (projectList != null && projectList != "") {
            LoadProductListDwarkaData();
        } else {
            getDwarkaList();
        }

    }

    void setActionBar() {
        MyTextView tv = new MyTextView(getApplicationContext());
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        /*if(ProjectId == Constants.ID_HAWTHORN_DWARKA) {
            tv.setText(R.string.product_list_dw1);
        }
        else if(ProjectId == Constants.ID_AAVAAS_HYDERABD){
            tv.setText(R.string.product_list_hay1);
        }
        else if(ProjectId == Constants.ID_AAVAAS_CHANGODER_COMPLEX){
            tv.setText(R.string.product_list_complex);
        }
        else if(ProjectId == Constants.ID_HOLIDAY){
            tv.setText(R.string.product_list_holiday);
        }*/
        tv.setText(R.string.product_list_dw1);
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
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.dwarka_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listview_products4);
        proNameTextView = (MyTextView) findViewById(R.id.proname3);
        lnProjectDwarka = (LinearLayout) findViewById(R.id.ln_project_dwarka);

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
            if (dwarkaListViewAdapter != null) {
                dwarkaListViewAdapter.clearData();
                dwarkaListViewAdapter.notifyDataSetChanged();
            }
            getDwarkaList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
           /* if (arrayListProductMaster.size() == 0||arrayListProductMaster.size() < 0){
                noInternetConnection();
            }else {
                LoadProductListDwarkaData();
            }*/

            if (arrayListProductMaster.size() < 0) {
                noInternetConnection();

            } else {
                LoadProductListDwarkaData();
            }
        }
    }

    private void getDwarkaList() {
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
            Call<ProductMaster> wsCallingProductMaster = mAPIInterface.getProductMaster(ProjectId);
            wsCallingProductMaster.enqueue(new Callback<ProductMaster>() {
                @Override
                public void onResponse(Call<ProductMaster> call, Response<ProductMaster> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            lnProjectDwarka.setVisibility(View.VISIBLE);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListProductMaster.clear();
                                arrayListProductMaster.addAll(response.body().getData());
                                prefs = PreferenceManager.getDefaultSharedPreferences(ProductListDwarka.this);
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(arrayListProductMaster);
                                editor.putString("product_list_dwarka", json);
                                editor.apply();
                                dwarkaListViewAdapter = new DwarkaListViewAdapter(ProductListDwarka.this, arrayListProductMaster);
                                listView.setAdapter(dwarkaListViewAdapter);
                                dwarkaListViewAdapter.notifyDataSetChanged();
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
            if (arrayListProductMaster.size() < 0) {
                noInternetConnection();
            } else {
                LoadProductListDwarkaData();
            }
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
        lnProjectDwarka.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        lnProjectDwarka.setVisibility(View.VISIBLE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        lnProjectDwarka.setVisibility(View.VISIBLE);
    }


    public void LoadProductListDwarkaData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(ProductListDwarka.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("product_list_dwarka", null);
        Type type = new TypeToken<ArrayList<ProductMasterList>>() {
        }.getType();
        arrayListProductMaster = gson.fromJson(json, type);
        if (json != null) {
            if (dwarkaListViewAdapter == null) {
           /* dwarkaListViewAdapter = new DwarkaListViewAdapter(ProductListDwarka.this, arrayListProductMaster);
        listView.setAdapter(dwarkaListViewAdapter);
        dwarkaListViewAdapter.notifyDataSetChanged();
        lnGold.setVisibility(View.VISIBLE);*/
                dwarkaListViewAdapter = new DwarkaListViewAdapter(ProductListDwarka.this, arrayListProductMaster);
                listView.setAdapter(dwarkaListViewAdapter);
                dwarkaListViewAdapter.notifyDataSetChanged();
                listView.setVisibility(View.VISIBLE);
                lnProjectDwarka.setVisibility(View.VISIBLE);
                Log.i("INFO", "Call For arrayList:-" + arrayListProductMaster.size());
                Log.i("INFO", "Call For arrayList:-" + arrayListProductMaster);
            }
        }else {
            noInternetConnection();
        }
    }
}

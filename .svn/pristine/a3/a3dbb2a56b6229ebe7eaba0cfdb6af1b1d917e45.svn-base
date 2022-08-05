package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.MySalesHolidayAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.MySalesAavaasDetails;
import com.nebulacompanies.ibo.model.MySalesList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.ID_HOLIDAY;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class MySalesHoliday extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView lview;
    MyTextView custNameTextView, aptTextView;
    LinearLayout linearLayout;

    MySalesHolidayAdapter salesAdapter;
    Session session;
    Boolean isRefreshed = false;
    int projectId;
    String projectName;

    ConnectionDetector cd;
    private APIInterface mAPIInterface;
    public static final String TAG = "MySalesHoliday";

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    public static ArrayList<MySalesList> MySalesHolidayList = new ArrayList<>();
    LinearLayout lnSpotIncome;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_sales_holiday_new, container, false);

        init(view);
        getMySalesHolidayList();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)
            return;
        projectId=getArguments().getInt("ProjectId");
        session = new Session(getActivity());
        cd = new ConnectionDetector(getActivity().getApplicationContext());
      //  Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
    }

    void init(View view) {
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.holiday_sales_swipe_refresh_layout);
        lview = (ListView)view.findViewById(R.id.listview_my_sales_holiday);
        linearLayout = (LinearLayout)view.findViewById(R.id.tablelayout8);
        lnSpotIncome = (LinearLayout)view.findViewById(R.id.ln_spot_income);

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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (salesAdapter != null) {
                salesAdapter.clearData();
                salesAdapter.notifyDataSetChanged();
            }
            getMySalesHolidayList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            LoadData();
        }
    }

    private void getMySalesHolidayList() {
        if (Utils.isNetworkAvailable(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<MySalesAavaasDetails> wsCallingMySalesHolidayDetails = mAPIInterface.getMySalesAavaas("Vacations", "0", "0", ID_HOLIDAY);
            wsCallingMySalesHolidayDetails.enqueue(new Callback<MySalesAavaasDetails>() {
                @Override
                public void onResponse(Call<MySalesAavaasDetails> call, Response<MySalesAavaasDetails> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                MySalesHolidayList.clear();
                                MySalesHolidayList.addAll(response.body().getData());
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(MySalesHolidayList);
                                editor.putString("SalesHoliday", json);
                                editor.apply();

                                salesAdapter = new MySalesHolidayAdapter(getActivity(), MySalesHolidayList);
                                lview.setAdapter(salesAdapter);
                                salesAdapter.notifyDataSetChanged();
                                lnSpotIncome.setVisibility(View.VISIBLE);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                            if (MySalesHolidayList.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                lview.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                lview.setVisibility(View.GONE);
                            }
                        } else {
                            serviceUnavailable();
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<MySalesAavaasDetails> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            LoadData();
        }
    }
    void initError(View view){
        llEmpty = (LinearLayout)view.findViewById(R.id.llEmpty);
        imgError = (ImageView)view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView)view.findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView)view. findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void noRecordsFound(){
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        lview.setVisibility(View.GONE);
    }

    void serviceUnavailable(){
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);

    }

    void noInternetConnection(){
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }

    public void LoadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString("SalesHoliday", null);
        Type type = new TypeToken<ArrayList<MySalesList>>() {
        }.getType();
        MySalesHolidayList = gson.fromJson(json, type);
        if (json != null) {
            salesAdapter = new MySalesHolidayAdapter(getActivity(), MySalesHolidayList);
            lview.setAdapter(salesAdapter);
            salesAdapter.notifyDataSetChanged();
            lnSpotIncome.setVisibility(View.VISIBLE);
        } else {
            noInternetConnection();
        }
    }
}

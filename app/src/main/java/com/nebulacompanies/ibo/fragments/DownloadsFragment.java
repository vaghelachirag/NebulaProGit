package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DownloadRateCharts;
import com.nebulacompanies.ibo.adapter.DownloadsAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.RateChartList;
import com.nebulacompanies.ibo.model.RateCharts;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.RecyclerItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 12/29/2016.
 */

public class DownloadsFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView listView;
    DownloadsAdapter downloadsAdapter;
    Boolean isRefreshed = false;
    public static ArrayList<RateChartList> arrayListRateChartList = new ArrayList<>();
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    ConnectionDetector cd;
    private APIInterface mAPIInterface;
    public static final String TAG = "ratechart";
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences prefs;
    MaterialProgressBar materialProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloads_project_list, container, false);
        init(view);
        //getIncomeHistory();
        //getIncomeHistoryGraph();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String downloadList = prefs.getString("download_list", "");
        if (downloadList != null && downloadList != "") {
            LoadDownloadData();
        } else {
            getRateChartList();
        }


        return view;
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloads_project_list);


        setActionBar();
        init();
        getRateChartList();
    }*/


    void init(View view) {
        cd = new ConnectionDetector(getActivity());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.downloads_project_list_swipe_refresh_layout);
        listView = (RecyclerView) view.findViewById(R.id.downloads_project_list);
        materialProgressBar= (MaterialProgressBar) view.findViewById(R.id.progresbar);
        /*listView.setOnItemClickListener(this);*/
        DividerItemDecoration myDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
        listView.addItemDecoration(myDivider);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

       /* listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });*/
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), listView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DownloadRateCharts.class);
                        intent.putExtra("ProductName", arrayListRateChartList.get(position).getProjectName());
                        intent.putExtra("ProductID", arrayListRateChartList.get(position).getRateChartId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void getRateChartList() {
        if (Utils.isNetworkAvailable(getActivity())) {
          /*  final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            *//*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*//*
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/

            materialProgressBar.setVisibility(View.VISIBLE);
            Call<RateCharts> wsCallingRateCharts = mAPIInterface.getRateChartList();
            wsCallingRateCharts.enqueue(new Callback<RateCharts>() {
                @Override
                public void onResponse(Call<RateCharts> call, Response<RateCharts> response) {
                    if (materialProgressBar != null ) {
                        materialProgressBar.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (arrayListRateChartList != null) {
                            arrayListRateChartList.clear();
                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListRateChartList.addAll(response.body().getData());
                                    // if (arrayListRateChartList != null) {
                                /*downloadsAdapter = new DownloadsAdapter(getActivity(), arrayListRateChartList);
                                listView.setAdapter(downloadsAdapter);
                                downloadsAdapter.notifyDataSetChanged();*/
                                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListRateChartList);
                                    editor.putString("download_list", json);
                                    editor.apply();

                                    mLayoutManager = new LinearLayoutManager(getActivity());
                                    listView.setLayoutManager(mLayoutManager);
                                    listView.setItemAnimator(new DefaultItemAnimator());
                                    downloadsAdapter = new DownloadsAdapter(getActivity(), arrayListRateChartList);
                                    listView.setAdapter(downloadsAdapter);

                                    if (arrayListRateChartList.size() > 0) {
                                        llEmpty.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                    } else {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                        //   }
                                    }
                                }
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }
                        } else {
                            serviceUnavailable();
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<RateCharts> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    materialProgressBar.setVisibility(View.GONE);
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            //noInternetConnection();
            LoadDownloadData();
        }


    }


    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (downloadsAdapter != null) {
                downloadsAdapter.clearData();
                downloadsAdapter.notifyDataSetChanged();
            }
            getRateChartList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            //noInternetConnection();
            LoadDownloadData();
        }
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty);
        imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry);
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


    public void LoadDownloadData() {
        materialProgressBar.setVisibility( View.GONE );
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("download_list", null);
        Type type = new TypeToken<ArrayList<RateChartList>>() {
        }.getType();
        arrayListRateChartList = gson.fromJson(json, type);
        if (json != null) {
            if (downloadsAdapter == null)
                downloadsAdapter = new DownloadsAdapter(getActivity(), arrayListRateChartList);

            mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.setAdapter(downloadsAdapter);
            downloadsAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListRateChartList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListRateChartList);
        } else {
            noInternetConnection();
        }
    }


    public void hideSpotLight() {
        if (downloadsAdapter!=null) {
            downloadsAdapter.hideSpotLight();
        }
    }
}

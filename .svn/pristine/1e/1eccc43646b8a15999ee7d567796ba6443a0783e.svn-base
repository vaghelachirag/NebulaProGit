package com.nebulacompanies.ibo.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.legacy.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.DownlineRanksAdapter;
import com.nebulacompanies.ibo.adapter.DownlineStatusBaseAdapter;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.DownlineIBORankDetails;
import com.nebulacompanies.ibo.model.DownlineIBORankList;
import com.nebulacompanies.ibo.model.DownlineRankList;
import com.nebulacompanies.ibo.model.DownlineRanks;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

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
import static com.nebulacompanies.ibo.util.PopupEmptyView.DisplayEmptyDialog;

/**
 * Created by Palak Mehta on 22-Feb-18.
 */

public class DownlineRanksFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView listView;

    public static final String TAG = "Downline Ranks";
    Boolean isRefreshed = false;
    DownlineStatusBaseAdapter downlineStatusBaseAdapter;
    private ArrayList<DownlineRankList> arrayListDownlineRankLists = new ArrayList<>();

    //Error View
    private LinearLayout llEmpty, lynologin;
    MyButtonEcom btnLogin;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtSubError, txtRetry;
    Session session;

    String RankId;
    DownlineRanksAdapter downlineRanksAdapter;
    private ArrayList<DownlineIBORankList> arrayListDownlineRankDetailsList = new ArrayList<>();
    Dialog dialog;

    MaterialProgressBar mProgressDialog;
    Utils utils = new Utils();

    IntentFilter intentFilter;
    private BroadcastReceiver downlineRankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(UpdatesFragment.REFRESH_DOWNLINE)) {
                refreshContent();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downline_ranks, container, false);
        init(view);
        intentFilter = new IntentFilter();
        intentFilter.addAction(UpdatesFragment.REFRESH_DOWNLINE);

        getActivity().registerReceiver(downlineRankReceiver, intentFilter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(downlineRankReceiver);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;

        session = new Session(getActivity());
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            if (arrayListDownlineRankLists.size() > 0) {
                downlineStatusBaseAdapter = new DownlineStatusBaseAdapter(getActivity(), arrayListDownlineRankLists);
                listView.setAdapter(downlineStatusBaseAdapter);
                downlineStatusBaseAdapter.notifyDataSetChanged();
            } else {
                getDownlineRanks();
            }
        }
    }*/

    void init(View view) {
        initError(view);
        listView = (ListView) view.findViewById(R.id.listview_downline_ranks);
        lynologin = view.findViewById(R.id.ly_no_login);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v-> utils.openLoginDialog(getActivity(),utils.gotoMyBusiness) );

        mProgressDialog = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        ViewCompat.setNestedScrollingEnabled(listView, true);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    //  mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/
        listView.setOnItemClickListener(this);
        //refreshContent();
    }

    UpdatesFragment parentFrag;

    private void refreshContent() {
        parentFrag = ((UpdatesFragment) DownlineRanksFragment.this.getParentFragment());
        Log.d("Update==", "load full data :Downline");
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (downlineStatusBaseAdapter != null) {
                downlineStatusBaseAdapter.clearData();
                downlineStatusBaseAdapter.notifyDataSetChanged();
            }
            getDownlineRanks();
            //  mSwipeRefreshLayout.setRefreshing(true);
        } else {
            try {
                parentFrag.stopAnim(parentFrag.imageViewDownline, txtRetry);
            } catch (Exception e) {
                e.printStackTrace();

            }
            // mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    void getDownlineRanks() {
        if (session.getLogin()) {
            lynologin.setVisibility(View.GONE);
            if (Utils.isNetworkAvailable(getActivity())) {
                /* final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
                 *//*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*//*
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/

                Call<DownlineRanks> wsCallingDownlineRanks =  BusinessActivity.mAPIInterface.getDownlineRanks();
                wsCallingDownlineRanks.enqueue(new Callback<DownlineRanks>() {
                    @Override
                    public void onResponse(Call<DownlineRanks> call, Response<DownlineRanks> response) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageViewDownline, txtRetry);
                        } catch (Exception e) {
                        }
                  /*  if (mProgressDialog != null & mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                        //  mSwipeRefreshLayout.setRefreshing(false);

                        if (response.isSuccessful()) {

                            if (response.code() == 200) {

                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListDownlineRankLists.clear();
                                    arrayListDownlineRankLists.addAll(response.body().getData());
                           /* for (DownlineRankList downlineRankList:response.body().getData()){
                                RankId=downlineRankList.getRankId().toString();
                            }*/
                                    downlineStatusBaseAdapter = new DownlineStatusBaseAdapter(getActivity(), arrayListDownlineRankLists);
                                    listView.setAdapter(downlineStatusBaseAdapter);
                                    downlineStatusBaseAdapter.notifyDataSetChanged();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }

                                if (arrayListDownlineRankLists.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            }

                        } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(getActivity(), session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                            serviceUnavailable();
                        }
                    }

                    @Override
                    public void onFailure(Call<DownlineRanks> call, Throwable t) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageViewDownline, txtRetry);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        //  mProgressDialog.dismiss();
                        Log.e(TAG, "ERROR : " + t.getMessage());
                        serviceUnavailable();
                    }
                });

            } else {
                try {
                    parentFrag.stopAnim(parentFrag.imageViewDownline, txtRetry);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                noInternetConnection();
            }
        } else {
            listView.setVisibility(View.GONE);
            lynologin.setVisibility(View.VISIBLE);
        }
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty);
        imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle);
        txtSubError = (MyTextView) view.findViewById(R.id.txtErrorSubTitle);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(v -> refreshContent());
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        //   imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        //  imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_title);
        txtSubError.setText(R.string.error_content);
        //  imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String RankId = String.valueOf(arrayListDownlineRankLists.get(position).getRankId());
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        getDowlineRankList(RankId);

    }

    private void getDowlineRankList(String RankId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            /* final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
             *//*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*//*
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<DownlineIBORankDetails> wsCallingDownlineIBORank =  BusinessActivity.mAPIInterface.getDownlineIBORank(RankId);
            wsCallingDownlineIBORank.enqueue(new Callback<DownlineIBORankDetails>() {
                @Override
                public void onResponse(Call<DownlineIBORankDetails> call, Response<DownlineIBORankDetails> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    if (response.isSuccessful()) {
                        arrayListDownlineRankDetailsList.clear();
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            if (getActivity() != null) {
                                LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                                dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                View convertView = mLayoutInflater.inflate(R.layout.dialog_downline_list, null);
                                dialog.setContentView(convertView);
                                dialog.setCancelable(true);
                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                layoutParams.gravity = Gravity.CENTER;

                                dialog.getWindow().setAttributes(layoutParams);
                                dialog.getWindow().setBackgroundDrawable(null);
                                ListView downlineListView = (ListView) convertView.findViewById(R.id.listViewDownline);
                                arrayListDownlineRankDetailsList.addAll(response.body().getData());
                                downlineRanksAdapter = new DownlineRanksAdapter(getActivity(), arrayListDownlineRankDetailsList);
                                downlineListView.setAdapter(downlineRanksAdapter);
                                downlineRanksAdapter.notifyDataSetChanged();

                           /*  AmenitiesListViewAdapter adapter = new AmenitiesListViewAdapter(mContext);
                            amenitiesListView.setAdapter(adapter);*/
                                dialog.show();
                            }

                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            DisplayEmptyDialog(getActivity(), 0);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            DisplayEmptyDialog(getActivity(), -1);
                        }
                    }

                }

                @Override
                public void onFailure(Call<DownlineIBORankDetails> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    DisplayEmptyDialog(getActivity(), -1);
                }
            });
        } else {
            DisplayEmptyDialog(getActivity(), -1);
        }
    }
}

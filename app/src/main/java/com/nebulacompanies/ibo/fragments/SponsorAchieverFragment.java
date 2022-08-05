package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.DreamVolumeAdapter;
import com.nebulacompanies.ibo.adapter.SponsorAchieverAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.AchieverIncomeList;
import com.nebulacompanies.ibo.model.AchieverclosingIncomeIBODetails;
import com.nebulacompanies.ibo.model.HolidayAchieverDetails;
import com.nebulacompanies.ibo.model.HolidayAchieverList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.view.MyTextView;

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
 * Created by Sagar Virvani on 09-03-2018.
 */

public class SponsorAchieverFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;

    private APIInterface mAPIInterface;
    public static final String TAG = "DreamVolume";
    ConnectionDetector cd;

    DreamVolumeAdapter dreamVolumeAdapter;
    Boolean isRefreshed = false;

    MyTextView achieversalestitleMyTextView,incometitleMyTextView;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    HolidayAchieverList holidayAchieverList;
    private ArrayList<AchieverIncomeList> achievercIncomeDetails = new ArrayList<>();
    private ArrayList<AchieverclosingIncomeIBODetails> achieverclosingIncomeIBODetails = new ArrayList<>();
    private ArrayList<AchieverclosingIncomeIBODetails> achieverclosingDetails = new ArrayList<>();
    SponsorAchieverAdapter sponsorAchieverAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dream_volume, container, false);
        init(view);
        getHolidayAchieverIncomeList();
        return view;
    }

    void init(View view) {
        initError(view);
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.dream_volume_swipe_refresh_layout);
        listView = (ListView)view.findViewById(R.id.dv_listview_income);
        achieversalestitleMyTextView=(MyTextView)view.findViewById(R.id.date_title);
        incometitleMyTextView=(MyTextView)view.findViewById(R.id.dv_title);
        achieversalestitleMyTextView.setText(R.string.date);
        incometitleMyTextView.setText(R.string.achiever_income);

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

                mSwipeRefreshLayout.measure(View.MEASURED_SIZE_MASK,View.MEASURED_HEIGHT_STATE_SHIFT);
                mSwipeRefreshLayout.setNestedScrollingEnabled(true);
                ViewCompat.setNestedScrollingEnabled(mSwipeRefreshLayout, true);
                refreshContent();
            }
        });

    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            getHolidayAchieverIncomeList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }


    private void getHolidayAchieverIncomeList() {
        if (Utils.isNetworkAvailable(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Call<HolidayAchieverDetails> wsCallingHolidayAchiever = mAPIInterface.getHolidayAchieverDetails();
            wsCallingHolidayAchiever.enqueue(new Callback<HolidayAchieverDetails>() {
                @Override
                public void onResponse(Call<HolidayAchieverDetails> call, Response<HolidayAchieverDetails> response) {

                    if(mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    achievercIncomeDetails.clear();
                    achieverclosingIncomeIBODetails.clear();

                    if (response.isSuccessful()){
                        if (response.code()==200)
                        {

                            if(response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS)
                            {
                                holidayAchieverList = response.body().getData();
                                achievercIncomeDetails.addAll(holidayAchieverList.getAchieverIncomeDetails());
                                achieverclosingIncomeIBODetails.addAll(holidayAchieverList.getAchieverclosingIncomeIBODetails());

                                if(achievercIncomeDetails.size() > 0) {
                                    /*for (int i = 0; i < achievercIncomeDetails.size(); i++) {
                                        int a = achievercIncomeDetails.get(i).getHolidayAchieveIBOMasterID();
                                        int income = 0;

                                        for (int j = 0; j < achieverclosingIncomeIBODetails.size(); j++) {
                                            int b = achieverclosingIncomeIBODetails.get(j).getHolidayAchieveIBOMasterID();
                                            String type = achieverclosingIncomeIBODetails.get(j).getCustomerType();

                                            if (a == b & type.equals("IBO")) {
                                                income += achieverclosingIncomeIBODetails.get(j).getTotalAmount();
                                                achieverclosingDetails.add(achieverclosingIncomeIBODetails.get(j));
                                            }
                                            else{
                                                achievercIncomeDetails.clear();
                                            }
                                        }

                                        if(achievercIncomeDetails.size() > 0) {
                                            achievercIncomeDetails.get(i).setAmount(income);
                                        }
                                    }*/

                                    //if(achievercIncomeDetails.size() > 0) {
                                        sponsorAchieverAdapter = new SponsorAchieverAdapter(getActivity(), achievercIncomeDetails, achieverclosingDetails, achieverclosingIncomeIBODetails);
                                        listView.setAdapter(sponsorAchieverAdapter);
                                        sponsorAchieverAdapter.notifyDataSetChanged();
                                    /*}
                                    else{
                                        noRecordsFound();
                                    }*/
                                } else {
                                    noRecordsFound();
                                }
                            }
                            else if(response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS){
                                noRecordsFound();
                            }
                            else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }
                        }
                        else{
                            serviceUnavailable();
                        }

                        ViewCompat.setNestedScrollingEnabled(listView, true);
                        ViewCompat.setNestedScrollingEnabled(llEmpty, true);
                    }
                    else {
                       /* if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(getActivity(), session);
                            serviceUnavailable();
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                       serviceUnavailable();
                    }
                }


                @Override
                public void onFailure(Call<HolidayAchieverDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    serviceUnavailable();
                }
            });
        }
        else{
            noInternetConnection();
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

}

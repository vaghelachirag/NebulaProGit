package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.SponsorSalesAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.AchieverIBOList;
import com.nebulacompanies.ibo.model.AchieverIncomeList;
import com.nebulacompanies.ibo.model.HolidayAchieverDetails;
import com.nebulacompanies.ibo.model.HolidayAchieverList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
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
 * Created by Sagar Virvani on 08-03-2018.
 */

public class SponsorSalesFragment extends Fragment {


    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    TextView rankTextView, dateTextView, totalAmtTextView, moreInfoTextView;

    Boolean isRefreshed = false;
    SponsorSalesAdapter sponsorSalesAdapter;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    ArrayList<String> sr = new ArrayList<>();

    private ArrayList<AchieverIBOList> arrayListHolidayAchieverList = new ArrayList<>();

    private APIInterface mAPIInterface;
    public static final String TAG = "Sponsor Sales Holiday Achiever";
    ConnectionDetector cd;

    HolidayAchieverList holidayAchieverList;

    private ArrayList<AchieverIncomeList> arrayListHolidayAchieverIncomeList = new ArrayList<>();
    Session session;
    LinearLayout lnStarPoolHoliday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sponsor_sales, container, false);
        // View view = inflater.inflate(R.layout.star_pool_income_holiday_sales_acheiver, container, false);

        init(view);
        getHolidayAchieverIncomeList();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;
        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


      *//*  if (arrayListHolidayAchieverList.size() > 0) {
            sponsorSalesAdapter = new SponsorSalesAdapter(getActivity(), arrayListHolidayAchieverList);
            listView.setAdapter(sponsorSalesAdapter);
            sponsorSalesAdapter.notifyDataSetChanged();
        }
        else {*//*
            getHolidayAchieverIncomeList();
        //}
    }*/
    void init(View view) {
        cd = new ConnectionDetector(getActivity());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.starpool_swipe_refresh_layout);
        listView = (ListView) view.findViewById(R.id.listview_starpool_income);
        rankTextView = (TextView) view.findViewById(R.id.rank_starpool);
        dateTextView = (TextView) view.findViewById(R.id.date_starclub);
        totalAmtTextView = (TextView) view.findViewById(R.id.gross_amt_starpool);
        moreInfoTextView = (TextView) view.findViewById(R.id.more_info_starpool);
        lnStarPoolHoliday = (LinearLayout) view.findViewById(R.id.ln_star_pool);
        rankTextView.setText(R.string.ibo_id);
        totalAmtTextView.setText(R.string.ibo_name);
        ViewCompat.setNestedScrollingEnabled(listView, true);
        //sr();
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

                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    arrayListHolidayAchieverList.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            lnStarPoolHoliday.setVisibility(View.VISIBLE);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                holidayAchieverList = response.body().getData();
                                if (holidayAchieverList.getAchieverIncomeDetails() != null) {
                                    arrayListHolidayAchieverIncomeList.addAll(holidayAchieverList.getAchieverIncomeDetails());
                                }

                                if (holidayAchieverList.getAchieverIBOList() != null) {
                                    arrayListHolidayAchieverList.addAll(holidayAchieverList.getAchieverIBOList());

                                }
                                if(arrayListHolidayAchieverList.size() > 0) {
                                    sponsorSalesAdapter = new SponsorSalesAdapter(getActivity(), arrayListHolidayAchieverList, arrayListHolidayAchieverIncomeList);
                                    listView.setAdapter(sponsorSalesAdapter);
                                    sponsorSalesAdapter.notifyDataSetChanged();
                                    lnStarPoolHoliday.setVisibility(View.VISIBLE);
                                } else{
                                    noRecordsFound();
                                }
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }
                        } else {
                            serviceUnavailable();
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
                public void onFailure(Call<HolidayAchieverDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    serviceUnavailable();
                }
            });
        } else {
            noInternetConnection();
        }

    }


    /*private void sr() {
        sr.clear();
        sr.add("");
        sr.add("");
        sr.add("");
        sr.add("");
        sponsorSalesAdapter= new SponsorSalesAdapter(getActivity(), sr);
        listView.setAdapter(sponsorSalesAdapter);
        sponsorSalesAdapter.notifyDataSetChanged();
    }*/


    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (arrayListHolidayAchieverList != null) {
                arrayListHolidayAchieverList.clear();
                if (sponsorSalesAdapter != null) {
                    sponsorSalesAdapter.notifyDataSetChanged();
                }
            }
            //sr();
            getHolidayAchieverIncomeList();
            mSwipeRefreshLayout.setRefreshing(true);
            showRecords();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty);
        imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry);
        int minHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        imgError.getLayoutParams().height = minHeight;
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
        setMargins(txtRetry, 5, 5, 5, 5);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        lnStarPoolHoliday.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        lnStarPoolHoliday.setVisibility(View.VISIBLE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        lnStarPoolHoliday.setVisibility(View.VISIBLE);
    }

    void showRecords() {
        //txtErrorTitle.setText(R.string.error_msg_network);
        //imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }
}

package com.nebulacompanies.ibo.fragments;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.DataAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.HolidayList;
import com.nebulacompanies.ibo.model.HolidayPackageDetail;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class HolidayPackagesFragment extends Fragment {

    public static final String TAG = "HolidayPackages";
    ConnectionDetector cd;
    APIInterface mAPIInterface;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<HolidayList> holidayList = new ArrayList<>();
    Boolean isRefreshed = false;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    private int lastPosition = -1;
    //RecyclerView holidayRecyclerView;
    Session session;
    //private HolidayListAdapter holidayListAdapter;
    private int type;

    //android side put static data
    private final int[] image_urls = {
            R.drawable.my_sales_new,
            R.drawable.my_package
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_holidays_list, container, false);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //init(view);
        //getHolidayPackageList();
        initViews(view);

        return view;
    }
    private void initViews(View view){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_holiday_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<HolidayPackageDetail> holidayPackageDetails = prepareData();
        DataAdapter adapter = new DataAdapter(getActivity(),holidayPackageDetails);
        recyclerView.setAdapter(adapter);

    }
    private ArrayList<HolidayPackageDetail> prepareData(){

        ArrayList<HolidayPackageDetail> holidayPackageDetails = new ArrayList<>();
        for(int i=0;i<image_urls.length;i++){
            HolidayPackageDetail holidayPackageDetail = new HolidayPackageDetail();
            //androidVersion.setAndroid_version_name(android_version_names[i]);
            holidayPackageDetail.set_image_url(image_urls[i]);
            holidayPackageDetails.add(holidayPackageDetail);
        }
        return holidayPackageDetails;
    }

    private void init(View view) {
        cd = new ConnectionDetector(getActivity());
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        session = new Session(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.holiday_swipe_refresh_layout);

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

            //getHolidayPackageList();
            mSwipeRefreshLayout.setRefreshing(true);
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
        //holidayRecyclerView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        //holidayRecyclerView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        //holidayRecyclerView.setVisibility(View.GONE);

    }
}

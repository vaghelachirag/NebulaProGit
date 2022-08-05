package com.nebulacompanies.ibo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.CommunityGrowthIncomeAdapter;
import com.nebulacompanies.ibo.adapter.PVTableAdapter;
import com.nebulacompanies.ibo.adapter.WeeklyMatchingAdapter;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.CommunityGrowthIncome;
import com.nebulacompanies.ibo.model.CommunityGrowthModel;
import com.nebulacompanies.ibo.model.PVTable;
import com.nebulacompanies.ibo.model.PVTableModel;
import com.nebulacompanies.ibo.model.WeeklyMatching;
import com.nebulacompanies.ibo.model.WeeklyMatchingModel;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.SimpleDividerItemDecoration;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


public class MyPVFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Boolean isRefreshed = false;
    private RecyclerView recyclerViewpv, recyclerViewweekly, recyclerViewcommunity;

    ArrayList<PVTable> pvTable = new ArrayList<>();
    Session session;

    private PVTableAdapter pvTableAdapter;

    ArrayList<WeeklyMatching> weeklyMatching = new ArrayList<>();
    private WeeklyMatchingAdapter weeklyMatchingAdapter;

    ArrayList<CommunityGrowthIncome> communityGrowthIncome = new ArrayList<>();
    private CommunityGrowthIncomeAdapter communityGrowthIncomeAdapter;
    LinearLayout linearLayout;
    MaterialProgressBar mProgressDialog;
    HorizontalScrollView horizontalScrollViewpv, horizontalScrollViewweekly, ScrollViewcommunity;
    View view_pv, view_weekly, view_community;
    private LinearLayout llEmpty, llEmptyWeek, llEmptyCommunity,lynologin,lynologinweek,lynologincom;
    private ImageView  imgRefreshFamilyPv, imgRefreshWeeklyMatching, imgRefreshCommunityGrowth;
    private MyTextView txtErrorTitle,txtSubError, txtRetry;
    private MyTextView txtErrorTitleweekly,txtSubErrorweekly, txtRetryweekly;
    private MyTextView txtErrorTitleincome,txtSubErrorincome, txtRetryincome;
    LinearLayout lnDashboardIncome, lnDashboardGrowth;
    SharedPreferences prefPV;
    int totalRecords = 0;
    SharedPreferences.Editor edtPV;
    MyButtonEcom btnLogin,btnloginWeek,btnlogincom;
    Utils utils = new Utils();


    private BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(BusinessActivity.VISIBLEPv)) {
               // Toast.makeText(getActivity(), ": Load  PV " , Toast.LENGTH_SHORT).show();
                //getData();
                refreshContent();
            }
        }
    };
    IntentFilter intentFilter;
    public MyPVFragment() {
    }

    public static MyPVFragment newInstance(String param1, String param2) {
        MyPVFragment fragment = new MyPVFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        intentFilter = new IntentFilter();
        intentFilter.addAction(BusinessActivity.VISIBLEPv);
        getActivity().registerReceiver(rankReceiver, intentFilter);
        return inflater.inflate(R.layout.pv_fragment, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        lynologin =view.findViewById(R.id.ly_no_login);
        btnLogin =view.findViewById(R.id.btn_login);
        lynologinweek = view.findViewById(R.id.ly_no_login_week);
        btnloginWeek = view.findViewById(R.id.btn_login_week);
        lynologincom  = view.findViewById(R.id.ly_no_login_community);
        btnlogincom = view.findViewById(R.id.btn_login_community);

        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty_pv);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetrypv);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitlepv);
        txtSubError = (MyTextView) view.findViewById(R.id.txtErrorSubTitlepv);

        llEmptyWeek = (LinearLayout) view.findViewById(R.id.llEmpty_week);
        txtRetryweekly = (MyTextView) view.findViewById(R.id.txtRetryweek);
        txtErrorTitleweekly = (MyTextView) view.findViewById(R.id.txtErrorTitleweek);
        txtSubErrorweekly = (MyTextView) view.findViewById(R.id.txtErrorSubTitleweek);

        llEmptyCommunity = (LinearLayout) view.findViewById(R.id.llEmpty_income);
        txtRetryincome = (MyTextView) view.findViewById(R.id.txtRetryincome);
        txtErrorTitleincome = (MyTextView) view.findViewById(R.id.txtErrorTitleincome);
        txtSubErrorincome = (MyTextView) view.findViewById(R.id.txtErrorSubTitleincome);

        imgRefreshFamilyPv = (ImageView) view.findViewById(R.id.refreshFamilyPv);
        imgRefreshWeeklyMatching = (ImageView) view.findViewById(R.id.refreshWeeklyMatching);
        imgRefreshCommunityGrowth = (ImageView) view.findViewById(R.id.refreshCommunitygrowth);

        view_pv = (View) view.findViewById(R.id.pv_view);
        horizontalScrollViewpv = (HorizontalScrollView) view.findViewById(R.id.scroll_pv);

        view_weekly = (View) view.findViewById(R.id.view_weekly);
        horizontalScrollViewweekly = (HorizontalScrollView) view.findViewById(R.id.scroll_weekly);

        view_community = (View) view.findViewById(R.id.view_community);
        ScrollViewcommunity = (HorizontalScrollView) view.findViewById(R.id.scroll_community);

        prefPV = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        edtPV = prefPV.edit();

        linearLayout = (LinearLayout) view.findViewById(R.id.ln_rank);
        lnDashboardIncome = (LinearLayout) view.findViewById(R.id.ln_dashboard_weekly);
        lnDashboardGrowth = (LinearLayout) view.findViewById(R.id.ln_dashboard_growth);

        mProgressDialog = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        recyclerViewpv = (RecyclerView) view.findViewById(R.id.recyclerview_pv);
        RecyclerView.LayoutManager mLayoutManagerDeal = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewpv.setLayoutManager(mLayoutManagerDeal);
        recyclerViewpv.setItemAnimator(new DefaultItemAnimator());
        recyclerViewpv.setHasFixedSize(true);
        recyclerViewpv.setNestedScrollingEnabled(false);
        recyclerViewpv.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        pvTableAdapter = new PVTableAdapter(getActivity(), pvTable);
        recyclerViewpv.setAdapter(pvTableAdapter);

        recyclerViewweekly = (RecyclerView) view.findViewById(R.id.recyclerview_weekly_matching);
        RecyclerView.LayoutManager mLayoutManagerDeal1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewweekly.setLayoutManager(mLayoutManagerDeal1);
        recyclerViewweekly.setItemAnimator(new DefaultItemAnimator());
        recyclerViewweekly.setHasFixedSize(true);
        recyclerViewweekly.setNestedScrollingEnabled(false);
        recyclerViewweekly.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        weeklyMatchingAdapter = new WeeklyMatchingAdapter(getActivity(), weeklyMatching);
        recyclerViewweekly.setAdapter(weeklyMatchingAdapter);

        recyclerViewcommunity = (RecyclerView) view.findViewById(R.id.recyclerview_community_income);
        RecyclerView.LayoutManager mLayoutManagerDeal2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewcommunity.setLayoutManager(mLayoutManagerDeal2);
        recyclerViewcommunity.setItemAnimator(new DefaultItemAnimator());
        recyclerViewcommunity.setHasFixedSize(true);
        recyclerViewcommunity.setNestedScrollingEnabled(false);
        recyclerViewcommunity.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        communityGrowthIncomeAdapter = new CommunityGrowthIncomeAdapter(getActivity(), communityGrowthIncome);
        recyclerViewcommunity.setAdapter(communityGrowthIncomeAdapter);

        btnLogin.setOnClickListener(v-> utils.openLoginDialog(getActivity(),utils.gotoMyBusiness));
        btnloginWeek.setOnClickListener(v -> utils.openLoginDialog(getActivity(),utils.gotoMyBusiness));
        btnlogincom.setOnClickListener(v -> utils.openLoginDialog(getActivity(),utils.gotoMyBusiness));


        imgRefreshFamilyPv.setOnClickListener(v -> {
            if(imgRefreshFamilyPv.getAnimation()==null &&
                    imgRefreshWeeklyMatching.getAnimation()==null &&
                    imgRefreshCommunityGrowth.getAnimation()==null) {
                startRotateAnim(imgRefreshFamilyPv,txtRetry);
                getPvTableData(false);
            }
        });

        imgRefreshWeeklyMatching.setOnClickListener(v -> {
            if(imgRefreshFamilyPv.getAnimation()==null &&
                    imgRefreshWeeklyMatching.getAnimation()==null &&
                    imgRefreshCommunityGrowth.getAnimation()==null) {
                startRotateAnim(imgRefreshWeeklyMatching,txtRetryweekly);
                getWeeklyMatchingData(false);
            }
        });

        imgRefreshCommunityGrowth.setOnClickListener(v -> {
            if(imgRefreshFamilyPv.getAnimation()==null &&
                    imgRefreshWeeklyMatching.getAnimation()==null &&
                    imgRefreshCommunityGrowth.getAnimation()==null) {
                startRotateAnim(imgRefreshCommunityGrowth,txtRetryincome);
                getCommunitygrowthData(false);
            }
        });

        txtRetry.setOnClickListener(v -> {
            startRotateAnim(imgRefreshFamilyPv,txtRetry);
            getPvTableData(false);
        });
        txtRetryweekly.setOnClickListener(v -> {
            startRotateAnim(imgRefreshWeeklyMatching,txtRetryweekly);
            getWeeklyMatchingData(false);
        });
        txtRetryincome.setOnClickListener(v -> {
            startRotateAnim(imgRefreshCommunityGrowth,txtRetryincome);
            getCommunitygrowthData(false);
        });

    }

  /*  private void LoadData() {
        String familywise = prefPV.getString(PrefUtils.prefFamily, null);
        String weekly = prefPV.getString(PrefUtils.prefWeekly, null);
        String community = prefPV.getString(PrefUtils.prefCommunity, null);
        if (!TextUtils.isEmpty(familywise))
        setPvTable(familywise);
        if (!TextUtils.isEmpty(weekly))
        setWeekly(weekly);
        if (!TextUtils.isEmpty(community))
        setCommunity(community);
    }

    private void getData() {
        String familywise = prefPV.getString(PrefUtils.prefFamily, null);
        String weekly = prefPV.getString(PrefUtils.prefWeekly, null);
        String community = prefPV.getString(PrefUtils.prefCommunity, null);
        if (TextUtils.isEmpty(familywise) || TextUtils.isEmpty(weekly) || TextUtils.isEmpty(community)) {
            refreshContent();
        }else{
            setPvTable(familywise);
            setWeekly(weekly);
            setCommunity(community);
        }

    }*/

    private void startRotateAnim(ImageView imgvw,MyTextView txtvw) {
        RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(400);
        imgvw.setEnabled(false);
        txtvw.setEnabled(false);
        // Start animating the image
        imgvw.startAnimation(anim);

    }

    private void stopAnim(ImageView imgvw,MyTextView txtvw) {
        try {
            imgvw.setAnimation(null);
            imgvw.setEnabled(true);
            txtvw.setEnabled(true);
        } catch (Exception ignored) {
        }
    }

/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisible()){
            if(isVisibleToUser){

                getPvTableData();
                Log.e("MyTag","My Fragment is visible");
            }else{
                Log.e("MyTag","My Fragment is not visible");
            }
        }
    }
*/

    private void refreshContent() {
       // if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
           /* lnDashboardIncome.setVisibility(View.GONE);
            lnDashboardGrowth.setVisibility(View.GONE);*/
            mProgressDialog.setVisibility(View.GONE);
            getPvTableData(true);
           /* getWeeklyMatchingData();
            getCommunitygrowthData();*/

            //  mSwipeRefreshLayout.setRefreshing(false);

      /*  } else {
            //  mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnectionpv();

        }*/
    }

    /*  public void onButtonPressed(Uri uri) {
          if (mListener != null) {
              mListener.onFragmentInteraction(uri);
          }
      }
  */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    /* private void getPvTableData() {

         Call<PVTableModel> wsCallingBanersList = mAPIInterface.getPvTable(session.getIboKeyId());
         Log.d( "OnresponseStart", "OnresponseStart: " );
         wsCallingBanersList.enqueue( new Callback<PVTableModel>() {
             @Override
             public void onResponse(Call<PVTableModel> call, Response<PVTableModel> response) {
                 Log.d( "OnresponseIF", "OnresponseIF: " + response );
                 if (response.isSuccessful()) {
                     pvTable.clear();
                     if (response.code() == 200) {
                         Log.d( "Onresponse", "Onresponse: " + response );
                         if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                         } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                             pvTable.addAll(response.body().getData());
                             RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                             recyclerViewpv.setLayoutManager(mLayoutManager);
                             recyclerViewpv.setItemAnimator(new DefaultItemAnimator());
                             pvTableAdapter = new PVTableAdapter(getActivity(), pvTable);
                             recyclerViewpv.setAdapter(pvTableAdapter);



                         } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                 || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                         }
                     }

                 } else {
                     Log.d( "onResponse: ", "onResponse: " + response );
                 }
             }

             @Override
             public void onFailure(Call<PVTableModel> call, Throwable t) {
                 Log.d( "OnresponseFail", "OnresponseFail: " + t );
             }
         } );
     }
 */

    @Override
    public void onDestroy() {
        //Toast.makeText(getActivity(), ":Destroy volume" , Toast.LENGTH_SHORT).show();

        super.onDestroy();
        getActivity().unregisterReceiver(rankReceiver);
    }

    private void getPvTableData(boolean callNext) {
        if(session.getLogin()) {
            lynologin.setVisibility(View.GONE);
            lynologinweek.setVisibility(View.GONE);
            lynologincom.setVisibility(View.GONE);
            recyclerViewpv.setVisibility(View.VISIBLE);
            Log.d("Call::", "getPvTableData()");
            if (Utils.isNetworkAvailable(getActivity())) {
                if (callNext)
                    mProgressDialog.setVisibility(View.VISIBLE);

                Call<PVTableModel> wsCallingRankAndVolumes = BusinessActivity.mAPIInterface.getPvTable(session.getIboKeyId());
                wsCallingRankAndVolumes.enqueue(new Callback<PVTableModel>() {

                    @Override
                    public void onResponse(Call<PVTableModel> call, Response<PVTableModel> response) {
                        try {
                            stopAnim(imgRefreshFamilyPv, txtRetry);
                        } catch (Exception e) {
                        }
                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }

                        if (response.isSuccessful()) {

                            if (mProgressDialog != null) {
                                mProgressDialog.setVisibility(View.GONE);
                            }
                            pvTable.clear();
                            linearLayout.setVisibility(View.VISIBLE);
                            if (response.code() == 200) {

                                if (mProgressDialog != null) {
                                    mProgressDialog.setVisibility(View.GONE);
                                }

                                if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {

                                    if (mProgressDialog != null) {
                                        mProgressDialog.setVisibility(View.GONE);
                                    }
                                    Type collectionType = new TypeToken<List<PVTable>>() {
                                    }.getType();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(response.body().getData(), collectionType);

                                    edtPV.putString(PrefUtils.prefFamily, json).apply();

                                    setPvTable(json);
                                    if (callNext)
                                        getWeeklyMatchingData(callNext);


                                } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    if (totalRecords == 0) {
                                        // llNoRecords.setVisibility(View.GONE);
                                        noRecordsFound();
                                    }
                                } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                    //AppUtils.displayServiceUnavailableMessage(getActivity());
                                }

                                if (pvTable.size() > 0) {

                                    llEmpty.setVisibility(View.GONE);
                                    // llNoRecords.setVisibility(View.GONE);
                                    recyclerViewpv.setVisibility(View.VISIBLE);
                                    view_pv.setVisibility(View.VISIBLE);
                                    horizontalScrollViewpv.setVisibility(View.VISIBLE);
                                } else {
                                    noRecordsFound();
                              /*  llEmpty.setVisibility(View.VISIBLE);
                                recyclerViewpv.setVisibility(View.GONE);
                                view_pv.setVisibility(View.GONE);
                                horizontalScrollViewpv.setVisibility(View.GONE);*/
                                }

                            }
                        } else {
                            noRecordsFound();
                            //AppUtils.displayServerErrorMessage(getActivity());
                        }

                    }

                    @Override
                    public void onFailure(Call<PVTableModel> call, Throwable t) {
                        try {
                            stopAnim(imgRefreshFamilyPv, txtRetry);
                        } catch (Exception e) {
                        }
                        mProgressDialog.setVisibility(View.GONE);
                        noRecordsFound();
                        //   AppUtils.displayServiceUnavailableMessage(mContext);
                    }
                });

            } else {
                try {
                    stopAnim(imgRefreshFamilyPv, txtRetry);
                } catch (Exception e) {
                }
                noInternetConnectionpv();
                // llNoRecords.setVisibility(View.VISIBLE);
            /*lnDashboardIncome.setVisibility(View.GONE);
            lnDashboardGrowth.setVisibility(View.GONE);*/
            }
        }else
        {
            noLogin();
        }
    }

    private void noLogin() {
        mProgressDialog.setVisibility(View.GONE);
        stopAnim(imgRefreshCommunityGrowth,txtRetryincome);
        stopAnim(imgRefreshWeeklyMatching,txtRetryweekly);
        stopAnim(imgRefreshFamilyPv, txtRetry);
        lynologin.setVisibility(View.VISIBLE);
        lynologinweek.setVisibility(View.VISIBLE);
        lynologincom.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
        Log.d("norecord==","pv");


    }
  /*  private void openLogin() {
        Intent login = new Intent(getActivity(), LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        getActivity().finish();
    }*/
    private void setPvTable(String json) {
        Type collectionType = new TypeToken<List<PVTable>>() {
        }.getType();
        Gson gson = new Gson();
        List<PVTable> data = gson.fromJson(json, collectionType);
        pvTable.clear();
        pvTable.addAll(data);
        pvTableAdapter.notifyDataSetChanged();
    }


    private void getWeeklyMatchingData(boolean callNext) {
        Log.d("Call::", "getWeeklyMatchingData()");
        if(session.getLogin()) {
            lynologinweek.setVisibility(View.GONE);
            recyclerViewweekly.setVisibility(View.VISIBLE);
            if (Utils.isNetworkAvailable(getActivity())) {
                if (callNext)
                    mProgressDialog.setVisibility(View.VISIBLE);

                Call<WeeklyMatchingModel> wsCallingBanersList =  BusinessActivity.mAPIInterface.getWeeklyMatching(session.getIboKeyId());
                Log.d("OnresponseStart", "OnresponseStart: ");
                wsCallingBanersList.enqueue(new Callback<WeeklyMatchingModel>() {

                    @Override
                    public void onResponse(Call<WeeklyMatchingModel> call, Response<WeeklyMatchingModel> response) {
                        try {
                            stopAnim(imgRefreshWeeklyMatching, txtRetryweekly);
                        } catch (Exception e) {
                        }
                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }

                        if (response.isSuccessful()) {

                            if (mProgressDialog != null) {
                                mProgressDialog.setVisibility(View.GONE);
                            }
                            weeklyMatching.clear();
                            //linearLayout.setVisibility(View.VISIBLE);
                            if (response.code() == 200) {

                                if (mProgressDialog != null) {
                                    mProgressDialog.setVisibility(View.GONE);
                                }

                                if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {

                                    if (mProgressDialog != null) {
                                        mProgressDialog.setVisibility(View.GONE);
                                    }

                                    Type collectionType = new TypeToken<List<WeeklyMatching>>() {
                                    }.getType();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(response.body().getData(), collectionType);

                                    edtPV.putString(PrefUtils.prefWeekly, json).apply();

                                    setWeekly(json);


                                    if (callNext)
                                        getCommunitygrowthData(callNext);
                                    //init();


                                } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                                    noRecordsFoundWeek();

                                } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                    //AppUtils.displayServiceUnavailableMessage(getActivity());
                                }

                                if (weeklyMatching.size() == 0) {
                                    noRecordsFoundWeek();
                              /*  llEmptyWeek.setVisibility(View.VISIBLE);
                                recyclerViewweekly.setVisibility(View.GONE);
                                view_weekly.setVisibility(View.GONE);
                                horizontalScrollViewweekly.setVisibility(View.GONE);*/


                                } else {
                                    llEmptyWeek.setVisibility(View.GONE);
                                    recyclerViewweekly.setVisibility(View.VISIBLE);
                                    view_weekly.setVisibility(View.VISIBLE);
                                    horizontalScrollViewweekly.setVisibility(View.VISIBLE);
                                }

                            }
                        } else {

                            //AppUtils.displayServerErrorMessage(getActivity());
                        }

                    }

                    @Override
                    public void onFailure(Call<WeeklyMatchingModel> call, Throwable t) {
                        try {
                            stopAnim(imgRefreshWeeklyMatching, txtRetryweekly);
                        } catch (Exception e) {
                        }
                        mProgressDialog.setVisibility(View.GONE);
                        noRecordsFoundWeek();
                        //   AppUtils.displayServiceUnavailableMessage(mContext);
                    }
                });

            } else {
                noRecordsFoundWeek();
                try {
                    stopAnim(imgRefreshWeeklyMatching, txtRetryweekly);
                } catch (Exception e) {
                }
                noInternetConnectionweekly();
            }
        }else
            noLogin();
    }

    private void setWeekly(String json) {
        Type collectionType = new TypeToken<List<WeeklyMatching>>() {
        }.getType();
        Gson gson = new Gson();
        List<WeeklyMatching> data = gson.fromJson(json, collectionType);

        weeklyMatching.clear();
        weeklyMatching.addAll(data);

        weeklyMatchingAdapter.notifyDataSetChanged();
        lnDashboardIncome.setVisibility(View.VISIBLE);
    }

   /* private void getWeeklyMatchingData() {

        Call<WeeklyMatchingModel> wsCallingBanersList = mAPIInterface.getWeeklyMatching(session.getIboKeyId());
        Log.d( "OnresponseStart", "OnresponseStart: " );
        wsCallingBanersList.enqueue( new Callback<WeeklyMatchingModel>() {
            @Override
            public void onResponse(Call<WeeklyMatchingModel> call, Response<WeeklyMatchingModel> response) {
                Log.d( "OnresponseIF", "OnresponseIF: " + response );
                if (response.isSuccessful()) {
                    pvTable.clear();
                    if (response.code() == 200) {
                        Log.d( "Onresponse", "Onresponse: " + response );
                        if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            weeklyMatching.addAll(response.body().getData());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerViewweekly.setLayoutManager(mLayoutManager);
                            recyclerViewweekly.setItemAnimator(new DefaultItemAnimator());
                            weeklyMatchingAdapter = new WeeklyMatchingAdapter(getActivity(), weeklyMatching);
                            recyclerViewweekly.setAdapter(weeklyMatchingAdapter);



                        } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                        }
                    }

                } else {
                    Log.d( "onResponse: ", "onResponse: " + response );
                }
            }

            @Override
            public void onFailure(Call<WeeklyMatchingModel> call, Throwable t) {
                Log.d( "OnresponseFail", "OnresponseFail: " + t );
            }
        } );
    }*/

    private void getCommunitygrowthData(boolean callNext) {
        Log.d("Call::", "getCommunitygrowthData()");
        if(session.getLogin()) {
            lynologincom.setVisibility(View.GONE);
            recyclerViewcommunity.setVisibility(View.VISIBLE);
            if (Utils.isNetworkAvailable(getActivity())) {
                if (callNext)
                    mProgressDialog.setVisibility(View.VISIBLE);


                Call<CommunityGrowthModel> wsCallingBanersList =  BusinessActivity.mAPIInterface.getCommunityGrowth(session.getIboKeyId());
                Log.d("OnresponseStart", "OnresponseStart: ");
                wsCallingBanersList.enqueue(new Callback<CommunityGrowthModel>() {

                    @Override
                    public void onResponse(Call<CommunityGrowthModel> call, Response<CommunityGrowthModel> response) {
                        try {
                            stopAnim(imgRefreshCommunityGrowth, txtRetryincome);
                        } catch (Exception e) {
                        }
                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }

                        if (response.isSuccessful()) {

                            if (mProgressDialog != null) {
                                mProgressDialog.setVisibility(View.GONE);
                            }
                            communityGrowthIncome.clear();
                            linearLayout.setVisibility(View.VISIBLE);
                            if (response.code() == 200) {

                                if (mProgressDialog != null) {
                                    mProgressDialog.setVisibility(View.GONE);
                                }

                                if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {

                                    if (mProgressDialog != null) {
                                        mProgressDialog.setVisibility(View.GONE);
                                    }

                                    Type collectionType = new TypeToken<List<CommunityGrowthIncome>>() {
                                    }.getType();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(response.body().getData(), collectionType);

                                    edtPV.putString(PrefUtils.prefCommunity, json).apply();

                                    setCommunity(json);


                                } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    if (totalRecords == 0) {
                                        noRecordsFoundCommunity();
                                    }
                                } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                    //AppUtils.displayServiceUnavailableMessage(getActivity());
                                }

                                if (communityGrowthIncome.size() > 0) {
                                    llEmptyCommunity.setVisibility(View.GONE);
                                    recyclerViewcommunity.setVisibility(View.VISIBLE);
                                    view_community.setVisibility(View.VISIBLE);
                                    ScrollViewcommunity.setVisibility(View.VISIBLE);
                                } else {
                                    noRecordsFoundCommunity();
                                }

                            }
                        } else {
                            noRecordsFoundCommunity();
                            //AppUtils.displayServerErrorMessage(getActivity());
                        }

                    }

                    @Override
                    public void onFailure(Call<CommunityGrowthModel> call, Throwable t) {
                        try {
                            stopAnim(imgRefreshCommunityGrowth, txtRetryincome);
                        } catch (Exception e) {
                        }
                        mProgressDialog.setVisibility(View.GONE);
                        noRecordsFoundCommunity();
                        //   AppUtils.displayServiceUnavailableMessage(mContext);
                    }
                });

            } else {
                try {
                    stopAnim(imgRefreshCommunityGrowth, txtRetryincome);
                } catch (Exception e) {
                }
                noInternetConnectionincome();

            }
        }else
            noLogin();

    }

    private void setCommunity(String json) {

        Type collectionType = new TypeToken<List<CommunityGrowthIncome>>() {
        }.getType();
        Gson gson = new Gson();
        List<CommunityGrowthIncome> data = gson.fromJson(json, collectionType);


        communityGrowthIncome.clear();
        //arrayListUpdates.addAll(response.body().getData());
        communityGrowthIncome.addAll(data);
        communityGrowthIncomeAdapter.notifyDataSetChanged();
        //android.util.Log.e( "getCommunitygrowthData", "getCommunitygrowthData: " + new Gson().toJson( response.body() ) );
        lnDashboardGrowth.setVisibility(View.VISIBLE);
        //init();
    }

  /*  private void getCommunitygrowthData() {

        Call<CommunityGrowthModel> wsCallingBanersList = mAPIInterface.getCommunityGrowth(session.getIboKeyId());
        Log.d( "OnresponseStart", "OnresponseStart: " );
        wsCallingBanersList.enqueue( new Callback<CommunityGrowthModel>() {
            @Override
            public void onResponse(Call<CommunityGrowthModel> call, Response<CommunityGrowthModel> response) {
                Log.d( "OnresponseIF", "OnresponseIF: " + response );
                if (response.isSuccessful()) {
                    pvTable.clear();
                    if (response.code() == 200) {
                        Log.d( "Onresponse", "Onresponse: " + response );
                        if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            communityGrowthIncome.addAll(response.body().getData());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerViewcommunity.setLayoutManager(mLayoutManager);
                            recyclerViewcommunity.setItemAnimator(new DefaultItemAnimator());
                            communityGrowthIncomeAdapter = new CommunityGrowthIncomeAdapter(getActivity(), communityGrowthIncome);
                            recyclerViewcommunity.setAdapter(communityGrowthIncomeAdapter);



                        } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                        }
                    }

                } else {
                    Log.d( "onResponse: ", "onResponse: " + response );
                }
            }

            @Override
            public void onFailure(Call<CommunityGrowthModel> call, Throwable t) {
                Log.d( "OnresponseFail", "OnresponseFail: " + t );
            }
        } );
    }*/


    /*void initError(View view) {
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
*/

    void noInternetConnectionpv() {
        Log.d("PV===","No internet");
        txtErrorTitle.setText(R.string.error_title);
        txtSubError.setText(R.string.error_content);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

    }
    void noInternetConnectionweekly() {
        Log.d("PV===","No internet");
        txtErrorTitleweekly.setText(R.string.error_title);
        txtSubErrorweekly.setText(R.string.error_content);
        llEmptyWeek.setVisibility(View.VISIBLE);
        txtRetryweekly.setVisibility(View.VISIBLE);

    }
    void noInternetConnectionincome() {
        Log.d("PV===","No internet");
        txtErrorTitleincome.setText(R.string.error_title);
        txtSubErrorincome.setText(R.string.error_content);
        llEmptyCommunity.setVisibility(View.VISIBLE);
        txtRetryincome.setVisibility(View.VISIBLE);

    }


    void noRecordsFound() {
        Log.d("norecord==","pv");
        txtErrorTitle.setText(R.string.error_no_records);
        txtSubError.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
    }

    void noRecordsFoundWeek() {
        Log.d("norecord==","week");
        txtErrorTitleweekly.setText(R.string.error_no_records);
        txtSubErrorweekly.setVisibility(View.GONE);
        llEmptyWeek.setVisibility(View.VISIBLE);
        txtRetryweekly.setVisibility(View.GONE);
    }

    void noRecordsFoundCommunity() {
        Log.d("norecord==","community");
        txtErrorTitleincome.setText(R.string.error_no_records);
        txtSubErrorincome.setVisibility(View.GONE);
        llEmptyCommunity.setVisibility(View.VISIBLE);
        txtRetryincome.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        recyclerViewpv.setVisibility(View.GONE);
        recyclerViewweekly.setVisibility(View.GONE);
        recyclerViewcommunity.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

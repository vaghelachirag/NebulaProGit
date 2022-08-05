package com.nebulacompanies.ibo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.activities.LoginActivity;
import com.nebulacompanies.ibo.activities.LoginSkipActivity;
import com.nebulacompanies.ibo.activities.LoginSkipDialog;
import com.nebulacompanies.ibo.adapter.BvAdapter;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.BvDetails;
import com.nebulacompanies.ibo.model.BvList;
import com.nebulacompanies.ibo.model.IdDetails;
import com.nebulacompanies.ibo.model.RankAndVolumeList;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.itangqi.waveloadingview.WaveLoadingView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;

/**
 * Created by Palak Mehta on 16-Feb-18.
 */

public class VolumeFragment extends Fragment {

    WaveLoadingView mWaveLoadingView;
    MyTextView bvMyTextView, nextBVMyTextView, myBVMyTextView, monthlyBVMyTextView;

    Boolean isRefreshed = false;
    //RankAndVolumeList volumeList;
    RelativeLayout bvLinearLayout;
    NumberFormat formatter;
    Session session;
    LinearLayout llSettings, lydata, lynologin, lylogindetails;
    MyButtonEcom btnLogin,btnSignin;
    private LinearLayout llEmpty;
    private ImageView imgError, imgRefreshBv;
    private MyTextView txtErrorTitle, txtSubError, txtRetry, mybv, monthlybv, commulativebv, bvPercent;
    ListView listviewbv;
    private ArrayList<BvList> arrayListBvList = new ArrayList<>();
    BvAdapter bvAdapter;
    //SharedPreferences.Editor editor;
    // SharedPreferences prefs;

    MaterialProgressBar mProgressDialog;
    BusinessActivity mActivity;
    private ArrayList<IdDetails> idDetails = new ArrayList<>();
    IntentFilter intentFilter;
    Utils utils = new Utils();

    public static VolumeFragment newInstance() {
        VolumeFragment f = new VolumeFragment();
        return f;
    }

    private final BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (mProgressDialog != null) {
                mProgressDialog.setVisibility(View.GONE);
            }
            if (action.equals(BusinessActivity.SUCCESS)) {
               /* Gson gson = new Gson();
                String json = intent.getExtras().getString("data");

                if (getActivity() != null) {
                    SharedPreferences prefs =getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
                    prefs.edit().putString("rankAndVolume", json).apply();
                }
*/
                bvLinearLayout.setVisibility(View.VISIBLE);
                /*Type collectionType = new TypeToken<RankAndVolumeList>() {
                }.getType();
                volumeList = gson.fromJson(json, collectionType);*/
                getBVGraph(BusinessActivity.rankAndVolumeList);
            } else if (action.equals(BusinessActivity.NOSERVICE)) {
                serviceUnavailable();
            } else if (action.equals(BusinessActivity.SUCCESSTAB)) {
                String json = intent.getExtras().getString("data");
                setBVData(json);
            } else if (action.equals(BusinessActivity.NOSERVICETAB)) {
                stopAnim(imgRefreshBv, txtRetry);
            } else if (action.equals(BusinessActivity.VISIBLEBv)) {
                // Toast.makeText(getActivity(), ": Load  BV " , Toast.LENGTH_SHORT).show();
                //refreshContent();
                loadFullData();
            }
        }
    };

    private void setBVData(String json) {

        stopAnim(imgRefreshBv, txtRetry);
        idDetails.clear();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<IdDetails>>() {
        }.getType();
        idDetails.addAll(gson.fromJson(json, collectionType));
        IdDetails data = idDetails.get(0);
        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String strMyBV = formatter.format(Integer.parseInt(data.getMyBV()));
        String strBvPercent = data.getBVPercent();
        String strMonthlyBV = formatter.format(Integer.parseInt(data.getMonthlyBV()));
        String strCummulativeGBV = formatter.format(Integer.parseInt(data.getCummulativeGBV()));

        mybv.setText(strMyBV);
        monthlybv.setText(strMonthlyBV);
        commulativebv.setText(strCummulativeGBV);
        bvPercent.setText(strBvPercent + "%");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.volume_fragment, container, false);
        init(view);
        mActivity = (BusinessActivity) getActivity();
        //prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction(BusinessActivity.SUCCESS);
        intentFilter.addAction(BusinessActivity.NOSERVICE);
        intentFilter.addAction(BusinessActivity.NOSERVICETAB);
        intentFilter.addAction(BusinessActivity.VISIBLEBv);
        intentFilter.addAction(BusinessActivity.SUCCESSTAB);
        getActivity().registerReceiver(rankReceiver, intentFilter);
        if (!session.getLogin()) {
            lynologin.setVisibility(View.VISIBLE);

        } else {
            lydata.setVisibility(View.VISIBLE);
            lynologin.setVisibility(View.GONE);

        }

        noInternetConnection();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;
        session = new Session(getActivity());
        formatter = NumberFormat.getNumberInstance(new Locale("en", "IN"));

    }

    void init(View view) {
        initError(view);
        mWaveLoadingView = view.findViewById(R.id.waveLoadingView);
        bvMyTextView = view.findViewById(R.id.bv);
        nextBVMyTextView = view.findViewById(R.id.next_bv);
        myBVMyTextView = view.findViewById(R.id.mybv);
        monthlyBVMyTextView = view.findViewById(R.id.monthlybv);
        bvLinearLayout = view.findViewById(R.id.bv_layout);
        llSettings = view.findViewById(R.id.llSettings);
        lydata = view.findViewById(R.id.ly_details);
        lynologin = view.findViewById(R.id.ly_no_login);
        lylogindetails = view.findViewById(R.id.ly_no_login_details);
        btnLogin = view.findViewById(R.id.btn_login);
        btnSignin = view.findViewById(R.id.btn_signin);
        ViewPager pager = getActivity().findViewById(R.id.viewpager_dashboard);
        TabLayout tabLayout = getActivity().findViewById(R.id.tab_dashboard);
        bvLinearLayout.setVisibility(View.GONE);
        listviewbv = view.findViewById(R.id.listview_bv);
        mProgressDialog = view.findViewById(R.id.progresbar);

        mybv = view.findViewById(R.id.id_my_bv);
        monthlybv = view.findViewById(R.id.id_monthly_bv);
        commulativebv = view.findViewById(R.id.commulative_bv);
        bvPercent = view.findViewById(R.id.bv_percent);
        imgRefreshBv = view.findViewById(R.id.refreshBV);

        btnLogin.setOnClickListener(v-> utils.openLoginDialog(getActivity(), utils.gotoMyBusiness));
        btnSignin.setOnClickListener(v-> utils.openLoginDialog(getActivity(), utils.gotoMyBusiness));


        imgRefreshBv.setOnClickListener(v -> {
            if (imgRefreshBv.getAnimation() == null) {

                startRotateAnim(imgRefreshBv, txtRetry);
                getBVDetails();

            }
        });

        listviewbv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listviewbv.getChildAt(0) != null) {
                    //mSwipeRefreshLayout.setEnabled( listviewbv.getFirstVisiblePosition() == 0 && listviewbv.getChildAt( 0 ).getTop() == 0 );
                }
            }
        });
    }

    private void noLogin() {
        stopAnim(imgRefreshBv, txtRetry);
      /*  txtErrorTitle.setText(R.string.login_title);
        txtSubError.setText(R.string.login_content);
        txtRetry.setText(R.string.login_retry);
        imgError.setImageResource(R.drawable.ic_baseline_login_24);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtSubError.setVisibility(View.VISIBLE);
        bvLinearLayout.setVisibility(View.GONE);
        listviewbv.setVisibility(View.GONE);
        txtRetry.setOnClickListener(v -> openLogin());*/
    }

   /* private void openLogin() {
        Intent login = new Intent(getActivity(), LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        getActivity().finish();
    }*/

    private void startRotateAnim(ImageView imgvw, MyTextView textvw) {
        RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(400);

        // Start animating the image
        imgvw.startAnimation(anim);
        imgvw.setEnabled(false);
        textvw.setEnabled(false);
    }

    private void stopAnim(ImageView imgvw, MyTextView textvw) {
        try {
            imgvw.setAnimation(null);
            imgvw.setEnabled(true);
            textvw.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getBVInfo() {
        if (!BusinessActivity.callRankVolume) {
            if (Utils.isNetworkAvailable(getActivity())) {
                mProgressDialog.setVisibility(View.VISIBLE);
                mActivity.getRankAndVolumes(true);
            } else {
                noInternetConnection();
            }
        }
         /*
            Call<RankAndVolumes> wsCallingRankAndVolumes = mAPIInterface.getRankAndVolumes();
            wsCallingRankAndVolumes.enqueue(new Callback<RankAndVolumes>() {
                @Override
                public void onResponse(Call<RankAndVolumes> call, Response<RankAndVolumes> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    //mSwipeRefreshLayout.setRefreshing( false );
                    if (response.isSuccessful()) {

                        Log.i("INFO", "RankAndVolumes code: " + response.body().getStatusCode());
                        if (response.code() == 200) {
                            if (mProgressDialog != null) {
                                mProgressDialog.setVisibility(View.GONE);
                            }
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                if (mProgressDialog != null) {
                                    mProgressDialog.setVisibility(View.GONE);
                                }
                                if (getActivity() != null) {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(response.body().getData());
                                    editor.putString("rankAndVolume", json);
                                    editor.apply();
                                }

                                bvLinearLayout.setVisibility(View.VISIBLE);
                                volumeList = response.body().getData();
                                getBVGraph(volumeList);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                        }
                    } else {

                        serviceUnavailable();
                    }

                }

                @Override
                public void onFailure(Call<RankAndVolumes> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    serviceUnavailable();
                }
            });*/
    }

    @Override
    public void onDestroy() {
        // Toast.makeText(getActivity(), ":Destroy volume" , Toast.LENGTH_SHORT).show();

        super.onDestroy();
        getActivity().unregisterReceiver(rankReceiver);

    }

    private void getBvDetail() {
        if (session.getLogin()) {
            listviewbv.setVisibility(View.GONE);
            llEmpty.setVisibility(View.GONE);
            lylogindetails.setVisibility(View.GONE);
            lydata.setVisibility(View.VISIBLE);
            lynologin.setVisibility(View.GONE);
            if (Utils.isNetworkAvailable(getActivity())) {
           /* final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);

            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

                mProgressDialog.setVisibility(View.VISIBLE);

                Call<BvDetails> wsCallingBvDetails =  BusinessActivity.mAPIInterface.getGetBvDetail();
                wsCallingBvDetails.enqueue(new Callback<BvDetails>() {
                    @Override
                    public void onResponse(Call<BvDetails> call, Response<BvDetails> response) {
                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }
                        //mSwipeRefreshLayout.setRefreshing( false );
                        if (response.isSuccessful()) {

                            Log.i("INFO", "RankAndVolumes code: " + response.body().getStatusCode());
                            if (response.code() == 200) {
                                if (mProgressDialog != null) {
                                    mProgressDialog.setVisibility(View.GONE);
                                }
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListBvList = new ArrayList<>();
                                    arrayListBvList.addAll(response.body().getData());
/*
                                if (getActivity() != null) {
                                    SharedPreferences prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
                                    ;
                                    //editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListBvList);
                                    prefs.edit().putString("bv_lists", json).apply();
                                }*/
                                    bvAdapter = new BvAdapter(getActivity(), arrayListBvList);
                                    listviewbv.setAdapter(bvAdapter);
                                    bvAdapter.notifyDataSetChanged();
                                    listviewbv.setVisibility(View.VISIBLE);
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }

                            }
                        } else {
                            serviceUnavailable();
                        }

                    }

                    @Override
                    public void onFailure(Call<BvDetails> call, Throwable t) {
                        mProgressDialog.setVisibility(View.GONE);
                        serviceUnavailable();
                    }
                });
            } else {
                noInternetConnection();
            }
        } else {
            listviewbv.setVisibility(View.GONE);
            llEmpty.setVisibility(View.GONE);
            lylogindetails.setVisibility(View.VISIBLE);
        }

    }

    void getBVGraph(RankAndVolumeList list) {
        bvLinearLayout.setVisibility(View.VISIBLE);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setCenterTitleColor(Color.RED);
        mWaveLoadingView.setBottomTitleSize(10);
        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.RED);
        mWaveLoadingView.setBorderColor(Color.RED);
        mWaveLoadingView.setTopTitleStrokeColor(Color.WHITE);
        mWaveLoadingView.setTopTitleStrokeWidth(10);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

        if (list != null) {


            if (list.getBVPercent() != 0) {
                bvMyTextView.setText(list.getBVPercent() + "%");
            }
            if (list.getNextBVPercent() != 0) {
                nextBVMyTextView.setText(list.getNextBVPercent() + "%");
            }
            if (list.getBVPercent() != 0) {
                mWaveLoadingView.setTopTitle(list.getBVPercent() + "%");
                mWaveLoadingView.setProgressValue((int) list.getBVPercent());
            }
            if (list.getMyBV() != null) {
                myBVMyTextView.setText(formatter.format(list.getMyBV()) + "");
            }

          /*  bvMyTextView.setText(list.getBVPercent() + "%");
            nextBVMyTextView.setText(list.getNextBVPercent() + "%");
            mWaveLoadingView.setTopTitle(list.getBVPercent() + "%");
            mWaveLoadingView.setProgressValue((int) list.getBVPercent());
           myBVMyTextView.setText(formatter.format(list.getMyBV()) + "");*/
            double MonthlyBV = list.getMonthlyBV();
            float f = (float) list.getMonthlyBV();
            monthlyBVMyTextView.setText(formatter.format(f));
        }
    }


    private void getBVDetails() {
        if (session.getLogin()) {
            if (!BusinessActivity.callTab) {
                // mProgressDialog.setVisibility(View.VISIBLE);
                // BusinessActivity parentFrag = ((BusinessActivity) VolumeFragment.this.getParentFragment());
                mActivity.getIdDetails(false);
            }
        } else
            noLogin();
       /*
        Call<IdDetailsModel> wsCallingBanersList = mAPIInterface.getIdDetails(session.getIboKeyId());
        Log.d("OnresponseStart", "OnresponseStart: ");
        wsCallingBanersList.enqueue(new Callback<IdDetailsModel>() {
            @Override
            public void onResponse(Call<IdDetailsModel> call, Response<IdDetailsModel> response) {
                stopAnim(imgRefreshBv);
                Log.d("OnresponseIF", "OnresponseIF: " + response);
                if (response.isSuccessful()) {
                    idDetails.clear();

                    if (response.code() == 200) {
                        Log.d("Onresponse", "Onresponse: " + response);
                        if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            idDetails.addAll(response.body().getData());
//                            String s = idDetails.get(0).getJoiningDate();
//                            Log.d("DATAAAA","DATAAA: "+s);


                            DecimalFormat formatter = new DecimalFormat("#,##,###");
                            String strMyBV = formatter.format(Integer.parseInt(idDetails.get(0).getMyBV()));
                            String strBvPercent = idDetails.get(0).getBVPercent();
                            String strMonthlyBV = formatter.format(Integer.parseInt(idDetails.get(0).getMonthlyBV()));
                            String strCummulativeGBV = formatter.format(Integer.parseInt(idDetails.get(0).getCummulativeGBV()));

                            mybv.setText(strMyBV);
                            monthlybv.setText(strMonthlyBV);
                            commulativebv.setText(strCummulativeGBV);
                            bvPercent.setText(strBvPercent + "%");

                        } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                        }
                    }

                } else {
                    Log.d("onResponse: ", "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<IdDetailsModel> call, Throwable t) {
                stopAnim(imgRefreshBv);
                Log.d("OnresponseFail", "OnresponseFail: " + t);
            }
        });*/
    }

    private void refreshContent() {
        if (session.getLogin()) {
            if (Utils.isNetworkAvailable(getActivity())) {
                isRefreshed = true;
                getBVInfo();
                getBvDetail();
                getBVDetails();
                showRecords();
            } else {
                noInternetConnection();
            }
        } else
            noLogin();
    }

    void initError(View view) {
        llEmpty = view.findViewById(R.id.llEmpty);
        imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = view.findViewById(R.id.txtErrorTitle);
        txtSubError = view.findViewById(R.id.txtErrorSubTitle);
        txtRetry = view.findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(v -> refreshContent());
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        bvLinearLayout.setVisibility(View.GONE);
        listviewbv.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }


    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        // imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        bvLinearLayout.setVisibility(View.GONE);
        listviewbv.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_title);
        txtSubError.setText(R.string.error_content);
        txtSubError.setVisibility(View.VISIBLE);
        //  imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        bvLinearLayout.setVisibility(View.VISIBLE);
        listviewbv.setVisibility(View.GONE);
    }

    void showRecords() {
        //txtErrorTitle.setText(R.string.error_msg_network);
        //imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        bvLinearLayout.setVisibility(View.VISIBLE);
        listviewbv.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
        lylogindetails.setVisibility(View.GONE);
    }

    public void loadFullData() {
        getBvDetail();
        getBVDetails();
    }

}
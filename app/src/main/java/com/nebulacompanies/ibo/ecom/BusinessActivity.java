package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.fragments.MyPVFragment;
import com.nebulacompanies.ibo.fragments.RankFragment;
import com.nebulacompanies.ibo.fragments.UpdatesFragment;
import com.nebulacompanies.ibo.fragments.VolumeFragment;
import com.nebulacompanies.ibo.model.IdDetails;
import com.nebulacompanies.ibo.model.IdDetailsModel;
import com.nebulacompanies.ibo.model.RankAndVolumeList;
import com.nebulacompanies.ibo.model.RankAndVolumes;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class BusinessActivity extends AppCompatActivity {

    public static APIInterface mAPIInterface;
    MaterialProgressBar mProgressDialog;
    Session session;
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static boolean callRankVolume = false;
    public static String SUCCESS = "success";
    public static String NOSERVICE = "noservice";
    public static String VIEWVISIBLE = "viewvisible";
    public static boolean callTab = false;
    public static String SUCCESSTAB = "successtab";
    public static String NOSERVICETAB = "noservicetab";
    public static String NOINTERNETTAB = "nointernettab";

    MyTextViewEcom tvToolbarTitle;
    ImageView imgBackArrow;
    public static boolean othersTab = false;
    public static boolean pvTab = false;
    public static boolean bvTab = false;
    public static String VISIBLEOthers = "visibleothers";
    public static String VISIBLEPv = "visiblepv";
    public static String VISIBLEBv = "visiblebv";
    public static String VISIBLETAB = "visiblealltabdata";
    public static RankAndVolumeList rankAndVolumeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        initUI();
        setPagerTab();
        initOnClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Utils().checkExpireUser(mAPIInterface, this, session);

    }

    @SuppressLint("InflateParams")
    private void setPagerTab() {
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        MyTextView tabRank = (MyTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabRank.setText(getResources().getString(R.string.id_details));

        MyTextView tabPV = (MyTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabPV.setText(getResources().getString(R.string.pv_details));

        MyTextView tabBV = (MyTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabBV.setText(getResources().getString(R.string.bv_details));

        MyTextView tabIncome = (MyTextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabIncome.setText(getResources().getString(R.string.nv_details));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3 && !othersTab) {
                    Log.d("call==", "others");
                    othersTab = true;
                    Intent intent = new Intent(VISIBLEOthers);
                    sendBroadcast(intent);
                } else if (position == 1 && !pvTab) {
                    Log.d("call==", "PV");
                    pvTab = true;
                    Intent intent = new Intent(VISIBLEPv);
                    sendBroadcast(intent);
                } else if (position == 2 && !bvTab) {
                    Log.d("call==", "BV");
                    bvTab = true;
                    Intent intent = new Intent(VISIBLEBv);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void initUI() {
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        mProgressDialog = findViewById(R.id.progresbar);
        toolbar = findViewById(R.id.toolbar_search);

        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Business");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imgBackArrow = findViewById(R.id.img_back);
        session = new Session(this);
        viewPager = findViewById(R.id.viewpager_dashboard);
        tabLayout = (TabLayout) findViewById(R.id.tab_dashboard);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(VISIBLETAB);
        registerReceiver(rankReceiver, intentFilter);
    }

    private final BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(VISIBLETAB)) {
                mAPIInterface = APIClient.getClient(BusinessActivity.this).create(APIInterface.class);
                int position = viewPager.getCurrentItem();
                Log.d("call", " : pos : " + position);
                othersTab = false;
                pvTab = false;
                bvTab = false;
                if (position == 0) {
                    setPagerTab();
                } else {
                    getIdDetails(true);
                    getRankAndVolumes(true);
                }

                if (position == 3) {
                    Log.d("call==", "others");
                    othersTab = true;
                    Intent mintent = new Intent(VISIBLEOthers);
                    sendBroadcast(mintent);
                } else if (position == 1) {
                    Log.d("call==", "PV");
                    pvTab = true;
                    Intent mintent = new Intent(VISIBLEPv);
                    sendBroadcast(mintent);
                } else if (position == 2) {
                    Log.d("call==", "BV");
                    bvTab = true;
                    Intent mintent = new Intent(VISIBLEBv);
                    sendBroadcast(mintent);
                }
            }
        }
    };

    public static void clearTab() {
        othersTab = false;
        pvTab = false;
        bvTab = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(rankReceiver);
    }

    void initOnClick() {
        imgBackArrow.setOnClickListener(v -> onBackPressed());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RankFragment(), getResources().getString(R.string.id_details));
        adapter.addFragment(new MyPVFragment(), getResources().getString(R.string.pv_details));
        adapter.addFragment(new VolumeFragment(), getResources().getString(R.string.bv_details));
        //adapter.addFragment(new (), getResources().getString(R.string.nv_details));
        //adapter.addFragment(new IncomeFragment(), getResources().getString(R.string.income));
        adapter.addFragment(new UpdatesFragment(), getResources().getString(R.string.nv_details));
        //adapter.addFragment(new IBOGeoLocationFragment(), getResources().getString(R.string.prompt_ibo_geo_location));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Layout has happened here.
                        // Don't forget to remove your listener when you are done with it.
                        viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void getRankAndVolumes(boolean outsideCall) {
        if (Utils.isNetworkAvailable(BusinessActivity.this)) {
            callRankVolume = true;
            Log.d("Call:::", "getRankAndVolumes method");

            Call<RankAndVolumes> wsCallingRankAndVolumes = mAPIInterface.getRankAndVolumes();
            wsCallingRankAndVolumes.enqueue(new Callback<RankAndVolumes>() {
                @Override
                public void onResponse(@NotNull Call<RankAndVolumes> call, @NotNull Response<RankAndVolumes> response) {
                    if (response.isSuccessful()) {
                        Log.i("INFO", "RankAndVolumes code: " + response);
                        if (response.code() == 200) {

                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                // setData(response.body().getData());
                                Log.d("RANK==", "getRankAndVolumes ecom Business call: " + response.body().getData().getNextEligibleRank());

                                rankAndVolumeList = response.body().getData();
                                Type collectionType = new TypeToken<RankAndVolumeList>() {
                                }.getType();
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getData(), collectionType);
                                Intent intent = new Intent();
                                intent.setAction(SUCCESS);
                                intent.putExtra("data", json);
                                sendBroadcast(intent);
                            } else {
                                Intent intent = new Intent();
                                intent.setAction(NOSERVICE);
                                sendBroadcast(intent);
                            }
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(NOSERVICE);
                            sendBroadcast(intent);
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(VIEWVISIBLE);
                        sendBroadcast(intent);
                        Intent mintent = new Intent();
                        mintent.setAction(NOSERVICE);
                        sendBroadcast(mintent);
                    }
                    callRankVolume = false;
                }

                @Override
                public void onFailure(@NotNull Call<RankAndVolumes> call, @NotNull Throwable t) {
                    Intent intent = new Intent();
                    intent.setAction(NOSERVICE);
                    sendBroadcast(intent);
                    callRankVolume = false;
                }
            });
        } else {
            Log.d("RANK==", "Network error");
            Intent intent = new Intent();
            intent.setAction(NOINTERNETTAB);
            sendBroadcast(intent);
            callRankVolume = false;
        }
    }

    public void getIdDetails(boolean callRank) {
        if (Utils.isNetworkAvailable(BusinessActivity.this)) {
            callTab = true;
            Log.d("Call:::", "getIdDetails() method " + callRank);

            Call<IdDetailsModel> wsCallingBanersList = mAPIInterface.getIdDetails(session.getIboKeyId());
            Log.d("OnresponseStart", "OnresponseStart: ");
            wsCallingBanersList.enqueue(new Callback<IdDetailsModel>() {
                @Override
                public void onResponse(@NotNull Call<IdDetailsModel> call, @NotNull Response<IdDetailsModel> response) {
                    Log.d("OnresponseIF", "OnresponseIF: " + response);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("Onresponse", "Onresponse: " + response);
                            assert response.body() != null;
                            if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("RANK==", "call ID: Business: " + callRank + " : ");

                                Type collectionType = new TypeToken<List<IdDetails>>() {
                                }.getType();
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getData(), collectionType);
                                Intent intent = new Intent();
                                intent.setAction(SUCCESSTAB);
                            /*SharedPreferences prefs = getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("bv_data", json);
                            editor.apply();*/
                                intent.putExtra("data", json);
                                intent.putExtra("call", callRank);
                                sendBroadcast(intent);
                            } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                        }
                    } else {
                        Log.d("onResponse: ", "onResponse: " + response);
                    }
                    callTab = false;
                }

                @Override
                public void onFailure(@NotNull Call<IdDetailsModel> call, @NotNull Throwable t) {
                    Intent intent = new Intent();
                    intent.setAction(NOSERVICETAB);
                    sendBroadcast(intent);
                    callTab = false;
                }
            });
        } else {
            Log.d("RANK==", "Network error id details");
            Intent intent = new Intent();
            intent.setAction(NOINTERNETTAB);
            sendBroadcast(intent);
            callTab = false;
        }
    }
}
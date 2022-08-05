package com.nebulacompanies.ibo.activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.nebulacompanies.ibo.adapter.BonanzaIncomeAdapter;
import com.nebulacompanies.ibo.adapter.MiniBonanzaIncomeAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.BonanzaTopTenAdapter;
import com.nebulacompanies.ibo.adapter.MiniBonanzaLegDetailsAdapter;
import com.nebulacompanies.ibo.adapter.MiniBonanzaTopTenAdapter;
import com.nebulacompanies.ibo.model.Bonanza;
import com.nebulacompanies.ibo.model.BonanzaAchieversList;
import com.nebulacompanies.ibo.model.BonanzaDetails;
import com.nebulacompanies.ibo.model.BonanzaIncomeList;
import com.nebulacompanies.ibo.model.BonanzaTopTenList;
import com.nebulacompanies.ibo.model.MiniBonanza;
import com.nebulacompanies.ibo.model.MiniBonanzaAchieverList;
import com.nebulacompanies.ibo.model.MiniBonanzaDetails;
import com.nebulacompanies.ibo.model.MiniBonanzaIncomeList;
import com.nebulacompanies.ibo.model.MiniBonanzaLeg;
import com.nebulacompanies.ibo.model.MiniBonanzaLegDetails;
import com.nebulacompanies.ibo.model.MiniBonanzaTopTenList;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.CustomViewPager;
import com.nebulacompanies.ibo.view.MyTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.GetTime.SetDateFormats;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 25-Apr-18.
 */

public class BonanzaIncome extends AppCompatActivity {

    TreeView treeView;
    private TreeNode mCurrentNode;
    private int nodeCount = 1;

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView, listViewSales;
    MyTextView bonanza;
    LinearLayout linearLayout, treeLinearLayout;
    NestedScrollView nestedScrollView;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    //Error View1
    private LinearLayout llEmpty1;
    private ImageView imgError1;
    private MyTextView txtErrorTitle1, txtRetry1;

    //Error View2
    private LinearLayout llEmpty2;
    private ImageView imgError2;
    private MyTextView txtErrorTitle2, txtRetry2;

    public static final String TAG = "Bonanza";
    ConnectionDetector cd;
    ProgressDialog mProgressDialog;

    private Toolbar toolbar;
    private TabLayout tabLayout, tabLayout1;
    private CustomViewPager viewPager, viewPager1;
    Boolean isRefreshed = false, isDisplayTeam = false;
    private APIInterface mAPIInterface;
    String IncomeName;

    MiniBonanzaDetails miniBonanzaDetails;
    ArrayList<MiniBonanzaAchieverList> miniBonanzaAchieverLists = new ArrayList<MiniBonanzaAchieverList>();
    ArrayList<MiniBonanzaIncomeList> miniBonanzaIncomeLists = new ArrayList<MiniBonanzaIncomeList>();
    ArrayList<MiniBonanzaTopTenList> miniBonanzaTopTenLists = new ArrayList<MiniBonanzaTopTenList>();
    MiniBonanzaIncomeAdapter miniBonanzaIncomeAdapter;
    MiniBonanzaTopTenAdapter miniBonanzaTopTenAdapter;

    BonanzaDetails bonanzaDetails;
    ArrayList<BonanzaAchieversList> bonanzaAchieversLists = new ArrayList<BonanzaAchieversList>();
    ArrayList<BonanzaIncomeList> bonanzaIncomeLists = new ArrayList<BonanzaIncomeList>();
    ArrayList<BonanzaTopTenList> bonanzaTopTenLists = new ArrayList<BonanzaTopTenList>();
    BonanzaIncomeAdapter bonanzaIncomeAdapter;
    BonanzaTopTenAdapter bonanzaTopTenAdapter;

    BonanzaAdapter bonanzaAdapter;
    MiniBonanzaAdapter miniBonanzaAdapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CardView cardView;

    ArrayList<MiniBonanzaLegDetails> miniBonanzaLegDetails = new ArrayList<>();
    MiniBonanzaLegDetailsAdapter miniBonanzaLegDetailsAdapter;

    LinearLayout lnBonanza;
    final Handler handler = new Handler();
    Runnable r;
    Session session;
    boolean oneTime = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bonanza);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            IncomeName = b.getString("IncomeName");
        }

        setActionBar();
        init();

        if (IncomeName.equals("Mini Bonanza"))
            getMiniBonanza();
        else if (IncomeName.equals("Bonanza")) {
            getBonanza();
        }
    }

    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        session = new Session(this);
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(IncomeName);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }

    void init() {
        cd = new ConnectionDetector(getApplicationContext());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        initError1();
        initError2();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mini_bonanza_swipe_refresh_layout);
        linearLayout = (LinearLayout) findViewById(R.id.bonanaza_layout);
        listView = (ListView) findViewById(R.id.bonanza_listview_income);
        listViewSales = (ListView) findViewById(R.id.bonanza_listview_sales);
        treeLinearLayout = (LinearLayout) findViewById(R.id.tree_layout);
        lnBonanza = (LinearLayout) findViewById(R.id.ln_bonanza);

        bonanza = (MyTextView) findViewById(R.id.bonanza_title);
        //mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.mini_bonanza_swipe_refresh_layout);
        bonanza.setText(IncomeName);
        //llEmpty.setVisibility(View.VISIBLE);+
        //mSwipeRefreshLayout.setEnabled(false);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.getParent().requestChildFocus(nestedScrollView, nestedScrollView);

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

        listViewSales.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (listViewSales.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(listViewSales.getFirstVisiblePosition() == 0 && listViewSales.getChildAt(0).getTop() == 0);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        cardView = (CardView) findViewById(R.id.bonanza_leg_cardview);
        recyclerView = (RecyclerView) findViewById(R.id.bonanza_recyclerview);
        recyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (IncomeName.equals("Mini Bonanza"))
                getMiniBonanza();
            else if (IncomeName.equals("Bonanza")) {
                getBonanza();
            }
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    void getMiniBonanza() {
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
            Call<MiniBonanza> wsCallingMiniBonanza = mAPIInterface.getMiniBonanza();
            wsCallingMiniBonanza.enqueue(new Callback<MiniBonanza>() {
                @Override
                public void onResponse(Call<MiniBonanza> call, Response<MiniBonanza> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    //arrayListDreamVolumeList.clear();

                    miniBonanzaAchieverLists.clear();
                    miniBonanzaIncomeLists.clear();
                    miniBonanzaTopTenLists.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                miniBonanzaDetails = response.body().getData();
                                miniBonanzaAchieverLists.addAll(miniBonanzaDetails.getMiniBonanzaAchievers());

                                int legcount = miniBonanzaAchieverLists.size();

                                if (miniBonanzaAchieverLists.size() > 0) {
                                    cardView.setVisibility(View.VISIBLE);
                                    miniBonanzaAdapter = new MiniBonanzaAdapter(BonanzaIncome.this, miniBonanzaAchieverLists);
                                    recyclerView.setAdapter(miniBonanzaAdapter);
                                    lnBonanza.setVisibility(View.VISIBLE);
                                } else {
                                    cardView.setVisibility(View.GONE);
                                }

                                //Income List
                                miniBonanzaIncomeLists.addAll(miniBonanzaDetails.getMiniBonanzaIncome());
                                if (miniBonanzaIncomeLists.size() > 0) {
                                    miniBonanzaIncomeAdapter = new MiniBonanzaIncomeAdapter(BonanzaIncome.this, miniBonanzaIncomeLists);
                                    listView.setAdapter(miniBonanzaIncomeAdapter);
                                    miniBonanzaIncomeAdapter.notifyDataSetChanged();
                                    lnBonanza.setVisibility(View.VISIBLE);
                                } else {
                                    noRecordsFound1();
                                }

                                //Top 10 List
                                miniBonanzaTopTenLists.addAll(miniBonanzaDetails.getMiniBonanzaTopTenList());
                                if (miniBonanzaTopTenLists.size() > 0) {
                                    miniBonanzaTopTenAdapter = new MiniBonanzaTopTenAdapter(BonanzaIncome.this, miniBonanzaTopTenLists);
                                    listViewSales.setAdapter(miniBonanzaTopTenAdapter);
                                    miniBonanzaTopTenAdapter.notifyDataSetChanged();
                                    lnBonanza.setVisibility(View.VISIBLE);
                                } else {
                                    noRecordsFound2();
                                }

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                                serviceUnavailable1();
                                serviceUnavailable2();
                            }
                        } else {
                            serviceUnavailable();
                            serviceUnavailable1();
                            serviceUnavailable2();
                        }

                        ViewCompat.setNestedScrollingEnabled(listView, true);
                        ViewCompat.setNestedScrollingEnabled(listViewSales, true);

                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            // //doLogout(DreamVolume.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                        serviceUnavailable1();
                        serviceUnavailable2();
                    }
                }

                @Override
                public void onFailure(Call<MiniBonanza> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    serviceUnavailable();
                    serviceUnavailable1();
                    serviceUnavailable2();
                }
            });
        } else {
            noInternetConnection();
            //AppUtils.displayNetworkErrorMessage(this);
        }
    }

    void getBonanza() {
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
            Call<Bonanza> wsCallingMiniBonanza = mAPIInterface.getBonanza();
            wsCallingMiniBonanza.enqueue(new Callback<Bonanza>() {
                @Override
                public void onResponse(Call<Bonanza> call, Response<Bonanza> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    //arrayListDreamVolumeList.clear();

                    bonanzaAchieversLists.clear();
                    bonanzaIncomeLists.clear();
                    bonanzaTopTenLists.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                bonanzaDetails = response.body().getData();
                                bonanzaAchieversLists.addAll(bonanzaDetails.getBonanzaAchievers());

                                int legcount = bonanzaAchieversLists.size();
                                //getTreeViewBonanza(legcount);
                                lnBonanza.setVisibility(View.VISIBLE);
                                if (miniBonanzaAchieverLists.size() > 0) {
                                    cardView.setVisibility(View.VISIBLE);
                                    bonanzaAdapter = new BonanzaAdapter(BonanzaIncome.this, bonanzaAchieversLists);
                                    recyclerView.setAdapter(bonanzaAdapter);
                                    lnBonanza.setVisibility(View.VISIBLE);
                                } else {
                                    cardView.setVisibility(View.GONE);
                                }

                                //Income List
                                bonanzaIncomeLists.addAll(bonanzaDetails.getBonanzaIncome());
                                if (bonanzaIncomeLists.size() > 0) {
                                    bonanzaIncomeAdapter = new BonanzaIncomeAdapter(BonanzaIncome.this, bonanzaIncomeLists);
                                    listView.setAdapter(bonanzaIncomeAdapter);
                                    bonanzaIncomeAdapter.notifyDataSetChanged();
                                    lnBonanza.setVisibility(View.VISIBLE);
                                } else {
                                    noRecordsFound1();
                                }

                                //Top 10 List
                                bonanzaTopTenLists.addAll(bonanzaDetails.getBonanzaTopTenList());
                                if (bonanzaTopTenLists.size() > 0) {
                                    bonanzaTopTenAdapter = new BonanzaTopTenAdapter(BonanzaIncome.this, bonanzaTopTenLists);
                                    listViewSales.setAdapter(bonanzaTopTenAdapter);
                                    bonanzaTopTenAdapter.notifyDataSetChanged();
                                    lnBonanza.setVisibility(View.VISIBLE);
                                } else {
                                    noRecordsFound2();
                                }

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || +response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                                serviceUnavailable1();
                                serviceUnavailable2();
                            }

                        } else {
                            serviceUnavailable();
                            serviceUnavailable1();
                            serviceUnavailable2();
                        }

                        ViewCompat.setNestedScrollingEnabled(listView, true);
                        ViewCompat.setNestedScrollingEnabled(listViewSales, true);
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            // //doLogout(DreamVolume.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                        serviceUnavailable1();
                        serviceUnavailable2();
                    }
                }

                @Override
                public void onFailure(Call<Bonanza> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                    serviceUnavailable1();
                    serviceUnavailable2();
                }
            });
        } else {
            noInternetConnection();
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
        treeLinearLayout.setVisibility(View.GONE);
        lnBonanza.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        treeLinearLayout.setVisibility(View.GONE);
        lnBonanza.setVisibility(View.VISIBLE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        treeLinearLayout.setVisibility(View.GONE);
        lnBonanza.setVisibility(View.VISIBLE);
    }

    void initError1() {
        llEmpty1 = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError1 = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle1 = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtRetry1 = (MyTextView) findViewById(R.id.txtRetry1);
        txtRetry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void noRecordsFound1() {
        txtErrorTitle1.setText(R.string.error_no_records);
        imgError1.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty1.setVisibility(View.VISIBLE);
        txtRetry1.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

    }

    void serviceUnavailable1() {
        txtErrorTitle1.setText(R.string.error_service_unavailable);
        imgError1.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty1.setVisibility(View.VISIBLE);
        txtRetry1.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

   /* void noInternetConnection1(){
        txtErrorTitle1.setText(R.string.error_msg_network);
        imgError1.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty1.setVisibility(View.VISIBLE);
        txtRetry1.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }*/

    void initError2() {
        llEmpty2 = (LinearLayout) findViewById(R.id.llEmpty2);
        imgError2 = (ImageView) findViewById(R.id.imgError2);
        txtErrorTitle2 = (MyTextView) findViewById(R.id.txtErrorTitle2);
        txtRetry2 = (MyTextView) findViewById(R.id.txtRetry2);
        txtRetry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void serviceUnavailable2() {
        txtErrorTitle2.setText(R.string.error_service_unavailable);
        imgError2.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty2.setVisibility(View.VISIBLE);
        txtRetry2.setVisibility(View.VISIBLE);
        listViewSales.setVisibility(View.GONE);
    }

    void noRecordsFound2() {
        txtErrorTitle2.setText(R.string.error_no_records);
        imgError2.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty2.setVisibility(View.VISIBLE);
        txtRetry2.setVisibility(View.GONE);
        listViewSales.setVisibility(View.GONE);
    }

    public class MiniBonanzaAdapter extends RecyclerView.Adapter<MiniBonanzaAdapter.ViewHolder> {

        ArrayList<MiniBonanzaAchieverList> bonanzaAchieversLists;
        Context context;

        public MiniBonanzaAdapter(Context context, ArrayList<MiniBonanzaAchieverList> bonanzaAchieversLists) {
            super();
            this.context = context;
            this.bonanzaAchieversLists = bonanzaAchieversLists;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.bonanza_recycler_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {

            viewHolder.tvLeg.setText("Leg " + String.valueOf(bonanzaAchieversLists.get(i).getLeg()));
            viewHolder.tvCount.setText("Achievers: " + String.valueOf(bonanzaAchieversLists.get(i).getAchievers()));

            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "POsition : " + i, Toast.LENGTH_LONG).show();

                    miniBonanzaLegDetails.clear();
                    getMiniBonanzaLegDetails(view, i);

                }
            });
        }


        @Override
        public int getItemCount() {
            return bonanzaAchieversLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public MyTextView tvLeg, tvCount;
            public LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.bonanza_tree);
                tvLeg = (MyTextView) itemView.findViewById(R.id.bonanza_leg);
                tvCount = (MyTextView) itemView.findViewById(R.id.bonanza_count);
                //linearLayout.setOnClickListener(this);
            }

        }

    }

    void getMiniBonanzaLegDetails(final View view, int Pos) {
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
            Call<MiniBonanzaLeg> wsCallingMiniBonanza = mAPIInterface.getMiniBonanzaLegDetails(miniBonanzaAchieverLists.get(Pos).getIBOKeyID());
            wsCallingMiniBonanza.enqueue(new Callback<MiniBonanzaLeg>() {
                @Override
                public void onResponse(Call<MiniBonanzaLeg> call, Response<MiniBonanzaLeg> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                miniBonanzaLegDetails.addAll(response.body().getData());
                                popupAchieverList(view, miniBonanzaLegDetails);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                                //Toast.makeText(BonanzaIncome.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                               // AppUtils.displayServiceUnavailableMessage(BonanzaIncome.this);
                            }
                        } else {
                          //  AppUtils.displayServiceUnavailableMessage(BonanzaIncome.this);
                        }

                    } else {
                  //      AppUtils.displayServiceUnavailableMessage(BonanzaIncome.this);
                    }
                }

                @Override
                public void onFailure(Call<MiniBonanzaLeg> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();

                }
            });
        } else {
            //noInternetConnection();
            AppUtils.displayNetworkErrorMessage(this);
        }
    }

    private void popupAchieverList(View anchorView, ArrayList<MiniBonanzaLegDetails> legDetails) {
        PopupWindow popup = new PopupWindow(this);
        View layout = this.getLayoutInflater().inflate(R.layout.popup_content_achiever_list, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
        popup.setAnimationStyle(R.style.DialogAnimation);

        ListView listView = (ListView) layout.findViewById(R.id.mini_bonanza_listview_income);

        miniBonanzaLegDetailsAdapter = new MiniBonanzaLegDetailsAdapter(BonanzaIncome.this, legDetails);
        listView.setAdapter(miniBonanzaLegDetailsAdapter);
        miniBonanzaLegDetailsAdapter.notifyDataSetChanged();

    }


    public class BonanzaAdapter extends RecyclerView.Adapter<BonanzaAdapter.ViewHolder> {

        ArrayList<BonanzaAchieversList> bonanzaAchieversLists;
        Context context;

        public BonanzaAdapter(Context context, ArrayList<BonanzaAchieversList> bonanzaAchieversLists) {
            super();
            this.context = context;
            this.bonanzaAchieversLists = bonanzaAchieversLists;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.bonanza_recycler_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {

            viewHolder.tvLeg.setText("Leg " + String.valueOf(bonanzaAchieversLists.get(i).getLeg()));
            viewHolder.tvCount.setText("Mini Bonanzas: " + String.valueOf(bonanzaAchieversLists.get(i).getMiniBonanzaCount()));

            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "POsition : " + i, Toast.LENGTH_LONG).show();

                    miniBonanzaLegDetails.clear();
                    getBonanzaLegDetails(view, i);

                }
            });
        }


        @Override
        public int getItemCount() {
            return bonanzaAchieversLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public MyTextView tvLeg, tvCount;
            public LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.bonanza_tree);
                tvLeg = (MyTextView) itemView.findViewById(R.id.bonanza_leg);
                tvCount = (MyTextView) itemView.findViewById(R.id.bonanza_count);
                //linearLayout.setOnClickListener(this);
            }

        }

    }

    void getBonanzaLegDetails(final View view, int Pos) {
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
            Call<MiniBonanzaLeg> wsCallingBonanza = mAPIInterface.getBonanzaLegDetails(miniBonanzaAchieverLists.get(Pos).getIBOKeyID());
            wsCallingBonanza.enqueue(new Callback<MiniBonanzaLeg>() {
                @Override
                public void onResponse(Call<MiniBonanzaLeg> call, Response<MiniBonanzaLeg> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                miniBonanzaLegDetails.addAll(response.body().getData());
                                popupMiniBonanzaList(view, miniBonanzaLegDetails);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                                //Toast.makeText(BonanzaIncome.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                              //  AppUtils.displayServiceUnavailableMessage(BonanzaIncome.this);
                            }
                        } else {
                          //  AppUtils.displayServiceUnavailableMessage(BonanzaIncome.this);
                        }

                    } else {
                     //   AppUtils.displayServiceUnavailableMessage(BonanzaIncome.this);
                    }
                }

                @Override
                public void onFailure(Call<MiniBonanzaLeg> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();

                }
            });
        } else {
            //noInternetConnection();
            AppUtils.displayNetworkErrorMessage(this);
        }
    }

    private void popupMiniBonanzaList(View anchorView, ArrayList<MiniBonanzaLegDetails> legDetails) {
        PopupWindow popup = new PopupWindow(this);
        View layout = this.getLayoutInflater().inflate(R.layout.popup_content_achiever_list, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
        popup.setAnimationStyle(R.style.DialogAnimation);

        ListView listView = (ListView) layout.findViewById(R.id.mini_bonanza_listview_income);

        miniBonanzaLegDetailsAdapter = new MiniBonanzaLegDetailsAdapter(BonanzaIncome.this, legDetails);
        listView.setAdapter(miniBonanzaLegDetailsAdapter);
        miniBonanzaLegDetailsAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
       /* r = new Runnable() {
            public void run() {
                Calendar cala = Calendar.getInstance();
                SimpleDateFormat simpledat = new SimpleDateFormat("HH:mm:ss");
                String Dat = simpledat.format(cala.getTime());
                String[] Time = Dat.split(":");
                int minutes = Integer.parseInt(Time[0]);
                if (minutes >= SetDateFormats(session.getIboDate())) {
                    if (!oneTime) {
                        Intent i = new Intent(BonanzaIncome.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        session.setLogout(true);
                        session.setLogin(false);
                        session.clear();
                        finish();
                        startActivity(i);
                        oneTime = true;
                    }
                }
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);*/
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }
}

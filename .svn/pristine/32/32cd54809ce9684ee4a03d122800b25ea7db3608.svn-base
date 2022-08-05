package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.components.YAxis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.IncomeAdapter;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.IncomeHistory;
import com.nebulacompanies.ibo.model.IncomeHistoryList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightView;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


/**
 * Created by Palak Mehta on 15-Feb-18.
 */

public class IncomeFragment extends Fragment { //implements OnChartGestureListener, OnChartValueSelectedListener

    SwipeRefreshLayout mSwipeRefreshLayout;
    //ImageView imgRefresh;
    //LineChart incomeHistoryLineChart;
    NumberFormat formatter;
    Boolean isRefreshed = false;
    private APIInterface mAPIInterface;
    private ArrayList<IncomeHistoryList> arrayListIncomeHistory = new ArrayList<>();

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, txtNext;
    Integer maxvalue, showvalue;
    ArrayList<Integer> setvalue = new ArrayList<>();
    YAxis leftAxis;
    Integer set = 0;
    Session session;
    ConnectionDetector cd;
    RelativeLayout rlOverlay;
    ListView incomelistview;
    IncomeAdapter incomeAdapter;
    LinearLayout lnDashboardIncome;
    SharedPreferences.Editor editor;
  //  LinearLayout tabStrip;
    SharedPreferences prefs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income_fragment, container, false);
        init(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;

        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        formatter = NumberFormat.getNumberInstance(new Locale("en", "IN"));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (arrayListIncomeHistory.isEmpty()) {
                prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String incomeHistory = prefs.getString("income_list", "");
                if (incomeHistory != null && incomeHistory!="") {
                    LoadData();
                } else {
                    getIncomeHistory();
                    //getIncomeHistoryGraph();
                }
            }
        }
    }

    void init(View view) {
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.income_fragment_swipe_refresh_layout);
        // incomeHistoryLineChart = (LineChart) view.findViewById(R.id.income_history_linechart);
        lnDashboardIncome = (LinearLayout) view.findViewById(R.id.ln_dashboard_income);
        rlOverlay = (RelativeLayout) view.findViewById(R.id.rlOverlay);
        txtNext = (MyTextView) view.findViewById(R.id.tv_next);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        final ViewPager pager = (ViewPager) getActivity().findViewById(R.id.viewpager_dashboard);
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_dashboard);
        //imgRefresh = view.findViewById(R.id.imgRefresh1);
        incomelistview = (ListView) view.findViewById(R.id.listview_income);
       // tabStrip = ((LinearLayout)tabLayout.getChildAt(2));
       /* imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
*/
       /* incomeHistoryLineChart.setDescription("");
        incomeHistoryLineChart.setNoDataText("");
        incomeHistoryLineChart.setNoDataTextDescription("");
        incomeHistoryLineChart.zoom(9, 0, 10, 0);*/

        incomelistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (incomelistview.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(incomelistview.getFirstVisiblePosition() == 0 && incomelistview.getChildAt(0).getTop() == 0);
                }
            }
        });

        SharedPreferences walkthroughIncome = getActivity().getSharedPreferences(PrefUtils.prefIncome, 0);
        if (walkthroughIncome.getBoolean("walkthrough_first_time_income", true)) {
            rlOverlay.setVisibility(View.VISIBLE);
          /*  tabStrip.setEnabled(false);
            for(int i = 2; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setClickable(false);
            }*/
            walkthroughIncome.edit().putBoolean("walkthrough_first_time_income", false).apply();
        }

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNext.getText().toString().equals("Got it")) {
                    rlOverlay.setVisibility(View.GONE);
                    //
                    SpotlightView spotLight = new SpotlightView.Builder(getActivity())
                            .introAnimationDuration(400)
                            .enableRevealAnimation(true)
                            .performClick(true)
                            .fadeinTextDuration(400)
                            .setTypeface(FontUtil.get(getActivity(), "fonts/Montserrat-Regular.ttf"))
                            .headingTvColor(Color.parseColor("#eb273f"))
                            .headingTvSize(18)
                            .headingTvText("Others Updates")
                            .subHeadingTvColor(Color.parseColor("#ffffff"))
                            .subHeadingTvSize(14)
                            .subHeadingTvText("Updates from your and your team sales. New associates and rank holder in your downline.")
                            .maskColor(Color.parseColor("#dc000000"))
                            .target(tabLayout.getTabAt(3).getCustomView())
                            .lineAnimDuration(400)
                            .lineAndArcColor(Color.parseColor("#eb273f"))
                            .dismissOnTouch(false)
                            .dismissOnBackPress(false)
                            .enableDismissAfterShown(false)
                            .show();
                    tabLayout.getTabAt(3).getCustomView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pager.setCurrentItem(3);
                        }
                    });
                   /* tabStrip.setEnabled(true);
                    for(int i = 2; i < tabStrip.getChildCount(); i++) {
                        tabStrip.getChildAt(i).setClickable(true);
                    }*/
                }
            }
        });
    }

    private void refreshContent() {
        /*cd = new ConnectionDetector(getActivity().getApplicationContext());
        Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();*/

        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            getIncomeHistory();
            //getIncomeHistoryGraph();
            //mSwipeRefreshLayout.setRefreshing(true);
            showRecords();
        } else {
            // mSwipeRefreshLayout.setRefreshing(false);
            //AppUtils.displayNetworkErrorMessage(getActivity());
            // noInternetConnection();
            if (arrayListIncomeHistory.size()<0)
            {
                noInternetConnection();
            }
            else {
                LoadData();
            }
        }
    }

    void getIncomeHistory() {
        if (Utils.isNetworkAvailable(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(true);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<IncomeHistory> wsCallingIncomeHistory = mAPIInterface.getIncomeHistory();
            wsCallingIncomeHistory.enqueue(new Callback<IncomeHistory>() {
                @Override
                public void onResponse(Call<IncomeHistory> call, Response<IncomeHistory> response) {

                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    arrayListIncomeHistory.clear();
                    setvalue.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                    mProgressDialog.dismiss();
                                }
                                arrayListIncomeHistory.addAll(response.body().getData());
                                 prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(arrayListIncomeHistory);
                                editor.putString("income_list", json);
                                editor.apply();
                                Collections.sort(arrayListIncomeHistory, Collections.reverseOrder());
                                incomeAdapter = new IncomeAdapter(getActivity(), arrayListIncomeHistory);
                                incomelistview.setAdapter(incomeAdapter);
                                incomeAdapter.notifyDataSetChanged();
                                lnDashboardIncome.setVisibility(View.VISIBLE);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                        }
                    } else {
                       /* if (response.code() == 401) {
                            //doLogout(getActivity(), session);
                        } else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/

                        serviceUnavailable();
                    }

                }

                @Override
                public void onFailure(Call<IncomeHistory> call, Throwable t) {
                    mProgressDialog.dismiss();
                    serviceUnavailable();
                }
            });
        } else {
            LoadData();
        }
    }

   /* void getIncomeHistoryGraph(){
        incomeHistoryLineChart.setOnChartGestureListener(this);
        incomeHistoryLineChart.setOnChartValueSelectedListener(this);
        incomeHistoryLineChart.setDrawGridBackground(false);
        incomeHistoryLineChart.setHorizontalScrollBarEnabled(true);
        // add data
        //setdata

        // get the legend (only possible after setting data)
        Legend l = incomeHistoryLineChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        //mChart.setDescription("Demo Line Chart");

        incomeHistoryLineChart.setDescription("");
        incomeHistoryLineChart.setNoDataText("");
        incomeHistoryLineChart.setNoDataTextDescription("");

        // enable touch gestures
        incomeHistoryLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        incomeHistoryLineChart.setDragEnabled(true);
        incomeHistoryLineChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        incomeHistoryLineChart.setPinchZoom(true);
        incomeHistoryLineChart.setDoubleTapToZoomEnabled(true);
        incomeHistoryLineChart.setVisibleXRange(1, 10);

        //LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        LimitLine upper_limit = new LimitLine(500f, "");
        upper_limit.setLineWidth(0f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        //LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        LimitLine lower_limit = new LimitLine(-10f, "");
        lower_limit.setLineWidth(0f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        leftAxis = incomeHistoryLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        //leftAxis.setAxisMaxValue(showvalue * 0.5f);

        leftAxis.setAxisMinValue(1f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        incomeHistoryLineChart.getAxisRight().setEnabled(false);
        incomeHistoryLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //incomeHistoryLineChart.getXAxis().setLabelRotationAngle(-90);
        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        incomeHistoryLineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        incomeHistoryLineChart.invalidate();
    }*/

  /*  private ArrayList<String> setXAxisValuesRankHistory(ArrayList<IncomeHistoryList> list){

        ArrayList<String> xVals = new ArrayList<String>();
        //xVals.add("");
                *//*xVals.add("08-10-2017");
        xVals.add("14-10-2017");
        xVals.add("28-10-2017");*//*
        for(int i=0;i<list.size();i++) {
            xVals.add(SetGmtTime(list.get(i).getIncomeDate()));
            //Log.i("INFO"," Income xvals: " + SetGmtTime(list.get(i).getIncomeDate()));
        }
       *//* xVals.add("03-04-2018");
        xVals.add("14-06-2018");
        xVals.add("28-08-2018");
        xVals.add("03-10-2018");
        xVals.add("14-12-2018");
        xVals.add("14-02-2019");*//*
        xVals.add("");
        return xVals;
    }

    private ArrayList<Entry> setYAxisValuesRankHistory(ArrayList<IncomeHistoryList> list) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        //yVals.add(new Entry(0, 0));
        *//*yVals.add(new Entry(9, 0));
        yVals.add(new Entry(10, 1));
        yVals.add(new Entry(11, 2));*//*
        for(int i=0;i<list.size();i++) {
            yVals.add(new Entry(list.get(i).getTotalAmount(), i));
            //Log.i("INFO"," Income yvals: " + list.get(i).getTotalAmount());
            maxvalue=list.get(i).getTotalAmount();
            setvalue.add(maxvalue);
        }
        *//*yVals.add(new Entry(50000, 2));
        yVals.add(new Entry(900000, 3));
        yVals.add(new Entry(100000, 4));
        yVals.add(new Entry(700000, 5));
        yVals.add(new Entry(80000, 6));
        yVals.add(new Entry(200000, 7));*//*
        showvalue=Collections.max(setvalue);
        leftAxis.setAxisMaxValue(showvalue * 1.5f);
        incomeHistoryLineChart.invalidate();

        return yVals;
    }

    private void setDataIncomeHistory(ArrayList<IncomeHistoryList> arrayList) {
        ArrayList<String> xVals = setXAxisValuesRankHistory(arrayList);

        ArrayList<Entry> yVals = setYAxisValuesRankHistory(arrayList);

        if (yVals.size() == 1){
            incomeHistoryLineChart.zoom(0, 0, 0, 0);
        }


        LineDataSet set1, set2;

        // create a dataset and give it a type
        //set1 = new LineDataSet(yVals, "DataSet 1");
        set1 = new LineDataSet(yVals, "Income");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(11f);
        set1.setDrawFilled(true);

        //set2 = new LineDataSet(xVals, "DataSet 2");
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        //YAxisValueFormatter custom = new RankValueFormatter();
        YAxis YAxis = incomeHistoryLineChart.getAxisLeft();
        YAxis.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return formatter.format(value);
            }
        });

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        //mChart.setData(data);
        incomeHistoryLineChart.invalidate();

        // set data
        incomeHistoryLineChart.setData(data);

        incomeHistoryLineChart.getLineData().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return formatter.format(value);
            }
        });

        // incomeHistoryLineChart.setBottom(View.SCROLL_INDICATOR_BOTTOM);

        incomeHistoryLineChart.moveViewToX(arrayList.size());

        //getIncomeHistoryGraph();

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }*/

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
        // incomeHistoryLineChart.setVisibility(View.GONE);
        incomelistview.setVisibility(View.GONE);
        lnDashboardIncome.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        // incomeHistoryLineChart.setVisibility(View.GONE);
        incomelistview.setVisibility(View.GONE);
        lnDashboardIncome.setVisibility(View.VISIBLE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        // incomeHistoryLineChart.setVisibility(View.GONE);
        lnDashboardIncome.setVisibility(View.VISIBLE);
        incomelistview.setVisibility(View.GONE);
    }

    void showRecords() {
        //txtErrorTitle.setText(R.string.error_msg_network);
        //imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        // incomeHistoryLineChart.setVisibility(View.VISIBLE);
        incomelistview.setVisibility(View.VISIBLE);
    }

    public void LoadData() {
        mSwipeRefreshLayout.setRefreshing(false);
         prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString("income_list", null);
        Type type = new TypeToken<ArrayList<IncomeHistoryList>>() {
        }.getType();
        if (json!=null) {
            arrayListIncomeHistory = gson.fromJson(json, type);
            Collections.sort(arrayListIncomeHistory, Collections.reverseOrder());
            if (incomeAdapter == null)
                incomeAdapter = new IncomeAdapter(getActivity(), arrayListIncomeHistory);
            incomelistview.setAdapter(incomeAdapter);
            incomeAdapter.notifyDataSetChanged();
            lnDashboardIncome.setVisibility(View.VISIBLE);
        }
        else
        {
            noInternetConnection();
        }
    }
}
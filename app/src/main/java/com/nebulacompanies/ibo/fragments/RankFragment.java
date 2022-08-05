package com.nebulacompanies.ibo.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.activities.LoginActivity;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.IdDetails;
import com.nebulacompanies.ibo.model.RankAndVolumeList;
import com.nebulacompanies.ibo.model.RankHistoryList;
import com.nebulacompanies.ibo.pin.OnOtpCompletionListener;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.RankValueFormatter;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 15-Feb-18.
 */

public class RankFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener, OnOtpCompletionListener {
    FrameLayout frameLayout;
    BarChart currentRankBarChart;
    ImageView currentRankImageButton, nextEligibleImageButton;
    ProgressBar leg1ProgressBar, leg2ProgressBar, leg3ProgressBar;
    MyTextView currentRankMyTextView, nextRankMyTextView, count1MyTextView, count2MyTextView, count3MyTextView, leg1ProgressMyTextView, leg2ProgressMyTextView, leg3ProgressMyTextView;
    LineChart rankHistoryLineChart;
    String TokenKey, DeviceId, IMEI1, IMEI2;
    Session session;


    int index = -1;
    NumberFormat formatter;
    Context mContext = null;

    private final ArrayList<IdDetails> idDetails = new ArrayList<>();

    private CardView barchartCardView, progressbarCardView;
    Typeface customFont;
    //Error View
    private LinearLayout llEmptyid, layidData;
    private MyTextView txtErrorTitle, txtSubMsg, txtRetry;
    private MyTextView txtErrorTitlerank, txtSubMsgrank, txtRetryrank;

    LinearLayout llMenu, lnRank, lnrankdata;
    ImageView imgRankShare;

    Bitmap mbitmap;
    ImageView myIncomeShareRank;

    LinearLayout lnShareFull, llemptyrank, lnnologin;
    ImageView imgIncomeClose, imgRank, imgRefreshGraph, imgError;
    MyTextView tvId, tvName;
    MyTextView tvNextRankNewOne, tvNextRankNewTwo;
    MyTextView joiningdate, rank, activedate,expiryDate, totalactivation, totalactivation1,cappingAmount;

    Button btn_share;
    MyButtonEcom btn_login;
    LinearLayout tabStrip;

    boolean firstrun;
    MaterialProgressBar materialProgressBar3;
    IntentFilter intentFilter;
    BusinessActivity mActivity;
    SharedPreferences.Editor edtdata;
    SharedPreferences prefdata;
    Utils utils;
    MyButtonEcom btnLogin;
    private final BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            stopAnim(imgRefreshGraph, txtRetryrank);
            if (action.equals(BusinessActivity.SUCCESS)) {
                // Log.d("RANK==", "call success");
              /*  Gson gson = new Gson();
                String json = intent.getExtras().getString("data");
                Type collectionType = new TypeToken<RankAndVolumeList>() {}.getType();
                rankAndVolumeList = gson.fromJson(json, collectionType);
                DashboardActivity.rankAndVolumeList = rankAndVolumeList;*/
                // Log.d("RANK==", "Receiver: " + BusinessActivity.rankAndVolumeList.getNextEligibleRank());
                reflectData();
            } else if (action.equals(BusinessActivity.NOSERVICE)) {
                stopAnim(imgRefreshGraph, txtRetryrank);
                if (materialProgressBar3 != null) {
                    materialProgressBar3.setVisibility(View.GONE);
                }
                serviceUnavailableRank();
            } else if (action.equals(BusinessActivity.VIEWVISIBLE)) {
                barchartCardView.setVisibility(View.GONE);
                progressbarCardView.setVisibility(View.VISIBLE);
            } else if (action.equals(BusinessActivity.SUCCESSTAB)) {
                stopAnim(imgRank, txtRetry);
                idDetails.clear();
                Gson gson = new Gson();
                String json = intent.getExtras().getString("data");
                Type collectionType = new TypeToken<List<IdDetails>>() {
                }.getType();
                idDetails.addAll(gson.fromJson(json, collectionType));
                Log.d("RANK==", "call: " + intent.getExtras().getBoolean("call"));
                setTabData(intent.getExtras().getBoolean("call"));
            } else if (action.equals(BusinessActivity.NOSERVICETAB)) {
                stopAnim(imgRank, txtRetry);
                if (materialProgressBar3 != null) {
                    materialProgressBar3.setVisibility(View.GONE);
                }
                serviceUnavailableID();
            } else if (action.equals(BusinessActivity.NOINTERNETTAB)) {
                if (materialProgressBar3 != null) {
                    materialProgressBar3.setVisibility(View.GONE);
                }
                stopAnim(imgRefreshGraph, txtRetryrank);
                stopAnim(imgRank, txtRetry);
                noInternetConnectionID();
                noInternetConnectionStar();
            }


        }
    };

    private void setTabData(boolean callRank) {
        Log.d("RANK==", "setTabData: " + callRank);

        if (session.getLogin()) {
            IdDetails data = idDetails.get(0);
            joiningdate.setText(data.getJoiningDate());
            rank.setText(data.getRank());
            activedate.setText(data.getActiveDate());
            expiryDate.setText(data.getExpiryDate());
            String leftactive = data.getLeftActivedistributors();
            String rightactive = data.getRightActivedistributors();
            int a = Integer.parseInt(leftactive);
            int b = Integer.parseInt(rightactive);
            int totalactive = a + b;
            totalactivation.setText(totalactive + "");
            cappingAmount.setText(data.getCapping());

            SpannableStringBuilder builder = new SpannableStringBuilder();

            String red = "(F1:";
            SpannableString redSpannable = new SpannableString(red);
            redSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_table_text)), 0, red.length(), 0);
            builder.append(redSpannable);

            String white = data.getLeftActivedistributors();
            SpannableString whiteSpannable = new SpannableString(white);
            whiteSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, white.length(), 0);
            builder.append(whiteSpannable);

            String blue = ", F2:";
            SpannableString blueSpannable = new SpannableString(blue);
            blueSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_table_text)), 0, blue.length(), 0);
            builder.append(blueSpannable);


            String blue1 = idDetails.get(0).getRightActivedistributors() + ")";
            SpannableString blueSpannable1 = new SpannableString(blue1);
            blueSpannable1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, blue1.length(), 0);
            builder.append(blueSpannable1);
            totalactivation1.setText(builder);

            showRecordsID();
            if (callRank) {
                getRankAndVolumes();
            } else {
                if (materialProgressBar3 != null) {
                    materialProgressBar3.setVisibility(View.GONE);
                }
            }
        } else {
            noLogin();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rank_fragment, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mActivity = (BusinessActivity) getActivity();

        mContext = getActivity();
        customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        firstrun = getActivity().getSharedPreferences(PrefUtils.prefPref, MODE_PRIVATE).getBoolean("firstrun", true);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BusinessActivity.SUCCESS);
        intentFilter.addAction(BusinessActivity.NOSERVICE);
        intentFilter.addAction(BusinessActivity.VIEWVISIBLE);
        intentFilter.addAction(BusinessActivity.NOSERVICETAB);
        intentFilter.addAction(BusinessActivity.SUCCESSTAB);
        intentFilter.addAction(BusinessActivity.NOINTERNETTAB);
        getActivity().registerReceiver(rankReceiver, intentFilter);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefdata = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        edtdata = prefdata.edit();

        materialProgressBar3.setVisibility(View.VISIBLE);
        if (!session.getLogin())
            btn_login.setVisibility(View.VISIBLE);
        btn_login.setOnClickListener(v -> utils.openLoginDialog(getActivity(), utils.gotoMyBusiness));
        getIdDetails(true);
        Log.e("getAccessToken", "getAccessToken: " + session.getAccessToken());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(rankReceiver);
    }

    void init(View view) {
        initError(view);
        utils = new Utils();
        formatter = NumberFormat.getNumberInstance(new Locale("en", "IN"));
        materialProgressBar3 = view.findViewById(R.id.progresbar3);

        barchartCardView =  view.findViewById(R.id.barchart_cardview1);
        //cardviewIdDetails =  view.findViewById(R.id.cardview_id_details);
        progressbarCardView =  view.findViewById(R.id.progressbar_cardview1);
        //linechartCardView =  view.findViewById(R.id.linechart_cardview1);
        currentRankBarChart = view.findViewById(R.id.current_rank_barchart);
        currentRankImageButton =  view.findViewById(R.id.nextrankname1);
        nextEligibleImageButton =  view.findViewById(R.id.nextrankname);
        imgIncomeClose =  view.findViewById(R.id.img_income_close_rank);
        imgRank =  view.findViewById(R.id.ecomimage);
        imgRefreshGraph =  view.findViewById(R.id.refreshGraph);
        currentRankMyTextView =  view.findViewById(R.id.currentrank_label1);
        nextRankMyTextView =  view.findViewById(R.id.nextrank_label);
        leg1ProgressBar =  view.findViewById(R.id.leg1);
        leg2ProgressBar =  view.findViewById(R.id.leg2);
        leg3ProgressBar =  view.findViewById(R.id.leg3);
        btn_login = view.findViewById(R.id.btn_signin);
        count1MyTextView =  view.findViewById(R.id.total_count1);
        count2MyTextView =  view.findViewById(R.id.total_count2);
        count3MyTextView =  view.findViewById(R.id.total_count3);
        lnrankdata = view.findViewById(R.id.layrankdata);

        leg1ProgressMyTextView =  view.findViewById(R.id.leg1_progress);
        leg2ProgressMyTextView =  view.findViewById(R.id.leg2_progress);
        leg3ProgressMyTextView =  view.findViewById(R.id.leg3_progress);
        tvNextRankNewOne =  view.findViewById(R.id.tv_next_rank_new_one);
        tvNextRankNewTwo =  view.findViewById(R.id.tv_next_rank_new_two);
        lnRank = view.findViewById(R.id.ln_rank);
        lnnologin = view.findViewById(R.id.ly_no_login);
        lnnologin.setVisibility(View.GONE);
        lnShareFull = view.findViewById(R.id.ln_share_full);
        layidData = view.findViewById(R.id.layiddata);
        llemptyrank = view.findViewById(R.id.llEmptyrank);
        llemptyrank.setVisibility(View.GONE);
        lnrankdata.setVisibility(View.VISIBLE);
        TabLayout tabLayout = getActivity().findViewById(R.id.tab_dashboard);

        llMenu = view.findViewById(R.id.llMenu);
        rankHistoryLineChart = view.findViewById(R.id.rank_history_linechart);
        imgRankShare =  view.findViewById(R.id.img_share);

        //Id details
        joiningdate =  view.findViewById(R.id.id_joining_date);
        rank =  view.findViewById(R.id.rank);
        activedate =  view.findViewById(R.id.active_date);
        expiryDate = view.findViewById(R.id.expire_date);
        totalactivation =  view.findViewById(R.id.total_activation);
        totalactivation1 =  view.findViewById(R.id.total_activation1);
        cappingAmount = view.findViewById(R.id.capping);

        myIncomeShareRank =  view.findViewById(R.id.my_income_share_rank);
        tvId =  view.findViewById(R.id.tv_id);
        tvName =  view.findViewById(R.id.tv_name);
        frameLayout = view.findViewById(R.id.frame_layout);
        btn_share = view.findViewById(R.id.btn_share);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        btn_share.setTypeface(typeface);
        tabStrip = ((LinearLayout) tabLayout.getChildAt(0));

        tvId.setText("ID : " + session.getLoginID());
        tvName.setText("Name : " + session.getUserName());

        imgRank.setOnClickListener(v -> {
            if (imgRank.getAnimation() == null && imgRefreshGraph.getAnimation() == null) {
                startRotateAnim(imgRank, txtRetry);
                getIdDetails(false);
            }
        });


        imgRefreshGraph.setOnClickListener(v -> {
            if (imgRank.getAnimation() == null && imgRefreshGraph.getAnimation() == null) {
                startRotateAnim(imgRefreshGraph, txtRetryrank);
                getRankAndVolumes();
            }
        });

        imgRankShare.setOnClickListener(v -> {
            if (imgRankShare.isEnabled()) {
                imgRankShare.setEnabled(false);
                lnShareFull.setVisibility(View.VISIBLE);
                btn_share.setVisibility(View.VISIBLE);
                btn_share.setOnClickListener(v12 -> {
                    btn_share.setVisibility(View.INVISIBLE);
                    screenShot(frameLayout);
                    Uri bmpUri = getLocalBitmapUri(myIncomeShareRank);
                    lnShareFull.setVisibility(View.GONE);
                    if (bmpUri != null) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("image/*");
                        startActivity(Intent.createChooser(shareIntent, "Share Rank"));
                    } else {
                        Toast.makeText(getActivity(), "sharing failed", Toast.LENGTH_SHORT).show();
                    }

                });

                imgIncomeClose.setOnClickListener(v1 -> {
                    lnShareFull.setVisibility(View.GONE);
                    imgRankShare.setEnabled(true);
                    imgRankShare.setClickable(true);
                });
            }
        });
        currentRankBarChart.setDescription("");
        currentRankBarChart.setNoDataText("");
        currentRankBarChart.setNoDataTextDescription("");
        currentRankBarChart.setClickable(false);
        currentRankBarChart.setFocusable(false);
        currentRankBarChart.setFocusableInTouchMode(false);
        firebaseTokenGenerated(session.getIboKeyId());

    }

    private void startRotateAnim(ImageView imgvw, MyTextView textvw) {
        RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(400);

        // Start animating the image
        imgvw.startAnimation(anim);
        textvw.setEnabled(false);
        imgvw.setEnabled(false);
    }

    public class ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.textView);
        }
    }

    public void screenShot(View view) {
        mbitmap = getBitmapOFRootView(imgRankShare);
        myIncomeShareRank.setImageBitmap(mbitmap);
        createImage(mbitmap);
    }

    public Bitmap getBitmapOFRootView(View v) {
        frameLayout.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = frameLayout.getDrawingCache();
        return bitmap1;
    }

    public void createImage(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File(Environment.getExternalStorageDirectory() +
                "/capturedscreenandroid.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            materialProgressBar3.setVisibility(View.VISIBLE);
            getIdDetails(true);
            showRecords();
        } else {
            noInternetConnectionID();
        }
    }
*/

    private void getIdDetails(boolean callRank) {
        if (!BusinessActivity.callTab) {
            if (session.getLogin()) {
                lnnologin.setVisibility(View.GONE);
                mActivity.getIdDetails(callRank);
            } else {
                stopAnim(imgRank, txtRetry);
                noLogin();
            }
        }
    }

    private void stopAnim(ImageView imgvw, MyTextView textvw) {
        try {
            imgvw.setAnimation(null);
            imgvw.setEnabled(true);
            textvw.setEnabled(true);
        } catch (Exception ignored) {
        }
    }


    void getRankAndVolumes() {
        Log.d("RANK==", "getRankAndVolumes " + BusinessActivity.callRankVolume);
        if (!BusinessActivity.callRankVolume) {
            if (!session.getLogin()) {
                stopAnim(imgRefreshGraph, txtRetryrank);
                noLogin();
            } else {
                if (Utils.isNetworkAvailable(getActivity())) {
                    Log.d("RANK==", "call getRankAndVolumes");
                    mActivity.getRankAndVolumes(true);
                } else {
                    Log.d("RANK==", "materialProgressBar3 " + materialProgressBar3);
                    hideProgress();
                    stopAnim(imgRefreshGraph, txtRetryrank);
                    noInternetConnectionStar();
                }
            }
        } else {
            hideProgress();
        }
    }

    private void hideProgress() {

        if (materialProgressBar3 != null) {
            materialProgressBar3.setVisibility(View.GONE);
        }
    }

    private void reflectData() {
        Log.d("reflect==", "Data::");
        if (materialProgressBar3 != null) {
            materialProgressBar3.setVisibility(View.GONE);
        }
        OnlineLoad();
        // lnrankdata.setVisibility(View.VISIBLE);
       /* barchartCardView.setVisibility(View.GONE);
        progressbarCardView.setVisibility(View.GONE);
        linechartCardView.setVisibility(View.GONE);*/
        //llemptyrank.setVisibility(View.GONE);
    }

    private void OnlineLoad() {
        Glide.with(mContext)
                .load(BusinessActivity.rankAndVolumeList.getCurrentRankIcon()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.nebula_effect)
                .into(currentRankImageButton);


        getCurrentRankChart(BusinessActivity.rankAndVolumeList);


        Glide.with(mContext)
                .load(BusinessActivity.rankAndVolumeList.getNextEligibleRankIcon()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.nebula_effect)
                .into(nextEligibleImageButton);


        currentRankMyTextView.setText(BusinessActivity.rankAndVolumeList.getCurrentRank());
        Config.RANK_ID = BusinessActivity.rankAndVolumeList.getCurrentRank();
        getCurrentRankChart(BusinessActivity.rankAndVolumeList);

        Log.d("RANK==", "OnlineLoad: " + BusinessActivity.rankAndVolumeList.getNextEligibleRank());
        nextRankMyTextView.setText(BusinessActivity.rankAndVolumeList.getNextEligibleRank());

        getNextRankChart(BusinessActivity.rankAndVolumeList);

/*
        barchartCardView.setVisibility(View.GONE);
        progressbarCardView.setVisibility(View.GONE);*/
        getCurrentRank(BusinessActivity.rankAndVolumeList.getCurrentRank());
        showRecordsStar();
    }

    private void getCurrentRank(String currentRank) {
        Log.i("INFO", "index : " + index);
        if (currentRank.equalsIgnoreCase("Associate")) {
            index = 0;

        } else if (currentRank.equalsIgnoreCase("IBO")) {
            index = 1;

        } else if (currentRank.equalsIgnoreCase("Bronze")) {
            index = 2;

        } else if (currentRank.equalsIgnoreCase("Silver")) {
            index = 3;

        } else if (currentRank.equalsIgnoreCase("Gold")) {
            index = 4;

        } else if (currentRank.equalsIgnoreCase("Platinum")) {
            index = 5;

        } else if (currentRank.equalsIgnoreCase("Star Platinum")) {
            index = 6;

        } else if (currentRank.equalsIgnoreCase("2 Star Platinum")) {
            index = 7;

        } else if (currentRank.equalsIgnoreCase("3 Star Platinum")) {
            index = 8;

        } else if (currentRank.equalsIgnoreCase("4 Star Platinum")) {
            index = 9;

        } else if (currentRank.equalsIgnoreCase("5 Star Platinum")) {

            index = 10;
        }
       /* else if (currentRank.equalsIgnoreCase("6 Star Platinum")) {

            index = 11;
        }
        else if (currentRank.equalsIgnoreCase("7 Star Platinum")) {

            index = 12;

        } else if (currentRank.equalsIgnoreCase("Crown")) {

            index = 13;
        }*/
        else if (currentRank.equalsIgnoreCase("Brand Ambassador")) {

            index = 11;
        } else if (currentRank.equalsIgnoreCase("Universal Brand Ambassador")) {

            index = 12;

        }

        Log.i("INFO", "index  after: " + index);
        myIncomeShareRank.setVisibility(View.VISIBLE);
        myIncomeShareRank.setImageResource(Config.NewrankImages[index]);
        tvId.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.VISIBLE);
    }

    void getCurrentRankChart(RankAndVolumeList rankAndVolumeList_) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(rankAndVolumeList_.getLargestLegNV(), 0));
        entries.add(new BarEntry(rankAndVolumeList_.getSecondLargestLegNV(), 1));
        entries.add(new BarEntry(rankAndVolumeList_.getThirdPlusLegNV(), 2));

        BarDataSet set = new BarDataSet(entries, "NV (Nebula Volume)");

        if (mContext != null && isAdded()) {
            set.setColor(getResources().getColor(R.color.GREEN));
        }


        ArrayList<String> labels = new ArrayList<>();
        labels.add("Leg 1");
        labels.add("Leg 2");
        labels.add("Leg 3+");

        BarData data = new BarData(labels, set);

        currentRankBarChart.setData(data);
        currentRankBarChart.animateY(2000);
        currentRankBarChart.setDescriptionColor(Color.WHITE);
        currentRankBarChart.setDrawBarShadow(false);
        currentRankBarChart.setDrawValueAboveBar(true);
        currentRankBarChart.setPinchZoom(false);
        currentRankBarChart.setDrawGridBackground(false);
        currentRankBarChart.setDescription("");
        currentRankBarChart.setNoDataText("");
        currentRankBarChart.setNoDataTextDescription("");
        currentRankBarChart.getAxisLeft().setDrawGridLines(false);
        currentRankBarChart.getXAxis().setDrawGridLines(false);
        currentRankBarChart.setClickable(false);
        currentRankBarChart.setFocusable(false);
        currentRankBarChart.setFocusableInTouchMode(false);
        currentRankBarChart.setTouchEnabled(false);


        currentRankBarChart.getBarData().setValueTextSize(10f);
        currentRankBarChart.getBarData().setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> formatter.format(value));

        //show YAxis right side
        YAxis yl = currentRankBarChart.getAxisRight();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);

        YAxis y2 = currentRankBarChart.getAxisLeft();
        y2.setValueFormatter((value, yAxis) -> formatter.format(value));
        //show XAxis BOTTOM side
        XAxis xl = currentRankBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        // xl.setTypeface(customFont);
        //Zoom false for show bar chart
        currentRankBarChart.setPinchZoom(false);
        currentRankBarChart.setDoubleTapToZoomEnabled(false);
        currentRankBarChart.setClickable(false);
        //bar chart get Value in Selected
        currentRankBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                /*Log.i("VAL SELECTED", "Value: " + e.getVal() + ", xIndex: " + e.getXIndex() +" ,Data: "+e.getData() + " ,DataSet index: " + h.getDataSetIndex());*/
            }

            public void onNothingSelected() {
            }
        });
        currentRankBarChart.invalidate();
    }

    void getNextRankChart(RankAndVolumeList rankAndVolumeList_) {
        leg1ProgressBar.setProgress(rankAndVolumeList_.getAlphaPercent());
        leg2ProgressBar.setProgress(rankAndVolumeList_.getBetaPercent());
        leg3ProgressBar.setProgress(rankAndVolumeList_.getGammaPercent());

        leg1ProgressMyTextView.setText(rankAndVolumeList_.getAlphaPercent() + "%");
        leg2ProgressMyTextView.setText(rankAndVolumeList_.getBetaPercent() + "%");
        leg3ProgressMyTextView.setText(rankAndVolumeList_.getGammaPercent() + "%");

        count1MyTextView.setText(formatter.format(rankAndVolumeList_.getAlpha()) + " / " + formatter.format(rankAndVolumeList_.getNextAlpha()));
        count2MyTextView.setText(formatter.format(rankAndVolumeList_.getBeta()) + " / " + formatter.format(rankAndVolumeList_.getNextBeta()));
        count3MyTextView.setText(formatter.format(rankAndVolumeList_.getGamma()) + " / " + formatter.format(rankAndVolumeList_.getNextGamma()));
    }

    private ArrayList<String> setXAxisValuesRankHistory(ArrayList<RankHistoryList> list) {
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            xVals.add(SetGmtTime(list.get(i).getCreateOn()));
            Log.i("INFO", " Rank xvals: " + SetGmtTime(list.get(i).getCreateOn()));
        }
       /* xVals.add("08-12-2017");
        xVals.add("14-01-2018");
        xVals.add("28-05-2018");*/
        xVals.add("");
        return xVals;
    }

    private ArrayList<Entry> setYAxisValuesRankHistory(ArrayList<RankHistoryList> list) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(getRankIndex(list.get(i).getRank()), i));
            Log.i("INFO", " Rank yvals: " + list.get(i).getRank());
        }
        /*yVals.add(new Entry(12, 3));
        yVals.add(new Entry(13, 4));
        yVals.add(new Entry(14, 5));*/
        return yVals;
    }

    private void setDataRankHistory(ArrayList<RankHistoryList> arrayList) {
        ArrayList<String> xVals = setXAxisValuesRankHistory(arrayList);

        ArrayList<Entry> yVals = setYAxisValuesRankHistory(arrayList);

        LineDataSet set1, set2;

        // create a dataset and give it a type
        //set1 = new LineDataSet(yVals, "DataSet 1");
        set1 = new LineDataSet(yVals, "Rank");
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
        set1.setValueTextSize(10f);
        set1.setDrawFilled(true);

        //set2 = new LineDataSet(xVals, "DataSet 2");
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        YAxisValueFormatter custom = new RankValueFormatter();
        YAxis YAxis = rankHistoryLineChart.getAxisLeft();
        YAxis.setValueFormatter(custom);

       /* XAxis xAxis = rankHistoryLineChart.getXAxis();
        //xAxis.setSpaceBetweenLabels(1);
        //xAxis.setAxisLineWidth(3);
        //xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setGridLineWidth(3);*/

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = rankHistoryLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setAxisMaxValue(14445454);
        //  rankHistoryLineChart.getLineData().set
        // xAxis.set
        //rankHistoryLineChart.getXAxis().setLabelCount(5);
        //rankHistoryLineChart.getXAxis().setGranularity(1f);


        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        //mChart.setData(data);
        rankHistoryLineChart.invalidate();
        // set data
        rankHistoryLineChart.setData(data);

        rankHistoryLineChart.getLineData().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String appendix = "Associate";
                appendix = Config.rankValues[(int) value];

                return appendix;
            }
        });
    }

    int getRankIndex(String rank) {
        int index_ = -1;

        if (rank.equals("Associate")) {
            index_ = 0;
        } else if (rank.equals("IBO")) {
            index_ = 1;
        } else if (rank.equals("Bronze")) {
            index_ = 2;
        } else if (rank.equals("Silver")) {
            index_ = 3;
        } else if (rank.equals("Gold")) {
            index_ = 4;
        } else if (rank.equals("Platinum")) {
            index_ = 5;
        } else if (rank.equals("Star Platinum")) {
            index_ = 6;
        } else if (rank.equals("2 Star Platinum")) {
            index_ = 7;
        } else if (rank.equals("3 Star Platinum")) {
            index_ = 8;
        } else if (rank.equals("4 Star Platinum")) {
            index_ = 9;
        } else if (rank.equals("5 Star Platinum")) {
            index_ = 10;
        } else if (rank.equals("Brand Ambassador")) {
            index_ = 11;
        } else if (rank.equals("Universal Brand Ambassador")) {
            index_ = 12;
        }
        /*else if (rank.equals("6 Star Platinum")) {
            index_ = 11;
        } else if (rank.equals("7 Star Platinum")) {
            index_ = 12;
        } else if (rank.equals("Crown")) {
            index_ = 13;
        } else if (rank.equals("Noble Crown")) {
            index_ = 14;
        } else if (rank.equals("Royal Crown")) {
            index_ = 15;
        }*/

        return index_;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            rankHistoryLineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + rankHistoryLineChart.getLowestVisibleXIndex() + ", high: " + rankHistoryLineChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + rankHistoryLineChart.getXChartMin() + ", xmax: " + rankHistoryLineChart.getXChartMax() + ", ymin: " + rankHistoryLineChart.getYChartMin() + ", ymax: " + rankHistoryLineChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    void initError(View view) {
        llEmptyid = view.findViewById(R.id.llEmptyid);

        txtErrorTitle =  view.findViewById(R.id.txtErrorTitleid);
        txtSubMsg =  view.findViewById(R.id.txtErrorSubTitleid);
        txtRetry =  view.findViewById(R.id.txtRetryid);
        txtRetry.setOnClickListener(v -> {
            startRotateAnim(imgRank, txtRetry);
            getIdDetails(false);
        });
        imgError =  view.findViewById(R.id.imgError);
        txtErrorTitlerank =  view.findViewById(R.id.txtErrorTitlerank);
        txtSubMsgrank =  view.findViewById(R.id.txtErrorSubTitlerank);
        txtSubMsgrank.setVisibility(View.GONE);
        txtRetryrank =  view.findViewById(R.id.txtRetryrank);
        btnLogin = view.findViewById(R.id.btn_login);
        txtRetryrank.setOnClickListener(v -> {
            startRotateAnim(imgRefreshGraph, txtRetryrank);
            getRankAndVolumes();
        });
        btnLogin.setOnClickListener(v -> utils.openLoginDialog(getActivity(), utils.gotoMyBusiness));

    }

    void serviceUnavailableID() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        llEmptyid.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtSubMsg.setVisibility(View.GONE);
        layidData.setVisibility(View.GONE);
        lnnologin.setVisibility(View.GONE);
    }

    void serviceUnavailableRank() {
        txtErrorTitlerank.setText(R.string.error_service_unavailable);
        llemptyrank.setVisibility(View.VISIBLE);
        txtRetryrank.setVisibility(View.VISIBLE);
        txtSubMsgrank.setVisibility(View.GONE);
        lnrankdata.setVisibility(View.GONE);
        lnnologin.setVisibility(View.GONE);
    }

    void noInternetConnectionID() {
        txtErrorTitle.setText(R.string.error_title);
        txtSubMsg.setText(R.string.error_content);
        llEmptyid.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lnnologin.setVisibility(View.GONE);
        txtSubMsg.setVisibility(View.VISIBLE);
        layidData.setVisibility(View.GONE);
    }


    void noInternetConnectionStar() {
        txtErrorTitlerank.setText(R.string.error_title);
        txtSubMsgrank.setText(R.string.error_content);
        txtRetryrank.setText(R.string.error_retry);
        lnnologin.setVisibility(View.GONE);
        llemptyrank.setVisibility(View.VISIBLE);
        txtRetryrank.setVisibility(View.VISIBLE);
        txtSubMsgrank.setVisibility(View.VISIBLE);
        lnrankdata.setVisibility(View.GONE);
    }


    void noLogin() {
        hideProgress();
        llemptyrank.setVisibility(View.GONE);
        lnrankdata.setVisibility(View.GONE);
        lnnologin.setVisibility(View.VISIBLE);
    }

    /*private void openLogin() {
        Intent login = new Intent(getActivity(), LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        getActivity().finish();
    }*/

    void showRecordsID() {
        llEmptyid.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        layidData.setVisibility(View.VISIBLE);
    }

    void showRecordsStar() {
        llemptyrank.setVisibility(View.GONE);
        txtRetryrank.setVisibility(View.GONE);
        lnrankdata.setVisibility(View.VISIBLE);
        lnnologin.setVisibility(View.GONE);
    }

    @Override
    public void onOtpCompleted(String otp) {
        //Toast.makeText(getActivity(), "OnOtpCompletionListener called", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint({"NewApi", "MissingPermission"})
    private void firebaseTokenGenerated(String user) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                TokenKey = FirebaseInstanceId.getInstance().getToken();
                DeviceId = Build.SERIAL;
                IMEI1 = tManager.getDeviceId(0);
                IMEI2 = tManager.getDeviceId(1);
                Config.REGISTERED_TOKEN = FirebaseInstanceId.getInstance().getToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

/*
    private void GetNotificationToken(String tokenKey, String deviceId, String imei1, String imei2, String User) {

        Call<JsonObject> wsCallingToken = BusinessActivity.mAPIInterface.postTokenKey(tokenKey, deviceId, imei1, imei2, User);
        wsCallingToken.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("INFO", "Token new Send IBO:-" + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
            }
        });
    }
*/


    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            if (Build.VERSION.SDK_INT > 24) {
                bmpUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                bmpUri = Uri.fromFile(file);
            }
            Log.i("INFO", "Call for bmpUri:-" + bmpUri);
            //bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public void onResume() {
        super.onResume();
        imgRankShare.setEnabled(true);
        imgRankShare.setClickable(true);
    }
}
package com.nebulacompanies.ibo.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ContactUs;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.activities.EcomPromosActivityWeb;
import com.nebulacompanies.ibo.activities.MyDownlineWeb;
import com.nebulacompanies.ibo.activities.MyIncomeActivityWeb;
import com.nebulacompanies.ibo.activities.NewCompanyEvent;
import com.nebulacompanies.ibo.activities.RegistrationUpdateKYCWebview;
import com.nebulacompanies.ibo.activities.SiteProductsWeb;
import com.nebulacompanies.ibo.adapter.MockPagerBannerAdapter;
import com.nebulacompanies.ibo.customBanner.CirclePageIndicator;
import com.nebulacompanies.ibo.customBanner.InfinitePagerAdapter;
import com.nebulacompanies.ibo.customBanner.InfiniteViewPager;
import com.nebulacompanies.ibo.dwarkaPackage.DwarkaDashBoardActivity;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextView;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.BanerText;
import com.nebulacompanies.ibo.model.BanersTextList;
import com.nebulacompanies.ibo.model.GetIboDetails;
import com.nebulacompanies.ibo.model.ProjectBaners;
import com.nebulacompanies.ibo.model.ProjectBanersList;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextViewDesign;
import com.nebulacompanies.ibo.view.MyTextViewDesignBold;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.SetDateFormat.SetRegistationDateFormat;
/**
 * Created by Palak Mehta on 15-Feb-18.
 */
public class DashboardFragment extends Fragment {

    private final ArrayList<BanersTextList> banersTextList = new ArrayList<>();
    private final ArrayList<ProjectBanersList> projectbanersList = new ArrayList<>();
    MockPagerBannerTextAdapter pagerBannerTextAdapter;
    MockPagerBannerAdapter adapters;
    RelativeLayout llSettings;
    LinearLayout rlEcom, rlPackage, rlBusiness, rlIncome, rlPromotions, rlDownline, lydetails, lyNologin;
    Session session;
    private APIInterface mAPIInterface;
    public DashboardActivity mActivity;
    InfiniteViewPager mViewPager3, mViewPager2;
    SharedPreferences prefss;
    CirclePageIndicator mCircleIndicator;
    MyTextViewDesignBold txtvwUser;
    ImageView imgFB, imgYb;
    MyButtonEcom btnLogin;
    MyTextViewDesign tvConatctUs;
    Utils utils = new Utils();

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }
    private final BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(Utils.actionLogin)) {
                setData();
                getBannersTextList();
                getBannersList();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard_design_change, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        mActivity = (DashboardActivity) getActivity();
        prefss = PreferenceManager.getDefaultSharedPreferences(getActivity());
        session = new Session(Objects.requireNonNull(getActivity()));
        init(view);
        checkFirstRun();
        setData();
        getBannersTextList();
        getBannersList();
        return view;
    }

    private void setData() {
        if (!session.getLogin()) {
            lydetails.setVisibility(View.VISIBLE);
            lyNologin.setVisibility(View.GONE);
            txtvwUser.setText("");
        } else {
            lydetails.setVisibility(View.VISIBLE);
            txtvwUser.setText(session.getLoginID());
            lyNologin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    IntentFilter intentFilter;

    void init(View view) {
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        rlEcom = view.findViewById(R.id.ln_nebulacare);
        rlPackage = view.findViewById(R.id.ln_holiday_dwarka);
        rlBusiness = view.findViewById(R.id.ln_my_business);
        rlIncome = view.findViewById(R.id.ln_my_income);
        rlPromotions = view.findViewById(R.id.ln_promotions);
        rlDownline = view.findViewById(R.id.ln_my_downline);
        lydetails = view.findViewById(R.id.ly_details);
        lyNologin = view.findViewById(R.id.ly_no_login);
        btnLogin = view.findViewById(R.id.btn_login);
        mViewPager3 = view.findViewById(R.id.viewpager3);
        mViewPager2 = view.findViewById(R.id.viewpager2);
        mCircleIndicator = view.findViewById(R.id.indicator2);
        txtvwUser = view.findViewById(R.id.textUser);
        imgFB = view.findViewById(R.id.img_fb);
        imgYb = view.findViewById(R.id.img_yb);
        tvConatctUs = view.findViewById(R.id.tv_contact_us);

        intentFilter = new IntentFilter();
        intentFilter.addAction(DashboardActivity.SUCCESSDATA);
        intentFilter.addAction(Utils.actionLogin);
        (getActivity()).registerReceiver(rankReceiver, intentFilter);

        rlPackage.setOnClickListener(v -> {
            Intent cp = new Intent(getActivity(), DwarkaDashBoardActivity.class);
            cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(cp);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            BusinessActivity.clearTab();
        });

        rlEcom.setOnClickListener(v -> {
            Intent cp = new Intent(getActivity(), MainActivity.class);
            cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            cp.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            cp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(cp);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            BusinessActivity.clearTab();
        });

        imgFB.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                String url = "https://www.facebook.com/nebulacompanies/";
                Uri uri = Uri.parse(url);
                try {
                    ApplicationInfo applicationInfo = getActivity().getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                    if (applicationInfo.enabled) {
                        uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        imgYb.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/nebulacompanies?sub_confirmation=1"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        tvConatctUs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactUs.class);
            intent.putExtra("showSpotLightView", true);
            startActivity(intent);
        });
        rlBusiness.setOnClickListener(v -> {
            Intent cp = new Intent(getActivity(), BusinessActivity.class);
            cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(cp);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            BusinessActivity.clearTab();
        });

        rlIncome.setOnClickListener(v -> {
            if (session.getLogin()) {
                Intent cp = new Intent(getActivity(), MyIncomeActivityWeb.class);
                cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cp);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            } else {
                utils.openNoSession(getActivity(), utils.gotoIncome);
            }
            BusinessActivity.clearTab();
        });

        rlPromotions.setOnClickListener(v -> {
            if (session.getLogin()) {
                Intent cp = new Intent(getActivity(), EcomPromosActivityWeb.class);
                cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cp);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                BusinessActivity.clearTab();
            } else {
                utils.openNoSession(getActivity(), utils.gotoPromos);
            }
            BusinessActivity.clearTab();
        });
        rlDownline.setOnClickListener(v -> {
            if (session.getLogin()) {

                Intent cp = new Intent(getActivity(), MyDownlineWeb.class);
                cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cp);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            } else {
                utils.openNoSession(getActivity(), utils.gotoDownline);
            }
            BusinessActivity.clearTab();
        });
        llSettings = view.findViewById(R.id.llSettings);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(rankReceiver);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.d("onActivity", resultCode + " : dashboard frag : " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == utils.gotoMyBusiness) {
                rlBusiness.performClick();
            } else if (requestCode == utils.gotoDownline) {
                rlDownline.performClick();
            } else if (requestCode == utils.gotoPromos) {
                rlPromotions.performClick();
            } else if (requestCode == utils.gotoIncome) {
                rlIncome.performClick();
            }
        }
    }

    private void getIboDetails() {
        if (Utils.isNetworkAvailable(mActivity)) {
            Call<GetIboDetails> wsCallingGetIboDetails = mAPIInterface.getGetIboDetails(session.getLoginID());
            Log.i("getIboDetails", "getIboDetails code: " + wsCallingGetIboDetails);
            wsCallingGetIboDetails.enqueue(new Callback<GetIboDetails>() {
                @Override
                public void onResponse(@NotNull Call<GetIboDetails> call, @NotNull Response<GetIboDetails> response) {
                    if (response.isSuccessful()) {
                        Log.i("getIboDetails", "getIboDetails code: " + response);
                        if (response.code() == 200) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                long date = SetRegistationDateFormat(response.body().getData().getCreatedOn());
                                long ValiDate = 1555945980000L;
                                if (date > ValiDate) {

                                    if (response.body().getData().getGender().isEmpty() || response.body().getData().getAddress().isEmpty() || response.body().getData().getState().isEmpty() ||
                                            response.body().getData().getCity().isEmpty() || response.body().getData().getPincode().isEmpty()
                                            || response.body().getData().getAccountHolderName().isEmpty() || response.body().getData().getIFSCNo().isEmpty() ||
                                            response.body().getData().getBranchName().isEmpty() || response.body().getData().getBranchCity().isEmpty() || response.body().getData().getBankId() == null ||
                                            response.body().getData().getAccountNo().isEmpty() || response.body().getData().getMiddleName().isEmpty() || response.body().getData().getDOB().equals("01-01-1990")) {

                                        showOnClickProfileDialog();
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetIboDetails> call, @NotNull Throwable t) { }
            });
        }
    }


    private void showOnClickProfileDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        View alertView = getLayoutInflater().inflate(R.layout.alert_profile_check, null);
        //Set the view
        alert.setView(alertView);
        alert.setCancelable(true);
        //Show alert
        final AlertDialog alertDialog = alert.show();
        //Can not close the alert by touching outside.
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MyButtonEcom btnProfile = alertDialog.findViewById(R.id.btn_profile);
        assert btnProfile != null;
        btnProfile.setOnClickListener(v -> {
            Intent registations = new Intent(getActivity(), RegistrationUpdateKYCWebview.class);
            startActivity(registations);
            alertDialog.dismiss();
        });
    }


    public void checkFirstRun() {
        boolean isFirstRun = Objects.requireNonNull(getActivity()).getSharedPreferences(PrefUtils.prefIsfirstrun, MODE_PRIVATE).getBoolean(PrefUtils.prefIsfirstrun, true);
        if (isFirstRun) {
            // Place your dialog code here to display the dialog
            getIboDetails();
            getActivity().getSharedPreferences(PrefUtils.prefIsfirstrun, MODE_PRIVATE)
                    .edit()
                    .putBoolean(PrefUtils.prefIsfirstrun, false)
                    .apply();
        }
    }

    private void getBannersTextList() {
        Call<BanerText> wsCallingBanersTextList = mAPIInterface.getBanerTextList();
        wsCallingBanersTextList.enqueue(new Callback<BanerText>() {
            @Override
            public void onResponse(@NotNull Call<BanerText> call, @NotNull Response<BanerText> response) {
                if (response.isSuccessful()) {
                    banersTextList.clear();
                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            mViewPager3.setVisibility(View.VISIBLE);
                            //  rlSiteProgress.setVisibility(View.VISIBLE);
                            banersTextList.addAll(response.body().getData());
                            Log.d("size==", banersTextList.size() + " : ");
                            if(getActivity()!=null) {
                                pagerBannerTextAdapter = new MockPagerBannerTextAdapter(getActivity(), banersTextList);
                                mViewPager3.setAdapter(pagerBannerTextAdapter);
                                mViewPager3.setAutoScrollTime(6000);
                                mViewPager3.startAutoScroll();
                            }
                            SharedPreferences.Editor editor = prefss.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(banersTextList);
                            editor.putString("banersText", json);
                            editor.apply();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BanerText> call, @NotNull Throwable t) { }
        });
    }


    public class MockPagerBannerTextAdapter extends InfinitePagerAdapter {

        private final LayoutInflater mInflater;
        private final ArrayList<BanersTextList> mList;
        public MockPagerBannerTextAdapter(Activity activity, ArrayList<BanersTextList> banersTextList) {
            super();
            this.mList = banersTextList;
            mInflater = LayoutInflater.from(activity);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public View getView(final int position, View view, ViewGroup container) {
            MockPagerBannerTextAdapter.ViewHolder holder;
            if (view != null) {
                holder = (MockPagerBannerTextAdapter.ViewHolder) view.getTag();
            } else {
                view = mInflater.inflate(R.layout.card_layout_text, container, false);
                holder = new MockPagerBannerTextAdapter.ViewHolder(view);
                view.setTag(holder);
            }
            BanersTextList item = mList.get(position);
            holder.position = position;
            holder.appName.setText(item.getDescription());

            holder.rlBannerItem.setOnClickListener(view1 -> {

                if (llSettings.getVisibility() == View.VISIBLE) {
                    holder.rlBannerItem.setClickable(false);
                    holder.rlBannerItem.setEnabled(false);
                    mViewPager3.stopAutoScrollCustom();
                    mViewPager3.setPagingEnabled(false);
                    mViewPager3.setBackgroundColor(getResources().getColor(R.color.transparent));
                } else {
                    holder.rlBannerItem.setClickable(true);
                    holder.rlBannerItem.setEnabled(true);
                    mViewPager3.setPagingEnabled(true);
                    mViewPager3.setBackground(getResources().getDrawable(R.drawable.nebula_text_effect));
                    if (item.getCategory() != null) {
                        if (item.getCategory().equalsIgnoreCase("Site Progress")) {
                            Intent sp = new Intent(getActivity(), SiteProductsWeb.class);
                            sp.putExtra("keyPro", "SiteProducts");
                            sp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(sp);
                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }
                        if (item.getCategory().equalsIgnoreCase("Event")) {
                            Intent eve = new Intent(getActivity(), NewCompanyEvent.class);
                            eve.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(eve);
                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }
                        if (item.getCategory().equalsIgnoreCase("Video")) {
                            Uri uri = Uri.parse(item.getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setPackage("com.google.android.youtube");
                            startActivity(intent);
                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }
                        if (item.getCategory().equalsIgnoreCase("Weblink")) {
                            Uri uri = Uri.parse(item.getUrl());
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(browserIntent);
                        }
                        /*if (item.getCategory().equalsIgnoreCase("EDocuments")) {
                           *//* Intent eb = new Intent(getActivity(), NewEDocuments.class);
                            eb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(eb);
                            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*//*
                        }
                        if (item.getCategory().equalsIgnoreCase("Holidays")) {
                            *//*Intent eb = new Intent(getActivity(), BusinessActivity.class);
                            eb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(eb);
                            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*//*
                        }*/
                    }
                }
            });
            return view;
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        private class ViewHolder {
            public int position;
            private final MyTextView appName;
            private final RelativeLayout rlBannerItem;
            public ViewHolder(View v) {
                appName = v.findViewById(R.id.item_tv);
                rlBannerItem = v.findViewById(R.id.rl_banner_item_text);
            }
        }
    }

    private void getBannersList() {

        projectbanersList.clear();
        Type collectionType = new TypeToken<List<ProjectBanersList>>() {
        }.getType();
        Gson gson = new Gson();
        String data = prefss.getString("baners_List", null);
        Log.d("data::size", data + " : ");
        if (data != null) {
            projectbanersList.addAll(gson.fromJson(data, collectionType));
            if (getActivity() != null) {
                adapters = new MockPagerBannerAdapter(getActivity(), projectbanersList);
                mViewPager2.setAdapter(adapters);
                mViewPager2.setAutoScrollTime(6000);
                mViewPager2.startAutoScroll();
                mCircleIndicator.setViewPager(mViewPager2);
            }
        } else {
            Call<ProjectBaners> wsCallingBanersList = mAPIInterface.getProjectBanersList();
            wsCallingBanersList.enqueue(new Callback<ProjectBaners>() {
                @Override
                public void onResponse(@NotNull Call<ProjectBaners> call, @NotNull Response<ProjectBaners> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                projectbanersList.addAll(response.body().getData());
                                if (getActivity() != null) {
                                    adapters = new MockPagerBannerAdapter(getActivity(), projectbanersList);
                                    mViewPager2.setAdapter(adapters);
                                    mViewPager2.setAutoScrollTime(6000);
                                    mViewPager2.startAutoScroll();
                                    mCircleIndicator.setViewPager(mViewPager2);
                                }
                                SharedPreferences.Editor editor = prefss.edit();

                                String json = gson.toJson(response.body().getData(), collectionType);
                                editor.putString("baners_List", json);
                                Log.d("data::size json", json + " : ");
                                editor.apply();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProjectBaners> call, @NotNull Throwable t) { }
            });
        }
    }
/*
    Dialog dialog1;
    @SuppressLint("InflateParams")
    private void disclaimerPolicy() {
        LayoutInflater mInflater2 = (LayoutInflater)
                Objects.requireNonNull(getActivity()).getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        View convertView1;
        convertView1 = mInflater2.inflate(R.layout.term_condition_agreement, null);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);
        dialog1.setContentView(convertView1);
        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        Button btnAgreement =convertView1.findViewById(R.id.btn_agreement);
        btnAgreement.setTypeface(typeface);
        btnAgreement.setOnClickListener(v -> {
            Call<JSONObject> wsCallingPostSop = mAPIInterface.PostSop();
            wsCallingPostSop.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(@NotNull Call<JSONObject> call, @NotNull Response<JSONObject> response) {
                    if (response.isSuccessful()) {
                        Log.i("INFO", "RankAndVolumes code: " + response.message());
                    }
                }
                @Override
                public void onFailure(@NotNull Call<JSONObject> call, @NotNull Throwable t) {}
            });
            dialog1.dismiss();
        });
        if (!dialog1.isShowing())
            dialog1.show();
    }*/
}

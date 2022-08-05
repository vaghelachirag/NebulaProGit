package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.MockPagerBannerEcomCategoryAdapter;
import com.nebulacompanies.ibo.ecom.customBanner.CirclePageIndicator;
import com.nebulacompanies.ibo.ecom.customBanner.InfiniteViewPager;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.Baners;
import com.nebulacompanies.ibo.ecom.model.BanersList;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.Category;
import com.nebulacompanies.ibo.ecom.model.CategoryListModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.NewCategoryProductDetails;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.model.ShowMRPPriceModelEcom;
import com.nebulacompanies.ibo.ecom.model.SubCategoryProductList;
import com.nebulacompanies.ibo.ecom.model.TrendingProductModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryRequestModel;
import com.nebulacompanies.ibo.ecom.utils.AdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.ProductDecsDetailsDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.ProductDetailsCategoryDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.EndlessRecyclerViewScrollListener;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyBoldTextView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.nebulacompanies.ibo.ecom.utils.Utils.showReviews;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MyCategoryActivity extends AppCompatActivity implements AdapterCallback, ProductDetailsCategoryDialogFragment.ItemClickListener {

    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    ArrayList<MyCart> cartModels = new ArrayList<>();
    ArrayList<Category> categoryArrayList = new ArrayList<>();
   // ArrayList<SubCategoryProductList> categoryDetails = new ArrayList<>();
    ArrayList<SubCategoryProductList> categoryDetailsOrig = new ArrayList<>();
    ArrayList<SubCategoryProductList> categoryDetailsDisplay = new ArrayList<>();
    ArrayList<SubCategoryProductList> trendingproductLists = new ArrayList<>();
    ArrayList<SubCategoryProductList> newproductLists = new ArrayList<>();
    HashMap<String, ArrayList<String>> newHashmap = new HashMap<>();
    HashMap<String, ArrayList<String>> trendingHashmap = new HashMap<>();
    ArrayList<String> categoryPic = new ArrayList<>();
    ArrayList<Integer> categoryPrice = new ArrayList<>();
    ArrayList<Integer> categoryMRPPrice = new ArrayList<>();
    ArrayList<String> categoryPV = new ArrayList<>();
    ArrayList<String> categoryBV = new ArrayList<>();
    ArrayList<String> categoryNV = new ArrayList<>();
    ArrayList<String> categoryName = new ArrayList<>();
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    private final ArrayList<BanersList> banersList = new ArrayList<>();

    private CategoryDetailsAdapter categoryDetailsAdapter;
    MockPagerBannerEcomCategoryAdapter adapters;

    RelativeLayout rlSearchView;
    NebulaEditTextEcom editSearch;
    APIInterface mAPIInterface, mAPIInterfaceProfile;
    MyTextViewEcom tvHome, tvTrackOrder, tvAccount, tvSort,tvLocation,tvPvStatus,
            tvToolbarTitle,tvCartBadge;
    ImageView imgFav, imgSearch, imgSearchClose,imgCart, imgMainBack,imgMyAccount, imgHome, imgTrackOrder;
    MyButtonEcom btnBottomSheet;
    RadioGroup radioGroup;
    RadioButton checkedRadioButton, savedCheckedRadioButton;
    FloatingActionButton fabsort;
    RelativeLayout relcontent,rlbottomcontent;
    RecyclerView recyclerView;
    public RelativeLayout lnCart;
    Session session;
    com.nebulacompanies.ibo.util.Session sessionNormal;
    LinearLayout lnHome, lnAccount, lnTrackOrder,loclayout;
    Spinner spinnerSort;
    LinearLayoutManager mLayoutManager;
    CirclePageIndicator mCircleIndicatorEcom;
    NestedScrollView nestedScrollView;
    InfiniteViewPager mViewPagerEcom;
    Handler handler;
    String deviceId,uniqueID,userName,sortType,addressNameSave, cityFacility;
    int categoryIDTwo = 1,selectCategory,selPos = 0,cityId;
    boolean mrpisAssociate, mrpisFirstPurchase,isAddressType;
    SharedPreferences SharedPreferencesLocationName, citySave, sharedPreferencesAddressType,
            sharedPreferencesFacility , spGetIsAssociate,sharedPreferences;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    Intent categoryDetailss;
    Integer pvResult;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MaterialProgressBar mProgressDialog;
    Typeface typeface;
    UtilsVersion utilsVersion = new UtilsVersion();
    Utils utils;

    private RelativeLayout llEmpty;
    private ImageView imgError;
    private MyTextViewEcom txtRetry, txtErrorContent;
    MyBoldTextViewEcom txtErrorTitle;
    IntentFilter filterLogin;

    @SuppressLint({"ClickableViewAccessibility", "HardwareIds"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_category);

        utils = new Utils();
        Utils.myPromocode.clear();
        Utils.promoAPIcall = false;
        session = new Session(this);
        sessionNormal = new com.nebulacompanies.ibo.util.Session(this);
        mAPIInterface = APIClient.getClient(MyCategoryActivity.this).create(APIInterface.class);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        mProgressDialog =findViewById(R.id.progresbar);
        handler = new Handler();
        mSwipeRefreshLayout =findViewById(R.id.swipe_refresh_layout);
        categoryDetailss = getIntent();

        if (categoryDetailss != null) {
            categoryIDTwo = categoryDetailss.getIntExtra("selid", 1);
            String jsondata = categoryDetailss.getStringExtra("data");
            selPos = categoryDetailss.getIntExtra("selpos", 0);
            if (!TextUtils.isEmpty(jsondata)) {
                Type collectionType = new TypeToken<ArrayList<Category>>() {}.getType();
                Gson gson = new Gson();
                ArrayList<Category> marr = gson.fromJson(jsondata, collectionType);
                categoryArrayList.addAll(marr);
            }
        }

        filterLogin = new IntentFilter(Utils.actionLogin);
        registerReceiver(myReceiver, filterLogin);
        utilsVersion.checkVersion(this);

        initProgress();
        initSharedPref();
        LoadPreferences(false);
        initUI();
        initClickEvent();
        setSession();
        setPromptSpinner();
        callAPI();
        setAddress();
    }
    //The BroadcastReceiver that listens for bluetooth broadcasts
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase(Utils.actionLogin)) {
                setSession();
                callAPI();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    @SuppressLint("SetTextI18n")
    private void setAddress() {
        SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
        addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

        sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, 0);
        isAddressType = sharedPreferencesAddressType.getBoolean(PrefUtils.prefAddresstype, false);

        if (!TextUtils.isEmpty(addressNameSave)) {
            tvLocation.setText((isAddressType ? "Pickup from " : "Deliver to ") + addressNameSave);
        }

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

        Log.d("CITY_IDR ", "CITY_IDR " + cityId);
    }

    ProgressDialog mLoadProgressDialog;

    private void initProgress() {
        mLoadProgressDialog = new ProgressDialog(MyCategoryActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    @SuppressLint("SetTextI18n")
    private void setSession() {
        if (session.getLogin()) {
            if (userName == null || userName.equals("")) {
                getMyProfile();
                Log.d("UserName Fill", "UserName Fill " + userName);
            } else {
                Log.d("UserName Empty", "UserName Empty " + userName);
                tvToolbarTitle.setText("Hi, " + userName);
            }
        } else {
            tvToolbarTitle.setText("Hi, Guest");
        }
    }

    private void setPromptSpinner() {
        spinnerSort.setPrompt(sortType);

        List<String> categories = new ArrayList<>();
        categories.add("Price: Low to High");
        categories.add("Price: High to Low");
        categories.add("A - Z");
        categories.add("Z - A");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_textview_ecom, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_textview_ecom);
        // attaching data adapter to spinner
        spinnerSort.setAdapter(dataAdapter);
        categories.add(0, "Sort By");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickEvent() {
        tvLocation.setOnClickListener(view -> {
            ProductDetailsCategoryDialogFragment productDetailsCategoryDialogFragment =
                    ProductDetailsCategoryDialogFragment.newInstance();
            productDetailsCategoryDialogFragment.show(getSupportFragmentManager(),
                    ProductDecsDetailsDialogFragment.TAG);
            productDetailsCategoryDialogFragment.setCancelable(true);
        });

        lnHome.setOnClickListener(v -> {
            imgHome.setColorFilter(ContextCompat.getColor(MyCategoryActivity.this, R.color.black));
            tvHome.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(MyCategoryActivity.this, MainActivity.class);

            login.putExtra("SELECTEDINNERHOME", 7);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        lnAccount.setOnClickListener(v -> {
            imgMyAccount.setColorFilter(ContextCompat.getColor(MyCategoryActivity.this, R.color.black));
            tvAccount.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(MyCategoryActivity.this, MainActivity.class);
            login.putExtra("SELECTEDINNERACCOUNT", 8);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        lnTrackOrder.setOnClickListener(v -> {
            imgTrackOrder.setColorFilter(ContextCompat.getColor(MyCategoryActivity.this, R.color.black));
            tvTrackOrder.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(MyCategoryActivity.this, MainActivity.class);
            login.putExtra("SELECTEDINNERTRACKORDER", 9);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        String value = Objects.requireNonNull(editSearch.getText()).toString();
        imgSearchClose.setOnClickListener(view -> {
            editSearch.getText().clear();
        });

        imgSearch.setOnClickListener(view -> rlSearchView.setVisibility(View.VISIBLE));

        imgCart.setOnClickListener(view -> {
            Intent login = new Intent(MyCategoryActivity.this, MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(login, utils.productShare);
        });
        imgMainBack.setOnClickListener(view -> onBackPressed());

        mSwipeRefreshLayout.setOnRefreshListener(
                this::refreshContent
        );

        editSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent searchIntent = new Intent(MyCategoryActivity.this, SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.fade_in_new, R.anim.fade_out_new);
                return true;
            }
            return false;
        });

        txtRetry.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(MyCategoryActivity.this)) {
                llEmpty.setVisibility(View.GONE);
                callAPI();
            }
        });
    }

    private void callAPI() {
        getMyCartListTotalCount(deviceId, session.getIboKeyId());
        getMyCartListPV(deviceId, session.getIboKeyId());
        getMRPPrice();
        fetchCategorylist();
        getInventory();
        setCategoryAdapter();
        loadFirstPage(categoryIDTwo, cityId, false);
       // if (showTrendingnew) {
            getTrendingProduct();
            getNewProduct();
       // }
        llEmpty.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    private void initUI() {

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        lnCart = toolbar.findViewById(R.id.toolbar_settings);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgFav = toolbar.findViewById(R.id.img_my_fav);
        imgCart = toolbar.findViewById(R.id.img_my_cart);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        tvCartBadge = toolbar.findViewById(R.id.cart_badge);
        imgSearchClose =  toolbar.findViewById(R.id.img_search_close);
        editSearch =  toolbar.findViewById(R.id.editMobileNo);
        nestedScrollView =  findViewById(R.id.nested_scroll_category);
        lnHome =  findViewById(R.id.ln_home);
        lnTrackOrder =  findViewById(R.id.ln_myorder);
        lnAccount =  findViewById(R.id.ln_my_account);
        loclayout = findViewById(R.id.ln_location);
        relcontent = findViewById(R.id.laycontent);
        imgMyAccount =  findViewById(R.id.img_my_account);
        imgHome =  findViewById(R.id.img_home);
        imgTrackOrder =  findViewById(R.id.img_order);
        fabsort = findViewById(R.id.fab_sort);
        tvLocation =  findViewById(R.id.tv_location);
        tvPvStatus =  findViewById(R.id.tv_pv_status);
        tvHome =  findViewById(R.id.tv_home);
        tvAccount =  findViewById(R.id.tv_my_account);
        tvTrackOrder =  findViewById(R.id.tv_my_order);
        tvSort =  findViewById(R.id.tv_sort);
        btnBottomSheet =  findViewById(R.id.btn_bottom_sheet);
        spinnerSort = findViewById(R.id.spinner_sort);
        rlbottomcontent = findViewById(R.id.rl_bottom_sheet_category);
        recyclerView =  findViewById(R.id.recyclerview_best_deals);
        llEmpty =  findViewById(R.id.llEmpty);
        imgError =  findViewById(R.id.imgError);
        txtErrorTitle =  findViewById(R.id.txtErrorTitle);
        txtErrorContent =  findViewById(R.id.txt_error_content);
        txtRetry =  findViewById(R.id.txtRetry);

        tvSort.setOnClickListener(v -> alertBoxSort());
        fabsort.setOnClickListener(v -> alertBoxSort());
        rlbottomcontent.setVisibility(session.getLogin() ? View.VISIBLE : View.GONE);

       // if (!showLocation) {
            loclayout.setVisibility(View.GONE);
        /*} else {
            if (!TextUtils.isEmpty(addressNameSave))
                tvLocation.setText(isAddressType ? "Pickup from " : "Deliver to " + addressNameSave);
        }*/
    }

    @SuppressLint("HardwareIds")
    private void initSharedPref() {

        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
        addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

        sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, 0);
        isAddressType = sharedPreferencesAddressType.getBoolean(PrefUtils.prefAddresstype, false);

        sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, 0);
        cityFacility = sharedPreferencesFacility.getString(PrefUtils.prefFacility, null);

        spGetIsAssociate = getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyCategoryActivity.this);

        sharedPreferences.edit().putInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0).apply();

        if (deviceId == null || deviceId.equals("")) {
            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }

        //SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefUsername, 0);
        userName = sessionNormal.getUserName();// SharedPreferencesUserName.getString(PrefUtils.prefUsername, null);
    }

    private void fetchCategorylist() {
        if (categoryArrayList.size() == 0) {
            Call<CategoryListModelEcom> wsCallingEvents = mAPIInterface.getCategoryListEcom();
            wsCallingEvents.enqueue(new Callback<CategoryListModelEcom>() {
                @Override
                public void onResponse(@NotNull Call<CategoryListModelEcom> call, @NotNull Response<CategoryListModelEcom> response) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        categoryArrayList.clear();
                        categoryArrayList.addAll(response.body().getData());
                        //  categoryArrayList.addAll(response.body().getData());
                        for (int i = 0; i < categoryArrayList.size(); i++) {
                            categoryArrayList.get(i).setSetSelected(i == 0);
                            if (i == 0)
                                categoryIDTwo = categoryArrayList.get(i).getProductID();
                        }
                        //  setCategoryAdapter();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CategoryListModelEcom> call, @NotNull Throwable t) { }
            });
        }
    }

    private void setCategoryAdapter() {
        Log.d("load==","setCategoryAdapter call");
        categoryDetailsAdapter = new CategoryDetailsAdapter(MyCategoryActivity.this, categoryDetailsDisplay, MyCategoryActivity.this);
        mLayoutManager = new LinearLayoutManager(MyCategoryActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(null);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(categoryDetailsAdapter);
        setFilterSortby(false);
        //notifyFilterList(myListdata);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }


    private void getTrendingProduct() {
        if (Utils.isNetworkAvailable(getApplication())) {

            Call<TrendingProductModel> wsCallingEvents = mAPIInterface.getTrendingProduct();
            wsCallingEvents.enqueue(new Callback<TrendingProductModel>() {
                @Override
                public void onResponse(@NotNull Call<TrendingProductModel> call, @NotNull Response<TrendingProductModel> response) {
                    if (response.isSuccessful() || response.code() == 200) {
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            trendingproductLists.clear();
                            trendingproductLists.addAll(response.body().getData());
                            trendingHashmap.clear();

                            for (int i = 0; i < trendingproductLists.size(); i++) {
                                int categoryid = trendingproductLists.get(i).getCategoryCategoryId();
                                String catId = String.valueOf(categoryid);
                                String productId = String.valueOf(trendingproductLists.get(i).getCategoryProductId());
                                if (trendingHashmap.containsKey(catId)) {
                                    ArrayList<String> sublist;
                                    sublist = trendingHashmap.get(catId);
                                    sublist.add(productId);
                                    trendingHashmap.put(catId, sublist);
                                } else {
                                    ArrayList<String> sublist = new ArrayList<>();
                                    sublist.add(productId);
                                    trendingHashmap.put(catId, sublist);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<TrendingProductModel> call, Throwable t) {
                }
            });
        } else {
            //noRecordsFound();
        }
    }


    private void getNewProduct() {

        Call<TrendingProductModel> wsCallingEvents = mAPIInterface.getNewProduct();
        wsCallingEvents.enqueue(new Callback<TrendingProductModel>() {
            @Override
            public void onResponse(Call<TrendingProductModel> call, Response<TrendingProductModel> response) {
                if (response.isSuccessful() || response.code() == 200) {
                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                        newproductLists.clear();
                        newproductLists.addAll(response.body().getData());
                        newHashmap.clear();
                        for (int i = 0; i < newproductLists.size(); i++) {
                            int categoryid = newproductLists.get(i).getCategoryCategoryId();
                            String catId = String.valueOf(categoryid);
                            String productId = String.valueOf(newproductLists.get(i).getCategoryProductId());
                            if (newHashmap.containsKey(catId)) {
                                ArrayList<String> sublist;
                                sublist = newHashmap.get(catId);
                                sublist.add(productId);
                                newHashmap.put(catId, sublist);
                            } else {
                                ArrayList<String> sublist = new ArrayList<>();
                                sublist.add(productId);
                                newHashmap.put(catId, sublist);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TrendingProductModel> call, Throwable t) {
            }
        });
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            loadFirstPage(categoryIDTwo, cityId, false);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    void noRecordsFound() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtRetry.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        if (selectCategory == 11) {
            Intent cartReview = new Intent(MyCategoryActivity.this, MainActivity.class);
            startActivity(cartReview);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @Override
    public void onMethodCallbackMain() {
        getMyCartListTotalCount(deviceId, session.getIboKeyId());
        getMyCartListPV(deviceId, session.getIboKeyId());
        loadFirstPage(categoryIDTwo, cityId, true);
        Log.e("selectCategoryID", "selectCategoryID " + categoryIDTwo);
    }

    private void getMRPPrice() {
        mAPIInterface = APIClient.getClient(MyCategoryActivity.this).create(APIInterface.class);
        Call<ShowMRPPriceModelEcom> wsCallingEvents = mAPIInterface.getMRPPriceEcom();
        wsCallingEvents.enqueue(new Callback<ShowMRPPriceModelEcom>() {
            @Override
            public void onResponse(Call<ShowMRPPriceModelEcom> call, Response<ShowMRPPriceModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("MRPPRICEAPI", "MRPPRICEAPI: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            Log.d("MRPPRICEResponse", "MRPPRICEResponse: " + new Gson().toJson(response.body()));
                            boolean isAssociate = response.body().getData().isAssociate();
                            boolean isFirstPurchase = response.body().getData().isFirstPurchase();
                            boolean isjoined30Days = response.body().getData().isJoined30Days();
                            mrpisAssociate = isAssociate;
                            mrpisFirstPurchase = isFirstPurchase;
                            boolean mrpisJoined30Days = isjoined30Days;
                            Log.d("isAssociate", "isAssociate: " + isAssociate);
                            Log.d("isFirstPurchase", "isFirstPurchase: " + isFirstPurchase);
                            Log.d("isjoined30Days", "isjoined30Days: " + isjoined30Days);

                            //Associate Save
                            SharedPreferences pfIsAssociate = getSharedPreferences(PrefUtils.prefMrp, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edtIsAssociate = pfIsAssociate.edit();
                            edtIsAssociate.putBoolean(PrefUtils.prefMrp, mrpisAssociate);
                            edtIsAssociate.apply();
                            Log.d("SharedisAssociate", "SharedisAssociate: " + mrpisAssociate);
                            Log.d("SharedisAssociate", "SharedisAssociate: " + isAssociate);

                            //First Purchase Save
                            SharedPreferences pfIsFirstPurchase = getSharedPreferences(PrefUtils.prefFirstpurchase, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edtIsFirstPurchase = pfIsFirstPurchase.edit();
                            edtIsFirstPurchase.putBoolean(PrefUtils.prefFirstpurchase, mrpisFirstPurchase);
                            edtIsFirstPurchase.apply();
                            Log.d("SharedisFirstPurchase", "SharedisFirstPurchase: " + mrpisFirstPurchase);
                            Log.d("SharedisFirstPurchase", "SharedisFirstPurchase: " + isFirstPurchase);

                            //Join 30 Days
                            SharedPreferences pfJoin30Days = getSharedPreferences(PrefUtils.prefJoindays, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edtIspfJoin30Days = pfJoin30Days.edit();
                            edtIspfJoin30Days.putBoolean(PrefUtils.prefJoindays, mrpisJoined30Days);
                            edtIspfJoin30Days.apply();
                            Log.d("SharedisJoin30Days", "SharedisJoin30Days: " + mrpisJoined30Days);
                            Log.d("SharedisJoin30Days", "SharedisJoin30Days: " + isjoined30Days);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }

                    }
                } else if (response.code() == 401) {
                    Log.d("MRPPRICEGETINData401", "MRPPRICEGETINData: " + new Gson().toJson(response.body()));
                    /*Intent dash = new Intent(getActivity(), LoginActivityEcom.class);
                    startActivity(dash);*/
                }
            }

            @Override
            public void onFailure(Call<ShowMRPPriceModelEcom> call, Throwable t) {
                Log.d("MRPPRICEAPIINERROR", "MRPPRICEAPIINERROR: " + t);
            }
        });
    }

    private void getMyProfile() {
        mAPIInterfaceProfile = APIClient.getClient(MyCategoryActivity.this).create(APIInterface.class);
        //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceProfile.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ProfileModelEcom> call, @NotNull Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        assert response.body() != null;
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                            userName = firstName;
                            tvToolbarTitle.setText("Hi, " + userName);
                            sessionNormal.setUserName(firstName);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }
                    }
                } else if (response.code() == 401) {
                    Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));
                    /*Intent dash = new Intent(getActivity(), LoginActivityEcom.class);
                    startActivity(dash);*/
                }
            }

            @Override
            public void onFailure(Call<ProfileModelEcom> call, Throwable t) {
                Log.d("PROFILEAPIINERROR", "PROFILEAPIINERROR: " + t);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        utils.checkExpireUser(mAPIInterface, this, session);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void SavePreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void alertBoxSort() {

        Dialog dialog = new Dialog(MyCategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialog_sort);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        // This is line that does all the magic
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        radioGroup = dialog.findViewById(R.id.radioGroup);
        RadioButton rbTrending = dialog.findViewById(R.id.rb_trending);
        RadioButton rbNew = dialog.findViewById(R.id.rb_new);
        //RadioButton rbAll = dialog.findViewById(R.id.rb_all);

        /*if (!showTrendingnew) {
            rbTrending.setVisibility(View.GONE);
            rbNew.setVisibility(View.GONE);
        }*/

        LoadPreferences(true);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            checkedRadioButton = radioGroup.findViewById(checkedId);
            int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);
            SavePreferences(KEY_SAVED_RADIO_BUTTON_INDEX, checkedIndex);
            setFilterSortby(true);
            dialog.dismiss();
        });

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void setFilterSortby(boolean fromFilter) {
        LoadPreferences(false);
        String msg = "";
        categoryDetailsDisplay.clear();

        switch (savedRadioIndex) {
            case 0: //All
                msg = "All products loading";
                Log.d("All", "All");
                Log.d("All Index", "checkedIndex" + savedRadioIndex);
                llEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                PreferenceManager.getDefaultSharedPreferences(MyCategoryActivity.this).edit().putString("LANG", "en").apply();
                categoryDetailsDisplay.addAll(categoryDetailsOrig);
               /* Collections.sort(categoryDetails, new Comparator<SubCategoryProductList>() {
                    @Override
                    public int compare(SubCategoryProductList o1, SubCategoryProductList o2) {
                        return o1.getCategorySalePrice() - o2.getCategorySalePrice();
                    }
                });*/
                notifyFilterList();

                break;

            case 1: //English
                msg = "Price from Low to High";
                Log.d("Price Low", "Price Low");
                Log.d("Price Low Index", "checkedIndex" + savedRadioIndex);
                llEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                categoryDetailsDisplay.addAll(categoryDetailsOrig);
                PreferenceManager.getDefaultSharedPreferences(MyCategoryActivity.this).edit().putString("LANG", "en").apply();
                Collections.sort(categoryDetailsDisplay, new Comparator<SubCategoryProductList>() {
                    @Override
                    public int compare(SubCategoryProductList o1, SubCategoryProductList o2) {
                        return o1.getCategorySalePrice() - o2.getCategorySalePrice();
                    }
                });
                notifyFilterList();

                break;
            case 2: //High Price
                msg = "Price from High to Low";
                Log.d("Price High", "Price High");
                llEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                categoryDetailsDisplay.addAll(categoryDetailsOrig);
                PreferenceManager.getDefaultSharedPreferences(MyCategoryActivity.this).edit().putString("LANG", "hi").apply();
                Collections.sort(categoryDetailsDisplay, new Comparator<SubCategoryProductList>() {
                    @Override
                    public int compare(SubCategoryProductList o1, SubCategoryProductList o2) {
                        return o2.getCategorySalePrice().compareTo(o1.getCategorySalePrice());
                    }
                });
                notifyFilterList();
                break;
            case 3: //A-Z
                msg = "Sort by A - Z";
                llEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                categoryDetailsDisplay.addAll(categoryDetailsOrig);
                PreferenceManager.getDefaultSharedPreferences(MyCategoryActivity.this).edit().putString("LANG", "te").apply();
                Collections.sort(categoryDetailsDisplay, new Comparator<SubCategoryProductList>() {
                    @Override
                    public int compare(SubCategoryProductList o1, SubCategoryProductList o2) {
                        return o1.getCategoryName().compareTo(o2.getCategoryName());

                    }
                });
                notifyFilterList();

                break;
            case 4: //Z-A
                msg = "Sort by Z - A";
                llEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                PreferenceManager.getDefaultSharedPreferences(MyCategoryActivity.this).edit().putString("LANG", "te").apply();
                categoryDetailsDisplay.addAll(categoryDetailsOrig);
                Collections.sort(categoryDetailsDisplay, Collections.reverseOrder());
                notifyFilterList();
                break;

            case 5: //Trending
                Log.d("Trending", "Trending");
                Log.d("Trending Index", "checkedIndex" + savedRadioIndex);
                if (Utils.isNetworkAvailable(getApplication())) {
                    ArrayList<String> sublist ;
                   // ArrayList<SubCategoryProductList> filterProducts = new ArrayList<>();
                    if (trendingHashmap.containsKey(String.valueOf(categoryIDTwo))) {
                        sublist = trendingHashmap.get(String.valueOf(categoryIDTwo));
                        Log.d("filterid", "" + sublist);
                        for (int i = 0; i < categoryDetailsOrig.size(); i++) {
                            String prodid = String.valueOf(categoryDetailsOrig.get(i).getCategoryProductId());
                            Log.d("filterid1", "" + prodid);
                            if (sublist.contains(prodid)) {
                                categoryDetailsDisplay.add(categoryDetailsOrig.get(i));
                                Log.d("filter2", "" + categoryDetailsDisplay);
                            }
                        }

                        Log.d("Trending", categoryIDTwo + " sublist " + categoryDetailsDisplay.size());
                    } else
                        Log.d("Trending", " no exist sublist " + categoryIDTwo);
                    if (categoryDetailsDisplay.size() > 0) {
                        //msg = "Trending products loading";
                        recyclerView.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.GONE);
                    }
                    //else msg="No Trending products in this category";
                    else {
                        recyclerView.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.VISIBLE);
                        txtErrorTitle.setVisibility(View.GONE);
                        txtErrorContent.setText("Please select another category");
                        txtErrorContent.setVisibility(View.VISIBLE);
                        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
                        txtRetry.setVisibility(View.VISIBLE);
                        txtRetry.setOnClickListener(v -> {
                            sharedPreferences.edit().putInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0).apply();
                            loadFirstPage(categoryIDTwo, cityId, false);
                        });
                    }
                    notifyFilterList();
                } else {
                    noInternetSort();
                }
                break;
            case 6: //New

                Log.d("New", "New");
                Log.d("New Index", "checkedIndex" + savedRadioIndex);
                if (Utils.isNetworkAvailable(getApplication())) {

                    ArrayList<String> sublistnew ;
                  //  ArrayList<SubCategoryProductList> filterProductsnew = new ArrayList<>();
                    if (newHashmap.containsKey(String.valueOf(categoryIDTwo))) {
                        sublistnew = newHashmap.get(String.valueOf(categoryIDTwo));
                        for (int i = 0; i < categoryDetailsOrig.size(); i++) {
                            String prodid = String.valueOf(categoryDetailsOrig.get(i).getCategoryProductId());
                            if (sublistnew.contains(prodid)) {
                                categoryDetailsDisplay.add(categoryDetailsOrig.get(i));
                            }
                        }
                        Log.d("New", categoryIDTwo + " sublist " + categoryDetailsDisplay.size());
                    } else
                        Log.d("New", " no exist sublist " + categoryIDTwo);
                    if (categoryDetailsDisplay.size() > 0) {
                        // msg="New products loading";
                        recyclerView.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.GONE);

                    } else {
                        //msg="No New products in this category";
                        recyclerView.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.VISIBLE);
                        txtErrorTitle.setVisibility(View.GONE);
                        txtErrorContent.setText("Please select another category");
                        txtErrorContent.setVisibility(View.VISIBLE);
                        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
                        txtRetry.setVisibility(View.VISIBLE);
                        txtRetry.setOnClickListener(v -> {
                            sharedPreferences.edit().putInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0).apply();
                            loadFirstPage(categoryIDTwo, cityId, false);

                        });
                    }
                    notifyFilterList();
                } else {
                    noInternetSort();
                }
                break;
        }
        if (fromFilter) {
            Snackbar snackbar = Snackbar.make(relcontent, msg, Snackbar.LENGTH_LONG);
            // snackbar.show();
        }
    }


    void noInternetSort() {
        recyclerView.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtRetry.setVisibility(View.VISIBLE);
        txtRetry.setOnClickListener(v -> {
            sharedPreferences.edit().putInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0).apply();
            loadFirstPage(categoryIDTwo, cityId, false);
        });

    }
    int savedRadioIndex = 0;
    private void LoadPreferences(boolean setbtncheck) {
        savedRadioIndex = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        if (setbtncheck) {
            savedCheckedRadioButton = (RadioButton) radioGroup.getChildAt(savedRadioIndex);
            savedCheckedRadioButton.setChecked(true);
        }
    }

    private void getMyCartListTotalCount(String deviceId, String userID) {

        Call<CartListTotalCountModelEcom> wsCallingEvents = mAPIInterface.getMyCartTotalCountListEcom(deviceId, userID);
        wsCallingEvents.enqueue(new Callback<CartListTotalCountModelEcom>() {
            @Override
            public void onResponse(Call<CartListTotalCountModelEcom> call, Response<CartListTotalCountModelEcom> response) {
                myTotalCountCartData.clear();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("CartAPI", "CartAPI: " + response);
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            tvCartBadge.setText("0");
                        } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            Log.d("CartAPIInCat", "CartAPIInCat: " + response.body().getData());
                            String count = String.valueOf(response.body().getData().getCartTotalCount());
                            tvCartBadge.setText(count);
                            //  final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCategoryActivity.this);

                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {
                Log.d("CartAPIInCatError", "CartAPIInCatError: " + t);
            }
        });
    }

    private void getMyCartListPV(String deviceId, String userId) {
        pvResult = 0;
        // mProgressDialog.setVisibility(View.VISIBLE);
        Log.d("getMyCartListPV", "getMyCartListPV: call " + userId);

        Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, userId, cityId);
        wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<CartListModelEcom> call, @NotNull Response<CartListModelEcom> response) {
                Log.d("CartResponse", "CartResponse11: " + response.body());
                cartModels.clear();

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("CartAPI", "CartAPI: " + response);
                        Log.d("CartResponse", "CartResponse200: " + response.body());

                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            //noRecordsFound();
                        } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            cartModels.addAll(response.body().getData().getCart());
                            Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());
                            pvResult = response.body().getData().getTotalPV();
                            Log.e("Data", "URL:: call  pvResult " + pvResult);
                            Log.d("CartAPIIn", "CartAPIIn: " + pvResult);
                            if (pvResult < Const.PVValue && mrpisAssociate) {
                                tvPvStatus.setVisibility(View.VISIBLE);
                                tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");

                            } else if (pvResult == 0) {
                                tvPvStatus.setVisibility(View.GONE);
                            } else {
                                tvPvStatus.setVisibility(View.GONE);

                            }
                            Log.d("pvResult", "pvResult: " + pvResult);
                            Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));

                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            // serviceUnavailable();
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<CartListModelEcom> call, @NotNull Throwable t) {
                Log.d("CartAPI", "CartAPI: " + t);
                Log.d("CartResponse", "CartResponseFil: " + t);
            }
        });
    }

    public void notifyFilterList() {
      // categoryDetailsAdapter = new CategoryDetailsAdapter(MyCategoryActivity.this, categoryDetailsDisplay, MyCategoryActivity.this);
      //  recyclerView.setAdapter(categoryDetailsAdapter);
        categoryDetailsAdapter.notifyDataSetChanged();
        Log.d("load==","notifyFilterList call");
    }

    List<SubCategoryProductList> results;
    public void loadFirstPage(int catID, int cityID, boolean itemAdded) {

        mProgressDialog.setVisibility(View.VISIBLE);
        currentPage = PAGE_START;
       // isLastPage = false;

        if (endlessRecyclerViewScrollListener != null) {
            endlessRecyclerViewScrollListener.resetState();
        }

        callTopRatedApi(catID, cityID).enqueue(new Callback<NewCategoryProductDetails>() {
            @Override
            public void onResponse(@NotNull Call<NewCategoryProductDetails> call, @NotNull Response<NewCategoryProductDetails> response) {
                mProgressDialog.setVisibility(View.INVISIBLE);
                //isLoading=false;
                if (response.isSuccessful()) {

                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                        Toast toast = Toast.makeText(MyCategoryActivity.this, "Coming Soon", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }/* else if (categoryIDTwo != catID) {
                        categoryDetails.clear();
                    }*/
                    recyclerView.setVisibility(View.VISIBLE);
                    categoryIDTwo = catID;
                    categoryDetailsOrig.clear();
                    // Got data. Send it to adapter
                    results = response.body().getData().getData();
                    //categoryDetailsAdapter.addAll( results );
                    categoryDetailsOrig.addAll(results);

                    setFilterSortby(false);
                    // notifyFilterList(categoryDetails);
                    Log.e("Data", "URL:: NotifyDatasetchange " + catID);
                    Log.d("projectImage", "projectImage " + results.toString());

                    ArrayList<String> getData = new ArrayList<>();
                    getData.add(results.toString());
                    Log.e("getData array: ", "getData array: " + getData);
                    for (int i = 0; i < results.size(); i++) {
                        if (!response.body().getData().getData().contains(results.get(i).getCategoryMainImage())) {
                            categoryPic.add(results.get(i).getCategoryMainImage());
                            categoryPrice.add(results.get(i).getCategorySalePrice());
                            categoryMRPPrice.add(results.get(i).getCategoryMRP());
                            categoryPV.add(results.get(i).getCategoryPV());
                            categoryBV.add(results.get(i).getCategoryBV());
                            categoryNV.add(results.get(i).getCategoryNV());
                            categoryName.add(results.get(i).getCategoryName());
                        }
                    }

                  /*  if (categoryDetails.size() == response.body().getData().getTotalRecord()) {
                        isLastPage = true;
                    }*/
                    if (categoryDetailsOrig.size() > 0) {
                        // llEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        imgError.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.GONE);

                    } else {
                        imgError.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.VISIBLE);
                        noRecordsFound();
                    }
                }
                try {
                    mLoadProgressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<NewCategoryProductDetails> call, @NotNull Throwable t) {
                t.printStackTrace();
                imgError.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.INVISIBLE);
                noRecordsFound();
                mLoadProgressDialog.dismiss();
            }
        });
    }


    public void loadFirstPageBack(int catID, int cityID) {

        Log.e("Data", "catID " + catID);
        Log.e("Data", "cityID " + cityID);
        mProgressDialog.setVisibility(View.VISIBLE);
        currentPage = PAGE_START;
       // isLastPage = false;

        if (endlessRecyclerViewScrollListener != null) {
            endlessRecyclerViewScrollListener.resetState();
        }

        callTopRatedApi(catID, cityID).enqueue(new Callback<NewCategoryProductDetails>() {
            @Override
            public void onResponse(@NotNull Call<NewCategoryProductDetails> call, @NotNull Response<NewCategoryProductDetails> response) {
                mProgressDialog.setVisibility(View.INVISIBLE);
                //isLoading=false;
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                        Toast toast = Toast.makeText(MyCategoryActivity.this, "Coming Soon", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } /*else if (categoryIDTwo != catID) {
                        categoryDetails.clear();
                    }*/
                    recyclerView.setVisibility(View.VISIBLE);
                    categoryIDTwo = catID;
                    categoryDetailsOrig.clear();
                    // Got data. Send it to adapter
                    List<SubCategoryProductList> results = response.body().getData().getData();
                    //categoryDetailsAdapter.addAll( results );
                    categoryDetailsOrig.addAll(results);
                    setFilterSortby(false);
                    //notifyFilterList(categoryDetails);
                    Log.d("projectImage", "projectImage " + results.toString());

                    ArrayList<String> getData = new ArrayList<>();
                    getData.add(results.toString());

                    Log.e("getData array: ", "getData array: " + getData);

                    for (int i = 0; i < results.size(); i++) {
                        if (!response.body().getData().getData().contains(results.get(i).getCategoryMainImage())) {
                            categoryPic.add(results.get(i).getCategoryMainImage());
                            categoryPrice.add(results.get(i).getCategorySalePrice());
                            categoryMRPPrice.add(results.get(i).getCategoryMRP());
                            categoryPV.add(results.get(i).getCategoryPV());
                            categoryBV.add(results.get(i).getCategoryBV());
                            categoryNV.add(results.get(i).getCategoryNV());
                            categoryName.add(results.get(i).getCategoryName());
                        }
                    }
                    /*if (categoryDetails.size() == response.body().getData().getTotalRecord()) {
                        isLastPage = true;
                    }*/
                    if (categoryDetailsOrig.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        imgError.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.GONE);
                    } else {
                        imgError.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NewCategoryProductDetails> call, @NotNull Throwable t) {
                t.printStackTrace();
                imgError.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.INVISIBLE);
            }
        });
    }

   /* private void loadNextPage() {
        callTopRatedApi(categoryIDTwo, cityId).enqueue(new Callback<NewCategoryProductDetails>() {
            @Override
            public void onResponse(@NotNull Call<NewCategoryProductDetails> call, @NotNull Response<NewCategoryProductDetails> response) {
                categoryDetailsAdapter.removeLoadingFooter();
               // isLoading = false;
                categoryDetails.clear();
                assert response.body() != null;
                List<SubCategoryProductList> results = response.body().getData().getData();
                //categoryDetailsAdapter.addAll( results );
                categoryDetails.addAll(results);
                setFilterSortby(false);
                // notifyFilterList(categoryDetails);

                //ImagePass(results);
                for (int i = 0; i < results.size(); i++) {
                    if (!response.body().getData().getData().contains(results.get(i).getCategoryMainImage())) {
                        categoryPic.add(results.get(i).getCategoryMainImage());
                    }
                }

               *//* if (categoryDetails.size() == response.body().getData().getTotalRecord()) {
                    isLastPage = true;
                }*//*
            }

            @Override
            public void onFailure(Call<NewCategoryProductDetails> call, Throwable t) {
              //  isLoading = false;
                categoryDetailsAdapter.removeLoadingFooter();
                t.printStackTrace();
            }
        });
    }*/


    public Call<NewCategoryProductDetails> callTopRatedApi(int i, int cityId) {
        return mAPIInterface.getCategory(i, cityId, currentPage, 50);
    }


    @Override
    public void onItemClick(String item) {

    }

    public class CategoryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;

        List<SubCategoryProductList> mArrayList;

        boolean clicked = false;
        private AdapterCallback mAdapterCallback;

        private APIInterface mAPIInterface;
        String m_deviceId,uniqueID;
        Session session;
        //private boolean isLoadingAdded = false;
        boolean mrpisAssociate, mrpisJoined30Days;
        int cityId;
        Utils utils = new Utils();
        SharedPreferences spGetIsAssociate, spGetIsFirstPurchase, citySave;
        Dialog nagDialog;
        Runnable timeRunnable;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.name)
            MyTextViewEcom name;

            @BindView(R.id.tv_out_of_stock)
            MyTextViewEcom tvOutOfStock;

            @BindView(R.id.name_short_decs)
            MyTextViewEcom nameShortDecs;

            @BindView(R.id.tv_offer_price_value)
            MyTextViewEcom tvOfferPrice;

            @BindView(R.id.tv_original_price)
            MyTextViewEcom tvOriginalPriceSign;

            @BindView(R.id.tv_original_price_value)
            MyTextViewEcom tvOriginalPrice;

            @BindView(R.id.tv_price_discount)
            MyTextViewEcom tvDiscountPrice;

            @BindView(R.id.tv_pv_value)
            MyTextViewEcom tvPVValue;

            @BindView(R.id.tv_nv_value)
            MyTextViewEcom tvNVValue;

            @BindView(R.id.tv_bv_value)
            MyTextViewEcom tvBVValue;

            @BindView(R.id.product_count)
            MyTextViewEcom tvCount;

            @BindView(R.id.thumbnail)
            AspectRatioImageView thumbnail;

            @BindView(R.id.ic_add)
            ImageView icAdd;

            @BindView(R.id.img_share)
            ImageView imgShare;

            @BindView(R.id.ic_remove)
            ImageView icRemove;

            @BindView(R.id.progressBar)
            ProgressBar progressBar;

            @BindView(R.id.rlContent)
            RelativeLayout rlContent;

            @BindView(R.id.product_weight)
            MyTextViewEcom productWeight;

            @BindView(R.id.ratingBar)
            RatingBar ratingBar;

            @BindView(R.id.btn_add_to_cart)
            MyButtonEcom btnAddToCart;

            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public class MyViewHolderTopBanner extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_sort)
            MyTextViewEcom tvSort;

            @BindView(R.id.recyclerview_category_home)
            RecyclerView recyclerCategoryList;

            public MyViewHolderTopBanner(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mViewPagerEcom =view.findViewById(R.id.viewpager_ecom);

                mViewPagerEcom.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
                    }
                });
                mLayoutManager = new LinearLayoutManager(MyCategoryActivity.this, LinearLayoutManager.HORIZONTAL, false);

                CategoryDisplayAdapter mAdapter = new CategoryDisplayAdapter(recyclerCategoryList);
                recyclerCategoryList.setLayoutManager(mLayoutManager);
                recyclerCategoryList.setItemAnimator(new DefaultItemAnimator());
                recyclerCategoryList.setNestedScrollingEnabled(false);

                recyclerCategoryList.setAdapter(mAdapter);
                mCircleIndicatorEcom = view.findViewById(R.id.indicator_ecom);
                citySave = context.getSharedPreferences(PrefUtils.prefCityid, 0);
                cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

                getBannersList();

                tvSort.setOnClickListener(v -> ((MyCategoryActivity) context).alertBoxSort());
            }
        }

        private void getBannersList() {
            Call<Baners> wsCallingBanersList = mAPIInterface.getBanersList(true,false);
            wsCallingBanersList.enqueue(new Callback<Baners>() {
                @Override
                public void onResponse(@NotNull Call<Baners> call, @NotNull Response<Baners> response) {
                    if (response.isSuccessful()) {
                        banersList.clear();
                        if (response.code() == 200) {

                            assert response.body() != null;
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                banersList.addAll(response.body().getData());
                                adapters = new MockPagerBannerEcomCategoryAdapter(MyCategoryActivity.this, banersList);
                                mViewPagerEcom.setAdapter(adapters);
                                mViewPagerEcom.setAutoScrollTime(4000);
                                mViewPagerEcom.startAutoScroll();
                                mCircleIndicatorEcom.setViewPager(mViewPagerEcom);
                                Log.e("Banner Resp ", "Banner Resp : " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Baners> call, @NotNull Throwable t) {

                }
            });
        }

        @SuppressLint("HardwareIds")
        public CategoryDetailsAdapter(Context context, ArrayList<SubCategoryProductList> mArrayList, MyCategoryActivity callback) {
            this.context = context;
            this.mArrayList = mArrayList;
            this.mAdapterCallback = callback;
            mAPIInterface = APIClient.getClient(context).create(APIInterface.class);

            m_deviceId = android.provider.Settings.Secure.getString(
                    context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            session = new Session(context);
            sessionNormal = new com.nebulacompanies.ibo.util.Session(context);
            SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
            uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);
            typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");

            spGetIsAssociate = context.getSharedPreferences(PrefUtils.prefMrp, 0);
            mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

            spGetIsFirstPurchase = context.getSharedPreferences(PrefUtils.prefJoindays, 0);
            mrpisJoined30Days = spGetIsFirstPurchase.getBoolean(PrefUtils.prefJoindays, false);

            Log.d("categorydata:: ", mrpisAssociate + " -- " + pvResult + " -- " + Const.PVValue + " -- " + mrpisJoined30Days);

            Log.d("MDEVICEID product", "MDEVICEID product uniqueID: " + uniqueID);
            if (m_deviceId == null || m_deviceId.equals("")) {

                if (uniqueID == null || uniqueID.equals("")) {
                    String randomID = UUID.randomUUID().toString();
                    SharedPreferences preferences = context.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(PrefUtils.prefDeviceid, randomID);
                    editor.apply();
                    m_deviceId = randomID;
                } else {
                    m_deviceId = uniqueID;
                }
            }
            Log.d("MDEVICEID product", "MDEVICEID product: " + m_deviceId);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.content_category_top, parent, false);
                return new MyViewHolderTopBanner(itemView);
            } else {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.product_list_item, parent, false);
                return new MyViewHolder(itemView);
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof MyViewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                SubCategoryProductList product = mArrayList.get(position - 1);
                if (product.getCategoryCategoryId() == null) {
                    myViewHolder.progressBar.setVisibility(View.VISIBLE);
                    myViewHolder.rlContent.setVisibility(View.GONE);
                } else {
                    myViewHolder.progressBar.setVisibility(View.GONE);
                    myViewHolder.rlContent.setVisibility(View.VISIBLE);

                    if (!session.getLogin()) {
                        Log.d("Data:: ", " -- " + pvResult + " : " + product.getCategoryMRP());

                        myViewHolder.tvOfferPrice.setText(String.valueOf(product.getCategoryMRP()));
                        myViewHolder.tvOriginalPrice.setVisibility(View.GONE);
                        myViewHolder.tvDiscountPrice.setVisibility(View.GONE);
                        myViewHolder.tvOriginalPriceSign.setVisibility(View.GONE);
                    } else if (!mrpisAssociate) {
                        myViewHolder.tvOriginalPriceSign.setVisibility(View.VISIBLE);
                        myViewHolder.tvOriginalPrice.setVisibility(View.VISIBLE);
                        myViewHolder.tvOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        myViewHolder.tvOfferPrice.setText(String.valueOf(product.getCategorySalePrice()));
                        myViewHolder.tvOriginalPrice.setText(String.valueOf(product.getCategoryMRP()));
                        myViewHolder.tvDiscountPrice.setText(product.getCategorySaving());
                        Log.d("Data::", "showMRP 1 = false");
                    } else if (pvResult != null && pvResult >= Const.PVValue && !mrpisJoined30Days) {
                        myViewHolder.tvOriginalPriceSign.setVisibility(View.VISIBLE);
                        myViewHolder.tvOriginalPrice.setVisibility(View.VISIBLE);
                        myViewHolder.tvOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        myViewHolder.tvOfferPrice.setText(String.valueOf(product.getCategorySalePrice()));
                        myViewHolder.tvOriginalPrice.setText(String.valueOf(product.getCategoryMRP()));
                        myViewHolder.tvDiscountPrice.setText(product.getCategorySaving());
                        Log.d("Data::", "showMRP 2 = false");
                    } else {
                        Log.d("Data:: ", " -- " + pvResult + " : " + product.getCategoryMRP());
                        Log.d("Data::", "showMRP 1 = true");
                        myViewHolder.tvOfferPrice.setText(String.valueOf(product.getCategoryMRP()));
                        myViewHolder.tvOriginalPrice.setVisibility(View.GONE);
                        myViewHolder.tvDiscountPrice.setVisibility(View.GONE);
                        myViewHolder.tvOriginalPriceSign.setVisibility(View.GONE);
                    }

                    myViewHolder.nameShortDecs.setText(product.getCategoryShortDescription());
                    myViewHolder.name.setText(product.getCategoryName());
                    myViewHolder.productWeight.setText(product.getCategoryWeight());

                    double defRate = 4.5;
                    String defrating = String.valueOf(defRate);
                    if (showReviews) {
                        double avgRate = product.getAverageRating();
                        String avgRating = String.valueOf(avgRate);
                        if (avgRating.equals("0.0")) {
                            myViewHolder.ratingBar.setRating(Float.parseFloat(defrating));
                        } else {
                            myViewHolder.ratingBar.setRating(Float.parseFloat(avgRating));
                        }
                    } else {
                        myViewHolder.ratingBar.setRating(Float.parseFloat(defrating));
                    }
                    //myViewHolder.name.setText( product.getCategoryCategoryName() );

                    myViewHolder.tvPVValue.setText("" + product.getCategoryPV());
                    myViewHolder.tvBVValue.setText("" + product.getCategoryBV());
                    myViewHolder.tvNVValue.setText("" + product.getCategoryNV());
                    myViewHolder.thumbnail.setImageDrawable(getRandomDrawbleColor());

                    Glide
                            .with(context)
                            .load(product.getCategoryThumbnailImage())
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            //.skipMemoryCache(true)
                            .dontAnimate()
                            .placeholder(getRandomDrawbleColor())
                            .into(new SimpleTarget<Bitmap>() {

                                @Override
                                public void onResourceReady(Bitmap arg0, GlideAnimation<? super Bitmap> arg1) {
                                    // TODO Auto-generated method stub
                                    myViewHolder.thumbnail.setImageBitmap(arg0);
                                }
                            });
                    myViewHolder.thumbnail.setOnClickListener(v -> {
                        openDetail(product);
                    });
                    myViewHolder.thumbnail.setOnLongClickListener(v -> {
                        nagDialog = new Dialog(context, R.style.MyAlertDialogStyle);
                        nagDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        nagDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        nagDialog.setCancelable(true);
                        nagDialog.setContentView(R.layout.preview_image);
                        Button btnClose = nagDialog.findViewById(R.id.btnIvClose);
                        ImageView ivPreview =  nagDialog.findViewById(R.id.iv_preview_image);
                        MyBoldTextView textView = nagDialog.findViewById(R.id.tv_item_name);
                        textView.setText(product.getCategoryName());

                        DrawableRequestBuilder<String> thumbnailRequest = Glide
                                .with(context)
                                .load(product.getCategoryThumbnailImage());

                        // pass the request as a a parameter to the thumbnail request
                        Glide.with(context)
                                .load(product.getCategoryMainImage())
                                .thumbnail(thumbnailRequest)
                                .into(ivPreview);

                        nagDialog.show();

                        return false;
                    });
                    myViewHolder.thumbnail.setOnTouchListener((View.OnTouchListener) (v, event) -> {
                        //ActionBar actionBar = getSupportActionBar();
                        Log.d("Action", "onTouch: " + event.getAction());

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            Log.d("Action", "onTouch: Action up");
                            if (nagDialog != null) {
                                nagDialog.dismiss();
                                nagDialog.cancel();
                            }
                        }
                        return false;
                    });

                    timeRunnable = () -> {
                        nagDialog = new Dialog(context, R.style.MyAlertDialogStyle);
                        nagDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        nagDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        nagDialog.setCancelable(false);
                        nagDialog.setContentView(R.layout.preview_image);
                        Button btnClose = (Button) nagDialog.findViewById(R.id.btnIvClose);
                        ImageView ivPreview =  nagDialog.findViewById(R.id.iv_preview_image);
                        MyBoldTextView textView = (MyBoldTextView) nagDialog.findViewById(R.id.tv_item_name);
                        textView.setText(product.getCategoryName());
                        ivPreview.setClipToOutline(true);


                        DrawableRequestBuilder<String> thumbnailRequest = Glide
                                .with(context)
                                .load("https://i.pinimg.com/originals/11/1a/03/111a03133d14214539c96e0f657dff1a.png");

                        // pass the request as a a parameter to the thumbnail request
                        Glide.with(context)
                                .load("https://i.pinimg.com/originals/86/ff/b8/86ffb87572d657f335cd7cd828c70de3.jpg")
                                .thumbnail(thumbnailRequest)
                                .into(ivPreview);

                        Glide.with(context)
                                .load(product.getCategoryMainImage())
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        //Glide.with(context).load("https://i.pinimg.com/originals/86/ff/b8/86ffb87572d657f335cd7cd828c70de3.jpg").into(ivPreview);
                                        return false;
                                    }
                                })
                                .into(ivPreview);


                        Glide.with(context)
                                .load(product.getCategoryMainImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                // .error(R.drawable.imagenotfound)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        // log exception
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(ivPreview);

                        nagDialog.show();
                    };


                    //  HoverTouchHelper.make(root, myViewHolder.thumbnail);

                    if (product.getCategoryQuantity() == 0) {
                        myViewHolder.btnAddToCart.setVisibility(View.INVISIBLE);
                        myViewHolder.thumbnail.setColorFilter(ContextCompat.getColor(context, R.color.gray_light_opacity), android.graphics.PorterDuff.Mode.MULTIPLY);
                        myViewHolder.rlContent.setBackgroundColor(getResources().getColor(R.color.gray_light_opacity_layout));
                        myViewHolder.tvOutOfStock.setVisibility(View.VISIBLE);
                    } else {
                        myViewHolder.tvOutOfStock.setVisibility(View.GONE);
                        myViewHolder.rlContent.setBackgroundColor(getResources().getColor(R.color.white));
                        myViewHolder.btnAddToCart.setVisibility(View.VISIBLE);
                        myViewHolder.thumbnail.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

                    }

                    myViewHolder.btnAddToCart.setOnClickListener(view -> {
                        clicked = true;
                        //Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show(); getCategoryProductID()
                        addToCart(m_deviceId, session.getIboKeyId(), product.getCategoryProductId(), 1, "plus");
                        Log.d("session.getIboKeyId()", "session.getIboKeyId() " + session.getIboKeyId());
                    });

                    if (position == (getItemCount() - 1)) {
                        // here goes some code
                        //  callback.sendMessage(Message);
                    }
                    boolean sharelink = product.getShowShareLink() != null && product.getShowShareLink();
                    utils.setShare(myViewHolder.imgShare, getApplicationContext(), mrpisAssociate, sharelink);
                    myViewHolder.imgShare.setOnClickListener(v -> {
                        v.setEnabled(false);
                        utils.shareReferenceID(context, sessionNormal.getRefId(), String.valueOf(product.getCategoryId()), product.getCategoryName(), session.getIboKeyId(), (ImageView) v, mLoadProgressDialog);
                    });
                    myViewHolder.rlContent.setOnClickListener(view -> {
                        openDetail(product);

                    });
                }
            } else {
            }
        }

        private void openDetail(SubCategoryProductList product) {
            Intent intent = new Intent(context, ProductDescription.class);
            if (clicked) {
                intent.putExtra("product_clicked", "1");
            } else {
                intent.putExtra("product_clicked", "0");
            }
            intent.putExtra("catid", product.getCategoryCategoryId());
            intent.putExtra("ebc_id", product.getCategoryId());
            intent.putExtra("quantity_count", product.getCategoryQuantity());
            intent.putExtra("product_id", product.getCategoryProductId());
            intent.putExtra("product_name", product.getCategoryName());
            intent.putExtra("product_offer_price", String.valueOf(product.getCategorySalePrice()));
            intent.putExtra("product_mrp_price", String.valueOf(product.getCategoryMRP()));
            intent.putExtra("product_desc", product.getCategoryDescription());
            intent.putExtra("product_saving", product.getCategorySaving());
            intent.putExtra("product_return", product.getCategoryReturnPolicy());
            intent.putExtra("product_warranty", product.getCategoryWarranty());
            intent.putExtra("product_quantity", product.getCategoryQuantity());
            intent.putExtra("product_MaxSaleQuantity", product.getCategoryMaxSaleQuantity());
            intent.putExtra("product_short_dec", product.getCategoryShortDescription());
            intent.putExtra("product_SKU", product.getCategorySKU());
            startActivityForResult(intent, utils.productShare);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return mArrayList.size() + 1;
        }

        private void enableDisableSwipeRefresh(boolean enable) {
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setEnabled(enable);
            }
        }

        private void addToCart(String deviceId, String userId, Integer productId, Integer quantity, String CartFlag) {
            if (Utils.isNetworkAvailable(context)) {
                Call<AddToCartModel> wsCallingRegistation = mAPIInterface.getAddToCartModelCall(deviceId, userId, productId, quantity, CartFlag);
                wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                    @Override
                    public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                Toast toast = Toast.makeText(context, "item added to cart", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Log.d("Add to Cart res", "Add to Cart res" + response);
                                mAdapterCallback.onMethodCallbackMain();
                            }
                        } else {
                            utils.showErrorDialog(MyCategoryActivity.this,true,"Session Timeout","Your Session has been expired. Please Login again.",true,session);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddToCartModel> call, Throwable t) {
                        //mProgressDialog.dismiss();
                        Log.d("Add to Cart error", "Add to Cart error " + t);
                        Toast.makeText(context, "Error: " + t, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                utils.dialogueNoInternet((Activity) context);
            }
        }

        public void setItems(List<SubCategoryProductList> items) {
            mArrayList = items;
            notifyDataSetChanged();
        }

        public void clearData() {
            // clear the data
            mArrayList.clear();
        }

        public void add(SubCategoryProductList r) {
            mArrayList.add(r);
            notifyItemInserted(mArrayList.size());
        }

        public void addAll(List<SubCategoryProductList> moveResults) {
            mArrayList.addAll(moveResults);
            notifyDataSetChanged();
        }

        public void remove(SubCategoryProductList r) {
            int position = mArrayList.indexOf(r);
            if (position > -1) {
                mArrayList.remove(position);
                notifyItemRemoved(position + 1);
            }
        }

        public void clear() {
          //  isLoadingAdded = false;
            mArrayList.clear();
        }

        public boolean isEmpty() {
            return getItemCount() == 0;
        }

        public void addLoadingFooter() {
          //  isLoadingAdded = true;
            add(new SubCategoryProductList());
        }

        public void removeLoadingFooter() {
         //   isLoadingAdded = false;

            int position = mArrayList.size();
            SubCategoryProductList result = getItem(position - 1);

            if (result != null) {
                mArrayList.remove(position - 1);
                notifyItemRemoved(position);
            }
        }

        public SubCategoryProductList getItem(int position) {
            return mArrayList.get(position);
        }

        public ColorDrawable[] vibrantLightColorList =
                {
                        new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                        new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                        new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                        new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
                };

        public ColorDrawable getRandomDrawbleColor() {
            int idx = new Random().nextInt(vibrantLightColorList.length);
            return vibrantLightColorList[idx];
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == utils.productShare) {
            callAPI();
        }
    }

    private void getInventory() {
        List<String> sku = new ArrayList<>();
        sku.add("BBJ011001");
        sku.add("CCJ011002");

        mAPIInterface = APIClient.getClientUniCommmerce(MyCategoryActivity.this).create(APIInterface.class);
        Log.e("SKU Response1", "SKU Response1: " + sku);
        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel();
        inventoryRequestModel.setItemTypeSKU(sku);

        Log.e("SKU Response132323", "SKU Response132323: " + sku);
        Call<InventoryModel> wsCallingRegistation = mAPIInterface.postInventory(inventoryRequestModel);
        wsCallingRegistation.enqueue(new Callback<InventoryModel>() {
            @Override
            public void onResponse(Call<InventoryModel> call, Response<InventoryModel> response) {
                Log.e("SKU Response2", "SKU Response2: " + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getSuccessful()) {
                        if (response.body().getData() != null) {
                            Log.e("SKU Response3", "SKU Response3: " + new Gson().toJson(response.body()));
                        }
                    } else if (!response.body().getSuccessful()) {
                        Log.e("SKU Response4", "SKU Response4: " + new Gson().toJson(response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<InventoryModel> call, Throwable t) {
                Log.e("SKU Response5", "SKU Response5: " + t);
            }
        });
    }

    public class CategoryDisplayAdapter extends RecyclerView.Adapter<CategoryDisplayAdapter.MyViewHolder> {

        RecyclerView recyclerCategoryList;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.textViewDispname)
            MyTextViewEcom name;

            @BindView(R.id.line)
            View lineview;

            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public CategoryDisplayAdapter(RecyclerView recyclerCategoryList) {
            this.recyclerCategoryList = recyclerCategoryList;
        }

        @Override
        public CategoryDisplayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_display_list_item, parent, false);
            return new CategoryDisplayAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CategoryDisplayAdapter.MyViewHolder holder, int position) {
            Category category = categoryArrayList.get(position);
            holder.name.setText(category.getCategoryName());
            Log.e("CategoryChecked",""+category.isSetSelected());
            try {
                if (category.isSetSelected()) {
                    selPos = position;
                    recyclerCategoryList.getLayoutManager().scrollToPosition(position);
                    holder.lineview.setVisibility(View.VISIBLE);
                }else
                holder.lineview.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                Log.e("Error",e.getMessage());
                holder.lineview.setVisibility(View.INVISIBLE);

            }

            if(selPos == 5){
                recyclerCategoryList.getLayoutManager().scrollToPosition(selPos);
            }
            holder.name.setOnClickListener(v -> {
                categoryIDTwo = category.getProductID();
                categoryArrayList.get(selPos).setSetSelected(false);
                category.setSetSelected(true);
                notifyDataSetChanged();
                sharedPreferences.edit().putInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0).apply();
                loadFirstPage(categoryIDTwo, cityId, false);
            });
        }
        @Override
        public int getItemCount() {
            return categoryArrayList.size();
        }
    }
}
package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.ecom.adapter.SearchAdapter;
import com.nebulacompanies.ibo.ecom.fragment.HomeFragment;
import com.nebulacompanies.ibo.ecom.fragment.MyAccountFragment;
import com.nebulacompanies.ibo.ecom.fragment.MyTrackorderFragment;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.model.SearchModelDetails;
import com.nebulacompanies.ibo.ecom.model.SearchModelEcom;
import com.nebulacompanies.ibo.ecom.model.ShowMRPPriceModelEcom;
import com.nebulacompanies.ibo.ecom.utils.ActionBottomDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.CallBackListener;
import com.nebulacompanies.ibo.ecom.utils.CustomItemClickListener;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.ProductDetailsDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_FAIL;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MainActivity extends AppCompatActivity implements
        CounterCallback, CallBackListener,
        ProductDetailsDialogFragment.ItemClickListener {

    APIInterface mAPIInterface, mAPIInterfaceProfile, mAPIInterfaceMRP;
    MaterialProgressBar mProgressDialog;

    BottomNavigationView bottomNavigation;
    public RelativeLayout lnCart, rlSearchView;
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle;
    MyTextViewEcom txtErrorContent;
    ImageView imgSearch, imgFav, imgCart, imgSearchClose, imgMainBack;
    MyTextViewEcom tvToolbarTitle, tvCartBadge, tvLocation, tvPvStatus, txtRetry;
    NebulaEditTextEcom editSearch;
    RelativeLayout rl_search_view;
    LinearLayout lnSearch, lnLocation;
    FrameLayout frameLayoutContent;
    RecyclerView rvSearch;
    SearchAdapter searchAdapter;
    Handler handler;

    // private CallBackListenerHome callBackListenerHome;
    ProductDetailsDialogFragment addPhotoBottomDialogFragment;
    UtilsVersion utilsVersion = new UtilsVersion();

    private ArrayList<SearchModelDetails> searchModelDetailsList = new ArrayList<>();
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    ArrayList<MyCart> cartModels = new ArrayList<>();

    // private static final String SELECTED_ITEM = "arg_selected_item";
    boolean isAddressType, mrpisAssociate, mrpisFirstPurchase, mrpisJoined30Days;
    boolean isVisible = false;
    String m_deviceId, uniqueID, userName, version_name, addressNameSave, addressType, cityFacility;
    int cityId, version_code, selectHomePage, selectedHomeMyOrder, selectedMyAccount, selectedMyAccountOrders, selectedInnerHome, selectedInnerMyOrder, selectedInnerMyAccount;
    Integer pvResult, allCityID;
    // private int mSelectedItem;

    SharedPreferences SharedPreferencesLocationName, sharedPreferencesAddressType, sharedPreferencesAddressTypeValue, sharedPreferencesFacility;
    Session session;
    Intent homeData;
    public static String Dialogdismis = "dialogdismis";
    BroadcastReceiver rec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Do stuff
            addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);
            if (TextUtils.isEmpty(addressNameSave))
                finish();
        }
    };
    com.nebulacompanies.ibo.util.Session sessionNormal;
    int notification = 0;
    Utils utils;
    IntentFilter filterLogin;

    @SuppressLint({"ClickableViewAccessibility", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ecom);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        initPref();
        initUI();
        initBundle();
        initMenu();
        initData();
        initOnclick();
        callAPI();

        Log.e("Main","Main");

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initOnclick() {

        editSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.fade_in_new, R.anim.fade_out_new);
                return true;
            }
            return false;
        });

        editSearch.addTextChangedListener(textWatcher);

        imgSearchClose.setOnClickListener(view -> editSearch.getText().clear());

        rl_search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Search","Search");
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.fade_in_new, R.anim.fade_out_new);
            }
        });

        tvLocation.setOnClickListener(view -> {
            addPhotoBottomDialogFragment =
                    ProductDetailsDialogFragment.newInstance();
            addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                    ProductDetailsDialogFragment.TAG);
        });

        imgSearch.setOnClickListener(view -> rlSearchView.setVisibility(View.VISIBLE));

        imgCart.setOnClickListener(view -> {
            Intent login = new Intent(MainActivity.this, MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });


        imgMainBack.setOnClickListener(view -> {

            if (isVisible = true) {
                lnSearch.setVisibility(View.GONE);
                frameLayoutContent.setVisibility(View.VISIBLE);
                isVisible = false;
                editSearch.getText().clear();
                // Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_LONG).show();
            } else {
                if (bottomNavigation.getSelectedItemId() == R.id.navigation_home) {

//                    Intent i = new Intent(MainActivity.this, DashboardActivity.class);
//                    startActivity(i);
//                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

                    finish();
                } else if (selectedMyAccountOrders == 4) {
                    finish();
                } else if (selectedInnerMyAccount == 8) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_home);
                } else if (selectedInnerMyOrder == 9) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_home);
                } else if (homeData == null) {
                    finish();
                } else {
                    bottomNavigation.setSelectedItemId(R.id.navigation_home);
                }
            }
        });
    }

    private void initData() {
       // if (!showLocation) {
            lnLocation.setVisibility(View.GONE);
       /* } else {
            if (addressNameSave == null || addressNameSave.equals("")) {
                lnLocation.setVisibility(View.GONE);
                checkFirstLocation();
            } else {
                lnLocation.setVisibility(View.VISIBLE);
                tvLocation.setText((isAddressType ? "Pickup from " : "Deliver to ") + addressNameSave);
            }
        }*/
        tvLocation.setText("Deliver to " + addressNameSave);
    }

    private void initMenu() {

        if (notification == 1) {
            bottomNavigation.getMenu().getItem(1).setChecked(true);
            openFragment(MyTrackorderFragment.newInstance("", ""));
        } else if (selectHomePage == 1) {
            openFragment(MyTrackorderFragment.newInstance("", ""));
            bottomNavigation.getMenu().getItem(1).setChecked(true);
        } else if (selectedHomeMyOrder == 2) {
            openFragment(HomeFragment.newInstance("", ""));
            // tvToolbarTitle.setText("Home");
        } else if (selectedMyAccount == 3) {
            Log.d("selectedMyAccount", "selectedMyAccount: " + selectedMyAccount);
            bottomNavigation.getMenu().getItem(2).setChecked(true);
            openFragment(MyAccountFragment.newInstance());
            // tvToolbarTitle.setText("My Accounts");
        } else if (selectedMyAccountOrders == 4) {
            Log.d("selectedMyAccount", "selectedMyAccount: " + selectedMyAccountOrders);
            bottomNavigation.getMenu().getItem(1).setChecked(true);
            openFragment(MyTrackorderFragment.newInstance("", ""));
            // tvToolbarTitle.setText("My Order");
        } else if (selectedInnerMyAccount == 8) {
            Log.d("SELECTEDINNERACCOUNT", "SELECTEDINNERACCOUNT: " + selectedInnerMyAccount);
            bottomNavigation.getMenu().getItem(2).setChecked(true);
            openFragment(MyAccountFragment.newInstance());

            // tvToolbarTitle.setText("My Order");
        } else if (selectedInnerMyOrder == 9) {
            Log.d("SELECTEDINNERTRACKORDER", "SELECTEDINNERTRACKORDER: " + selectedInnerMyOrder);
            bottomNavigation.getMenu().getItem(1).setChecked(true);
            openFragment(MyTrackorderFragment.newInstance("", ""));
            // tvToolbarTitle.setText("My Order");
        } else {
            openFragment(HomeFragment.newInstance("", ""));
            // tvToolbarTitle.setText("Home");
        }
    }

    private void initBundle() {
        homeData = getIntent();

        if (homeData != null) {
            selectHomePage = homeData.getIntExtra("SELECTEDVALUE", 0);
            selectedHomeMyOrder = homeData.getIntExtra("MYORDERDETAILS", 0);
            selectedMyAccount = homeData.getIntExtra("SELECTEDVALUELOGIN", 0);
            selectedMyAccountOrders = homeData.getIntExtra("SELECTEDACCOUNTORDER", 0);

            selectedInnerHome = homeData.getIntExtra("SELECTEDINNERHOME", 0);
            selectedInnerMyAccount = homeData.getIntExtra("SELECTEDINNERACCOUNT", 0);
            selectedInnerMyOrder = homeData.getIntExtra("SELECTEDINNERTRACKORDER", 0);
            notification = homeData.getIntExtra("notificationIntent", 0);
        }
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        frameLayoutContent = findViewById(R.id.frameLayoutContent);
        lnSearch = findViewById(R.id.ln_search);
        lnLocation = findViewById(R.id.ln_location);
        mProgressDialog = findViewById(R.id.progresbar);

        llEmpty = findViewById(R.id.llEmpty1);
        imgError = findViewById(R.id.imgError1);
        txtErrorTitle = findViewById(R.id.txtErrorTitle1);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);
        tvPvStatus = findViewById(R.id.tv_pv_status);

        filterLogin = new IntentFilter(utils.actionLogin);
        registerReceiver(myReceiver, filterLogin);

        lnCart = toolbar.findViewById(R.id.toolbar_settings);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgFav = toolbar.findViewById(R.id.img_my_fav);
        imgCart = toolbar.findViewById(R.id.img_my_cart);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        tvCartBadge = toolbar.findViewById(R.id.cart_badge);
        imgSearchClose = toolbar.findViewById(R.id.img_search_close);
        rl_search_view =  toolbar.findViewById(R.id.rl_search_view_main);
        editSearch = toolbar.findViewById(R.id.editMobileNo);

        tvLocation = findViewById(R.id.tv_location);
        rvSearch = findViewById(R.id.rv_search);
        rvSearch.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rvSearch.setLayoutManager(mLayoutManager);
        rvSearch.setItemAnimator(new DefaultItemAnimator());


    }

    private void initPref() {
        mAPIInterface = APIClient.getClient(MainActivity.this).create(APIInterface.class);
        session = new Session(this);
        sessionNormal = new com.nebulacompanies.ibo.util.Session(this);
        handler = new Handler();

        utilsVersion.checkVersion(this);
        utils = new Utils();
        Intent intent = getIntent();
        // String action = intent.getAction();
        Uri data = intent.getData();

        Log.e("data ", "data: " + data);
        // String URL = String.valueOf(data);


        IntentFilter filter = new IntentFilter(Dialogdismis);
        this.registerReceiver(rec, filter);


        m_deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);


        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        SharedPreferences citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value
        SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
        addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

       // if (!showLocation) {
            cityId = 0;
       // }
        Log.d("MDEVICEID uniqueID", "MDEVICEID uniqueID: " + uniqueID);
        if (m_deviceId == null || m_deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                m_deviceId = randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }

    }

    //The BroadcastReceiver that listens for bluetooth broadcasts
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(utils.actionLogin)) {
                setSessionData();
                // callAPI();
            }
        }
    };

    /*
        @Override
        public void onDestroy() {
            super.onDestroy();
            unregisterReceiver(myReceiver);
        }*/
    public void setSessionData() {
        //SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefUsername, 0);
        userName = sessionNormal.getUserName();// SharedPreferencesUserName.getString(PrefUtils.prefUsername, null);
        Log.d("username", "username:" + userName);
        if (session.getLogin()) {
            if (userName == null || userName.equals("")) {
                getMyProfile();
            } else {
                tvToolbarTitle.setText("Hi, " + userName);
            }
        } else {
            tvToolbarTitle.setText("Hi, Guest");
        }
        if (!session.getLogin()) {
            bottomNavigation.getMenu().getItem(2).setVisible(false);
            bottomNavigation.getMenu().getItem(1).setVisible(false);
        } else {
            bottomNavigation.getMenu().getItem(2).setVisible(true);
            bottomNavigation.getMenu().getItem(1).setVisible(true);
        }
    }

    public void callAPI() {
        setSessionData();
        getMRPPrice();
        getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
        getTokenUniCommerce();
        getMyCartListPV();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(rec);
        this.unregisterReceiver(myReceiver);
    }

    boolean mrpResult;

    private void getMyCartListPV() {

        SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpResult = SharedPreferencesUserName.getBoolean(PrefUtils.prefMrp, false);

        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(m_deviceId, session.getIboKeyId(), cityId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            Log.d("CartResponse", "CartResponse200: " + response.body());

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());
                                pvResult = response.body().getData().getTotalPV();
                                showFailData();


                                Log.d("pvResult", "pvResult: " + pvResult);
                                Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_FAIL) {
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());
                                pvResult = response.body().getData().getTotalPV();
                                showFailData();

                                Log.d("pvResult", "pvResult: " + pvResult);
                                Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    mProgressDialog.setVisibility(View.INVISIBLE);
                    pvResult = 0;
                    showFailData();
                    Log.d("CartAPI", "CartAPI: " + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    boolean showMRP = true;

    private void showFailData() {
        Log.d("Data::", "showFailData");
        if (!mrpResult) {
            showMRP = false;
            Log.d("Data::", "1 Offer");
        } else if (pvResult >= Const.PVValue && !mrpisJoined30Days) {
            showMRP = false;
            Log.d("Data::", "2 Offer");
        } else {
            showMRP = true;
            Log.d("Data::", "3 MRP");
        }

        if (pvResult < Const.PVValue && mrpResult) {
            showMRP = true;
            Log.d("Data::", "4 MRP");
        }
        setSearchAdapter();
    }

    private void setSearchAdapter() {

        searchAdapter = new SearchAdapter(MainActivity.this, showMRP, searchModelDetailsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(SearchModelDetails user, int position) {

                Intent login = new Intent(MainActivity.this, ProductDescription.class);
                login.putExtra("product_id", user.getSearchProductId());
                login.putExtra("product_name", user.getSearchName());
                login.putExtra("product_offer_price", String.valueOf(user.getSearchSalePrice()));
                login.putExtra("product_mrp_price", String.valueOf(user.getSearchMRP()));
                login.putExtra("product_desc", user.getSearchDescription());
                login.putExtra("product_saving", user.getSearchSaving());
                login.putExtra("product_return", user.getSearchReturnPolicy());
                login.putExtra("product_warranty", user.getSearchWarranty());
                login.putExtra("product_quantity", user.getSearchQuantity());
                login.putExtra("product_MaxSaleQuantity", user.getSearchMaxSaleQuantity());
                login.putExtra("catid", user.getSearchCategoryId());
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);

                // Toast.makeText( MainActivity.this, "user: "+user ,Toast.LENGTH_SHORT ).show();
            }
        });
        rvSearch.setAdapter(searchAdapter);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 1000);
            //getSearchListAPI(editSearch.getText().toString());
        }
    };

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String search = editSearch.getText().toString();
            if (search.isEmpty()) {
                searchModelDetailsList.clear();
                searchAdapter.notifyDataSetChanged();
                rvSearch.setVisibility(View.GONE);
            } else {
                getSearchListAPI(editSearch.getText().toString());
            }
        }
    };


    void getTokenUniCommerce() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            Call<JsonObject> wsCallingUniCommLogin = mAPIInterface.getUniCommToken("it@nebulacompanies.com", "Nebula7890", "password", "my-trusted-client");
            wsCallingUniCommLogin.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e("Response",""+response.body());

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                trimCache(MainActivity.this);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO response", "response: " + responseString);
                                Log.i("TokenError", "TokenError: " + responseString);
                                JSONObject jsonObject = new JSONObject(responseString);

                                String tokenUniCommerce = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_TOKEN_TYPE_UNICOMMERCE) + " "
                                        + jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_ACCESS_TOKEN_UNICOMMERCE);

                                session.setAccessTokenUnicommerce(tokenUniCommerce);

                                Log.d("tokenUniCommerce: ", "tokenUniCommerce: " + tokenUniCommerce);
                                android.util.Log.e("tokenUniCommerce", "tokenUniCommerce: " + new Gson().toJson(response.body()));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                        }
                    } else {
                        if (response.code() == 400) {
                            // mProgressDialog.dismiss();
                            // Toast.makeText(LoginActivity.this, "The username or password is incorrect.", Toast.LENGTH_SHORT).show();
                            android.util.Log.e("tokenUniCommerce", "tokenUniCommerce: " + new Gson().toJson(response.body()));

                        } else {
                            //mProgressDialog.dismiss();
                            android.util.Log.e("tokenUniCommerce", "tokenUniCommerce: " + new Gson().toJson(response.body()));

                            // Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // mProgressDialog.dismiss();

                    Log.i("tokenUniCommerce", "tokenUniCommerce: " + t);
                }
            });
        } else {
            //  AppUtils.displayNetworkErrorMessage(this);
        }
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


    //Bottom Sheet Code.
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            // tvToolbarTitle.setText("Home");
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        /*case R.id.navigation_track_order:
                            tvToolbarTitle.setText("My Order");
                            openFragment(MyOrdersFragment.newInstance("", ""));
                            return true;*/
                        case R.id.navigation_track_order:


                            Log.d("Session Check1", "Session Check1" + session.getLogin());
                            if (session.getLogin()) {
                                Log.d("Session Check2", "Session Check2" + session.getLogin());
                                // tvToolbarTitle.setText("My Order");
                                openFragment(MyTrackorderFragment.newInstance("", ""));
                                /*Intent dash = new Intent(MainActivity.this, MyOrderDetailsActivity.class);
                                startActivity(dash);*/
                            } else {
                                Log.d("Session Check3", "Session Check3" + session.getLogin());

                               /* Intent dash = new Intent( MainActivity.this, LoginActivityEcom.class );
                                dash.putExtra( "TRACKORDERCALL", 2 );
                                startActivity( dash );*/
                            }

                            return true;
                        case R.id.navigation_offers:
                            //tvToolbarTitle.setText("My Accounts");
                            openFragment(MyAccountFragment.newInstance());
                            return true;
                      /* case R.id.navigation_offers:
                            openFragment(MyCartFragment.newInstance("", ""));
                            return true;*/
                    }
                    return false;
                }
            };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (bottomNavigation.getSelectedItemId() == R.id.navigation_home) {
            Bundle b = new Bundle();
            b.putInt("From",1);
            Intent i = new Intent(MainActivity.this, DashboardActivity.class);
            i.putExtras(b);
            startActivity(i);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

            finish();
        } else if (selectedMyAccountOrders == 4) {
            finish();
        } else if (selectedInnerMyAccount == 8) {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        } else if (selectedInnerMyOrder == 9) {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        } else if (homeData == null) {
            finish();
        } else {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }

    }

    /*private void getMyCartList(String deviceId, String userID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, userID, cityId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());
                                String count = String.valueOf(response.body().getData().getCart().size());
                                tvCartBadge.setText(count);
                                pvResult = response.body().getData().getTotalPV();

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    Log.d("CartAPI", "CartAPI: " + t);

                }
            });
        } else {
            // noInternetConnection();
        }
    }
*/

    private void getMyCartListTotalCount(String deviceId, String userID) {
        //  if (Utils.isNetworkAvailable(getApplicationContext())) {

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

                            Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData());
                            String count = String.valueOf(response.body().getData().getCartTotalCount());
                            tvCartBadge.setText(count);
                            // final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);

                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {
                Log.d("CartAPI", "CartAPI: " + t);
            }
        });
       /* } else {
            // noInternetConnection();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        //checkVersion();
        utils.checkExpireUser(mAPIInterface, this, session);

        utilsVersion.checkVersion(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
        //   getMyCartListPV(m_deviceId, session.getIboKeyId());

        SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
        addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

        sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, 0);
        isAddressType = sharedPreferencesAddressType.getBoolean(PrefUtils.prefAddresstype, false);

        sharedPreferencesAddressTypeValue = getSharedPreferences(PrefUtils.prefAddresstypevalue, 0);
        addressType = sharedPreferencesAddressTypeValue.getString(PrefUtils.prefAddresstypevalue, null);


        sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, 0);
        cityFacility = sharedPreferencesFacility.getString(PrefUtils.prefFacility, null);


        if (addressNameSave == null || addressNameSave.equals("")) {
            Log.d("Location Fill", "Location Fill " + addressNameSave);

        } else {
            Log.d("Location Empty", "Location Empty " + addressNameSave);

            if (isAddressType) {
                tvLocation.setText("Pickup from " + addressNameSave);
            } else {
                tvLocation.setText("Deliver to " + addressNameSave);
                //  Toast.makeText(this, "Update Value Resume"+isAddressType, Toast.LENGTH_SHORT).show();
            }
            Log.d("Update Value", "Update Value" + isAddressType);
        }
    }

    /*@Override
    public void onMethodCallbackMain() {
        getMyCartList(m_deviceId);

        Log.d("counter API","counter API");

    }*/

    @Override
    public void onMethodCounterCallback() {
        Log.d("counter API Final", "counter API Final");

    }

    @Override
    public void onCallBack() {
        // Toast.makeText(this,"onCallback Called",Toast.LENGTH_LONG).show();
        //getMyCartList(m_deviceId,session.getIboKeyIdNew());
        //getMyCartList(m_deviceId,"");
        //   getMyCartList(m_deviceId,session.getIboKeyId());
        getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
        // getMyCartListPV(m_deviceId, session.getIboKeyId());
    }

    private void getMyProfile() {
        mAPIInterfaceProfile = APIClient.getClient(MainActivity.this).create(APIInterface.class);
        //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceProfile.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @Override
            public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        //  Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            //   Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                          /*  String lastName = response.body().getData().getLastName();
                            String email = response.body().getData().getEmail();
                            String gender = response.body().getData().getGender();
                            String mobile = response.body().getData().getMobile();
                            Log.d("firstName", "firstName: " + firstName);*/
                            userName = "Hi, " + firstName;
                            tvToolbarTitle.setText(userName);

                            sessionNormal.setUserName(firstName);
                           /* SharedPreferences preferences = getSharedPreferences(PrefUtils.prefUsername, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(PrefUtils.prefUsername, userName);
                            editor.apply();*/

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


    private void getSearchListAPI(String searchText) {
        //  if (Utils.isNetworkAvailable(getApplicationContext())) {
        mProgressDialog.setVisibility(View.VISIBLE);
        mAPIInterfaceProfile = APIClient.getClient(MainActivity.this).create(APIInterface.class);
        Call<SearchModelEcom> wsCallingEvents = mAPIInterfaceProfile.getSearch(searchText);
        wsCallingEvents.enqueue(new Callback<SearchModelEcom>() {
            @Override
            public void onResponse(Call<SearchModelEcom> call, Response<SearchModelEcom> response) {
                if (mProgressDialog != null) {
                    mProgressDialog.setVisibility(View.GONE);
                }
                searchModelDetailsList.clear();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        // searchModelDetailsList.clear();
                        Log.d("SearchListGETOUT", "SearchListGETOUT: " + response.body().getMessage() + "Code = " + response.body().getStatusCode());
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            searchModelDetailsList.addAll(response.body().getData());
                            Log.d("SearchListGETIN", "SearchListGETIN: " + response.body().getMessage());


                            //  searchAdapter.notifyDataSetChanged();

                            Log.e("SearchListGETINData", "SearchListGETINData: " + new Gson().toJson(response.body()));

                            Log.d("SearchListGETINData", "SearchListGETINData: " + response.body().getData());

                        }

                        if (response.body().getData().size() > 0) {
                            // llEmpty.setVisibility(View.GONE);
                            mProgressDialog.setVisibility(View.GONE);
                            rvSearch.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                            searchAdapter.notifyDataSetChanged();
                            Log.d("sizeLIST", "sizeLIST: " + response.body().getData().size());

                        } else {
                            mProgressDialog.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                            rvSearch.setVisibility(View.GONE);
                            searchAdapter.notifyDataSetChanged();
                            noRecordsFound();
                            Log.d("sizeLIST1", "sizeLIST1: " + response.body().getData().size());
                        }
                    }
                }
                Log.d("SearchList", "result size =" + searchModelDetailsList.size());
            }

            @Override
            public void onFailure(Call<SearchModelEcom> call, Throwable t) {
                mProgressDialog.setVisibility(View.GONE);
                Log.d("SearchListGETError", "SearchListGETError: " + t);
                noRecordsFound();

            }
        });
        /*} else {
            // noInternetConnection();
            Log.d( "SearchListGETErrorNO", "SearchListGETError: NO" );
            noRecordsFound();
        }*/
    }


    void noRecordsFound() {
        txtErrorTitle.setText("No Record Found");
        txtRetry.setVisibility(View.GONE);
        txtErrorContent.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.no_search);
        llEmpty.setVisibility(View.VISIBLE);
        rvSearch.setVisibility(View.GONE);
    }

    public void checkFirstLocation() {
      /*  boolean isFirstRun = getSharedPreferences( "isFirstTimeLocation", MODE_PRIVATE ).getBoolean( "isFirstTimeLocation", true );
        if (isFirstRun) {*/
        // if(Utils.isNetworkAvailable(MainActivity.this)) {
        addPhotoBottomDialogFragment =
                ProductDetailsDialogFragment.newInstance();
        addPhotoBottomDialogFragment.setCancelable(false);
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ActionBottomDialogFragment.TAG);
        // }
        /*    getSharedPreferences( "isFirstTimeLocation", MODE_PRIVATE )
                    .edit()
                    .putBoolean( "isFirstTimeLocation", false )
                    .apply();
        }*/
    }

    @Override
    public void onItemClick(String item) {
        Toast.makeText(this, "ITEMS: " + item, Toast.LENGTH_SHORT).show();
    }

    public void setSelectedLocation(String addressType, Integer allCityID, String address, boolean isAddressType, String cityFacility) {

        if (isAddressType) {
            lnLocation.setVisibility(View.VISIBLE);
            tvLocation.setText("Pickup from " + address);
            Log.d("Update Value", "Update Value" + isAddressType);

        } else {
            lnLocation.setVisibility(View.VISIBLE);
            tvLocation.setText("Deliver to " + address);
            Log.d("Update Value", "Update Value" + isAddressType);

        }
        addressNameSave = address;
        allCityID = allCityID;
        addressType = addressType;
        cityFacility = cityFacility;

        SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPreferencesLocationName.edit();
        editor.putString(PrefUtils.prefLocation, addressNameSave);
        editor.apply();
        Log.d("Location Empty SAVE", "Location Empty SAVE" + addressNameSave);

        SharedPreferences preferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressType = preferencesAddressType.edit();
        editorpreferencesAddressType.putBoolean(PrefUtils.prefAddresstype, isAddressType);
        editorpreferencesAddressType.apply();

        SharedPreferences preferencesAddressCityID = getSharedPreferences(PrefUtils.prefPrefcityid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressCityID = preferencesAddressCityID.edit();
        editorpreferencesAddressCityID.putInt(PrefUtils.prefPrefcityid, allCityID);
        editorpreferencesAddressCityID.apply();

        Log.d("Location ID", "Location ID" + allCityID);

        SharedPreferences sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstypevalue, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorAddressType = sharedPreferencesAddressType.edit();
        editorAddressType.putString(PrefUtils.prefAddresstypevalue, addressType);
        editorAddressType.apply();
        Log.d("Location Type", "Location Type" + addressType);

        SharedPreferences sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorFacility = sharedPreferencesFacility.edit();
        editorFacility.putString(PrefUtils.prefFacility, cityFacility);
        editorFacility.apply();
        Log.d("Facility", "Facility: " + cityFacility);

        Intent intent = new Intent("custom-message-city");
        intent.putExtra("allCityID", allCityID);
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);

    }

    /*private void getMyCartListPV(String deviceId, String userId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, userId, cityId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
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

                                *//*tvTotalAmount.setText("" + response.body().getData().getGrandTotal());
                                tvMycartItemPrice.setText("" + response.body().getData().getSubTotal());
                                tvShipingPrice.setText("" + response.body().getData().getShippingCharge());
                                tvGrandTotal.setText("" + response.body().getData().getGrandTotal());
                                tvGrandTotalPV.setText("" + response.body().getData().getTotalPV());
                                tvGrandTotalNV.setText("" + response.body().getData().getTotalNV());*//*
                                pvResult = response.body().getData().getTotalPV();
                                if (pvResult < Const.PVValue) {
                                    tvPvStatus.setVisibility(View.VISIBLE);
                                    tvPvStatus.setText("Total PV: " + pvResult + ". Add item worth " + Const.PVValue + " PV to purchase these products at discounted Nebula IBO Price.");
                                } else if (pvResult == 0 || pvResult == null) {
                                    tvPvStatus.setVisibility(View.GONE);
                                } else {
                                    tvPvStatus.setVisibility(View.GONE);
                                }
                                Log.d("pvResult", "pvResult: " + pvResult);
                                android.util.Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("CartAPI", "CartAPI: " + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }
*/
   /* private void checkVersion() {
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version_code = info.versionCode;
        version_name = info.versionName;
        Log.i("Info", "version_number : " + version_code);
        Log.i("Info", "version_name : " + version_name);

       // if (Config.isFirstTimeEcom) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                try {
//                    versionChecker = new  VersionChecker(version_name);
//                    versionChecker.execute();
                    Call<VersionCheck> wsCallingversionChecker = mAPIInterface.getVersion();
                    wsCallingversionChecker.enqueue(new Callback<VersionCheck>() {
                        @Override
                        public void onResponse(Call<VersionCheck> call, Response<VersionCheck> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    try {
                                        String latestVersions = response.body().getData();
                                        if (latestVersions != null && !latestVersions.equals(version_name)) {
                                            System.out.println("The two strings are not the same.");
                                            @SuppressLint("RestrictedApi") androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.Update_Dialog));
//                                            builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                                                    try {
//                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                                                    } catch (android.content.ActivityNotFoundException anfe) {
//                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                                                    }
//                                                }
//                                            });
//                                            builder.setNegativeButton(R.string.update_later, new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
                                            LayoutInflater inflater = getLayoutInflater();
                                            View dialoglayout = inflater.inflate(R.layout.app_update_dialog, null);
                                            //TextView txt = (TextView) dialoglayout.findViewById(R.id.showtext);
                                            MyButton btnUpdate = (MyButton) dialoglayout.findViewById(R.id.btn_update);

                                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                                    try {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                    } catch (android.content.ActivityNotFoundException anfe) {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                    }
                                                }
                                            });



                                            builder.setView(dialoglayout);
                                            builder.setCancelable( false );
                                            if (!isFinishing() && builder != null) {
                                                builder.show();
                                            }


                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<VersionCheck> call, Throwable t) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Config.isFirstTimeEcom = false;
           // }
        }
    }*/


    private void getMRPPrice() {
        // if(Utils.isNetworkAvailable(getApplicationContext())) {
        mAPIInterfaceMRP = APIClient.getClient(MainActivity.this).create(APIInterface.class);
        //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Call<ShowMRPPriceModelEcom> wsCallingEvents = mAPIInterfaceMRP.getMRPPriceEcom();
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
                            mrpisJoined30Days = isjoined30Days;
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

                          /*  Intent cp = new Intent(getActivity(), MainActivity.class);
                            cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(cp);
                            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/

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
        /* }else
         *//*{
            noInternetAvailable();
        }*/
    }


    private void noInternetAvailable() {
        //rvSearch.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.VISIBLE);
// chnges
    }

  /*  String currentRank = "";

    void getRankAndVolumes() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
            Call<RankAndVolumes> wsCallingRankAndVolumes = mAPIInterface.getRankAndVolumes();
            wsCallingRankAndVolumes.enqueue(new Callback<RankAndVolumes>() {
                @Override
                public void onResponse(Call<RankAndVolumes> call, Response<RankAndVolumes> response) {

                    if (response.isSuccessful()) {

                        if (response.code() == 200) {

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                RankAndVolumeList rankAndVolumeList = response.body().getData();
                                Log.d("RankGET", "RankGET: " + rankAndVolumeList.getCurrentRank());
                                currentRank = rankAndVolumeList.getCurrentRank();

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<RankAndVolumes> call, Throwable t) {
                }
            });
        }
    }*/
}

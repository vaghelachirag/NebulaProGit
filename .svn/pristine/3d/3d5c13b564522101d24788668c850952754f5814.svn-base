package com.nebulacompanies.ibo.ecom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelReviewEcom;
import com.nebulacompanies.ibo.ecom.model.DeleteCartModel;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyCartReview;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryRequestModel;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterPayCallback;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.activities.ProductListDwarka.TAG;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


public class MyCartActivityNotification extends AppCompatActivity implements CartAdapterCallback, CartAdapterPayCallback {

    APIInterface mAPIInterface, mAPIInterfaceAddress, mAPIInterfaceCartReview;
    MaterialProgressBar mProgressDialog;
    private Handler myHandler;
    private Runnable myRunnable;
    Typeface typeface;
    Session session;

    Toolbar toolbar;
    ImageView imgBackArrow, imgFav, imgSearch, imgSearchClose, imgError, imgMyAccount, imgHome, imgTrackOrder;
    MyButtonEcom btnProceed, btnAddToCart;
    RecyclerView rvCartItem;
    RelativeLayout rlSearchView, llEmpty;
    LinearLayout lnPV, lnHome, lnAccount, lnTrackOrder, lnCartMRP, lnCartRetail;
    NebulaEditTextEcom editSearch;
    MyTextViewEcom tvTotalAmount, tvToolbarTitle, txtErrorContent, txtRetry, tvPvStatus, tvHome, tvTrackOrder, tvAccount;
    ConstraintLayout constraintLayoutPlaceOrder;
    MyBoldTextViewEcom txtErrorTitle;
    CardView cardView;
    MyTextViewEcom tvMycartItemPrice, tvShipingPrice, tvGrandTotal, tvGrandTotalPV, tvGrandTotalNV, tvMRP, tvMycartRetailPrice;
    NestedScrollView nestedScrollView;
    CheckBox cbFreeLookUp;


    private ArrayList<AddressModel> addressDataList = new ArrayList<>();
    ArrayList<MyCart> cartModels = new ArrayList<>();
    ArrayList<MyCartReview> cartModelsMyCartReviews = new ArrayList<>();
    private MyCartProductsAdapter myCartProductsAdapter;
    UtilsVersion utilsVersion = new UtilsVersion();

    SharedPreferences citySave, spGetIsAssociate;
    Intent notificationData, categoryData;
    View viewMRP;

    boolean isWaiveOff, isShowRetail, mrpisAssociate;
    Integer grandTotal, pvResult;
    String deviceId, uniqueID, version_name, strDeviceID, strSession;
    int version_code, cityId, selectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        mAPIInterface = APIClient.getClient(MyCartActivityNotification.this).create(APIInterface.class);
        session = new Session(this);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");
        /*TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        //checkVersion();
        utilsVersion.checkVersionFirsTime(this);

        initSharedPreference();
        notificationData = getIntent();
        if (notificationData != null) {
            strDeviceID = notificationData.getStringExtra("device_id");
            strSession = notificationData.getStringExtra("session_id");
            Log.e("strID", "strID " + strDeviceID);
            Log.e("strID", "strID " + strSession);

        }
        getMyCartList(strDeviceID, strSession);

        initUI();
        initOnClick();

        // tvTotalAmount.setText("9999");
        displayTotalPrice(0);
        // getMyCartList(deviceId,"69b68abb-9bc0-45b6-aa6a-f8727f9609bf");
        //  getMyCartList( deviceId, session.getIboKeyId() );

    }

    void initSharedPreference() {
        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        spGetIsAssociate = this.getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value
       // if (!showLocation) {
            cityId = 0;
       // }
        Log.d("MDEVICEID cart uniqueID", "MDEVICEIDcart uniqueID: " + uniqueID);
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
        Log.d("MDEVICEID Cart", "MDEVICEID cart: " + deviceId);

    }

    void initUI() {
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);
//getting the toolbar
        mProgressDialog = findViewById(R.id.progresbar);
        toolbar = findViewById(R.id.toolbar_search);
        imgBackArrow = findViewById(R.id.img_back);
        imgFav = findViewById(R.id.img_my_fav);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnProceed = findViewById(R.id.proceed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rvCartItem = findViewById(R.id.rv_myCartList);
        rvCartItem.setNestedScrollingEnabled(false);
        tvTotalAmount = findViewById(R.id.tv_payable_amount);
        constraintLayoutPlaceOrder = findViewById(R.id.cn_place_order);
        nestedScrollView = findViewById(R.id.nv_product_decs);

        tvTotalAmount = findViewById(R.id.tv_payable_amount);
        tvMycartItemPrice = findViewById(R.id.tv_mycart_item_price);
        tvShipingPrice = findViewById(R.id.tv_shiping_price);
        tvGrandTotal = findViewById(R.id.tv_grand_total_value);
        tvGrandTotalPV = findViewById(R.id.tv_mycart_pv);
        tvGrandTotalNV = findViewById(R.id.tv_mycart_nv);
        cardView = findViewById(R.id.card_view);
        lnPV = findViewById(R.id.ln_pv);
        tvPvStatus = findViewById(R.id.tv_pv_status);
        cbFreeLookUp = findViewById(R.id.chk_free_look_up);
        lnCartMRP = findViewById(R.id.ln_cart_mrp);
        lnCartRetail = findViewById(R.id.ln_cart_retail);
        viewMRP = findViewById(R.id.view_mrp);

        tvMRP = findViewById(R.id.tv_mycart_mrp_price);
        tvMycartRetailPrice = findViewById(R.id.tv_mycart_retail_price);

        lnHome = findViewById(R.id.ln_home);
        lnTrackOrder = findViewById(R.id.ln_myorder);
        lnAccount = findViewById(R.id.ln_my_account);

        imgMyAccount = findViewById(R.id.img_my_account);
        imgHome = findViewById(R.id.img_home);
        imgTrackOrder = findViewById(R.id.img_order);

        tvHome = findViewById(R.id.tv_home);
        tvAccount = findViewById(R.id.tv_my_account);
        tvTrackOrder = findViewById(R.id.tv_my_order);
        rvCartItem.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCartActivityNotification.this, LinearLayoutManager.VERTICAL, false);
        rvCartItem.setLayoutManager(mLayoutManager);
        rvCartItem.setItemAnimator(new DefaultItemAnimator());

        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose = toolbar.findViewById(R.id.img_search_close);
        editSearch = toolbar.findViewById(R.id.edt_search_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Cart");
    }

    void initOnClick() {
        String value = editSearch.getText().toString();
        imgSearch.setVisibility(View.GONE);
        imgSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value != null) {
                    editSearch.getText().clear();
                }
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlSearchView.setVisibility(View.VISIBLE);
            }
        });

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                if (myHandler != null) {
                    myHandler.removeCallbacksAndMessages(null);

                }
            }
        });

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MyCartActivityNotification.this, MyWishListActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });


        lnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHome.setColorFilter(ContextCompat.getColor(MyCartActivityNotification.this, R.color.black));
                tvHome.setTextColor(getResources().getColor(R.color.black));
                Intent login = new Intent(MyCartActivityNotification.this, MainActivity.class);

                login.putExtra("SELECTEDINNERHOME", 7);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        lnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMyAccount.setColorFilter(ContextCompat.getColor(MyCartActivityNotification.this, R.color.black));
                tvAccount.setTextColor(getResources().getColor(R.color.black));
                Intent login = new Intent(MyCartActivityNotification.this, MainActivity.class);
                login.putExtra("SELECTEDINNERACCOUNT", 8);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        lnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTrackOrder.setColorFilter(ContextCompat.getColor(MyCartActivityNotification.this, R.color.black));
                tvTrackOrder.setTextColor(getResources().getColor(R.color.black));
                Intent login = new Intent(MyCartActivityNotification.this, MainActivity.class);
                login.putExtra("SELECTEDINNERTRACKORDER", 9);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMyCartList(strDeviceID, strSession);
       /* categoryData = getIntent();
        if (categoryData != null) {
            selectCategory = categoryData.getIntExtra( "SELECTEDVALUECATEGORY", 0 );
            if (selectCategory == 10) {
                Toast.makeText(MyCartActivity.this,"Call Again ",Toast.LENGTH_SHORT).show();
                getMyCartList( deviceId, session.getIboKeyId() );
            }
        }*/
        // getMyCartListResume( deviceId, session.getIboKeyId() );
    }

    /*private void checkVersion() {
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

        if (Config.isFirstTime) {
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
                                            @SuppressLint("RestrictedApi") androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper( MyCartActivityNotification.this, R.style.Update_Dialog));
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
                Config.isFirstTime = false;
            }
        }
    }*/


    private void getMyCartList(String deviceId, String userId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, userId, cityId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("CartResponseNo", "CartResponseNo11: " + response.body());

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    cartModels.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPINo", "CartAPINo: " + response);
                            Log.d("CartResponseNo", "CartResponseNo200: " + response.body());

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                                cbFreeLookUp.setVisibility(View.GONE);
                                constraintLayoutPlaceOrder.setVisibility(View.GONE);
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModels.addAll(response.body().getData().getCart());
                                Log.d("CartAPIInNo", "CartAPIInNo: " + response.body().getData().getCart().size());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCartActivityNotification.this);
                                rvCartItem.setLayoutManager(mLayoutManager);
                                rvCartItem.setItemAnimator(new DefaultItemAnimator());
                                myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivityNotification.this, cartModels, MyCartActivityNotification.this,
                                        MyCartActivityNotification.this);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvCartItem.setAdapter(myCartProductsAdapter);
                                tvTotalAmount.setText("₹ " + response.body().getData().getGrandTotal());

                                tvMycartItemPrice.setText("" + response.body().getData().getSubTotal());
                                tvShipingPrice.setText("" + response.body().getData().getShippingCharge());
                                tvGrandTotal.setText("" + response.body().getData().getGrandTotal());
                                tvGrandTotalPV.setText("" + response.body().getData().getTotalPV());
                                tvGrandTotalNV.setText("" + response.body().getData().getTotalNV());
                                tvMRP.setText("" + response.body().getData().getMrp());
                                tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvMycartRetailPrice.setText("" + response.body().getData().getRetailProfit());


                                pvResult = response.body().getData().getTotalPV();
                                grandTotal = response.body().getData().getGrandTotal();
                                Log.d("totalCartAmountGETNo", "totalCartAmountGETNo " + grandTotal);
                                Log.d("SKU CartNo ", "SKU CartNo: " + cartModels.toString());

                                isShowRetail = response.body().getData().isShowRetail();

                                if (isShowRetail == false) {
                                    lnCartMRP.setVisibility(View.GONE);
                                    lnCartRetail.setVisibility(View.GONE);
                                    viewMRP.setVisibility(View.GONE);
                                } else {
                                    lnCartMRP.setVisibility(View.VISIBLE);
                                    lnCartRetail.setVisibility(View.VISIBLE);
                                    viewMRP.setVisibility(View.VISIBLE);
                                }


                                if (pvResult < Const.PVValue && mrpisAssociate) {
                                    tvPvStatus.setVisibility(View.VISIBLE);
                                    tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");

                                } else if (pvResult == 0 || pvResult == null) {
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                                            .getLayoutParams();

                                    layoutParams.setMargins(0, 0, 0, 100);
                                    nestedScrollView.setLayoutParams(layoutParams);
                                    tvPvStatus.setVisibility(View.GONE);
                                } else {
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                                            .getLayoutParams();

                                    layoutParams.setMargins(0, 0, 0, 100);
                                    nestedScrollView.setLayoutParams(layoutParams);
                                    tvPvStatus.setVisibility(View.GONE);

                                }

                                myHandler = new Handler();
                                myRunnable = new Runnable() {
                                    @Override
                                    public void run() {

                                        // if (pvResult != null) {
                                        if (pvResult < Const.PVValue && mrpisAssociate) {
                                            alertLessOne();
                                            // paymentFail();
                                        } else if (pvResult > Const.PVValue && pvResult < Const.PVValueMax && mrpisAssociate) {
                                            alertLessTwo();
                                            // paymentFail();
                                        } else if (pvResult >= Const.PVValueMax && mrpisAssociate) {
                                            alertLessThree();
                                            // paymentFail();
                                        }
                                        //}

                                    }
                                };
                                myHandler.postDelayed(myRunnable, 1500);


                                Log.d("pvResultNo", "pvResultNo: " + pvResult);
                                android.util.Log.e("CartListAPINo", "CartListAPINo: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                                cardView.setVisibility(View.GONE);
                                lnPV.setVisibility(View.GONE);
                                cbFreeLookUp.setVisibility(View.GONE);
                                constraintLayoutPlaceOrder.setVisibility(View.GONE);
                                noRecordsFound();
                            }
                            if (cartModels.size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                                rvCartItem.setVisibility(View.VISIBLE);
                                cardView.setVisibility(View.VISIBLE);
                                lnPV.setVisibility(View.VISIBLE);
                                //cbFreeLookUp.setVisibility( View.VISIBLE );
                                constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
                            } else {
                                noRecordsFound();
                            }
                        }
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            // serviceUnavailable();
                            cardView.setVisibility(View.GONE);
                            lnPV.setVisibility(View.GONE);
                            cbFreeLookUp.setVisibility(View.GONE);
                            constraintLayoutPlaceOrder.setVisibility(View.GONE);
                            noRecordsFound();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("CartAPINo", "CartAPINo: " + t);
                    Log.d("CartResponseNo", "CartResponseFilNo: " + t);
                    Log.d("CartResponse 2No", "CartResponse 2No: " + cartModels.size());

                    cardView.setVisibility(View.GONE);
                    lnPV.setVisibility(View.GONE);
                    cbFreeLookUp.setVisibility(View.GONE);
                    constraintLayoutPlaceOrder.setVisibility(View.GONE);
                    noRecordsFound();

                }
            });
        } else {
            // noInternetConnection();
        }
    }


    private void getMyCartListResume(String deviceId, String userId) {
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
                                noRecordsFound();
                                cbFreeLookUp.setVisibility(View.GONE);
                                constraintLayoutPlaceOrder.setVisibility(View.GONE);
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModels.addAll(response.body().getData().getCart());
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCartActivityNotification.this);
                                rvCartItem.setLayoutManager(mLayoutManager);
                                rvCartItem.setItemAnimator(new DefaultItemAnimator());
                                myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivityNotification.this, cartModels, MyCartActivityNotification.this,
                                        MyCartActivityNotification.this);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvCartItem.setAdapter(myCartProductsAdapter);
                                tvTotalAmount.setText("₹ " + response.body().getData().getGrandTotal());

                                tvMycartItemPrice.setText("" + response.body().getData().getSubTotal());
                                tvShipingPrice.setText("" + response.body().getData().getShippingCharge());
                                tvGrandTotal.setText("" + response.body().getData().getGrandTotal());
                                tvGrandTotalPV.setText("" + response.body().getData().getTotalPV());
                                tvGrandTotalNV.setText("" + response.body().getData().getTotalNV());
                                tvMRP.setText("" + response.body().getData().getMrp());
                                tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvMycartRetailPrice.setText("" + response.body().getData().getRetailProfit());


                                pvResult = response.body().getData().getTotalPV();
                                grandTotal = response.body().getData().getGrandTotal();
                                Log.d("totalCartAmountGET", "totalCartAmountGET " + grandTotal);
                                Log.d("SKU Cart ", "SKU Cart: " + cartModels.toString());

                                isShowRetail = response.body().getData().isShowRetail();

                                if (isShowRetail == false) {
                                    lnCartMRP.setVisibility(View.GONE);
                                    lnCartRetail.setVisibility(View.GONE);
                                    viewMRP.setVisibility(View.GONE);
                                } else {
                                    lnCartMRP.setVisibility(View.VISIBLE);
                                    lnCartRetail.setVisibility(View.VISIBLE);
                                    viewMRP.setVisibility(View.VISIBLE);
                                }


                                if (pvResult < Const.PVValue && mrpisAssociate) {
                                    tvPvStatus.setVisibility(View.VISIBLE);
                                    tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");

                                } else if (pvResult == 0 || pvResult == null) {
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                                            .getLayoutParams();

                                    layoutParams.setMargins(0, 0, 0, 100);
                                    nestedScrollView.setLayoutParams(layoutParams);
                                    tvPvStatus.setVisibility(View.GONE);
                                } else {
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                                            .getLayoutParams();

                                    layoutParams.setMargins(0, 0, 0, 100);
                                    nestedScrollView.setLayoutParams(layoutParams);
                                    tvPvStatus.setVisibility(View.GONE);

                                }

                              /*  myHandler = new Handler();
                                myRunnable = new Runnable() {
                                    @Override
                                    public void run() {

                                        if (pvResult < 2500 && mrpisAssociate ==true) {
                                            alertLessOne();

                                        } else if (pvResult > 2500 && pvResult < 5000 && mrpisAssociate ==true) {
                                            alertLessTwo();

                                        } else if (pvResult >= 5000 && mrpisAssociate ==true) {
                                            alertLessThree();

                                        }

                                    }
                                };
                                myHandler.postDelayed( myRunnable, 1500 );*/


                                Log.d("pvResult", "pvResult: " + pvResult);
                                android.util.Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                                cardView.setVisibility(View.GONE);
                                lnPV.setVisibility(View.GONE);
                                cbFreeLookUp.setVisibility(View.GONE);
                                constraintLayoutPlaceOrder.setVisibility(View.GONE);
                                noRecordsFound();
                            }
                            if (cartModels.size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                                rvCartItem.setVisibility(View.VISIBLE);
                                cardView.setVisibility(View.VISIBLE);
                                lnPV.setVisibility(View.VISIBLE);
                                //cbFreeLookUp.setVisibility( View.VISIBLE );
                                constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
                            } else {
                                noRecordsFound();
                            }
                        }
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            // serviceUnavailable();
                            cardView.setVisibility(View.GONE);
                            lnPV.setVisibility(View.GONE);
                            cbFreeLookUp.setVisibility(View.GONE);
                            constraintLayoutPlaceOrder.setVisibility(View.GONE);
                            noRecordsFound();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("CartAPI", "CartAPI: " + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                    Log.d("CartResponse 2", "CartResponse 2: " + cartModels.size());

                    cardView.setVisibility(View.GONE);
                    lnPV.setVisibility(View.GONE);
                    cbFreeLookUp.setVisibility(View.GONE);
                    constraintLayoutPlaceOrder.setVisibility(View.GONE);
                    noRecordsFound();

                }
            });
        } else {
            // noInternetConnection();
        }
    }

    @Override
    public void onMethodCallbackMain() {

        if (cartModels.size() > 0) {
            //  cbFreeLookUp.setVisibility( View.VISIBLE );
            constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
        } else {
            cbFreeLookUp.setVisibility(View.GONE);
            constraintLayoutPlaceOrder.setVisibility(View.GONE);
            noRecordsFound();

        }

        getMyCartListCallBack(deviceId, session.getIboKeyId());
        //  displayTotalPrice(d);

    }

    private void displayTotalPrice(double price) {

        tvTotalAmount = (MyTextViewEcom) findViewById(R.id.tv_payable_amount);

        tvTotalAmount.setText("₹ " + price);
        android.util.Log.d("Total Delete Price", "Total Delete Price: " + price);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyCartListReview(String.valueOf(cityId));
            }
        });
    }


    @Override
    public void onMethodPayCallbackMain(double d) {

    }

    void noRecordsFound() {
        txtErrorTitle.setText("Cart is Empty");
        txtErrorContent.setText("Looks like you have no items in your shopping cart.");
        imgError.setImageResource(R.drawable.ic_shopping_cart);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        rvCartItem.setVisibility(View.GONE);
    }


    private void getMyCartListCallBack(String deviceId, String userId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
           /* mProgressDialog = new ProgressDialog(MyCartActivity.this, R.style.MyTheme);
            mProgressDialog.show();

            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode==KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if(mProgressDialog.isShowing()) {
                            //your logic here for back button pressed event
                            mProgressDialog.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            });*/


            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, userId, cityId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());

                    /*if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                    cartModels.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            Log.d("CartResponse", "CartResponse200: " + response.body());

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();
                                cbFreeLookUp.setVisibility(View.GONE);
                                constraintLayoutPlaceOrder.setVisibility(View.GONE);
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModels.addAll(response.body().getData().getCart());
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCartActivityNotification.this);
                                rvCartItem.setLayoutManager(mLayoutManager);
                                rvCartItem.setItemAnimator(new DefaultItemAnimator());
                                myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivityNotification.this, cartModels, MyCartActivityNotification.this,
                                        MyCartActivityNotification.this);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvCartItem.setAdapter(myCartProductsAdapter);
                                tvTotalAmount.setText("" + response.body().getData().getGrandTotal());

                                tvMycartItemPrice.setText("" + response.body().getData().getSubTotal());
                                tvShipingPrice.setText("" + response.body().getData().getShippingCharge());
                                tvGrandTotal.setText("" + response.body().getData().getGrandTotal());
                                tvGrandTotalPV.setText("" + response.body().getData().getTotalPV());
                                tvGrandTotalNV.setText("" + response.body().getData().getTotalNV());
                                tvMRP.setText("" + response.body().getData().getMrp());
                                tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvMycartRetailPrice.setText("" + response.body().getData().getRetailProfit());
                                android.util.Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));


                                isShowRetail = response.body().getData().isShowRetail();

                                if (isShowRetail == false) {
                                    lnCartMRP.setVisibility(View.GONE);
                                    lnCartRetail.setVisibility(View.GONE);
                                    viewMRP.setVisibility(View.GONE);
                                } else {
                                    lnCartMRP.setVisibility(View.VISIBLE);
                                    lnCartRetail.setVisibility(View.VISIBLE);
                                    viewMRP.setVisibility(View.VISIBLE);
                                }


                                pvResult = response.body().getData().getTotalPV();
                                grandTotal = response.body().getData().getGrandTotal();
                                if (pvResult < Const.PVValue && mrpisAssociate) {
                                    tvPvStatus.setVisibility(View.VISIBLE);
                                    tvPvStatus.setText("PV value in your cart: " + pvResult + ". Add items worth " + Const.PVValue + " PV to buy the items at discounted Nebula IBO Price.");

                                } else if (pvResult == 0 || pvResult == null) {
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                                            .getLayoutParams();

                                    layoutParams.setMargins(0, 0, 0, 100);
                                    nestedScrollView.setLayoutParams(layoutParams);
                                    tvPvStatus.setVisibility(View.GONE);
                                } else {
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nestedScrollView
                                            .getLayoutParams();

                                    layoutParams.setMargins(0, 0, 0, 100);
                                    nestedScrollView.setLayoutParams(layoutParams);
                                    tvPvStatus.setVisibility(View.GONE);

                                }
                                // checkFirstRunPV();

                               /* boolean isFirstRun = getSharedPreferences(PrefUtils.prefIsfirstpv, MODE_PRIVATE).getBoolean(PrefUtils.prefIsfirstpv, true);
                                if (isFirstRun) {
                                    // Place your dialog code here to display the dialog
                                    if (pvResult > 2500 && pvResult < 5000) {
                                        alertLessTwo();
                                    }
                                    getSharedPreferences(PrefUtils.prefIsfirstpv, MODE_PRIVATE)
                                            .edit()
                                            .putBoolean(PrefUtils.prefIsfirstpv, false)
                                            .apply();
                                }*/


                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (cartModels.size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                                rvCartItem.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    //  mProgressDialog.dismiss();
                    Log.d("CartAPI", "CartAPI: " + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                    Log.d("CartResponse 2", "CartResponse 2: " + cartModels.size());

                    cardView.setVisibility(View.GONE);
                    lnPV.setVisibility(View.GONE);
                    cbFreeLookUp.setVisibility(View.GONE);
                    constraintLayoutPlaceOrder.setVisibility(View.GONE);
                    noRecordsFound();

                }
            });
        } else {
            // noInternetConnection();
        }
    }

    private void paymentFail() {

        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivityNotification.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                alertDialog.setCancelable(false);
                //textTitle.setText(response.body().getMessage());
                textTitle.setText("Payment Failed");
                textContent.setText("Your total PV for this order is " + pvResult + ". No NV is generated on this order. Add products worth total " + Const.PVValue + " PV on this order to avail guaranteed 100 NV and you will also get these product at a discounted Nebula IBO Price.");
                btnConfirm.setText("OK");
                // textContent.setText("Pin you have entered is Invalid.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            loading.show();
                        } catch (Exception e) {
                            // WindowManager$BadTokenException will be caught and the app would not display
                            // the 'Force Close' message
                        }
                    }
                });
            }
        });

        try {
            loading.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
        }
    }

    private void alertLessOne() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivityNotification.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setCancelText("Cancel");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
                TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
                Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
                Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                btnCancel.setTypeface(typeface);
                textTitle.setText("PV: " + pvResult);
                btnConfirm.setBackgroundResource(R.drawable.blue_button_background);
                btnConfirm.setText("Shop More");
                btnCancel.setText("Ignore");
                textContent.setText("Your total PV for this order is " + pvResult + ". No NV is generated on this order. Add products worth total " + Const.PVValue + " PV on this order to avail guaranteed 100 NV and you will also get these product at a discounted Nebula IBO Price.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MyCartActivityNotification.this, MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

                        loading.dismiss();

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        try {
            //  loading.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
        }
    }


    private void alertLessTwo() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivityNotification.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("Shop More");
        loading.setCancelText("Ignore");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
                TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
                Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
                Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                btnCancel.setTypeface(typeface);
                textTitle.setText("PV: " + pvResult);
                btnConfirm.setBackgroundResource(R.drawable.blue_button_background);
                btnConfirm.setText("Shop More");
                btnCancel.setText("Ignore");
                textContent.setText("Your total PV for this order is " + pvResult + ". Purchase products worth " + Const.PVValueMax + " PV to earn upto 1Lakh Community Growth Income every week.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MyCartActivityNotification.this, MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);


                        loading.dismiss();

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        try {
            loading.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
        }
    }


    private void alertLessThree() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivityNotification.this, SweetAlertDialogCart.SUCCESS_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("Shop More");
        loading.setCancelText("Ignore");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
                TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
                Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
                Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                btnCancel.setTypeface(typeface);
                btnCancel.setVisibility(View.GONE);
                textTitle.setText("PV: " + pvResult);
                btnConfirm.setBackgroundResource(R.drawable.blue_button_background);
                btnConfirm.setText("OK");
                btnCancel.setText("Ignore");
                textContent.setText("Congratulation, you can now earn upto 1 Lakh Community Growth Income weekly.\n Kindly checkout and complete your payment.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        loading.dismiss();

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        loading.show();
    }


    @Override
    public void onBackPressed() {

        if (selectCategory == 10) {
            Intent cartReview = new Intent(MyCartActivityNotification.this, MyCategoryActivity.class);
            cartReview.putExtra("SELECTEDVALUECATEGORYBACK", 11);
            startActivity(cartReview);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            super.onBackPressed();

        }
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);

        }


        SharedPreferences FirstRunPV = getSharedPreferences(PrefUtils.prefIsfirstpv, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = FirstRunPV.edit();
        edit.clear();
        edit.apply();
    }


    public void checkFirstRunPV() {
        boolean isFirstRun = getSharedPreferences(PrefUtils.prefIsfirstpv, MODE_PRIVATE).getBoolean(PrefUtils.prefIsfirstpv, true);
        if (isFirstRun) {
            // Place your dialog code here to display the dialog
            if (pvResult > Const.PVValue && pvResult < Const.PVValueMax) {
                alertLessTwo();
            }
            getSharedPreferences(PrefUtils.prefIsfirstpv, MODE_PRIVATE)
                    .edit()
                    .putBoolean(PrefUtils.prefIsfirstpv, false)
                    .apply();
        }
    }

    public class MyCartProductsAdapter extends RecyclerView.Adapter<MyCartProductsAdapter.MyViewHolder> {

        private Context context;
        //private MyCartProductsAdapter.ProductsAdapterListener listener;
        ArrayList<MyCart> myCartsModel = new ArrayList<>();

        private APIInterface mAPIInterface;
        String m_deviceId;
        String uniqueID;
        int addvalue;
        int productQuantity, productMaxSaleQuantity;
        private CartAdapterCallback mAdapterCallback;
        private CartAdapterPayCallback cartAdapterPayCallback;
        Session session;
        double pvResultAdapter;
        boolean mrpisAssociate, mrpisJoin;
        SharedPreferences spGetIsAssociate, spGetIsFirstPurchase, spGetIsJoin;


        //MyCart myCart;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_mycart_name)
            MyTextViewEcom tvMyCartName;


            MyTextViewEcom tvMyCartItemPrice;

            @BindView(R.id.product_count)
            MyTextViewEcom tvProductCount;

            @BindView(R.id.tv_mycart_tablet)
            MyTextViewEcom tvMyCartTablet;

            @BindView(R.id.thumbnail)
            ImageView thumbnail;


            @BindView(R.id.img_my_cart_delete)
            ImageView imgDelete;

            @BindView(R.id.btn_remove_item)
            MyButtonEcom btnRemoveItem;

            @BindView(R.id.btn_add_item)
            MyButtonEcom btnAddItem;


            @BindView(R.id.card_view)
            CardView cardView;

            @BindView(R.id.tv_pv_value)
            MyTextViewEcom tvCartPV;

            @BindView(R.id.tv_nv_value)
            MyTextViewEcom tvCartNV;

            @BindView(R.id.tv_bv_value)
            MyTextViewEcom tvCartBV;

            @BindView(R.id.tv_original_price_value)
            MyTextViewEcom tvMRPPrice;


            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                tvMyCartItemPrice = (MyTextViewEcom) view.findViewById(R.id.tv_mycart_item_price);
            }
        }


        public MyCartProductsAdapter(Context context, ArrayList<MyCart> myCartsModel, CartAdapterCallback callback,
                                     CartAdapterPayCallback cartAdapterPayCallback) {
            // public MyCartProductsAdapter(Context context, ArrayList<MyCart> myCartsModel) {
            this.context = context;
            this.myCartsModel = myCartsModel;
            // this.listener = listener;
            this.mAdapterCallback = callback;
            this.cartAdapterPayCallback = cartAdapterPayCallback;
            mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
        /*TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/

            m_deviceId = android.provider.Settings.Secure.getString(
                    context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            session = new Session(context);
            SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
            uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

            Log.d("MDEVICEID uniqueIDADP", "MDEVICEID uniqueIDADP: " + uniqueID);
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
            Log.d("MDEVICEIDMYCARTADAP", "MDEVICEIDMYCARTADAP: " + m_deviceId);
        }

        @Override
        public MyCartProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_cart_list_item, parent, false);

            return new MyCartProductsAdapter.MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //getting the product of the specified position
            MyCart myCart = myCartsModel.get(position);
            //binding the data with the viewholder views

            holder.tvMyCartName.setText(myCart.getProductName());

            holder.tvProductCount.setText(String.valueOf(myCart.getCartQuantity()));
            holder.tvMyCartTablet.setText(String.valueOf(myCart.getVolwt()));


            holder.tvCartPV.setText("" + myCart.getCategoryPV());
            holder.tvCartBV.setText("" + myCart.getCategoryBV() + "%");
            holder.tvCartNV.setText("" + myCart.getCategoryNV());

            spGetIsAssociate = context.getSharedPreferences(PrefUtils.prefMrp, 0);
            mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

            spGetIsJoin = context.getSharedPreferences(PrefUtils.prefJoindays, 0);
            mrpisJoin = spGetIsJoin.getBoolean(PrefUtils.prefJoindays, false);

            pvResultAdapter = Double.parseDouble(myCart.getCategoryPV());


            if (pvResult >= Const.PVValue && !mrpisJoin) {
                holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getPricePerPiece()));
                holder.tvMRPPrice.setText(String.valueOf(myCart.getCartMRPPrice()));
                holder.tvMRPPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else if (!mrpisAssociate) {
                holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getPricePerPiece()));
                holder.tvMRPPrice.setText(String.valueOf(myCart.getCartMRPPrice()));
                holder.tvMRPPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvMRPPrice.setVisibility(View.GONE);
                holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getCartMRPPrice()));
            }
//        double productPerPrice = myCart.getPricePerPiece();
//        Integer cartAddedQuantity = myCart.getCartQuantity();


            //holder.tvCount.setText("1");


            Glide.with(context)
                    .load(myCart.getImageURL()).fitCenter()
                    .placeholder(getRandomDrawbleColor())
                    .into(holder.thumbnail);

            holder.btnAddItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
               /* int addvalue = 0;
                addvalue = Integer.parseInt(holder.tvProductCount.getText().toString());
                Log.d("addval", "addval "+addvalue);
                addvalue++;
                addToCart(m_deviceId,"", Integer.valueOf(myCart.getProductId()),addvalue);
                holder.tvProductCount.setText(Integer.toString(addvalue));*/
//                addvalue = 1;
                    productQuantity = Integer.parseInt(myCart.getStockQuantity());
                    addvalue = Integer.parseInt(holder.tvProductCount.getText().toString());
                    Log.d("addval", "addval " + addvalue);

//                int maxQuantity = 0;
                    int maxQuantity = productQuantity;


                /*if(productMaxSaleQuantity < productQuantity){
                    maxQuantity = productMaxSaleQuantity;
                } else {
                    maxQuantity = productQuantity;
                }*/

                    if (addvalue < maxQuantity) {
                        addvalue++;
                        holder.tvProductCount.setText(Integer.toString(addvalue));
                        Log.d(TAG, "product Id and quantity: " + myCart.getProductId() + "quantity " + addvalue);
                        //addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), addvalue);
                        addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 1, "plus");

                    } else {
                        Toast.makeText(context, "Sorry you have reached max quantity.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int curval = Integer.parseInt(holder.tvProductCount.getText().toString());
                    Log.d("curval", "curval " + curval);
                    if (curval > 1) {
                        curval--;
                        holder.tvProductCount.setText(Integer.toString(curval));
                        // addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), curval);
                        addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 1, "minus");

                    }
                }
            });


            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  deleteFromCart(m_deviceId,session.getIboKeyId() ,Integer.valueOf(myCart.getProductId()));
                    deleteFromCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), myCart.getID());

                }
            });


        }


        @Override
        public int getItemCount() {

            Log.d("myCartsModel.size()", "myCartsModel.size():" + myCartsModel.size());
            return myCartsModel.size();


        }


        private void addToCart(String deviceId, String userId, Integer productId, Integer quantity, String CartFlag) {
            final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
            mProgressDialog.show();

            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if (mProgressDialog.isShowing()) {
                            //your logic here for back button pressed event
                            mProgressDialog.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            });
            Call<AddToCartModel> wsCallingRegistation = mAPIInterface.getAddToCartModelCall(deviceId, userId, productId, quantity, CartFlag);
            Log.d("quantityOUT: ", "quantityOUT: " + quantity);
            wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("quantityINBEfore: ", "quantityINBEfore: " + quantity);
                            mAdapterCallback.onMethodCallbackMain();

                            Log.d("quantityINAFTER: ", "quantityINAFTER: " + quantity);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    mProgressDialog.dismiss();
                }
            });
        }


        private void deleteFromCart(String deviceId, String userId, Integer productId, Integer id) {
            final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
            mProgressDialog.show();

            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if (mProgressDialog.isShowing()) {
                            //your logic here for back button pressed event
                            mProgressDialog.dismiss();

                        }
                        return true;
                    }
                    return false;
                }
            });
            Call<DeleteCartModel> wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCall(deviceId, userId, productId);
            wsCallingRegistation.enqueue(new Callback<DeleteCartModel>() {
                @Override
                public void onResponse(Call<DeleteCartModel> call, Response<DeleteCartModel> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Total Delete APISuccess", "Total Delete APISuccess: " + productId);
                            mAdapterCallback.onMethodCallbackMain();
                            Log.d("Total DeleteAPISuccess1", "Total DeleteAPISuccess1: " + productId);

                        }
                    }
                }

                @Override
                public void onFailure(Call<DeleteCartModel> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.d("Total Delete Failure", "Total Delete Failure: " + productId + "Error: " + t);
                }
            });
        }

        public void removeAt(int position) {
            myCartsModel.remove(position);
            notifyItemRemoved(position);
            //notifyItemRangeChanged(position, myCartsModel.size());
        }


        private ColorDrawable[] vibrantLightColorList =
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


    private void getInventory() {

        List<String> sku = new ArrayList<>();
        for (int i = 0; i < cartModels.size(); i++) {
            sku.add(cartModels.get(i).getCartMRPSKU());
        }

        mAPIInterface = APIClient.getClientUniCommmerce(MyCartActivityNotification.this).create(APIInterface.class);
        Log.e("SKU Response1", "SKU Response1: " + sku);
        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel();
        inventoryRequestModel.setItemTypeSKU(sku);
        // inventoryModel.setInventory(1440);

        Log.e("SKU Response132323", "SKU Response132323: " + sku);
        Call<InventoryModel> wsCallingRegistation = mAPIInterface.postInventory(inventoryRequestModel);
        wsCallingRegistation.enqueue(new Callback<InventoryModel>() {
            @Override
            public void onResponse(Call<InventoryModel> call, Response<InventoryModel> response) {
                Log.e("SKU Response2", "SKU Response2: " + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {
                    if (response.body().getSuccessful()) {

                        if (response.body().getData() != null) {
                            Log.e("SKU Response31", "SKU Response3: " + response.body());
                            Log.e("SKU Response3", "SKU Response3: " + new Gson().toJson(response.body()));
                            Log.e("SKU Response3", "SKU Response32: " + new Gson().toJson(response.body().getData().size()));

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                String itemSku = response.body().getData().get(i).getItemTypeSKU();
                                Integer itemInventory = response.body().getData().get(i).getInventory();
                                String itemStock = "outStock";
                                Log.e("SKU Response33", "SKU Response33: " + itemSku);
                                Log.e("SKU Response34", "SKU Response34: " + itemInventory);

                                if (itemInventory != 0) {
                                    itemStock = "inStock";
                                }
                                Log.e("SKU Response35", "SKU Response35: " + itemStock);
                            }
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


    private void getMyCartListReview(String cityID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mAPIInterfaceCartReview = APIClient.getClient(MyCartActivityNotification.this).create(APIInterface.class);
            mProgressDialog.setVisibility(View.VISIBLE);


            Call<CartListModelReviewEcom> wsCallingEvents = mAPIInterfaceCartReview.getMyCartListReviewEcom(cityID);
            wsCallingEvents.enqueue(new Callback<CartListModelReviewEcom>() {
                @Override
                public void onResponse(Call<CartListModelReviewEcom> call, Response<CartListModelReviewEcom> response) {
                    Log.d("CartAPIReview1", "CartAPIReview1: " + response.body());

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    cartModelsMyCartReviews.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPIReview2", "CartAPIReview2: " + response);
                            Log.d("CartResponse2", "CartResponse2: " + response.body());

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {


                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModelsMyCartReviews.addAll(response.body().getData().getOutOfStockProducts());
                                Log.d("CartAPIReview4", "CartAPIReview4: " + response.body().getData().getOutOfStockProducts());


                                if (response.body().getData().getCartCount() == 0) {

                                    Intent addressListIntent = new Intent(MyCartActivityNotification.this, OrderSummaryActivity.class);
                                    if (grandTotal != null) {
                                        addressListIntent.putExtra("cartTotalPrice", grandTotal);
                                        Log.d("totalCartAmountP", "totalCartAmountP " + grandTotal);
                                    }
                                    // Log.d("totalCartAmountP","totalCartAmountP "+ price);
                                    addressListIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(addressListIntent);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    Log.d("AddressListGETINData", "AddressListGETINData: " + response.body().getData());


                                } else {
                                    Intent cartReview = new Intent(MyCartActivityNotification.this, ProductAvaibilityActivity.class);
                                    startActivity(cartReview);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                                }

                                android.util.Log.e("CartAPIReview4 Res", "CartAPIReview4 Res: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelReviewEcom> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("CartAPIReview5", "CartAPIReview5 " + t);

                }
            });
        } else {
            // noInternetConnection();
        }
    }


}
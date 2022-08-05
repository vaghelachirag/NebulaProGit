package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.FreeProductsListAdapter;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelReviewEcom;
import com.nebulacompanies.ibo.ecom.model.DeleteCartModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductListModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductsModel;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyCartData;
import com.nebulacompanies.ibo.ecom.model.MyCartReview;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterPayCallback;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.ProductDecsDetailsDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionFail;
import static com.nebulacompanies.ibo.ecom.utils.Utils.actionUservalid;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MyCartActivity extends AppCompatActivity
        implements CartAdapterCallback, CartAdapterPayCallback,
        ProductDecsDetailsDialogFragment.ItemClickListener {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Typeface typeface;
    private Handler myHandler;
    private Runnable myRunnable;
    Session session;
    String addressNameSave;
    Toolbar toolbar;
    private RelativeLayout llEmpty;
    ImageView imgBackArrow, imgFav, imgSearch, imgSearchClose, imgMyAccount, imgHome,
            imgTrackOrder, imgError, imgClose, imgRewardClose;
    Spinner spinner;
    MyButtonEcom btnAddToCart, btnProceed, btnConfirm, btnConfirmReward;
    ConstraintLayout constraintLayoutPlaceOrder;
    LinearLayout lnPV, lnHome, lnAccount, lnTrackOrder, lnCartDetails, lnCartMRP, lnCartRetail, lnLocation;
    RelativeLayout rlSearchView;
    NebulaEditTextEcom editSearch, editcartqnty;
    private MyTextViewEcom txtErrorContent, txtRetry, tvPvStatus, tvHome, tvTrackOrder, tvAccount, tvLocation;
    MyBoldTextViewEcom txtErrorTitle;
    CardView cardView;
    MyTextViewEcom tvTotalAmount, tvToolbarTitle, tvMycartItemPrice, tvShipingPrice, tvGrandTotal, tvGrandTotalPV, tvGrandTotalNV, tvMRP, tvMycartRetailPrice;
    NestedScrollView nestedScrollView;
    RecyclerView rvFreeProducts, rvRewardFreeProducts, rvCartItem;
    AlertDialog dialogFreeProducts, dialogCart, dialogRewardFreeProducts;
    CheckBox cbFreeLookUp;

    ArrayList<MyCart> cartModels = new ArrayList<>();
    ArrayList<FreeProductListModel.Datum> freeProductModels = new ArrayList<>();
    ArrayList<FreeProductListModel.Datum> freeRewardProductModels = new ArrayList<>();
    ArrayList<MyCartReview> cartModelsMyCartReviews = new ArrayList<>();
    private FreeProductsListAdapter freeProductsListAdapter;
    private FreeProductsListAdapter freeRewardProductsListAdapter;
    UtilsVersion utilsVersion = new UtilsVersion();
    Utils utils = new Utils();
    SharedPreferences citySave, spGetIsAssociate, sharedPreferencesAddressType;
    View viewMRP;
    double subtotal, totalPV;
    Intent cartNotification;
    String deviceId, uniqueID;
    int cityId, backID, selectCategory, promoid;
    Integer pvResult, grandTotal;
    boolean firsttime = true;
    ProgressDialog mLoadProgressDialog, mProcessDiaoug;
    boolean isAddressType, isServicable, isShowRetail, mrpisAssociate;
    boolean checkAddress = false;
    ArrayList<Long> rewardVariantID = new ArrayList<>();
    ArrayList<MyCart> variantModels = new ArrayList<>();
    int variantIDPos = 0;
    MyCart mycartvariant;
    CartListModelEcom responseData;
    boolean callFree = true;
    String pvslab = "", totalslab = "";
    Snackbar snackbar = null;
    MyTextViewEcom txtTitle, txtTitleReward;
    String[] cartqnty = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        session = new Session(this);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");
        mAPIInterface = APIClient.getClient(MyCartActivity.this).create(APIInterface.class);
        cartNotification = getIntent();
        if (cartNotification != null) {
            backID = cartNotification.getIntExtra("session_back", 0);
        }
        initFullDialog();
        initUI();
        initSharedPreference();
        //initDialog();
        initDialogFreeProducts();
        initRewardDialogFreeProducts();
        initDialogCarrt();
        displayTotalPrice(0);
        initOnClick();
        setDataUI();
    }

    public void initFullDialog() {
        mLoadProgressDialog = new ProgressDialog(MyCartActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    public void showFullDialog() {
        if (mLoadProgressDialog != null && !mLoadProgressDialog.isShowing()) {
            //showLog("full dialog==", "SHOW===");
            mLoadProgressDialog.show();
        }
    }

    public void hideFullDialog() {
        if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing()) {
           // showLog("full dialog==", "HIDE===");
            mLoadProgressDialog.dismiss();
        }
    }
    @SuppressLint("HardwareIds")
    void initSharedPreference() {
        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        spGetIsAssociate = this.getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

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
        lnCartDetails = findViewById(R.id.ln_cart_price_details);
        tvLocation = findViewById(R.id.tv_location);
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvCartItem.setLayoutManager(mLayoutManager);
        rvCartItem.setItemAnimator(new DefaultItemAnimator());

        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose = toolbar.findViewById(R.id.img_search_close);
        editSearch = toolbar.findViewById(R.id.edt_search_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Cart");
        lnLocation = findViewById(R.id.ln_location);
        mLoadProgressDialog = new ProgressDialog(MyCartActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");

        IntentFilter filter = new IntentFilter();
        filter.addAction(actionUservalid);
        filter.addAction(actionFail);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(actionUservalid)) {
                openOrderSummary();
            } else if (intent.getAction().equalsIgnoreCase(actionFail)) {
                Toast.makeText(MyCartActivity.this, "There is an error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void showLocationAdd() {
        ProductDecsDetailsDialogFragment addPhotoBottomDialogFragment =
                ProductDecsDetailsDialogFragment.newInstance();

        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ProductDecsDetailsDialogFragment.TAG);
    }

    void initOnClick() {
        String value = editSearch.getText().toString();

        imgSearch.setVisibility(View.GONE);
        imgSearchClose.setOnClickListener(view -> {
            editSearch.getText().clear();
        });

        imgSearch.setOnClickListener(view -> rlSearchView.setVisibility(View.VISIBLE));
        imgBackArrow.setOnClickListener(view -> {
            onBackPressed();
            if (myHandler != null) {
                myHandler.removeCallbacksAndMessages(null);
            }
        });

        imgFav.setOnClickListener(view -> {
            Intent login = new Intent(MyCartActivity.this, MyWishListActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        lnHome.setOnClickListener(v -> {
            imgHome.setColorFilter(ContextCompat.getColor(MyCartActivity.this, R.color.black));
            tvHome.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(MyCartActivity.this, MainActivity.class);
            login.putExtra("SELECTEDINNERHOME", 7);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        lnAccount.setOnClickListener(v -> {
            imgMyAccount.setColorFilter(ContextCompat.getColor(MyCartActivity.this, R.color.black));
            tvAccount.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(MyCartActivity.this, MainActivity.class);
            login.putExtra("SELECTEDINNERACCOUNT", 8);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        lnTrackOrder.setOnClickListener(v -> {
            imgTrackOrder.setColorFilter(ContextCompat.getColor(MyCartActivity.this, R.color.black));
            tvTrackOrder.setTextColor(getResources().getColor(R.color.black));
            Intent login = new Intent(MyCartActivity.this, MainActivity.class);
            login.putExtra("SELECTEDINNERTRACKORDER", 9);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        txtRetry.setOnClickListener(v -> {
            getMyCartList(true, false);
        });
        tvLocation.setOnClickListener(view -> {
            checkAddress = false;
            showLocationAdd();
        });
        btnProceed.setOnClickListener(view -> {
            btnProceed.setEnabled(false);
            btnProceed.setClickable(false);
            if (session.getLogin()) {
                checkAddress = true;
                showLocationAdd();
                hideprogress();
            } else {
                hideprogress();
                utils.openLoginDialog(MyCartActivity.this, utils.gotoMyCart);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        utilsVersion.checkVersion(this);
        getMyCartList(true, false);
        utils.checkExpireUser(mAPIInterface, this, session);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == utils.gotoMyCart) {
            if (resultCode == RESULT_OK) {
                setDataUI();
                Intent mIntent = new Intent(Utils.actionLogin);
                sendBroadcast(mIntent);
            }
        }
    }

    private void setDataUI() {
        if (session.getLogin()) { //!showLocation &&

            lnLocation.setVisibility(View.VISIBLE);
            sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, 0);
            isAddressType = sharedPreferencesAddressType.getBoolean(PrefUtils.prefAddresstype, false);

            SharedPreferences SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
            addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

            if (addressNameSave == null || addressNameSave.equals("")) {
                Log.d("Location Fill", "Location Fill " + addressNameSave);

            } else {
                Log.d("Location Empty", "Location Empty " + addressNameSave);
                if (isAddressType) {
                    tvLocation.setText("Pickup from " + addressNameSave);
                } else {
                    tvLocation.setText("Deliver to " + addressNameSave);
                }
                Log.d("Update Value", "Update Value" + isAddressType);
            }
        }
        // lnPV.setVisibility(session.getLogin() ? View.VISIBLE : View.GONE);
        // btnProceed.setText(session.getLogin() ? "Checkout" : "Sign in");
    }

    private void getMyCartList(boolean calculate, boolean compare) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            showprogress();
            Call<CartListModelEcom> wsCallingEvents;
            if (session.getLogin())
                wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, session.getIboKeyId(), cityId);
            else
                wsCallingEvents = mAPIInterface.getMyCartListEcomWoLogin(deviceId, 0);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    hideprogress();
                    cartModels.clear();
                    mLoadProgressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (compare) {
                                CartListModelEcom responseDataNew = response.body();
                                String mydataNew = new Gson().toJson(responseDataNew, new TypeToken<CartListModelEcom>() {
                                }.getType());
                                String mydataOld = new Gson().toJson(responseData, new TypeToken<CartListModelEcom>() {
                                }.getType());
                                if (mydataNew.equals(mydataOld)) {
                                    checkAddress = true;
                                    showLocationAdd();
                                    hideprogress();
                                    hideFullDialog();
                                } else {
                                    Toast.makeText(MyCartActivity.this, "Cart has been updated. Kindly place your order again.", Toast.LENGTH_SHORT).show();
                                    setDataCart(response.body(), calculate);
                                }
                                responseData = response.body();
                            } else {
                                responseData = response.body();
                                setDataCart(response.body(), calculate);
                            }
                        } else {
                            setErrorDataCart();
                            hideFullDialog();
                        }
                    } else {
                        setErrorDataCart();
                        hideFullDialog();
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    mLoadProgressDialog.dismiss();
                    hideprogress();
                    setErrorDataCart();
                    hideFullDialog();
                }
            });
        } else {
            noInternetAvailable();
            hideFullDialog();
        }
    }

    public void hideprogress() {
        if (mProgressDialog != null)
            mProgressDialog.setVisibility(View.GONE);
        btnProceed.setEnabled(true);
        btnProceed.setClickable(true);
    }

    public void showprogress() {
        if (mProgressDialog != null)
            mProgressDialog.setVisibility(View.VISIBLE);
        btnProceed.setEnabled(false);
        btnProceed.setClickable(false);
    }

    private void setErrorDataCart() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
        cardView.setVisibility(View.GONE);
        lnPV.setVisibility(View.GONE);
        cbFreeLookUp.setVisibility(View.GONE);
        constraintLayoutPlaceOrder.setVisibility(View.GONE);
        noRecordsFound();
    }

    @SuppressLint("SetTextI18n")
    private void setDataCart(CartListModelEcom body, boolean calculateFree) {
        showFullDialog();
        MyCartData data = null;
        if (body.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
            noRecordsFound();
            cbFreeLookUp.setVisibility(View.GONE);
            constraintLayoutPlaceOrder.setVisibility(View.GONE);
        } else if (body.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
            data = body.getData();
            cartModels.addAll(data.getCart());

            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCartActivity.this);
            rvCartItem.setLayoutManager(mLayoutManager);
            rvCartItem.setItemAnimator(new DefaultItemAnimator());
            MyCartProductsAdapter myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this, cartModels, MyCartActivity.this, MyCartActivity.this);
            rvCartItem.setAdapter(myCartProductsAdapter);
            lnCartDetails.setVisibility(View.VISIBLE);

            tvTotalAmount.setText("₹ " + data.getGrandTotal());
            tvMycartItemPrice.setText("" + data.getSubTotal());
            tvShipingPrice.setText("" + data.getShippingCharge());
            tvGrandTotal.setText("" + data.getGrandTotal());
            tvGrandTotalPV.setText("" + data.getTotalPV());
            tvGrandTotalNV.setText("" + data.getTotalNV());
            tvMRP.setText("" + data.getMrp());
            tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvMycartRetailPrice.setText("" + data.getRetailProfit());

            pvResult = data.getTotalPV();
            grandTotal = data.getGrandTotal();
            subtotal = data.getSubTotal();
            totalPV = data.getTotalPV();
            String pvslabnew = data.getPVSlab();
            String totalslabnew = data.getPriceSlab();
            String prodid = "0";

            if (calculateFree) {
                boolean isfreeproduct = false;
                callFree = true;
                if (firsttime) {
                    firsttime = false;
                    for (int i = 0; i < cartModels.size(); i++) {
                        boolean ispromo = cartModels.get(i).getPromo();
                        boolean isrewarded = cartModels.get(i).getRankReward();
                        //boolean isbronze = cartModels.get(i).getRankRewardBronze();
                        if (ispromo && !isrewarded) {
                            callFree = false;
                            prodid = cartModels.get(i).getProductId();
                            isfreeproduct = cartModels.get(i).getFree();
                        }
                    }
                    if (!callFree) {
                        pvslab = pvslabnew;
                        totalslab = totalslabnew;
                        if (!isfreeproduct)
                            getMyFreeProductList(Integer.parseInt(prodid));
                    } else {
                        Log.d("FIRST SLAB++", pvslab + " : " + pvslabnew + " , " + totalslab + " : " + totalslabnew);
                        if (!(pvslab.equals(pvslabnew)) || !(totalslab.equals(totalslabnew))) {
                            pvslab = pvslabnew;
                            totalslab = totalslabnew;
                            getMyFreeProductList(-1);
                        }
                    }
                } else {
                    // check slab
                    Log.d("SLAB++", pvslab + " : " + pvslabnew + " , " + totalslab + " : " + totalslabnew);
                    if (!(pvslab.equals(pvslabnew)) || !(totalslab.equals(totalslabnew))) {
                        pvslab = pvslabnew;
                        totalslab = totalslabnew;
                        getMyFreeProductList(-1);
                    }
                }
            }
            Log.d("totalCartAmountGET", "totalCartAmountGET " + grandTotal);
            Log.d("SKU Cart ", "SKU Cart: " + cartModels.toString());
            isShowRetail = body.getData().isShowRetail();

            if (!isShowRetail) {
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
            } else if (pvResult == 0) {
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
            myRunnable = () -> {
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
            };
            myHandler.postDelayed(myRunnable, 1500);

            Log.d("pvResult", "pvResult: " + pvResult);
            Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(body));

        } else if (body.getStatusCode() == REQUEST_STATUS_CODE_ERROR
                || body.getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
            setErrorDataCart();
        }
        if (cartModels.size() > 0) {
            for (int i = 0; i < cartModels.size(); i++) {
                Long variants = cartModels.get(i).getVariants();
                Log.d("variants==", " : " + variants);
                if (variants > 0 && !rewardVariantID.contains(variants)) {
                    rewardVariantID.add(variants);
                    variantModels.add(cartModels.get(i));
                }
            }
            callVariantRewards();
            llEmpty.setVisibility(View.GONE);
            rvCartItem.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            lnPV.setVisibility(View.VISIBLE);
            lnLocation.setVisibility(View.VISIBLE);
            constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
        } else {
            setErrorDataCart();
            hideFullDialog();
        }
        setSnackBar(data);
    }


    private void callVariantRewards() {
        int size = rewardVariantID.size();

        if (size > 0) {
            int length = variantIDPos + 1;
            if (length <= size) {
                mycartvariant = variantModels.get(variantIDPos);
                //hideFullDialog();
                getMyFreeRewardProductList();
            }else
                hideFullDialog();
        }else
            hideFullDialog();
    }


    private void setSnackBar(MyCartData data) {
        if (data != null && data.getShowOfferMessage()) {
            snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            View snackView = getLayoutInflater().inflate(R.layout.snack_offer, null);
            MyTextViewEcom textEcom = snackView.findViewById(R.id.snack_message2);
            MyBoldTextViewEcom textEcomMain = snackView.findViewById(R.id.snack_message);
            MyBoldTextViewEcom textAdd = snackView.findViewById(R.id.textadditems);
            ImageView imageCancel = snackView.findViewById(R.id.imgcancel);

            imageCancel.setOnClickListener(v -> snackbar.dismiss());
            textEcomMain.setText(data.getMainOfferMessage());
            textEcom.setText(data.getSecondaryOfferMessage());
            textAdd.setOnClickListener(v -> callOffer());
            textEcom.setOnClickListener(v -> callOffer());
            textEcomMain.setOnClickListener(v -> callOffer());
            layout.setPadding(0, 0, 0, 0);
            layout.addView(snackView, 0);
            snackbar.show();
        } else {
            if (snackbar != null && snackbar.isShown())
                snackbar.dismiss();
        }
    }

    private void callOffer() {
        Intent intent = new Intent(MyCartActivity.this, MyCategoryActivity.class);
        startActivity(intent);
        finish();
    }

    /*private void getMyCartListResume(boolean calculateFee) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showprogress();

            Call<CartListModelEcom> wsCallingEvents;
            if (session.getLogin())
                wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, session.getIboKeyId(), cityId);
            else
                wsCallingEvents = mAPIInterface.getMyCartListEcomWoLogin(deviceId, 0);

            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());

                    cartModels.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            Log.d("CartResponse", "CartResponse200: " + response.body());
                            setDataCart(response.body(), calculateFee);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                setErrorDataCart();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

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
                                // lnPV.setVisibility(session.getLogin() ? View.VISIBLE : View.GONE);
                                lnPV.setVisibility(View.VISIBLE);
                                //cbFreeLookUp.setVisibility( View.VISIBLE );
                                constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
                            } else {
                                noRecordsFound();
                            }
                        } else
                            setErrorDataCart();

                    } else
                        setErrorDataCart();

                    hideprogress();
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    hideprogress();
                    setErrorDataCart();
                }
            });
        } else {
            noInternetAvailable();
        }
    }*/

    @Override
    public void onMethodCallbackMain() {
        Log.d("onMethodCallbackMain", "cartModels: " + cartModels.size());
        if (cartModels.size() > 0) {
            //  cbFreeLookUp.setVisibility( View.VISIBLE );
            constraintLayoutPlaceOrder.setVisibility(View.VISIBLE);
        } else {
            cbFreeLookUp.setVisibility(View.GONE);
            constraintLayoutPlaceOrder.setVisibility(View.GONE);
            noRecordsFound();
        }
        //getMyCartListCallBack(true);
        getMyCartList(true, false);
    }

    private void displayTotalPrice(double price) {
        tvTotalAmount.setText("₹ " + price);
       // Log.d("Total Delete Price", "Total Delete Price: " + price);
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
        lnLocation.setVisibility(View.GONE);
    }

   /* private void getMyCartListCallBack(boolean calculateFee) {

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<CartListModelEcom> wsCallingEvents;
            wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, session.getIboKeyId(), cityId);

            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    cartModels.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                cbFreeLookUp.setVisibility(View.GONE);
                                constraintLayoutPlaceOrder.setVisibility(View.GONE);
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                setDataCart(response.body(), calculateFee);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                            if (cartModels.size() > 0) {
                                rvCartItem.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    setErrorDataCart();
                }
            });
        } else {
            noInternetAvailable();
        }
    }*/

    private void getMyFreeRewardProductList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            Long selId = mycartvariant.getVariants();
            Call<FreeProductListModel> wsCallingEvents = mAPIInterface.getMyFreeRewardProduct(
                    session.getIboKeyId(), selId, mycartvariant.getCartQuantity());
            wsCallingEvents.enqueue(new Callback<FreeProductListModel>() {
                @Override
                public void onResponse(Call<FreeProductListModel> call, Response<FreeProductListModel> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());
                    freeRewardProductModels.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                freeRewardProductModels.addAll(response.body().getData());
                                freeRewardProductsListAdapter = new FreeProductsListAdapter(MyCartActivity.this, freeRewardProductModels);
                                rvRewardFreeProducts.setAdapter(freeRewardProductsListAdapter);
                                // promorewardid = selId.intValue();
                                // freeRewardProductModels.get(0).setSelected(true);
                                //if (selId != -1) {
                                //  boolean selExist = false;
                              /*  for (int i = 0; i < freeRewardProductModels.size(); i++) {
                                    int prod = freeRewardProductModels.get(i).getProductId();
                                    if (prod == selId) {
                                        //   selExist = true;
                                        // promorewardid = freeRewardProductModels.get(i).getPromoId();
                                        freeRewardProductModels.get(i).setSelected(true);
                                        //  Log.d("Promo==", selId + " : " + prod + " : " + promorewardid + " : " + i);
                                    } else
                                        freeRewardProductModels.get(i).setSelected(false);
                                }*/
                                 /*   if (freeRewardProductModels.size() == 1 && selExist) {
                                    } else {*/
                                //promorewardid = -1;
                                showRewardFreeProductDialog();
                                // }
                                //} else
                                //  showRewardFreeProductDialog();
                            }
                        }
                    }
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<FreeProductListModel> call, Throwable t) {
                    hideFullDialog();
                }
            });
        } else {
            hideFullDialog();
            utils.dialogueNoInternet(this);
        }
    }

    private void showFreeProductDialog() {
        if (!dialogFreeProducts.isShowing())
            dialogFreeProducts.show();
    }

    private void showRewardFreeProductDialog() {
        if (!dialogRewardFreeProducts.isShowing()) {
            dialogRewardFreeProducts.show();
        }
    }

    private void getMyFreeProductList(int selId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<FreeProductListModel> wsCallingEvents = mAPIInterface.getMyFreeProduct(session.getIboKeyId(), subtotal, totalPV);
            wsCallingEvents.enqueue(new Callback<FreeProductListModel>() {
                @Override
                public void onResponse(Call<FreeProductListModel> call, Response<FreeProductListModel> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());
                    freeProductModels.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                freeProductModels.addAll(response.body().getData());
                                freeProductsListAdapter = new FreeProductsListAdapter(MyCartActivity.this, freeProductModels);
                                rvFreeProducts.setAdapter(freeProductsListAdapter);
                                promoid = selId;
                                freeProductModels.get(0).setSelected(true);
                                if (selId != -1) {
                                    boolean selExist = false;
                                    for (int i = 0; i < freeProductModels.size(); i++) {
                                        int prod = freeProductModels.get(i).getProductId();
                                        if (prod == selId) {
                                            selExist = true;
                                            promoid = freeProductModels.get(i).getPromoId();
                                            freeProductModels.get(i).setSelected(true);
                                            Log.d("Promo==", selId + " : " + prod + " : " + promoid + " : " + i);
                                        }
                                    }

                                    if (freeProductModels.size() == 1 && selExist) {
                                    } else {
                                        promoid = -1;
                                        showFreeProductDialog();
                                    }
                                } else
                                    showFreeProductDialog();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<FreeProductListModel> call, Throwable t) {
                }
            });
        } else {
            utils.dialogueNoInternet(this);
        }
    }

    private void submitFreeProducts() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
            mLoadProgressDialog.show();
            Call<FreeProductsModel> wsCallingEvents = mAPIInterface.submitProducts(session.getIboKeyId(), deviceId, promoid);
            wsCallingEvents.enqueue(new Callback<FreeProductsModel>() {
                @Override
                public void onResponse(Call<FreeProductsModel> call, Response<FreeProductsModel> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            FreeProductsModel mData = response.body();
                            int statuscode = mData.getStatusCode();
                            if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Toast.makeText(MyCartActivity.this, "Free product added successfully", Toast.LENGTH_SHORT).show();
                                getMyCartList(false, false);
                            } else
                                mLoadProgressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<FreeProductsModel> call, Throwable t) {
                    mLoadProgressDialog.dismiss();
                }
            });
        } else {
            //  noInternetConnectionMessage();
            utils.dialogueNoInternet(this);
        }
    }

    private void alertLessOne() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setCancelText("Cancel");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            TextView textTitle = alertDialog.findViewById(R.id.title_text);
            TextView textContent = alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = alertDialog.findViewById(R.id.cancel_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

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
            btnConfirm.setOnClickListener(v -> {
                Intent i = new Intent(MyCartActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                loading.dismiss();
            });
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
    }

    private void alertLessTwo() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("Shop More");
        loading.setCancelText("Ignore");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            TextView textTitle = alertDialog.findViewById(R.id.title_text);
            TextView textContent = alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = alertDialog.findViewById(R.id.cancel_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

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
            btnConfirm.setOnClickListener(v -> {
                Intent i = new Intent(MyCartActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                loading.dismiss();

            });
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
        try {
            loading.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
        }
    }

    private void alertLessThree() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.SUCCESS_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("Shop More");
        loading.setCancelText("Ignore");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            TextView textTitle = alertDialog.findViewById(R.id.title_text);
            TextView textContent = alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = alertDialog.findViewById(R.id.cancel_button);
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
            btnConfirm.setOnClickListener(v -> loading.dismiss());
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
        loading.show();
    }

    private void initRewardDialogFreeProducts() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_free_products, null);
        builder.setView(customLayout);

        imgRewardClose = customLayout.findViewById(R.id.img_close);
        rvRewardFreeProducts = customLayout.findViewById(R.id.rv_myFreeProductList);
        btnConfirmReward = customLayout.findViewById(R.id.btnConfirm);
        txtTitleReward = customLayout.findViewById(R.id.text_title);
        imgRewardClose.setVisibility(View.INVISIBLE);

        txtTitleReward.setText("Select your reward product");

        rvRewardFreeProducts.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvRewardFreeProducts.setLayoutManager(mLayoutManager1);
        rvRewardFreeProducts.setItemAnimator(new DefaultItemAnimator());

        dialogRewardFreeProducts = builder.create();

        dialogRewardFreeProducts.setCancelable(false);
        final int[] prorewardID = new int[1];
        btnConfirmReward.setOnClickListener(v -> {
            boolean selected = false;
            for (int i = 0; i < freeRewardProductModels.size(); i++) {
                FreeProductListModel.Datum datum = freeRewardProductModels.get(i);
                if (datum.getSelected() != null) {
                    if (datum.getSelected()) {
                        selected = true;
                        prorewardID[0] = datum.getProductId();
                    }
                }
            }
            //Log.d("promoid", "promoid" + promoid);
            if (selected) {
                dialogRewardFreeProducts.dismiss();
                submitRewardProduct(prorewardID[0]);
            } else {
                Toast.makeText(MyCartActivity.this, "Please select reward product.", Toast.LENGTH_SHORT).show();
            }
        });

        /*imgRewardClose.setOnClickListener(v -> {
            if (promoid != -1) {
                dialogRewardFreeProducts.dismiss();
            } else {
                Toast.makeText(MyCartActivity.this, "Please confirm your free product.", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void submitRewardProduct(int newvariant) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mLoadProgressDialog.show();
            Call<FreeProductsModel> wsCallingEvents = mAPIInterface.
                    updateMyFreeRewardProduct(session.getIboKeyId(), newvariant,
                            Long.parseLong(mycartvariant.getProductId()),
                            mycartvariant.getCartQuantity(), deviceId);
            wsCallingEvents.enqueue(new Callback<FreeProductsModel>() {
                @Override
                public void onResponse(Call<FreeProductsModel> call, Response<FreeProductsModel> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            FreeProductsModel mData = response.body();
                            int statuscode = mData.getStatusCode();
                            if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Toast.makeText(MyCartActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                                getMyCartList(false, false);
                                variantIDPos++;
                            } else
                                mLoadProgressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<FreeProductsModel> call, Throwable t) {
                    mLoadProgressDialog.dismiss();
                }
            });
        } else {
            //  noInternetConnectionMessage();
            utils.dialogueNoInternet(this);
        }

    }
  /*  private void initDialog() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_free_products, null);
        builder.setView(customLayout);

        imgClose = customLayout.findViewById(R.id.img_close);
        rvFreeProducts = customLayout.findViewById(R.id.rv_myFreeProductList);
        btnConfirm = customLayout.findViewById(R.id.btnConfirm);
        txtTitle = customLayout.findViewById(R.id.text_title);

        rvFreeProducts.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvFreeProducts.setLayoutManager(mLayoutManager1);
        rvFreeProducts.setItemAnimator(new DefaultItemAnimator());

        dialogFreeProducts = builder.create();
        dialogFreeProducts.setCancelable(false);

        dialogRewardFreeProducts = builder.create();
        dialogRewardFreeProducts.setCancelable(false);

    }*/


    public void initDialogFreeProducts() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_free_products, null);
        builder.setView(customLayout);

        imgClose = customLayout.findViewById(R.id.img_close);
        rvFreeProducts = customLayout.findViewById(R.id.rv_myFreeProductList);
        btnConfirm = customLayout.findViewById(R.id.btnConfirm);
        txtTitle = customLayout.findViewById(R.id.text_title);
        imgClose.setVisibility(View.INVISIBLE);
        txtTitle.setText("Free Products");

        rvFreeProducts.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvFreeProducts.setLayoutManager(mLayoutManager1);
        rvFreeProducts.setItemAnimator(new DefaultItemAnimator());

        dialogFreeProducts = builder.create();

        dialogFreeProducts.setCancelable(false);

        btnConfirm.setOnClickListener(v -> {
            for (int i = 0; i < freeProductModels.size(); i++) {
                FreeProductListModel.Datum datum = freeProductModels.get(i);
                if (datum.getSelected() != null) {
                    if (datum.getSelected())
                        promoid = datum.getPromoId();
                }
            }
            Log.d("promoid", "promoid" + promoid);
            if (promoid != -1) {
                dialogFreeProducts.dismiss();
                submitFreeProducts();
            } else {
                Toast.makeText(MyCartActivity.this, "Please select free product.", Toast.LENGTH_SHORT).show();
            }
        });

        imgClose.setOnClickListener(v -> {
            if (promoid != -1) {
                dialogFreeProducts.dismiss();
            } else {
                Toast.makeText(MyCartActivity.this, "Please confirm your free product.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initDialogCarrt() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_cart_quantity, null);
        builder.setView(customLayout);


        imgClose = customLayout.findViewById(R.id.img_close);
        spinner = customLayout.findViewById(R.id.spinner);
        editcartqnty = customLayout.findViewById(R.id.edt_cart);
        btnConfirm = customLayout.findViewById(R.id.btnConfirm);
        ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.spinner_ticket_item_ecom, cartqnty);
        dataAdapter.setDropDownViewResource(R.layout.spinner_ticket_item_ecom);
        spinner.setAdapter(dataAdapter);

        if (spinner.getSelectedItem() == null) {
            spinner.performClick();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editcartqnty.setText(spinner.getSelectedItem().toString()); //this is taking the first value of the spinner by default.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dialogCart = builder.create();

        dialogCart.setCancelable(true);
        btnConfirm.setOnClickListener(v -> {


        });

        imgClose.setOnClickListener(v -> dialogCart.dismiss());
    }

    public void setSelectedLocation(String addressType, Integer allCityID, String address, boolean isAddressType, String cityFacility, boolean isPincodeServicable) {

        if (isAddressType) {
            isServicable = true;
            tvLocation.setText("Pickup from " + address);

        } else {
            tvLocation.setText("Deliver to " + address);
            isServicable = isPincodeServicable;
        }

        addressNameSave = address;

        allCityID = allCityID;
        addressType = addressType;
        cityFacility = cityFacility;
        SharedPreferences preferences = getSharedPreferences(PrefUtils.prefLocation, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefUtils.prefLocation, addressNameSave);
        editor.apply();

        SharedPreferences preferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstype, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressType = preferencesAddressType.edit();
        editorpreferencesAddressType.putBoolean(PrefUtils.prefAddresstype, isAddressType);
        editorpreferencesAddressType.apply();

        SharedPreferences preferencesAddressCityID = getSharedPreferences(PrefUtils.prefPrefcityid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorpreferencesAddressCityID = preferencesAddressCityID.edit();
        editorpreferencesAddressCityID.putInt(PrefUtils.prefPrefcityid, allCityID);
        editorpreferencesAddressCityID.apply();

        SharedPreferences sharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstypevalue, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorAddressType = sharedPreferencesAddressType.edit();
        editorAddressType.putString(PrefUtils.prefAddresstypevalue, addressType);
        editorAddressType.apply();


        SharedPreferences sharedPreferencesFacility = getSharedPreferences(PrefUtils.prefFacility, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorFacility = sharedPreferencesFacility.edit();
        editorFacility.putString(PrefUtils.prefFacility, cityFacility);
        editorFacility.apply();
        // checkAddress = true;
        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0);

        getMyCartList(false, false);
        getMyCartListReview(String.valueOf(cityId));
    }

    @Override
    public void onBackPressed() {
        Log.d("Location Empty SAVE", "Location Empty SAVE Back" + addressNameSave);

        hideprogress();
        if (selectCategory == 10) {
            Intent intent = new Intent();
            setResult(utils.productShare, intent);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else if (backID == 1) {
            Intent cartReview = new Intent(MyCartActivity.this, MainActivity.class);
            startActivity(cartReview);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            Intent intent = new Intent();
            setResult(utils.productShare, intent);
            finish();
        }
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        SharedPreferences FirstRunPV = getSharedPreferences(PrefUtils.prefIsfirstpv, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = FirstRunPV.edit();
        edit.clear();
        edit.apply();
    }

    @Override
    public void onItemClick(String item) { }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    public class MyCartProductsAdapter extends RecyclerView.Adapter<MyCartProductsAdapter.MyViewHolder> {

        private Context context;
        ArrayList<MyCart> myCartsModel;
        private APIInterface mAPIInterface;
        String m_deviceId;
        String uniqueID;
        int addvalue;
        int productQuantity;
        private CartAdapterCallback mAdapterCallback;
        Session session;
        double pvResultAdapter;
        boolean mrpisAssociate, mrpisJoin;
        SharedPreferences spGetIsAssociate, spGetIsJoin;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_mycart_name)
            MyTextViewEcom tvMyCartName;

            @BindView(R.id.tv_mycart_item_price)
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

            @BindView(R.id.btn_add_size)
            MyButtonEcom btnAddSize;

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

            @BindView(R.id.txtreward)
            MyTextViewEcom txtReward;

            @BindView(R.id.lv_free)
            LinearLayout layfree;

            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        @SuppressLint("HardwareIds")
        public MyCartProductsAdapter(Context context, ArrayList<MyCart> myCartsModel,
                                     CartAdapterCallback callback,
                                     CartAdapterPayCallback cartAdapterPayCallback) {
            this.context = context;
            this.myCartsModel = myCartsModel;
            this.mAdapterCallback = callback;
            mAPIInterface = APIClient.getClient(context).create(APIInterface.class);

            m_deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            session = new Session(context);
            SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
            uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

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
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_cart_list_item, parent, false);

            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //getting the product of the specified position
            MyCart myCart = myCartsModel.get(position);
            //binding the data with the viewholder views

            holder.tvMyCartName.setText(myCart.getProductName());

            holder.tvProductCount.setText(String.valueOf(myCart.getCartQuantity()));
            holder.tvMyCartTablet.setText(String.valueOf(myCart.getVolwt()));
            if (!session.getLogin()) {
                holder.tvCartPV.setText("0");
                holder.tvCartBV.setText("0" + "%");
                holder.tvCartNV.setText("0");
            } else {
                holder.tvCartPV.setText("" + myCart.getCategoryPV());
                holder.tvCartBV.setText("" + myCart.getCategoryBV() + "%");
                holder.tvCartNV.setText("" + myCart.getCategoryNV());
            }
            spGetIsAssociate = context.getSharedPreferences(PrefUtils.prefMrp, 0);
            mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

            spGetIsJoin = context.getSharedPreferences(PrefUtils.prefJoindays, 0);
            mrpisJoin = spGetIsJoin.getBoolean(PrefUtils.prefJoindays, false);

            pvResultAdapter = Double.parseDouble(myCart.getCategoryPV());
            if (!session.getLogin()) {
                holder.tvMRPPrice.setVisibility(View.GONE);
                holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getCartMRPPrice()));
            } else if (pvResult >= Const.PVValue && !mrpisJoin) {
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
            final String[] items = {"S", "M", "L", "XL"};

            holder.btnAddSize.setOnClickListener(v -> new AlertDialog.Builder(context)
                    .setSingleChoiceItems(items, 1, null)
                    .setPositiveButton(R.string.submit, (dialog, whichButton) -> {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                    })
                    .show());

            Glide.with(context)
                    .load(myCart.getImageURL()).fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(getRandomDrawbleColor())
                    .into(holder.thumbnail);

            if (myCart.getPromo()) {
                //holder.imgDelete.setImageResource(myCart.getFree() ? R.drawable.ic_delete_black : R.drawable.ic_baseline_edit_24);
                holder.btnAddItem.setVisibility(View.GONE);
                holder.btnRemoveItem.setVisibility(View.GONE);
                holder.imgDelete.setVisibility(View.GONE);
                holder.layfree.setVisibility(View.VISIBLE);

                if (myCart.getRankReward()) {
                    holder.txtReward.setVisibility(View.VISIBLE);
                    holder.txtReward.setText(myCart.getRankRewardText());
                } else
                    holder.txtReward.setVisibility(View.GONE);
            } else {
                holder.btnAddItem.setVisibility(View.VISIBLE);
                holder.btnRemoveItem.setVisibility(View.VISIBLE);
                holder.imgDelete.setImageResource(R.drawable.ic_delete_black);
                holder.layfree.setVisibility(View.GONE);
                holder.txtReward.setVisibility(View.GONE);
            }

            holder.btnAddItem.setOnClickListener(v -> {
                productQuantity = Integer.parseInt(myCart.getStockQuantity());
                addvalue = Integer.parseInt(holder.tvProductCount.getText().toString());
                int maxQuantity = productQuantity;
                if (addvalue < maxQuantity) {
                    addvalue++;
                    addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 1, "plus");
                } else {
                    Toast.makeText(context, "Sorry you have reached max quantity.", Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnRemoveItem.setOnClickListener(v -> {
                int curval = Integer.parseInt(holder.tvProductCount.getText().toString());
                Log.d("curval", "curval " + curval);
                if (curval > 1) {
                    curval--;
                    addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 1, "minus");
                }
            });


            holder.imgDelete.setOnClickListener(v -> {
                if (myCart.getPromo()) {
                    if (myCart.getFree()) {
                        deleteFromCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), myCart.getID(), true);
                    } else {
                        if (freeProductModels.size() == 0)
                            getMyFreeProductList(Integer.parseInt(myCart.getProductId()));
                        else
                            showFreeProductDialog();
                    }
                } else
                    deleteFromCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 0, false);

            });
        }

        @Override
        public int getItemCount() {
            return myCartsModel.size();
        }


        private void addToCart(String deviceId, String userId, Integer productId, Integer quantity, String CartFlag) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                mLoadProgressDialog.show();
                showFullDialog();
                Call<AddToCartModel> wsCallingRegistation;
                if (session.getLogin())
                    wsCallingRegistation = mAPIInterface.getAddToCartModelCall(deviceId, userId, productId, quantity, CartFlag);
                else
                    wsCallingRegistation = mAPIInterface.getAddToCartModelCallNOLogin(deviceId, productId, quantity, CartFlag);

                Log.d("quantityOUT: ", "quantityOUT: " + quantity);
                wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                    @Override
                    public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                        if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing()) {
                            mLoadProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                mAdapterCallback.onMethodCallbackMain();
                            }
                        }
                        hideFullDialog();
                    }

                    @Override
                    public void onFailure(Call<AddToCartModel> call, Throwable t) {
                        mLoadProgressDialog.dismiss();
                        hideFullDialog();
                    }
                });
            } else {
                utils.dialogueNoInternet((Activity) context);
                hideFullDialog();
            }
        }


        private void deleteFromCart(String deviceId, String userId, Integer productId, Integer id, boolean isFreeproduct) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                showFullDialog();
                mLoadProgressDialog.show();

                Call<DeleteCartModel> wsCallingRegistation;
                if (session.getLogin()) {
                    if (isFreeproduct)
                        wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCallV2(deviceId, userId, productId, id);
                    else
                        wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCall(deviceId, userId, productId);
                } else
                    wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCallNologin(deviceId, productId);

                wsCallingRegistation.enqueue(new Callback<DeleteCartModel>() {
                    @Override
                    public void onResponse(Call<DeleteCartModel> call, Response<DeleteCartModel> response) {
                        if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing()) {
                            mLoadProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                mAdapterCallback.onMethodCallbackMain();
                            }
                        }
                        hideFullDialog();
                    }

                    @Override
                    public void onFailure(Call<DeleteCartModel> call, Throwable t) {
                        mLoadProgressDialog.dismiss();
                        hideFullDialog();
                        Log.d("Total Delete Failure", "Total Delete Failure: " + productId + "Error: " + t);
                    }
                });
            } else {
                // noInternetConnectionMessage();
                utils.dialogueNoInternet((Activity) context);
                hideFullDialog();
            }
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

    private void getMyCartListReview(String cityID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            showFullDialog();
          //  showprogress();
           // mLoadProgressDialog.show();
            Utils.showProgressDialoug(this);
            APIInterface mAPIInterfaceCartReview = APIClient.getClient(MyCartActivity.this).create(APIInterface.class);
            Call<CartListModelReviewEcom> wsCallingEvents;
            wsCallingEvents = mAPIInterfaceCartReview.getMyCartListReviewEcom(cityID);
            wsCallingEvents.enqueue(new Callback<CartListModelReviewEcom>() {
                @Override
                public void onResponse(Call<CartListModelReviewEcom> call, Response<CartListModelReviewEcom> response) {
                    cartModelsMyCartReviews.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModelsMyCartReviews.addAll(response.body().getData().getOutOfStockProducts());
                                if (response.body().getData().getCartCount() == 0) {
                                    if (checkAddress) {
                                        if (isServicable) {
                                            utils.getExpireUser(mAPIInterface, MyCartActivity.this, session);
                                        } else {
                                            pincodenotAvailable();
                                        }
                                    }
                                } else {
                                    Intent cartReview = new Intent(MyCartActivity.this, ProductAvaibilityActivity.class);
                                    startActivity(cartReview);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                }
                            }
                        }
                    } else {
                        utils.showErrorDialog(MyCartActivity.this, true, "Session Timeout", "Your Session has been expired. Please Login again.", true, session);
                    }
                    hideprogress();
                    hideFullDialog();
                    mLoadProgressDialog.dismiss();
                    Utils.hideProgressDialoug();
                }

                @Override
                public void onFailure(Call<CartListModelReviewEcom> call, Throwable t) {
                    hideprogress();
                    mLoadProgressDialog.dismiss();
                    hideFullDialog();
                    Utils.hideProgressDialoug();
                }
            });
        } else {
            hideprogress();
            utils.dialogueNoInternet(this);
            hideFullDialog();
            Utils.hideProgressDialoug();
        }
    }

    public void openOrderSummary() {
        Intent addressListIntent = new Intent(MyCartActivity.this, OrderSummaryActivity.class);
        addressListIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(addressListIntent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        hideFullDialog();
        Utils.hideProgressDialoug();
    }

    private void pincodenotAvailable() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(MyCartActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            // ImageView imgCustom = (ImageView) alertDialog.findViewById( R.id.custom_image );
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
           /* imgCustom.setVisibility( View.VISIBLE );
            imgCustom.setImageResource(R.drawable.success_pay);*/
            textTitle.setText("This pincode is not serviceable");
            textContent.setText("Please select another address.");
            btnConfirm.setText("OK");
            // textContent.setText("Pin you have entered is Invalid.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                btnProceed.performClick();
            });
        });
        loading.show();
    }

    private void noInternetAvailable() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
        cardView.setVisibility(View.GONE);
        lnPV.setVisibility(View.GONE);
        cbFreeLookUp.setVisibility(View.GONE);
        constraintLayoutPlaceOrder.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        //imgError.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        rvCartItem.setVisibility(View.GONE);
        lnLocation.setVisibility(View.VISIBLE);
    }
}
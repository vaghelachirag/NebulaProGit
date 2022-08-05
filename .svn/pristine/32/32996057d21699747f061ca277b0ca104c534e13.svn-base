package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.MyBillingAddressAdapter;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class BillingAddressActivity extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    private RecyclerView rvAddressList;
    private MyBillingAddressAdapter mybillingAddressAdapter;
    private final ArrayList<AddressModel> addressDataList = new ArrayList<>();
    Toolbar toolbar;
    ImageView imgBackArrow, imgSearch, imgError;
    MyButtonEcom btnDeliver;
    MyBoldTextViewEcom txtErrorTitle;
    LinearLayout ln_address;
    private RelativeLayout llEmpty;
    private MyTextViewEcom txtRetry;

    String addressOne, addressTwo, addressThree, addressFour, addressFive, addressMobile;
    String addressEditCallBack, addressAddCallBack, deviceId, uniqueID;

    double totalCartAmount;
    Intent addAddressData;
    Session session;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_address);
        session = new Session(this);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initIntentData();
        initError();
        initSharedPreference();
        initUI();
        initOnClick();
        getAddressList();
    }

    void initIntentData() {
        addAddressData = getIntent();
        if (addAddressData != null) {
            addressOne = addAddressData.getStringExtra("addressFullName");
            addressTwo = addAddressData.getStringExtra("addressLineOne");
            addressThree = addAddressData.getStringExtra("addressLineTwo");
            addressFour = addAddressData.getStringExtra("addressLineThree");
            addressFive = addAddressData.getStringExtra("addressLineFour");
            totalCartAmount = addAddressData.getDoubleExtra("totalCartAmount", 0);
            totalCartAmount = addAddressData.getDoubleExtra("cartTotalPrice", 0);
            Log.d("totalCartAmount", "totalCartAmount " + totalCartAmount);
        }
    }

    @SuppressLint("HardwareIds")
    void initSharedPreference() {
        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEID uniqueID 4", "MDEVICEID uniqueID 4: " + uniqueID);
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
        Log.d("MDEVICEID Address", "MDEVICEID Address: " + deviceId);
    }

    @SuppressLint("SetTextI18n")
    void initUI() {
        rvAddressList = findViewById(R.id.recycler_view);
        btnDeliver = findViewById(R.id.btn_deliver);

        rvAddressList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BillingAddressActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAddressList.setLayoutManager(mLayoutManager);
        rvAddressList.setItemAnimator(new DefaultItemAnimator());

        ln_address = findViewById(R.id.ln_address);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        //getting the toolbar
        toolbar = findViewById(R.id.toolbar_search);
        mProgressDialog = findViewById(R.id.progresbar);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        MyTextViewEcom tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Billing Address");
        imgBackArrow = findViewById(R.id.img_back);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    void initOnClick() {
        imgBackArrow.setOnClickListener(view -> onBackPressed());
        ln_address.setOnClickListener(v -> {
            Intent login = new Intent(BillingAddressActivity.this, AddAddressAcountActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });
    }

    private void getAddressList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);

            Call<AddressData> wsCallingEvents = mAPIInterface.getAddressListEcom();
            wsCallingEvents.enqueue(new Callback<AddressData>() {
                @Override
                public void onResponse(@NotNull Call<AddressData> call, @NotNull Response<AddressData> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    addressDataList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            assert response.body() != null;
                           // Log.d("AddressListGETOUT", "AddressListGETOUT: " + response.body().getMessage());
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                addressDataList.clear();
                                addressDataList.addAll(response.body().getData());
                               // Log.d("AddressListGETIN", "AddressListGETIN: " + response.body().getMessage());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BillingAddressActivity.this);
                                rvAddressList.setLayoutManager(mLayoutManager);
                                rvAddressList.setItemAnimator(new DefaultItemAnimator());
                                mybillingAddressAdapter = new MyBillingAddressAdapter(addressDataList, BillingAddressActivity.this, addressOne, addressTwo, addressThree, addressFour, addressFive, totalCartAmount);
                                //myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvAddressList.setAdapter(mybillingAddressAdapter);
                              //  Log.d("AddressListGETINData", "AddressListGETINData: " + response.body().getData());

                            } else {
                                if (response.body().getStatusCode() != REQUEST_STATUS_CODE_ERROR) {
                                    response.body().getStatusCode();
                                }// serviceUnavailable();
                            }
                            if (response.body().getData().size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                rvAddressList.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                rvAddressList.setVisibility(View.GONE);
                            }
                        }
                    } else if (response.code() == 401) {
                        Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddressData> call, @NotNull Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("AddressListGETError", "AddressListGETError: " + t);
                }
            });
        } else {
            // noInternetConnection();
            Log.d("AddressListGETErrorNO", "AddressListGETError: NO");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent editAddressCallBack = getIntent();
        if (editAddressCallBack != null && addressEditCallBack != null) {
            addressMobile = editAddressCallBack.getStringExtra("editAddressCallBack");
            getAddressList();

        } else if (editAddressCallBack != null && addressAddCallBack != null) {
            addressMobile = editAddressCallBack.getStringExtra("addAddressCallBack");
            getAddressList();
        }
    }

    void initError() {
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtRetry = findViewById(R.id.txtRetry);
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        rvAddressList.setVisibility(View.GONE);
    }
}

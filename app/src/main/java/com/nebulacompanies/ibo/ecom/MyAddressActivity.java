package com.nebulacompanies.ibo.ecom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.nebulacompanies.ibo.ecom.adapter.MyAddressAdapter;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class MyAddressActivity extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;

    private RecyclerView rvAddressList;
    private MyAddressAdapter myAddressAdapter;
    private ArrayList<AddressModel> addressDataList = new ArrayList<>();
    Toolbar toolbar;
    ImageView imgBackArrow, imgSearch,imgError;
    MyButtonEcom btnDeliver;
    LinearLayout ln_address;
    private RelativeLayout llEmpty;
    private MyTextViewEcom txtRetry,tvToolbarTitle;
    MyBoldTextViewEcom txtErrorTitle;

    Intent cartTotalPriceData,addAddressData;
    Session session;

    double totalCartAmount;
    String deviceId,uniqueID,addressMobile,addressEditCallBack,addressAddCallBack;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        session = new Session(this);

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        //mAPIInterface = APIClient.getClient(MyAddressActivity.this).create(APIInterface.class);

        cartTotalPriceData = getIntent();
        if (cartTotalPriceData != null) {
            totalCartAmount = cartTotalPriceData.getDoubleExtra("cartTotalPrice",0);
            Log.d("totalCartAmount","totalCartAmount "+ totalCartAmount);
        }
        initError();
        /*TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        initSharedPreference();

        Button add=findViewById(R.id.add);
        Button remove=findViewById(R.id.remove);
        rvAddressList = findViewById(R.id.recycler_view);
        btnDeliver = findViewById(R.id.btn_deliver);

        rvAddressList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyAddressActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAddressList.setLayoutManager(mLayoutManager);
        rvAddressList.setItemAnimator(new DefaultItemAnimator());
        ln_address= findViewById(R.id.ln_address);
        //getting the toolbar
        toolbar =  findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mProgressDialog =  findViewById(R.id.progresbar);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        tvToolbarTitle =  toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Address");
        imgBackArrow = findViewById(R.id.img_back);



        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.d("Session CHECK11111","Session CHECK1111111"+session.getAccessToken());
        getAddressList();


        ln_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MyAddressActivity.this, AddAddressAcountActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });

    }

    void initSharedPreference(){
        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        Log.d("MDEVICEID uniqueID 4", "MDEVICEID uniqueID 4: "+ uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid,randomID);
                editor.apply();
                deviceId =  randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID Address", "MDEVICEID Address: "+ deviceId);

    }

    private void getAddressList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<AddressData> wsCallingEvents = mAPIInterface.getAddressListEcom();
            wsCallingEvents.enqueue(new Callback<AddressData>() {
                @Override
                public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                    if (mProgressDialog != null ) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    addressDataList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            Log.d("AddressListGETOUT","AddressListGETOUT: "+ response.body().getMessage() );
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                 noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                addressDataList.clear();
                                addressDataList.addAll(response.body().getData());
                                Log.d("AddressListGETIN","AddressListGETIN: "+ response.body().getMessage() );
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyAddressActivity.this);
                                rvAddressList.setLayoutManager(mLayoutManager);
                                rvAddressList.setItemAnimator(new DefaultItemAnimator());
                                myAddressAdapter = new MyAddressAdapter(addressDataList,MyAddressActivity.this,totalCartAmount);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvAddressList.setAdapter(myAddressAdapter);
                                Log.d("AddressListGETINData","AddressListGETINData: "+ response.body().getData() );

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (response.body().getData().size()>0) {
                                llEmpty.setVisibility(View.GONE);
                                rvAddressList.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                rvAddressList.setVisibility(View.GONE);
                            }
                        }
                    }
                    else if (response.code() == 401){
                        Log.d("AddressListGETINData401","AddressListGETINData: "+  new Gson().toJson(response.body()) );

                      /*  Intent dash = new Intent(MyAddressActivity.this, LoginActivityEcom.class);
                        startActivity(dash);*/

                    }
                }

                @Override
                public void onFailure(Call<AddressData> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("AddressListGETError","AddressListGETError: "+ t );

                }
            });
        } else {
            // noInternetConnection();
            Log.d("AddressListGETErrorNO","AddressListGETError: NO" );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent editAddressCallBack = getIntent();
        if (editAddressCallBack != null && addressEditCallBack!=null) {
            addressMobile = editAddressCallBack.getStringExtra("editAddressCallBack");

            getAddressList();

        }else if (editAddressCallBack != null && addressAddCallBack!=null){
            addressMobile = editAddressCallBack.getStringExtra("addAddressCallBack");
            getAddressList();
        }
    }
    void initError() {
        llEmpty = (RelativeLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyBoldTextViewEcom) findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextViewEcom) findViewById(R.id.txtRetry);

    }
    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        rvAddressList.setVisibility(View.GONE);
    }
}
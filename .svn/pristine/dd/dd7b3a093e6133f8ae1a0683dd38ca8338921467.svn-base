package com.nebulacompanies.ibo.ecom;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.nebulacompanies.ibo.ecom.adapter.MyAddressAccountAdapter;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class MyAddressAccountActivity extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;

    Toolbar toolbar;
    ImageView imgBackArrow, imgSearch, imgSearchClose;
    NebulaEditTextEcom editSearch;
    RelativeLayout rlSearchView;
    MyTextViewEcom tvToolbarTitle,tvMyAccountAddresses;

    private RecyclerView recyclerView;
    private ArrayList<AddressModel> addressDataList = new ArrayList<>();
    private MyAddressAccountAdapter myAddressAdapter;

    String deviceId,addressMobile,addressEditCallBack,addressAddCallBack;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_account);
        
      /*  TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
       // mAPIInterface = APIClient.getClient(MyAddressAccountActivity.this).create(APIInterface.class);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        //getting the toolbar
        toolbar =  findViewById(R.id.toolbar_search);
        mProgressDialog =  findViewById(R.id.progresbar);
        imgBackArrow = findViewById(R.id.img_back);
        tvMyAccountAddresses = findViewById(R.id.tv_my_account_addresses);
        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose =  toolbar.findViewById(R.id.img_search_close);
        editSearch =  toolbar.findViewById(R.id.edt_search_search);
        tvToolbarTitle =  toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("My Addresses");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String value = editSearch.getText().toString();

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
            }
        });

        tvMyAccountAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressAccountActivity.this, AddAddressAcountNewActivity.class);
                startActivity(intent);
            }
        });

      //  AddressDataPrepare();
        getAddressList();

    }


    private void getAddressList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
           /* mProgressDialog = new ProgressDialog(MyAddressAccountActivity.this, R.style.MyTheme);
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
                               // noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                addressDataList.clear();
                                addressDataList.addAll(response.body().getData());
                                Log.d("AddressListGETIN","AddressListGETIN: "+ response.body().getMessage() );
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyAddressAccountActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                myAddressAdapter = new MyAddressAccountAdapter(addressDataList,MyAddressAccountActivity.this);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                recyclerView.setAdapter(myAddressAdapter);
                                Log.d("AddressListGETINData","AddressListGETINData: "+ response.body().getData() );

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (response.body().getData().size()>0) {
                               // llEmpty.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                //llEmpty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                    }else if (response.code() == 401){
                        Log.d("AddressListGETINData401","AddressListGETINData: "+  new Gson().toJson(response.body()) );

                       /* Intent addressAccountIntent = new Intent(MyAddressAccountActivity.this, LoginActivityEcom.class);
                        addressAccountIntent.putExtra("ADDRESSACCOUNTCALL", 1);
                        startActivity(addressAccountIntent);*/

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
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent editAddressCallBack = getIntent();
        if (editAddressCallBack != null && addressEditCallBack!=null) {
            addressMobile = editAddressCallBack.getStringExtra("editAddressCallBackAccount");
            getAddressList();

        }else if (editAddressCallBack != null && addressAddCallBack!=null){
            addressMobile = editAddressCallBack.getStringExtra("addAddressCallBackAccount");
            getAddressList();
        }
    }
}
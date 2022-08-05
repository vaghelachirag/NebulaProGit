package com.nebulacompanies.ibo.ecom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.SearchAdapter;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.model.SearchModelDetails;
import com.nebulacompanies.ibo.ecom.model.SearchModelEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class SearchActivity extends AppCompatActivity {

    private APIInterface mAPIInterface, mAPIInterfaceProfile;
    MaterialProgressBar mProgressDialog;
    Handler handler;
    Session session;
    NebulaEditTextEcom editSearch;
    private LinearLayout llEmpty;
    private MyTextView txtErrorTitle, txtErrorContent, txtRetry;
    MyTextViewEcom tvToolbarTitle, tvCartBadge;
    RecyclerView rvSearch;
    ImageView imgSearch, imgFav, imgCart, imgSearchClose, imgMainBack, imgError;

    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    private ArrayList<SearchModelDetails> searchModelDetailsList = new ArrayList<>();
    private ArrayList<SearchModelDetails> searchModelDetailsMainList = new ArrayList<>();

    SearchAdapter searchAdapter;

    String m_deviceId, uniqueID, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seachbar);
        handler = new Handler();
        session = new Session(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        mAPIInterface = APIClient.getClient(SearchActivity.this).create(APIInterface.class);
        // SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefUsername, 0);
        userName = session.getUserName();// SharedPreferencesUserName.getString(PrefUtils.prefUsername, null);

        /*TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/

        m_deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

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
        Log.d("MDEVICEID", "MDEVICEID: " + m_deviceId);


        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearchClose = (ImageView) toolbar.findViewById(R.id.img_search_close);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        editSearch = (NebulaEditTextEcom) toolbar.findViewById(R.id.editMobileNo);
        editSearch.setPressed(true);
        editSearch.requestFocus();

        imgCart = toolbar.findViewById(R.id.img_my_cart);

        tvCartBadge = toolbar.findViewById(R.id.cart_badge);

        mProgressDialog = (MaterialProgressBar) findViewById(R.id.progresbar);
        rvSearch = (RecyclerView) findViewById(R.id.rv_search);
        rvSearch.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        rvSearch.setLayoutManager(mLayoutManager);
        rvSearch.setItemAnimator(new DefaultItemAnimator());


        llEmpty =  findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtErrorContent = (MyTextView) findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);

        if (session.getLogin()) {
            if (userName == null || userName.equals("")) {
                getMyProfile();
                Log.d("UserName Fill", "UserName Fill " + userName);
            } else {
                Log.d("UserName Empty", "UserName Empty " + userName);
                tvToolbarTitle.setText(userName);

            }

        } else {
            tvToolbarTitle.setText("Hi, Guest");
        }


        txtRetry.setOnClickListener(v -> {
            callAPI();
        });
        imgSearchClose.setOnClickListener(view -> {
            editSearch.getText().clear();
            onBackPressed();
        });

        imgMainBack.setOnClickListener(view -> onBackPressed());

        imgCart.setOnClickListener(view -> {
            Intent login = new Intent(SearchActivity.this, MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        //setAdapter();
        callAPI();
        editSearch.addTextChangedListener(textWatcher);
    }

    private void callAPI() {
        getSearchListAPI();
        getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
        getMyCartListPV();
    }

    private void setAdapter() {

        searchAdapter = new SearchAdapter(SearchActivity.this, showMRP, searchModelDetailsList, (user, position) -> {
            Intent login = new Intent(SearchActivity.this, ProductDescription.class);
            login.putExtra("catid", user.getSearchCategoryId());
            login.putExtra("ebc_id", user.getSearchId());
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
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);

            // Toast.makeText( MainActivity.this, "user: "+user ,Toast.LENGTH_SHORT ).show();
        });

        rvSearch.setAdapter(searchAdapter);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            imgSearchClose.setVisibility(View.VISIBLE);

        }

        @Override
        public void afterTextChanged(Editable s) {
            String search = editSearch.getText().toString();
            searchModelDetailsList.clear();

            if (search.isEmpty()) {
                rvSearch.setVisibility(View.GONE);
            } else {

                //ch
                for (int i = 0; i < searchModelDetailsMainList.size(); i++) {
                    String pName=searchModelDetailsMainList.get(i).getSearchName().toLowerCase();
                    if(pName.contains(search.toLowerCase())){
                        searchModelDetailsList.add(searchModelDetailsMainList.get(i));
                    }
                }
                rvSearch.setVisibility(View.VISIBLE);
            }
            try {
                searchAdapter.notifyDataSetChanged();
            }catch(Exception e){
                e.printStackTrace();
            }

            if (!search.isEmpty()){
                if (searchModelDetailsList == null || searchModelDetailsList.isEmpty()){
                    Log.e("Record","No Record Found!");
                    noRecordsFound(true);
                }
                else {
                    noRecordsFound(false);
                }
            }

            //handler.removeCallbacks(runnable);
            // handler.postDelayed(runnable, 1000);
            //getSearchListAPI(editSearch.getText().toString());
        }
    };
   /* private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String search = editSearch.getText().toString();
            if (search.isEmpty()) {
                searchModelDetailsList.clear();
                // setAdapter();
                searchAdapter.notifyDataSetChanged();
                rvSearch.setVisibility(View.GONE);
            } else {
                searchModelDetailsList.clear();
                //ch
                for (int i = 0; i < searchModelDetailsMainList.size(); i++) {
                    String pName=searchModelDetailsMainList.get(i).getSearchName().toLowerCase();
                    if(pName.contains(search.toLowerCase())){
                        searchModelDetailsList.add(searchModelDetailsMainList.get(i));
                    }
                }
                // setAdapter();
            }
        }
    };*/

    private void getSearchListAPI() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            mAPIInterfaceProfile = APIClient.getClient(SearchActivity.this).create(APIInterface.class);
            Call<SearchModelEcom> wsCallingEvents = mAPIInterfaceProfile.getSearch("");
            wsCallingEvents.enqueue(new Callback<SearchModelEcom>() {
                @Override
                public void onResponse(Call<SearchModelEcom> call, Response<SearchModelEcom> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    searchModelDetailsList.clear();
                    searchModelDetailsMainList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            // searchModelDetailsList.clear();
                            Log.d("SearchListGETOUT", "SearchListGETOUT: " + response.body().getMessage() + "Code = " + response.body().getStatusCode());
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                searchModelDetailsMainList.addAll(response.body().getData());
                                //  Log.d("SearchListGETIN", "SearchListGETIN: " + response.body().getMessage());
                                //  searchAdapter.notifyDataSetChanged();
                                //  Log.e("SearchListGETINData", "SearchListGETINData: " + new Gson().toJson(response.body()));
                                //  Log.d("SearchListGETINData", "SearchListGETINData: " + response.body().getData());
                            }
                        }
                    }

                    if (searchModelDetailsMainList.size() > 0) {
                        // llEmpty.setVisibility(View.GONE);
                        // mProgressDialog.setVisibility(View.GONE);
                        rvSearch.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.GONE);
                        setAdapter();
                        //  searchAdapter.notifyDataSetChanged();
                        // Log.d("sizeLIST", "sizeLIST: " + response.body().getData().size());

                    } else {
                        // llEmpty.setVisibility(View.VISIBLE);
                        // rvSearch.setVisibility(View.GONE);
                        //setAdapter();
                        //   searchAdapter.notifyDataSetChanged();
                        noRecordsFound(true);
                        //  Log.d("sizeLIST1", "sizeLIST1: " + response.body().getData().size());
                    }
                    //mProgressDialog.setVisibility(View.GONE);

                    // Log.d("SearchList", "result size =" + searchModelDetailsList.size());
                }

                @Override
                public void onFailure(Call<SearchModelEcom> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                   // Log.d("SearchListGETError", "SearchListGETError: " + t);
                    noRecordsFound(true);

                }
            });
        } else {
            // noInternetConnection();
           // Log.d("SearchListGETErrorNO", "SearchListGETError: NO");
            noInternet();
        }
    }


    private void getMyProfile() {
        mAPIInterfaceProfile = APIClient.getClient(SearchActivity.this).create(APIInterface.class);
        //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceProfile.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @Override
            public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                            String lastName = response.body().getData().getLastName();
                            String email = response.body().getData().getEmail();
                            String gender = response.body().getData().getGender();
                            String mobile = response.body().getData().getMobile();
                            Log.d("firstName", "firstName: " + firstName);
                            userName = "Hi, " + firstName;
                            tvToolbarTitle.setText(userName);

                            session.setUserName(firstName);
                           /* SharedPreferences preferences = getSharedPreferences(PrefUtils.prefUsername, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(PrefUtils.prefUsername, userName);
                            editor.apply();*/

                        }/* else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }*/

                    }
                } /*else if (response.code() == 401) {
                    Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));

                    *//*Intent dash = new Intent(getActivity(), LoginActivityEcom.class);
                    startActivity(dash);*//*

                }*/
            }

            @Override
            public void onFailure(Call<ProfileModelEcom> call, Throwable t) {
                Log.d("PROFILEAPIINERROR", "PROFILEAPIINERROR: " + t);
            }
        });
    }


    private void getMyCartListTotalCount(String deviceId, String userID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

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
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this);

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
        } else {
            // noInternetConnection();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
        //getMyCartListPV();
    }


    void noRecordsFound(boolean bl_toShow) {
        if (bl_toShow){
            txtErrorTitle.setText("No Record Found");
            txtErrorContent.setVisibility(View.GONE);
            imgError.setImageResource(R.drawable.no_search);
            llEmpty.setVisibility(View.VISIBLE);
            txtRetry.setVisibility(View.GONE);
            rvSearch.setVisibility(View.GONE);
        }
        else {
            txtErrorTitle.setText("No Record Found");
            txtErrorContent.setVisibility(View.VISIBLE);
            imgError.setImageResource(R.drawable.no_search);
            llEmpty.setVisibility(View.GONE);
            txtRetry.setVisibility(View.GONE);
            rvSearch.setVisibility(View.VISIBLE);
        }
    }

    void noInternet() {
        llEmpty.setVisibility(View.VISIBLE);
        rvSearch.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtRetry.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        /*Intent i = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(i);*/
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_new, R.anim.fade_out_new);
    }

    int pvResult = 0;
    boolean mrpResult, mrpisJoined30Days;
    int cityId;

    private void getMyCartListPV() {

        SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpResult = SharedPreferencesUserName.getBoolean(PrefUtils.prefMrp, false);

        SharedPreferences spGetIsFirstPurchase = getSharedPreferences(PrefUtils.prefJoindays, 0);
        mrpisJoined30Days = spGetIsFirstPurchase.getBoolean(PrefUtils.prefJoindays, false);

        SharedPreferences citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value
        //if (!showLocation) {
        cityId = 0;
        //}
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(m_deviceId, session.getIboKeyId(), cityId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                    Log.d("getMyCartListPV", "CartResponse11: " + response.body());


                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.INVISIBLE);
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                        /*    Log.d("getMyCartListPV", "CartAPI: " + response);
                            Log.d("getMyCartListPV", "CartResponse200: " + response.body());*/

                          /*  if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();

                            } else *//*if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("getMyCartListPV", "CartAPIIn: " + response.body().getData().getCart().size());
                              */  pvResult = response.body().getData().getTotalPV();
                                showFailData();


                               /* Log.d("getMyCartListPV", "pvResult: " + pvResult);
                                Log.e("getMyCartListPV", "CartListAPI: " + new Gson().toJson(response.body()));

                            } *//*else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            } *//*else*//* if (response.body().getStatusCode() == REQUEST_STATUS_CODE_FAIL) *//*{
                                Log.d("getMyCartListPV", "CartAPIIn: " + response.body().getData().getCart().size());
                                pvResult = response.body().getData().getTotalPV();
                                showFailData();

                                Log.d("getMyCartListPV", "pvResult: " + pvResult);
                                Log.e("getMyCartListPV", "CartListAPI: " + new Gson().toJson(response.body()));
                            }*/

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    mProgressDialog.setVisibility(View.INVISIBLE);
                    pvResult = 0;
                    showFailData();
                    Log.d("getMyCartListPV", "Failure CartAPI: " + t);
                    Log.d("getMyCartListPV", "Failure CartResponseFil: " + t);
                }
            });
        }
    }

    boolean showMRP = true;

    private void showFailData() {
        //   Log.d("Data::", "showFailData " + mrpResult);
        if (!session.getLogin()) {
            showMRP = true;
        } else if (!mrpResult) {
            showMRP = false;
            Log.d("Data::", "showMRP 1 = false");
            Log.d("Data::", "1 Offer");
        } else if (pvResult >= Const.PVValue && !mrpisJoined30Days) {
            showMRP = false;
            Log.d("Data::", "showMRP 2 = false");
            Log.d("Data::", "2 Offer");
        } else  if (pvResult < Const.PVValue && mrpResult) {
            showMRP = true;
            Log.d("Data::", "4 MRP");
            Log.d("Data::", "showMRP 2 = true");
        }else {
            showMRP = true;
            Log.d("Data::", "showMRP 1 = true");
            Log.d("Data::", "3 MRP");
        }

        // setAdapter();
        // searchAdapter.notifyDataSetChanged();
    }

}

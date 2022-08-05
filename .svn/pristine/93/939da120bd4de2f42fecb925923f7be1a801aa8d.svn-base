package com.nebulacompanies.ibo.ecom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.MyCouponAdapter;
import com.nebulacompanies.ibo.ecom.model.PromoCodeModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class MyPromoCodes extends AppCompatActivity {
    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Session session;
    RecyclerView recyclerView;
    MyCouponAdapter myCouponAdapter;
    List<PromoCodeModel.Datum> promodata = new ArrayList<>();
    RelativeLayout llEmpty;
    ImageView imgError;
    MyBoldTextViewEcom txtErrorTitle;
    MyTextViewEcom txtErrorContent, txtRetry;
    private BroadcastReceiver couponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(Utils.actionCoupon)) {
                //loadFullData();
                getPromoList();
            }
        }
    };


    Toolbar toolbar;
    MyTextViewEcom tvToolbarTitle;
    ImageView imgBackArrow;
    FloatingTextButton fabCreatecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_promo_codes);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        initUI();
        initCouponList();
        getPromoList();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Utils.actionCoupon);
       registerReceiver(couponReceiver, intentFilter);
    }

    void initUI() {

        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Promotions");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        imgBackArrow = findViewById(R.id.img_back);

        imgBackArrow.setOnClickListener(view -> onBackPressed());

        mProgressDialog = findViewById(R.id.progresbar);
        recyclerView = findViewById(R.id.recycler_view);
        fabCreatecode = findViewById(R.id.action_button);
        //no records
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);

        txtRetry.setOnClickListener(v -> getPromoList());
        fabCreatecode.setOnClickListener(v -> {
            Intent intent = new Intent(this,GenerateCoupon.class);
            startActivity(intent);
        });
    }


    private void initCouponList() {
        session = new Session(this);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCouponAdapter = new MyCouponAdapter(this, promodata);
        recyclerView.setAdapter(myCouponAdapter);
        recyclerView.setHasFixedSize(true);

    }


    private void getPromoList() {
        if (Utils.isNetworkAvailable(this)) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<PromoCodeModel> wsCallingEvents = mAPIInterface.getPromoCodes(session.getIboKeyId());
            wsCallingEvents.enqueue(new Callback<PromoCodeModel>() {
                @Override
                public void onResponse(Call<PromoCodeModel> call, Response<PromoCodeModel> response) {

                    mProgressDialog.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            PromoCodeModel mData = response.body();
                            promodata.clear();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();

                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                promodata.addAll(mData.getData());
                                myCouponAdapter.notifyDataSetChanged();

                                if(promodata.size()>0){
                                    llEmpty.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                                else {
                                   noRecordsFound();
                                }
                            }

                        } else if (response.code() == 401) {
                            serviceUnavailable();
                        }

                    } else {

                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<PromoCodeModel> call, Throwable t) {

                    serviceUnavailable();
                }
            });
        } else {
            noInternetAvailable();
        }
    }


    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        txtErrorContent.setText(R.string.error_coupon);
        txtErrorContent.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

    }

    void noRecordsFound() {
        Log.d("record==","no record==");
        txtErrorTitle.setText(R.string.error_no_records);
        txtErrorContent.setText(R.string.error_promo);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        recyclerView.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        //  imgError.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(couponReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
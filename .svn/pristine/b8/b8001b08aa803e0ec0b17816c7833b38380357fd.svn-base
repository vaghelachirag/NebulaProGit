package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.OrderDetailsModel;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrderDetailsActivity extends AppCompatActivity {

    private APIInterface mAPIInterface;
    Toolbar toolbar;
    ImageView imgBackArrow,imgFav,imgSearch,imgSearchClose,imgMyCart;
    MyButtonEcom btnAddToCart;
    RecyclerView my_order_recyclerview;
    List<OrderDetailsModel> orderDetailsModels;
    RelativeLayout rlSearchView;
    NebulaEditTextEcom editSearch;
    MyTextViewEcom tvCartBadge;
    String m_deviceId,uniqueID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_my_orders);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        /*TelephonyManager TelephonyMgr = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/

        m_deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        Log.d("MDEVICEIDuniqueIDDetail", "MDEVICEIDuniqueIDDetail: "+ uniqueID);
        if (m_deviceId == null || m_deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid,randomID);
                editor.apply();
                m_deviceId =  randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEIDOrderDetails", "MDEVICEIDOrderDetails: "+ m_deviceId);

        //getting the toolbar
        mAPIInterface = APIClient.getClient(MyOrderDetailsActivity.this).create(APIInterface.class);
        toolbar =  findViewById(R.id.toolbar_dashboard);
        imgBackArrow =  findViewById(R.id.img_back);
        imgFav =  findViewById(R.id.img_my_fav);
        btnAddToCart =  findViewById(R.id.btn_add_to_cart);
        setSupportActionBar(toolbar);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        my_order_recyclerview = findViewById(R.id.recycler_view);
        my_order_recyclerview.setHasFixedSize(true);
        tvCartBadge =  findViewById(R.id.cart_badge);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false);
        my_order_recyclerview.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        my_order_recyclerview.setItemAnimator(new DefaultItemAnimator());
        orderDetailsModels = new ArrayList<>();
        imgMyCart =  findViewById(R.id.img_my_cart);
        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose =  toolbar.findViewById(R.id.img_search_close);
        editSearch = toolbar. findViewById(R.id.editMobileNo);
        String value = editSearch.getText().toString();
        imgSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value!=null){
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

     //   getMyCartList(m_deviceId);
        //adding some items to our list
        orderDetailsModels = new ArrayList<>();


        //adding some items to our list
        orderDetailsModels.add(
                new OrderDetailsModel(
                        "Arriving Soon",
                        "Order Dispatched", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQpPD6FKAHQakqfzczGwMS3rNq80hmEixZezhRvhHOjmYQgpJyF&usqp=CAU"));

        orderDetailsModels.add(
                new OrderDetailsModel("Arriving Soon",
                        "On the way", "https://img.mensxp.com/media/shop/catalog/products/F/708972/f9-pre-workout-in-fruit-punch-flavour-50-servings-400-gm-107553-default.jpg"));

        orderDetailsModels.add(
                new OrderDetailsModel("Order Delivered",
                        "08th Jan,2020", "https://www.enomark.com/admin/images/composition_images/LTSYP-105_0_1545215812.jpg"));

        orderDetailsModels.add(
                new OrderDetailsModel("Order Delivered",
                        "08th Oct,2019", "https://nutrifusion.com/wp-content/uploads/2018/02/NF-216-ProductPhoto-min.png"));


        //creating recyclerview adapter
       // OrderListAdapter adapter = new OrderListAdapter(MyOrderDetailsActivity.this, orderDetailsModels);

        //setting adapter to recyclerview
        //my_order_recyclerview.setAdapter(adapter);

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MyOrderDetailsActivity.this, MyWishListActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });
        imgMyCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MyOrderDetailsActivity.this, MyCartActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });


    }
    /*private void getMyCartList(String deviceId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId);
            wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
                @Override
                public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI","CartAPI: my order"+ response );
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                Log.d("CartAPIIn","CartAPIIn: "+ response.body().getData().size() );
                                String count = String.valueOf(response.body().getData().size());
                                tvCartBadge.setText(count);
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyOrderDetailsActivity.this);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {

                    Log.d("CartAPI","CartAPI: "+ t );

                }
            });
        } else {
            // noInternetConnection();
        }
    }*/

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent placeOderIntent = new Intent(MyOrderDetailsActivity.this, MainActivity.class);
        placeOderIntent.putExtra("MYORDERDETAILS", 2);
        placeOderIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(placeOderIntent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
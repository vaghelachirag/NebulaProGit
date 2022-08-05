package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.TimelineAdapter;
import com.nebulacompanies.ibo.ecom.adapter.TrackListItem;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class TrackOrderActivity extends AppCompatActivity {

    private APIInterface mAPIInterface;
    Session session;

    Toolbar toolbar;
    ImageView imgBackArrow, imgMyCart, imgFav, imgSearch,imgSearchClose,imgPincodeBack;
    MyTextViewEcom toolbarTitle,cartBadge;
    public RelativeLayout rlSearchView;
    NebulaEditTextEcom editSearch;

    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    TimelineAdapter adapter;

    String m_deviceId,uniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        Utils.darkenStatusBar(this, R.color.colorNotification);
        mAPIInterface = APIClient.getClient(TrackOrderActivity.this).create(APIInterface.class);
        /*TelephonyManager TelephonyMgr = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/

        m_deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        session = new Session(this);
        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        Log.d("MDEVICEIDuniqueIDOrder", "MDEVICEIDuniqueIDOrder: "+ uniqueID);
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
        Log.d("MDEVICEID Track Order", "MDEVICEID  Track Order: "+ m_deviceId);

        //getting the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        imgBackArrow = (ImageView) findViewById(R.id.img_back);
        imgMyCart = (ImageView) findViewById(R.id.img_my_cart);
        imgFav = (ImageView) findViewById(R.id.img_my_fav);
        imgSearchClose = (ImageView) findViewById(R.id.img_search_close);
        cartBadge = (MyTextViewEcom) findViewById(R.id.cart_badge);
        editSearch = (NebulaEditTextEcom) findViewById(R.id.edt_search_search);
        String value = editSearch.getText().toString();
        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        toolbarTitle = (MyTextViewEcom) findViewById(R.id.toolbar_title1);
        toolbarTitle.setText("Track Order");

        setSupportActionBar(toolbar);


       // getMyCartList(m_deviceId,session.getIboKeyIdNew());
        getMyCartListTotalCount(m_deviceId,session.getIboKeyId());
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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


        imgMyCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent login = new Intent(TrackOrderActivity.this, MyCartActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(TrackOrderActivity.this, MyWishListActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });

        int orientation =getIntent().getIntExtra(ORIENTATION, LinearLayoutManager.VERTICAL);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this, orientation, false));

        adapter = new TimelineAdapter(orientation, setDataListItems());
        recycler.setAdapter(adapter);
    }

    private List<TrackListItem> setDataListItems(){
        List<TrackListItem> items = new ArrayList<>();
        Random random = new Random();
        items.add(new TrackListItem("","Item successfully delivered"));
        items.add(new TrackListItem("2017-02-12, 08:00","Courier is out to delivery your order" ));
        items.add(new TrackListItem( "2017-02-11, 06:32","Item has reached courier facility at Ahmedabad"));
        items.add(new TrackListItem("2017-02-11 09:30","Item has been given to the courier"));
        items.add(new TrackListItem("2017-02-11, 18:00","Item is packed and will dispatch soon"));
        items.add(new TrackListItem("2017-02-10 14:30","Order confirmed by seller" ));
        items.add(new TrackListItem("2017-02-10 14:00","Order placed successfully"));
        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  getMyCartList(m_deviceId,session.getIboKeyIdNew());
        getMyCartListTotalCount(m_deviceId,session.getIboKeyId());
    }



    private void getMyCartListTotalCount(String deviceId,String userID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            Call<CartListTotalCountModelEcom> wsCallingEvents = mAPIInterface.getMyCartTotalCountListEcom(deviceId,userID);
            wsCallingEvents.enqueue(new Callback<CartListTotalCountModelEcom>() {
                @Override
                public void onResponse(Call<CartListTotalCountModelEcom> call, Response<CartListTotalCountModelEcom> response) {
                    myTotalCountCartData.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: track" + response);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                cartBadge.setText("0");
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData());
                                String count = String.valueOf(response.body().getData().getCartTotalCount());
                                cartBadge.setText(count);
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TrackOrderActivity.this);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {

                    Log.d("CartAPI", "CartAPI: track" + t);

                }
            });
        } else {
            // noInternetConnection();
        }
    }


}
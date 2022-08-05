package com.nebulacompanies.ibo.ecom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.viewtooltip.BuildConfig;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.MyViewOrderAdapter;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.model.TrackListModelEcom;
import com.nebulacompanies.ibo.ecom.model.TrackOrderModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.OrderListCallback;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.WHITE;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MyViewOrderActivity extends AppCompatActivity implements OrderListCallback, OnItemClickTrack {

    Toolbar toolbar;
    ImageView imgBackArrow, imgFav, imgSearch, imgSearchClose, imgMyCart,imageView;
    MyButtonEcom btnAddToCart, btnOrderTrack;
    RecyclerView rvMyViewOrder;
    private ArrayList<OrderListModel.OrderDetailsListModel> orderDetailsListModels = new ArrayList<>();
    private MyViewOrderAdapter myViewOrderAdapter;
    private ArrayList<TrackListModelEcom> trackListModelEcoms = new ArrayList<>();

    RelativeLayout rlSearchView,mRootLayout;
    LinearLayout lyshippingdetails,laywallet;
    NebulaEditTextEcom editSearch;


    APIInterface mAPIInterface, mAPIInterfaceTrackOrder;
    Intent viewOrderData;
    Session session;
    String m_deviceId,uniqueID,orderNumber, orderShippingAddress, orderBillingAddress,   orderBillingAddressUser, orderShippingAddressUser, status, statusDate,
            orderShippingTranId, mobileNo;
    Integer isPickupPoint;
    int orderTotalPrice, orderShippingCharge, orderSubTotal;
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    private ArrayList<TrackListModelEcom> trackListModels = new ArrayList<>();

    MyTextViewEcom tvOrderId, tvwalletvalue,  tvOrderShippingAddress, tvShippingMobileNo, tvOrderBillingAddress, tvShippingAddressOne, tvbillingAddressOne,
            tvbillingShipping, tvBillingSubTotal, tvBillingGrandTotal, tvBillingTranID,
            tvShippingProviderName, tvShippingNumber,cartBadge,toolbarTitle,tvCreateTicket;

    MyBoldTextViewEcom tvshippingtitle, tvshippingProvider, tvShippingNo;
    ToolTipsManager mToolTipsManager;
    String pdf = "";
    MaterialProgressBar mProgressDialog1;
    String getFacility, invoicecode, valueCallBack;
    Utils utils = new Utils();
    int ewallet = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_orders);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);
        viewOrderData = getIntent();
        if (viewOrderData != null) {
            orderDetailsListModels = (ArrayList<OrderListModel.OrderDetailsListModel>) viewOrderData.getSerializableExtra("view_order_data");
            orderNumber = viewOrderData.getStringExtra("orderNumber");
            //orderTotalPrice= viewOrderData.getIntExtra("orderTotalPrice",0);
            orderShippingCharge = viewOrderData.getIntExtra("orderShippingPrice", 0);
            orderSubTotal = viewOrderData.getIntExtra("orderSubtotalPrice", 0);
            ewallet = viewOrderData.getIntExtra("ewallet", 0);
            orderTotalPrice = viewOrderData.getIntExtra("orderTotalPrice", 0);
            orderShippingAddress = viewOrderData.getStringExtra("orderShippingAddresses");
            orderBillingAddress = viewOrderData.getStringExtra("orderBillingAddresses");
            mobileNo = viewOrderData.getStringExtra("mobileNo");
            orderBillingAddressUser = viewOrderData.getStringExtra("orderBillingAddressUser");
            orderShippingAddressUser = viewOrderData.getStringExtra("orderShippingAddressUser");
            orderShippingTranId = viewOrderData.getStringExtra("orderTransactionID");
            isPickupPoint = viewOrderData.getIntExtra("isPickupPoint", 0);
            status = viewOrderData.getStringExtra("status");
            statusDate = viewOrderData.getStringExtra("statusDate");
        }

        Log.d("orderNumber ", "orderNumber: " + orderNumber);
        String orderId = orderNumber;
        mToolTipsManager = new ToolTipsManager();


        //getting the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        mAPIInterface = APIClient.getClient(MyViewOrderActivity.this).create(APIInterface.class);
        imgBackArrow = (ImageView) findViewById(R.id.img_back);
        imgFav = (ImageView) findViewById(R.id.img_my_fav);
        btnAddToCart = (MyButtonEcom) findViewById(R.id.btn_add_to_cart);

        setSupportActionBar(toolbar);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        rvMyViewOrder = findViewById(R.id.recyclerview_order_summary);
        rvMyViewOrder.setHasFixedSize(true);

        //orderDetailsListModels = new ArrayList<>();
        imgMyCart = (ImageView) findViewById(R.id.img_my_cart);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.INVISIBLE);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose = (ImageView) toolbar.findViewById(R.id.img_search_close);
        editSearch = (NebulaEditTextEcom) toolbar.findViewById(R.id.edt_search_search);
        cartBadge = (MyTextViewEcom) findViewById(R.id.cart_badge);
        toolbarTitle = (MyTextViewEcom) findViewById(R.id.toolbar_title1);
        toolbarTitle.setText("Order Summary");

        tvOrderId = (MyTextViewEcom) findViewById(R.id.tv_order_id);
        tvshippingtitle = (MyBoldTextViewEcom) findViewById(R.id.tv_payment_shipping_title);
        tvCreateTicket = findViewById(R.id.tv_ticket);
        lyshippingdetails = (LinearLayout) findViewById(R.id.ly_shipping_details);
        tvShippingProviderName = (MyTextViewEcom) findViewById(R.id.tv_shipping_provider_name);
        tvshippingProvider = (MyBoldTextViewEcom) findViewById(R.id.tv_shipping_provider);
        tvShippingNo = (MyBoldTextViewEcom) findViewById(R.id.tv_shipping_no);
        tvShippingNumber = (MyTextViewEcom) findViewById(R.id.tv_shipping_number);
        tvwalletvalue = findViewById(R.id.tv_wallet_value);
        laywallet = findViewById(R.id.laywallet);
        tvOrderBillingAddress = (MyTextViewEcom) findViewById(R.id.tv_shipping_address_two);
        tvOrderShippingAddress = (MyTextViewEcom) findViewById(R.id.tv_billing_address_two);
        tvShippingMobileNo = (MyTextViewEcom) findViewById(R.id.tv_shipping_address_mobile);
        tvShippingAddressOne = (MyTextViewEcom) findViewById(R.id.tv_shipping_address_one);
        tvbillingAddressOne = (MyTextViewEcom) findViewById(R.id.tv_billing_address_one);
        tvbillingShipping = (MyTextViewEcom) findViewById(R.id.tv_order_subtotal_value);
        tvBillingGrandTotal = (MyTextViewEcom) findViewById(R.id.tv_order_shiping_price);
        tvBillingSubTotal = (MyTextViewEcom) findViewById(R.id.tv_order_grand_total_value);
        tvBillingTranID = (MyTextViewEcom) findViewById(R.id.tv_tran_value);
        mProgressDialog1 = (MaterialProgressBar) findViewById(R.id.progresbar);
        imageView = (ImageView) findViewById(R.id.img_info);
        mRootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        btnOrderTrack = findViewById(R.id.btn_track);
        getTrackOrder(orderId);

        imageView.setOnClickListener(view -> ViewTooltip
                .on(imageView)
                // .customView(customView)
                .position(ViewTooltip.Position.TOP)
                .arrowSourceMargin(0)
                .arrowTargetMargin(0)
                .text("Once we have the stock available at this pickup location, we shall call you to collect your order.")
                .textColor(Color.BLACK)
                .clickToHide(true)
                .padding(10, 0, 10, 0)
                .autoHide(true, 2800)
                .clickToHide(true)
                .color(createPaint())
                .border(Color.BLACK, 4)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .onDisplay(new ViewTooltip.ListenerDisplay() {
                    @Override
                    public void onDisplay(View view) {
                        Log.d("ViewTooltip", "onDisplay");
                    }
                })
                .onHide(new ViewTooltip.ListenerHide() {
                    @Override
                    public void onHide(View view) {

                        Log.d("ViewTooltip", "onHide");
                    }
                })
                .show());


      /*  BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.layout_sample_popup, null);
        PopupWindow popupWindow = BubblePopupHelper.create(this, bubbleLayout);
        final Random random = new Random();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);
                if (random.nextBoolean()) {
                    bubbleLayout.setArrowDirection( ArrowDirection.TOP);
                } else {
                    bubbleLayout.setArrowDirection(ArrowDirection.BOTTOM);
                }
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], v.getHeight() + location[1]);
            }
        });*/


        tvOrderId.setText(orderNumber);

        getInvoice(orderNumber);

        Log.d("PickupPoint:", "PickupPoint" + isPickupPoint);
        if (isPickupPoint == 0) {

            tvshippingtitle.setText("Shipping Detail");
            tvOrderBillingAddress.setText(orderBillingAddress);
            tvShippingMobileNo.setVisibility(View.VISIBLE);
            tvShippingMobileNo.setText("Mobile: +91" + mobileNo);
            lyshippingdetails.setVisibility(View.VISIBLE);
            mRootLayout.setVisibility(View.GONE);

        } else {
            tvshippingtitle.setText("Pickup Location");
            lyshippingdetails.setVisibility(View.GONE);
            mRootLayout.setVisibility(View.VISIBLE);
            //tvOrderBillingAddress.setText( orderBillingAddress );
            try {
                String currentString = orderBillingAddress;
                String[] separated = currentString.split("\\(Ph.:");
                String address = separated[0];
                String mobile = separated[1];
                tvOrderBillingAddress.setText(address);
                tvShippingMobileNo.setVisibility(View.VISIBLE);
                mobile = mobile.replaceAll("\\)", "");
                tvShippingMobileNo.setText("Mobile: " + mobile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        tvOrderShippingAddress.setText(orderShippingAddress);
        tvShippingAddressOne.setText(orderShippingAddressUser);
        tvbillingAddressOne.setText(orderBillingAddressUser);
        tvbillingShipping.setText("" + orderShippingCharge);
        tvBillingSubTotal.setText("" + orderSubTotal);
        tvBillingGrandTotal.setText("" + orderTotalPrice);
        tvBillingTranID.setText(orderShippingTranId);

        /*TelephonyManager TelephonyMgr = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/

        m_deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        ToolTip.Builder builder = new ToolTip.Builder(this, imageView, mRootLayout, "Tip message", ToolTip.POSITION_RIGHT_TO);

        Log.d("MDEVICEIDuniqueIDOrder", "MDEVICEIDuniqueIDOrder: " + uniqueID);
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
        Log.d("MDEVICEID Track Order", "MDEVICEID  Track Order: " + m_deviceId);

        String value = editSearch.getText().toString();
        imgSearchClose.setOnClickListener(view -> {
            if (value != null) {
                editSearch.getText().clear();
            }
        });

        imgSearch.setOnClickListener(view -> rlSearchView.setVisibility(View.VISIBLE));

        imgBackArrow.setOnClickListener(view -> onBackPressed());

        imgFav.setOnClickListener(view -> {
            Intent login = new Intent(MyViewOrderActivity.this, MyWishListActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });


        imgMyCart.setOnClickListener(view -> {
            Intent login = new Intent(MyViewOrderActivity.this, MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        tvOrderId.setOnClickListener(view -> {
            if (valueCallBack.equalsIgnoreCase("Dispatched") ||
                    valueCallBack.equalsIgnoreCase("Delivered")) {
                getInvoicePDF();
            } else {
                Toast.makeText(getApplicationContext(), "Your order is not yet dispatched.", Toast.LENGTH_SHORT).show();
            }

        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMyViewOrder.setLayoutManager(mLayoutManager);
        rvMyViewOrder.setItemAnimator(new DefaultItemAnimator());
        myViewOrderAdapter = new MyViewOrderAdapter(MyViewOrderActivity.this, orderDetailsListModels, MyViewOrderActivity.this, orderNumber, status, statusDate, MyViewOrderActivity.this,
                isPickupPoint != 0);
        //productsAdapter = new ProductsAdapter(getActivity(),productArrayList);
        rvMyViewOrder.setAdapter(myViewOrderAdapter);
        btnOrderTrack.setVisibility(isPickupPoint != 0 ? View.GONE : View.VISIBLE);
        tvCreateTicket.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Intent intent = new Intent(MyViewOrderActivity.this, CustomerSupport.class);
                intent.putExtra("showorder", 1);
                intent.putExtra("ticketid", orderNumber);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(this);
            }
        });
        btnOrderTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(MyViewOrderActivity.this)) {
                    mProgressDialog1.setVisibility(View.VISIBLE);
                    Call<TrackListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getTrackEcom(orderNumber);
                    wsCallingEvents.enqueue(new Callback<TrackListModelEcom>() {
                        @Override
                        public void onResponse(Call<TrackListModelEcom> call, Response<TrackListModelEcom> response) {
                            if (mProgressDialog1 != null) {
                                mProgressDialog1.setVisibility(View.VISIBLE);
                            }
                            if (response.isSuccessful()) {
                                trackListModels.clear();
                                // if (response.body().getData().size() > 0) {
                                if (response.code() == 200) {
                                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                        trackUrl = response.body().getData().getTrackingData().getTrackUrl();

                                        if (mProgressDialog1 != null) {
                                            mProgressDialog1.setVisibility(View.GONE);
                                        }
                                        if (!TextUtils.isEmpty(trackUrl)) {

                                            Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                                            myWebLink.setData(Uri.parse(trackUrl));
                                            startActivity(myWebLink);
                                        } else {
                                            if (mProgressDialog1 != null) {
                                                mProgressDialog1.setVisibility(View.GONE);
                                            }
                                            Toast.makeText(MyViewOrderActivity.this, "Tracking not available", Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                            || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    }
                                } else {
                                    if (mProgressDialog1 != null) {
                                        mProgressDialog1.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                if (mProgressDialog1 != null) {
                                    mProgressDialog1.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TrackListModelEcom> call, Throwable t) {
                            Log.d("TrackOrderERROR", "TrackOrderERROR: " + t);

                            if (mProgressDialog1 != null) {
                                mProgressDialog1.setVisibility(View.GONE);
                            }
                        }
                    });

                } else {
                    utils.dialogueNoInternet(MyViewOrderActivity.this);
                }


            }
        });
        if (Utils.showWallet && ewallet > 0) {
            tvwalletvalue.setText("" + ewallet);
            laywallet.setVisibility(View.VISIBLE);
        } else {
            laywallet.setVisibility(View.GONE);
        }
    }

    String trackUrl;

    //api shipping details
    private void getTrackOrder(String orderId) {
        mAPIInterfaceTrackOrder = APIClient.getClient(this).create(APIInterface.class);
        Call<TrackListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getTrackEcom(orderId);
        wsCallingEvents.enqueue(new Callback<TrackListModelEcom>() {
            @Override
            public void onResponse(Call<TrackListModelEcom> call, Response<TrackListModelEcom> response) {

                if (response.isSuccessful()) {
                    trackListModels.clear();
                    if (response.code() == 200) {
                        Log.d("TrackOrder", "TrackOrder: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("TrackOrder", "TrackOrder: " + new Gson().toJson(response.body()));

                            String shippingProviderName = response.body().getData().getTrackingData().getShippingProvider();
                            if (!shippingProviderName.isEmpty()) {
                                tvShippingProviderName.setText(" " + shippingProviderName);
                                tvShippingNumber.setText(" " + response.body().getData().getTrackingData().getAwbNo());

                            } else {
                                lyshippingdetails.setVisibility(View.GONE);
                            }

                            //joiningdate.setText(idDetails.get(0).getJoiningDate());
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TrackListModelEcom> call, Throwable t) {
                Log.d("TrackOrderERROR", "TrackOrderERROR: " + t);
            }
        });
    }

    private Paint createPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new LinearGradient(0, 0, 0, 600, WHITE, WHITE, Shader.TileMode.REPEAT));
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMyCartListTotalCount(m_deviceId, session.getIboKeyId());
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
                            Log.d("CartAPI", "CartAPI: view order" + response);
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                cartBadge.setText("0");
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData());
                                Log.e("CartAPIIn", "CartAPIIn" + new Gson().toJson(response.body()));
                                String count = String.valueOf(response.body().getData().getCartTotalCount());
                                cartBadge.setText(count);
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyViewOrderActivity.this);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {
                    Log.d("CartAPI", "CartAPI: view order" + t);
                }
            });
        } else {
            // noInternetConnection();
        }
    }


    @Override
    public void onMethodCallbackMain() {
        onBackPressed();
    }


    public void pdf() {
        File dir = Environment.getExternalStorageDirectory();
        File assist = new File(pdf);
        try {
            InputStream fis = new FileInputStream(assist);

            long length = assist.length();
            if (length > Integer.MAX_VALUE) {
                Log.e("MainActivity", "cannnottt   readddd");
            }
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            File data = new File(dir, "mydemo.pdf");
            OutputStream op = new FileOutputStream(data);
            op.write(bytes);
        } catch (Exception ex) {
            Log.e("MainActivity", "" + ex.getMessage());
        }
    }


    private void getInvoice(String orderNumber) {

        // mProgressDialog1.setVisibility(View.VISIBLE);
        Call<TrackOrderModel> wsCallingBanersList = mAPIInterface.getInvoice(orderNumber);
        Log.d("OnresponseStart", "OnresponseStart: ");
        wsCallingBanersList.enqueue(new Callback<TrackOrderModel>() {
            @Override
            public void onResponse(Call<TrackOrderModel> call, Response<TrackOrderModel> response) {
                Log.d("OnresponseIF", "OnresponseIF: " + response);
               /* if (mProgressDialog1!=null){
                    mProgressDialog1.setVisibility(View.VISIBLE);
                }*/
                if (response.isSuccessful()) {
                    trackListModelEcoms.clear();
                    if (response.code() == 200) {
                        Log.d("Onresponse", "Onresponse: " + response);
                        if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                        } else if (response.body().getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            getFacility = response.body().getData().getFacility();
                            invoicecode = response.body().getData().getInvoicecode();

                            SharedPreferences.Editor editor = getSharedPreferences(PrefUtils.prefFacility, MODE_PRIVATE).edit();
                            editor.putString("getFacility", getFacility);
                            editor.apply();

                            Log.e("getFacility: ", "getFacility: " + getFacility + " " + invoicecode);
                        } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {

                        }
                    }
                } else {
                    Log.d("onResponse", "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<TrackOrderModel> call, Throwable t) {
                Log.d("OnresponseFail", "OnresponseFail: " + t);
                if (mProgressDialog1 != null) {
                    mProgressDialog1.setVisibility(View.GONE);
                }
            }
        });
    }


    private void getInvoicePDF() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mAPIInterface = APIClient.getClientUniCommmerce(MyViewOrderActivity.this).create(APIInterface.class);
            mProgressDialog1.setVisibility(View.VISIBLE);
            //Call<ResponseBody> wsCallingRegistation = mAPIInterface.getPDF( "AMDHYD2021INS0017" );
            Call<ResponseBody> wsCallingRegistation = mAPIInterface.getPDF(invoicecode);
            //if (!invoicecode.equals( "" )) {
            wsCallingRegistation.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (mProgressDialog1 != null) {
                        mProgressDialog1.setVisibility(View.INVISIBLE);
                    }

                    try {
                        InputStream mypdfbytes = response.body().byteStream();
                        Log.d("pdf:", "success: " + mypdfbytes);
                        // Save PDF file in Folder Name: "Nebula"
                        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        File folder = new File(extStorageDirectory, "Nebula");
                        folder.mkdir();
                        InputStream is = response.body().byteStream();
                        FileOutputStream fos = new FileOutputStream(
                                new File(folder, "Invoice.pdf"));

                        int read = 0;
                        byte[] buffer = new byte[32768];
                        while ((read = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, read);
                        }
                        fos.close();
                        is.close();
                        Log.d("pdf:", "SuccessPDF: " + fos);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(Environment.getExternalStorageDirectory() + "/Nebula/" + "Invoice.pdf");
                        Uri data = FileProvider.getUriForFile(MyViewOrderActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                        intent.setDataAndType(data, "application/pdf");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(MyViewOrderActivity.this, "Invoice is not generated yet. ", Toast.LENGTH_LONG).show();
                        Log.d("pdf:", "error: " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mProgressDialog1.setVisibility(View.INVISIBLE);
                    Log.d("pdf:", "error: " + t);
                }
            });
        } else {
            utils.dialogueNoInternet(this);
            // Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(String value) {
        valueCallBack = value;
        Log.e("Order Track Status", "Order Track Status: " + valueCallBack);
    }
}
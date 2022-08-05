package com.nebulacompanies.ibo.ecom.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.MyWishListActivity;
import com.nebulacompanies.ibo.ecom.adapter.OrderListAdapter;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyProfileDataModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModelEcom;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Const;
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

public class MyTrackorderFragment extends Fragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;
    APIInterface  mAPIInterfaceTrackOrder;
    Session session;
    MyTextViewEcom tvToolbarTitle;
    NebulaEditTextEcom editSearch;
    MyTextViewEcom tvCartBadge;
    String userName;
    String deviceId;
    String uniqueID;
    public RelativeLayout lnCart, rlSearchView;
    ImageView imgSearch, imgFav, imgCart, imgSearchClose, imgMainBack;
    MaterialProgressBar mProgressDialog;
    RecyclerView recyclerView;
    ArrayList<OrderListModel> orderListModel = new ArrayList<>();
    private OrderListAdapter myOrderDetailsActivity;
    //Error View
    private RelativeLayout llEmpty;
    private ImageView imgError;
    private MyTextViewEcom txtErrorContent, txtRetry;
    MyBoldTextViewEcom txtErrorTitle;
   // private CallBackListener callBackListener;
    ArrayList<MyCart> cartModels = new ArrayList<>();
    Integer pvResult;
    LinearLayout lnTrackOrder;
    SharedPreferences citySave;
    int cityId;

    public MyTrackorderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyTrackorderFragment newInstance(String param1, String param2) {
        MyTrackorderFragment fragment = new MyTrackorderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getActivity());
       // if (getArguments() != null) {
          /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        //}

        /*TelephonyManager TelephonyMgr = (TelephonyManager) Objects.requireNonNull(getActivity()).getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        deviceId = android.provider.Settings.Secure.getString(getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = getActivity().getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        citySave = getActivity().getSharedPreferences( PrefUtils.prefCityid, 0 );
        cityId = citySave.getInt( PrefUtils.prefCityid, 0 ); //0 is the default value
       // if (!showLocation) {
            cityId = 0;
        //}
        Log.d("MDEVICEID Home uniqueID", "MDEVICEID Home uniqueID: "+ uniqueID);
        if (deviceId == null || deviceId.equals("")) {
            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid,randomID);
                editor.apply();
                deviceId =  randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID Home", "MDEVICEID Home: "+ deviceId);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_dashboard);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
       // lnCart = toolbar.findViewById(R.id.toolbar_settings);

        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility( View.GONE );
        imgFav = toolbar.findViewById(R.id.img_my_fav);
        imgCart = toolbar.findViewById(R.id.img_my_cart);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        tvCartBadge = toolbar.findViewById(R.id.cart_badge);
        // tvToolbarTitle.setText("Home");

        imgSearchClose = (ImageView) toolbar.findViewById(R.id.img_search_close);
        editSearch = (NebulaEditTextEcom) toolbar.findViewById(R.id.editMobileNo);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);

        rlSearchView = toolbar.findViewById(R.id.rl_search_view_main);
        rlSearchView.setVisibility( View.GONE );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mProgressDialog = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        lnTrackOrder = (LinearLayout) view.findViewById(R.id.ln_track_order);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String value = editSearch.getText().toString();
        imgSearchClose.setOnClickListener(view13 -> {
            if (value != null) {
                editSearch.getText().clear();
            }
        });

       /* imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlSearchView.setVisibility(View.VISIBLE);
            }nebul22
        });*/

        imgCart.setOnClickListener(view14 -> {
            Intent login = new Intent(getActivity(), MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        imgFav.setOnClickListener(view1 -> {
            Intent login = new Intent(getActivity(), MyWishListActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
        });

        imgMainBack.setOnClickListener(view12 -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            ActivityCompat.finishAffinity(getActivity());
        });

        // Error View
        llEmpty = (RelativeLayout)view. findViewById(R.id.llEmpty);
        imgError = (ImageView)view. findViewById(R.id.imgError);
        txtErrorTitle = (MyBoldTextViewEcom) view.findViewById(R.id.txtErrorTitle);
        txtErrorContent = (MyTextViewEcom)view. findViewById(R.id.txt_error_content);
        txtRetry = (MyTextViewEcom) view.findViewById(R.id.txtRetry);

        txtRetry.setOnClickListener(v->{
            lnTrackOrder.setVisibility(View.VISIBLE);
            getOrderList();
        });
      /*  if (session.getLogin()) {
                getMyProfile();
        }else {
            tvToolbarTitle.setText("Hi, Guest");
        }*/
       // SharedPreferences deviceSharedPreferences = getActivity().getSharedPreferences(PrefUtils.prefUsername, 0);
        userName =session.getUserName();// deviceSharedPreferences.getString(PrefUtils.prefUsername,null);

        if (session.getLogin()) {
            if (userName == null || userName.equals("")){
                getMyProfile();
                Log.d("UserName Fill","UserName Fill "+userName);
            }else {
                Log.d("UserName Empty","UserName Empty "+userName);
                tvToolbarTitle.setText("Hi, " +userName);
            }
        }else {
            tvToolbarTitle.setText("Hi, Guest");
        }
       // getMyCartList(deviceId);
        getOrderList();
       // getMyCartListPV( deviceId, session.getIboKeyId() );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
     //   getOrderList();
    }

    private void getMyProfile() {
        if (Utils.isNetworkAvailable(getActivity())) {
            mAPIInterfaceTrackOrder =
                    APIClient.getClient(getActivity()).create(APIInterface.class);
            //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
            Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getMyProfileEcom();
            wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
                @Override
                public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                            ProfileModelEcom profileModelEcom = response.body();
                            if (profileModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                MyProfileDataModel myProfileDataModel = profileModelEcom.getData();
                                Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(profileModelEcom));
                                String firstName = myProfileDataModel.getFirstName();
                                String lastName = myProfileDataModel.getLastName();
                                String email = myProfileDataModel.getEmail();
                                String gender = myProfileDataModel.getGender();
                                String mobile = myProfileDataModel.getMobile();
                                Log.d("firstName", "firstName: " + firstName);
                                userName = "Hi, " + firstName;
                                tvToolbarTitle.setText(userName);

                                session.setUserName(firstName);
                               /* SharedPreferences preferences = getActivity().getSharedPreferences(PrefUtils.prefUsername, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(PrefUtils.prefUsername, userName);
                                editor.apply();*/
                                Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));

                            } else if (profileModelEcom.getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || profileModelEcom.getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                        }
                    } else if (response.code() == 401) {
                        Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));
                    /*Intent dash = new Intent(getActivity(), LoginActivityEcom.class);
                    startActivity(dash);*/
                    }
                }

                @Override
                public void onFailure(Call<ProfileModelEcom> call, Throwable t) {
                    Log.d("PROFILEAPIINERROR", "PROFILEAPIINERROR: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }
    private void getOrderList() {
       if (Utils.isNetworkAvailable(getActivity())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            mAPIInterfaceTrackOrder = APIClient.getClient(getActivity()).create(APIInterface.class);
            Call<OrderListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getOrderListEcom();
            wsCallingEvents.enqueue(new Callback<OrderListModelEcom>() {
                @Override
                public void onResponse(Call<OrderListModelEcom> call, Response<OrderListModelEcom> response) {
                   // if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.setVisibility(View.GONE);
                   // }
                    // mSwipeRefreshLayout.setRefreshing(false);
                    orderListModel.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            OrderListModelEcom orderListModelEcom = response.body();
                            if (orderListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (orderListModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                llEmpty.setVisibility(View.GONE);
                                orderListModel.addAll(orderListModelEcom.getData());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                myOrderDetailsActivity = new OrderListAdapter(getActivity(), orderListModel);
                                //productsAdapter = new ProductsAdapter(getActivity(),productArrayList);
                                recyclerView.setAdapter(myOrderDetailsActivity);

                                Log.e("ORDERListAPI","ORDERListAPI: " +  new Gson().toJson(response.body()));
                            } else if (orderListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || orderListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (orderListModel.size() > 0) {
                                 llEmpty.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                //   llEmpty.setVisibility(View.VISIBLE);
                                //   listView.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                noRecordsFound();
                            }
                        }  else {
                            //  serviceUnavailable();
                            recyclerView.setVisibility(View.GONE);
                            noRecordsFound();
                        }
                    }else if (response.code()==-1){
                        recyclerView.setVisibility(View.GONE);
                        noRecordsFound();
                    } else {
                        //  serviceUnavailable();
                        recyclerView.setVisibility(View.GONE);
                        noRecordsFound();
                    }
                }

                @Override
                public void onFailure(Call<OrderListModelEcom> call, Throwable t) {
                    // mSwipeRefreshLayout.setEnabled(false);
                    mProgressDialog.setVisibility(View.GONE);
                    Log.e("ORDERListAPIError","ORDERListAPI: " +  t);
                    if (t.equals( "java.io.IOException: failed to rename" )){
                        recyclerView.setVisibility(View.GONE);
                        //noRecordsFound();
                    }else {
                        recyclerView.setVisibility(View.GONE);
                        noRecordsFound();
                    }

                   /* if (orderListModel.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noRecordsFound();
                    }*/
                    //  serviceUnavailable();
                }
            });
        } else {
             noInternetAvailable();
        }
    }

    //Error View
    void noRecordsFound() {
        txtErrorTitle.setText("You have not placed any orders yet.");
        txtErrorContent.setText("Looks like you have no orders in your order list.");
        imgError.setImageResource(R.drawable.ic_shopping_bag);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        lnTrackOrder.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        lnTrackOrder.setVisibility(View.GONE);
        mProgressDialog.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }

    private void getMyCartListPV(String deviceId, String userId) {
        if (Utils.isNetworkAvailable(getActivity())) {

            mAPIInterfaceTrackOrder = APIClient.getClient(getActivity()).create(APIInterface.class);
            mProgressDialog.setVisibility(View.VISIBLE);

            Call<CartListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getMyCartListEcom(deviceId, userId,cityId);
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
                                //noRecordsFound();

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                cartModels.addAll(response.body().getData().getCart());
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().getCart().size());

                                /*tvTotalAmount.setText("" + response.body().getData().getGrandTotal());
                                tvMycartItemPrice.setText("" + response.body().getData().getSubTotal());
                                tvShipingPrice.setText("" + response.body().getData().getShippingCharge());
                                tvGrandTotal.setText("" + response.body().getData().getGrandTotal());
                                tvGrandTotalPV.setText("" + response.body().getData().getTotalPV());
                                tvGrandTotalNV.setText("" + response.body().getData().getTotalNV());*/
                                pvResult = response.body().getData().getTotalPV();
                                if (pvResult< Const.PVValue){
                                   /* LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)lnTrackOrder.getLayoutParams();
                                    //params.setMargins(0, 0, 0, 90);
                                    params.bottomMargin = 90;
                                    lnTrackOrder.setBackgroundResource(R.color.bottom_sheet_color);
                                    lnTrackOrder.setLayoutParams(params);*/

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    params.setMargins(0, 0, 0, 90);
                                    lnTrackOrder.setLayoutParams(params);

                                }else {
                                  /*  FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)lnTrackOrder.getLayoutParams();
                                    //params.setMargins(0, 0, 0, 60);
                                    params.bottomMargin = 60;
                                    lnTrackOrder.setLayoutParams(params);*/

                                }
                                Log.d("pvResult", "pvResult: " + pvResult);
                                android.util.Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("CartAPI", "CartAPI: " + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                }
            });
        } else {
            noInternetAvailable();
        }
    }
}

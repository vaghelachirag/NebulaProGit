package com.nebulacompanies.ibo.ecom.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.MyAddressAccountActivity;
import com.nebulacompanies.ibo.ecom.MyLocationUsingHelper;
import com.nebulacompanies.ibo.ecom.adapter.PickUpAddressAdapter;
import com.nebulacompanies.ibo.ecom.adapter.PinCodeAdapter;
import com.nebulacompanies.ibo.ecom.locationutils.PermissionUtils;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.model.PickUpAddressData;
import com.nebulacompanies.ibo.ecom.model.PickUpAddressModel;
import com.nebulacompanies.ibo.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class ProductDetailsDialogFragment extends BottomSheetDialogbackFragment
        implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionUtils.PermissionResultCallback {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;

   // ProgressDialog mProgressDialog;
    private APIInterface mAPIInterface;
    private ArrayList<AddressModel> addressDataList = new ArrayList<>();
    private ArrayList<PickUpAddressModel> pickUpAddressModels = new ArrayList<>();
    RecyclerView rvAddress,rvPickUpAddress;
    private PinCodeAdapter pinCodeAdapter;
    private PickUpAddressAdapter pickUpAddressAdapter;
    MyTextViewEcom tvEnterPincode;
    ImageView imgPincodeBack;
    LinearLayout lnPincode, lnAvailability;
    String addressEditCallBack,addressMobile;

    //Location
    // LogCat tag
    private static final String TAG1 = MyLocationUsingHelper.class.getSimpleName();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API

    private GoogleApiClient mGoogleApiClient;

    double latitude;
    double longitude;
    Integer cityID, allCityID,cityIDPickUp,allCityIDPickUp;
    String name, city, pincode, alldata,address,state,addressType;

    String allPickUpData,namePickUp,cityFacility,cityFacilityValue;

    // list of permissions

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    MyTextViewEcom tvDetectLocation;
    boolean isPermissionGranted;
    String currentLocation;
    MyBoldTextViewEcom tvMyAccountAddresses;
    RadioButton rbOnline,rbPickUp,rbAhmd,rbHyd,rbChennai;
    MyBoldTextViewEcom tvcity;
    HorizontalScrollView horizontalScrollView,horizontalPickUp;
    MaterialProgressBar mProgressDialog;
    boolean isAddressType;

    public static ProductDetailsDialogFragment newInstance() {
        return new ProductDetailsDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.bottom_sheet_product_details, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        mAPIInterface = APIClient.getClient( getActivity() ).create( APIInterface.class );

        // mGoogleApiClient = new GoogleApiClient.Builder( getActivity() ).addApi(  )
        mProgressDialog = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        rbOnline = (RadioButton) view.findViewById( R.id.rd1);
        rbPickUp = (RadioButton) view.findViewById( R.id.rd2);
        rbAhmd = (RadioButton) view.findViewById( R.id.rd_ahmd);
        rbHyd = (RadioButton) view.findViewById( R.id.rd_hyd);
        rbChennai = (RadioButton) view.findViewById( R.id.rd_chennai);
        tvcity = (MyBoldTextViewEcom) view.findViewById( R.id.tv_city);
        horizontalScrollView = (HorizontalScrollView) view.findViewById( R.id.horizontal_address);
        horizontalPickUp = (HorizontalScrollView) view.findViewById( R.id.horizontal_pick_up);

        rvAddress = (RecyclerView) view.findViewById( R.id.rv_address_list );
        rvAddress.setHasFixedSize( true );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );
        rvAddress.setLayoutManager( mLayoutManager );


        rvPickUpAddress = (RecyclerView) view.findViewById( R.id.rv_pickup_list );
        rvPickUpAddress.setHasFixedSize( true );
        RecyclerView.LayoutManager mLayoutManagerPickUp = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );
        rvPickUpAddress.setLayoutManager( mLayoutManagerPickUp );

        pinCodeAdapter = new PinCodeAdapter( addressDataList, getActivity(), new PinCodeAdapter.ItemListener() {
            @Override
            public void onItemClick(AddressModel addressDataList) {
                if (addressDataList != null) {
                    cityID = addressDataList.getID();
                    name = addressDataList.getFullName();
                    city = addressDataList.getAddressCity();
                    pincode = addressDataList.getAddressPincode();
                    address = addressDataList.getAddressLineOne() + " "+ addressDataList.getAddressLineTwo();
                    state = addressDataList.getAddressState();

                    allCityID = cityID;
                    addressType = "Door Step";
                    cityFacilityValue = "nebula";
                    alldata = name +", "+ address +", "+ city +", "+ state +"- "+ pincode;
                  // ((ProductDescription) getActivity()).setSelectedLocation( alldata );
                    isAddressType =false;
                    ((MainActivity) getActivity()).setSelectedLocation( addressType,allCityID,alldata ,isAddressType,cityFacilityValue);
                    dismiss();

                }
            }
        } );


        pickUpAddressAdapter = new PickUpAddressAdapter( pickUpAddressModels, getActivity(), new PickUpAddressAdapter.ItemListener() {
            @Override
            public void onItemClick(PickUpAddressModel addressDataList) {
                if (addressDataList != null) {
                    cityIDPickUp = addressDataList.getID();
                    namePickUp = addressDataList.getPickUpAddress();
                    cityFacility = addressDataList.getPickUpAddressFacility();

                    allCityIDPickUp = cityIDPickUp;
                    allPickUpData = namePickUp;
                    cityFacilityValue = cityFacility;
                    isAddressType =true;
                    addressType = "PickUp";

                     //((ProductDescription) getActivity()).setSelectedLocation( alldata );
                    ((MainActivity) getActivity()).setSelectedLocation( addressType,allCityIDPickUp,allPickUpData,isAddressType ,cityFacilityValue);
                    dismiss();

                }
            }
        } );
       /* pinCodeAdapter.setmListener( new PinCodeAdapter.ItemListener() {
            @Override
            public void onItemClick(AddressModel addressDataList) {


            }
        } );*/


        rbOnline.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalScrollView.setVisibility( View.VISIBLE );
                tvcity.setVisibility( View.GONE );
                rbAhmd.setVisibility( View.GONE );
                rbHyd.setVisibility( View.GONE );
                rbChennai.setVisibility( View.GONE );
            }
        } );

        rbPickUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalPickUp.setVisibility( View.GONE );
                horizontalScrollView.setVisibility( View.GONE );
                tvcity.setVisibility( View.VISIBLE );
                rbAhmd.setVisibility( View.VISIBLE );
                rbHyd.setVisibility( View.VISIBLE );
                rbChennai.setVisibility( View.VISIBLE );
            }
        } );


        rbAhmd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalPickUp.setVisibility( View.VISIBLE );
                horizontalScrollView.setVisibility( View.GONE );
                tvcity.setVisibility( View.VISIBLE );
                rbHyd.setVisibility( View.GONE );
                rbChennai.setVisibility( View.GONE );
                getPickUpAddressList("783");
            }
        } );


        rbHyd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalPickUp.setVisibility( View.VISIBLE );
                horizontalScrollView.setVisibility( View.GONE );
                tvcity.setVisibility( View.VISIBLE );
                rbAhmd.setVisibility( View.GONE );
                rbChennai.setVisibility( View.GONE );
                getPickUpAddressList("4460");
            }
        } );


        rbChennai.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalPickUp.setVisibility( View.VISIBLE );
                horizontalScrollView.setVisibility( View.GONE );
                tvcity.setVisibility( View.VISIBLE );
                rbAhmd.setVisibility( View.GONE );
                rbHyd.setVisibility( View.GONE );
                getPickUpAddressList("3659");
            }
        } );

        rvAddress.setAdapter( pinCodeAdapter );
        rvPickUpAddress.setAdapter( pickUpAddressAdapter );
        getAddressList();


        lnPincode = (LinearLayout) view.findViewById( R.id.ln_pincode );
        lnAvailability = (LinearLayout) view.findViewById( R.id.ln_availability );
        tvEnterPincode = (MyTextViewEcom) view.findViewById( R.id.tv_enter_pincode );
        imgPincodeBack = (ImageView) view.findViewById( R.id.img_pincode_back );
        tvDetectLocation = (MyTextViewEcom) view.findViewById( R.id.tv_detect_location );
        tvMyAccountAddresses = (MyBoldTextViewEcom) view.findViewById( R.id.tv_my_account_addresses );


        tvMyAccountAddresses.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent( getActivity(), MyAddressAccountActivity.class );
                startActivity( dash );
            }
        } );


        // view.findViewById( R.id.tv_my_account_addresses ).setOnClickListener( this );


        tvDetectLocation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    //getAddress();

                    dismiss();
                } else {
                    showToast( "Couldn't get the location. Make sure location is enabled on the device" );

                }
            }
        } );

        // view.findViewById( R.id.tv_detect_location ).setOnClickListener( this );
        tvEnterPincode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnAvailability.setVisibility( View.GONE );
                lnPincode.setVisibility( View.VISIBLE );
            }
        } );


        imgPincodeBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnPincode.setVisibility( View.GONE );
                lnAvailability.setVisibility( View.VISIBLE );
            }
        } );


        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement ItemClickListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        //  TextView tvSelected = (TextView) view;

        //MyBoldTextViewEcom myBoldTextViewEcom = (MyBoldTextViewEcom) view;
        MyTextViewEcom myTextViewEcom = (MyTextViewEcom) view;
        //mListener.onItemClick(alldata);
        mListener.onItemClick( currentLocation );
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }


    private void getAddressList() {
        if (Utils.isNetworkAvailable(getActivity())) {
          /*  mProgressDialog = new ProgressDialog( getActivity(), R.style.MyTheme );
            mProgressDialog.show();

            mProgressDialog.setCancelable( false );
            mProgressDialog.setContentView( R.layout.progressdialog_ecom );
            mProgressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );*/
            //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);


            /*mProgressDialog.setOnKeyListener( new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if (mProgressDialog.isShowing()) {
                            //your logic here for back button pressed event
                            mProgressDialog.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            } );*/

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<AddressData> wsCallingEvents = mAPIInterface.getAddressListEcom();
            wsCallingEvents.enqueue( new Callback<AddressData>() {
                @Override
                public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                  /*  if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/

                    mProgressDialog.setVisibility(View.GONE);
                    addressDataList.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            Log.d( "AddressListGETOUT", "AddressListGETOUT: " + response.body().getMessage() );
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                addressDataList.clear();
                                addressDataList.addAll( response.body().getData() );
                                Log.d( "AddressListGETIN", "AddressListGETIN: " + response.body().getMessage() );
                               /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProductDescription.this);
                                rvAddress.setLayoutManager(mLayoutManager);*/
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );
                                rvAddress.setLayoutManager( mLayoutManager );
                                rvAddress.setItemAnimator( new DefaultItemAnimator() );
                              /*  myAddressAdapter = new MyAddressAdapter(addressDataList,ProductDescription.this,totalCartAmount);
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvAddress.setAdapter(myAddressAdapter);*/


                                pinCodeAdapter.notifyDataSetChanged();


                                Log.d( "AddressListGETINData", "AddressListGETINData: " + response.body().getData() );

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (response.body().getData().size() > 0) {
                                //llEmpty.setVisibility(View.GONE);
                                rvAddress.setVisibility( View.VISIBLE );
                            } else {
                                //llEmpty.setVisibility(View.VISIBLE);
                                rvAddress.setVisibility( View.GONE );
                            }
                        }
                    } else if (response.code() == 401) {
                        Log.d( "AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson( response.body() ) );

                        /*Intent dash = new Intent( getActivity(), LoginActivityEcom.class );
                        startActivity( dash );*/

                    }
                }

                @Override
                public void onFailure(Call<AddressData> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                 //   mProgressDialog.dismiss();
                    Log.d( "AddressListGETError", "AddressListGETError: " + t );

                    rvAddress.setVisibility( View.GONE );
                }
            } );
        } else {
            // noInternetConnection();
            Log.d( "AddressListGETErrorNO", "AddressListGETError: NO" );
        }
    }



    private void getPickUpAddressList(String cityId) {
        //if (Utils.isNetworkAvailable(getApplicationContext())) {
          /*  mProgressDialog = new ProgressDialog( getActivity(), R.style.MyTheme );
            mProgressDialog.show();

            mProgressDialog.setCancelable( false );
            mProgressDialog.setContentView( R.layout.progressdialog_ecom );
            mProgressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );*/
            //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);


           /* mProgressDialog.setOnKeyListener( new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if (mProgressDialog.isShowing()) {
                            //your logic here for back button pressed event
                            mProgressDialog.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            } );*/

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<PickUpAddressData> wsCallingEvents = mAPIInterface.getPickUpListEcom( cityId );
            wsCallingEvents.enqueue( new Callback<PickUpAddressData>() {
                @Override
                public void onResponse(Call<PickUpAddressData> call, Response<PickUpAddressData> response) {
                   /* if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                    mProgressDialog.setVisibility(View.GONE);
                    pickUpAddressModels.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            Log.d( "AddressListGETOUT", "AddressListGETOUT: " + response.body().getMessage() );
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                pickUpAddressModels.clear();
                                pickUpAddressModels.addAll( response.body().getData() );
                                Log.d( "AddressListGETIN", "AddressListGETIN: " + response.body().getMessage() );
                               /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProductDescription.this);
                                rvAddress.setLayoutManager(mLayoutManager);*/
                                RecyclerView.LayoutManager mLayoutManagerPickUp = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );
                                rvPickUpAddress.setLayoutManager( mLayoutManagerPickUp );
                                rvPickUpAddress.setItemAnimator( new DefaultItemAnimator() );

                                pickUpAddressAdapter.notifyDataSetChanged();


                                Log.d( "AddressListGETINData", "AddressListGETINData: " + response.body().getData() );

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (response.body().getData().size() > 0) {
                                //llEmpty.setVisibility(View.GONE);
                                rvPickUpAddress.setVisibility( View.VISIBLE );
                            } else {
                                //llEmpty.setVisibility(View.VISIBLE);
                                rvPickUpAddress.setVisibility( View.GONE );
                            }
                        }
                    } else if (response.code() == 401) {
                        Log.d( "AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson( response.body() ) );

                        /*Intent dash = new Intent( getActivity(), LoginActivityEcom.class );
                        startActivity( dash );*/

                    }
                }

                @Override
                public void onFailure(Call<PickUpAddressData> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    //mProgressDialog.dismiss();
                    Log.d( "AddressListGETError", "AddressListGETError: " + t );


                }
            } );
       /* } else {
            mProgressDialog.setVisibility(View.GONE);
            // noInternetConnection();
            Log.d( "AddressListGETErrorNO", "AddressListGETError: NO" );
        }*/
    }


    private void getLocation() {

        try {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation( mGoogleApiClient );
        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder( getActivity(), Locale.getDefault() );

        try {
            addresses = geocoder.getFromLocation( latitude, longitude, 1 ); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get( 0 );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void getAddress() {

        Address locationAddress = getAddress( latitude, longitude );

        if (locationAddress != null) {
            //  String address = locationAddress.getAddressLine(0);
            // String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            //  String state = locationAddress.getAdminArea();
            //  String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            if (!TextUtils.isEmpty( city )) {
                currentLocation = city;


                if (!TextUtils.isEmpty( city )) {

                    if (!TextUtils.isEmpty( postalCode ))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty( postalCode ))
                        currentLocation += "\n" + postalCode;
                }

               // ((ProductDescription) getActivity()).setSelectedLocation( currentLocation );
                //  Toast.makeText( getActivity(),"currentLocation: "+currentLocation,Toast.LENGTH_SHORT ).show();

            }

        }

    }

    /**
     * Creating google api client object
     */

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API ).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval( 10000 );
        mLocationRequest.setFastestInterval( 5000 );
        mLocationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest( mLocationRequest );

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings( mGoogleApiClient, builder.build() );

        result.setResultCallback( new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult( getActivity(), REQUEST_CHECK_SETTINGS );

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        } );


    }


    /**
     * Method to verify google play services on the device
     */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable( getActivity() );

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError( resultCode )) {
                googleApiAvailability.getErrorDialog( getActivity(), resultCode,
                        PLAY_SERVICES_REQUEST ).show();
            } else {
                Toast.makeText( getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG )
                        .show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent( data );
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
        getAddressList();
       /* Intent editAddressCallBack = getActivity().getIntent();
        if (editAddressCallBack != null && addressEditCallBack != null) {
            addressMobile = editAddressCallBack.getStringExtra( "addAddressCallBackAccountBottom" );
            getAddressList();

        }*/
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i( TAG1, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode() );
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    // Permission check functions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult( requestCode, permissions, grantResults );

    }


    @Override
    public void PermissionGranted(int request_code) {
        Log.i( "PERMISSION", "GRANTED" );
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i( "PERMISSION PARTIALLY", "GRANTED" );
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i( "PERMISSION", "DENIED" );
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i( "PERMISSION", "NEVER ASK AGAIN" );
    }

    public void showToast(String message) {
        Toast.makeText( getActivity(), message, Toast.LENGTH_SHORT ).show();
    }

}

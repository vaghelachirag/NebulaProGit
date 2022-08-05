package com.nebulacompanies.ibo.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.koushikdutta.ion.Ion;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.IBOGeoLocation;
import com.nebulacompanies.ibo.model.IBOGeoLocationInfo;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


/**
 * Class : IBOGeoLocationFragment
 * Details:
 * Create by : Jadav Chirag At NebulaApplication Infra space LLP 11-09-2017
 * Modification by :
 */
public class IBOGeoLocationFragment extends Fragment implements LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    //TODO : Class Variables
    private APIInterface mAPIInterface;
    private GoogleMap mMap;
    private ArrayList<IBOGeoLocationInfo> mArrayListGEOLocation = new ArrayList<>();
    //private double latitude = 23.013004, longitude = 72.513417;
    private double latitude, longitude;
    // private double latitude, longitude;

    LocationManager locationManager;
    SupportMapFragment mapFragment;

    //TODO : UI Components

    LinearLayout llEmpty, llGoogleMap;
    TextView txtRetry, txtErrorTitle;
    ImageView imgRefresh;
    Session session;

    private FusedLocationProviderClient fusedLocationProviderClient = null;
    private Location currentLocation;
    MaterialProgressBar mProgressDialog;


    /************************************************************
     *                    OVERRIDE METHOD
     *************************************************************/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_geo_location_ibo, container, false);

        //TODO : Initialization
        llGoogleMap = view.findViewById(R.id.llGoogleMap);
        // llGoogleMap.setVisibility(View.GONE);
        llEmpty = view.findViewById(R.id.llEmpty);
        txtRetry = view.findViewById(R.id.txtRetry);
        txtErrorTitle = view.findViewById(R.id.txtErrorTitle);
        mProgressDialog = view.findViewById( R.id.progresbar );
        session = new Session(getActivity());
        // doGetIBOGeoLocation();
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetIBOGeoLocation();
            }
        });

       /* if (mMap == null) {
            final OnMapReadyCallback listener = this;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (isAdded()) {
                        mapFragment = new SupportMapFragment();
                        FragmentManager fm = getChildFragmentManager();
                        fm.beginTransaction()
                                .replace(R.id.map, mapFragment).commit();
                        mapFragment.getMapAsync(listener);
                    }
                }
            }, 1000);
        }*/

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapIBOLocation);
        mapFragment.getMapAsync(this);

        // mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        /*if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            if (isLocationEnabled())
                getLocation();
            else
                showSettingsAlert();
        }
        doGetIBOGeoLocation();*/
        imgRefresh = view.findViewById(R.id.imgRefresh);
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLastLocation();
                if (currentLocation != null) {

                    doGetIBOGeoLocation();
                }
            }
        });
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        //mMap.setOnMarkerClickListener(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        fetchLastLocation();

    }

    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    latitude = currentLocation.getLatitude();
                    longitude = currentLocation.getLongitude();
                    // Toast.makeText(getActivity(),currentLocation.getLatitude()+" "+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SendLocation();
                } else {
                   // Toast.makeText(getActivity(), "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doGetIBOGeoLocation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    if (isLocationEnabled())
                        getLocation();
                    else
                        showSettingsAlert();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            if (currentLocation != null) {
                doGetIBOGeoLocation();
            }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        fetchLastLocation();
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.e(getClass().getSimpleName(), "IBO GEO Location Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        // showSettingsAlert();
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /************************************************************
     *                    PRIVATE METHOD
     *************************************************************/
    protected Marker createMarker(IBOGeoLocationInfo info) {

        Bitmap bmImg = null;
        if (info.getRankIcon() != null) {
            try {
                bmImg = Ion.with(getContext()).load(info.getRankIcon()).asBitmap().get();
                bmImg = Bitmap.createScaledBitmap(bmImg, 100, 100, true);
                //bmImg = Ion.with(getContext()).load("http://203.88.139.169:9065//Content/Images/RankIcons/member.png").asBitmap().get();
            } catch (Exception error) {
                Log.e(getClass().getSimpleName(), " ERROR : " + error.getMessage());
            }
        }
        Marker mMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(info.getLatitude(), info.getLongitude()))
                .anchor(1f, 1f)
                .title(info.getTitle())
                .snippet(info.getSnippet())
                .icon(BitmapDescriptorFactory.fromBitmap(bmImg)));
        mMarker.setTag(info);
        return mMarker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() != null) {
            marker.showInfoWindow();
            return true;
        } else
            return false;
    }

    /************************************************************
     *                    WEB SERVICES
     *************************************************************/

    private void doGetIBOGeoLocation() {
        if (AppUtils.isOnline(getActivity())) {
           /* final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            *//*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*//*
            mProgressDialog.show();
            llEmpty.setVisibility(View.INVISIBLE);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/

            mProgressDialog.setVisibility( View.VISIBLE );

            //TODO : REST API Request Call.
            //  Call<IBOGeoLocation> requestCall = mAPIInterface.postIBOGeoLocation(latitude, longitude);
            Call<IBOGeoLocation> requestCall = mAPIInterface.postIBOGeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
            requestCall.enqueue(new Callback<IBOGeoLocation>() {
                @Override
                public void onResponse(Call<IBOGeoLocation> call, Response<IBOGeoLocation> response) {
                    // TODO : Success Parsing & Display Data Binding.
                    if (mProgressDialog != null ) {
                        mProgressDialog.setVisibility( View.GONE );
                    }
                    try {
                        if (response.isSuccessful() && response.code() == 200) {
                            if (mProgressDialog != null) {
                                mProgressDialog.setVisibility( View.GONE );
                            }
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                if (mProgressDialog != null ) {
                                    mProgressDialog.setVisibility( View.GONE );
                                }
                                mArrayListGEOLocation.clear();
                                mArrayListGEOLocation.addAll(response.body().getData());

                                MarkerOptions markerIBOOptions = new MarkerOptions();
                                markerIBOOptions.position(new LatLng(latitude, longitude))
                                        .title(session.getLoginID() + "-" + session.getUserName());
                                mMap.addMarker(markerIBOOptions);

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
                                // mMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap());
                                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
                                for (int i = 0; i < mArrayListGEOLocation.size(); i++) {
                                    createMarker(mArrayListGEOLocation.get(i));
                                }
                            } else {
                                if (mProgressDialog != null ) {
                                    mProgressDialog.setVisibility( View.GONE );
                                }
                                MarkerOptions markerIBOOptions = new MarkerOptions();
                                markerIBOOptions.position(new LatLng(latitude, longitude))
                                        .title(session.getLoginID() + "-" + session.getUserName());
                                mMap.addMarker(markerIBOOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
                                //mMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap());
                                // mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
                                AppUtils.displayErrorMessage(getActivity(), response.body().getMessage());
                            }
                            if (mProgressDialog != null ) {
                                mProgressDialog.setVisibility( View.GONE );
                            }
                            llGoogleMap.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                        } else if (response.code() == 500 || response.code() == 401) {
                            if (mProgressDialog != null ) {
                                mProgressDialog.setVisibility( View.GONE );
                            }
                            llGoogleMap.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                            txtRetry.setVisibility(View.VISIBLE);
                            txtErrorTitle.setText(getString(R.string.error_internal_server));
                            AppUtils.displayServerErrorMessage(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong!Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception Error) {
                        Log.e(getClass().getSimpleName(), "ERROR " + Error.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<IBOGeoLocation> call, Throwable t) {
                    Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                    mProgressDialog.setVisibility( View.GONE );
                    llGoogleMap.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.VISIBLE);
                    txtRetry.setVisibility(View.VISIBLE);
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(getActivity());
            llGoogleMap.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
            txtRetry.setVisibility(View.VISIBLE);
            txtErrorTitle.setText(getString(R.string.Network_is_not_available));
        }
    }


    /*
     *  Custom Info Adapter click action
     * */
    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        View convertView;
        CircleImageView imgProfile;
        TextView txtIBOId, txtIBOName, txtMobileNo;
        LinearLayout llIBOName, llMobile;
        IBOGeoLocationInfo nearMe;

        public CustomInfoWindowAdapter() {  //IBOGeoLocationInfo nearMe
            convertView = getActivity().getLayoutInflater().inflate(R.layout.row_layout_ibo_marker, null);
            //  this.nearMe = nearMe;
            this.convertView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_layout_ibo_marker, null);
            // Initialization info Windows
            imgProfile = convertView.findViewById(R.id.imgProfile);
            txtIBOId = convertView.findViewById(R.id.txtIBOId);
            llIBOName = convertView.findViewById(R.id.llIBOName);
            txtIBOName = convertView.findViewById(R.id.txtIBOName);
            txtMobileNo = convertView.findViewById(R.id.txtMobileNo);
            llMobile = convertView.findViewById(R.id.llMobile);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            //TODO : Binding Data
            IBOGeoLocationInfo infoWindowData = (IBOGeoLocationInfo) marker.getTag();

            if (infoWindowData != null) {
                if (infoWindowData.getProfilePic() != null) {
                    try {
                        Bitmap bmImg = Ion.with(getContext()).load(infoWindowData.getProfilePic()).asBitmap().get();
                        imgProfile.setImageBitmap(bmImg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    imgProfile.setVisibility(View.VISIBLE);
                } else
                    imgProfile.setVisibility(View.GONE);

                txtIBOId.setText("" + infoWindowData.getIBOId());

                if (infoWindowData.getIBOUser() != null && infoWindowData.getIBOUser().length() > 0) {
                    llIBOName.setVisibility(View.VISIBLE);
                    txtIBOName.setText(infoWindowData.getIBOUser());
                } else
                    llIBOName.setVisibility(View.GONE);

                if (infoWindowData.getMobileNo() != null && infoWindowData.getMobileNo().length() > 0) {
                    llMobile.setVisibility(View.VISIBLE);
                    txtMobileNo.setText("+91 " + infoWindowData.getMobileNo());
                } else
                    llMobile.setVisibility(View.GONE);

            } else {
                imgProfile.setVisibility(View.INVISIBLE);
                txtIBOId.setText("" + session.getLoginID());
                if (session.getUserName().length() > 0) {
                    llIBOName.setVisibility(View.VISIBLE);
                    txtIBOName.setText(session.getUserName());
                } else
                    llIBOName.setVisibility(View.GONE);
                llMobile.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }


    void SendLocation() {
        if (Utils.isNetworkAvailable(getActivity())) {
            Call<JSONObject> wsSendLocation = mAPIInterface.PostLatLong(latitude, longitude);
            wsSendLocation.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                }
            });
        }
    }
}

package com.nebulacompanies.ibo.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIClientCustomer;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.MyViewPagerOfferAdapter;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.model.CreateTicketModel;
import com.nebulacompanies.ibo.ecom.model.EcomOfferModel;
import com.nebulacompanies.ibo.ecom.model.EcomOfferModelList;
import com.nebulacompanies.ibo.ecom.model.MarkCartDeleteModel;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.fragments.DashboardFragment;
import com.nebulacompanies.ibo.fragments.DownloadsFragment;
import com.nebulacompanies.ibo.fragments.HolidayPackagesFragment;
import com.nebulacompanies.ibo.fragments.IBOListFragment;
import com.nebulacompanies.ibo.fragments.MyScratchFragment;
import com.nebulacompanies.ibo.model.BasicInfo;
import com.nebulacompanies.ibo.model.GetOfferImageResponse.GetOfferImageData;
import com.nebulacompanies.ibo.model.GetOfferImageResponse.GetOfferImageResponse;
import com.nebulacompanies.ibo.model.MyAccount;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.WrappingViewPager;
import com.nebulacompanies.ibo.web.AccountSummaryFragmentWeb;
import com.nebulacompanies.ibo.web.DownlineFragmentWeb;
import com.nebulacompanies.ibo.web.EventsFragmentWeb;
import com.nebulacompanies.ibo.web.MyIncomeFragmentWeb;
import com.nebulacompanies.ibo.web.MyProfileFragmentWeb;
import com.nebulacompanies.ibo.web.MySalesFragmentWeb;
import com.nebulacompanies.ibo.web.PrivacyFragmentWeb;
import com.nebulacompanies.ibo.web.ProjectsFragmentWeb;
import com.nebulacompanies.ibo.web.PromotionFragmentWeb;
import com.nebulacompanies.ibo.web.PurchaseFragmentWeb;
import com.nebulacompanies.ibo.web.RegistrationFragmentWeb;
import com.nebulacompanies.ibo.web.StarFragmentWeb;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionFail;
import static com.nebulacompanies.ibo.ecom.utils.Utils.actionUservalid;
import static com.nebulacompanies.ibo.util.Config.showProfileUpload;
import static com.nebulacompanies.ibo.util.Config.showSPC;
import static com.nebulacompanies.ibo.util.Config.starplatinumID;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int UPDATE_REQUEST_CODE = 101;
    public static final String TAG = "DashboardActivity";
    public static String SUCCESSDATA = "successdata";

    public DrawerLayout drawer;
    NavigationView navigationView;
    Menu nav_Menu;
    LinearLayout linearLayoutProfile;
    public LinearLayout lnSettings;
    TextView mName, mId;
    ConnectionDetector cd;
    Dialog dialog;
    MyTextView ibonametxt, iboidtxt, iborank;
    ImageView imgKYCStatus;
    CircleImageView imgRank;
    int IMAGE_PICKER_SELECT = 101;
    public ActionBarDrawerToggle toggle;
    private boolean doubleBackToExitPressedOnce = false;
    private Fragment fragment = null;
    private APIInterface mAPIInterface;
    private AppUpdateManager appUpdateManager;
    Session session;
    Runnable r;
    ReviewManager manager;
    int selectMyPurchasePage;
    String TokenKey, DeviceId, rank;
    Intent dashBoardData;
    final Handler handler = new Handler();
    UtilsVersion utilsVersion = new UtilsVersion();
    Toolbar toolbar;
    Utils utils;
    IntentFilter filterLogin;


    private TextView[] dots;
   public static WrappingViewPager viewPager ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity_navigation);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        //android O fix bug orientation
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        initFullDialog();
        initData();
        initToolBar();
        utilsVersion.checkVersionFirsTime(this);
        initDrawer();
        initConditionDialog();
        initUI();
        checkAndRequestPermissions();
        setDataUI();
        if (session.getLogin()) {
            markWalletDelete();
            sendTransaction();
        }
        getIntentData();
        //initReview();


    }

    private void getIntentData() {
        Bundle b = getIntent().getExtras();
        if (b !=null){
            int int_From = b.getInt("From");
            Log.e("From",""+int_From);
            if(int_From != 1){
                getOfferImageResponse();
            }
        }
        else{
            getOfferImageResponse();
        }
    }


    private void getOfferImageResponse() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<GetOfferImageResponse> wsCallingOfferImage = mAPIInterface.getOfferImageResponse();

            wsCallingOfferImage.enqueue(new Callback<GetOfferImageResponse>() {
                @Override
                public void onResponse(Call<GetOfferImageResponse> call, Response<GetOfferImageResponse> response) {

                    if(response.isSuccessful()){
                        if (response.body().getData() != null){
                            Log.e("Response"," Sucessfull!");
                            ArrayList<GetOfferImageData> getOfferImageData = new ArrayList<>();
                            getOfferImageData = response.body().getData();
                            showOfferDialoug(getOfferImageData);

                        }
                    }
                    else {
                        Log.e("Response","Not Sucessfull!");
                    }
                }

                @Override
                public void onFailure(Call<GetOfferImageResponse> call, Throwable t) {
                    Log.e("Response",t.getMessage());
                }
            });
        }
    }
    private void showOfferDialoug(ArrayList<GetOfferImageData> getOfferImageData) {

        if(getOfferImageData.isEmpty() || getOfferImageData == null){
            return;
        }

        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialoug_offer);


        Button iv_Close = (Button)dialog.findViewById(R.id.iv_close);
        iv_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog !=null){
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ViewPager rv_OfferImage = (ViewPager) dialog.findViewById(R.id.view_pagerOffer);


        MyViewPagerOfferAdapter adapter = new MyViewPagerOfferAdapter(this,getOfferImageData);
        LinearLayout  dotsLayout = (LinearLayout)dialog.findViewById(R.id.layoutDots);
        rv_OfferImage.setAdapter(adapter);


        dialog.show();


        rv_OfferImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               Log.e("Viewpager","Selected");
                addBottomDots(position,dotsLayout,getOfferImageData,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("Viewpager","Selected Changed");
            }
        });

        // adding bottom dots
        addBottomDots(0,dotsLayout,getOfferImageData,true);

    }

    private void addBottomDots(int currentPage, LinearLayout dotsLayout, ArrayList<GetOfferImageData> getOfferImageData, boolean blIsFirstTime) {
        dots = new TextView[getOfferImageData.size()];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        if(!blIsFirstTime){
            dotsLayout.removeAllViews();
        }

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsActive[0]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsInactive[0]);
    }

    private void setDataUI() {
        setAPIData();
        setFragment();
        replaceFragment(fragment);
    }

    ProgressDialog mLoadProgressDialog;

    public void initFullDialog() {
        mLoadProgressDialog = new ProgressDialog(DashboardActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    public void showFullDialog() {
        if (mLoadProgressDialog != null && !mLoadProgressDialog.isShowing() && session.getLoader())
            mLoadProgressDialog.show();
    }

    public void hideFullDialog() {
        if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing())
            mLoadProgressDialog.dismiss();
    }

    private void setAPIData() {
        if (showSPC)
            showFullDialog();
        firebaseTokenGenerated(session.getIboKeyId());

        setName();
        setSessionData();
        getExpireOrnot();
    }

    private void getExpireOrnot() {
        //Call API from Utils
        utils.getExpireUser(mAPIInterface, this, session);
    }

    private void setSessionData() {
        if (!session.getLogin()) {
            iboidtxt.setText("");
            ibonametxt.setText("");
            iborank.setVisibility(View.GONE);
            imgKYCStatus.setVisibility(View.GONE);
        } else {
            ibonametxt.setText(session.getUserName());
            iboidtxt.setText(session.getLoginID());
            // we can set value from 0 to 255
            navigationView.getBackground().setAlpha(240);
        }
    }

    private ReviewManager reviewManager;
    ReviewInfo reviewInfo;

    private void initReview() {
        reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                showRateAppFallbackDialog();
            }
        });
    }

    private void showRateAppFallbackDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.rate_app_title)
                .setMessage(R.string.rate_app_message)
                .setPositiveButton(R.string.rate_btn_pos, (dialog, which) -> {
                })
                .setNegativeButton(R.string.rate_btn_neg,
                        (dialog, which) -> {
                        })
                .setNeutralButton(R.string.rate_btn_nut,
                        (dialog, which) -> {
                        })
                .setOnDismissListener(dialog -> {
                })
                .show();
    }

    String fragTAG = "";

    private void setFragment() {
        fragment = new DashboardFragment();
        fragTAG = getResources().getString(R.string.nav_dashboard);
        int notification = 0;
        if (getIntent().getExtras() != null) {
            dashBoardData = getIntent();
            if (dashBoardData != null) {
                selectMyPurchasePage = dashBoardData.getIntExtra("PAYMENTSUCCESS", 0);
                notification = dashBoardData.getIntExtra("notificationIntent", 0);
            }
            if (selectMyPurchasePage == 1) {
                navigationView.setCheckedItem(R.id.nav_my_purchase);
                getSupportActionBar().setTitle(getResources().getString(R.string.my_purchases));
                fragTAG = getResources().getString(R.string.my_purchases);
                fragment = new PurchaseFragmentWeb();
            }
            if (notification == 1) {
                navigationView.setCheckedItem(R.id.nav_events);
                getSupportActionBar().setTitle(getResources().getString(R.string.nav_events));
                fragTAG = getResources().getString(R.string.nav_events);
                fragment = new EventsFragmentWeb();
            }
        }
    }

    Uri outPutfileUri;

    private void initUI() {
        filterLogin = new IntentFilter();
        filterLogin.addAction(Utils.actionLogin);
        filterLogin.addAction(actionUservalid);
        filterLogin.addAction(actionFail);
        registerReceiver(myReceiver, filterLogin);

        navigationView = findViewById(R.id.nav_view_dashboard);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);
        ibonametxt = headerView.findViewById(R.id.iboname);
        iboidtxt = headerView.findViewById(R.id.iboid);
        iborank = headerView.findViewById(R.id.iborank);
        imgKYCStatus = headerView.findViewById(R.id.img_kyc);
        imgRank = headerView.findViewById(R.id.imgRank);
        linearLayoutProfile = headerView.findViewById(R.id.ln_profile);

        imgRank.setOnClickListener(v -> {
            if(showProfileUpload) {
         /*   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");*/

/*
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");*/

                //   startActivityForResult(intent, IMAGE_PICKER_SELECT);
//https://stackoverflow.com/questions/38971608/android-camera-and-photo-picker-intent
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setType("image/*");


                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                outPutfileUri = Uri.fromFile(file);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);

                Intent chooserIntent = Intent.createChooser(pickPhoto, "Choose profile picture");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePicture});
                startActivityForResult(chooserIntent, IMAGE_PICKER_SELECT);
            }
        });

    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Utils.actionLogin)) {
                setAPIData();
            } else if (intent.getAction().equalsIgnoreCase(actionUservalid)) {
                getOfferDetails(); // after
                getImageUrlDetails(); // after

            } else if (intent.getAction().equalsIgnoreCase(actionFail)) {
                hideFullDialog();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private void initDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // do something when drawer opened
               /* InputMethodManager imm =(InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);*/
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
               /* InputMethodManager imm =(InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);*/
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        lnSettings = toolbar.findViewById(R.id.toolbar_settings);
        mName = toolbar.findViewById(R.id.toolbar_title1);
        mId = toolbar.findViewById(R.id.toolbar_title2);
        mName.setText(session.getLoginID());
        lnSettings.setOnClickListener(view -> {
            Intent login = new Intent(DashboardActivity.this, SettingFragment.class);
            startActivity(login);
        });
    }

    private void initData() {
        manager = ReviewManagerFactory.create(this);
        appUpdateManager = AppUpdateManagerFactory.create(this);
        cd = new ConnectionDetector(getApplicationContext());
        session = new Session(this);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        utils = new Utils();
    }

    @SuppressLint("MissingPermission")
    private void firebaseTokenGenerated(String user) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                TokenKey = FirebaseInstanceId.getInstance().getToken();
                Log.e("Token Get ", "Token Get " + TokenKey);
                DeviceId = " DEVICE ID: " + Build.SERIAL + ": MODEL: " + Build.MODEL + ": VERSION: " + Build.VERSION.RELEASE + ": MANUFACTURER: " + Build.MANUFACTURER;
                Log.e("Token Get 110 ", "Token Get110 " + TokenKey);
                Config.REGISTERED_TOKEN = FirebaseInstanceId.getInstance().getToken();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.e("Token Get 11 ", "Token Get " + TokenKey);
                    GetNotificationToken(TokenKey, DeviceId, "0", "0", user);
                    Log.e("Token Get ", "Token Get " + TokenKey);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void GetNotificationToken(String tokenKey, String deviceId, String imei1, String imei2, String user) {
        Call<JsonObject> wsCallingToken = mAPIInterface.postTokenKey(tokenKey, deviceId, imei1, imei2, user);
        wsCallingToken.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("INFO", "Token new Send:-" + response.body());
                        Log.e("Token Get 3", "Token Get 3: " + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                Log.e("Token Get 4", "Token Get 4: " + t.getMessage());
            }
        });
    }

    private boolean checkAndRequestPermissions() {
        int read_phone_state = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);

        /*Removed Due to Google Play Store Policy*/
        /*int read_phone_state = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        int read_contacts = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS);*/
        int read_storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int fine_location = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
//        int coarse_location = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_phone_state_noti = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);


        /*Removed Due to Google Play Store Policy*/
        /*int read_sms = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS);
        int receive_sms = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS);
        int send_sms = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);*/
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (read_phone_state != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CALL_PHONE);
        }
        /*Removed Due to Google Play Store Policy*/
        /*if (read_phone_state != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (read_contacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_CONTACTS);
        }*/
        if (read_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (write_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
//        if (fine_location != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (coarse_location != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
//        }

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (read_phone_state_noti != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        /*Removed Due to Google Play Store Policy*/
        /*if (receive_sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.RECEIVE_SMS);
        }
         if (send_sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.SEND_SMS);
        }
        if (read_sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
        }*/

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 11);
            return false;
        } else {
            /*if(Utils.isNetworkAvailable(getApplicationContext())) {
             *//* if (NebulaApplication.getGoogleApiHelper().isConnected()) {
                    //Get google api client
                    mGoogleApiClient = NebulaApplication.getGoogleApiHelper().getGoogleApiClient();*//*
                    //if(isLocationPermissionGranted(this)) {
                    new LocationSender(this, mGoogleApiClient, "").execute();
                //}
            }*/
            //if(isReadPhoneStatePermissionGranted(this)) {
            //}
            // getDeviceInfo();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        String permission = permissions[i];
                        if (Manifest.permission.READ_PHONE_STATE.equals(permission)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                // you now have permission

                              //  getOfferImageResponse();
                            }
                        }

                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }

        // other 'case' lines to check for other
        // permissions this app might request
    }

    private void replaceFragment(Fragment fragFragment) {
        //clearTab();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(fragTAG).replace(R.id.frameLayoutContent, fragFragment, fragTAG).commit();
        //fragmentManager.beginTransaction().addToBackStack("").replace(R.id.frameLayoutContent, fragment).commit();
        supportInvalidateOptionsMenu();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = openHome();
            fragTAG = getResources().getString(R.string.nav_dashboard);
        } else if (id == R.id.nav_my_income) {
            navigationView.setCheckedItem(R.id.nav_my_income);
            getSupportActionBar().setTitle(getResources().getString(R.string.my_income));
            fragTAG = getResources().getString(R.string.nav_my_income);
            fragment = new MyIncomeFragmentWeb();
        } else if (id == R.id.nav_offers) {
            navigationView.setCheckedItem(R.id.nav_offers);
            getSupportActionBar().setTitle("Cashback & Offers");
            fragTAG = "Cashback & Offers";
            fragment = new MyScratchFragment();
        } else if (id == R.id.nav_account_summary) {
            navigationView.setCheckedItem(R.id.nav_account_summary);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_account_summary));
            fragTAG = getResources().getString(R.string.nav_account_summary);
            fragment = new AccountSummaryFragmentWeb();
        } else if (id == R.id.nav_downloads) {
            navigationView.setCheckedItem(R.id.nav_downloads);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_downloads));
            fragTAG = getResources().getString(R.string.nav_downloads);
            fragment = new DownloadsFragmentWeb();
        } else if (id == R.id.nav_events) {
            navigationView.setCheckedItem(R.id.nav_events);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_events));
            fragTAG = getResources().getString(R.string.nav_events);
            fragment = new EventsFragmentWeb();
        } else if (id == R.id.nav_ibolist) {
            navigationView.setCheckedItem(R.id.nav_ibolist);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_ibo_list));
            fragTAG = getResources().getString(R.string.nav_ibo_list);
            fragment = new IBOListFragment();
        } else if (id == R.id.registation) {
            navigationView.setCheckedItem(R.id.registation);
            fragTAG = getResources().getString(R.string.nav_registation);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_registation));
            fragment = new RegistrationFragmentWeb();
        } else if (id == R.id.nav_my_sales) {
            openMySales();
        } else if (id == R.id.nav_my_purchase) {
            openMyPurchases();
        } else if (id == R.id.nav_my_downline) {
            openMyCommunity();
        } else if (id == R.id.nav_report) {
            openMyStarReport();
        } else if (id == R.id.nav_my_promotions) {
            navigationView.setCheckedItem(R.id.nav_my_promotions);
            getSupportActionBar().setTitle(getResources().getString(R.string.wallet_detail));
            fragTAG = getResources().getString(R.string.wallet_detail);
            fragment = new PromotionFragmentWeb();
        } else if (id == R.id.nav_projects) {
            navigationView.setCheckedItem(R.id.nav_projects);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_projects));
            fragTAG = getResources().getString(R.string.nav_projects);
            fragment = new ProjectsFragmentWeb();
        } else if (id == R.id.nav_pin) {
            Intent pinPayout = new Intent(DashboardActivity.this, PinActivity.class);
            pinPayout.putExtra("home", true);
            pinPayout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(pinPayout);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else if (id == R.id.nav_holidays) {
            navigationView.setCheckedItem(R.id.nav_holidays);
            getSupportActionBar().setTitle(getResources().getString(R.string.holiday_packages));
            fragTAG = getResources().getString(R.string.holiday_packages);
            fragment = new HolidayPackagesFragment();
        } else if (id == R.id.nav_profile) {
            openMyProfile();
        } else if (id == R.id.nav_query) {
            openIBOsupport();
        } else if (id == R.id.nav_privacy) {
            navigationView.setCheckedItem(R.id.nav_privacy);
            getSupportActionBar().setTitle(getResources().getString(R.string.nav_privacy));
            fragTAG = getResources().getString(R.string.nav_privacy);
            fragment = new PrivacyFragmentWeb();
        }
        if (fragment != null) {
            replaceFragment(fragment);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openIBOsupport() {
        // if (session.getLogin()) {
        navigationView.setCheckedItem(R.id.nav_query);
        getSupportActionBar().setTitle(getResources().getString(R.string.raise_query));
        fragTAG = getResources().getString(R.string.raise_query);
        fragment = new RaiseQueryScreen();
       /* } else {
            utils.openNoSession(DashboardActivity.this, utils.gotoIbosupport);
        }*/
    }

    private void openMyProfile() {
        // if (session.getLogin()) {
        navigationView.setCheckedItem(R.id.nav_profile);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_profile));
        fragTAG = getResources().getString(R.string.nav_profile);
        fragment = new MyProfileFragmentWeb();
       /* } else {
            utils.openNoSession(DashboardActivity.this, utils.gotoMyProfile);
        }*/
    }

    private void openMySales() {
        // if (session.getLogin()) {
        navigationView.setCheckedItem(R.id.nav_my_sales);
        getSupportActionBar().setTitle(getResources().getString(R.string.my_sales));
        fragTAG = getResources().getString(R.string.my_sales);
        fragment = new MySalesFragmentWeb();
        /*} else {
            utils.openNoSession(DashboardActivity.this, utils.gotoMysales);
        }*/
    }

    private void openMyPurchases() {
        // if (session.getLogin()) {
        navigationView.setCheckedItem(R.id.nav_my_purchase);
        getSupportActionBar().setTitle(getResources().getString(R.string.my_purchases));
        fragTAG = getResources().getString(R.string.my_purchases);
        fragment = new PurchaseFragmentWeb();
       /* } else {
            utils.openNoSession(DashboardActivity.this, utils.gotoMypurchase);
        }*/
    }

    private void openMyCommunity() {
        // if (session.getLogin()) {
        navigationView.setCheckedItem(R.id.nav_my_downline);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_my_downline));
        fragTAG = getResources().getString(R.string.nav_my_downline);
        fragment = new DownlineFragmentWeb();
       /* } else {
            utils.openNoSession(DashboardActivity.this, utils.gotoMycommunity);
        }*/
    }

    private void openMyStarReport() {
        navigationView.setCheckedItem(R.id.nav_report);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_star_report));
        fragTAG = getResources().getString(R.string.nav_star_report);
        fragment = new StarFragmentWeb();
    }

    private Fragment openHome() {
        navigationView.setCheckedItem(R.id.nav_home);
        fragTAG = getResources().getString(R.string.nav_dashboard);
        setName();
        return new DashboardFragment();
    }

    Uri selectedMediaUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final int unmaskedRequestCode = requestCode & 0x0000ffff;

        Log.d("onActivity", resultCode + " : dashboard Activity: " + requestCode + " : " + unmaskedRequestCode);
        // check if the request code is same as what is passed  here it is 2
        if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == IMAGE_PICKER_SELECT) {

            } else if (requestCode == utils.gotoMysales || unmaskedRequestCode == utils.gotoMysales) {
                navigationView.getMenu().findItem(R.id.nav_my_sales).setChecked(false);
                loadHome();
            } else if (requestCode == utils.gotoMypurchase || unmaskedRequestCode == utils.gotoMypurchase) {
                navigationView.getMenu().findItem(R.id.nav_my_purchase).setChecked(false);
                loadHome();
            } else if (requestCode == utils.gotoMycommunity || unmaskedRequestCode == utils.gotoMycommunity) {
                navigationView.getMenu().findItem(R.id.nav_my_downline).setChecked(false);
                loadHome();
            } else if (requestCode == utils.gotoMyProfile || unmaskedRequestCode == utils.gotoMyProfile) {
                navigationView.getMenu().findItem(R.id.nav_profile).setChecked(false);
                loadHome();
            } else if (requestCode == utils.gotoIbosupport || unmaskedRequestCode == utils.gotoIbosupport) {
                navigationView.getMenu().findItem(R.id.nav_query).setChecked(false);
                loadHome();
            }
        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICKER_SELECT) {

                try {
                    // MEDIA GALLERY
                    selectedMediaUri = data.getData();
                    Uri selectedImageUri = data.getData();
                    String selectedImagePath = getPath(selectedImageUri);
                    // submitTicket() in CustomerSupportjava
                    File selectedFile = new File(selectedImagePath);
                    String strFileName = selectedFile.getName();
                    Toast.makeText(this, "sele picker: " + selectedImagePath + " : " + strFileName, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    //pic coming from camera
                   // Bitmap bitmap = null;
                    try {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                      //  imgRank.setImageBitmap(photo);

                       /* bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), outPutfileUri);
                        imgRank.setImageBitmap(bitmap);*/
                        Toast.makeText(this, "sele: photo" , Toast.LENGTH_SHORT).show();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                }
                //  uploadFile(selectedFile,strFileName);
              /*  fileclose.setVisibility(View.VISIBLE);
                selectedFile = new File(selectedImagePath);
                strFileName = selectedFile.getName();
                tvFileName.setText(selectedFile.getName());*/
            }
            // update Drawer Info
            else if (requestCode == utils.gotoMysales || unmaskedRequestCode == utils.gotoMysales) {
                openMySales();
                replaceFragment(fragment);
                setSessionData();
                getImageUrlDetails();
            } else if (requestCode == utils.gotoMypurchase || unmaskedRequestCode == utils.gotoMypurchase) {
                openMyPurchases();
                replaceFragment(fragment);
                setSessionData();
                getImageUrlDetails();
            } else if (requestCode == utils.gotoMycommunity || unmaskedRequestCode == utils.gotoMycommunity) {
                openMyCommunity();
                replaceFragment(fragment);
                setSessionData();
                getImageUrlDetails();
            } else if (requestCode == utils.gotoMyProfile || unmaskedRequestCode == utils.gotoMyProfile) {
                openMyProfile();
                replaceFragment(fragment);
                setSessionData();
                getImageUrlDetails();
            } else if (requestCode == utils.gotoIbosupport || unmaskedRequestCode == utils.gotoIbosupport) {
                openIBOsupport();
                replaceFragment(fragment);
                setSessionData();
                getImageUrlDetails();
            } else if (unmaskedRequestCode == utils.gotoMyDashboard
                    || unmaskedRequestCode == utils.gotoMyBusiness
                    || unmaskedRequestCode == utils.gotoDownline
                    || unmaskedRequestCode == utils.gotoPromos
                    || unmaskedRequestCode == utils.gotoIncome
                    || unmaskedRequestCode == utils.gotoDwarka) {
                setDataUI();
            }
        }
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private void loadHome() {
        DashboardFragment myFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.nav_dashboard));
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            navigationView.setCheckedItem(R.id.nav_home);
        } else {
            // openHome();
            //  getSupportFragmentManager().popBackStack();
            replaceFragment(openHome());
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("Back==", "Home==");
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            spotLightCall();
            drawer.closeDrawer(GravityCompat.START);

        } else {
            if (navigationView.getMenu().findItem(R.id.nav_projects).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_my_sales).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_my_income).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_downloads).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_holidays).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_ibolist).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_my_downline).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_my_purchase).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_account_summary).isChecked() ||
                    navigationView.getMenu().findItem(R.id.registation).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_events).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_profile).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_privacy).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_query).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_my_promotions).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_report).isChecked() ||
                    navigationView.getMenu().findItem(R.id.nav_offers).isChecked()
            ) {
                // navigationView.setCheckedItem(R.id.nav_home);
                // setname();
                // getSupportActionBar().setTitle("Hi, " + session.getUserName());
                // fragment = new DashboardFragment();
                spotLightCall();
                replaceFragment(openHome());
            } else {
                if (doubleBackToExitPressedOnce) {
                    // super.onBackPressed();
                    moveTaskToBack(true);
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setName() {
        String userName = session.getUserName();
        if (session.getLogin()) {
            if (userName == null || userName.equals("")) {
                getSupportActionBar().setTitle("Hi, Guest");
            } else {
                getSupportActionBar().setTitle("Hi, " + userName);
            }
        } else {
            getSupportActionBar().setTitle("Hi, Guest");
        }
    }

    private void spotLightCall() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment f : fragmentList) {
            if (f instanceof DownloadsFragment) {
                ((DownloadsFragment) f).hideSpotLight();
            } else if (f instanceof IBOListFragment) {
                ((IBOListFragment) f).hideSpotLightTree();
            }
        }
    }

    @Override
    protected void onResume() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        utilsVersion.checkVersionFirsTime(this);
        utils.checkExpireUser(mAPIInterface, this, session);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("INFO", "call for onStop");
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }

    private void getImageUrlDetails() {
        Log.d("call====", "getImageUrlDetails :: getImageUrlDetails");
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<MyAccount> requestCall = mAPIInterface.getMyAccountDetails();
            Log.d("my details", "call account details");
            requestCall.enqueue(new Callback<MyAccount>() {
                @Override
                public void onResponse(Call<MyAccount> call, Response<MyAccount> response) {
                    Log.d("call====", "getImageUrlDetails :: getImageUrlDetails " + response);
                    if (response.isSuccessful()) {
                        Object loadpath = null;
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                Intent intent = new Intent();
                                String rnk = response.body().getData().getBasicInfo().getRank();
                                session.setRole(rnk);
                                intent.setAction(SUCCESSDATA);
                                intent.putExtra("rank", rnk);
                                sendBroadcast(intent);
                                setData(response.body().getData().getBasicInfo());

                                if (response.body().getData().getBasicInfo().getProfilePicFileName().equals("")) {
                                    if (response.body().getData().getBasicInfo().getSex() != null) {
                                        if (response.body().getData().getBasicInfo().getSex().equalsIgnoreCase("Male")) {
                                            loadpath = R.drawable.male;
                                            // Picasso.with(DashboardActivity.this).load(R.drawable.male).placeholder(R.drawable.ic_user).into(imgRank);
                                        } else {
                                            loadpath = R.drawable.female;
                                            // Picasso.with(DashboardActivity.this).load(R.drawable.female).placeholder(R.drawable.ic_user).into(imgRank);
                                        }
                                    } else {
                                        loadpath = R.drawable.male;
                                        // Picasso.with(DashboardActivity.this).load(R.drawable.male).placeholder(R.drawable.ic_user).into(imgRank);
                                    }
                                } else {
                                    Uri uri = Uri.parse(response.body().getData().getBasicInfo().getProfilePic());
                                    Log.e(getClass().getSimpleName(), " Path " + uri);
                                    loadpath = uri;
                                    // Picasso.with(DashboardActivity.this).load(uri).placeholder(R.drawable.ic_user).into(imgRank);
                                }
                            }
                        } else {
                            loadpath = R.drawable.male;
                            // Picasso.with(DashboardActivity.this).load(R.drawable.male).placeholder(R.drawable.ic_user).into(imgRank);
                        }

                        Glide.with(getApplicationContext())
                                .load(loadpath)//.fitCenter()
                                // .diskCacheStrategy(DiskCacheStrategy.ALL)
                                // .placeholder(R.drawable.ic_user)
                                .into(imgRank);

                    }
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<MyAccount> call, Throwable t) {
                    //TODO : Failure Response Display Error Developer Mode.
                    Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                    hideFullDialog();
                }
            });
        } else {
            Glide.with(DashboardActivity.this)
                    .load(R.drawable.male)
                    // .fitCenter()
                    // .diskCacheStrategy(DiskCacheStrategy.ALL)
                    // .placeholder(R.drawable.ic_user)
                    .into(imgRank);
            //Picasso.with(DashboardActivity.this).load(R.drawable.male).into(imgRank);
            hideFullDialog();
        }
    }

    private void inAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                Log.e("AVAILABLE_VERSION_CODE", appUpdateInfo.availableVersionCode() + "");
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Request the update.

                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                AppUpdateType.IMMEDIATE,
                                // The current activity making the update request.
                                DashboardActivity.this,
                                // Include a request code to later monitor this update request.
                                UPDATE_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException ignored) {

                    }
                }
            }
        });
        appUpdateManager.registerListener(installStateUpdatedListener);
    }

    //lambda operation used for below listener
    InstallStateUpdatedListener installStateUpdatedListener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        } else
            Log.e("UPDATE", "Not downloaded yet");
    };

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                "Update almost finished!",
                Snackbar.LENGTH_INDEFINITE);
        //lambda operation used for below action
        snackbar.setAction(this.getString(R.string.update_finish), view ->
                appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    Dialog dialog1, dialogSPC;
    ImageView imgviewSpc;

    private void initConditionDialog() {

        LayoutInflater mInflater2 = (LayoutInflater)
                DashboardActivity.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        dialogSPC = new Dialog(DashboardActivity.this);
        dialogSPC.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSPC.setCancelable(false);
        dialogSPC.setCanceledOnTouchOutside(false);
        View convertView1 = mInflater2.inflate(R.layout.layout_spcondition, null);
        dialogSPC.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSPC.setContentView(convertView1);
        dialogSPC.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSPC.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        // MyTextView policy = (MyTextView) convertView1.findViewById(R.id.tv_policy_agreement);
        // ImageView imgPolicyClose = (ImageView) convertView1.findViewById(R.id.img_policy_close);
        Button btnAgreement = (Button) convertView1.findViewById(R.id.btn_agreement);
        imgviewSpc = convertView1.findViewById(R.id.img_spc);


        btnAgreement.setTypeface(typeface);
        // policy.setText(Html.fromHtml(getString(R.string.disclaimer_agreement)));
        btnAgreement.setOnClickListener(v -> {
            SweetAlertDialog loading = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            loading.setCancelable(false);
            loading.setConfirmText("OK");
            loading.setCancelText("Cancel");

            loading.setOnShowListener(dialog -> {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                MyButton btnCancel = alertDialog.findViewById(R.id.cancel_button);

                //textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                //textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                textTitle.setText("Agree");
                btnConfirm.setText("Ok");
                btnCancel.setText("Cancel");
                textContent.setText("Are you sure you want to submit?");

                textContent.setGravity(Gravity.NO_GRAVITY);
                btnCancel.setOnClickListener(v1 -> {
                    loading.cancel();
                });
                btnConfirm.setOnClickListener(v1 -> {
                    btnConfirm.setEnabled(false);
                    btnCancel.setEnabled(false);
                    Call<CreateTicketModel> wsCallingPostSop = mAPIInterface.PostSpConditions();
                    wsCallingPostSop.enqueue(new Callback<CreateTicketModel>() {
                        @Override
                        public void onResponse(Call<CreateTicketModel> call, Response<CreateTicketModel> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().getStatusCode() == 1) {
                                    session.setLoader(false);
                                    loading.dismiss();
                                    dialogSPC.dismiss();
                                    Log.i("INFO", "RankAndVolumes code: " + response.message());
                                    // Toast.makeText(DashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    btnConfirm.setEnabled(true);
                                    Toast.makeText(DashboardActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                btnConfirm.setEnabled(true);
                                Toast.makeText(DashboardActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }
                            btnCancel.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<CreateTicketModel> call, Throwable t) {
                            btnConfirm.setEnabled(true);
                            btnCancel.setEnabled(true);
                            Toast.makeText(DashboardActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });

                });
            });
            loading.show();

        });

    }

    private void disclaimerPolicy() {

///SalesAdapter.ViewHolder2 holder2 = null;

        LayoutInflater mInflater2 = (LayoutInflater)
                DashboardActivity.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        dialog1 = new Dialog(DashboardActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        View convertView1 = mInflater2.inflate(R.layout.term_condition_agreement, null);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);
        dialog1.setContentView(convertView1);
        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        // MyTextView policy = (MyTextView) convertView1.findViewById(R.id.tv_policy_agreement);
        // ImageView imgPolicyClose = (ImageView) convertView1.findViewById(R.id.img_policy_close);
        Button btnAgreement = (Button) convertView1.findViewById(R.id.btn_agreement);
        btnAgreement.setTypeface(typeface);
        // policy.setText(Html.fromHtml(getString(R.string.disclaimer_agreement)));
        btnAgreement.setOnClickListener(v -> {
            Call<JSONObject> wsCallingPostSop = mAPIInterface.PostSop();
            wsCallingPostSop.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()) {
                        Log.i("INFO", "RankAndVolumes code: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                }
            });
            dialog1.dismiss();
        });
        if (!dialog1.isShowing())
            dialog1.show();
    }


    private void setData(BasicInfo data) {
        int rankid = 0;
        try {
            rankid = data.getRankId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Log.d("SOP::", data.getShowSOP() + " == ");
            if (data.getShowSOP()) {
                disclaimerPolicy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (showSPC) {
            try {
                if (data.getShowSPC() && rankid <= starplatinumID) {
                    if (!dialogSPC.isShowing()) {
                        Glide.with(DashboardActivity.this)
                                .load(data.getSPCondition()).fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                //.placeholder(getRandomDrawbleColor())
                                .into(imgviewSpc);
                        dialogSPC.show();
                    }
                } else
                    session.setLoader(false);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            session.setLoader(false);
        navigationView = findViewById(R.id.nav_view_dashboard);
        Menu nav_Menu = navigationView.getMenu();
        // if (showSPC)
        nav_Menu.findItem(R.id.nav_report).setVisible(rankid <= starplatinumID);
    /*    else
            nav_Menu.findItem(R.id.nav_report).setVisible(true);*/


        iborank.setText("KYC");
        if (data.getKYC()) {
            imgKYCStatus.setImageResource(R.drawable.ic_tick);
        } else {
            imgKYCStatus.setImageDrawable(ContextCompat.getDrawable(DashboardActivity.this, R.drawable.ic_criss_cross));
        }
        imgKYCStatus.setVisibility(View.VISIBLE);
        iborank.setVisibility(View.VISIBLE);
        rank = session.getUserName() + " (" + data.getRank() + ")";
        if (data.getShowRegistration()) {
            showItem();
        } else {
            hideItem();
        }
    }

    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view_dashboard);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.registation).setVisible(false);
    }

    private void showItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view_dashboard);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.registation).setVisible(true);
    }

    private void getOfferDetails() {
        if (Utils.isNetworkAvailable(this)) {
            mAPIInterface = APIClient.getClient(getApplicationContext()).create(APIInterface.class);
            Call<EcomOfferModel> wsCallingEvents = mAPIInterface.getOfferListEcom();
            wsCallingEvents.enqueue(new Callback<EcomOfferModel>() {
                @Override
                public void onResponse(Call<EcomOfferModel> call, Response<EcomOfferModel> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            EcomOfferModel ecomOfferModel = response.body();
                            if (ecomOfferModel.getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (ecomOfferModel.getStatuscode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                ArrayList<EcomOfferModelList> ecomOfferModels = new ArrayList<>();
                                ecomOfferModels.addAll(ecomOfferModel.getData());
                                if (ecomOfferModels.size() > 0) {
                                    showTimerDialog(ecomOfferModels.get(0));
                                }
                                Log.e("ORDERListAPI", "ORDERListAPI: " + new Gson().toJson(response.body()));
                            }
                        } else {
                        }
                    }
                }

                @Override
                public void onFailure(Call<EcomOfferModel> call, Throwable t) {
                }
            });
        } else {
        }
    }



    private void showTimerDialog(EcomOfferModelList modeldata) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_offer_timer, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        AspectRatioImageView imageView = dialogView.findViewById(R.id.customView);
        CountdownView editText = dialogView.findViewById(R.id.easyCountDownTextview);
        LinearLayout layTimer = dialogView.findViewById(R.id.layTimer);
        MyButton myClose = dialogView.findViewById(R.id.confirm_button);
      /*  MyTextViewEcom txtMessage1 = dialogView.findViewById(R.id.textmessage1);
        MyTextViewEcom txtMessage2 = dialogView.findViewById(R.id.textmessage2);*/

        Glide.with(getApplicationContext())
                .load(modeldata.getOfferImage())
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        long startTimestamp = modeldata.getOfferStartDate() * 1000;// 1624342038000L;//
        long endTimesatamp = modeldata.getOfferEndDate() * 1000;// //
        long currenttimestamp = System.currentTimeMillis();
        layTimer.setVisibility(modeldata.getDelete() ? View.GONE : View.VISIBLE);
        AlertDialog alertDialog = dialogBuilder.create();
        myClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        Log.d("Timestamp", startTimestamp + " : " + currenttimestamp + " : " + endTimesatamp);
        if (!modeldata.getDelete() && startTimestamp <= currenttimestamp && currenttimestamp <= endTimesatamp) {
            long diff = endTimesatamp - currenttimestamp;
            Log.d("Timestamp", startTimestamp + " : " + currenttimestamp + " : " + endTimesatamp + " : " + diff);
            editText.start(diff);
            editText.setOnCountdownEndListener(cv -> {
                alertDialog.dismiss();
                //Toast.makeText(this, "Offer complete", Toast.LENGTH_SHORT).show();
            });
            alertDialog.show();
        }/*else{
            editText.setVisibility(View.GONE);
           // txtMessage2.setText("Time over");
        }*/
    }

    private void markWalletDelete() {
        SharedPreferences SharedPrefEwallet;
        SharedPrefEwallet = getSharedPreferences(PrefUtils.prefEwalletTransaction, 0);
        String ewalletAmount = SharedPrefEwallet.getString("PREFwallet", "");
        String orderid = SharedPrefEwallet.getString("PREFOrderId", "");
        Log.d("transaction::", "send Ewallet Transaction " + ewalletAmount + " : " + orderid);
        if (!TextUtils.isEmpty(orderid) && Integer.parseInt(ewalletAmount) > 0) {
            if (Utils.isNetworkAvailable(this)) {
                mAPIInterface = APIClient.getClient(getApplicationContext()).create(APIInterface.class);
                Call<MarkCartDeleteModel> wsCallingEvents = mAPIInterface.deleteEwallet(orderid, session.getIboKeyId(), ewalletAmount);
                wsCallingEvents.enqueue(new Callback<MarkCartDeleteModel>() {
                    @Override
                    public void onResponse(Call<MarkCartDeleteModel> call, Response<MarkCartDeleteModel> response) {
                        SharedPrefEwallet.edit().clear().apply();
                    }

                    @Override
                    public void onFailure(Call<MarkCartDeleteModel> call, Throwable t) {
                    }
                });
            }
        }
    }

    SharedPreferences SharedPrefTransID;

    private void sendTransaction() {
        SharedPrefTransID = getSharedPreferences(PrefUtils.prefTransaction, 0);
        String transID = SharedPrefTransID.getString("PREFtransId", "0");
        String orderID = SharedPrefTransID.getString("PREFOrderId", "0");
        String approval = SharedPrefTransID.getString("PREFAproval", "0");
        String response = SharedPrefTransID.getString("PREFResponse", "0");
        String status = SharedPrefTransID.getString("PREFStatus", "0");
        String refId = SharedPrefTransID.getString("PREFtxnRef", "0");
        Log.d("transaction::", "sendTransaction " + transID + " : " + orderID + " : " + approval + " : " + response + " : " + status + " : " + refId);
        if (!transID.equals("0")) {
            // call API for submitting transaction
            if (Utils.isNetworkAvailable(this)) {
                callAPITransactionUPI(orderID, transID, approval, response, status, refId);
            }
        }
    }

    private void callAPITransactionUPI(String OrderID, String TxnId, String ApprovalRef, String ResponseCode, String Status, String txnRef) {
        mAPIInterface = APIClientCustomer.getClient(DashboardActivity.this).create(APIInterface.class);
        //Log.d("result::", "call transaction:: ");
        Log.d("transaction::", "callAPITransactionUPI: " + TxnId + " : " + OrderID + " : " + ApprovalRef + " : " + ResponseCode + " : " + Status + " : " + txnRef);
        Call<AddAddressDetail> wsCallingEvents = mAPIInterface.successUPI(OrderID, TxnId, ApprovalRef, ResponseCode, Status, txnRef, "GooglePay");
        wsCallingEvents.enqueue(new Callback<AddAddressDetail>() {
            @Override
            public void onResponse(Call<AddAddressDetail> call, Response<AddAddressDetail> response) {
                try {
                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        SharedPrefTransID.edit().clear().apply();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddAddressDetail> call, Throwable t) {
            }
        });
    }

    private void uploadFile(File myFile, String fname) {
        ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        // Map is used to multipart the file using okhttp3.RequestBody
        // File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
        MultipartBody.Part fbody = MultipartBody.Part.createFormData("pdf", fname, requestBody);
        // Parsing any Media type file
        //RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        //MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("pdf", fname, requestBody);
        // RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiConfigs getResponse = AppConfigs.getRetrofit().create(ApiConfigs.class);
        Call<ServerResponse> wsCallingEvents = getResponse.uploadFile(fbody);//, filename);
        wsCallingEvents.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> serverResponse) {
                Toast.makeText(getApplicationContext(), serverResponse.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }
        });

    }
}


class AppConfigs {
    private static String BASE_URL = "http://dhyancommerce.com/dcgt/api/";
    private static OkHttpClient okHttpClient;

    // http://dhyancommerce.com/dcgt/api/api/appUserSubmitTestPDF
    static Retrofit getRetrofit() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.MINUTES)
                .addInterceptor(chain -> {
                    Request.Builder ongoing = chain.request().newBuilder();
                    //ongoing.addHeader("Authorization", session.getAccessToken());
                    return chain.proceed(ongoing.build());
                })
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.MINUTES)
                //  .cache(cache)
                .build();
        return new Retrofit.Builder()
                .baseUrl(AppConfigs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}



interface ApiConfigs {
    @Multipart
    @POST("api/appUserSubmitTestPDF")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file);
}



class ServerResponse {
    // variable name should be same as in the json response from php
    @SerializedName("status")
    public boolean success;
    @SerializedName("responseMessage")
    public String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
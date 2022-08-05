package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.VersionCheck;
import com.nebulacompanies.ibo.sms.SmsBroadcastReceiverLogin;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.AsyncComplex;
import com.nebulacompanies.ibo.util.AsyncResponse;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.NebulaEditText;
import com.nebulacompanies.ibo.view.ShownEdittext;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.PrefUtils.prefSkiplogin;
import static com.nebulacompanies.ibo.util.AvailableSpace.megabytesAvailable;
import static com.nebulacompanies.ibo.util.SetFonts.setFonts;

/**
 * Created by Palak Mehta on 15-Feb-18.
 */

public class LoginSkipActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, AsyncResponse {

    NebulaEditText loginidEditText, edtmid, edtOTP, edtNewPassword, edtUserName, edtConfirmPassword;
    ShownEdittext passwordEditText;
    RadioButton radioBtnEmail, radioBtnSms;
    MyTextView forgotPasswordTextView, kycTextView, tvPrivacyPolicy;
    Button btnLogin, dialogok;
    ImageView imgPhone, imgEmail;
    DownloadManager downloadManager;
    Dialog dialog, dialogOtp;
    AsyncComplex asyncComplex1;
    Session session;
    Button btnSkip;

    private BroadcastReceiver receiverDownloadComplete, receiverNotificationClicked;
    SmsBroadcastReceiverLogin mSmsBroadcastReceiver;
    private APIInterface mAPIInterface;
    private ProgressDialog progressDialog;
    MaterialProgressBar mProgressDialog;
    ByteArrayOutputStream bytearrayoutputstream;
    Typeface typeface;
    private AppUpdateManager appUpdateManager;

    String user_name, password, TokenKey, DeviceId, IMEI1, IMEI2;
    static float availableSpace;
    String FORGOT_PASSWORD_API = null;

    public static final String TAG = "IBOLogin";
    int version_code;
    String version_name;
    int UPDATE_REQUEST_CODE = 0;
    private static final int HIGH_PRIORITY_UPDATE = 5;
    Utils utils = new Utils();
    SharedPreferences settings;
    ImageView imgback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_animation);
        this.setFinishOnTouchOutside(false);
        //android O fix bug orientation
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        appUpdateManager = AppUpdateManagerFactory.create(this);
        mSmsBroadcastReceiver = new SmsBroadcastReceiverLogin();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
        clearDashData();
        checkVersion();
    }

    private void clearDashData() {
        SharedPreferences sharedPrefs = getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        receiverNotificationClicked = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String extraId = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                long[] references = intent.getLongArrayExtra(extraId);
            }
        };
        this.registerReceiver(receiverNotificationClicked, filter);

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        receiverDownloadComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            }
        };
        this.registerReceiver(receiverDownloadComplete, intentFilter);

        bytearrayoutputstream = new ByteArrayOutputStream();

        init();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        availableSpace = megabytesAvailable();

        btnLogin.setEnabled(true);
    }

    void init() {
        settings = getSharedPreferences(PrefUtils.prefMyprefsfile, 0);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        session = new Session(this);
        session.setLogin(false);
        mProgressDialog = (MaterialProgressBar) findViewById(R.id.progresbar);
        btnSkip = findViewById(R.id.btn_skip_main);

        loginidEditText = (NebulaEditText) findViewById(R.id.txtUserName);
        passwordEditText = (ShownEdittext) findViewById(R.id.txtPassword);
        imgback = findViewById(R.id.img_back);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        forgotPasswordTextView = (MyTextView) findViewById(R.id.forgotpassword);
        kycTextView = (MyTextView) findViewById(R.id.kyc);
        tvPrivacyPolicy = (MyTextView) findViewById(R.id.tv_privacy);
        //  btnSkip.setVisibility(showWithoutLogin ? View.VISIBLE : View.GONE);
        btnSkip.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        setFonts(this, btnLogin);

        passwordEditText.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                    || (actionId == EditorInfo.IME_ACTION_DONE)) {
                Log.i(TAG, "Enter pressed");

                getLoginDetails();
            }
            return false;
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginidEditText.setError(null);
                passwordEditText.getEditText().setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        loginidEditText.addTextChangedListener(textWatcher);
        passwordEditText.getEditText().addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(this);
        loginidEditText.setOnFocusChangeListener(this);
        passwordEditText.setOnFocusChangeListener(this);
        forgotPasswordTextView.setOnClickListener(this);
        kycTextView.setOnClickListener(this);

        tvPrivacyPolicy.setOnClickListener(view -> {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Intent intent = new Intent(LoginSkipActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(this);
            }
        });
        imgback.setOnClickListener(v -> onBackPressed());
        btnSkip.setOnClickListener(v -> {
            settings.edit().putBoolean(prefSkiplogin, true).apply();
            openDashboard();
        });
    }

    public float getKYCFormSize() {
        URL url = null;
        URLConnection urlConnection = null;
        try {
            url = new URL("https://nebulacompanies.net/download/kyc.pdf");
            urlConnection = url.openConnection();
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (float) (urlConnection.getContentLength() * (0.000001));
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

                getLoginDetails();
                break;


            case R.id.kyc: {

                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    Intent registation = new Intent(LoginSkipActivity.this, RegistrationActivityWebview.class);
                    registation.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(registation);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                } else {
                    utils.dialogueNoInternet(this);
                }

                break;
            }

            case R.id.forgotpassword:
                //dialog = new Dialog(new ContextThemeWrapper(LoginSkipActivity.this, R.style.Dialog));
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    dialog = new Dialog(LoginSkipActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.setContentView(R.layout.forgot_password);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

                    // This is line that does all the magic
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    dialog.getWindow().setAttributes(lp);

                    edtmid = (NebulaEditText) dialog.findViewById(R.id.edtmid);
                    edtmid.setHint("Enter Your IBO ID");

                    radioBtnEmail = (RadioButton) dialog.findViewById(R.id.forgot_rd_mail);
                    radioBtnSms = (RadioButton) dialog.findViewById(R.id.forgot_rd_sms);
                    imgPhone = (ImageView) dialog.findViewById(R.id.img_phone);
                    imgEmail = (ImageView) dialog.findViewById(R.id.img_email);
                    dialogok = (Button) dialog.findViewById(R.id.btnget);
                    radioBtnEmail.setTypeface(ResourcesCompat.getFont(LoginSkipActivity.this, R.font.montserrat_regular));
                    radioBtnSms.setTypeface(ResourcesCompat.getFont(LoginSkipActivity.this, R.font.montserrat_regular));
                    typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
                    dialogok.setTypeface(typeface);
                    dialogok.setOnClickListener(v1 -> {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v1.getWindowToken(), 0);

                        if (Utils.isNetworkAvailable(getApplicationContext())) {
                            if (edtmid.getText().toString().length() == 0) {
                                edtmid.setError("Enter Your IBO ID");
                            } else {
                                boolean isChecked = radioBtnSms.isChecked();


                                forgotIBOPassword(edtmid.getText().toString());
                            }

                        } else {
                            noInternetDailogue();
                        }
                    });
                    dialog.show();
                } else {
                    utils.dialogueNoInternet(this);
                }
                break;
        }
    }

    void getLoginDetails() {
        btnLogin.setEnabled(false);
        btnSkip.setEnabled(false);
        loginidEditText.clearFocus();
        passwordEditText.clearFocus();
        btnLogin.requestFocus();

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginidEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);

        user_name = loginidEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if (user_name.length() == 0 && password.length() == 0) {
            btnLogin.setEnabled(true);
            btnSkip.setEnabled(true);
            Toast toast = Toast.makeText(LoginSkipActivity.this, R.string.enter_login_id_password, Toast.LENGTH_SHORT);
            toast.show();
        } else if (user_name.length() == 0) {
            btnLogin.setEnabled(true);
            btnSkip.setEnabled(true);
            Toast toast = Toast.makeText(LoginSkipActivity.this, R.string.enter_login_id, Toast.LENGTH_SHORT);
            toast.show();
        } else if (password.length() == 0) {
            btnLogin.setEnabled(true);
            btnSkip.setEnabled(true);
            Toast toast = Toast.makeText(LoginSkipActivity.this, R.string.enter_login_password, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            getToken(user_name, password);
        }
    }

    void getToken(final String userName, final String password) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<JsonObject> wsCallingLogin = mAPIInterface.getToken(userName, password, "password");
            wsCallingLogin.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            try {
                                trimCache(LoginSkipActivity.this);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO response", "response: " + responseString);
                                Log.i("TokenError", "TokenError: " + responseString);
                                JSONObject jsonObject = new JSONObject(responseString);

                                String token = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_TOKEN_TYPE) + " "
                                        + jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_ACCESS_TOKEN);
                                String refreshToken = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_TOKEN_TYPE) + " "
                                        + jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_REFRESH_TOKEN);
                                String role = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_ROLE);
                                String displayName = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_DISPLAYNAME);
                                String ibo_key_id = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID);
                                String ibo_ref_id = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_IBO_REF_ID);

                                if (role.equals("IBO")) {

                                    session.setAccessToken(token);
                                    session.setRefreshToken(refreshToken);
                                    session.setUserCredential(userName, password);
                                    session.setLoginID(userName);
                                    session.setUserName(displayName);
                                    session.setIboKeyId(ibo_key_id);
                                    session.setRefId(ibo_ref_id);
                                    //Close Due to 10:00 PM to 12:25 AM Issue
                                    //session.setIboDate(ibo_key_date);
                                    Log.i("INFO TOKEN", "TOKEN " + session.getAccessToken());
                                    Log.i("INFO REFRESH TOKEN", "REFRESH TOKEN " + session.getRefreshToken());
                                    Log.i("INFO IboKeyId", "IboKeyId " + session.getIboKeyId());
                                    btnLogin.setEnabled(false);
                                    btnSkip.setEnabled(false);
                                    session.setLogin(true);

                                    firebaseTokenGenerated(ibo_key_id);
                                    openDashboard();

                                } else {
                                    mProgressDialog.setVisibility(View.GONE);
                                    Toast.makeText(LoginSkipActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (response.code() == 400) {
                            mProgressDialog.setVisibility(View.GONE);
                            warningDailogueUserName();
                        } else {
                            mProgressDialog.setVisibility(View.GONE);
                            warningDailogueTry();
                        }
                    }
                    btnLogin.setEnabled(true);
                    btnSkip.setEnabled(true);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    btnLogin.setEnabled(true);
                    btnSkip.setEnabled(true);
                    mProgressDialog.setVisibility(View.GONE);
                    Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    Log.i("TokenError", "TokenError: " + t);
                }
            });
        } else {
            btnLogin.setEnabled(true);
            btnSkip.setEnabled(true);
            AppUtils.displayNetworkErrorMessage(this);
        }
    }

    private void openDashboard() {
       /* Intent i = new Intent(LoginSkipActivity.this, DashboardActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();*/

        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        // returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }


    @SuppressLint({"NewApi", "MissingPermission"})
    private void firebaseTokenGenerated(String user) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                TokenKey = FirebaseInstanceId.getInstance().getToken();
                DeviceId = Build.SERIAL;
                IMEI1 = tManager.getDeviceId(0);
                IMEI2 = tManager.getDeviceId(1);
                Config.REGISTERED_TOKEN = FirebaseInstanceId.getInstance().getToken();

                GetNotificationToken(TokenKey, DeviceId, IMEI1, "0", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetNotificationToken(String tokenKey, String deviceId, String imei1, String imei2, String User) {
        Call<JsonObject> wsCallingToken = mAPIInterface.postTokenKey(tokenKey, deviceId, imei1, imei2, User);
        wsCallingToken.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.e("TOKEN LOGIN", "TOKEN LOGIN:-" + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TOKEN LOGIN", "TOKEN LOGIN ERROR : " + t.getMessage());
            }
        });
    }


    private void checkVersion() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version_code = info.versionCode;
        version_name = info.versionName;
        Log.i("Info", "version_number : " + version_code);
        Log.i("Info", "version_name : " + version_name);

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            try {
                Call<VersionCheck> wsCallingversionChecker = mAPIInterface.getVersion();
                wsCallingversionChecker.enqueue(new Callback<VersionCheck>() {
                    @Override
                    public void onResponse(Call<VersionCheck> call, Response<VersionCheck> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                try {
                                    String latestVersions = response.body().getData();
                                    if (latestVersions != null && !latestVersions.equals(version_name)) {
                                        System.out.println("The two strings are not the same.");
                                        Log.e("Version Success", "Version Success" + new Gson().toJson(response.body()));

                                        @SuppressLint("RestrictedApi") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(LoginSkipActivity.this, R.style.Update_Dialog));
                                       /* builder.setPositiveButton(R.string.update_now, (dialog, which) -> {
                                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        });
                                        builder.setNegativeButton(R.string.update_later, (dialog, which) -> dialog.dismiss());
                                       */ LayoutInflater inflater = getLayoutInflater();
                                        View dialoglayout = inflater.inflate(R.layout.app_update_dialog, null);
                                        //TextView txt = (TextView) dialoglayout.findViewById(R.id.showtext);
                                        MyButton btnUpdate = (MyButton) dialoglayout.findViewById(R.id.btn_update);

                                        btnUpdate.setOnClickListener(view -> {
                                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        });

                                        builder.setView(dialoglayout);
                                        builder.setCancelable(false);
                                        if (!isFinishing() && builder != null) {
                                            builder.show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VersionCheck> call, Throwable t) {
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void forgotIBOPassword(String IBOId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<JsonObject> wsCallingPassword = mAPIInterface.getIBOForgotPassword(IBOId);
            wsCallingPassword.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    mProgressDialog.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO", "response: " + responseString);

                                JSONObject jsonObject = new JSONObject(responseString);
                                String message = jsonObject.get("Message").toString();
                                String data = jsonObject.get("Data").toString();

                                if (data.trim().equals(null) || data.trim().equals("") || data.trim().equals("null")) {
                                    radioBtnSms.setVisibility(View.GONE);
                                    radioBtnEmail.setVisibility(View.GONE);
                                    imgEmail.setVisibility(View.GONE);
                                    imgPhone.setVisibility(View.GONE);
                                    Toast.makeText(LoginSkipActivity.this, " " + message, Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONObject jsonObjectData = new JSONObject(data);
                                    String email = jsonObjectData.getString("Email");
                                    String mobile = jsonObjectData.getString("Mobile");
                                    Log.d("Email: ", email);
                                    Log.d("Mobile: ", mobile);

                                    if (email.equals("null") || email.equals("")) {
                                        radioBtnEmail.setVisibility(View.GONE);
                                        imgEmail.setVisibility(View.GONE);
                                        radioBtnSms.setVisibility(View.VISIBLE);
                                        imgPhone.setVisibility(View.VISIBLE);

                                        radioBtnSms.setSelected(true);
                                        radioBtnSms.setText(mobile);

                                        if (radioBtnSms.isChecked()) {
                                            forgotIBOPasswordOTP(Objects.requireNonNull(edtmid.getText()).toString());
                                        }
                                    } else if (mobile.equals("null") || mobile.equals("")) {
                                        radioBtnSms.setVisibility(View.GONE);
                                        imgPhone.setVisibility(View.GONE);
                                        radioBtnEmail.setVisibility(View.VISIBLE);
                                        imgEmail.setVisibility(View.VISIBLE);
                                        radioBtnEmail.setSelected(true);
                                        radioBtnEmail.setText(email);
                                        boolean isChecked = radioBtnEmail.isChecked();
                                        radioBtnSms.setChecked(false);
                                        radioBtnEmail.setChecked(true);

                                        if (isChecked) {
                                            forgotPassword(edtmid.getText().toString());

                                        }

                                    } else if (email != null && !email.isEmpty() ||
                                            mobile != null && !mobile.isEmpty()) {
                                        //opctxt.setVisibility(View.VISIBLE);
                                        imgPhone.setVisibility(View.VISIBLE);
                                        imgEmail.setVisibility(View.VISIBLE);
                                        radioBtnEmail.setVisibility(View.VISIBLE);
                                        radioBtnSms.setVisibility(View.VISIBLE);
                                        radioBtnEmail.setText(email);
                                        radioBtnSms.setText(mobile);
                                        radioBtnEmail.setOnClickListener(view -> {
                                            if (!radioBtnEmail.isChecked()) {
                                                radioBtnEmail.setChecked(false);
                                            } else {
                                                radioBtnSms.setChecked(false);
                                                radioBtnEmail.setChecked(true);
                                                String emailSend = edtmid.getText().toString();
                                                dialogok.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        forgotPassword(edtmid.getText().toString());
                                                    }
                                                });
                                            }
                                        });

                                        radioBtnSms.setOnClickListener(view -> {
                                            if (!radioBtnSms.isChecked()) {
                                                radioBtnSms.setChecked(false);
                                            } else {
                                                radioBtnEmail.setChecked(false);
                                                radioBtnSms.setChecked(true);
                                                dialogok.setOnClickListener(view1 -> forgotIBOPasswordOTP(edtmid.getText().toString()));
                                            }
                                        });
                                    } else if (email.equals("null") || email.equals("") || email.equals(null) ||
                                            mobile.equals("null") || mobile.equals("") || mobile.equals(null)) {
                                        imgPhone.setVisibility(View.GONE);
                                        imgEmail.setVisibility(View.GONE);
                                        radioBtnSms.setVisibility(View.GONE);
                                        radioBtnEmail.setVisibility(View.GONE);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginSkipActivity.this, "Please enter correct IBO ID.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(this);
        }
    }

    // Send OTP on Mobile Number
    private void forgotIBOPasswordOTP(final String IBOId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<JsonObject> wsCallingPassword = mAPIInterface.getIBOForgotPasswordOTP(IBOId);
            wsCallingPassword.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    mProgressDialog.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO", "response OTP send: " + responseString);

                                JSONObject jsonObject = new JSONObject(responseString);
                                String message = jsonObject.get("Message").toString();
                                String data = jsonObject.get("Data").toString();

                                if (data.trim().equals(null) || data.trim().equals("") || data.trim().equals("null")) {

                                    Toast.makeText(LoginSkipActivity.this, " " + message, Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONObject jsonObjectData = new JSONObject(data);
                                    String id = jsonObjectData.getString("Id");
                                    String email = jsonObjectData.getString("Email");
                                    String mobile = jsonObjectData.getString("Mobile");
                                    String code = jsonObjectData.getString("Code");
                                    Log.d("Email: ", email);
                                    Log.d("Mobile: ", mobile);
                                    Log.d("ID: ", id);
                                    Log.d("CODE: ", code);
                                    Toast.makeText(LoginSkipActivity.this, " " + message, Toast.LENGTH_SHORT).show();
                                    alertDialogueOTP(mobile, IBOId, code);
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginSkipActivity.this, "Please try again. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(this);
        }
    }

    // Popup for Verify OTP
    @SuppressLint("RestrictedApi")//
    private void alertDialogueOTP(final String mobile, final String IboId, final String code) {
        startSMSListener();
        dialogOtp = new Dialog(LoginSkipActivity.this);
        dialogOtp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOtp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOtp.setContentView(R.layout.forgot_otp_password);
        dialogOtp.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogOtp.getWindow().setAttributes(lp);
        ImageView imgClose = (ImageView) dialogOtp.findViewById(R.id.img_close);
        edtOTP = (NebulaEditText) dialogOtp.findViewById(R.id.edtotp);
        edtUserName = (NebulaEditText) dialogOtp.findViewById(R.id.edt_username);
        edtUserName.setHint("Enter Your IBO ID");
        edtUserName.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtUserName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtNewPassword = (NebulaEditText) dialogOtp.findViewById(R.id.edt_new_password);
        edtConfirmPassword = (NebulaEditText) dialogOtp.findViewById(R.id.edt_confirm_new_password);
        edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        Button dialogreset = (Button) dialogOtp.findViewById(R.id.btnreset);
        Typeface typefaceConfirm = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        dialogreset.setTypeface(typefaceConfirm);
        dialogreset.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getApplicationContext())) {

                if (edtUserName.getText().toString().length() == 0 || edtOTP.getText().toString().length() == 0 ||
                        edtNewPassword.getText().toString().length() == 0 || edtConfirmPassword.getText().toString().length() == 0 ||
                        edtNewPassword.getText().toString().trim().length() <= 5 && edtConfirmPassword.getText().toString().trim().length() <= 5
                        || !edtNewPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())) {

                    checkForm1Validation();
                } else {
                    String otp = edtOTP.getText().toString();
                    String passwordmatch = edtNewPassword.getText().toString();
                    postOTPVerify(mobile, otp, IboId, code, passwordmatch);
                    dialogOtp.dismiss();
                }
            } else {
                noInternetDailogue();
            }
        });
        imgClose.setOnClickListener(v -> dialogOtp.dismiss());
        dialogOtp.show();
    }

    // Send Link by Email to reset password
    void forgotPassword(String userId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<JsonObject> wsCallingPassword = mAPIInterface.getChangedPassword(userId);
            wsCallingPassword.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO", "response: " + responseString);

                                JSONObject jsonObject = new JSONObject(responseString);
                                String message = jsonObject.get("Message").toString();
                                String data = jsonObject.get("Data").toString();
                                Toast.makeText(LoginSkipActivity.this, data, Toast.LENGTH_LONG).show();

                                if ((dialog != null) && dialog.isShowing()) {
                                    dialog.dismiss();
                                    dialog = null;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(this);
        }
    }

    // Verify OTP API
    private void postOTPVerify(String mobile, String otp, String iboId, String code, String password) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<JsonObject> wsCallingPostPassword = mAPIInterface.postIBOForgotPasswordOTPVerify(mobile, otp, iboId, code, password);
            wsCallingPostPassword.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                String responseString = response.body().toString();
                                Log.i("INFO", "response OTP changed: " + responseString);

                                JSONObject jsonObject = new JSONObject(responseString);
                                String message = jsonObject.get("Message").toString();
                                // String data = jsonObject.get("Data").toString();
                                Toast.makeText(LoginSkipActivity.this, " " + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginSkipActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        }
    }

    @Override
    public void processFinish(String output, String Tag) {
        if (output == null) {
            output = "THERE WAS AN ERROR";
        }

        String response = output.toString();
        Log.i("INFO", "response :" + response);

        String message = "";
        if (Tag.equals("Forgot_Password")) {
            try {
                try {
                    JSONObject object = new JSONObject(response);
                    message = object.get("Message").toString();
                    Log.i("INFO", "Forgot_Password message : " + message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(LoginSkipActivity.this, message, Toast.LENGTH_SHORT).show();

                if ((dialog != null) && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
    }

    private void forgotPasswordAPI(String ID) {
        FORGOT_PASSWORD_API = Config.NEB_API + "/API/Login/ForgotPassword?DistributerID=" + ID;
        asyncComplex1 = new AsyncComplex(this, FORGOT_PASSWORD_API, "Forgot_Password", false);
        asyncComplex1.asyncResponse = this;
        asyncComplex1.execute();
    }

    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(receiverDownloadComplete);
        this.unregisterReceiver(receiverNotificationClicked);

        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    // Check Validation OTP Verify Popup
    public Boolean checkForm1Validation() {

        if (edtUserName.getText().toString().length() == 0) {
            edtUserName.setError("Please enter your IBO ID");
            edtUserName.requestFocus();
            return false;
        } else if (edtOTP.getText().toString().length() == 0) {
            edtOTP.setError("Please enter your OTP");
            edtOTP.requestFocus();
            return false;
        } else if (edtNewPassword.getText().toString().length() == 0) {
            edtNewPassword.setError("Please enter your New Password");
            edtNewPassword.requestFocus();
            return false;
        } else if (edtConfirmPassword.getText().toString().length() == 0) {
            edtConfirmPassword.setError("Please re-write your Confirm New Password");
            edtConfirmPassword.requestFocus();
            return false;
        } else if (edtNewPassword.getText().toString().trim().length() <= 5 && edtConfirmPassword.getText().toString().trim().length() <= 5) {
            Toast toastPassMax = Toast.makeText(LoginSkipActivity.this, "Your Password digit must be at least 6 digits", Toast.LENGTH_SHORT);
            toastPassMax.show();
            return false;
        } else if (!edtNewPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())) {
            Toast toastPass = Toast.makeText(LoginSkipActivity.this, "Your Password doesn't match", Toast.LENGTH_SHORT);
            toastPass.show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void noInternetDailogue() {
        SweetAlertDialog loading = new SweetAlertDialog(LoginSkipActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            textTitle.setText("Error");
            btnConfirm.setText("OK");
            textContent.setText(R.string.error_msg_network);

            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                Intent login = new Intent(LoginSkipActivity.this, LoginSkipActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            });
        });
        loading.show();
    }


    private void warningDailogueUserName() {
        SweetAlertDialog loading = new SweetAlertDialog(LoginSkipActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            textTitle.setText("Error");
            btnConfirm.setText("OK");
            textContent.setText("The username or password is incorrect.");

            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });
        loading.show();
    }

    private void warningDailogueTry() {
        SweetAlertDialog loading = new SweetAlertDialog(LoginSkipActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setOnShowListener((DialogInterface.OnShowListener) dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            textTitle.setText("Error");
            btnConfirm.setText("OK");
            textContent.setText("Please try again.");

            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
            });
        });
        loading.show();
    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(aVoid -> {
            // Toast.makeText(getActivity(), "SMS Retriever starts", Toast.LENGTH_LONG).show();
        });
        mTask.addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show());
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


    private void inAppUpdate() {

        appUpdateManager = AppUpdateManagerFactory.create(this);

        appUpdateManager.registerListener(installStateUpdatedListener);

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.updatePriority() >= HIGH_PRIORITY_UPDATE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, LoginSkipActivity.this, UPDATE_REQUEST_CODE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });
    }

    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (appUpdateManager != null) {
                            appUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Update almost finished!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Let's Go", view -> {
            if (appUpdateManager != null) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.snackbar_install));
        snackbar.show();
    }
}

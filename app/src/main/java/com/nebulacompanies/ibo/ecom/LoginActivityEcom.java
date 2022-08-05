package com.nebulacompanies.ibo.ecom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.fragment.MyTrackorderFragment;
import com.nebulacompanies.ibo.ecom.model.LoginDetailsModel;
import com.nebulacompanies.ibo.ecom.model.LoginModelEcom;
import com.nebulacompanies.ibo.ecom.model.RegisterDetailsModel;
import com.nebulacompanies.ibo.ecom.model.RegisterModelEcom;
import com.nebulacompanies.ibo.ecom.sms.OtpReceivedInterface;
import com.nebulacompanies.ibo.ecom.utils.Config;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.RegistationOTP;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Validations.isValidEmail;
import static com.nebulacompanies.ibo.ecom.utils.Validations.isValidMobile;
import static com.nebulacompanies.ibo.ecom.utils.Validations.isValidPhone;
import static com.nebulacompanies.ibo.ecom.utils.Validations.isValidname;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;

public class LoginActivityEcom extends AppCompatActivity implements TextWatcher, GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {

    ProgressDialog mProgressDialog;
    APIInterface mAPIInterface;
    Typeface typeface;

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;
    RelativeLayout rlRegister, rlLogin;
    LinearLayout lnRegister, lnLogin, lnEnterOtp;
    MyButtonEcom btnLogin, btnRegister, btnGetOTP;
    NebulaEditTextEcom edtRegisterFirstName, edtRegisterLastName, edtRegisterMobile, edtRegisterEmail, edtRegisterOTP,
            edtLoginMobile, edtLoginOTP;
    RadioButton male, female;
    Session session;

    private MyTrackorderFragment.OnFragmentInteractionListener mListener;
    ArrayList<RegisterDetailsModel> registerDetailsModels = new ArrayList<>();
    ArrayList<LoginDetailsModel> loginDetailsModels = new ArrayList<>();

    private String mParam1, mParam2;
    String deviceId, usernameLogin, passwordLogin, OTPLogin;
    int addressCallID, trackOrderCallID;
    private int RESOLVE_HINT = 2;
    //  SmsBroadcastReceiver mSmsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ecom);
        session = new Session(LoginActivityEcom.this);
        /*TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/
        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        Intent loginAddressCall = getIntent();
        if (loginAddressCall != null) {
            addressCallID = loginAddressCall.getIntExtra("ADDRESSACCOUNTCALL", 0);
            trackOrderCallID = loginAddressCall.getIntExtra("TRACKORDERCALL", 0);
        }

        //mAPIInterface = APIClient.getClient(PaymentActivity.this).create(APIInterface.class);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");
        session.setLogin(false);
        mAPIInterface = APIClient.getClient(LoginActivityEcom.this).create(APIInterface.class);

        //  mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        //  mSmsBroadcastReceiver.setOnOtpListeners(LoginActivityEcom.this);
       /* IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);*/

        initUI();
        startSMSListener();
        initOnClick();

        if (male.isChecked()) {
            female.setChecked(false);
            Config.gender = "male";
        }
        if (female.isChecked()) {
            male.setChecked(false);
            Config.gender = "female";
        }

    }

    void initUI() {
        frameLayout = findViewById(R.id.my_cart_frame);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnGetOTP = findViewById(R.id.btn_get_otp);
        lnRegister = findViewById(R.id.ln_register);
        lnLogin = findViewById(R.id.ln_login);
        rlRegister = findViewById(R.id.rl_register);
        rlLogin = findViewById(R.id.rl_login);
        lnEnterOtp = findViewById(R.id.ln_enter_otp);

        edtRegisterFirstName = findViewById(R.id.edt_register_first_name);
        edtRegisterLastName = findViewById(R.id.edt_register_last_name);
        edtRegisterMobile = findViewById(R.id.edt_register_mobile);
        edtRegisterEmail = findViewById(R.id.edt_register_email);
        edtRegisterOTP = findViewById(R.id.edt_register_otp);
        edtLoginMobile = findViewById(R.id.edt_mobile);
        edtLoginOTP = findViewById(R.id.edt_otp);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        typeface = Typeface.createFromAsset(LoginActivityEcom.this.getAssets(), "fonts/ember.ttf");
        male.setTypeface(typeface);
        female.setTypeface(typeface);
        edtRegisterMobile.addTextChangedListener(this);
        edtRegisterOTP.addTextChangedListener(this);
        edtLoginMobile.addTextChangedListener(this);
        //edtLoginOTP.addTextChangedListener(this);
    }

    void initOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnLogin.getText().equals("Get OTP")) {
                    if (edtLoginMobile.getText().toString().length() == 0 ||
                            edtLoginMobile.getText().toString().length() < 10 || !isValidMobile(edtLoginMobile.getText().toString())) {
                        checkValidationLogin();
                    }
                } else if (btnLogin.getText().equals("Login")) {

                    if (!OTPLogin.equals(edtLoginOTP.getText().toString())) {
                        Toast.makeText(LoginActivityEcom.this, R.string.otp_registation, Toast.LENGTH_SHORT).show();
                    } else if (OTPLogin.equals(edtLoginOTP.getText().toString()) &&
                            usernameLogin != null && passwordLogin != null) {
                        getToken(usernameLogin, passwordLogin);
                    }
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!Config.RegistationOTP.equals(edtRegisterOTP.getText().toString())) {
                    Toast.makeText(getActivity(), R.string.otp_registation, Toast.LENGTH_SHORT).show();
                } else if (Config.RegistationOTP.equals(edtRegisterOTP.getText().toString())) {
                    if (edtRegisterFirstName.getText().toString().length() == 0 || !isValidname(edtRegisterFirstName.getText().toString()) ||
                            edtRegisterLastName.getText().toString().length() == 0 || !isValidname(edtRegisterLastName.getText().toString()) ||
                            edtRegisterMobile.getText().toString().length() == 0 ||
                            edtRegisterMobile.getText().toString().length() < 10 || !isValidMobile(edtRegisterMobile.getText().toString()) ||
                            edtRegisterEmail.getText().toString().length() == 0 || !isValidEmail(edtRegisterEmail.getText().toString())) {
                        checkValidation();
                    } else {
                        registerAccount(edtRegisterFirstName.getText().toString(), edtRegisterLastName.getText().toString(),
                                edtRegisterMobile.getText().toString(), edtRegisterEmail.getText().toString(), Config.gender);

                    }
                }*/


                registerAccount(edtRegisterFirstName.getText().toString(), edtRegisterLastName.getText().toString(),
                        edtRegisterMobile.getText().toString(), edtRegisterEmail.getText().toString(), Config.gender);

            }
        });

        lnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlRegister.setVisibility(View.VISIBLE);
                rlLogin.setVisibility(View.GONE);

            }
        });

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGetOTP.setText("Resend OTP");
                lnEnterOtp.setVisibility(View.VISIBLE);
                MoblieGetOtp(edtRegisterMobile.getText().toString());
            }
        });

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (female.isChecked()) {
                    male.setChecked(false);
                    Config.gender = "female";
                }
            }
        });

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (male.isChecked()) {
                    female.setChecked(false);
                    Config.gender = "male";
                }
            }
        });

        lnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlLogin.setVisibility(View.VISIBLE);
                rlRegister.setVisibility(View.GONE);
            }
        });

    }

    private void registerAccount(String firstName, String lastName, String mobile, String email, String gender) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(LoginActivityEcom.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<RegisterModelEcom> wsCallingFullRegistaionDetail = mAPIInterface.registerAccountEcom(firstName, lastName, mobile, email, gender);
            wsCallingFullRegistaionDetail.enqueue(new Callback<RegisterModelEcom>() {
                @Override
                public void onResponse(Call<RegisterModelEcom> call, Response<RegisterModelEcom> response) {
                    registerDetailsModels.clear();
                    if (response.isSuccessful()) {
                        Log.d("REGISTERDATA 0", "REGISTERDATA 0" + response.body().getMessage());

                        if (mProgressDialog1.isShowing() && mProgressDialog1 != null) {
                            mProgressDialog1.dismiss();
                        }
                        if (response.code() == 200) {
                            Log.d("REGISTERDATA 1", "REGISTERDATA 1" + response.body().getMessage());
                            if (response.body().getStatusCode() == Const.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("REGISTERDATA 2", "REGISTERDATA 2" + response.body().getMessage());
                                //registerDetailsModels.addAll(response.body().getData().);
                                SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.SUCCESS_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
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
                                        alertDialog.setCancelable(false);
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loading.dismiss();
                                                //finish();
                                                String username = response.body().getData().getUsername();
                                                String password = response.body().getData().getPassword();

                                                getToken(username, password);
                                            }
                                        });
                                    }
                                });

                                loading.show();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {

                                SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.ERROR_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
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
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        alertDialog.setCancelable(false);
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                edtRegisterFirstName.getText().clear();
                                                edtRegisterLastName.getText().clear();
                                                edtRegisterEmail.getText().clear();
                                                edtRegisterMobile.getText().clear();

                                                loading.dismiss();

                                            }
                                        });
                                    }
                                });

                                loading.show();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RegisterModelEcom> call, Throwable t) {
                    Log.d("REGISTERDATA ERROR", "REGISTERDATA ERROR" + t.getMessage());

                    mProgressDialog1.dismiss();
                }
            });
        } else {
            Toast.makeText(LoginActivityEcom.this, R.string.error_msg_network, Toast.LENGTH_SHORT).show();
        }
    }


    private void loginAccount(String mobile) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(LoginActivityEcom.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<LoginModelEcom> wsCallingFullRegistaionDetail = mAPIInterface.loginAccountEcom(mobile);
            wsCallingFullRegistaionDetail.enqueue(new Callback<LoginModelEcom>() {
                @Override
                public void onResponse(Call<LoginModelEcom> call, Response<LoginModelEcom> response) {
                    registerDetailsModels.clear();
                    if (response.isSuccessful()) {
                        Log.d("REGISTERDATA 0", "REGISTERDATA 0" + response.body().getMessage());

                        if (mProgressDialog1.isShowing() && mProgressDialog1 != null) {
                            mProgressDialog1.dismiss();
                        }
                        if (response.code() == 200) {
                            Log.d("REGISTERDATA 1", "REGISTERDATA 1" + response.body().getMessage());
                            if (response.body().getStatusCode() == Const.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("REGISTERDATA 2", "REGISTERDATA 2" + response.body().getMessage());
                                //registerDetailsModels.addAll(response.body().getData().);
                                SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.SUCCESS_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
                                        SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                        MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                                        MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                                        MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                                        textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                        textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                        // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                        textContent.setTypeface(typeface);
                                        textTitle.setTypeface(typeface);
                                        btnConfirm.setTypeface(typeface);
                                        alertDialog.setCancelable(false);
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loading.dismiss();
                                                btnLogin.setText("Login");
                                                usernameLogin = response.body().getData().getUsername();
                                                passwordLogin = response.body().getData().getPassword();
                                                OTPLogin = response.body().getData().getOTP();
                                               /* if (Config.RegistationOTP.equals(mobile)) {
                                                    String username = response.body().getData().getUsername();
                                                    String password = response.body().getData().getPassword();

                                                    getToken(username, password);
                                                }*/
                                            }
                                        });
                                    }
                                });

                                loading.show();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {

                                SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.ERROR_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
                                        SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                        MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                                        MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                                        MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                                        textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                        textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                        // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                        textContent.setTypeface(typeface);
                                        textTitle.setTypeface(typeface);
                                        btnConfirm.setTypeface(typeface);
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        alertDialog.setCancelable(false);
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                edtRegisterFirstName.getText().clear();
                                                edtRegisterLastName.getText().clear();
                                                edtRegisterEmail.getText().clear();
                                                edtRegisterMobile.getText().clear();

                                                loading.dismiss();

                                            }
                                        });
                                    }
                                });

                                loading.show();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginModelEcom> call, Throwable t) {
                    Log.d("REGISTERDATA ERROR", "REGISTERDATA ERROR" + t.getMessage());
                    if (loginDetailsModels.size() > 0) {


                    } else {
                        SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.ERROR_TYPE);
                        loading.setCancelable(true);
                        loading.setConfirmText("OK");
                        loading.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                                MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                                MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);
                                textTitle.setText("This mobile number is not registered.");
                                btnConfirm.setText("OK");
                                alertDialog.setCancelable(false);
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        edtRegisterFirstName.getText().clear();
                                        edtRegisterLastName.getText().clear();
                                        edtRegisterEmail.getText().clear();
                                        edtRegisterMobile.getText().clear();

                                        loading.dismiss();

                                    }
                                });
                            }
                        });

                        loading.show();
                    }
                    mProgressDialog1.dismiss();
                }
            });
        } else {
            Toast.makeText(LoginActivityEcom.this, R.string.error_msg_network, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable == edtRegisterMobile.getEditableText()) {
            if (edtRegisterMobile.getText().toString().length() > 0 && !isValidPhone(edtRegisterMobile.getText().toString())) {
                btnGetOTP.setVisibility(View.GONE);
                lnEnterOtp.setVisibility(View.GONE);
                edtRegisterOTP.setVisibility(View.GONE);
            } else if (isValidMobile(edtRegisterMobile.getText().toString())) {
                btnGetOTP.setVisibility(View.VISIBLE);
                lnEnterOtp.setVisibility(View.VISIBLE);
                edtRegisterOTP.setVisibility(View.VISIBLE);
                btnGetOTP.setText("Get OTP");
            }
        } else if (editable == edtRegisterOTP.getEditableText()) {
            if (edtRegisterOTP.getText().toString().length() == 6) {
                if (!edtRegisterOTP.getText().toString().equals(Config.RegistationOTP)) {
                    edtRegisterOTP.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close_red), null);
                    btnGetOTP.setVisibility(View.VISIBLE);
                } else {
                    edtRegisterOTP.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_right_green), null);
                    btnGetOTP.setVisibility(View.INVISIBLE);
                    edtRegisterOTP.setEnabled(false);
                    edtRegisterFirstName.setEnabled(true);
                    edtRegisterLastName.setEnabled(true);
                    edtRegisterEmail.setEnabled(true);
                    edtRegisterMobile.setEnabled(false);

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                }
            }
        } else if (edtRegisterOTP.getText().toString().length() <= 0 || edtRegisterOTP.getText().toString().length() < 6) {
            edtRegisterOTP.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close_red), null);
            btnGetOTP.setVisibility(View.VISIBLE);
            edtRegisterOTP.setEnabled(true);
            edtRegisterFirstName.setEnabled(true);
            edtRegisterLastName.setEnabled(true);
            edtRegisterEmail.setEnabled(true);

        }

        if (editable == edtLoginMobile.getEditableText()) {
            if (edtLoginMobile.getText().toString().length() > 0 && !isValidPhone(edtLoginMobile.getText().toString())) {
                edtLoginOTP.setVisibility(View.GONE);
            } else if (isValidMobile(edtLoginMobile.getText().toString())) {
                edtLoginOTP.setVisibility(View.VISIBLE);
                if (!Config.RegistationOTP.equals(edtLoginOTP.getText().toString())) {
                    Toast.makeText(LoginActivityEcom.this, R.string.otp_registation, Toast.LENGTH_SHORT).show();
                } else if (Config.RegistationOTP.equals(edtLoginOTP.getText().toString())) {

                    if (edtLoginMobile.getText().toString().length() == 0 ||
                            edtLoginMobile.getText().toString().length() < 10 || !isValidMobile(edtLoginMobile.getText().toString()) /*||
                            edtLoginOTP.getText().toString().length() == 0 || edtLoginOTP.getText().toString().length() < 6*/) {
                        checkValidationLogin();
                    } else {
                        loginAccount(edtLoginMobile.getText().toString());
                        //MoblieGetOtpLogin(edtLoginMobile.getText().toString());
                    }
                }

            }
        } else if (editable == edtLoginOTP.getEditableText()) {
            if (edtLoginOTP.getText().toString().length() == 6) {
                if (!edtLoginOTP.getText().toString().equals(Config.RegistationOTP)) {
                    edtLoginOTP.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close_red), null);
                    btnGetOTP.setVisibility(View.VISIBLE);
                } else {
                    edtLoginOTP.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_right_green), null);

                    edtLoginOTP.setEnabled(false);
                    edtLoginMobile.setEnabled(false);

                    InputMethodManager inputMethodManager = (InputMethodManager) LoginActivityEcom.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                }
            }
        } /*else if (edtLoginOTP.getText().toString().length() <= 0 || edtLoginOTP.getText().toString().length() < 6) {
            edtLoginOTP.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close_red), null);

            edtLoginOTP.setEnabled(true);
            edtLoginMobile.setEnabled(true);

        }*/

    }


    public Boolean checkValidation() {
        if (edtRegisterFirstName.getText().toString().length() == 0 || !isValidname(edtRegisterFirstName.getText().toString())) {
            edtRegisterFirstName.setError("Error");
            edtRegisterFirstName.requestFocus();
            return false;
        }
        if (!isValidname(edtRegisterLastName.getText().toString())) {
            edtRegisterLastName.setError("Error");
            edtRegisterLastName.requestFocus();
            return false;
        }
        if (edtRegisterEmail.getText().toString().length() == 0 || !isValidEmail(edtRegisterEmail.getText().toString())) {
            edtRegisterEmail.setError("Error");
            edtRegisterEmail.requestFocus();
            return false;
        }

        if (edtRegisterMobile.getText().toString().length() == 0 || edtRegisterMobile.getText().toString().length() < 10) {
            edtRegisterMobile.setError("Error");
            edtRegisterMobile.requestFocus();
            return false;
        }

        return true;
    }


    public Boolean checkValidationLogin() {

        if (edtLoginMobile.getText().toString().length() == 0 || edtLoginMobile.getText().toString().length() < 10) {
            edtLoginMobile.setError("Error");
            edtLoginMobile.requestFocus();
            return false;
        }

        return true;
    }

    void getToken(final String userName, final String password) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(LoginActivityEcom.this, R.style.MyTheme);

            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog_ecom);
            // mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.getWindow().setBackgroundDrawableResource(
                    R.color.transparent);

            Call<JsonObject> wsCallingLogin = mAPIInterface.getToken(userName, password, "password");
            wsCallingLogin.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("data==", "getToken call");
                            try {
                                String responseString = response.body().toString();
                                android.util.Log.i("INFO", "response: " + responseString);

                                JSONObject jsonObject = new JSONObject(responseString);

                                String token = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_TOKEN_TYPE) + " "
                                        + jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_ACCESS_TOKEN);
                                String refreshToken = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_TOKEN_TYPE) + " "
                                        + jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_REFRESH_TOKEN);
                                String role = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_ROLE);
                                String displayName = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_DISPLAYNAME);
                                String ibo_key_id = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID);
                                String ibo_ref_id="";
                                try {
                                     ibo_ref_id = jsonObject.getString(Constants.WEB_SERVICES_PARAM.KEY_IBO_REF_ID);
                                }catch (Exception e){e.printStackTrace();}
                                //Toast.makeText(LoginActivity.this, "Role : " + role, Toast.LENGTH_SHORT).show();
                                Log.d("data==", ibo_ref_id + " --> refid");
                                //session.setRole(role);
                                if (role.equals("EComCustomer")) {
                                    //  Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    session.setAccessToken(token);
                                    session.setRefreshToken(refreshToken);
                                    session.setUserCredential(userName, password);
                                    session.setLoginID(userName);
                                    session.setUserName(displayName);
                                    session.setIboKeyId(ibo_key_id);
                                    session.setRefId(ibo_ref_id);
                                    // session.setIboDate(ibo_key_date);
                                    android.util.Log.i("INFO", "TOKEN " + session.getAccessToken());
                                    android.util.Log.i("INFO", "REFRESH TOKEN " + session.getRefreshToken());

                                    /*Intent i = new Intent(getActivity(), DashboardActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);*/
                                    finish();
                                    /*Intent intent = new Intent(LoginActivityEcom.this, MyOrderDetailsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    btnLogin.setEnabled(false);*/

                                    session.setLogin(true);

                                    // firebaseTokenGenerated(ibo_key_id);
                                    finish();
                                    /*if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                    }*/
                                } else {
                                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                    }
                                    Toast.makeText(LoginActivityEcom.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginActivityEcom.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (response.code() == 400) {
                            mProgressDialog.dismiss();
                            Toast.makeText(LoginActivityEcom.this, "The username or password is incorrect.", Toast.LENGTH_SHORT).show();
                            //warningDailogueUserName();
                        } else {
                            mProgressDialog.dismiss();
                            // warningDailogueTry();
                            Toast.makeText(LoginActivityEcom.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivityEcom.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(LoginActivityEcom.this);
        }
    }


    private void MoblieGetOtp(String MobileNumber) {
       /* final ProgressDialog mProgressDialog = new ProgressDialog(RegistationFullActivity.this, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
*/

        Call<RegistationOTP> wsCallingRegistationOtp = mAPIInterface.getRegistationOtp(MobileNumber);
        wsCallingRegistationOtp.enqueue(new Callback<RegistationOTP>() {
            @Override
            public void onResponse(Call<RegistationOTP> call, Response<RegistationOTP> response) {
              /*  if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }*/
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == Const.REQUEST_STATUS_CODE_SUCCESS) {
                        Config.RegistationOTP = String.valueOf(response.body().getData());
                        lnEnterOtp.setVisibility(View.VISIBLE);
                        edtRegisterOTP.setVisibility(View.VISIBLE);
                        SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.SUCCESS_TYPE);
                        loading.setCancelable(true);
                        loading.setConfirmText("OK");
                        loading.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                //   btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);
                                //textTitle.setText(response.body().getMessage());
                                textTitle.setText("OTP sent to this number.");
                                btnConfirm.setText("OK");
                                alertDialog.setCancelable(false);
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        //  finish();
                                    }
                                });
                            }
                        });

                        loading.show();

                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        lnEnterOtp.setVisibility(View.GONE);
                        edtRegisterOTP.setVisibility(View.GONE);
                        btnGetOTP.setVisibility(View.GONE);
                        SweetAlertDialog loading = new SweetAlertDialog(LoginActivityEcom.this, SweetAlertDialog.ERROR_TYPE);
                        loading.setCancelable(true);
                        loading.setConfirmText("OK");
                        loading.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                //   btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
                                alertDialog.setCancelable(false);
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        //  finish();
                                    }
                                });
                            }
                        });

                        loading.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistationOTP> call, Throwable t) {
                // mProgressDialog.dismiss();
            }
        });
    }


    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(LoginActivityEcom.this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(getActivity(), "SMS Retriever starts", Toast.LENGTH_LONG).show();
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivityEcom.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {

    }

    @Override
    public void onOtpTimeout() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    // credential.getId();  <-- will need to process phone number string
                    //  webView.loadUrl("javascript: (function() {document.getElementById('OTP').value= '"+credential.getId()+"';VerifyOTP();}) ();" );
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (addressCallID == 1) {
            Intent addressAccountIntent = new Intent(LoginActivityEcom.this, MainActivity.class);
            addressAccountIntent.putExtra("SELECTEDVALUELOGIN", 3);
            addressAccountIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(addressAccountIntent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            android.util.Log.d("selectedMyAccount1", "selectedMyAccount1: ");
        } else if (trackOrderCallID == 2) {
            Intent trackOderIntent = new Intent(LoginActivityEcom.this, MainActivity.class);
            trackOderIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(trackOderIntent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            super.onBackPressed();
        }
    }
}
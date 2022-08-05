package com.nebulacompanies.ibo.ecom.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.CustomerSupport;
import com.nebulacompanies.ibo.ecom.GenerateCoupon;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.MyAddressAccountActivity;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.MyPromoCodes;
import com.nebulacompanies.ibo.ecom.MyWalletActivity;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.WalletModel;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Utils.showPromoUI;
import static com.nebulacompanies.ibo.ecom.utils.Utils.showRefUI;
import static com.nebulacompanies.ibo.ecom.utils.Utils.showWallet;
import static com.nebulacompanies.ibo.util.Config.shareMainUrl;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MyAccountFragment extends Fragment {

    FrameLayout frameLayout;
    private OnFragmentInteractionListener mListener;
    MyTextViewEcom tvMyAccountLogin,  tvWallet, tvWalletvalue, tvMyAccountMyAddress, tvMyAccountMyProfile, tvMyAccountCustomerSupport, tvMyAccountCouponCode, tvMyAccountPromo, tvRefId;
    Session session;
    public RelativeLayout lnCart, rlSearchView;
    MyTextViewEcom tvToolbarTitle;
    ImageView imgSearch, imgFav, imgCart, imgSearchClose, imgMainBack, imgShare;
    NebulaEditTextEcom editSearch;
    MyTextViewEcom tvCartBadge;
    String deviceId;
    String uniqueID;
    APIInterface mAPIInterface, mAPIInterfaceProfile;
    MyTextViewEcom txtAbout, txtReturn, txtShipping, txtPrivacy, txtContact;
    LinearLayout layWallet;
    MyTextViewEcom tvProfileName, tvProfileEmail, tvProfileMobile, tvProfileGender;
    ImageView imgProfileClose;
    String userName;
    CardView cardView;
    MaterialProgressBar mProgressDialog;
    com.nebulacompanies.ibo.util.Session sessionNormal;
    String reflink;
    Utils utils = new Utils();
    String about="https://shop.nebulacare.in/Home/About";
    String returnpolicy="https://shop.nebulacare.in/Home/ReturnPolicy";
    String shipping="https://shop.nebulacare.in/Home/ShippingPolicy";
    String privacy="https://shop.nebulacare.in/Home/PrivacyPolicy";
    String contactus="https://shop.nebulacare.in/Home/Contact";

    public MyAccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deviceId = android.provider.Settings.Secure.getString(getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = getActivity().getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEIDuniqueID 2", "MDEVICEID uniqueID 2: " + uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID Account", "MDEVICEID Account: " + deviceId);

        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_dashboard);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        lnCart = toolbar.findViewById(R.id.toolbar_settings);

        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        imgFav = toolbar.findViewById(R.id.img_my_fav);
        imgCart = toolbar.findViewById(R.id.img_my_cart);
        imgMainBack = toolbar.findViewById(R.id.img_main_back);
        tvCartBadge = toolbar.findViewById(R.id.cart_badge);
        // tvToolbarTitle.setText("Home");

        imgSearchClose = (ImageView) toolbar.findViewById(R.id.img_search_close);
        editSearch = (NebulaEditTextEcom) toolbar.findViewById(R.id.editMobileNo);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);

        rlSearchView = toolbar.findViewById(R.id.rl_search_view_main);
        rlSearchView.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        session = new Session(getActivity());
        sessionNormal = new com.nebulacompanies.ibo.util.Session(getActivity());
        Log.d("Session Details My Acc", "Session Details1" + session.getLogin());

        mProgressDialog = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        frameLayout = (FrameLayout) view.findViewById(R.id.my_cart_frame);
        tvMyAccountLogin = (MyTextViewEcom) view.findViewById(R.id.tv_my_account_login);
      //  tvMyAccountOrders = (MyTextViewEcom) view.findViewById(R.id.tv_my_account_orders);
        tvMyAccountMyAddress = (MyTextViewEcom) view.findViewById(R.id.tv_my_account_addresses);
        tvMyAccountMyProfile = (MyTextViewEcom) view.findViewById(R.id.tv_my_account_profile);
        txtAbout= (MyTextViewEcom) view.findViewById(R.id.tv_about_us);
        txtReturn= (MyTextViewEcom) view.findViewById(R.id.tv_return);
        txtShipping= (MyTextViewEcom) view.findViewById(R.id.tv_shipping);
        txtPrivacy= (MyTextViewEcom) view.findViewById(R.id.tv_privacy);
        txtContact= (MyTextViewEcom) view.findViewById(R.id.tv_contact_us);
        tvWallet = (MyTextViewEcom) view.findViewById(R.id.tv_my_wallet);
        tvWalletvalue = (MyTextViewEcom) view.findViewById(R.id.tv_my_wallet_value);
        layWallet = view.findViewById(R.id.tv_lay_wallet);
        tvMyAccountCustomerSupport = (MyTextViewEcom) view.findViewById(R.id.tv_my_account_customer_support);
        tvMyAccountCouponCode = view.findViewById(R.id.tv_my_account_coupon_code);
        tvMyAccountPromo = view.findViewById(R.id.tv_my_account_promo);
        cardView = view.findViewById(R.id.card_referral);
        tvRefId = view.findViewById(R.id.tvref_id);
        imgShare = view.findViewById(R.id.img_share);

        imgShare.setOnClickListener(v -> {
            ShareCompat.IntentBuilder.from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle("Nebula Care")
                    .setText(reflink)
                    .startChooser();
        });

        tvMyAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* MyTrackorderFragment myTrackorderFragment = new MyTrackorderFragment();
                 FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                 fragmentTransaction.replace(R.id.my_cart_frame, myTrackorderFragment);
                 fragmentTransaction.addToBackStack(null);
                 fragmentTransaction.commit();*/
                if (tvMyAccountLogin.getText().equals("Login")) {
                    /*Intent addressAccountIntent = new Intent(getActivity(), LoginActivityEcom.class);
                    startActivity(addressAccountIntent);*/
                }
            }
        });
/*

        tvMyAccountOrders.setOnClickListener(v -> {

            if (session.getLogin()) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("SELECTEDACCOUNTORDER", 4);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
*/

        tvMyAccountMyAddress.setOnClickListener(v -> getAddressList());
        txtAbout.setOnClickListener(v -> openLink(about));
        txtReturn.setOnClickListener(v -> openLink(returnpolicy));
        txtShipping.setOnClickListener(v -> openLink(shipping));
        txtPrivacy.setOnClickListener(v -> openLink(privacy));
        txtContact.setOnClickListener(v -> openLink(contactus));
        tvMyAccountCustomerSupport.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                if (session.getLogin()) {
                    Intent intent = new Intent(getActivity(), CustomerSupport.class);
                    intent.putExtra("showorder", 0);
                    intent.putExtra("ticketid", "");
                    startActivity(intent);
                    //getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                } else {
                    Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });

        tvMyAccountCouponCode.setOnClickListener(v -> {
            if (session.getLogin()) {
                Intent intent = new Intent(getActivity(), GenerateCoupon.class);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            } else {
                Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
            }
        });

        tvMyAccountPromo.setVisibility(showPromoUI ? View.VISIBLE : View.GONE);
        tvMyAccountPromo.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), MyPromoCodes.class);
                startActivity(intent);

            } else {
                utils.dialogueNoInternet(getActivity());
            }

        });
        String value = editSearch.getText().toString();
        imgSearchClose.setOnClickListener(view1 -> {
            if (value != null) {
                editSearch.getText().clear();
            }
        });

        imgSearch.setOnClickListener(view14 -> rlSearchView.setVisibility(View.VISIBLE));

        imgCart.setOnClickListener(view13 -> {
            Intent login = new Intent(getActivity(), MyCartActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(login);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        imgMainBack.setOnClickListener(view12 -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        });

        userName = sessionNormal.getUserName();

        if (session.getLogin()) {
            if (userName == null || userName.equals("")) {
                getMyProfileToolBar();
                Log.d("UserName Fill", "UserName Fill " + userName);
            } else {
                Log.d("UserName Empty", "UserName Empty " + userName);
                tvToolbarTitle.setText("Hi, " + userName);
            }
        } else {
            tvToolbarTitle.setText("Hi, Guest");
        }

        tvMyAccountMyProfile.setOnClickListener(v -> {
            if (session.getLogin())
                getMyProfile();
        });

        tvMyAccountCustomerSupport.setVisibility(View.GONE);
        if (showRefUI && session.getLogin()) {
            SharedPreferences SharedPreferencesUserName = getActivity().getSharedPreferences(PrefUtils.prefMrp, 0);
            boolean mrpResult = SharedPreferencesUserName.getBoolean(PrefUtils.prefMrp, false);

            String ref = sessionNormal.getRefId();
            if (TextUtils.isEmpty(ref)) {

                cardView.setVisibility(View.GONE);
                utils.dialogueLogout(getActivity());

            } else {
                reflink = shareMainUrl + "?refid=" + ref;
                // Log.d("refId", "refId==" + ref);
                tvRefId.setText(reflink);
              /*  if (TextUtils.isEmpty(rank)) {
                    tvMyAccountPromo.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                } else {*/
                if (mrpResult) {
                    tvMyAccountPromo.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                } else {

                    cardView.setVisibility(View.VISIBLE);
                }
                // }
            }
        } else {
            cardView.setVisibility(View.GONE);
        }

        if (showPromoUI && session.getLogin())
            tvMyAccountPromo.setVisibility(View.VISIBLE);
        else
            tvMyAccountPromo.setVisibility(View.GONE);

        if (showWallet && session.getLogin()) {
            layWallet.setVisibility(View.VISIBLE);
            getWallet(session.getIboKeyId());
        } else {
            layWallet.setVisibility(View.GONE);
        }
        layWallet.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyWalletActivity.class);
            intent.putExtra("wallet", walletamount);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }

    private void openLink(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    double walletamount = 0;

    private void getWallet(String iboKeyId) {
        mProgressDialog.setVisibility(View.VISIBLE);
        Call<WalletModel> wsCallingEvents = mAPIInterface.getMyWalletAmount(iboKeyId);
        wsCallingEvents.enqueue(new Callback<WalletModel>() {
            @Override
            public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {
                if (mProgressDialog != null) {
                    mProgressDialog.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        WalletModel data = response.body();

                        if (data.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            walletamount = 0;
                        } else if (data.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            walletamount = data.getData();
                        }
                    }
                }
                tvWalletvalue.setHint("₹ " + walletamount);
            }

            @Override
            public void onFailure(Call<WalletModel> call, Throwable t) {
                mProgressDialog.setVisibility(View.GONE);
                tvWalletvalue.setHint("₹ " + walletamount);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account_ecom, container, false);
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

        if (session.getLogin()) {
            if (userName == null || userName.equals("")) {
                getMyProfileToolBar();
                Log.d("UserName Fill", "UserName Fill " + userName);
            } else {
                Log.d("UserName Empty", "UserName Empty " + userName);
                tvToolbarTitle.setText("Hi, " + userName);
            }
        } else {
            tvToolbarTitle.setText("Hi, Guest");
        }
    }

    private void getMyProfile() {
        if (Utils.isNetworkAvailable(getContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            mAPIInterfaceProfile = APIClient.getClient(getActivity()).create(APIInterface.class);
            Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceProfile.getMyProfileEcom();
            wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
                @Override
                public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                                String firstName = response.body().getData().getFirstName();
                                String lastName = response.body().getData().getLastName();
                                String email = response.body().getData().getEmail();
                                String gender = response.body().getData().getGender();
                                String mobile = response.body().getData().getMobile();
                                Log.d("firstName", "firstName: " + firstName);

                                Log.d("PROFILEAPIIN", "PROFILEAPIIN: " + response);
                                Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                dialog.setContentView(R.layout.dialog_profile);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

                                // This is line that does all the magic
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                lp.gravity = Gravity.CENTER;
                                dialog.getWindow().setAttributes(lp);

                                tvProfileName = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_name);
                                tvProfileEmail = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_email);
                                tvProfileMobile = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_mobile);
                                tvProfileGender = (MyTextViewEcom) dialog.findViewById(R.id.tv_my_profile_gender);
                                imgProfileClose = (ImageView) dialog.findViewById(R.id.img_profile_close);

                                tvProfileName.setText(firstName);
                                tvProfileEmail.setText(email);
                                tvProfileMobile.setText(mobile);
                                tvProfileGender.setText(gender);

                                imgProfileClose.setOnClickListener(v -> dialog.dismiss());
                                dialog.show();

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }

                        }
                    } else if (response.code() == 401) {
                        Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));
                        mProgressDialog.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<ProfileModelEcom> call, Throwable t) {
                    Log.d("PROFILEAPIINERROR", "PROFILEAPIINERROR: " + t);
                    mProgressDialog.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            utils.dialogueNoInternet(getActivity());
        }
    }


    private void getMyProfileToolBar() {
        mAPIInterfaceProfile = APIClient.getClient(getActivity()).create(APIInterface.class);
        //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceProfile.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @Override
            public void onResponse(Call<ProfileModelEcom> call, Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                            Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                            String lastName = response.body().getData().getLastName();
                            String email = response.body().getData().getEmail();
                            String gender = response.body().getData().getGender();
                            String mobile = response.body().getData().getMobile();
                            Log.d("firstName", "firstName: " + firstName);

                            userName = "Hi, " + firstName;
                            sessionNormal.setUserName(firstName);
                            tvToolbarTitle.setText(userName);
                            Log.d("PROFILEAPIIN", "PROFILEAPIIN: " + response);

                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
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
    }

    private void getAddressList() {
        if (Utils.isNetworkAvailable(getContext())) {
            mAPIInterfaceProfile = APIClient.getClient(getActivity()).create(APIInterface.class);
           /* mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            mProgressDialog.show();

            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

            mProgressDialog.setVisibility(View.VISIBLE);
           /* mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
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
            });*/

            Call<AddressData> wsCallingEvents = mAPIInterfaceProfile.getAddressListEcom();
            wsCallingEvents.enqueue(new Callback<AddressData>() {
                @Override
                public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

                            Log.d("AddressListGETOUT", "AddressListGETOUT: " + response.body().getMessage());
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Intent intent = new Intent(getActivity(), MyAddressAccountActivity.class);
                                startActivity(intent);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (response.body().getData().size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                            } else {
                                //llEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (response.code() == 401) {
                        Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));

                       /* Intent addressAccountIntent = new Intent(getActivity(), LoginActivityEcom.class);
                        addressAccountIntent.putExtra("ADDRESSACCOUNTCALL", 1);
                        startActivity(addressAccountIntent);*/
                    }
                }

                @Override
                public void onFailure(Call<AddressData> call, Throwable t) {

                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("AddressListGETError", "AddressListGETError: " + t);
                    Intent intent = new Intent(getActivity(), MyAddressAccountActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            // noInternetConnection();
            utils.dialogueNoInternet(getActivity());
        }
    }
}

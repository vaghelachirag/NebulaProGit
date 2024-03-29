package com.nebulacompanies.ibo.ecom.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.LoginActivity;
import com.nebulacompanies.ibo.activities.LoginSkipActivity;
import com.nebulacompanies.ibo.activities.LoginSkipDialog;
import com.nebulacompanies.ibo.activities.NoLoginActivity;
import com.nebulacompanies.ibo.adapter.CouponcodeAdapter;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.model.ActivePromoCodeModel;
import com.nebulacompanies.ibo.model.LoginValidate;
import com.nebulacompanies.ibo.model.ShareRefID;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.nebulacompanies.ibo.util.Config.shareMainUrl;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;

public class Utils {
    public static String closeCart = "Closecart";
    public static String actionCoupon = "actionCoupon";
    public static String actionLogin = "actionLogin";
    public static String actionUservalid = "actionUservalid";
    public static String actionFail = "actionFail";
    public static String actionCloseDialog = "actionCloseDialog";

    public static String shareUrl = shareMainUrl + "Product/";// "https://nebulacompanies.net/";//http://203.88.139.169:9066/Product/";
    public int productShare = 110;
    public static ArrayList<String> myPromocode = new ArrayList<>();
    public static boolean promoAPIcall = false;
    public static String selPromocode = "";
    public static int callForceLogout = 0;

//    public static boolean showPromoUI = false;
//    public static boolean showRefUI = true; // Live
//    public static boolean showWallet = true; // Live
//    public static boolean showVariants = true; // Live
//    public static boolean showLocation = false; // Live
//    public static boolean showReviews = false;
//    public static boolean showSimilar = true; // Live
//    public static boolean showTrendingnew = true; // Live
//    public static boolean showWithoutLogin = false;
//    public static boolean showdialogCart = false;


    public static boolean showRefUI = true; // Live
    public static boolean showWallet = true; // Live
    public static boolean showPromoUI = false;
    public static boolean showReviews = false;
    public static boolean showWithoutLogin = false;

    public static String convertDateRes = "";

    public int gotoMyDashboard = 101;
    public int gotoMysales = 102;
    public int gotoMypurchase = 103;
    public int gotoMycommunity = 104;
    public int gotoProducts = 105;
    public int gotoDocuments = 106;
    public int gotoEvents = 107;
    public int gotoMyProfile = 108;
    public int gotoIbosupport = 109;

    public int gotoMyBusiness = 201;
    public int gotoIncome = 202;
    public int gotoPromos = 203;
    public int gotoDownline = 204;

    public int gotoMyCart = 301;
    public int gotoNologin = 302;
    public int gotoDwarka = 401;

    public static int gotoSettings = 501;
    Session session;


    // For ProgressDialoug
  public  static   ProgressDialog mProcessDiaoug;

    public void shareReferenceID(Context ctx, String refid, String id, String pName, String ibokeyid, ImageView imgShare,
                                 ProgressDialog mLoadProgressDialog) {

        if (TextUtils.isEmpty(refid)) {
            imgShare.setEnabled(true);
            dialogueLogout((Activity) ctx);
            //Toast.makeText(ctx, "Do Logout", Toast.LENGTH_SHORT).show();
        } else {
            if (Utils.isNetworkAvailable(ctx)) {
                mLoadProgressDialog.show();
                if (promoAPIcall) {
                    doCallShare(ctx, refid, id, pName, ibokeyid, imgShare, mLoadProgressDialog);
                } else {
                    doCallPromo(ctx, refid, id, pName, ibokeyid, imgShare, mLoadProgressDialog);
                }
            } else {
                imgShare.setEnabled(true);
                dialogueNoInternet((Activity) ctx);
            }
        }
    }

    private void doCallPromo(Context ctx, String refid, String id, String pName, String ibokeyid, ImageView imgShare, ProgressDialog mLoadProgressDialog) {
        APIInterface mAPIInterface = APIClient.getClient(ctx).create(APIInterface.class);
        Call<ActivePromoCodeModel> wsCallingEvents = mAPIInterface.getActivePromoCodes(ibokeyid);
        wsCallingEvents.enqueue(new Callback<ActivePromoCodeModel>() {
            @Override
            public void onResponse(@NotNull Call<ActivePromoCodeModel> call, @NotNull Response<ActivePromoCodeModel> response) {
                if (response.isSuccessful() && response.code() == 200 && Objects.requireNonNull(response.body()).getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                    promoAPIcall = true;
                    myPromocode.clear();
                    if (response.body().getData().size() > 0) {
                        myPromocode.addAll(response.body().getData());
                        myPromocode.add("None");
                    }
                    doCallShare(ctx, refid, id, pName, ibokeyid, imgShare, mLoadProgressDialog);
                } else {
                    imgShare.setEnabled(true);
                    mLoadProgressDialog.dismiss();
                    Toast.makeText(ctx, R.string.error_normal, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ActivePromoCodeModel> call, @NotNull Throwable t) {
                imgShare.setEnabled(true);
                mLoadProgressDialog.dismiss();
                Toast.makeText(ctx, R.string.error_normal, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doCallShare(Context ctx, String refid, String id, String pName, String ibokeyid, ImageView imgShare,
                             ProgressDialog mLoadProgressDialog) {
        Log.d("pName==", pName + " : ");
        String url = shareUrl + id + "-" + pName.replaceAll("\\s+", "-").replaceAll("\\|", "") + "?refid=" + refid;

        APIInterface mAPIInterface = APIClient.getClient(ctx).create(APIInterface.class);
        Call<ShareRefID> wsCallingEvents = mAPIInterface.getReferenceId(ibokeyid, Integer.parseInt(id), refid, url);
        wsCallingEvents.enqueue(new Callback<ShareRefID>() {
            @Override
            public void onResponse(@NotNull Call<ShareRefID> call, @NotNull Response<ShareRefID> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                    mLoadProgressDialog.dismiss();
                    if (myPromocode.size() == 0) {
                        String text = "Hey! Check out Nebulacare’s " + pName + ". Now shipping all across India. Shop now, click on the link below.\n\n";
                        imgShare.setEnabled(true);
                        shareData(ctx, text, url);
                    } else {
                        selPromocode = "";
                        Dialog dialog = new Dialog(ctx);
                        dialog.setCancelable(false);
                        CouponcodeAdapter mAdapter = new CouponcodeAdapter(myPromocode);

                        dialog.setContentView(R.layout.dialog_couponcode);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager((Activity) ctx);

                        RecyclerView recView = dialog.findViewById(R.id.rec_coupon);
                        recView.setLayoutManager(mLayoutManager);
                        recView.setAdapter(mAdapter);
                        MyButtonEcom btnApply = dialog.findViewById(R.id.btn_apply);
                        btnApply.setOnClickListener(v -> {

                            String text;
                            if (TextUtils.isEmpty(selPromocode)) {
                                Toast.makeText(ctx, "Please select coupon code.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (selPromocode.equalsIgnoreCase("none")) {
                                    text = "Hey! Check out Nebulacare’s " + pName + ". Now shipping all across India. Shop now, click on the link below.\n\n";
                                } else {
                                    text = "Hey! Check out Nebulacare’s " + pName + ". Now shipping all across India.\n" +
                                            "Use the promo code <" + selPromocode + "> to avail offer on all your purchases. Discount valid for limited time. Shop now, click on the link below.\n\n";
                                }
                                dialog.dismiss();
                                imgShare.setEnabled(true);
                                shareData(ctx, text, url);
                            }
                        });
                        dialog.show();
                    }
                } else {
                    imgShare.setEnabled(true);
                    mLoadProgressDialog.dismiss();
                    Toast.makeText(ctx, R.string.error_normal, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ShareRefID> call, @NotNull Throwable t) {
                mLoadProgressDialog.dismiss();
                imgShare.setEnabled(true);
                Toast.makeText(ctx, R.string.error_normal, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void shareData(Context ctx, String text, String url) {
        ShareCompat.IntentBuilder.from((Activity) ctx)
                .setType("text/plain")
                .setChooserTitle("Nebula Care")
                .setText(text + url)
                .startChooser();
    }

    Typeface typeface;

    public static boolean isNetworkAvailable(Context context) {
        boolean b = true;
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            b = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            Log.d("Network==", ": " + b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @SuppressLint("SimpleDateFormat")
    public String convertDateFormat(String date1) {
        if (date1 != null && !date1.isEmpty()) {

            Date date = null;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = dateFormat.parse(date1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);
            SimpleDateFormat printFormat = new SimpleDateFormat("dd/MM/yyyy");
            convertDateRes = printFormat.format(calendar.getTime());
            Log.d("result = ", "==========>" + convertDateRes);
        }
        return convertDateRes;
    }

    @SuppressLint("SimpleDateFormat")
    public String convetDate(long unixSeconds) {
        String formattedDate = "";
        try {
            if (unixSeconds != 0) {
                Date date = new java.util.Date(unixSeconds * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy (hh:mm a)");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
                formattedDate = sdf.format(date);
                System.out.println(formattedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
   /*public Long convetDatetomilli(long unixSeconds) {
        Long formattedDate = 0L;
        try {
            if (unixSeconds != 0) {
                Date date = new java.util.Date(unixSeconds * 1000L);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy (hh:mm a)");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
                formattedDate = sdf.format(date);
                System.out.println(formattedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }*/

    public String convertDateMonth(long unixSeconds) {
        String formattedDate = "";
        try {
            if (unixSeconds != 0) {
                Date date = new java.util.Date(unixSeconds * 1000L);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy (hh:mm a)");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
                formattedDate = sdf.format(date);
                System.out.println(formattedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public String convertDate(long unixSeconds) {
        String formattedDate = "";
        try {
            if (unixSeconds != 0) {
                Date date = new java.util.Date(unixSeconds * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd (hh:mm a)");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
                formattedDate = sdf.format(date);
                System.out.println(formattedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    @SuppressLint("SetTextI18n")
    public void noLoginDailog(Activity activity) {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(activity, SweetAlertDialogCart.CUSTOM_IMAGE_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("Login");
        loading.setCancelText("Cancel");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            // ImageView imgCustom = (ImageView) alertDialog.findViewById( R.id.custom_image );
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            MyButton btnCancel = alertDialog.findViewById(R.id.cancel_button);
            ImageView imageView = alertDialog.findViewById(R.id.custom_image);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_baseline_login_24);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textTitle.setText(R.string.login_title);
            textTitle.setVisibility(View.GONE);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textContent.setText(R.string.login_content);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            //textTitle.setText(response.body().getMessage());
           /* imgCustom.setVisibility( View.VISIBLE );
            imgCustom.setImageResource(R.drawable.success_pay);*/

            btnConfirm.setText("Login");
            btnCancel.setText("Cancel");
            // textContent.setText("Pin you have entered is Invalid.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnCancel.setOnClickListener(v -> {
                loading.cancel();
                activity.finish();
            });
            btnConfirm.setOnClickListener(v -> {
                // lnDwarka.setVisibility( View.VISIBLE );
                // loading.dismiss();
                Intent login = new Intent(activity, LoginActivity.class);
                activity.startActivity(login);
                // activity.finishAffinity();
                activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                //finish();
            });
        });

        loading.show();
    }


    public void openLogin(Activity activity) {
        Intent login = new Intent(activity, LoginActivity.class);
        activity.startActivity(login);
        // activity.finishAffinity();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @SuppressLint("SetTextI18n")
    public void dialogueLogout(Activity activity) {
        SweetAlertDialog loading = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");


        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            textTitle.setText("Do Logout");
            btnConfirm.setText("Ok");
            textContent.setText("Please Login again!");

            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                session = new Session(activity);
                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(activity, R.style.MyTheme);
                try {
                    progressDialog.show();
                } catch (Error ignored) {
                }
                progressDialog.setContentView(R.layout.progressdialog);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Intent i = new Intent(activity, LoginActivity.class);
                i.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                session.setLogout(true);
                session.setLogin(false);

                PreferenceManager.getDefaultSharedPreferences(activity).edit().clear().apply();

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                session.clear();
                Config.CUSTOMER_LOGIN_ID = "";
                loading.dismiss();
                activity.startActivity(i);
            });
        });
        loading.show();
    }


    public void dialogueNoInternet(Activity activity) {
        SweetAlertDialog loading = new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setCustomImage(R.drawable.ic_cloud_off_black_24dp);

        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            textTitle.setText(R.string.error_title);
            btnConfirm.setText("Ok");
            textContent.setText(R.string.error_content);

            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });
        loading.show();
    }

    public static void darkenStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(
                    darkenColor(
                            ContextCompat.getColor(activity, color)));
        }
    }


    // Code to darken the color supplied (mostly color of toolbar)
    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }


    public static void main(String[] args) {
        double d = 1234;
       /* System.out.println(d);
        System.out.println(String.valueOf((int) d));*/
    }

    public void setShare(ImageView imgshare, Context context, boolean isassociate, boolean showsharelink) {
        Log.d("call==", "image share " + showsharelink + " : " + isassociate);
        Session session = new Session(context);
        // Session session = new Session(context);
        imgshare.setVisibility(showRefUI && session.getLogin() ? View.VISIBLE : View.GONE);
        if (showRefUI && session.getLogin()) {
            if (isassociate)
                imgshare.setVisibility(View.GONE);
            else
                imgshare.setVisibility(showsharelink ? View.VISIBLE : View.GONE);
        }
    }

    public void openNoSession(Activity context, int reqcode) {
        Intent intent = new Intent(context, NoLoginActivity.class);
        // intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("req", reqcode);
        context.startActivityForResult(intent, reqcode);
        //context.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void openSkipLogin(Activity context, int reqcode) {
        Intent intent = new Intent(context, LoginSkipActivity.class);
        // intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("req", reqcode);
        context.startActivityForResult(intent, reqcode);
        //context.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void openLoginDialog(Activity context, int reqcode) {
        Intent intent = new Intent(context, LoginSkipDialog.class);
        //intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("req", reqcode);
        context.startActivityForResult(intent, reqcode);
        //context.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void checkExpireUser(APIInterface mAPIInterface, Context mcontext, Session session) {
        Log.d("checkExpireUser", "callForceLogout: " + (callForceLogout)+" : "+session.getLoginID());
        if (String.valueOf(callForceLogout).equalsIgnoreCase(session.getLoginID())) {
            getExpireUser(mAPIInterface, mcontext, session);
        }
    }

    public void getExpireUser(APIInterface mAPIInterface, Context mcontext, Session session) {
        Log.d("getExpireUser", "callForceLogout: " + (callForceLogout));
        Call<LoginValidate> wsCallingToken = mAPIInterface.loginValidate(session.getIboKeyId());
        wsCallingToken.enqueue(new Callback<LoginValidate>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<LoginValidate> call, @NotNull Response<LoginValidate> response) {
                Utils.hideProgressDialoug();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callForceLogout = 0;
                        Log.d("INFO", "getExpireUser Send:- " + response.body().getData());
                        LoginValidate loginValidate = response.body();
                        assert loginValidate != null;
                        if (loginValidate.getData() == 1) {
                            showErrorDialog(mcontext, false, "", response.body().getMessage(), true, session);
                        } else {
                            Intent intent = new Intent(actionUservalid);
                            mcontext.sendBroadcast(intent);

                            intent = new Intent(actionCloseDialog);
                            mcontext.sendBroadcast(intent);
                        }
                    } else {
                        Log.e("Token Get 4", "Token Get 4: ");
                        Intent intent = new Intent(actionCloseDialog);
                        mcontext.sendBroadcast(intent);
                        intent = new Intent(actionFail);
                        mcontext.sendBroadcast(intent);
                    }
                } else {
                    Log.e("Token Get 4", "Token Get 4: ");
                    Intent intent = new Intent(actionCloseDialog);
                    mcontext.sendBroadcast(intent);
                    intent = new Intent(actionFail);
                    mcontext.sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginValidate> call, @NotNull Throwable t) {
                Utils.hideProgressDialoug();
                Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                Log.e("Token Get 4", "Token Get 4: " + t.getMessage());
                Intent intent = new Intent(actionCloseDialog);
                mcontext.sendBroadcast(intent);
                intent = new Intent(actionFail);
                mcontext.sendBroadcast(intent);

            }
        });
    }

    public void showErrorDialog(Context context, boolean cancel, String header, String message, boolean doLogout, Session session) {
        if (doLogout) doLogout(context, session);
        SweetAlertDialog loading = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
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
            //textTitle.setText(response.body().getMessage());
            textTitle.setText(header);
            textContent.setText(message);
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                Intent editAddress = new Intent(context, LoginActivity.class);
                editAddress.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(editAddress);
                //   context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            });
        });

        try {
            loading.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would not display
            // the 'Force Close' message
        }
    }

    public void doLogout(Context mcontext, Session session) {
        session.setLogout(true);
        session.setLogin(false);

        PreferenceManager.getDefaultSharedPreferences(mcontext).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefIsfirstrun, Context.MODE_PRIVATE).edit().clear().apply();

        session.setUserName("");

        mcontext.getSharedPreferences(PrefUtils.prefLocation, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefCityid, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefMrp, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefFirstpurchase, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefJoindays, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefFacility, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefFamily, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefWeekly, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefCommunity, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefKey, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefTransaction, Context.MODE_PRIVATE).edit().clear().apply();

        mcontext.getSharedPreferences(PrefUtils.prefEwalletTransaction, Context.MODE_PRIVATE).edit().clear().apply();
        session.clear();
        Config.CUSTOMER_LOGIN_ID = "";
    }
    /*CardView myCard = (CardView) findViewById(R.id.myCard);
    sendViewViaMail(myCard, getApplicationContext(), Activity_Cabinet.this, report);*/
    /*public void sendViewViaMail(final View view, final Context baseContext, final Context activityContextOnly, final String textToMail) {
        view.post(new Runnable() {
            @Override
            public void run() {
                int heightG = view.getHeight();
                int widthG = view.getWidth();
                sendViewViaMail(view, baseContext, activityContextOnly, widthG, heightG, textToMail);
            }
        });
    }*/

    public void sendViewViaMail(Bitmap bitmap,  Context activityContextOnly,String textToMail) {
        //Bitmap bitmap = getBitmapFromView(baseContext,view);
        Uri imageUri = null;

        File file = null;
        FileOutputStream fos1 = null;
        try {
            File folder = new File(activityContextOnly.getCacheDir() + File.separator + "My Temp Files");

            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            String filename = "img.jpg";
            file = new File(folder.getPath(), filename);

            fos1 = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos1);

            imageUri = FileProvider.getUriForFile(activityContextOnly, activityContextOnly.getPackageName() + ".fileprovider", file);
        } catch (Exception ex) {

        } finally {
            try {
                fos1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final Intent emailIntent1 = new Intent(Intent.ACTION_SEND);
        emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent1.putExtra(Intent.EXTRA_EMAIL, new String[]{});
        emailIntent1.putExtra(Intent.EXTRA_STREAM, imageUri);
        emailIntent1.putExtra(Intent.EXTRA_SUBJECT, "[" + "COMPANY_HEADER" + "]");
        emailIntent1.putExtra(Intent.EXTRA_TEXT, textToMail);
        emailIntent1.setData(Uri.parse("mailto:" + "mail@gmail.com")); // or just "mailto:" for blank
        emailIntent1.setType("image/jpg");
        activityContextOnly.startActivity(Intent.createChooser(emailIntent1, "Send email using"));
    }
    private static Bitmap getBitmapFromView(Context ctx, View view) {
        view.setLayoutParams(new
                ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        view.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels,
                View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels,
                        View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bitmap;
    }
    public static Bitmap createViewBitmap(View view, int widthG, int heightG) {
        Bitmap viewBitmap = Bitmap.createBitmap(widthG, heightG, Bitmap.Config.RGB_565);
        Canvas viewCanvas = new Canvas(viewBitmap);
        Drawable backgroundDrawable = view.getBackground();

        if(backgroundDrawable!=null){
            backgroundDrawable.draw(viewCanvas);
        }
        else{
            viewCanvas.drawColor(Color.WHITE);
            view.draw(viewCanvas);
        }
        return viewBitmap;
    }

    // For show Keyboard
    public static void showKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    // For Hide Keyboard
    public static void  hideKeyboard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public  static void  showProgressDialoug(Context context){
        mProcessDiaoug = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        mProcessDiaoug.setCancelable(false);
        mProcessDiaoug.setMessage("Loading...");

        if (mProcessDiaoug !=null){
            if (!mProcessDiaoug.isShowing()){
                mProcessDiaoug.show();
            }
        }
        Log.e("MyCondition","MyCodition");
    }

    public static void  hideProgressDialoug(){
        if (mProcessDiaoug !=null){
            if (mProcessDiaoug.isShowing()){
                mProcessDiaoug.dismiss();
            }
        }
    }
}

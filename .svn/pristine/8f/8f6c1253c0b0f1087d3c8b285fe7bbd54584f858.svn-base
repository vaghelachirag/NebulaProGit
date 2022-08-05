package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.io.File;
import java.util.Objects;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionLogin;
import static com.nebulacompanies.ibo.ecom.utils.Utils.gotoSettings;
import static com.nebulacompanies.ibo.util.Config.FONT_STYLE;

public class SettingFragment extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout logoutlayout, rlRate, rlShare, rlID;
    MyTextView tvVersionname;
    Session session;
    Typeface typeface;
    MyTextView txLoginlogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        setActionBar();
        init();
    }

    void setActionBar() {

        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), FONT_STYLE);
        //Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(R.string.nav_settings); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }

    @SuppressLint("SetTextI18n")
    void init() {

        session = new Session(this);
        rlShare = findViewById(R.id.rl_share);
        rlRate = findViewById(R.id.rl_rate);
        rlID = findViewById(R.id.rl_idcard);
        logoutlayout = findViewById(R.id.logout);
        txLoginlogout = findViewById(R.id.tv_logout);
        tvVersionname = findViewById(R.id.tv_version_name);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        rlShare.setOnClickListener(this);
        rlRate.setOnClickListener(this);
        rlID.setOnClickListener(this);
        logoutlayout.setOnClickListener(this);
        txLoginlogout.setText(getResources().getString(session.getLogin() ? R.string.logout : R.string.login));

        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert info != null;
        String versionName = info.versionName;
        //int versioncode = info.versionCode;
        tvVersionname.setText("APP VERSION: " + versionName);

        intentFilter = new IntentFilter();
        intentFilter.addAction(actionLogin);
        registerReceiver(downlineRankReceiver, intentFilter);
    }

    IntentFilter intentFilter;
    private final BroadcastReceiver downlineRankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(actionLogin)) {
                finish();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                if (session.getLogin())
                    getLogout();
                else
                    new Utils().openLoginDialog(SettingFragment.this, gotoSettings);
                break;

            case R.id.rl_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Nebula");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "http://play.google.com/store/apps/details?id=" + getPackageName();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.rl_rate:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case R.id.rl_idcard:
                Intent intenti=new Intent(SettingFragment.this,IdcardActivity.class);
                startActivity(intenti);
                break;

        }
    }

    @SuppressLint("SetTextI18n")
    void getLogout() {
        SweetAlertDialog loading = new SweetAlertDialog(SettingFragment.this, SweetAlertDialog.NORMAL_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setCancelText("Cancel");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            TextView textTitle = alertDialog.findViewById(R.id.title_text);
            TextView textContent = alertDialog.findViewById(R.id.content_text);
            Button btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            Button btnCancel = alertDialog.findViewById(R.id.cancel_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            btnCancel.setTypeface(typeface);
            textTitle.setText("Logout");
            btnConfirm.setText("OK");
            btnCancel.setText("Cancel");
            textContent.setText("Are you sure you want to logout?");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(SettingFragment.this, R.style.MyTheme);
                try {
                    progressDialog.show();
                } catch (Error ignored) { }
                progressDialog.setContentView(R.layout.progressdialog);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                new Utils().doLogout(this, session);
                Intent i = new Intent(SettingFragment.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                loading.dismiss();
                startActivity(i);
                finish();
            });
            btnCancel.setOnClickListener(view -> dialog.dismiss());
        });
        loading.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ErrorS", "ErrorS");
    }

    //Fires after the OnStop() state
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downlineRankReceiver);
        Log.d("ErrorD", "ErrorD");
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
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        assert dir != null;
        return dir.delete();
    }
}


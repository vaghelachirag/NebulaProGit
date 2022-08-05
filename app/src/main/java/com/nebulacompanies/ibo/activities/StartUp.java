package com.nebulacompanies.ibo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;

import static com.nebulacompanies.ibo.ecom.utils.PrefUtils.prefSkiplogin;
import static com.nebulacompanies.ibo.ecom.utils.Utils.showWithoutLogin;

/**
 * Created by Palak Mehta on 6/10/2016.
 */
public class StartUp extends Activity {
    private Handler myHandler;
    private Runnable myRunnable;
    Session session;
    ConnectionDetector cd;
    String versionName, versionNameSave;
   
    int launch_count;
    SharedPreferences prefs;
    //private static APIInterface mAPIInterface;
    SharedPreferences settings;
    boolean skiplogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        session = new Session(this);
        settings = getSharedPreferences(PrefUtils.prefMyprefsfile, 0);

       /*Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        Log.e( "StartUp","StartUp: "+data );
        Log.e( "StartUp","StartUp: "+action );*/

        prefs = getSharedPreferences(PrefUtils.prefMyappnebula, MODE_PRIVATE);
        cd = new ConnectionDetector(getApplicationContext());
        // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        //mAPIInterface = APIClient.getClient(StartUp.this).create(APIInterface.class);
        SharedPreferences SharedPreferencesUserName = getSharedPreferences(PrefUtils.prefVersion, 0);
        versionNameSave = SharedPreferencesUserName.getString(PrefUtils.prefVersion, null);
        skiplogin = settings.getBoolean(prefSkiplogin, false);

        if (versionName == null || versionName.equals("")) {
            if (versionNameSave == null || versionNameSave.equals("")) {
                int versionCode = BuildConfig.VERSION_CODE;
                versionName = BuildConfig.VERSION_NAME;
                Log.d("version322343243535C", "version322343243535C: " + versionCode);
                Log.d("version322343243535N", "version322343243535N: " + versionName);

                SharedPreferences preferences = getSharedPreferences(PrefUtils.prefVersion, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefVersion, versionName);
                editor.apply();
            } else {
                versionName = versionNameSave;
            }
        }

        // checkVersion();

/*String ratio =""+String.format("%d:%d", 630,230);

        Log.d("ratio", "ratio " + ratio);*/

        /*if (data.equals( "https://www.limeroad.com/Home_page" )){
            Intent dash = new Intent( StartUp.this, MainActivity.class );
            startActivity( dash );
        }else if (data.equals( "https://www.limeroad.com/Category_page" )){
            Intent dash = new Intent( StartUp.this, MyCategoryActivity.class );
            startActivity( dash );
        }*/

        launch_count = prefs.getInt("launch_count_app", 0);
        if (launch_count >= 2) {
        } else {
            prefs.edit()
                    .putInt("launch_count_app", launch_count + 1)
                    .apply();
        }


        myHandler = new Handler();
        myRunnable = () -> {
            SharedPreferences sharedPrefs = getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.clear();
            editor.apply();
            if (versionName.equals(versionNameSave)) {
                if (session.getLogin()) {
                    openDashboard();
                } else {
                    if (settings.getBoolean("walkthrough_first_time", true)) {
                        settings.edit().putBoolean("walkthrough_first_time", false).apply();
                        openTaketour();
                    } else {
                        // if (skiplogin && showWithoutLogin) {
                        if (showWithoutLogin) {
                            openDashboard();
                        } else {
                            openLogin();
                        }
                    }
                }
            } else {
                openTaketour();
            }
            finish();
        };
        myHandler.postDelayed(myRunnable, 2590);
    }

    private void openLogin() {
        Intent login = new Intent(StartUp.this, LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void openTaketour() {
        Intent login = new Intent(StartUp.this, TakeTourActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void openDashboard() {
        Intent dash = new Intent(StartUp.this, DashboardActivity.class);
        dash.putExtra(PrefUtils.prefMyappnebula, launch_count);
        startActivity(dash);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacks(myRunnable);
        super.onDestroy();
    }

   /* private void checkVersion() {
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version_code = info.versionCode;
        version_name = info.versionName;
        Log.i("Info", "version_number : " + version_code);
        Log.i("Info", "version_name : " + version_name);

        if (Config.isFirstTime) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                try {
                   *//* versionChecker = new  VersionChecker(version_name);
                    versionChecker.execute();*//*
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
                                            @SuppressLint("RestrictedApi") androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper(StartUp.this, R.style.Update_Dialog));
                                            builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                                    try {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                    } catch (android.content.ActivityNotFoundException anfe) {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                    }
                                                }
                                            });
                                           *//* builder.setNegativeButton(R.string.update_later, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });*//*
                                            LayoutInflater inflater = getLayoutInflater();
                                            View dialoglayout = inflater.inflate(R.layout.app_update_dialog, null);
                                            //TextView txt = (TextView) dialoglayout.findViewById(R.id.showtext);
                                            builder.setView(dialoglayout);
                                            builder.setCancelable( false );
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
                        public void onFailure(Call<VersionCheck> call, Throwable t) { }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Config.isFirstTime = false;
            }
        }
    }*/
}
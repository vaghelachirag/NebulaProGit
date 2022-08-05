package com.nebulacompanies.ibo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.VersionCheck;
import com.nebulacompanies.ibo.view.MyButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilsVersion
{
    public void checkVersion(Activity mActivity) {
        APIInterface mAPIInterface = APIClient.getClient( mActivity ).create( APIInterface.class );
        PackageManager manager = mActivity.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mActivity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        int version_code = info.versionCode;
        String version_name = info.versionName;
        Log.i("Info", "version_number : " + version_code);
        Log.i("Info", "version_name : " + version_name);

       // if (Config.isFirstTime) {
        if (Utils.isNetworkAvailable(mActivity)) {
            try {
                   /* versionChecker = new  VersionChecker(version_name);
                    versionChecker.execute();*/
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
                                        Log.e( "Version Success", "Version Success" + new Gson().toJson( response.body() ) );

                                        @SuppressLint("RestrictedApi")
                                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mActivity, R.style.Update_Dialog));
                                            /*builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                                    try {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                    } catch (android.content.ActivityNotFoundException anfe) {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                    }
                                                }
                                            });*/
                                          /*  builder.setNegativeButton(R.string.update_later, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });*/
                                        LayoutInflater inflater = LayoutInflater.from(mActivity);
                                        View dialoglayout = inflater.inflate(R.layout.app_update_dialog, null);
                                        //TextView txt = (TextView) dialoglayout.findViewById(R.id.showtext);
                                        MyButton btnUpdate = (MyButton) dialoglayout.findViewById(R.id.btn_update);

                                        btnUpdate.setOnClickListener(view -> {
                                            final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (ActivityNotFoundException anfe) {
                                                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        });

                                        builder.setView(dialoglayout);
                                        builder.setCancelable( false );
                                        if (!mActivity.isFinishing()) {
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
           // Config.isFirstTime = false;
        }
        //}
    }
  /*  public AppUpdateManager appUpdateManager;

    //lambda operation used for below listener
    InstallStateUpdatedListener installStateUpdatedListener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        } else
            Log.e("UPDATE", "Not downloaded yet");
    };
*/
   /* private void popupSnackbarForCompleteUpdate(Activity mActivity) {
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content),
                "Update almost finished!",
                Snackbar.LENGTH_INDEFINITE);
        //lambda operation used for below action
        snackbar.setAction(mActivity.getString(R.string.update_finish), view ->
                appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(mActivity.getResources().getColor(R.color.white));
        snackbar.show();
    }
    public void checkVersionInapp(Activity mActivity){
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(mActivity);

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
                                mActivity,
                                // Include a request code to later monitor this update request.
                                UPDATE_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException ignored) {

                    }
                }
            }
        });
        appUpdateManager.registerListener(installStateUpdatedListener);
    }*/
    /*Check Version First Time*/

    public void checkVersionFirsTime(Activity mActivity) {
        APIInterface mAPIInterface = APIClient.getClient( mActivity ).create( APIInterface.class );
        PackageManager manager = mActivity.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mActivity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        int version_code = info.versionCode;
        String version_name = info.versionName;
        Log.i("Info", "version_number : " + version_code);
        Log.i("Info", "version_name : " + version_name);

        if (Config.isFirstTime) {
        if (Utils.isNetworkAvailable(mActivity)) {
            try {
                   /* versionChecker = new  VersionChecker(version_name);
                    versionChecker.execute();*/
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
                                        Log.e( "Version Success", "Version Success" + new Gson().toJson( response.body() ) );

                                        @SuppressLint("RestrictedApi")
                                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mActivity, R.style.Update_Dialog));
                                            /*builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                                    try {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                    } catch (android.content.ActivityNotFoundException anfe) {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                    }
                                                }
                                            });*/
                                          /*  builder.setNegativeButton(R.string.update_later, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });*/
                                        LayoutInflater inflater = LayoutInflater.from(mActivity);
                                        View dialoglayout = inflater.inflate(R.layout.app_update_dialog, null);
                                        //TextView txt = (TextView) dialoglayout.findViewById(R.id.showtext);
                                        MyButton btnUpdate = (MyButton) dialoglayout.findViewById(R.id.btn_update);

                                        btnUpdate.setOnClickListener(view -> {
                                            final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (ActivityNotFoundException anfe) {
                                                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        });
                                        builder.setView(dialoglayout);
                                        builder.setCancelable( false );
                                        if (!mActivity.isFinishing()) {
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
    }
}

package com.nebulacompanies.ibo.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.DownloadManagerResolver;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.RateChartsDetailsAdapter;
import com.nebulacompanies.ibo.model.RateChartsDetail;
import com.nebulacompanies.ibo.model.RateChartsDetailList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.view.DownloadProgressView;
import com.nebulacompanies.ibo.view.MyTextView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.AvailableSpace.kilobytesAvailable;
import static com.nebulacompanies.ibo.util.AvailableSpace.megabytesAvailable;
import static com.nebulacompanies.ibo.util.InsufficientStorage.InsufficientStorageInDevice;
import static com.nebulacompanies.ibo.util.Permissions.isReadStoragePermissionGranted;
import static com.nebulacompanies.ibo.util.Permissions.isWriteStoragePermissionGranted;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 12/29/2016.
 */

public class DownloadRateCharts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    String project_name;
    String projectId;
    RateChartsDetailsAdapter rateChartsDetailsAdapter;
    ListView listView;
    DownloadProgressView downloadProgressView;
    static Float availableSpace;
    Float itemSize;
    DownloadManager downloadManager;
    private long myDownloadReference;
    Boolean isRefreshed = false;
    String Pdffile;
    String Imagefile;
    Intent intent;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    ConnectionDetector cd;

    private APIInterface mAPIInterface;
    public static final String TAG = "ratechartDetail";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    MaterialProgressBar materialProgressBar;
    public static ArrayList<RateChartsDetailList> arrayListRateChartsDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_events);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            project_name = b.getString("ProductName");
            projectId = b.getString("ProductID");
        }
        setActionBar();
        init();

       /* prefs = PreferenceManager.getDefaultSharedPreferences(DownloadRateCharts.this);
        String downloadRateChartsList = prefs.getString("download_rate_charts_list", "");
        if (downloadRateChartsList != null && downloadRateChartsList != "") {
            LoadDownloadRateData();
        } else {*/
            getRateChartDetailsList();

        //}

        availableSpace = megabytesAvailable();
    }

    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        tv.setText(project_name);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));

    }

    void init() {
        cd = new ConnectionDetector(getApplicationContext());
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.company_event_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listview_company_events);
        listView.setOnItemClickListener(this);
        materialProgressBar= (MaterialProgressBar) findViewById(R.id.progresbar);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void getRateChartDetailsList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
         /*   final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
           *//* mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*//*
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

            materialProgressBar.setVisibility(View.VISIBLE);
            Call<RateChartsDetail> wsCallingRateChartsDetail = mAPIInterface.getRateChartsDetail(projectId);
            wsCallingRateChartsDetail.enqueue(new Callback<RateChartsDetail>() {
                @Override
                public void onResponse(Call<RateChartsDetail> call, Response<RateChartsDetail> response) {
                    if (materialProgressBar != null ) {
                        materialProgressBar.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (arrayListRateChartsDetailList != null) {
                            arrayListRateChartsDetailList.clear();
                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    arrayListRateChartsDetailList.addAll(response.body().getData());
                                     prefs = PreferenceManager.getDefaultSharedPreferences(DownloadRateCharts.this);
                                    editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListRateChartsDetailList);
                                    editor.putString("download_rate_charts_list", json);
                                    editor.apply();
                                    rateChartsDetailsAdapter = new RateChartsDetailsAdapter(DownloadRateCharts.this, arrayListRateChartsDetailList);
                                    listView.setAdapter(rateChartsDetailsAdapter);
                                    rateChartsDetailsAdapter.notifyDataSetChanged();

                                    if (arrayListRateChartsDetailList.size() > 0) {
                                        llEmpty.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                    } else {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                    }

                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }
                            } else {
                                serviceUnavailable();
                            }
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<RateChartsDetail> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);

                        materialProgressBar.setVisibility(View.GONE);

                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
             noInternetConnection();
           // LoadDownloadRateData();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        downloadProgressView = (DownloadProgressView) view.findViewById(R.id.edoc_downloadProgressView);
        String name = arrayListRateChartsDetailList.get(i).getRateChartName();
        String filepath = arrayListRateChartsDetailList.get(i).getRateChartFilePath();
        String filesize = arrayListRateChartsDetailList.get(i).getRateChartFileSize();

        String path = filepath.replaceAll("%20", "");

        String fileName = path.substring(path.lastIndexOf('/') + 1, path.length());

        String[] size_ = filesize.split(" ");

        itemSize = Float.parseFloat(size_[0]);

        if (filesize.contains("MB")) {
            availableSpace = megabytesAvailable();
        } else if (filesize.contains("KB")) {
            availableSpace = kilobytesAvailable();
        }

        String ratechartspath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + fileName + "/";
        File file = new File(ratechartspath);
        Log.i("INFO", "file :" + file);

        if (file.exists()) {
            Log.i("INFO", "file exists :" + file);

            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                uri = Uri.fromFile(file);
            }

            if (isReadStoragePermissionGranted(this)) {
                if (fileName.endsWith("pdf")) {
                    Pdffile = "application/pdf";
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, Pdffile);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                } else {
                    Imagefile = "image";
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, Imagefile);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }

                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    if (fileName.endsWith("pdf"))
                        Toast.makeText(this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "No Application available to view image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.give_storage_permission, Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("INFO", "file doesn't exist:" + file);
            if (availableSpace > itemSize) {
                if (isWriteStoragePermissionGranted(this)) {
                    if (Utils.isNetworkAvailable(getApplicationContext())) {
                        downloadRateCharts(i, name, fileName);
                      //  if (rateChartsDetailsAdapter != null) {
                          // rateChartsDetailsAdapter.callSpotView();
                         //   downloadRateCharts(i, name, fileName);
                     //   }

                    } else {
                        Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.give_storage_permission, Toast.LENGTH_SHORT).show();
                }
            } else {
                InsufficientStorageInDevice(this, "RateCharts");
            }
        }
    }

    public void downloadRateCharts(final int position, final String rateChartName, final String rateChartsFileName) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialog.setTitle(R.string.download);
        if (project_name.equals(Config.HOLIDAY)) {
            alertDialog.setMessage("Are you sure you want to download this " + rateChartName + "?");
        } else {
            alertDialog.setMessage(getResources().getString(R.string.ratechart_download_toast));
        }
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    if (DownloadManagerResolver.resolve(DownloadRateCharts.this)) {
                        Uri uri = Uri.parse(arrayListRateChartsDetailList.get(position).getRateChartFilePath());
                        final DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDescription("Download in progress").setTitle(rateChartName);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, rateChartsFileName);
                        request.setVisibleInDownloadsUi(true);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.allowScanningByMediaScanner();
                        myDownloadReference = downloadManager.enqueue(request);
                        //downloadManager.remove(myDownloadReference);

                        downloadProgressView.show(myDownloadReference, new DownloadProgressView.DownloadStatusListener() {
                            @Override
                            public void downloadFailed(int reason) {

                            }

                            @Override
                            public void downloadSuccessful() {
                              //  rateChartsDetailsAdapter.callSpotViewDownload();
                            }

                            @Override
                            public void downloadCancelled() {
                                downloadManager.remove(myDownloadReference);
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //  alertDialog.setIcon(android.R.drawable.stat_sys_download);

        alertDialog.show();
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (rateChartsDetailsAdapter != null) {
                rateChartsDetailsAdapter.clearData();
                rateChartsDetailsAdapter.notifyDataSetChanged();
            }
            getRateChartDetailsList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            LoadDownloadRateData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }


    public void LoadDownloadRateData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(DownloadRateCharts.this);
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("download_rate_charts_list", null);
        Type type = new TypeToken<ArrayList<RateChartsDetailList>>() {
        }.getType();
        arrayListRateChartsDetailList = gson.fromJson(json, type);
        if (json != null) {
            if (rateChartsDetailsAdapter == null)
                rateChartsDetailsAdapter = new RateChartsDetailsAdapter(DownloadRateCharts.this, arrayListRateChartsDetailList);
            listView.setAdapter(rateChartsDetailsAdapter);
            rateChartsDetailsAdapter.notifyDataSetChanged();
            //lnGold.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListRateChartsDetailList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListRateChartsDetailList);
        } else {
            noInternetConnection();
        }
    }
}

package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.RankAndVolumeList;
import com.nebulacompanies.ibo.model.RankAndVolumes;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MyIncomeActivityWeb extends AppCompatActivity {


    String URL = TESTING_API + "/IBO/Income/IBOPayoutReport?Token=";
    WebView webView;

    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    MaterialProgressBar materialProgressBar;
    GifImageView mProgressDialog;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, txtErrorSubTitle;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    ImageView imgRank;
    MyTextView tvName, tvRank;
    private APIInterface mAPIInterface;
    RankAndVolumeList rankAndVolumeList;
    String rank;
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    RelativeLayout laytoolbar;
    String myurl = "";
    Utils utils;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_pv);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);
        utils = new Utils();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        //getRankAndVolumes();
        //myurl = URL;

        init();
        loadURL();
    }

    @Override
    protected void onResume() {
        super.onResume();
        utils.checkExpireUser(mAPIInterface, this, session);

    }

    @SuppressLint("SetJavaScriptEnabled")
    void init() {
        laytoolbar = findViewById(R.id.toolbarlay);
        mProgressDialog =  findViewById(R.id.progressBar);
        lnProgressBar =  findViewById(R.id.ln_progressBar);
        materialProgressBar =  findViewById(R.id.progresbar);
        webView =  findViewById(R.id.webView);
        rlEcom =  findViewById(R.id.rl_ecom);
        imgRank =  findViewById(R.id.img_rank);
        tvName =  findViewById(R.id.tv_name);
        tvRank =  findViewById(R.id.tv_rank);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);

        laytoolbar.setVisibility(View.VISIBLE);
        txtTitle.setText("My Income");
        cd = new ConnectionDetector(this);
        initError();
        Ssession = session.getAccessToken();
        Log.d("URLWebview:", "session.getLogin();: " + session.getLogin());
        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        // rlEcom.setVisibility(View.GONE);
        rlEcom.setOnClickListener(view -> loadURL());
        imgBack.setOnClickListener(v -> onBackPressed());

        String newString = Ssession.replace("bearer ", "");
        myurl = URL + newString;
    }

    private void loadURL() {
        if (!session.getLogin()) {
            mProgressDialog.setVisibility(View.GONE);
            lnProgressBar.setVisibility(View.GONE);
            materialProgressBar.setVisibility(View.GONE);
            utils.noLoginDailog(this);
           // noLogin();
            // Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        } else {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                materialProgressBar.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(myurl);
                Log.d("URLWebview:", "URLWebview:" + myurl);
            } else {
                noInternetConnection();
            }
        }
    }

    void initError() {
        llEmpty =  findViewById(R.id.llEmpty1);
        imgError =  findViewById(R.id.imgError1);
        txtErrorTitle =  findViewById(R.id.txtErrorTitle1);
        txtErrorSubTitle =  findViewById(R.id.txtErrorsubTitle1);
        txtRetry =  findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(v -> {
            if (!session.getLogin()) {
                utils.openLogin(MyIncomeActivityWeb.this);
                //openLogin();
            } else {
                loadURL();
            }
        });
    }


  /*  private void openLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finishAffinity();
        this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }*/

    void noLogin() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.login_title);
        txtErrorSubTitle.setText(R.string.login_content);
        txtRetry.setText(R.string.login_retry);
        imgError.setImageResource(R.drawable.ic_baseline_login_24);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorSubTitle.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        rlEcom.setVisibility(View.GONE);
    }


    void noInternetConnection() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_msg_network);
        txtErrorSubTitle.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorSubTitle.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorSubTitle.setVisibility(View.GONE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // materialProgressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String id = session.getLoginID();
            if (url.endsWith("/IBOPayoutShare/" + id + ".jpg")) {
                if (!download)
                    shareImage(url);
            } else {
                myurl = url;
                view.loadUrl(myurl);
            }
            //materialProgressBar.setVisibility(View.GONE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            materialProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                serviceUnavailable();
            } else {
                noInternetConnection();
            }
        }
    }

    boolean download = false;

    public void shareImage(String imagelink) {
        Log.d("imagelink", imagelink);
        download = true;
        Glide.with(getApplicationContext())
                .load(imagelink)
                .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        //intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", null);
                        Uri screenshotUri = Uri.parse(path);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        intent.setType("image/*");
                        startActivity(Intent.createChooser(intent, "Share"));
                        download = false;
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        download = false;
                        super.onLoadFailed(e, errorDrawable);
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        // Toast.makeText(getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();

                        super.onLoadStarted(placeholder);
                    }
                });
       /* Picasso.with(getApplicationContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Uri uri= Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(fileUri),null,null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Image"));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });*/
    }

    void getRankAndVolumes() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
            Call<RankAndVolumes> wsCallingRankAndVolumes = mAPIInterface.getRankAndVolumes();
            wsCallingRankAndVolumes.enqueue(new Callback<RankAndVolumes>() {
                @Override
                public void onResponse(Call<RankAndVolumes> call, Response<RankAndVolumes> response) {

                    if (response.isSuccessful()) {

                        if (response.code() == 200) {

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                rankAndVolumeList = response.body().getData();

                                Log.d("RankGET", "RankGET: " + rankAndVolumeList.getCurrentRank());

                                rank = session.getLoginID() + " - " + session.getUserName();
                                // rank = session.getUserName()+" ("+rankAndVolumeList.getCurrentRank()+")";
                                tvName.setText(rank);
                                tvRank.setText("(" + rankAndVolumeList.getCurrentRank() + ")");

                                Glide.with(getApplicationContext())
                                        .load(rankAndVolumeList.getCurrentRankIcon()).fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imgRank);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<RankAndVolumes> call, Throwable t) {
                }
            });
        }
    }
}

package com.nebulacompanies.ibo.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.CompanyProjectListModel;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ChangodarBanner extends AppCompatActivity {

    WebView web;
    //GifImageView progressBar;

    MaterialProgressBar mprogressbar;
    int project_id;
    String webUrl, ProjectName;
    private APIInterface mAPIInterface;
    public static ArrayList<CompanyProjectListModel> arrayListCompanyProjectList = new ArrayList<>();
    boolean productType;
    Toolbar toolbar;
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    Utils utils = new Utils();
    //Walkthrough walkthrough;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_new);
        Utils.darkenStatusBar(this,R.color.colorNotification);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            webUrl = b.getString("webUrl");
            project_id = b.getInt("ProjectId");
            ProjectName = b.getString("ProjectName");
            productType = b.getBoolean("productType");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);

        txtTitle.setText(ProjectName);
        imgBack.setOnClickListener(v -> onBackPressed());
      /*  setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(ProjectName);*/
        init();
    }


    public void init() {
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        //walkthrough=new Walkthrough(this);
        web = (WebView) findViewById(R.id.wv);
       // progressBar = (GifImageView) findViewById(R.id.progressBar1);
        mprogressbar = findViewById(R.id.progresbar);
        web.setWebViewClient(new MyWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //web.loadUrl("http://www.aavaasindia.com/html/second/second/hyderabad/option/AavaasAhmedabad.html");
        Log.d("webUrl==",webUrl);
        web.loadUrl(webUrl);
        Handler mHand = new Handler();
        mHand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mprogressbar.setVisibility(View.GONE);
                web.setVisibility(View.VISIBLE);
            }
        }, 5000);

       /* if (productType)
        {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
                        new ShowcaseView.Builder(ChangodarBanner.this)
                                .withMaterialShowcase()
                                .setTarget(navigationButtonViewTarget)
                                .setStyle(R.style.CustomShowcaseTheme2)
                                //.setContentTitle(R.string.company_profile)
                                .setContentText("To go to your home screen please click here.")
                                .build()
                                .show();
                        //walkthrough.setProject_main_Back(true);
                    } catch (ViewTargets.MissingViewException e) {
                        e.printStackTrace();
                    }
                }
            }, 9500);
        }*/
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            mprogressbar.setVisibility(View.GONE);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mprogressbar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        if (productType)
        {
            /*Intent i = new Intent(ChangodarBanner.this, ProductDescriptionAavaas.class);
            i.putExtra("OnBack", true);
            i.putExtra("keyPro", "Products");
            i.putExtra("ProjectId", project_id);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);*/
        }
        else
        {
            super.onBackPressed();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }

    }
}
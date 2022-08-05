package com.nebulacompanies.ibo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.fragments.DashboardFragment;
import com.nebulacompanies.ibo.fragments.MapCorporateOfficeChennai;
import com.nebulacompanies.ibo.fragments.MapCorporateOfficeHyderabad;
import com.nebulacompanies.ibo.fragments.MapCorporateOfficeKolkata;
import com.nebulacompanies.ibo.fragments.MapHeadOfficeAhmedabad;
import com.nebulacompanies.ibo.fragments.MapSiteOfficeAhmedabad;
import com.nebulacompanies.ibo.fragments.MapSiteOfficeChennai;
import com.nebulacompanies.ibo.fragments.MapSiteOfficeHyderabad;
import com.nebulacompanies.ibo.fragments.MapSiteOfficeSanand;
import com.nebulacompanies.ibo.showcaseviewback.ShowcaseView;
import com.nebulacompanies.ibo.showcaseviewback.ViewTarget;
import com.nebulacompanies.ibo.util.ViewTargets;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.WrappingViewPager;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightViewNew;

import java.util.ArrayList;
import java.util.List;

public class ContactUs extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    float zoomLevel = 17;
    SupportMapFragment mapFragment;
    WrappingViewPager wrappingViewPager, wrappingViewPager1, wrappingViewPager3;
    ImageView leftHyderabadImageView, rightHyderabadImageView, rightAhmedabadImageView, leftAhmedabadImageView, rightChennaiImageView, leftchennaiImageView;
    ImageView ahmedabadArrow, hyderabadArrow, chennaiArrow,imgBack;

    //Ahmedabad
    double latitude_headoffice_ahmedabad = 23.012725;
    double longitude_headoffice_ahmedabad = 72.513192;
    double latitude_siteoffice_ahmedabad = 22.919672;
    double longitude_siteoffice_ahmedabad = 72.429997;

    double latitude_siteoffice_sanand = 23.0013205;
    double longitude_siteoffice_sanand = 72.3781123;
    String title_headoffice_ahmedabad = "Nebula Companies, Head Office, Ahmedabad";
    String title_siteoffice_ahmedabad = "Aavaas (Changodar), Ahmedabad";
    String title_siteoffice_sanand = "Aavaas (Sanand), Ahmedabad";

    //Hyderabad
    double latitude_corporateoffice_hyderabad = 17.491652;
    double longitude_corporateoffice_hyderabad = 78.393555;
    double latitude_siteoffice_hyderabad = 17.522135;
    double longitude_siteoffice_hyderabad = 78.356305;
    String title_corporateoffice_hyderabad = "Nebula Companies, Corporate Office, Hyderabad";
    String title_siteoffice_hyderabad = "Aavaas, Hyderabad";

    //Kolkata
    double latitude_corporateoffice_kolkata = 22.554480;
    double longitude_corporateoffice_kolkata = 88.354421;
    String title_corporateoffice_kolkata = "Nebula Companies, Corporate Office, Kolkata";

    //Chennai
    double latitude_corporateoffice_chennai = 12.742213;//12.742213, 79.990199
    double longitude_corporateoffice_chennai = 79.990199;
    String title_corporateoffice_chennai = "Nebula Companies, Corporate Office, Chennai";
    String title_site_office_chennai = "Nebula Companies, Chennai";
    RelativeLayout titleChennai;
    private int type;
    MyTextView tvTollFreePhoneNumber;
    ScrollView svContactUs;
    androidx.appcompat.widget.Toolbar toolbar;
    ToggleButton tbAhmedabad, tbHyderabad, tbChennai;
    LinearLayout lnAhmedabad, lnHyderabad, lnChennai;
    MyTextViewEcom toolbarTitle;

    // final String PREFS_WALKTHROUGH_CONTACT_BACK = "contactbackwalkthrough";
    //Boolean isSpotLight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Utils.darkenStatusBar(this, R.color.colorNotification);
       // setActionBar();
        init();
    }

   /* void setActionBar() {

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.contact_us_toolbar);
        setSupportActionBar(toolbar);
        //if(getActionBar() != null) {
        getSupportActionBar().setTitle("Contact Us");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        // }
    }*/

    private void init() {
        // type = getIntent().getExtras().getInt(EXTRA_TYPE);
        // isSpotLight  = getIntent().getExtras().getBoolean("showSpotLightView");
        svContactUs = (ScrollView) findViewById(R.id.sv_contact_us);
        //  svContactUs.smoothScrollTo(0, 0);
        toolbarTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);
        toolbarTitle.setText("Contact Us");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ContactUs.this);

        tvTollFreePhoneNumber = (MyTextView) findViewById(R.id.tv_toll_free_phone_number);

        wrappingViewPager = (WrappingViewPager) findViewById(R.id.viewpager_ahmedabad);
        setupViewPager(wrappingViewPager);

        wrappingViewPager1 = (WrappingViewPager) findViewById(R.id.viewpager_hyderabad);
        setupViewPager1(wrappingViewPager1);


        //add chennai
        wrappingViewPager3 = (WrappingViewPager) findViewById(R.id.viewpager_chennai);
        setupViewPager3(wrappingViewPager3);


        //add chennai
        leftAhmedabadImageView = (ImageView) findViewById(R.id.left);
        rightAhmedabadImageView = (ImageView) findViewById(R.id.right);
        leftHyderabadImageView = (ImageView) findViewById(R.id.left1);
        rightHyderabadImageView = (ImageView) findViewById(R.id.right1);
        leftchennaiImageView = (ImageView) findViewById(R.id.chennai_left_icon);
        rightChennaiImageView = (ImageView) findViewById(R.id.chennai_right_icon);


        //Modify Sagar Virvani
        //ToggleButton add id
        tbAhmedabad = (ToggleButton) findViewById(R.id.tg_ahmed);
        tbHyderabad = (ToggleButton) findViewById(R.id.tg_hyd);
        tbChennai = (ToggleButton) findViewById(R.id.tg_chennai);

        //
        lnAhmedabad = (LinearLayout) findViewById(R.id.layout_ahmedabad);
        lnHyderabad = (LinearLayout) findViewById(R.id.layout_hyderabad);
        lnChennai = (LinearLayout) findViewById(R.id.layout_chennai);

        //Ahmedabad Title Click Listener
        tbAhmedabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lnAhmedabad.getVisibility() == View.GONE) {

                    if (tbAhmedabad.isChecked()) {
                        tbAhmedabad.setEnabled(false);
                        tbChennai.setEnabled(true);
                        tbHyderabad.setEnabled(true);
                        tbHyderabad.setClickable(true);
                    }
                    tbAhmedabad.setChecked(true);
                    tbChennai.setChecked(false);
                    tbHyderabad.setChecked(false);
                    lnAhmedabad.setVisibility(View.VISIBLE);
                    lnChennai.setVisibility(View.GONE);
                    lnHyderabad.setVisibility(View.GONE);
                    wrappingViewPager.setCurrentItem(0);
                    rightAhmedabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_headoffice_ahmedabad, longitude_headoffice_ahmedabad, title_headoffice_ahmedabad);
                }
            }
        });

        tbHyderabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnHyderabad.getVisibility() == View.GONE) {

                    if (tbHyderabad.isChecked()) {
                        tbHyderabad.setEnabled(false);
                        tbAhmedabad.setEnabled(true);
                        tbChennai.setEnabled(true);
                    }
                    tbHyderabad.setChecked(true);
                    tbChennai.setChecked(false);
                    tbAhmedabad.setChecked(false);
                    lnHyderabad.setVisibility(View.VISIBLE);
                    lnAhmedabad.setVisibility(View.GONE);
                    lnChennai.setVisibility(View.GONE);
                    wrappingViewPager1.setCurrentItem(0);
                    rightHyderabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_corporateoffice_hyderabad, longitude_corporateoffice_hyderabad, title_corporateoffice_hyderabad);
                }
            }
        });

        tbChennai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnChennai.getVisibility() == View.GONE) {

                    if (tbChennai.isChecked()) {
                        tbChennai.setEnabled(false);
                        tbAhmedabad.setEnabled(true);
                        tbHyderabad.setEnabled(true);
                        tbHyderabad.setClickable(true);
                    }
                    tbChennai.setChecked(true);
                    tbHyderabad.setChecked(false);
                    tbAhmedabad.setChecked(false);
                    lnChennai.setVisibility(View.VISIBLE);
                    rightChennaiImageView.setVisibility(View.VISIBLE);
                    lnAhmedabad.setVisibility(View.GONE);
                    lnHyderabad.setVisibility(View.GONE);
                    wrappingViewPager3.setCurrentItem(0);
                    showMap(latitude_corporateoffice_chennai, longitude_corporateoffice_chennai, title_corporateoffice_chennai);
                }
            }
        });


        wrappingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    leftAhmedabadImageView.setVisibility(View.INVISIBLE);
                    rightAhmedabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_headoffice_ahmedabad, longitude_headoffice_ahmedabad, title_headoffice_ahmedabad);
                }
                if (position == 1) {
                    rightAhmedabadImageView.setVisibility(View.VISIBLE);
                    leftAhmedabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_siteoffice_ahmedabad, longitude_siteoffice_ahmedabad, title_siteoffice_ahmedabad);
                }
                if (position == 2) {
                    rightAhmedabadImageView.setVisibility(View.INVISIBLE);
                    leftAhmedabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_siteoffice_sanand, longitude_siteoffice_sanand, title_siteoffice_sanand);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rightAhmedabadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrappingViewPager.setCurrentItem(wrappingViewPager.getCurrentItem() + 1);
            }
        });
        leftAhmedabadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrappingViewPager.setCurrentItem(wrappingViewPager.getCurrentItem() - 1);
            }
        });

        wrappingViewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    leftHyderabadImageView.setVisibility(View.INVISIBLE);
                    rightHyderabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_corporateoffice_hyderabad, longitude_corporateoffice_hyderabad,
                            title_corporateoffice_hyderabad);
                }
                if (position == 1) {
                    rightHyderabadImageView.setVisibility(View.INVISIBLE);
                    leftHyderabadImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_siteoffice_hyderabad, longitude_siteoffice_hyderabad, title_siteoffice_hyderabad);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        leftHyderabadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrappingViewPager1.setCurrentItem(wrappingViewPager1.getCurrentItem() - 1);
            }
        });

        rightHyderabadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrappingViewPager1.setCurrentItem(wrappingViewPager1.getCurrentItem() + 1);
            }
        });


        wrappingViewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    leftchennaiImageView.setVisibility(View.INVISIBLE);
                    rightChennaiImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_corporateoffice_chennai, longitude_corporateoffice_chennai, title_corporateoffice_chennai);
                }
                if (position == 1) {
                    rightChennaiImageView.setVisibility(View.INVISIBLE);
                    leftchennaiImageView.setVisibility(View.VISIBLE);
                    showMap(latitude_corporateoffice_chennai, longitude_corporateoffice_chennai, title_site_office_chennai);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        leftchennaiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrappingViewPager3.setCurrentItem(wrappingViewPager3.getCurrentItem() - 1);
            }
        });

        rightChennaiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrappingViewPager3.setCurrentItem(wrappingViewPager3.getCurrentItem() + 1);
            }
        });

        tvTollFreePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent2 = new Intent(Intent.ACTION_DIAL);
                callIntent2.setData(Uri.parse("tel:1800 123 7073"));
                startActivity(callIntent2);
            }
        });

        //  SharedPreferences walkthroughcontactus = getSharedPreferences(PREFS_WALKTHROUGH_CONTACT_BACK, 0);
      /*  new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
                boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                if (!isSkipMain && isSpotLight) {

                    if (walkthroughcontactus.getBoolean("walkthrough_first_time_contact", true)) {

                        SpotlightViewNew spotLight = new SpotlightViewNew.Builder(ContactUs.this)
                                .introAnimationDuration(400)
                                .enableRevealAnimation(true)
                                .performClick(true)
                                .fadeinTextDuration(400)
                                .setTypeface(FontUtil.get(ContactUs.this, "fonts/Montserrat-Regular.ttf"))
                                .headingTvColor(Color.parseColor("#eb273f"))
                                .headingTvSize(18)
                                .headingTvText("Contact Us")
                                .subHeadingTvColor(Color.parseColor("#ffffff"))
                                .subHeadingTvSize(14)
                                .targetPadding(4)
                                .subHeadingTvText("More about this section")
                                .maskColor(Color.parseColor("#dc000000"))
                                .target(tbHyderabad)
                                .lineAnimDuration(400)
                                .lineAndArcColor(Color.parseColor("#eb273f"))
                                .dismissOnTouch(false)
                                .dismissOnBackPress(false)
                                .enableDismissAfterShown(false)
                                .show();

                        //tbHyderabad.setChecked(true);

                        tbHyderabad.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (lnHyderabad.getVisibility() == View.GONE) {
                                    tbHyderabad.setChecked(true);
                                    if (tbHyderabad.isChecked()) {
                                        tbHyderabad.setEnabled(false);
                                        tbHyderabad.setClickable(false);
                                        tbAhmedabad.setEnabled(true);
                                        tbChennai.setEnabled(true);
                                    }
                                    tbHyderabad.setChecked(true);
                                    tbChennai.setChecked(false);
                                    tbAhmedabad.setChecked(false);
                                    lnHyderabad.setVisibility(View.VISIBLE);
                                    lnAhmedabad.setVisibility(View.GONE);
                                    lnChennai.setVisibility(View.GONE);
                                    wrappingViewPager1.setCurrentItem(0);
                                    rightHyderabadImageView.setVisibility(View.VISIBLE);
                                    showMap(latitude_corporateoffice_hyderabad, longitude_corporateoffice_hyderabad, title_corporateoffice_hyderabad);
                                }

                                spotLight.removeSpotlightView(true);

                                showMapArrow();
                            }
                        });

                    }
                    walkthroughcontactus.edit().putBoolean("walkthrough_first_time_contact", false).apply();
                }
            }
        }, 1500);*/
    }


    private void showMap(Double lattitude, Double longitude, String address) {
        LatLng latLng = new LatLng(lattitude, longitude);
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(address));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private void showMapArrow() {

     /*   SharedPreferences walkthroughcontactus1 = getSharedPreferences(PREFS_WALKTHROUGH_CONTACT_BACK, 0);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
                boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                if (!isSkipMain) {
                    if (walkthroughcontactus1.getBoolean("walkthrough_first_time_contact1", true)) {
                        SpotlightViewNew spotLight = new SpotlightViewNew.Builder(ContactUs.this)
                                .introAnimationDuration(400)
                                .enableRevealAnimation(true)
                                .performClick(true)
                                .fadeinTextDuration(400)
                                .setTypeface(FontUtil.get(ContactUs.this, "fonts/Montserrat-Regular.ttf"))
                                .headingTvColor(Color.parseColor("#eb273f"))
                                .headingTvSize(18)
                                .headingTvText("Contact Us")
                                .subHeadingTvColor(Color.parseColor("#ffffff"))
                                .subHeadingTvSize(14)
                                .subHeadingTvText("More about this section")
                                .maskColor(Color.parseColor("#dc000000"))
                                //.target(rightHyderabadImageView)
                                .target(rightHyderabadImageView)
                                .lineAnimDuration(400)
                                .targetPadding(1)
                                .lineAndArcColor(Color.parseColor("#eb273f"))
                                .dismissOnTouch(false)
                                .dismissOnBackPress(false)
                                .enableDismissAfterShown(false)
                                .show();

                        rightHyderabadImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wrappingViewPager1.setCurrentItem(1);
                                rightHyderabadImageView.setVisibility(View.INVISIBLE);
                                leftHyderabadImageView.setVisibility(View.VISIBLE);
                                showMap(latitude_siteoffice_hyderabad, longitude_siteoffice_hyderabad, title_siteoffice_hyderabad);
                                OnBackHandel();
                            }
                        });
                    }
                    walkthroughcontactus1.edit().putBoolean("walkthrough_first_time_contact1", false).apply();
                }
            }
        }, 500);*/

    }

    private void OnBackHandel() {
       /* SharedPreferences walkthroughcontactus11 = getSharedPreferences(PREFS_WALKTHROUGH_CONTACT_BACK, 0);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (walkthroughcontactus11.getBoolean("walkthrough_first_time_contact11", true)) {
                    try {
                        ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
                        new ShowcaseView.Builder(ContactUs.this)
                                .withMaterialShowcase()
                                .setTarget(navigationButtonViewTarget)
                                .setStyle(R.style.CustomShowcaseTheme2)

                                //.setContentTitle(R.string.company_profile)
                                .setContentText("To go to your home screen please click here.")
                                .build()
                                .show();
                    } catch (ViewTargets.MissingViewException e) {
                        e.printStackTrace();
                    }
                }
                walkthroughcontactus11.edit().putBoolean("walkthrough_first_time_contact11", false).apply();
            }

        }, 2500);*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MapHeadOfficeAhmedabad(), "MapHeadOfficeAhmedabad");
        adapter.addFragment(new MapSiteOfficeAhmedabad(), "MapSiteOfficeAhmedabad");
        adapter.addFragment(new MapSiteOfficeSanand(), "MapSiteOfficeSanand");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager1(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MapCorporateOfficeHyderabad(), "MapCorporateOfficeHyderabad");
        adapter.addFragment(new MapSiteOfficeHyderabad(), "MapSiteOfficeHyderabad");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager2(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MapCorporateOfficeKolkata(), "MapCorporateOfficeKolkata");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager3(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MapCorporateOfficeChennai(), "MapCorporateOfficeChennai");
        adapter.addFragment(new MapSiteOfficeChennai(), "MapCorporateOfficeChennai");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng nebula = new LatLng(latitude_headoffice_ahmedabad, longitude_headoffice_ahmedabad);
        mMap.addMarker(new MarkerOptions().position(nebula).title(title_headoffice_ahmedabad));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nebula, zoomLevel));
        mMap.getUiSettings().setZoomControlsEnabled(true);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  if (isSpotLight){
            Intent i = new Intent(ContactUs.this, DashboardFragment.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            ActivityCompat.finishAffinity(ContactUs.this);
        }else {
            Intent i = new Intent(ContactUs.this, DashboardActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            ActivityCompat.finishAffinity(ContactUs.this);
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
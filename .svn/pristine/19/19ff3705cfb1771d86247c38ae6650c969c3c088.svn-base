package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.BanersList;
import com.nebulacompanies.ibo.ecom.model.Category;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.model.SubCategoryProductList;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;

public class MyLatestCategoryActivity extends AppCompatActivity {

    RecyclerView recproductlist;
    RecyclerView reccategorylist;
    private CategoryProductDetailsAdapter categoryDetailsAdapter;
    private CategoryNameAdapter categoryNameAdapter;
  //  ArrayList<SubCategoryProductList> categoryDetails = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    TabLayout tabLayout;
    Dialog dialog_card;
    TextView txtSortby;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    ArrayList<MyCart> cartModels = new ArrayList<>();
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    // ArrayList<SubCategoryProductList> categoryDetails = new ArrayList<>();
    ArrayList<SubCategoryProductList> categoryDetailsOrig = new ArrayList<>();
    ArrayList<SubCategoryProductList> categoryDetailsDisplay = new ArrayList<>();
    ArrayList<SubCategoryProductList> trendingproductLists = new ArrayList<>();
    ArrayList<SubCategoryProductList> newproductLists = new ArrayList<>();
    HashMap<String, ArrayList<String>> newHashmap = new HashMap<>();
    HashMap<String, ArrayList<String>> trendingHashmap = new HashMap<>();
    ArrayList<String> categoryPic = new ArrayList<>();
    ArrayList<Integer> categoryPrice = new ArrayList<>();
    ArrayList<Integer> categoryMRPPrice = new ArrayList<>();
    ArrayList<String> categoryPV = new ArrayList<>();
    ArrayList<String> categoryBV = new ArrayList<>();
    ArrayList<String> categoryNV = new ArrayList<>();
    ArrayList<String> categoryName = new ArrayList<>();
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    private final ArrayList<BanersList> banersList = new ArrayList<>();
    Session session;
    com.nebulacompanies.ibo.util.Session sessionNormal;
    Typeface typeface;
    UtilsVersion utilsVersion = new UtilsVersion();
    Utils utils;
    APIInterface mAPIInterface, mAPIInterfaceProfile;
    MaterialProgressBar mProgressDialog;
    Handler handler;
    Intent categoryDetailss;
    Integer pvResult;
    int categoryIDTwo = 1,selectCategory,selPos = 0,cityId;
    IntentFilter filterLogin;
    SharedPreferences SharedPreferencesLocationName, citySave, sharedPreferencesAddressType,
            sharedPreferencesFacility , spGetIsAssociate,sharedPreferences;
    String deviceId,uniqueID,userName,sortType,addressNameSave, cityFacility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_new_category);

        initData();
        initUI();
        initDialog();
    }

    private void initData() {

        utils = new Utils();
        Utils.myPromocode.clear();
        Utils.promoAPIcall = false;
        session = new Session(this);
        sessionNormal = new com.nebulacompanies.ibo.util.Session(this);
        mAPIInterface = APIClient.getClient(MyLatestCategoryActivity.this).create(APIInterface.class);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        mProgressDialog =findViewById(R.id.progresbar);
        handler = new Handler();
      //  mSwipeRefreshLayout =findViewById(R.id.swipe_refresh_layout);
        categoryDetailss = getIntent();

        if (categoryDetailss != null) {
            categoryIDTwo = categoryDetailss.getIntExtra("selid", 1);
            String jsondata = categoryDetailss.getStringExtra("data");
            selPos = categoryDetailss.getIntExtra("selpos", 0);
            if (!TextUtils.isEmpty(jsondata)) {
                Type collectionType = new TypeToken<ArrayList<Category>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<Category> marr = gson.fromJson(jsondata, collectionType);
                categoryArrayList.addAll(marr);
            }
        }

        filterLogin = new IntentFilter(Utils.actionLogin);
        registerReceiver(myReceiver, filterLogin);
        utilsVersion.checkVersion(this);

    }
    //The BroadcastReceiver that listens for bluetooth broadcasts
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase(Utils.actionLogin)) {
                setSession();
                //callAPI();
            }
        }
    };
    @SuppressLint("SetTextI18n")
    private void setSession() {
      //  if (session.getLogin()) {
           /* if (userName == null || userName.equals("")) {
                getMyProfile();
                Log.d("UserName Fill", "UserName Fill " + userName);
            } else {
                Log.d("UserName Empty", "UserName Empty " + userName);
                tvToolbarTitle.setText("Hi, " + userName);
            }
        } else {
            tvToolbarTitle.setText("Hi, Guest");
        }*/
    }
    private void getMyProfile() {
        mAPIInterfaceProfile = APIClient.getClient(MyLatestCategoryActivity.this).create(APIInterface.class);
        //mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Call<ProfileModelEcom> wsCallingEvents = mAPIInterfaceProfile.getMyProfileEcom();
        wsCallingEvents.enqueue(new Callback<ProfileModelEcom>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ProfileModelEcom> call, @NotNull Response<ProfileModelEcom> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("PROFILEAPI", "PROFILEAPI: " + response);
                        assert response.body() != null;
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("PROFILEAPIResponse", "PROFILEAPIResponse: " + new Gson().toJson(response.body()));
                            String firstName = response.body().getData().getFirstName();
                            userName = firstName;
                           // tvToolbarTitle.setText("Hi, " + userName);
                            sessionNormal.setUserName(firstName);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
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

    private void SavePreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        utils.checkExpireUser(mAPIInterface, this, session);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    private void initDialog() {
        /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Final Result");
        alert.setMessage("msg");
        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }

                });
          */
        dialog_card =  new Dialog(MyLatestCategoryActivity.this);
        // dlgAlert.create();
        // dialog_card = alert.create();
        dialog_card.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_card.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_card.setContentView(R.layout.dialog_sortby);
        // dlgAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // WindowManager.LayoutParams WMLP =
        //dialog_card.getWindow().setGravity(Gravity.TOP);
        Window window = dialog_card.getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.TOP|Gravity.LEFT);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 100;
        params.y = 100;
        window.setAttributes(params);
    }

    //SearchView searchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("Search for Product,Brands...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by default

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                //  Toast.makeText(getApplicationContext(),"textChanged :"+newText,Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**
                Toast.makeText(getApplicationContext(), "searchvalue :" + query, Toast.LENGTH_LONG).show();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setContentInsetStartWithNavigation(0);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Sport"));
        tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        txtSortby = findViewById(R.id.toolbar_title2);
        recproductlist = findViewById(R.id.recyclerview_category_details);
        categoryDetailsAdapter = new CategoryProductDetailsAdapter(this);
        // mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // recproductlist.setLayoutManager(mLayoutManager);
        recproductlist.setLayoutManager(new GridLayoutManager(this, 2));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.horizontal_divider));
        recproductlist.addItemDecoration(itemDecoration);

        DividerItemDecoration itemDecorationH = new DividerItemDecoration(this, LinearLayout.HORIZONTAL);
        itemDecorationH.setDrawable(ContextCompat.getDrawable(this, R.drawable.vertical_divider));
        recproductlist.addItemDecoration(itemDecorationH);
        recproductlist.setItemAnimator(null);
        recproductlist.setAdapter(categoryDetailsAdapter);

        reccategorylist = findViewById(R.id.recyclerview_category);
        categoryNameAdapter = new CategoryNameAdapter(this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reccategorylist.setLayoutManager(mLayoutManager);
        reccategorylist.setItemAnimator(null);
        reccategorylist.setAdapter(categoryNameAdapter);

        txtSortby.setOnClickListener(v -> {
            dialog_card.show();
        });
    }

    public class CategoryProductDetailsAdapter extends RecyclerView.Adapter<CategoryProductDetailsAdapter.MyViewHolder> {
        String img = "https://nebulacompanies.net/Content/Images/EComProducts/Thumbnail/2010212147473799-BODY WASH -1.jpg";
        // String img = "http://203.88.139.169:9064/Content/Images/EComProducts/RootImage/1308210633557967-T-shirt-Full-size-800x1000---1.jpg";
        Context context;

        public CategoryProductDetailsAdapter(Context context) {
            this.context = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public AspectRatioImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                thumbnail = view.findViewById(R.id.thumbnail);
            }
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_item_latest, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Glide.with(context)
                    .load(img)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.skipMemoryCache(true)
                    .dontAnimate()
                    //.placeholder(getRandomDrawbleColor())
                    .into(new SimpleTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(Bitmap arg0, GlideAnimation<? super Bitmap> arg1) {
                            // TODO Auto-generated method stub
                            holder.thumbnail.setImageBitmap(arg0);
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }

    public class CategoryNameAdapter extends RecyclerView.Adapter<CategoryNameAdapter.MyViewHolder> {
        //  String img = "http://203.88.139.169:9064/Content/Images/EcomCategory/1302212310189098-wellness.png";
        String img = "https://nebulacompanies.net/Content/Images/EComProducts/Thumbnail/2010212147473799-BODY WASH -1.jpg";
        // String img = "http://203.88.139.169:9064/Content/Images/EComProducts/RootImage/1308210633557967-T-shirt-Full-size-800x1000---1.jpg";
        Context context;
        int selectedPosition = 0;

        public CategoryNameAdapter(Context context) {
            this.context = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView thumbnail, thumbnailBig;
            public LinearLayout layCard;
            View viewline;
            RelativeLayout relCard;
            // public GifImageView gifImg;
            public MyTextViewEcom txtTitle;

            public MyViewHolder(View view) {
                super(view);
                thumbnail = view.findViewById(R.id.img_category_small);
                thumbnailBig = view.findViewById(R.id.img_category);
                layCard = view.findViewById(R.id.card_view);
                viewline = view.findViewById(R.id.viewline);
                relCard = view.findViewById(R.id.rel_data);
                txtTitle = view.findViewById(R.id.textname);
            }
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list_item_latest, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (selectedPosition == position) {
                holder.thumbnail.setVisibility(View.GONE);
                holder.thumbnailBig.setVisibility(View.VISIBLE);
                holder.viewline.setVisibility(View.VISIBLE);
                holder.txtTitle.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                holder.thumbnail.setVisibility(View.VISIBLE);
                holder.thumbnailBig.setVisibility(View.GONE);
                holder.viewline.setVisibility(View.INVISIBLE);
                holder.txtTitle.setTypeface(Typeface.DEFAULT);

            /* holder.thumbnail.setVisibility(position > 0 ? View.VISIBLE : View.GONE);
                holder.thumbnailBig.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                holder.viewline.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                holder.txtTitle.setTypeface(position == 0 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);*/
            }
            holder.relCard.setOnClickListener(v -> {
                selectedPosition = position;
                notifyDataSetChanged();
            });
          /*  Glide.with(context)
                    .load(img)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.skipMemoryCache(true)
                    .dontAnimate()
                    //.placeholder(getRandomDrawbleColor())
                    .into(holder.thumbnail);
            Glide.with(context)
                    .load(img)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.skipMemoryCache(true)
                    .dontAnimate()
                    //.placeholder(getRandomDrawbleColor())
                    .into(holder.thumbnailBig);*/

        }

        @Override
        public int getItemCount() {
            return 6;//categoryDetails.size();
        }
    }
}

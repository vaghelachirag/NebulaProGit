package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.MyWishListProductsAdapter;
import com.nebulacompanies.ibo.ecom.model.CartModel;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.util.ArrayList;

public class MyWishListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgBackArrow,imgMyCart;
    ImageView imgSearch,imgSearchClose;
    MyButtonEcom btnAddToCart;
    RecyclerView cart_item_recyclerview;
    ArrayList<CartModel> mArrayList = new ArrayList<>();
    private MyWishListProductsAdapter mAdapter;
    RelativeLayout rlSearchView;
    NebulaEditTextEcom editSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish_list);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        //getting the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgBackArrow = (ImageView) findViewById(R.id.img_back);
        imgMyCart = (ImageView) findViewById(R.id.img_my_cart);
        btnAddToCart = (MyButtonEcom) findViewById(R.id.btn_add_to_cart);
        setSupportActionBar(toolbar);

        cart_item_recyclerview = findViewById(R.id.recyclerview_best_deals);
        cart_item_recyclerview.setHasFixedSize(true);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false);
        cart_item_recyclerview.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        cart_item_recyclerview.setItemAnimator(new DefaultItemAnimator());
        mArrayList = new ArrayList<>();
        imgSearch = toolbar.findViewById(R.id.img_search);
        rlSearchView = toolbar.findViewById(R.id.rl_search_view);
        imgSearchClose = (ImageView) toolbar.findViewById(R.id.img_search_close);
        editSearch = (NebulaEditTextEcom)toolbar. findViewById(R.id.editSearch);
        String value = editSearch.getText().toString();
        imgSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value!=null){
                    editSearch.getText().clear();
                }
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlSearchView.setVisibility(View.VISIBLE);
            }
        });

        //adding some items to our list
        mArrayList.add(
                new CartModel(
                        "Protein Shake",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcREjw38DaEUh1-mGqpiuxO_FqjSawvDk3Zhp11HK0iKwoeORZ2h&usqp=CAU"));

        mArrayList.add(
                new CartModel("Whey Gold",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRti_xHr8mxFqIrhm2qA1dk9mU3p8dPrCqGD_YIYABH8Hn8h9Cz&usqp=CAU"));

        mArrayList.add(
                new CartModel("Ensure Milk Shake",
                        "https://scene7.samsclub.com/is/image/samsclub/0007007457269_A"));

        mArrayList.add(
                new CartModel("Hair Care",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQFif0DPrHA1uqp0Xu0s_cv7nLLhP_vL4cRwgam3QKEdCDFTX2y&usqp=CAU"));
        mArrayList.add(
                new CartModel("Whey Us",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ5uTpuCR5AkvE_1VyT9ZdyPMssmDPtT2u3JK1Mti7u94SDO7tG&usqp=CAU"));
        mArrayList.add(
                new CartModel("Protein Isolation",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTmG1TVxt3lupmroRD2ZuMzBGGhfLQ5YvZx3MgJLPEra47fdrGA&usqp=CAU"));


        //creating recyclerview adapter
        mAdapter = new MyWishListProductsAdapter(this, mArrayList);

        //setting adapter to recyclerview
        cart_item_recyclerview.setAdapter(mAdapter);

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        imgMyCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MyWishListActivity.this, MyCartActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login);
            }
        });
    }

}
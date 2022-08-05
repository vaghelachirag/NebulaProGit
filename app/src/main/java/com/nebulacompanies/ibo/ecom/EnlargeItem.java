package com.nebulacompanies.ibo.ecom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.ListProductsAdapter;
import com.nebulacompanies.ibo.ecom.enlarge.ViewPagerAdapter;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;
import com.nebulacompanies.ibo.ecom.utils.HackyViewPager;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.util.ArrayList;

public class EnlargeItem extends AppCompatActivity {

    private HackyViewPager viewPager;
    //private View btnNext, btnPrev;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout thumbnailsContainer;
    RecyclerView rvThumbImage;
    ListProductsAdapter listProductsAdapter;
    ArrayList<BanersListEcom> mArrayList = new ArrayList<>();
    private ArrayList<BanersListEcom> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enlarge_activity);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        getIntentValues();
        initView();
        viewPagerBinding();
       // inflateThumbnails();
    }

    private void viewPagerBinding() {
        // init viewpager adapter and attach
        if (mList.size()>0){
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), mList);
            viewPager.setAdapter(adapter);
            int position =0;
            for (int i = 0; i <mList.size() ; i++) {
                if (mList.get(i).isSelected()){
                    position = i;

                }
            }
            viewPager.setCurrentItem(position);
        }

    }

    private void initView() {
        //find view by id
        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
        thumbnailsContainer = (LinearLayout) findViewById(R.id.container);
        rvThumbImage = (RecyclerView) findViewById(R.id.rv_item_image);
       /* btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));*/

        rvThumbImage.setHasFixedSize(true);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager mLayoutManagerDeal = new LinearLayoutManager(EnlargeItem.this,  LinearLayoutManager.HORIZONTAL, false);
        rvThumbImage.setLayoutManager(mLayoutManagerDeal);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvThumbImage.setItemAnimator(new DefaultItemAnimator());

        if (mList.size()>0){
            //mList.get(0).setSelected(true);

            listProductsAdapter = new ListProductsAdapter(this, mList, new ListProductsAdapter.onItemClick() {
                @Override
                public void onMethodCallbackMain(int position) {
                    if (listProductsAdapter!=null && listProductsAdapter.getItemCount()>0 ){
                        Log.d( "callBackMehod "," callBackMehod(position)"+ position );

                        setMessage(position, "Position: ");

                      /*  if (position > 0) {
                            //next page
                            if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                Log.d( "CHECK POSITION+", "CHECK POSITION+: "+ viewPager.getCurrentItem() );
                            }
                        } else {
                            //previous page
                            if (viewPager.getCurrentItem() > 0) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                Log.d( "CHECK POSITION-", "CHECK POSITION-: "+ viewPager.getCurrentItem() );

                            }
                        }*/
                    }
                }
            });

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    setMessage(position, "onPageScrolled ");
                }

                @Override
                public void onPageSelected(int position) {
                    setMessage(position, "onPageSelected ");
                    if (position == 0) {
                        /*btnPrev.setVisibility(View.INVISIBLE);
                        btnNext.setVisibility(View.VISIBLE);*/

                    } else if ((mList.size() - 1) == position) {
                       /* btnPrev.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.INVISIBLE);*/
                    } else {
                        /*btnPrev.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);*/
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //setting adapter to recyclerview
            rvThumbImage.setAdapter(listProductsAdapter);
            // Send Current Position

        }

        /*btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);
        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));*/
    }

    private void setMessage(int position, String s) {
        //Toast.makeText(EnlargeItem.this, s + position, Toast.LENGTH_SHORT).show();
        Log.d("Data","Position "+ position + s);
        viewPager.setCurrentItem(position);
        listProductsAdapter.callBackMehod(position);
        rvThumbImage.smoothScrollToPosition( position );
    }

    private void getIntentValues() {
        Intent intent=getIntent();
        if(intent!=null){
            mList= (ArrayList<BanersListEcom>) intent.getSerializableExtra("data");
            Log.d("data",""+mList.size());
        }

    }

    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    //next page
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                } else {
                    //previous page
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
            }
        };
    }


    private void inflateThumbnails() {
        for (int i = 0; i < mList.size(); i++) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_image, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.img_thumb);
            ImageView imageViewHolder = (ImageView) imageLayout.findViewById(R.id.img_thumb_visible);
            imageView.setOnClickListener(onChagePageClickListener(i));


            if (mList!=null) {
               /* Glide.with(EnlargeItem.this).load(mList.get(i).getImageFile())
                        .transition( new DrawableTransitionOptions().crossFade() )
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);*/


                Glide.with(getApplicationContext())
                        .load(mList.get(i).getImageFile())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                        // .error(R.drawable.imagenotfound)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                // log exception

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                return false;
                            }
                        })
                        .into(imageView);
            }

            if (mList.get(i).isSelected()){
                imageViewHolder.setVisibility(View.VISIBLE);
            }else {
                imageViewHolder.setVisibility(View.INVISIBLE);

            }
            Log.d("imageResourcePath ","imageResourcePath " + mList.get(i).getImageFile());

            //add imageview
            thumbnailsContainer.addView(imageLayout);
        }
    }

    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(i);

                if (i==0){
                   // btnPrev.setVisibility(View.GONE);
                }else {
                   // btnPrev.setVisibility(View.VISIBLE);
                }
                if (mList.size()>0){
                    for (int j = 0; j <mList.size() ; j++) {
                        if (j==i){
                            mList.get(j).setSelected(true);
                        }else {
                            mList.get(j).setSelected(false);
                        }
                    }
                }

            }

        };
    }
}

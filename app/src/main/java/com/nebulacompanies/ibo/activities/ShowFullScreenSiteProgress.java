package com.nebulacompanies.ibo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Palak Mehta on 9/15/2016.
 */
public class ShowFullScreenSiteProgress extends Activity {

    int id;
    boolean product_type, onBack;
    String name, monthIntext, year;
    int ProjectId, month;
    String PRODUCT_NAME;

    ArrayList<String> imagepic = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    boolean product_type_sub;

    ImageView imageViewBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_view);

        Intent i = getIntent();
        if (i != null) {
            id = i.getExtras().getInt("id");
            // siteProgressImageLists = (ArrayList<SiteProgressImageList>) i.getSerializableExtra("image_list");
            product_type = i.getExtras().getBoolean("product_type");
            PRODUCT_NAME = i.getExtras().getString("PRODUCT_NAME");
            //onBack = i.getExtras().getBoolean("OnBack");
            imagepic = i.getExtras().getStringArrayList("imagepic");
            date = i.getExtras().getStringArrayList("date");
            ProjectId = i.getExtras().getInt("ProjectId");
            month = i.getExtras().getInt("Month");
            monthIntext = i.getExtras().getString("MonthInText");
            year = i.getExtras().getString("Year");
            name = i.getExtras().getString("name");
            product_type_sub = i.getExtras().getBoolean("product_type_sub");
            onBack=i.getExtras().getBoolean("first_time_site_sup");
        }
        ViewPager viewPager =  findViewById(R.id.view_pager_swipe);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(id);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if (onBack) {
            Intent i1 = new Intent(ShowFullScreenSiteProgress.this, ViewSiteProgress.class);
            i1.putExtra("OnBack", onBack);
            i1.putExtra("ProjectId", ProjectId);
            i1.putExtra("Month", month);
            i1.putExtra("Year", year);
            i1.putExtra("MonthInText", monthIntext);
            i1.putExtra("PRODUCT_NAME", PRODUCT_NAME);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i1);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            super.onBackPressed();
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public ImagePagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagepic.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            final TouchImageView imageView =itemView.findViewById(R.id.pager_image);
            // ImageView sharing = (ImageView) itemView.findViewById(R.id.sharing);
            LinearLayout linearLayout =itemView.findViewById(R.id.ln_share);
            //final GifImageView progressBar = (GifImageView) itemView.findViewById(R.id.progressBar1);
            //MyTextView textView = (MyTextView) itemView.findViewById(R.id.pager_text);
            MyTextView dateTextView =  itemView.findViewById(R.id.pager_date);
            //   MyTextView likesTextView = (MyTextView) itemView.findViewById(R.id.tv_likes);
            //imageViewBack=(ImageView)findViewById(R.id.image_back);
          //  RelativeLayout re_like = (RelativeLayout) itemView.findViewById(R.id.rl_social);
            //re_like.setVisibility(View.VISIBLE);

            imageViewBack.setOnClickListener(view -> onBackPressed());

            if (position < imagepic.size()) {
                try {
                    Picasso.with(container.getContext()).load(imagepic.get(position).replaceAll(" ", "%20"))
                            //.skipMemoryCache()
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(imageView, new Callback() {

                                @Override
                                public void onSuccess() {
                                    //progressBar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError() {
                                   // progressBar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                }
                            });
                } catch (Exception ignored) {
                }
                //likesTextView.setText("41");

                linearLayout.setOnClickListener(view -> {
                    linearLayout.setClickable(false);
                    linearLayout.setFocusable(false);
                    Uri bmpUri = getLocalBitmapUri(imageView);
                    if (bmpUri != null) {
                        // Construct a ShareIntent with link to image
                        Intent shareIntent = new Intent();

                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("image/*");
                        // Launch sharing dialog for image
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        linearLayout.setClickable(true);
                        linearLayout.setFocusable(true);
                        startActivity(Intent.createChooser(shareIntent, "Share Image"));
                    } else {
                        Toast.makeText(getApplicationContext(), "sharing failed", Toast.LENGTH_SHORT).show();
                        // ...sharing failed, handle error
                    }
                });

                dateTextView.setText(date.get(position));
                container.addView(itemView);
            }
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((RelativeLayout) object);
        }
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s

            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            if (Build.VERSION.SDK_INT > 24) {
                bmpUri = FileProvider.getUriForFile(ShowFullScreenSiteProgress.this, BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                bmpUri = Uri.fromFile(file);
            }
            Log.i("INFO", "Call for bmpUri:-" + bmpUri);
            //bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) {
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) {
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else {
                view.setAlpha(0);
            }
        }
    }
}
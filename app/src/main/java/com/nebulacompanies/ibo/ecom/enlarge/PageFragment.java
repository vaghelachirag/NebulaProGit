package com.nebulacompanies.ibo.ecom.enlarge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.jsibbold.zoomage.ZoomageView;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;

import java.io.Serializable;

public class PageFragment extends Fragment {

    private BanersListEcom imageResource;
    //private Bitmap bitmap;
   // private BanersListEcom images;

    public static PageFragment getInstance(BanersListEcom banersListEcom) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
       // args.putInt("image_source", resourceID);
        args.putSerializable("image_source", banersListEcom);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            imageResource = (BanersListEcom) getArguments().getSerializable("image_source");
            Log.d("imageResource","imageResource" + imageResource.toString());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // ZoomageView imageView = (ZoomageView) view.findViewById(R.id.image);



        PhotoView imageView = (PhotoView)view. findViewById(R.id.photo_view);
        //imageView.setImageResource(R.drawable.image);
      /*  BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 4;
        o.inDither = false;
        bitmap = BitmapFactory.decodeResource(getResources(), imageResource, o);
        imageView.setImageBitmap(bitmap);*/


      if (imageResource!=null){
          /*Glide.with(getActivity()).load(imageResource.getHdImageFile())
                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                  .into(imageView);*/
          DrawableRequestBuilder<String> thumbnailRequest = Glide
                  .with( getActivity() )
                  .load( imageResource.getImageFile() );

// pass the request as a a parameter to the thumbnail request
          Glide
                  .with( getActivity() )
                  .load( imageResource.getHdImageFile() )
                  .thumbnail( thumbnailRequest )
                  .into( imageView );

         /* Glide.with(getActivity())
                  .load(imageResource.getImageFile())
                  .listener(new RequestListener<String, GlideDrawable>() {
                      @Override
                      public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                          return false;
                      }

                      @Override
                      public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                          Glide.with(getActivity()).load(imageResource.getHdImageFile()).into(imageView);
                          return false;
                      }
                  })
                  .into(imageView);*/


         /* Glide.with(getActivity())
                  .load(imageResource.getImageFile())
                  .asBitmap()
                  .into(new SimpleTarget<Bitmap>() {
                      @Override
                      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                          imageView.setImageBitmap(resource);
                      }
                  });*/

          Log.d("imageResourcePath ","imageResourcePath " + imageResource.getImageFile());
      }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* bitmap.recycle();
        bitmap = null;*/
    }
}

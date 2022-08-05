package com.nebulacompanies.ibo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.adapter.EBCAdapter;
import com.nebulacompanies.ibo.ecom.model.EBCBannerModel;
import com.nebulacompanies.ibo.ecom.model.ECBBannerDetailsModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MainActivityDemo extends AppCompatActivity {
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    private APIInterface mAPIInterface;
    public static ArrayList<ECBBannerDetailsModel> arrayListSiteProducts = new ArrayList<>();
    EBCAdapter ebcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo );
        mAPIInterface = APIClient.getClient( MainActivityDemo.this ).create( APIInterface.class );

        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

       /* ArrayList imageUrlList = prepareData();
        DataAdapterDemo dataAdapterDemo = new DataAdapterDemo(getApplicationContext(), imageUrlList);
        recyclerView.setAdapter( dataAdapterDemo );*/

        getProject( "1" );
    }

    private ArrayList prepareData() {

        String imageUrls[] = {
                "https://i.pinimg.com/736x/7a/14/64/7a146463281f6610fcab4a48e71492d7--fancy-cars-cool-cars.jpg",
                "https://i.pinimg.com/564x/32/cd/dc/32cddc2e3d5fc8b41dae8edbebae48ae--google-search-future-car.jpg",
                "https://images.unsplash.com/photo-1542282088-fe8426682b8f?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max",
                "http://203.88.139.169:9064/Content/Images/EComProducts/RootImage/0509201042112525-Kesar%20kavach_165x225.jpg",
                "http://i2.cdn.turner.com/money/dam/assets/140421122255-2014-ny-auto-show-2015-mclaren-650s-1280x720.jpg",
                "http://thenewswheel.com/wp-content/uploads/2017/03/10-Famous-New-Yorkers-with-Incredibly-Cool-Cars-Feature.jpg",
                "http://203.88.139.169:9064/Content/Images/EComProducts/RootImage/1809201846102844-1509201219064300-download%20(2).png",
                "https://i.pinimg.com/736x/51/22/24/512224b7565c4adf4cd66bba6184bf78--most-expensive-expensive-cars.jpg",
                "http://beverlyhillsmagazine.com/wp-content/uploads/Bentley-Aston-Martin-Dream-Cars-Maybach-Doge-Charger-Cool-Cars-Race-Car-Magazine-VIP-Style-cars-Supercars-Beverly-Hills-Magazine-3-e1492551913276.jpg",
                "http://203.88.139.169:9064/Content/Images/EComProducts/RootImage/1509201219420077-download%20(1).png",
                "https://s.aolcdn.com/dims-global/dims3/GLOB/legacy_thumbnail/916x515/quality/95/https://s.blogcdn.com/slideshows/images/slides/400/662/5/S4006625/slug/l/01-2017-chevrolet-corvette-gs-fd-1-1.jpg",
                "http://203.88.139.169:9064/Content/Images/EComProducts/RootImage/0509201041383375-Medley%20of%20Berries_165x225.jpg"};

        ArrayList imageUrlList = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            ImageUrlDemo imageUrl = new ImageUrlDemo();
            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(imageUrl);
        }
        Log.d("MainActivityDemo", "List count: " + imageUrlList.size());
        return imageUrlList;
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*ArrayList imageUrlList = prepareData();
        DataAdapterDemo dataAdapterDemo = new DataAdapterDemo(getApplicationContext(), imageUrlList);
        recyclerView.setAdapter( dataAdapterDemo );*/
        //getProject( "1" );
    }

    private void getProject(String idEBC) {
      //  if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<EBCBannerModel> wsCallingSiteProducts = mAPIInterface.getSiteProductList(idEBC);
            wsCallingSiteProducts.enqueue(new Callback<EBCBannerModel>() {
                @Override
                public void onResponse(Call<EBCBannerModel> call, Response<EBCBannerModel> response) {
                    arrayListSiteProducts.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatuscode()== REQUEST_STATUS_CODE_NO_RECORDS) {
                            } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListSiteProducts.addAll(response.body().getData());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                ebcAdapter = new EBCAdapter( MainActivityDemo.this, arrayListSiteProducts);
                                recyclerView.setAdapter(ebcAdapter);
                                android.util.Log.e( "EBC", "EBC: " + new Gson().toJson( response.body() ) );
                            } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) { }
                        }
                    } else { }
                }

                @Override
                public void onFailure(Call<EBCBannerModel> call, Throwable t) { }
            });
      /*  } else {
        }*/
    }
}

package com.nebulacompanies.ibo.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.SessionVacation;
import org.apache.http.protocol.HTTP;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.nebulacompanies.ibo.util.Config.TESTING_API;

/**
 * Class : APIClient
 * Details:
 * Create by : Palak Mehta At Nebula Infra space LLP 09-01-2018
 * Modification by :
 */
public class APIClient {

    private static final String TAG = "APIClient";
    private static Retrofit retrofit = null;
    private static Context context;
    private static Session session;
    private static OkHttpClient okHttpClient;
   // private static SessionVacation sessionVacation;

    public static Retrofit getClient(final Context mContext) {

        context = mContext;
        session = new Session(mContext);
      //  sessionVacation = new SessionVacation(mContext);

        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        //  Cache cache = new Cache(mContext.getCacheDir(), cacheSize);
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (session.getLogin()) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.MINUTES)
                    .addInterceptor(chain -> {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Authorization", session.getAccessToken());
                        return chain.proceed(ongoing.build());
                    })
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.MINUTES)
                    .cache(cache)
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.MINUTES)
                   /* .addInterceptor(new Interceptor() {
                        @Override
                    public Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder();
                            ongoing.addHeader("Authorization", sessionVacation.getAccessTokenHoliday());
                            return chain.proceed(ongoing.build());
                        }
                    })*/
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.MINUTES)
                    .cache(cache)
                    .build();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(TESTING_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");

            if (AppUtils.isOnline(context)) {
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") || cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 10)
                            .build();
                } else {
                    int maxAge = 60; // read from cache for 1 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                }
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    };


    public static Retrofit getClientUniCommmerce(final Context mContext) {

        context = mContext;


        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        //  Cache cache = new Cache(mContext.getCacheDir(), cacheSize);
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        //  ongoing.addHeader("Content-Type", "application/json");
                        ongoing.addHeader( HTTP.CONTENT_TYPE,
                                "application/json;charset=UTF-8");

                        SharedPreferences prefs = mContext.getSharedPreferences(PrefUtils.prefFacility, MODE_PRIVATE);
                        String getFacility = prefs.getString("getFacility", "No name defined");//"No name defined" is the default value.

                        Log.e( "getFacilityHeader: ","getFacilityHeader: "+getFacility );
                       // ongoing.addHeader("Authorization", "bearer f06535ee-c3be-41e2-9f5b-7066995839bd");
                        ongoing.addHeader("Authorization", session.getAccessTokenUnicommerce());
                       // ongoing.addHeader("Facility", "Hyd_001");
                        ongoing.addHeader("Facility", getFacility);
                        return chain.proceed(ongoing.build());
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.MINUTES)
                .cache(cache)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(TESTING_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    private static final Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (AppUtils.isOnline(context)) {
                Log.d(TAG, "Rewriting Request");
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return chain.proceed(request);
        }
    };


    // Custom Authorization Token Access.

    public static Retrofit getClientSite(final Context mContext) {

        context = mContext;
        session = new Session(mContext);

        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        //  Cache cache = new Cache(mContext.getCacheDir(), cacheSize);
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.MINUTES)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://neb.hksolutions.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

}

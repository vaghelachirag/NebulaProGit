package com.nebulacompanies.ibo;

import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.location.GoogleApiHelper;
import com.nebulacompanies.ibo.model.Amenities;
import com.nebulacompanies.ibo.sms.AppSignatureHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.nebulacompanies.ibo.util.AppUtils.amenitiesArrayList;
import static com.nebulacompanies.ibo.util.AppUtils.getHolidayCharges;


/**
 * Created by Palak Mehta on 5/14/2016.
 */
public class IBOBackOffice extends MultiDexApplication {

    private String TAG = getClass().getSimpleName();

    private GoogleApiHelper googleApiHelper;
    private static IBOBackOffice mInstance;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final Integer[] images = {R.drawable.ic_vector_business_center,
            R.drawable.ic_vector_hour_fron_desk,
            R.drawable.ic_vector_ac_unit,
            R.drawable.ic_vector_house_keeping,
            R.drawable.ic_vector_front_desk_safe,
            R.drawable.ic_vector_laundry_service,
            R.drawable.ic_vector_luggage_storage,
            R.drawable.ic_vector_smoking_room};

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mInstance = this;
        googleApiHelper = new GoogleApiHelper(mInstance);
        Fresco.initialize(this);
        createAmenitiesList();
        Gson gson = new Gson();
        /*IndianStateCity objectJson = gson.fromJson(readJSONFromAsset(), IndianStateCity.class);
        arrayListStateCity.clear();
        arrayListStateCity.addAll(objectJson.getArray());

        List<String> arrayListCity = new ArrayList<>();
        for (IndianStateCityDetails object : arrayListStateCity) {
            arrayListCity.add(object.getName());
        }
        Log.e(TAG, "Array list : " + arrayListCity.size());
        arrayCity = new String[arrayListCity.size()];
        arrayCity = arrayListCity.toArray(arrayCity);*/
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();



        //getHolidayCharges(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized IBOBackOffice getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

    /*public void showToast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }*/

    /**
     * This Method display more amenities dialog.
     */
    private void createAmenitiesList() {
        amenitiesArrayList.clear();
        for (int i = 0; i < getResources().getIntArray(R.array.array_amenities_images).length; i++) {

            Amenities object = new Amenities();
            object.setAmenitiesName(getResources().getStringArray(R.array.array_amenities)[i]);
            object.setDrawableId(images[i]);
            // object.setDrawableId(getResources().getIntArray(R.array.array_amenities_images)[i]);
            amenitiesArrayList.add(object);
        }
    }

    /**
     * This method for convert json file into json object.
     *
     * @return
     */
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

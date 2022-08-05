package com.nebulacompanies.nebula.CustomerBooking.Interface;

import android.net.Uri;

/**
 * Created by Nebula-1 on 10/2/2017.
 */

/**
 * Class : CropSelection
 * Details: An interface for crop images.
 * Create by : Jadav Chirag At NebulaApplication Infraspace LLP 10/2/2017.
 * Modification by :
 */

public interface CropSelection {

    /**
     * This abstract method for getting cropped image URI.
     *
     * @param sourceUri
     */
    public void getCropImage(Uri sourceUri);

    /**
     * This abstract method for checking contact list details.
     *
     * @param applicantType
     */
    public void getContactList(int applicantType);
}

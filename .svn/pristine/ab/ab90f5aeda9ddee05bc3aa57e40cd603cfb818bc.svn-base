package com.nebulacompanies.ibo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nebulacompanies.ibo.ecom.utils.PrefUtils;

/**
 * Class : UserAuthorization
 * Details: This Class for User Authorization & Token Access.
 * Create by : Jadav Chirag At NebulaApplication Infraspace LLP 23-11-2017
 * Modification by :
 */

public class UserAuthorization {

    private static SharedPreferences sharedpreferences;

    private final static String PRE_TOKEN = "ACCESS_TOKEN";
    private final static String PRE_AUTHORIZATION_DETAILS = "AUTHORIZATION_DETAILS";
    private final static String PRE_IS_LOGIN = "IS_LOGIN";
    private final static String PRE_FCM_TOKEN = "FCM_TOKEN";
    private final static String CUSTOMER_KEY_ID = "CUSTOMER_KEY_ID";

    public UserAuthorization(Context mContext) {
        //  if(sharedpreferences!=null)
        sharedpreferences = mContext.getSharedPreferences(PrefUtils.prefCustomerbook, Context.MODE_PRIVATE);
    }

    /**
     * This Method get Access Token
     *
     * @return the authorization token.
     */
    public static String getAuthorizationToken() {
        return sharedpreferences.getString(PRE_TOKEN, "None");
    }

    public static void setAuthorizationToken(String token) {
        sharedpreferences.edit().putString(PRE_TOKEN, token).apply();
    }
    public static String getCustomerKeyId() {
        return sharedpreferences.getString(CUSTOMER_KEY_ID, "None");
    }

    public static void SetCustomerKeyId(String customerKeyId) {
        sharedpreferences.edit().putString(CUSTOMER_KEY_ID, customerKeyId).apply();
    }
    /**
     * This method for set the bunch of user details string
     *
     * @param userAuthorization
     */
    public static void setUserAuthorization(String userAuthorization) {
        sharedpreferences.edit().putString(PRE_AUTHORIZATION_DETAILS, userAuthorization).apply();
    }

    /**
     * This method for store user credential for re use.
     *
     * @param username The User name
     * @param pwd      The Encoded Password.
     */
    public static void setUserCredential(String username, String pwd, boolean isLogin) {
        sharedpreferences.edit().putString(Const.WEB_SERVICES_PARAM.KEY_USER_NAME, username).apply();
        sharedpreferences.edit().putString(Const.WEB_SERVICES_PARAM.KEY_PASSWORD, pwd).apply();
        sharedpreferences.edit().putBoolean(PRE_IS_LOGIN, isLogin).apply();
    }

    /**
     * This method get for
     *
     * @return True id user has login else return false.
     */
    public static boolean getUserLogin() {
        return sharedpreferences.getBoolean(PRE_IS_LOGIN, false);
    }

    /**
     * This method for getting the user name
     *
     * @return The user name.
     */
    public static String getUserName() {
        return sharedpreferences.getString(Const.WEB_SERVICES_PARAM.KEY_USER_NAME, "None");
    }

    /**
     * This method for getting the user pwd.
     *
     * @return The password.
     */
    public static String getPassword() {
        return sharedpreferences.getString(Const.WEB_SERVICES_PARAM.KEY_PASSWORD, "None");
    }



}

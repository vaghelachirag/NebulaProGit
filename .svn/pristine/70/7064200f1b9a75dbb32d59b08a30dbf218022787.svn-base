package com.nebulacompanies.ibo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nebulacompanies.ibo.ecom.utils.PrefUtils;

/**
 * Created by Palak Mehta on 10/19/2016.
 */

public class Session {

    private SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String LOGIN_ID = "idKey";
    public static final String Login = "false";
    public static final String Logout = "false";
    public static final String Loader = "Loader";
    public static final String USER_NAME = "nameKey";
    //public static final String PASSWORD = "passwordKey";
    public static final String USER_DATE = "dateKey";
    private final static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private final static String REFRESH_TOKEN = "REFRESH_TOKEN";
    private final static String ACCESS_TOKEN_UNICOMMERCE = "ACCESS_TOKEN_UNICOMMERCE";
    private final static String IBO_KEY_ID = "IBO_KEY_ID";
    private final static String IBO_KEY_ID_NEW = "USER_KEY_ID";
    private final static String IBO_REF_ID = "IBO_REF_ID";
    private final static String IBO_ROLE="IBO_ROLE";
    private final static String IBO_RANK="IBO_RANK_MY";
    public Session(Context context) {
        // TODO Auto-generated constructor stub
        sharedpreferences = context.getSharedPreferences(PrefUtils.prefMyprefs, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void setLoginID(String loginID) {
        editor.putString(LOGIN_ID, loginID);
        editor.commit();
    }

    public String getLoginID() {
        String login_id = sharedpreferences.getString(LOGIN_ID, "");
        return login_id;
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public String getUserName() {
        String user_name = sharedpreferences.getString(USER_NAME, "");
        return user_name;
    }


    public void setLogin(Boolean aBoolean) {
        editor.putBoolean(Login, aBoolean);
        editor.commit();
    }

    public Boolean getLogin() {
        Boolean login = sharedpreferences.getBoolean(Login, false);
        return login;
    }

    public void setLogout(Boolean bBoolean) {
        editor.putBoolean(Logout, bBoolean);
        editor.commit();
    }

    public Boolean getLoader() {
        Boolean logout = sharedpreferences.getBoolean(Loader, true);
        return logout;
    }

    public void setLoader(Boolean bBoolean) {
        editor.putBoolean(Loader, bBoolean);
        editor.commit();
    }

    public Boolean getLogout() {
        Boolean logout = sharedpreferences.getBoolean(Logout, false);
        return logout;
    }
    public void setIboKeyId(String ibokeyid) {
        editor.putString(IBO_KEY_ID, ibokeyid);
        editor.commit();
    }

    public String getIboKeyId() {
        String ibo_key_id = sharedpreferences.getString(IBO_KEY_ID, "");
        return ibo_key_id;
    }

  /* public void setIboKeyIdNew(String ibokeyid) {
        editor.putString(IBO_KEY_ID_NEW, ibokeyid);
        editor.commit();
    }

    public String getIboKeyIdNew() {
        String ibo_key_id = sharedpreferences.getString(IBO_KEY_ID_NEW, "");
        return ibo_key_id;
    }*/
    public void setUserCredential(String username, String pwd) {
        sharedpreferences.edit().putString(Constants.WEB_SERVICES_PARAM.KEY_USER_NAME, username).commit();
        sharedpreferences.edit().putString(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD, pwd).commit();
    }

    public void setIboDate(String date) {
        editor.putString(USER_DATE, date);
        editor.commit();
    }

    public String getIboDate() {
        String ibo_date = sharedpreferences.getString(USER_DATE, "");
        return ibo_date;
    }
    public void setRefId(String iborefid) {
        editor.putString(IBO_REF_ID, iborefid);
        editor.commit();
    }

    public String getRefId() {
        String ibo_key_id = sharedpreferences.getString(IBO_REF_ID, "");
        return ibo_key_id;
    }

    public void setRole(String role) {
        editor.putString(IBO_ROLE, role);
        editor.commit();
    }

    public String getRole() {
        String ibo_key_id = sharedpreferences.getString(IBO_ROLE, "");
        return ibo_key_id;
    }
    /**
     * This method get for
     *
     * @return
     */
    /*public boolean getUserLogin() {
        //return sharedpreferences.getBoolean(IS_LOGIN, false);
    }*/

    /**
     * This Method get Access Token
     *
     * @return the authorization token.
     */
    public String getAccessToken() {
        return sharedpreferences.getString(ACCESS_TOKEN, "None");
    }

    public void setAccessToken(String token) {
        sharedpreferences.edit().putString(ACCESS_TOKEN, token).commit();
    }

    public String getAccessTokenUnicommerce() {
        return sharedpreferences.getString(ACCESS_TOKEN_UNICOMMERCE, "None");
    }

    public void setAccessTokenUnicommerce(String tokenUniCommerce) {
        sharedpreferences.edit().putString(ACCESS_TOKEN_UNICOMMERCE, tokenUniCommerce).commit();
    }


    /**
     * This Method get Access Token
     *
     * @return the authorization token.
     */
    public String getRefreshToken() {
        return sharedpreferences.getString(REFRESH_TOKEN, "None");
    }

    public void setRefreshToken(String refreshToken) {
        sharedpreferences.edit().putString(REFRESH_TOKEN, refreshToken).commit();
    }

    public void clear() {
        /*editor.remove(LOGIN_ID).commit();
        editor.remove(ACCESS_TOKEN).commit();
        editor.remove(REFRESH_TOKEN).commit();
        editor.remove(IBO_KEY_ID).commit();
        editor.remove(USER_DATE).commit();
        editor.remove(Constants.WEB_SERVICES_PARAM.KEY_USER_NAME).commit();
        editor.remove(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD).commit();*/
        //editor.clear();
        editor.clear().apply();

    }
    // Added By Jadav Chirag Holiday User Login Details


}
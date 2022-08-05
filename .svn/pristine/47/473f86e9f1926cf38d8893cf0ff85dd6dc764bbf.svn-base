package com.nebulacompanies.ibo.ecom.utils;

import android.text.Editable;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Palak Mehta on 9/5/2017.
 */

public class Validations {

    public static boolean isValidID(String id) {
        String NAME_PATTERN = "[0-9 ]*";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    public static boolean isValidname(String name) {
        String NAME_PATTERN = "[a-zA-Z ]*";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidcity(String city) {
        String CITY_PATTERN = "[a-zA-Z]*";
        Pattern pattern = Pattern.compile(CITY_PATTERN);
        Matcher matcher = pattern.matcher(city);
        return matcher.matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.length() == 10;
    }

    public static boolean isValidEmgPhone(String phone) {
        return phone != null && phone.length() <= 15;
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    static boolean inputValidation(Editable inputText) {
        return inputText.toString().isEmpty();
    }
    public static boolean isValidPhoneNumber(String phone)
    {
        if (!phone.trim().equals("") || phone.length() > 10)
        {
            return Patterns.PHONE.matcher(phone).matches();
        }

        return false;
    }

    public static boolean isValidMobile(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }


    public static boolean isIfscCodeValid(String IfscCode)
    {
        String regExp = "^[A-Z]{4}[0][A-Z0-9]{6}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(IfscCode);
        return matcher.matches();
    }
}

package com.novo.fidelicia.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by saurabh on 8/16/2017.
 */

public class SharedPreference {
    public static final String PREFS_INSTANCE_ID = "AOP_PREFS_INSTANCE";
    public static final String PREFS_DEVICE_ID = "AOP_PREFS_DEVICE";
    public static final String PREFS_FIRST_TIME = "AOP_PREFS_FIRST_TIME";
    public static final String PREFS_KEY_EMP = "AOP_PREFS_OPERATOR";
    public static final String PREF_KEY_PRODID = "AOP_PREF_EMPID";
    public static final String PREF_KEY_LOGIN_TOKEN = "AOP_PREF_LOGIN_TOKEN";
    public static final String PREF_KEY_USER_NAME = "AOP_PREF_USER_NAME";
    public static final String PREF_KEY_EMPPIC = "AOP_PREF_EMPPIC";
    public static final String PREF_USER_ID="AOP_PREFS_USER";
    public static final String PREF_KEY_CARD_NUMBER = "AOP_PREF_CARD_NUMBER";
    public static final String PREF_KEY_MIBARCODE_NUMBER = "AOP_PREF_MIBARCODE_NUMBER";

    public SharedPreference() {
        super();
    }
    public void saveInstanceId(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_INSTANCE_ID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getInstanceId(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_INSTANCE_ID, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }
    public void saveDeviceId(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_DEVICE_ID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getDeviceId(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_DEVICE_ID, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void saveFirstTime(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_FIRST_TIME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getFirstTime(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_FIRST_TIME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void saveProdId(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_PRODID, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getProdId(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_KEY_PRODID, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void saveLoginToken(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_LOGIN_TOKEN, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getLoginToken(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_KEY_LOGIN_TOKEN, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void clearLoginToken(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_LOGIN_TOKEN, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }
    public void saveUserName(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_USER_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getUserName(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_KEY_USER_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }
    public void saveEmpPic(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_EMPPIC, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getEmpPic(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_KEY_EMPPIC, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public String getUserId(Context context){
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_USER_ID, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void saveCardNumber(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_CARD_NUMBER, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getCardNumber(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_KEY_CARD_NUMBER, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void clearCardNumber(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_KEY_CARD_NUMBER, Context.MODE_PRIVATE);

        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void saveMiBarcodeNumber(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREF_KEY_MIBARCODE_NUMBER, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY_EMP, text);
        editor.commit();
    }

    public String getMiBarcodeNumber(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREF_KEY_MIBARCODE_NUMBER, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_EMP, null);
        return text;
    }

    public void clearMiBarcodeNumber(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREF_KEY_MIBARCODE_NUMBER, Context.MODE_PRIVATE);

        editor = settings.edit();

        editor.clear();
        editor.commit();
    }
}

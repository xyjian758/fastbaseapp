package app.qingtingfm.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import app.qingtingfm.com.api.Const;
import app.qingtingfm.com.application.AndroidApplication;

/**
 * @author Alex
 * @version V2.1.0
 * @Title: ${}
 * @Description: ${todo}()
 * @date ${date} 下午4:17
 */
public class AbSharedUtil {

    private static final String SHARED_PATH = Const.SP_NAME;

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
    }

    public static void putInt(String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static int getInt(String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        return sharedPreferences.getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void putFloat(String key, float value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    public static float getFloat(String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        return sharedPreferences.getFloat(key, 0);
    }


    //不需要context
    public static String getString(String key) {
        String mValue = "";
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        mValue = sharedPreferences.getString(key, "");
        if (!TextUtils.isEmpty(mValue)) {
            return mValue;
        }
        return "";
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        return sharedPreferences.getBoolean(key, defValue);
    }


    public static void putLong(String key, long value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public static long getLong(String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(AndroidApplication.getAppContext());
        return sharedPreferences.getLong(key, 0);
    }
}

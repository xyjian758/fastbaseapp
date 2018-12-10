package app.qingtingfm.com.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Base64;
import android.webkit.WebSettings;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.qingtingfm.com.BuildConfig;
import app.qingtingfm.com.R;
import app.qingtingfm.com.application.AndroidApplication;


public class AppUtil {
    /**
     * 退出时间
     */
    private static long mExitTime;

    /**
     * 退出间隔
     */
    private static final int EXIT_INTERVAL_TIME = 2000;

    //获取应用签名
    public static String getSignature(PackageManager manager) {
        // 网络获取用户信息
        /* 包签名 start */
        try {
            //BuildConfig.APPLICATION_ID   当前应用包名
            PackageInfo packageInfo = manager.getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            String signValidString = getSignValidString(packageInfo.signatures[0].toByteArray());
            return signValidString;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String getSignValidString(byte[] paramArrayOfByte) throws NoSuchAlgorithmException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("SHA");
        localMessageDigest.update(paramArrayOfByte);
        return Base64.encodeToString(localMessageDigest.digest(), Base64.DEFAULT);
    }

    /**
     * 判断两次返回时间间隔,小于两秒则退出程序
     */
    public static void exit(Context context) {
        if (context != null) {
            if ((System.currentTimeMillis() - mExitTime) > EXIT_INTERVAL_TIME) {
                Toast.makeText(context, ResourceUtil.getString(R.string.agin_press), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                context.startActivity(homeIntent);
//                if(context instanceof Activity){
//                    ((Activity) context).moveTaskToBack(true);
//                }
            }
        }

    }


    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(AndroidApplication.getAppContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}

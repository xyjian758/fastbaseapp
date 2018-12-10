package app.qingtingfm.com.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

/**
 * @author Xyjian
 * @version: 1.8.2
 * @Description: TODO(){控制是否输出日志}
 * @date: 2017/10/26 13:38
 */
public class LogJoneUtil {
    private static final boolean IS_LOG = true;//是否需要输出日志
    private static final String TAG = "tag";//默认的tag  可以统一变更

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 d等级的日志，tag为当前默认值}
     * @date: 2017/10/26 13:47
     */
    public static void d(String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.d(TAG, msg);
        }
    }

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 d等级的日志，tag为自定义}
     * @date: 2017/10/26 13:48
     */
    public static void d(String tag, String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 e等级的日志，tag为当前默认值}
     * @date: 2017/10/26 13:47
     */
    public static void e(String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.e(TAG, msg);
        }
    }

    // 打印对象
    public static void e(Object object) {

        if (IS_LOG && object != null) {
            Gson gson = new Gson();
            String s = gson.toJson(object);
            if (!TextUtils.isEmpty(s)) {
                Log.e(TAG, s);
            }
        }

    }

    // 打印对象
    public static void e(String tag, Object object) {

        if (IS_LOG && object != null) {
            Gson gson = new Gson();
            String s = gson.toJson(object);
            if (!TextUtils.isEmpty(s)) {
                Log.e(tag, s);
            }
        }

    }


    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 e等级的日志，tag为自定义}
     * @date: 2017/10/26 13:48
     */
    public static void e(String tag, String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 w等级的日志，tag为当前默认值}
     * @date: 2017/10/26 13:47
     */
    public static void w(String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.w(TAG, msg);
        }
    }

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 w等级的日志，tag为自定义}
     * @date: 2017/10/26 13:48
     */
    public static void w(String tag, String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 i等级的日志，tag为当前默认值}
     * @date: 2017/10/26 13:47
     */
    public static void i(String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.i(TAG, msg);
        }
    }

    /**
     * @author Xyjian
     * @version: 1.8.2
     * @Description: TODO(){输出 i等级的日志，tag为自定义}
     * @date: 2017/10/26 13:48
     */
    public static void i(String tag, String msg) {
        if (IS_LOG && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }
}

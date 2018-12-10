package app.qingtingfm.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Iterator;
import java.util.Stack;


/**
 * Created by zcs on 2018/3/30.
 */

public class ActivityUtil {
    private static String TAG = "ActivityUtil";
    private static Stack<Activity> activityStack;
    private static ActivityUtil activityUtil;

    private ActivityUtil() {
    }

    public static synchronized ActivityUtil getInstance() {
        if (activityUtil == null) {
            activityUtil = new ActivityUtil();
        }
        return activityUtil;
    }

    //页面跳转
    public void onNext(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public void onNextClearTop(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    // 跳转  传值
    public void onNext(Context context, Class clazz, String extraKey, String extryValue) {

        context.startActivity(new Intent(context, clazz).putExtra(extraKey, extryValue));

    }

    public void onNext(Context context, Class clazz, String extraKey, int extryValue) {
        context.startActivity(new Intent(context, clazz).putExtra(extraKey, extryValue));
    }

    public void onNext(Context context, Class clazz, String extraKey, boolean extryValue) {
        context.startActivity(new Intent(context, clazz).putExtra(extraKey, extryValue));
    }


    //跳转 主页
//    public void goMainFragment(Context context, Class clazz, String index) {
//        Intent intent = new Intent(context, clazz);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("toMainActivityFrom", index);
//        context.startActivity(intent);
//
//    }


    //打电话
//    public void gotoCall(Context context) {
//
//        String phone = context.getString(R.string.tv_mobile_num);
//
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//
//
//    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        LogJoneUtil.d(TAG + "<addActivity>" + activityStack.size());
    }


    /**
     * 添加Activity到堆栈
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        activityStack.remove(activity);
        LogJoneUtil.d(TAG + "<removeActivity>" + activityStack.size());
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null) {
                if (activity.getClass().equals(cls)) {
                    iterator.remove();
                    activity.finish();
                    activity = null;
                    LogJoneUtil.d(TAG + "<finishActivity>" + activityStack.size());
                }

            }
        }
    }
}

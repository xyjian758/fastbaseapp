package app.qingtingfm.com.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by elu on 2018/4/27.
 * /**
 * 用来管理手机状态栏一系列操作，主要是就Window类的使用
 */

public class StatusBarUtil {

    /**
     * 修改状态栏为全透明 ，并全屏
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        // 在Activity布局的根节点处加上android:fitsSystemWindows=”true”属性就可以了，
        // 要不布局会跑到状态栏和导航栏下面，与导航栏和状态栏重叠，这当然不是我们希望的。

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //api21新增接口
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);


            // Android 设置应用的底部导航栏(虚拟按键)背景颜色
            if (checkDeviceHasNavigationBar(activity)) {
//                window.setNavigationBarColor(activity.getResources().getColor(R.color.black));
            }


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int statusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().
                        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void statusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    public static void statusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //设置布局距离状态栏高度
    public static void setLayoutPadding(Activity activity, View contentLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            contentLayout.setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                    contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }
    }

    //一个Acitivity的根布局，根据是否有NavigationBa,不光padding上面的，也padding下面的
    public static void setRootLayoutPadding(Activity activity, View contentLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int navigationBarHeight = 0;
            // Android 设置应用的底部导航栏(虚拟按键)背景颜色
            if (checkDeviceHasNavigationBar(activity)) {
                navigationBarHeight = getNavigationBarHeight(activity);
            }
            contentLayout.setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                    contentLayout.getPaddingRight(), contentLayout.getPaddingBottom() + navigationBarHeight);
        }
    }

    //一个Acitivity的根布局，根据是否有NavigationBa,不光padding上面的，也padding下面的
    public static void setRootLayoutBottomPadding(Activity activity, View contentLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int navigationBarHeight = 0;
            // Android 设置应用的底部导航栏(虚拟按键)背景颜色
            if (checkDeviceHasNavigationBar(activity)) {
                navigationBarHeight = getNavigationBarHeight(activity);
            }
            contentLayout.setPadding(contentLayout.getPaddingLeft(), contentLayout.getPaddingTop(),
                    contentLayout.getPaddingRight(), contentLayout.getPaddingBottom() + navigationBarHeight);
        }
    }

    /**
     * 0  :方法同 statusBarLightMode
     *
     * @param change     是否变更 状态栏文字颜色,全屏
     * @param changeType 变更类型 0：深色（黑色）， 1：正常的颜色（白色）
     */
    public static void changeStatuBarTextImageColor(Activity activity, boolean change, int changeType) {
        if (change && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (changeType == 0) {
                //android6.0以后可以对状态栏文字颜色和图标进行修改
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            } else if (changeType == 1) {
                //android6.0以后可以对状态栏文字颜色和图标进行修改
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }

        }

    }

    /**
     * 首先要确认 已经用了沉浸式了  对部分有虚拟导航栏的进行处理
     * <p>
     * 暂时不用这个 用setLayoutPadding 也够了
     *
     * @param activity
     */
    public static void setNavigationBarNormal(Activity activity) {
        if (checkDeviceHasNavigationBar(activity)) {
            activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, getNavigationBarHeight(activity));
        }

    }

    //判断是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {

        if (context != null) {
            if (context instanceof Activity) {

                if (OSHelper.isMIUI()) {
                    boolean force_fsg_nav_bar = Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
                    //true 是手势，默认是 false
                    return !force_fsg_nav_bar;//是否 有虚拟键盘
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    Point realSize = new Point();
                    display.getSize(size);
                    display.getRealSize(realSize);
                    return realSize.y != size.y;
                } else {
                    boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
                    boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                    return !menu && !back;
                }
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * 获取底部导航栏高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int navigationHeight = resources.getDimensionPixelSize(resourceId);
        return navigationHeight;
    }

}

package app.qingtingfm.com.util;

/**
 * anther: created by zuochunsheng on 2018/11/2 14 : 19
 * descript : 判断是否连续点击
 */
public class PreventMultiClick {

    private static long lastClickTime;

    /*
     * 判断点击间隔，不能小于规定的间隔
     * */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

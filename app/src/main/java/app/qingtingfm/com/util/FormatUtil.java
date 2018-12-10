package app.qingtingfm.com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * anther: created by zuochunsheng on 2018/11/4 14 : 24
 * descript :
 */
public class FormatUtil {

    /**
     * 检查邮箱是否合法
     */
    public static Boolean checkEmail(String email) {
        String format = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";// 邮箱 格式

        return email.matches(format);
    }

    //格式化时间日期
    public static String formatMillis(long millis) {
        return formatMillis(millis, -1);
    }

    //格式化时间日期
    public static String formatMillis(long millis, int type) {
        DateFormat dateFormat = null;
        switch (type) {
            case 0:
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                break;
            default:
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
        }

        Date date = new Date(millis);
        return dateFormat.format(date);
    }

}

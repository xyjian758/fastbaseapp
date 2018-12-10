package app.qingtingfm.com.util;

import android.annotation.SuppressLint;

import java.util.Locale;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 6:07 PM
 * Desc: TimeUtils
 */
public class TimeUtils {

    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     *
     * @param duration The time needs to be parsed.
     */
    @SuppressLint("DefaultLocale")
    public static String formatDuration(long duration) {
        return formatDuration(duration, 0);
    }

    public static String formatDuration(long duration, int type) {
        duration /= 1000; // milliseconds into seconds
        long minute = duration / 60;
        long hour = minute / 60;
        minute %= 60;
        long second = duration % 60;
        if (type == 0) {
            if (hour != 0)
                return String.format(Locale.CHINA, "%02d hrs %02d mins", hour, minute, second);
            else
                return String.format(Locale.CHINA, "00 hrs %02d mins", minute, second);
        } else if (type == 1) {
            if (hour != 0)
                return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second);
            else
                return String.format(Locale.CHINA, "%02d:%02d", minute, second);
        } else if (type == 2) {
            return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second);
        } else {
            if (hour != 0)
                return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second);
            else
                return String.format(Locale.CHINA, "%02d:%02d", minute, second);
        }

    }

}

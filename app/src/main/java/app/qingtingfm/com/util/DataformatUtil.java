package app.qingtingfm.com.util;

import java.text.DecimalFormat;

/**
 * @author xyjian
 * @version V1.0.0
 * @Description: {数据格式化工具  # 有则保留无则不显示  0 强制保留}
 * @date 2018/9/6
 */
public class DataformatUtil {


    public static String decimalFormat_2(double formNumber) {
        return new DecimalFormat("#.##").format(formNumber);
    }

    public static String decimalFormat_2(long formNumber) {
        return new DecimalFormat("#.##").format(formNumber);
    }

    public static String decimalFormat_2(String formNumber) {
        return new DecimalFormat("#.##").format(stringToDouble(formNumber));
    }

    public static String decimalFormat2(double formNumber) {
        return new DecimalFormat("#.00").format(formNumber);
    }

    public static String decimalFormat2(long formNumber) {
        return new DecimalFormat("#.00").format(formNumber);
    }

    public static String decimalFormat2(String formNumber) {
        return new DecimalFormat("#.00").format(stringToDouble(formNumber));
    }

    public static String decimalFormat_4(double formNumber) {
        return new DecimalFormat("#.####").format(formNumber);
    }

    public static String decimalFormat_4(long formNumber) {
        return new DecimalFormat("#.####").format(formNumber);
    }

    public static String decimalFormat_4(String formNumber) {
        return new DecimalFormat("#.####").format(stringToDouble(formNumber));
    }


    public static String decimalFormat8(double formNumber) {
        return new DecimalFormat("#.00000000").format(formNumber);
    }

    public static String decimalFormat8(long formNumber) {
        return new DecimalFormat("#.00000000").format(formNumber);
    }

    public static String decimalFormat8(String formNumber) {
        return new DecimalFormat("#.00000000").format(stringToDouble(formNumber));
    }

    public static String decimalFormat_8(double formNumber) {
        return new DecimalFormat("#.########").format(formNumber);
    }

    public static String decimalFormat_8(long formNumber) {
        return new DecimalFormat("#.########").format(formNumber);
    }

    public static String decimalFormat_8(String formNumber) {
        return new DecimalFormat("#.########").format(stringToDouble(formNumber));
    }


    /**
     * @author xyjian
     * @version V1.0.0
     * @Description: {字符串数字转换成int型}
     * @date 2018/9/5
     */
    public static int stringToInt(String num) {
        int result;
        try {
            result = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    /**
     * @author xyjian
     * @version V1.0.0
     * @Description: {字符串数字转换成int型}
     * @date 2018/9/5
     */
    public static double stringToDouble(String num) {
        double result;
        try {
            result = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }
}

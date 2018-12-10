package app.qingtingfm.com.util;

import android.text.TextUtils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 请求参数，签名
 */
public class SignUtil {

    public static Map<String, String> getSortMap() {
        Map<String, String> params = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //升序
                return o1.compareTo(o2);
            }
        });
        return params;
    }

    public static String getSortSign(Map<String, String> params) {
        String signString = "";
        Set<String> keySets = params.keySet();
        Iterator<String> iterators = keySets.iterator();
        while (iterators.hasNext()) {
            String key = iterators.next();
            String value = params.get(key);
            if (TextUtils.isEmpty(signString)) {
                signString = key + "=" + value;
            } else {
                signString = signString + "&" + key + "=" + value;
            }
        }
        LogJoneUtil.e("getSortSign sign >>" + signString);

        String md5 = Md5.generateMD5(signString);
        LogJoneUtil.e("getSortSign Md5sign >>" + md5);
        return md5;
    }
}

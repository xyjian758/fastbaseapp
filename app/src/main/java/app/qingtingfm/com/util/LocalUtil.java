package app.qingtingfm.com.util;


/**
 * @author xyjian
 * @version V1.0.0
 * @Description: {语言切换管理}
 * @date 2018/10/17
 */

public class LocalUtil {

    /**
     * 切换语言，放在app启动页
     * 默认语言是根据当前app语言匹配，若是匹配不上默认为英语
     *
     * @param context
     * @return
     */
//    public static int recoverLanguage(Context context) {
//        if (context == null) {
//            return 0;
//        }
//        int languageType = -1;
//        Resources resources = context.getResources();
//        Configuration config = resources.getConfiguration();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//
//        languageType = AbSharedUtil.getInt(Const.SP_LOCAL_LANGUAGE_TYPE, -1);
//        LogJoneUtil.e("languageType =" +languageType);
//        if (languageType == -1) {
//            //看看手机本身是什么语言
//
//            Locale locale = config.locale;
//            if (TextUtils.equals(locale.getLanguage(), Const.LANGUAGE_ZH)) {
//                //当前是中文环境
//                languageType = 1;
//            } else if (TextUtils.equals(locale.getLanguage(), Const.LANGUAGE_RU)) {
//                //当前是俄语环境
//                languageType = 2;
//            } else {
//                //英语 或者默认的都是英语环境
//                languageType = 0;
//            }
//            AbSharedUtil.putInt(Const.SP_LOCAL_LANGUAGE_TYPE, languageType);
//        }
//
//        switch (languageType) {
//            case 0:
//                config.locale = Locale.ENGLISH;     // 英文
//                break;
//            case 1:
//                config.locale = Locale.SIMPLIFIED_CHINESE;   // 简体中文
//                break;
//            case 2:
//                config.locale = new Locale("ru"); //  俄文
//                break;
//            default:
//                config.locale = Locale.ENGLISH;         // 系统默认语言
//                break;
//        }
//        LogJoneUtil.d("<locale>"+config.locale.getLanguage()+"<Default>"+Locale.getDefault().getLanguage());
//        resources.updateConfiguration(config, metrics);
//        return languageType;
//    }


}

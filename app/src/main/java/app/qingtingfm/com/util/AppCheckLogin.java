package app.qingtingfm.com.util;

import android.content.Context;


/**
 * @author Alex
 * @version V1.2.1
 * @Title: ${}
 * @Description: ${todo}(是否登录 根据token)
 * @date ${date} 下午4:30
 */
public class AppCheckLogin {

    public static boolean isLogin(Context context, int loginValue) {

//        if (TextUtils.isEmpty(AbSharedUtil.getString(Const.USER_TOKEN))) {
//            User.getInstance().setLoginIndex(loginValue);
//            LoginActivity.newIntent(context);
////            LoginActivity.newIntent(context, loginValue);
//            return false;
//        } else {
//            return true;
//        }

        return false;

    }

    public static boolean isLogin() {
//        return !TextUtils.isEmpty(AbSharedUtil.getString(Const.USER_TOKEN));
        return false;
    }
}

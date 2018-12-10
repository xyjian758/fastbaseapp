package app.qingtingfm.com.util;

/**
 * anther: created by zuochunsheng on 2018/10/10 13 : 21
 * descript :
 */
public class User {

    private static User instance;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }


    public void clearUserCache() {
        //用户登陆失效 当前方法做缓存清除
        //缓存的播放列表数据
    }
}

package app.qingtingfm.com.retrofit;


import java.io.Serializable;

/**
 * 现行 后天返回的数据初步接口都是 这个模型 方便统一管理
 */
public class BaseBean implements Serializable {
    public String code;
    public String message;
}

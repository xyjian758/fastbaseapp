package app.qingtingfm.com.model;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Map;

import app.qingtingfm.com.retrofit.BaseBean;
import app.qingtingfm.com.retrofit.RetrofitClient;
import io.reactivex.functions.Consumer;

/**
 * 把所有的请求统一放到一个类下面 方便统一管理和复用
 */
public class DemoRequest {

    /**
     * 经纪人 热门城市定位 获取当前定位城市信息
     */
    public static void getLocationCityInfo(Map<String, String> params, Subscriber<BaseBean> subscriber) {
        params.put("appkey", "064D0CEF-1AC5-4BFE-A1C6-DA2CA90E1805");
        params.put("client_id", "ANDROID.MODELAndroidSDKbuiltforx86IMEI000000000000000");
        params.put("USER_TOKEN", "12b88f9a9eb3431eb93c73eac96a502f");
        params.put("branch_id", "");
        params.put("job_type", "%E4%B8%8D%E9%99%90");
        params.put("education", "%E4%B8%8D%E9%99%90");
        params.put("salary", "0-0");
        params.put("order_type", "0");
        params.put("page", "1");
        params.put("size", "15");
//        String baseUrl="http://10.0.11.44:8080/api/";
        String baseUrl = "https://life-api.youlanw.com/api/";
        RetrofitClient.getInstance(baseUrl)
                .post("platform/v2/job/findHomePageJobs", params, BaseBean.class)
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) {
                        Log.d("tag", "<doOnSubscribe>" + System.currentTimeMillis());
                    }
                })
                .subscribe(subscriber);
    }


}

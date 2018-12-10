package app.qingtingfm.com.retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.qingtingfm.com.api.API;
import app.qingtingfm.com.util.LogJoneUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装的retrofit类 get post upload download
 */
public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private static final int DEFAULT_TIMEOUT = 20;
    private OkHttpClient okHttpClient;
    private static String baseUrl = API.BASE_URL;// API.APPSERVERVERSION;

    private APIService mBaseApiServices;//临时确定的 BaseUrl创建的server
    private APIService mUrlApiServices;//临时确认的，动态的BaseUrl创建的server
    private APIService mApiServices;
    private boolean isBaseUrl = true;
    private Gson gson;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(String baseUrl) {
        return new RetrofitClient(baseUrl);
    }

    private RetrofitClient() {
        this(null);

    }

    private RetrofitClient(String url) {

        createHttpClient();

        if (TextUtils.isEmpty(url)) {
            //都是 baseUrl
            createBaseUrlService();
            mApiServices = mBaseApiServices;
            isBaseUrl = true;
        } else {
            //baseUrl 有变更
            createChangeUrlService(url);
            mApiServices = mUrlApiServices;
            isBaseUrl = false;
        }
    }


    private void createHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
//                    .addNetworkInterceptor(new RetrofitNetWorkInterceptor())
//                    .addInterceptor(new PublicParamsInterceptor())
                    .addInterceptor(new PublicHeaderInterceptor())
                    .addInterceptor(new RetrofitInterceptor())
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
    }

    private void createBaseUrlService() {
        if (mBaseApiServices == null) {
            mBaseApiServices = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                    .create(APIService.class);
        }
    }


    private void createChangeUrlService(String changeUrl) {

        mUrlApiServices = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(changeUrl)
                .build()
                .create(APIService.class);

    }

    public APIService getBaseApiServices() {
        return mApiServices;
    }

    //get请求
    public <T> Flowable<T> get(String url, Map<String, String> params, final Class<T> clazz) {
        return mApiServices.executeGet(url, params)
                .onBackpressureDrop()//背压丢弃策略
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //post请求
    public <T> Flowable<T> post(String urlField, Map<String, String> params, final Class<T> clazz) {
        return mApiServices.executePost(urlField, params)
                .onBackpressureDrop()//背压丢弃策略
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public <T> Flowable<T> uploadFile(String urlField, File file, String fileDesc, final Class<T> clazz) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody fileDescBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileDesc);
        MultipartBody.Part formData = MultipartBody.Part.createFormData("file", file.getName(), fileBody);//keyName是跟后台约定好的

        return mApiServices.upLoadFile(urlField, fileDescBody, formData)
                .onBackpressureDrop()//背压丢弃策略
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) {
                        T t = null;
                        try {
                            String body = responseBody.string();
                            t = getGson().fromJson(body, clazz);
                        } catch (Exception e) {
                            new Exception(e.getMessage());
                        } finally {
                            if (null != responseBody) {
                                responseBody.close();
                            }
                        }
                        return t;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Gson getGson() {
        if (null == gson) {
            gson = new Gson();
        }
        return gson;
    }

    //================================内部类=======================================================
    //cookie管理器
    private class RetrofitCookie implements CookieJar {

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return null;
        }
    }

    //网络拦截器
    private class RetrofitNetWorkInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();

            Request request = chain.request()
                    .newBuilder()
                    .header("mac", "f8:00:ea:10:45")//会覆盖之前的
                    .header("uuid", "gdeflatfgfg5454545e")
                    .header("userId", "Fea2405144")
                    .addHeader("netWork", "wifi")//不会覆盖之前的
                    .build();
            Response reponse = chain.proceed(request);
            return reponse;
        }
    }

    //这里不能直接使用response.body().string()的方式输出日志
    //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
    //个新的response给应用层处理
//    ResponseBody responseBody = response.peekBody(1024 * 1024);
//    Log.i("CommonLog",response.request().url()+ " , use-timeMs: " + (t2 - t1) + " , data: "+responseBody.string());

    //整个拦截器
    private class RetrofitInterceptor implements Interceptor {
        private long timeSecond = 1000 * 1000 * 1000 * 1000;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response reponse = chain.proceed(request);
            LogJoneUtil.d(TAG, "拦截器  本次请求数据："
                            + "\n" + "Request："
                            + "\n" + "请求头：" + request.headers()
                            + "\n" + "请求方式：" + request.method()
                            + "\n" + "请求体：" + bodyToString(request.body())
                            + "\n" + "请求地址：" + request.url()
//                    + "\n" + "请求缓存控制：" + request.cacheControl()
//                    + "\n" + "请求方式是否Https:" + request.isHttps()
                            + "\n" + "Reponse:"
                            // + "\n" + "响应头：" + "\n" + "[" + "\n" + reponse.headers() + "]"
                            + "\n" + "响应协议：" + reponse.protocol()
                            + "\n" + "响应code：" + reponse.code()
//                    + "\n" + "响应消息：" + reponse.message()
//                    + "\n" + "响应握手信息：" + reponse.handshake()
//                    + "\n" + "响应缓存控制：" + reponse.cacheControl()
                            // + "\n" + "响应头 Authorization：" + authorization
                            + "\n" + "响应体：" + reponse.peekBody(1024 * 1024).string()
//                    + "\n" + "响应接收耗时?：" + reponse.receivedResponseAtMillis() / timeSecond + "秒"
//                    + "\n" + "响应发送耗时?：" + reponse.sentRequestAtMillis() / timeSecond + "秒"
                            + "\n\n"
            );
            return reponse;
        }
    }

    private class PublicHeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request()
//                    .newBuilder()
//                    .header("Authorization", Const.HEADER_BEARER + User.getInstance().getToken())//会覆盖之前的  不准再改，标准用 _t
//                    .header("lang", User.getInstance().getLanguageName())
//                    .header("User-Agent", User.getInstance().getUserAgent())
////                    .addHeader("netWork", "wifi")//不会覆盖之前的
//                    .build();
            return chain.proceed(chain.request());
        }


    }

    private class PublicParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) {
            Request oldRequest = chain.request();
            Request.Builder newRequestBuilder = oldRequest.newBuilder();
            Response reponse = null;
            Request newRequest = null;
            //判断是否需要添加公共参数
//            if (TextUtils.isEmpty(UserConst.instance().getToken())) {
//                reponse = chain.proceed(oldRequest);
//            } else {
//                //判断是get或者post 添加公共参数
//                if (TextUtils.equals("GET", oldRequest.method()) || TextUtils.equals("get", oldRequest.method())) {
//                    //get请求 往get参数里添加公共参数
//                    HttpUrl.Builder urlNewBuilder = oldRequest.url().newBuilder();
//                    urlNewBuilder.addEncodedQueryParameter(Const.param_t, UserConst.instance().getToken());
//                    newRequest = newRequestBuilder.url(urlNewBuilder.build()).build();
//
//                } else {
//                    RequestBody body = oldRequest.body();
//                    MediaType mediaType = body.contentType();
//
//                    if (mediaType == null || !TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
//                        //上传文件或者其他类型的不主动添加参数
//                    } else {
//                        //post请求 往post body里添加参数
//                        FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                        formBodyBuilder.add(Const.param_t, UserConst.instance().getToken());
//                        RequestBody requestBody = formBodyBuilder.build();
//
//                        //解析本身的body参数 并生成新的body
//                        String postBodyString = bodyToString(body);
//                        postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(requestBody);
//                        newRequestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
//
//                        //创建新的请求
//                        newRequest = newRequestBuilder.build();
//                    }
//
//                }
//                if (newRequest == null) {
//                    newRequest = oldRequest;
//                }
//                reponse = chain.proceed(newRequest);
//            }
            return reponse;
        }


    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                if (copy.contentType() != null && TextUtils.equals(copy.contentType().subtype(), "form-data")) {
                    return "file up load";
                } else {
                    copy.writeTo(buffer);
                    return buffer.readUtf8();
                }
            } else {
                return "";
            }
        } catch (final IOException e) {
            return "did not work";
        }
    }
/*
    Observable使用场景:

    数据流最长不超过1000个元素，即随着时间的流逝，应用没有机会报OOME(OutOfMemoryError)错误。
    处理诸如鼠标移动或触摸事件之类的GUI事件

    Flowable使用场景:

    处理超过10K+ 的元素流
    从磁盘读取（解析文件）
    从数据库读取数据
            从网络获取数据流

    作者：依然范特稀西
    链接：http://www.jianshu.com/p/a2aa585ff6fd
    來源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/


}

package app.qingtingfm.com.retrofit;

import android.content.Context;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.ConnectException;

import app.qingtingfm.com.widget.dialog.CustomProgressDialog;
import retrofit2.Response;

/**
 * 主要是针对 BaseModel 型的数据的返回统一处理
 */
public abstract class ResultSubscriber<T extends BaseBean> implements Subscriber<T> {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;//
    private static final int NOT_FOUND = 404;
    private static final int BUSINESS_ERROR = 400;//业务上的错误，正确的话 全部是200了，业务错误的话，全部在400里面了
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static final int ERROR_NET = 201712;//网络连接异常
    public static final int ERROR_NET_TOKEN = 201713; //网络连接异常 token不能用了
    public static final int ERROR_NET_BUSINESS = 201714; //数据异常

    private Context mContext;
    private String mRequestTag;
    private boolean mShowDialog;
    private String mDialogMsg;


    public ResultSubscriber(String requestTag) {
        this.mRequestTag = requestTag;
        this.mShowDialog = false;
    }

    public ResultSubscriber(Context context, String requestTag) {
        this.mContext = context;
        this.mRequestTag = requestTag;
        this.mShowDialog = true;
    }

    public ResultSubscriber(Context context, String requestTag, String dialogMsg) {
        this.mContext = context;
        this.mRequestTag = requestTag;
        this.mShowDialog = true;
        this.mDialogMsg = dialogMsg;
    }

    //私有化 ，需要传参数
    private ResultSubscriber() {
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (mShowDialog) {
            //需要加载进度框
            if (!TextUtils.isEmpty(mDialogMsg)) {
                CustomProgressDialog.showLoading(mContext, mDialogMsg);
            } else {
                CustomProgressDialog.showLoading(mContext);
            }
        }
        s.request(1);//网络请求一般也只有发送一次
        RetrofitApiManager.getInstance().add(mRequestTag, s);//添加的网络请求管理中，方便取消或者 页面销毁时取消
    }

    @Override
    public void onNext(T t) {
        dataSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        int code = ERROR_NET;//默认就是网络错误
        String errorMsg;
        if (mShowDialog) {
            //取消对话框
            CustomProgressDialog.stopLoading();
        }
        RetrofitApiManager.getInstance().remove(mRequestTag);

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    code = ERROR_NET_TOKEN;
                    errorMsg = "登陆失效";
                    break;
                case NOT_FOUND:
//                    code = ERROR_NET_TOKEN;
                    errorMsg = "页面未找到";
                    break;
                case REQUEST_TIMEOUT:
                    errorMsg = "请求超时";
                    break;
                case GATEWAY_TIMEOUT:
                    errorMsg = "连接超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                    errorMsg = "服务器错误";
                    break;
                case BUSINESS_ERROR:
                    code = ERROR_NET_BUSINESS;
                    //业务错误
                    Response response = httpException.response();
                    try {
                        if (response != null && response.errorBody() != null) {
                            String string = response.errorBody().string();
                            errorMsg = string;
                            if (!TextUtils.isEmpty(string)) {
                                JSONObject jsonObject = new JSONObject(string);
                                errorMsg = jsonObject.getString("message");
                                if (!TextUtils.isEmpty(errorMsg)) {
                                }
                            }
                        } else {
                            errorMsg = "服务器错误";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        errorMsg = "服务器错误 IO";
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        errorMsg = "服务器错误 JSONE";
                    }
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    errorMsg = "网络错误" + httpException.code();
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            errorMsg = "解析错误";
        } else if (e instanceof ConnectException) {
            errorMsg = "连接失败";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            errorMsg = "证书验证失败";
        } else {
            errorMsg = "错误：" + e.getMessage();
        }
        dataError(code, errorMsg);
        Log.d("tag", "<onError>" + errorMsg);
        //
        if (code == ERROR_NET_TOKEN) {
            //跳转登陆页面
//            User.getInstance().clearUserCache();
//            Intent intent = new Intent(AndroidApplication.getAppContext(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            AndroidApplication.getAppContext().startActivity(intent);
        }
    }

    @Override
    public void onComplete() {
        if (mShowDialog) {
            //取消对话框
            CustomProgressDialog.stopLoading();
        }
        RetrofitApiManager.getInstance().remove(mRequestTag);
    }

    public abstract void dataSuccess(T t);

    public abstract void dataError(@DataErrorType int errorType, String msg);

    @IntDef({ERROR_NET, ERROR_NET_TOKEN, ERROR_NET_BUSINESS})
    public @interface DataErrorType {
    }
}

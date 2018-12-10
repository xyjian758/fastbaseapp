package app.qingtingfm.com.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.qingtingfm.com.retrofit.RetrofitApiManager;
import app.qingtingfm.com.util.StatusBarUtil;
import app.qingtingfm.com.util.TUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * 基类
 */

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements BaseView {
    protected P mPresent;
    protected List<String> mRequestTags;
    private InputMethodManager mIMM;
    private Toast toast;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//通过程序改变屏幕显示的方向
        //需要再setContentView前调用
        beforeSetContentView();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initMVP();
        initPresenter();

        initViewAndData(savedInstanceState);
    }

    private void initMVP() {
        mPresent = TUtil.getT(this, 0);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil(msg);
    }


    //添加请求tag
    protected void addRequestTag(String requestTag) {
        if (mRequestTags == null) {
            mRequestTags = new ArrayList();
        }

        if (mRequestTags.contains(requestTag)) {
            RetrofitApiManager.getInstance().cancel(requestTag);
            mRequestTags.remove(requestTag);
        }
        mRequestTags.add(requestTag);
    }

    protected void cancleRequestTag() {
        if (mRequestTags == null || mRequestTags.size() <= 0) {
            return;
        }
        for (String tag : mRequestTags) {
            RetrofitApiManager.getInstance().cancel(tag);
        }
        mRequestTags.clear();
        mRequestTags = null;
    }

    public void ToastUtil(String toastMsg) {
        if (TextUtils.isEmpty(toastMsg)) {
            return;
        }
        if (this == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(toastMsg);
        }
        toast.show();
    }


    // 隐藏软键盘
    protected void hideSoftInput() {
        initImm();
        if (mIMM != null) {
            mIMM.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private void initImm() {
        if (this.mIMM == null) {
            this.mIMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }

    }

    protected void setViewVisible(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void setViewGone(View view) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        cancleRequestTag();
        if (mPresent != null) {
            mPresent.onDestroy();
        }
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }

    /**
     * @param change     是否变更 状态栏文字颜色
     * @param changeType 变更类型 0：深色（黑色） 1：正常的颜色（白色）
     */
    public void changeStatuBarTextImageColor(boolean change, int changeType) {
        StatusBarUtil.changeStatuBarTextImageColor(this, change, changeType);
    }

    /*********************子类非必须实现*****************************/

    //在设置 contentView之前
    public void beforeSetContentView() {

    }

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化需要的东东
    public abstract void initViewAndData(Bundle savedInstanceState);


}

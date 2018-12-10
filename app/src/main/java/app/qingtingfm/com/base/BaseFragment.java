package app.qingtingfm.com.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.qingtingfm.com.retrofit.RetrofitApiManager;
import app.qingtingfm.com.util.TUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


public abstract class BaseFragment<P extends BasePresenter> extends SupportFragment implements BaseView {
    protected P mPresent;
    protected List<String> mRequestTags;
    private Toast toast;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMVP();
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    private void initMVP() {
        mPresent = TUtil.getT(this, 0);
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
        if (_mActivity == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(_mActivity, toastMsg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(toastMsg);
        }
        toast.show();

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
    public void showToast(String msg) {
        ToastUtil(msg);
    }


    @Override
    public void onDestroyView() {
        cancleRequestTag();
        if (mPresent != null) {
            mPresent.onDestroy();
        }
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return  new DefaultHorizontalAnimator();
    }


    /*********************子类实现*****************************/

    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();





}

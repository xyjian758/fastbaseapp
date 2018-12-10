package app.qingtingfm.com.base;

/**
 * author  xyj
 * createtime 2017/4/6 10:27
 * desc 基类presenter
 */
public abstract class BasePresenter<V extends BaseView> {
    public V mView;

    /**
     * 绑定View
     *
     * @param v View对象
     */
    public void setView(V v) {
        this.mView = v;
        this.onStart();
    }

    /**
     * 页面销毁时 销毁当前View.onDestroy（）中已主动调用
     */
    public void destroyView() {
        this.mView = null;
    }

    /**
     * 调用View时，判断当前页面是否已经跟activiity和fragment绑定，防止null出现
     */
    public boolean isViewAttrach() {
        return mView != null;
    }

    public void onStart() {
    }

    public void onDestroy() {
        destroyView();
    }

}

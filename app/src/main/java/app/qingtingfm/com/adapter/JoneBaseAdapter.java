package app.qingtingfm.com.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;


/**
 * @author Xyjian
 * @version: 1.8.2
 * @Description: TODO(){通用的适配器  只是数据需要回调到书写页面}
 * @date: 2017/10/19 11:03
 */
public abstract class JoneBaseAdapter<T> extends BGARecyclerViewAdapter<T> {


    public JoneBaseAdapter(RecyclerView recyclerView, @LayoutRes int resource) {
        super(recyclerView, resource);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, T model) {
        fillItemData(helper, position, model);
    }

    public abstract void fillItemData(BGAViewHolderHelper helper, int position, T model);
}

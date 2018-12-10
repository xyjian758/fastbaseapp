package app.qingtingfm.com.adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * @author Xyjian
 * @version: 1.8.2
 * @Description: TODO(){多条目显示 适配器}
 * @date: 2017/11/30 16:50
 */
public abstract class JoneMultiBaseAdapter<T> extends BGARecyclerViewAdapter<T> {


    public JoneMultiBaseAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return getMultiItemViewType(position);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, T model) {
        fillMultiItemData(helper, position, model);
    }

    public abstract void fillMultiItemData(BGAViewHolderHelper helper, int position, T model);

    public abstract int getMultiItemViewType(int position);
}

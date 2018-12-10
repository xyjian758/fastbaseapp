package app.qingtingfm.com.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.qingtingfm.com.R;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * @author Xyjian
 * @version: 1.8.2
 * @Description: TODO(){通用的适配器  底部可以显示数据是否已经加载完成  只是数据需要回调到书写页面}
 * @date: 2017/10/19 11:03
 */
public abstract class JoneBottomBaseAdapter<T> extends BGARecyclerViewAdapter<T> {

    private int mDefaultItemLayoutId;
    private int mBottomItemLayoutId = R.layout.recyclerview_footer_the_end;
    private boolean showEnd;

    public JoneBottomBaseAdapter(RecyclerView recyclerView, @LayoutRes int resource) {
        super(recyclerView);
        this.mDefaultItemLayoutId = resource;
    }

    //增加一个底部条目 显示的布局
    @Override
    public int getItemViewType(int position) {
        if (position == super.getItemCount()) {
            return mBottomItemLayoutId;
        }
        return mDefaultItemLayoutId;
    }

    //增加一个底部条目 显示的个数
    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    //获取条目对象，因为是最后一个 是null
    @Override
    public T getItem(int position) {
        if (position < super.getItemCount()) {
            return super.getItem(position);
        }
        return null;
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, T model) {
        if (position < super.getItemCount()) {
            fillItemData(helper, position, model);
        } else if (position == super.getItemCount()) {
            helper.setVisibility(R.id.tvTheEnd, showEnd ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == mBottomItemLayoutId) {
            BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(this, mRecyclerView, LayoutInflater.from(mContext).inflate(viewType, parent, false), null, mOnRVItemLongClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(mOnItemChildCheckedChangeListener);
            viewHolder.getViewHolderHelper().setOnRVItemChildTouchListener(mOnRVItemChildTouchListener);
            setItemChildListener(viewHolder.getViewHolderHelper(), viewType);
            return viewHolder;
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    public void setLoadMoreEnd(boolean isEnd) {
        this.showEnd = isEnd;
        notifyItemChanged(super.getItemCount());
    }

    @Override
    public void setOnRVItemClickListener(BGAOnRVItemClickListener onRVItemClickListener) {
        super.setOnRVItemClickListener(onRVItemClickListener);
    }

    public abstract void fillItemData(BGAViewHolderHelper helper, int position, T model);

}

package app.qingtingfm.com.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import app.qingtingfm.com.util.ResourceUtil;


//间隔分割线
public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private int mDividerHeight;
    private boolean needFirst;

    public ItemSpaceDecoration(Context context, int orientation, int height) {
        this.mDividerHeight = (int) ResourceUtil.dp2px(context.getResources(), height);
        setOrientation(orientation);
    }

    public ItemSpaceDecoration(Context context, int orientation, int height, boolean needFirst) {
        this.mDividerHeight = (int) ResourceUtil.dp2px(context.getResources(), height);
        this.needFirst = needFirst;
        setOrientation(orientation);
    }

    //设置屏幕的方向
    private void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (mOrientation == VERTICAL_LIST) {
                //画横线，就是往下偏移一个分割线的高度
                if (parent.getChildAdapterPosition(view) == 0 && needFirst) {
                    outRect.set(0, this.mDividerHeight, 0, this.mDividerHeight);
                } else {
                    outRect.set(0, 0, 0, this.mDividerHeight);
                }

            } else {
                //画竖线，就是往右偏移一个分割线的宽度

                if (parent.getChildAdapterPosition(view) == 0 && needFirst) {
                    outRect.set(this.mDividerHeight, 0, this.mDividerHeight, 0);
                } else {
                    outRect.set(0, 0, this.mDividerHeight, 0);
                }
            }
        }

    }


}

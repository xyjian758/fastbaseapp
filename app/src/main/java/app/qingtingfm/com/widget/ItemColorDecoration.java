package app.qingtingfm.com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import app.qingtingfm.com.R;
import app.qingtingfm.com.util.ResourceUtil;

/**
 * authr : edz on 2017/8/31  下午11:04
 * describe : recyclerView 分割线
 */


public class ItemColorDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private float mDividerHeight;
    private static int defaultColor = ResourceUtil.getColor(R.color.line);
    private Paint mPaint;

    public ItemColorDecoration(Context context, int orientation) {
        this(context, orientation, defaultColor);
    }

    public ItemColorDecoration(Context context, int orientation, @ColorInt int color) {
        this(context, orientation, color, 1);
    }

    public ItemColorDecoration(Context context, int orientation, @ColorInt int color, int height) {
        this.mDividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        setOrientation(orientation);
    }

    //设置屏幕的方向
    private void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            if (mOrientation == HORIZONTAL_LIST) {
                drawVerticalLine(c, parent, state);
            } else {
                drawHorizontalLine(c, parent, state);
            }
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) { // childCount ->childCount -1 消除底部分割线
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = (int) (top + this.mDividerHeight);
            c.drawRect(left, top, right, bottom, mPaint);
            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
        }
    }

    //画竖线
    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {// childCount ->childCount -1
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = (int) (left + this.mDividerHeight);
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (mOrientation == VERTICAL_LIST) {
                //画横线，就是往下偏移一个分割线的高度
                outRect.set(0, 0, 0, (int) this.mDividerHeight);
            } else {
                //画竖线，就是往右偏移一个分割线的宽度
                outRect.set(0, 0, (int) this.mDividerHeight, 0);
            }
        }

    }


}

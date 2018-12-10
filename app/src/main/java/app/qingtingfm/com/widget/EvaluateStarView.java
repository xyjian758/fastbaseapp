package app.qingtingfm.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Iterator;
import java.util.Map;

import app.qingtingfm.com.R;
import app.qingtingfm.com.util.ResourceUtil;


/**
 * Created by felix.fan on 2017/8/31.
 * 评论星级自定义view
 */

public class EvaluateStarView extends LinearLayout {

    private int mStartTotal;
    private LayoutInflater mInflater;
    //DP
    private static final int starDefaultWidth = 22;
    private static final int starDefaultHeight = 22;

    private ArrayMap<View, Integer> mStarViewArray;
    private float mStarWidth;
    private float mStarHeight;
    private float mStarRightMargin;

    //选择的星级数
    private int mStarCount;
    private int mStarStyle;

    public EvaluateStarView(Context context) {
        this(context, null);
    }

    public EvaluateStarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EvaluateStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        mStarViewArray = new ArrayMap<>();
        mInflater = LayoutInflater.from(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EvaluateStarView);
        mStartTotal = ta.getInteger(R.styleable.EvaluateStarView_star_total, 5);
        mStarWidth = ta.getDimensionPixelOffset(R.styleable.EvaluateStarView_star_width, (int) ResourceUtil.dp2px(context.getResources(), 22));
        mStarHeight = ta.getDimensionPixelOffset(R.styleable.EvaluateStarView_star_height, (int) ResourceUtil.dp2px(context.getResources(), 22));
        mStarRightMargin = ta.getDimension(R.styleable.EvaluateStarView_star_right_margin, (int) ResourceUtil.dp2px(context.getResources(), 8));
        mStarStyle = ta.getResourceId(R.styleable.EvaluateStarView_star_style, R.layout.widget_evaluate_result_single_star);
        ta.recycle();
        addStar();

    }

    private void addStar() {
        if (mStartTotal <= 0) {
            return;
        }
        for (int i = 0; i < mStartTotal; i++) {
            final View view = mInflater.inflate(mStarStyle, null);
            LayoutParams lp = new LayoutParams((int) mStarWidth, (int) mStarHeight);
            if (i != mStartTotal - 1) {
                lp.rightMargin = (int) mStarRightMargin;
            }
            view.setLayoutParams(lp);
            view.setTag(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectStarChangeState((int) view.getTag());
                }
            });
            this.addView(view);
            mStarViewArray.put(view, i);
        }
    }

    private void selectStarChangeState(int i) {
        Iterator<Map.Entry<View, Integer>> iterator = mStarViewArray.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<View, Integer> item = iterator.next();
            View view = item.getKey();
            Integer value = item.getValue();
            if (value <= i) {
                //选中
                setStarSelect(view);
                mStarCount = i + 1;
            } else {
                //不选中
                setStarNoselect(view);
            }
        }
        if (mListener != null) {
            mListener.onStarCount(mStarCount);
        }
    }

    public int getStarCount() {
        return mStarCount;
    }

    //只显示 不可点击 显示已评价的星级
    public void showStarRange(int starCount) {
        if (starCount < 0) {
            return;
        }
        Iterator<Map.Entry<View, Integer>> iterator = mStarViewArray.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<View, Integer> item = iterator.next();
            Integer value = item.getValue();
            View view = item.getKey();
            if (value <= starCount - 1) {
                setStarSelect(view);
            } else {
                setStarNoselect(view);
            }
            view.setEnabled(false);
        }

        if (mListener != null) {
            mListener.onStarCount(starCount);
        }
    }

    //设置星级不可点击
    public void noClickAllStar() {
        if (mStarViewArray == null) {
            return;
        }
        Iterator<Map.Entry<View, Integer>> iterator = mStarViewArray.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<View, Integer> item = iterator.next();
            View view = item.getKey();
            view.setEnabled(false);
        }
    }

    //没有半颗星时 不选中
    private void setStarNoselect(View view) {
        View statusView = view.findViewById(R.id.iv_star_status);
        statusView.setSelected(false);
    }

    //没有半颗星时 选中
    private void setStarSelect(View view) {
        View statusView = view.findViewById(R.id.iv_star_status);
        statusView.setSelected(true);
    }

    private OnEvaluteListener mListener;

    public void setOnEvaluteListener(OnEvaluteListener listener) {
        this.mListener = listener;
    }

    public interface OnEvaluteListener {
        void onStarCount(int count);
    }
}

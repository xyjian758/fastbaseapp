package app.qingtingfm.com.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import app.qingtingfm.com.R;


/**
 * @author Xyjian
 * @version: 1.8.2
 * @Description: TODO(){加载结果布局的显示}
 * @date: 2017/11/25 9:35
 */
public class LoadingLayout extends FrameLayout {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;


    private int animationTime = 500;//默认值
    private int state = -1;//默认是-1

    private Context mContext;
    private View errorPage;
    private View emptyPage;
    private View contentView;

    private boolean loading_empty_firstvisble; //是否第一次显示空数据布局 默认不显示
    private int loading_empty_resourceId;
    private int loading_error_resourceId;
    private boolean loading_error_need;
    private boolean loading_empty_need;


    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        loading_empty_firstvisble = a.getBoolean(R.styleable.LoadingLayout_loading_empty_firstvisble, false);
        loading_empty_resourceId = a.getResourceId(R.styleable.LoadingLayout_loading_empty, 0);
        loading_error_resourceId = a.getResourceId(R.styleable.LoadingLayout_loading_error, 0);
        a.recycle();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public LoadingLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }
        contentView = this.getChildAt(0);
        build();
        if (loading_empty_firstvisble) {
            setStatus(Empty);
        } else {
            setStatus(Success);
        }
    }

    private void build() {
        //空布局加载
        if (loading_empty_resourceId != 0) {
            //加载指向的空布局
            emptyPage = LayoutInflater.from(mContext).inflate(loading_empty_resourceId, null);
            this.addView(emptyPage);
            loading_empty_need = true;
        } else {
            loading_empty_need = false;
        }

        //错误布局加载
        if (loading_error_resourceId != 0) {
            //加载指向的布局
            errorPage = LayoutInflater.from(mContext).inflate(loading_error_resourceId, null);
            this.addView(errorPage);
            loading_error_need = true;
        } else {
            loading_error_need = false;
        }


    }

    public void setStatus(@Flavour int status) {

        if (!loading_empty_need && !loading_error_need) {
            //没有设置任何加载后的样式显示就不需要走了
            return;
        }
        switch (status) {
            case Success:
                if (this.state == Success) {
                    break;
                }
                setAlpha(contentView);
                if (loading_empty_need) {
                    setViewGone(emptyPage);
                }

                if (loading_error_need) {
                    setViewGone(errorPage);
                }
                break;

            case Empty:
                if (!loading_empty_need) {
                    break;
                }
                if (this.state == Empty) {
                    break;
                }
                setAlpha(emptyPage);
                setViewGone(contentView);
                if (loading_error_need) {
                    setViewGone(errorPage);
                }
                break;

            case Error:
                if (!loading_error_need) {
                    //不需要显示错误布局 就直接不处理了
                    break;
                }
                if (this.state == Error) {
                    break;
                }
                setAlpha(errorPage);
                if (loading_empty_need) {
                    setViewGone(emptyPage);
                }
                setViewGone(contentView);

                break;

            default:
                break;
        }
        this.state = status;
    }

    private void setViewVisible(View view) {
        if (null != view) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setViewGone(View view) {
        if (null != view) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @IntDef({Success, Empty, Error})
    public @interface Flavour {

    }

    //设置动画渐变时间
    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }

    private void setAlpha(final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animator.setDuration(animationTime);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setViewVisible(view);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}

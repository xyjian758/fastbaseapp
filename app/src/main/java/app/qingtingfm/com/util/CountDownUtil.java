package app.qingtingfm.com.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author xyjian
 * @version V1.0.0
 * @Description: {倒计时工具类}
 * @date 2018/9/5
 */

//    //初始化倒计时对象
//    private void initCountDowm() {
//
//        countDownUtil = new CountDownUtil();
//        countDownUtil.setNeedForm(true, 0);
//        countDownUtil.setCountDownListener(new CountDownUtil.CountDownListener() {
//            @Override
//            public void onCountDowm(long second) {
//
//            }
//
//            @Override
//            public void onCountDowm(String form) {
//                tv_wait_time.setText(form);
//            }
//
//            @Override
//            public void onFinish() {
//                refreshData();
//            }
//        });
//    }

//     countDownUtil.setNewSeconds(data.time);

//    @Override
//    public void onDestroyView() {
//        map_view.onDestroy();
//        finishRefresh();
//        if (countDownUtil != null) {
//        countDownUtil.cancleCocunDownTIme();
//        }
//        super.onDestroyView();}

public class CountDownUtil {
    private Subscription timeSub;
    private long mSecond;//倒计时时间  秒
    private CountDownListener mListener;
    private boolean neefForm;//是否需要格式化
    private int formTYpe;//格式化 样式  0://mm + ":" + ss; 1://hh + ":" + mm + ":" + ss; 2://hh + ":" + mm

    public CountDownUtil() {
    }

    public CountDownUtil(long seconds) {
        this.mSecond = seconds;
    }

    //取消倒计时
    public void cancleCocunDownTIme() {
        if (timeSub != null) {
            timeSub.cancel();
        }
    }

    //设置新的倒计时时间
    public void setNewSeconds(long seconds) {
        this.mSecond = seconds;
        startCoundDowmTime();
    }

    //设置是否格式化数据回调样式
    public void setNeedForm(boolean neefForm, int formTYpe) {
        this.neefForm = neefForm;
        this.formTYpe = formTYpe;
    }

    //开始倒计时
    public void startCoundDowmTime() {
        cancleCocunDownTIme();
        if (mSecond <= 0) {
            return;
        }
        Flowable.intervalRange(1, mSecond, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        timeSub = s;
                        s.request(mSecond);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (mSecond >= aLong && mListener != null) {
                            mListener.onCountDowm(mSecond - aLong);
                            if (neefForm) {
                                mListener.onCountDowm(confrimFormStyle(mSecond - aLong));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        if (mListener != null) {
                            mListener.onFinish();
                        }
                    }
                });
    }

    private String confrimFormStyle(Long aLong) {
        String tempForm = "";
        long a = aLong * 1000;
        long day = a / (1000 * 3600 * 24);// 天
        long b = a % (1000 * 3600 * 24);
        long hour = b / (1000 * 3600);
        long c = b % (1000 * 3600);
        long min = c / (1000 * 60);
        long d = c % (1000 * 60);
        long ms = d / 1000;
        String hh = "", mm = "", ss = "";
        long hhh = 24 * day;
        hour += hhh;
        hh += hour / 10;
        hh += hour % 10;
        mm += min / 10;
        mm += min % 10;
        ss += ms / 10;
        ss += ms % 10;
        String time;
//        + hh + ":" + mm + ":" + ss;

        switch (formTYpe) {
            case 0://mm + ":" + ss;
                tempForm = mm + ":" + ss;
                break;
            case 1://hh + ":" + mm + ":" + ss;
                tempForm = hh + ":" + mm + ":" + ss;
                break;
            case 2://hh + ":" + mm
                tempForm = hh + ":" + mm;
                break;
            default:
                tempForm = "未设置显示格式";
                break;

        }

        return tempForm;
    }


    public void setCountDownListener(CountDownListener downListener) {
        this.mListener = downListener;
    }

    public interface CountDownListener {
        void onCountDowm(long second);

        void onCountDowm(String form);

        void onFinish();
    }
}

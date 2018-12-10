package app.qingtingfm.com.contact.demo;

import android.content.Context;

import java.util.HashMap;

import app.qingtingfm.com.base.BasePresenter;
import app.qingtingfm.com.base.BaseView;
import app.qingtingfm.com.model.DemoRequest;
import app.qingtingfm.com.retrofit.BaseBean;
import app.qingtingfm.com.retrofit.ResultSubscriber;


/**
 * @author Xyjian
 * @Description: TODO(){临时Fragment层的契约类}
 * @date: 2018/4/19 11:57
 * <p>
 * {@link }
 */
public class DemoContact {
    interface TempView extends BaseView {
        void onSuccess();

        void onFailed();
    }

    public class Presenter extends BasePresenter<TempView> {
        public void getInfo(Context context, String tag) {
            DemoRequest.getLocationCityInfo(new HashMap<String, String>(), new ResultSubscriber<BaseBean>(context, tag) {
                @Override
                public void dataSuccess(BaseBean baseBean) {
                    mView.onSuccess();
                }

                @Override
                public void dataError(@ResultSubscriber.DataErrorType int errorType, String msg) {
                    if (isViewAttrach()) {
                        mView.onFailed();
                    }

                }
            });
        }

        public void getInfo(String tag) {
            DemoRequest.getLocationCityInfo(new HashMap<String, String>(), new ResultSubscriber<BaseBean>(tag) {
                @Override
                public void dataSuccess(BaseBean baseBean) {
                    mView.onSuccess();
                }

                @Override
                public void dataError(@ResultSubscriber.DataErrorType int errorType, String msg) {
                    if (isViewAttrach()) {
                        mView.onFailed();
                    }

                }
            });
        }
    }

}

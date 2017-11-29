package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.FeedBackView;
import com.butao.ulifebiz.mvp.view.FullDownView;
import com.butao.ulifebiz.retrofit.ApiCallback;
import com.butao.ulifebiz.retrofit.ApiPost;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class FeedBackPresenter extends BasePresenter<FeedBackView> {
    public FeedBackPresenter(FeedBackView view){
        attachView(view);
    }
    /**
     * 满减优惠
     */
    public void loadFeedBack(String remark){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("storeId", CApplication.getIntstance().getStoreId());
        map.put("remark", remark);
        addSubscription(apiServices.loadFeedBack(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getFeedBackSuccess(model);
                    }

                    @Override
                    public void onFailure(String model) {
                        mvpView.getFail(model);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }


}

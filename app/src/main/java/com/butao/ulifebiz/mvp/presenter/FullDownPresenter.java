package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.event.CutBuiltModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.CutListView;
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
public class FullDownPresenter extends BasePresenter<FullDownView> {
    public FullDownPresenter(FullDownView view){
        attachView(view);
    }
    /**
     * 满减优惠
     */
    public void loadFullDown(FullDownModel fullDownModel){
        mvpView.showLoading();
        addSubscription(apiServices.loadFullDown(ApiPost.toPost(fullDownModel)),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getFullDownSuccess(model);
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

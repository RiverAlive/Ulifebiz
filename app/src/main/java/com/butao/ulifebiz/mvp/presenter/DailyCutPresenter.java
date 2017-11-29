package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.mvp.model.shop.ShopStoreModel;
import com.butao.ulifebiz.mvp.model.shop.event.DailyCutModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.DailyCutView;
import com.butao.ulifebiz.mvp.view.FullDownView;
import com.butao.ulifebiz.retrofit.ApiCallback;
import com.butao.ulifebiz.retrofit.ApiPost;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DailyCutPresenter extends BasePresenter<DailyCutView> {
    public DailyCutPresenter(DailyCutView view){
        attachView(view);
    }
    /**
     * 天天特价
     */
    public void loadDayCut(DailyCutModel dailyCutModel){
        mvpView.showLoading();
        addSubscription(apiServices.loadDayCut(ApiPost.toPost(dailyCutModel)),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getDailyCutSuccess(model);
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

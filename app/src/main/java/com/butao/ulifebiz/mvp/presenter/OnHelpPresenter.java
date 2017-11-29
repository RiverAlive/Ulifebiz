package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.OnLineHelp;
import com.butao.ulifebiz.mvp.view.FeedBackView;
import com.butao.ulifebiz.mvp.view.OnHlepView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class OnHelpPresenter extends BasePresenter<OnHlepView> {
    public OnHelpPresenter(OnHlepView view){
        attachView(view);
    }
    /**
     *
     */
    public void loadONHelp(String remark){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("storeId", CApplication.getIntstance().getStoreId());
        map.put("remark", remark);
        addSubscription(apiServices.loadONHelp(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getHelpSuccess(model);
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
    /**
     *
     */
    public void loadHelps(){
        mvpView.showLoading();
        addSubscription(apiServices.loadHelps(),
                new ApiCallback<BaseModel<OnLineHelp>>() {
                    @Override
                    public void onSuccess(BaseModel<OnLineHelp> model) {
                        mvpView.getHelpListSuccess(model);
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

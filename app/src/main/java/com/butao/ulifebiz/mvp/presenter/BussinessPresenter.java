package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.BussinessInfo;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.BussinessView;
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
public class BussinessPresenter extends BasePresenter<BussinessView> {
    public BussinessPresenter(BussinessView view){
        attachView(view);
    }
    /**
     *47.获取门店经营信息
     */
    public void loadBussinessInfo(){
        mvpView.showLoading();
        Map<String,String> map = new HashMap<>();
        map.put("storeId", CApplication.getIntstance().getStoreId());
        addSubscription(apiServices.loadBussinessInfo(map),
                new ApiCallback<BaseModel<BussinessInfo>>() {
                    @Override
                    public void onSuccess(BaseModel<BussinessInfo> model) {
                        mvpView.getBussinessInfoSuccess(model);
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
     *49.修改自动接单
     * @param automatic
     */
    public void loadStoreAutomatic(String automatic){
        mvpView.showLoading();
        Map<String,String> map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("automatic", automatic);
        addSubscription(apiServices.loadStoreAutomatic(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getStoreAutomaticSuccess(model);
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

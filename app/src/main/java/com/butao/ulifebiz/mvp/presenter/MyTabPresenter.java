package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.BussinessInfo;
import com.butao.ulifebiz.mvp.view.BussinessView;
import com.butao.ulifebiz.mvp.view.MyTabView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyTabPresenter extends BasePresenter<MyTabView> {
    public MyTabPresenter(MyTabView view){
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
     *49.修改提示音
     */
    public void loadStoreSound(String isSound){
        mvpView.showLoading();
        Map<String,String> map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("isSound", isSound);
        addSubscription(apiServices.loadStoreSound(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getStoreSoundSuccess(model);
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

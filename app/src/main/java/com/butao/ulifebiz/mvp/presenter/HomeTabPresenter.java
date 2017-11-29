package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.BriefModel;
import com.butao.ulifebiz.mvp.model.HomeTabModel;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;
import com.butao.ulifebiz.mvp.view.HomeTabView;
import com.butao.ulifebiz.mvp.view.OperatTimeView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HomeTabPresenter extends BasePresenter<HomeTabView> {
    public HomeTabPresenter(HomeTabView view){
        attachView(view);
    }
    /**
     * 商家版首页
     */
    public void loadHomeTab(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadHomeTab(map),
                new ApiCallback<BaseModel<HomeTabModel>>() {
                    @Override
                    public void onSuccess(BaseModel<HomeTabModel> model) {
                        mvpView.getHomeSuccess(model);
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
     * 简介信息
     */
    public void loadBrief(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadBriefModel(map),
                new ApiCallback<BaseModel<BriefModel>>() {
                    @Override
                    public void onSuccess(BaseModel<BriefModel> model) {
                        mvpView.getBriefSuccess(model);
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
     * 退款申请
     */
    public void loadRefund(String  orderId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadRefound(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getRefundSuccess(model);
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
     * 接单申请
     */
    public void loadacceptOrder(String  orderId){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadacceptOrder(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getAcceptOrderSuccess(model);
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

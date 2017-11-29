package com.butao.ulifebiz.mvp.presenter;

import android.text.TextUtils;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.HomeTabModel;
import com.butao.ulifebiz.mvp.model.order.RefundOrderModel;
import com.butao.ulifebiz.mvp.view.HomeTabView;
import com.butao.ulifebiz.mvp.view.RefundOrderView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class RefoundOrderPresenter extends BasePresenter<RefundOrderView> {
    public RefoundOrderPresenter(RefundOrderView view){
        attachView(view);
    }
    /**
     * 退款列表
     */
    public void loadRFOrder(int pageNum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("pageNum",pageNum+"");
        map.put("pageSize", "10");
        addSubscription(apiServices.loadRefoundOrder(map),
                new ApiCallback<BaseModel<RefundOrderModel>>() {
                    @Override
                    public void onSuccess(BaseModel<RefundOrderModel> model) {
                        mvpView.getRFOrderSuccess(model);
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
     * 催单申请
     */
    public void loadRemind(String  orderId,String type,String remark){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("id",orderId);
        map.put("token", CApplication.getIntstance().getToken());
        if(!TextUtils.isEmpty(remark))
            map.put("remark", remark);
        map.put("type", type);
        addSubscription(apiServices.loadRemind(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getRemindSuccess(model);
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
     * 催单列表
     */
    public void loadRMOrder(int pageNum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("pageNum",pageNum+"");
        map.put("pageSize", "10");
        addSubscription(apiServices.loadRemindOrder(map),
                new ApiCallback<BaseModel<RefundOrderModel>>() {
                    @Override
                    public void onSuccess(BaseModel<RefundOrderModel> model) {
                        mvpView.getRMOrderSuccess(model);
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

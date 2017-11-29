package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.delivery.DeliveryAreaModel;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;
import com.butao.ulifebiz.mvp.view.DeliveryView;
import com.butao.ulifebiz.mvp.view.OperatTimeView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DeliveryPresenter extends BasePresenter<DeliveryView> {
    public DeliveryPresenter(DeliveryView view){
        attachView(view);
    }
    /**
     * 门店配送范围
     */
    public void loadDelivery(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadDelivery(map),
                new ApiCallback<BaseModel<DeliveryAreaModel>>() {
                    @Override
                    public void onSuccess(BaseModel<DeliveryAreaModel> model) {
                        mvpView.getDeliverySuccess(model);
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

package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.order.RefundOrderModel;
import com.butao.ulifebiz.mvp.model.shop.event.CutBuiltModel;
import com.butao.ulifebiz.mvp.view.CutListView;
import com.butao.ulifebiz.mvp.view.OrderListView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class CutListPresenter extends BasePresenter<CutListView> {
    public CutListPresenter(CutListView view){
        attachView(view);
    }
    /**
     * 优惠列表
     */
    public void loadCutList(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadCutList(map),
                new ApiCallback<BaseModel<CutBuiltModel>>() {
                    @Override
                    public void onSuccess(BaseModel<CutBuiltModel> model) {
                        mvpView.getCutListSuccess(model);
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

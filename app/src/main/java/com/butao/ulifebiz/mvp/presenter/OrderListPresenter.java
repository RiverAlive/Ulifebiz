package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.order.RefundOrderModel;
import com.butao.ulifebiz.mvp.view.OrderListView;
import com.butao.ulifebiz.mvp.view.RefundOrderView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class OrderListPresenter extends BasePresenter<OrderListView> {
    public OrderListPresenter(OrderListView view){
        attachView(view);
    }
    /**
     * 订单列表
     */
    public void loadOrderList(int pageNum,String status){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("pageNum",pageNum+"");
        map.put("pageSize", "10");
        map.put("status", status);
        addSubscription(apiServices.loadOrderList(map),
                new ApiCallback<BaseModel<RefundOrderModel>>() {
                    @Override
                    public void onSuccess(BaseModel<RefundOrderModel> model) {
                        mvpView.getOrderListSuccess(model);
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

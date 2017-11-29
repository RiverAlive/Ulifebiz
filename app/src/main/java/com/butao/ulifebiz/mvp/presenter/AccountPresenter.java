package com.butao.ulifebiz.mvp.presenter;

import android.text.TextUtils;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.AccountModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.AccountView;
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
public class AccountPresenter extends BasePresenter<AccountView> {
    public AccountPresenter(AccountView view){
        attachView(view);
    }
    /**
     * 1.startDate:开始日期
     2.endDate：结束日期
     3.orderEnd：结算，1 :未结算， 0 ：已结算
     4.pageNum：分页
     5.pageSize：分页
     */
    public void loadAccount(int pageNum,String orderEnd,String startDate,String endDate){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("pageNum",pageNum+"");
        map.put("pageSize","10");
        map.put("orderEnd",orderEnd);
        if(!TextUtils.isEmpty(startDate))
            map.put("startDate",startDate);
        if(!TextUtils.isEmpty(endDate))
            map.put("endDate",endDate);
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadAccountrder(map),
                new ApiCallback<BaseModel<AccountModel>>() {
                    @Override
                    public void onSuccess(BaseModel<AccountModel> model) {
                        mvpView.getAccountSuccess(model);
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

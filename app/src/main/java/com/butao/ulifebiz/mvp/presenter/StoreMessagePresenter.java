package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.StoreMessageModel;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.view.ShopEvaView;
import com.butao.ulifebiz.mvp.view.StoreMessageView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class StoreMessagePresenter extends BasePresenter<StoreMessageView> {
    public StoreMessagePresenter(StoreMessageView view){
        attachView(view);
    }
    /**
     * 1.storeId:门店ID
     */
    public void loadStoreMassages(int pageNum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("pageNum",pageNum+"");
        map.put("pageSize","10");
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadStoreMassages(map),
                new ApiCallback<BaseModel<StoreMessageModel>>() {
                    @Override
                    public void onSuccess(BaseModel<StoreMessageModel> model) {
                        mvpView.getStoreMessageSuccess(model);
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

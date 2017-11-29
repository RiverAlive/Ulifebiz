package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.view.ShopEvaView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopEvaPresenter extends BasePresenter<ShopEvaView> {
    public ShopEvaPresenter(ShopEvaView view){
        attachView(view);
    }
    /**
     * 1.storeId:门店ID
     */
    public void loadShopEvas(int pageNum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("pageNum",pageNum+"");
        map.put("pageSize","10");
        map.put("storeId", CApplication.getIntstance().getStoreId());
        addSubscription(apiServices.loadShopEvas(map),
                new ApiCallback<BaseModel<ShopEvaModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopEvaModel> model) {
                        mvpView.getShopEvasSuccess(model);
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

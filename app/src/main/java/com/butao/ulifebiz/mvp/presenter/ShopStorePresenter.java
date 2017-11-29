package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.ShopStoreModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.FullDownView;
import com.butao.ulifebiz.mvp.view.ShopStoreView;
import com.butao.ulifebiz.retrofit.ApiCallback;
import com.butao.ulifebiz.retrofit.ApiPost;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopStorePresenter extends BasePresenter<ShopStoreView> {
    public ShopStorePresenter(ShopStoreView view){
        attachView(view);
    }
    /**
     * 门店信息
     */
    public void loadShopStore(){
        mvpView.showLoading();
        Map<String ,String > map = new HashMap<>();
        map.put("storeId", CApplication.getIntstance().getStoreId());
        addSubscription(apiServices.loadShopStore(map),
                new ApiCallback<BaseModel<ShopStoreModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopStoreModel> model) {
                        mvpView.getShopStoreSuccess(model);
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

package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.ShopInfoModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.view.FullDownView;
import com.butao.ulifebiz.mvp.view.ShopFragmentView;
import com.butao.ulifebiz.retrofit.ApiCallback;
import com.butao.ulifebiz.retrofit.ApiPost;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopFragmentPresenter extends BasePresenter<ShopFragmentView> {
    public ShopFragmentPresenter(ShopFragmentView view){
        attachView(view);
    }
    /**
     * 满减优惠
     */
    public void loadShopInfo(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadShopInfo(map),
                new ApiCallback<BaseModel<ShopInfoModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopInfoModel> model) {
                        mvpView.getShopSuccess(model);
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

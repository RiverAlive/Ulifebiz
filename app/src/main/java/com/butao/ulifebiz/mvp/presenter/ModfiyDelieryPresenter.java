package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.view.ModifyDelieryView;
import com.butao.ulifebiz.mvp.view.ModifyPhoneView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ModfiyDelieryPresenter extends BasePresenter<ModifyDelieryView> {
    public ModfiyDelieryPresenter(ModifyDelieryView view){
        attachView(view);
    }
    /**
     * 60.配送设置
     */
    public void loadModifyDeliery(String price,String startPrice,String longTime,String distance){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
//        price=1&startPrice=10&longTime=50&distance=10
        map.put("token", CApplication.getIntstance().getToken());
        map.put("price", price);
        map.put("startPrice", startPrice);
        map.put("longTime", longTime);
        map.put("distance", distance);
        addSubscription(apiServices.loadModifyDeliery(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getModifyDelierySuccess(model);
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

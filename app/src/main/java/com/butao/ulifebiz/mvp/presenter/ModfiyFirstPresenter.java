package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.view.ModifyFirstView;
import com.butao.ulifebiz.mvp.view.ModifyPhoneView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ModfiyFirstPresenter extends BasePresenter<ModifyFirstView> {
    public ModfiyFirstPresenter(ModifyFirstView view){
        attachView(view);
    }
    /**
     * 48.修改手机号
     */
    public void loadModifyFirst(String firstCutPrice){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("firstCutPrice", firstCutPrice);
        addSubscription(apiServices.loadModifyFirst(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getModifyFirstSuccess(model);
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

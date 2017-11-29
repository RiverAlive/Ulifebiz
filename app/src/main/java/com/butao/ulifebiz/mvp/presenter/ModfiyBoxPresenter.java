package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.view.ModifyBoxView;
import com.butao.ulifebiz.mvp.view.ModifyPhoneView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ModfiyBoxPresenter extends BasePresenter<ModifyBoxView> {
    public ModfiyBoxPresenter(ModifyBoxView view){
        attachView(view);
    }
    /**
     * 48.修改手机号
     */
    public void loadModifyBoxFee(String packPrice){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("packPrice", packPrice);
        addSubscription(apiServices.loadModifyBoxFee(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getModifyBoxSuccess(model);
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

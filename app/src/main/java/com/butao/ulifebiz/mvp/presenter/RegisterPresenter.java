package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.ShopInfoModel;
import com.butao.ulifebiz.mvp.view.RegisterView;
import com.butao.ulifebiz.mvp.view.ShopFragmentView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView view){
        attachView(view);
    }
    /**
     * 注册
     */
    public void loadStoreRegister(String phoneNum,String validateCode,String password){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("phoneNum", phoneNum);
        map.put("validateCode", validateCode);
        map.put("password", password);
        addSubscription(apiServices.loadStoreRegister(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getRegisterSuccess(model);
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

    /**
     * 获取验证码
     */
    public void loadvalidCode(String phoneNum){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("phoneNum", phoneNum);
        addSubscription(apiServices.loadValidCode(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getCodeSuccess(model);
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

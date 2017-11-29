package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.BusinessLoginModel;
import com.butao.ulifebiz.mvp.view.BusinessLoginView;
import com.butao.ulifebiz.mvp.view.ChanagePwdView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ChanagePwdPresenter extends BasePresenter<ChanagePwdView> {
    public ChanagePwdPresenter(ChanagePwdView view){
        attachView(view);
    }
    /**
     * 商户登录
     */
    public void loadChanagePwd(String oldPwd,String newPwd){//3.旧密码newPwd:新密码
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("oldPwd",oldPwd);
        map.put("newPwd",newPwd);
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadChangePwd(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getChanagePwdSuccess(model);
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

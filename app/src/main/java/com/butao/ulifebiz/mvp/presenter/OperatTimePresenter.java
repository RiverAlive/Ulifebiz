package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;
import com.butao.ulifebiz.mvp.view.ChanagePwdView;
import com.butao.ulifebiz.mvp.view.OperatTimeView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class OperatTimePresenter extends BasePresenter<OperatTimeView> {
    public OperatTimePresenter(OperatTimeView view){
        attachView(view);
    }
    /**
     * 4.获取营业时间user/getWeeks.do
     */
    public void loadOperatTime(){//3.旧密码newPwd:新密码
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadOperatTime(map),
                new ApiCallback<BaseModel<OperatTimeModel>>() {
                    @Override
                    public void onSuccess(BaseModel<OperatTimeModel> model) {
                        mvpView.getOperatTimeSuccess(model);
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
     * 4.营业时间删除user/delWeeks.do
     */
    public void loadDeleteTime(String id){//3.旧密码newPwd:新密码
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("id",id);
        addSubscription(apiServices.loadDeleteTime(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getDeleteTimeSuccess(model);
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

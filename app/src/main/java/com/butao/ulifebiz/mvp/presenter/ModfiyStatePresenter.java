package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.view.FeedBackView;
import com.butao.ulifebiz.mvp.view.ModifyStateView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ModfiyStatePresenter extends BasePresenter<ModifyStateView> {
    public ModfiyStatePresenter(ModifyStateView view){
        attachView(view);
    }
    /**
     * 48.修改状态
     */
    public void loadStoreStatus(String status){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("status", status);
        addSubscription(apiServices.loadStoreStatus(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getModifyStateSuccess(model);
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

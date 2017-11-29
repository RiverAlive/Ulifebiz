package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.view.ContactView;
import com.butao.ulifebiz.mvp.view.OnHlepView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ContactPresenter extends BasePresenter<ContactView> {
    public ContactPresenter(ContactView view){
        attachView(view);
    }
    /**
     *
     */
    public void loadContactPhone(){
        mvpView.showLoading();
        addSubscription(apiServices.loadContactPhone(),
                new ApiCallback<BaseModel<Map<String, String>>>() {
                    @Override
                    public void onSuccess(BaseModel<Map<String, String>> model) {
                        mvpView.getContactPhoneSuccess(model);
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
     *
     */
    public void loadCallus(String remark){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("storeId", CApplication.getIntstance().getStoreId());
        map.put("remark", remark);
        addSubscription(apiServices.loadCallus(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getContactSuccess(model);
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

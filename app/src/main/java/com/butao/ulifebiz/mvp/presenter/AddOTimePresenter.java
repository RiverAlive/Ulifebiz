package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.HomeTabModel;
import com.butao.ulifebiz.mvp.view.AddOTimeView;
import com.butao.ulifebiz.mvp.view.HomeTabView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class AddOTimePresenter extends BasePresenter<AddOTimeView> {
    public AddOTimePresenter(AddOTimeView view){
        attachView(view);
    }
    /**
     * 修改或新增营业时间
     * id=1&startTime=09:00&endTime=12:00&weeks=2,3
     */
    public void loadAddOTime(String id,String startTime,String endTime,String weeks){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("id", id);
        map.put("startTime",startTime);
        map.put("endTime", endTime);
        map.put("weeks", weeks);
        map.put("token", CApplication.getIntstance().getToken());
        addSubscription(apiServices.loadAddOTime(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getTimeSuccess(model);
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

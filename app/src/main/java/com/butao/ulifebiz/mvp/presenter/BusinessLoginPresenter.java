package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.mvp.model.BusinessLoginModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;
import com.butao.ulifebiz.mvp.view.BusinessLoginView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class BusinessLoginPresenter extends BasePresenter<BusinessLoginView> {
    public BusinessLoginPresenter(BusinessLoginView view){
        attachView(view);
    }
    /**
     * 商户登录
     */
    public void loadBusinessLogin(String userName,String password){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userName",userName);
        map.put("password",password);
        addSubscription(apiServices.loadLogin(map),
                new ApiCallback<BaseModel<BusinessLoginModel>>() {
                    @Override
                    public void onSuccess(BaseModel<BusinessLoginModel> model) {
                        mvpView.getLoginSuccess(model);
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
     * 信息补全
     */
    public void loadStoreInfo( Map<String,String > map){
        mvpView.showLoading();
        addSubscription(apiServices.loadStoreInfo(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getStoreInfoSuccess(model);
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
     * 上传图片
     */
    public void loadIMG(File file){
        mvpView.showLoading();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile",file.getName(),requestBody);
        addSubscription(apiServices.loadPushImg(body),
                new ApiCallback<BaseModel<WareImgModel>>() {
                    @Override
                    public void onSuccess(BaseModel<WareImgModel> model) {
                        mvpView.getIMGSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getFail(msg);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }
}

package com.butao.ulifebiz.mvp.presenter;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;
import com.butao.ulifebiz.mvp.view.AddProductView;
import com.butao.ulifebiz.mvp.view.ProductsView;
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
public class AddProductPresenter extends BasePresenter<AddProductView> {
    public AddProductPresenter(AddProductView view){
        attachView(view);
    }
    /**
     * 13.获取分类列表
     */
    public void loadProductClass(){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("storeId", CApplication.getIntstance().getStoreId());
        addSubscription(apiServices.getWareclass(map),
                new ApiCallback<BaseModel<ProdcutClassModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ProdcutClassModel> model) {
                        mvpView.getProductClassSuccess(model);
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
    /**
     * 商品添加修改
     */
    public void AddWares(String id,String name,String price,String kuSum,String cid,String remark ,String mainImg){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("id", id);
        map.put("name", name);
        map.put("price", price);
        map.put("kuSum", kuSum);
        map.put("cid", cid);
        map.put("remark", remark);
        map.put("mainImg", mainImg);
        addSubscription(apiServices.AddUpdateWare(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getAddProductSuccess(model);
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

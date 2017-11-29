package com.butao.ulifebiz.mvp.presenter;

import android.text.TextUtils;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.view.ProductClassView;
import com.butao.ulifebiz.mvp.view.ProductsView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ProductClassPresenter extends BasePresenter<ProductClassView> {
    public ProductClassPresenter(ProductClassView view){
        attachView(view);
    }
    /**
     * 添加商品分类
     */
    public void AddWareClas(String id,String name){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        if(!TextUtils.isEmpty(id)) {
            map.put("id", id);
        }
            map.put("name", name);
        addSubscription(apiServices.AddProductClass(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getEditProductSuccess(model);
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
     * 删除分类
     */
    public void DeleteWareClas(String id){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("id",id);
        addSubscription(apiServices.DelteProductClass(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getDeleteProductSuccess(model);
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
     * 分类排序置顶
     */
    public void SortWareClass(String id){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("token", CApplication.getIntstance().getToken());
        map.put("id",id);
        addSubscription(apiServices.loadProductClassSort(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getProductClassSortSuccess(model);
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
}

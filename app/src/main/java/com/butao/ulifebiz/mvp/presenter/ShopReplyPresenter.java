package com.butao.ulifebiz.mvp.presenter;

import android.text.TextUtils;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.view.ShopEvaView;
import com.butao.ulifebiz.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopReplyPresenter extends BasePresenter<ShopEvaView> {
    public ShopReplyPresenter(ShopEvaView view){
        attachView(view);
    }
    /**
     * 门店评论列表
     * 1.storeId:门店ID
     2.type:1：差评未回复，2：未回复，3：已回复
     */
    public void loadShopStoreEvas(int pageNum,String type){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("pageNum",pageNum+"");
        map.put("pageSize","10");
        map.put("type",type);
        map.put("storeId", CApplication.getIntstance().getStoreId());
        addSubscription(apiServices.loadShopStoreEvas(map),
                new ApiCallback<BaseModel<ShopEvaModel>>() {
                    @Override
                    public void onSuccess(BaseModel<ShopEvaModel> model) {
                        mvpView.getShopEvasSuccess(model);
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
     * 1.userIdOrStoreId:用户ID
     2.reviewerId：评论ID
     3.remark:内容
     4.reviewerName：回复人名称
     5.unReviewerName：被回复人名称
     6.imgUrl：头像

     */
    public void loadStoreReply(String reviewerId,String remark,String reviewerName,String unReviewerName,String imgUrl){
        mvpView.showLoading();
        Map<String,String > map = new HashMap<>();
        map.put("userIdOrStoreId", CApplication.getIntstance().getStoreId());
        map.put("reviewerId", reviewerId);
        map.put("remark", remark);
        map.put("reviewerName", reviewerName);
        map.put("unReviewerName", unReviewerName);
        if(!TextUtils.isEmpty(imgUrl))
        map.put("imgUrl", imgUrl);
        addSubscription(apiServices.loadStoreReply(map),
                new ApiCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel model) {
                        mvpView.getReplySuccess(model);
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

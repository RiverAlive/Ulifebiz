package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.BusinessLoginModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface BusinessLoginView extends BaseView {
    void getLoginSuccess(BaseModel<BusinessLoginModel> model);
    void getIMGSuccess(BaseModel<WareImgModel> model);
    void getStoreInfoSuccess(BaseModel model);
    void getFail(String model);
}

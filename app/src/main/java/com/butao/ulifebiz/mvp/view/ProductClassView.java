package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface ProductClassView extends BaseView {
    void getEditProductSuccess(BaseModel model);
    void getProductClassSuccess(BaseModel<ProdcutClassModel> model);
    void getDeleteProductSuccess(BaseModel model);
    void getProductClassSortSuccess(BaseModel model);
    void getFail(String model);
}

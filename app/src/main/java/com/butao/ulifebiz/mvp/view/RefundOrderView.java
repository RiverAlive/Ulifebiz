package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.order.RefundOrderModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface RefundOrderView extends BaseView {
    void getRFOrderSuccess(BaseModel<RefundOrderModel> model);
    void getRefundSuccess(BaseModel model);
    void getRemindSuccess(BaseModel model);
    void getRMOrderSuccess(BaseModel<RefundOrderModel> model);
    void getFail(String model);
}

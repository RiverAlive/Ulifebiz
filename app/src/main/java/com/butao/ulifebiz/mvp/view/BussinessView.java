package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.shop.BussinessInfo;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface BussinessView extends BaseView {
    void getBussinessInfoSuccess(BaseModel<BussinessInfo> model);
    void getStoreAutomaticSuccess(BaseModel model);
    void getFail(String model);
}

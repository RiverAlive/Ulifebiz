package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.BriefModel;
import com.butao.ulifebiz.mvp.model.HomeTabModel;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface HomeTabView extends BaseView {
    void getHomeSuccess(BaseModel<HomeTabModel> model);
    void getBriefSuccess(BaseModel<BriefModel> model);
    void getRefundSuccess(BaseModel model);
    void getAcceptOrderSuccess(BaseModel model);
    void getFail(String model);
}

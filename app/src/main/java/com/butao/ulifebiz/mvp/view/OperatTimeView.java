package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface OperatTimeView extends BaseView {
    void getOperatTimeSuccess(BaseModel<OperatTimeModel> model);
    void getDeleteTimeSuccess(BaseModel<OperatTimeModel> model);
    void getFail(String model);
}

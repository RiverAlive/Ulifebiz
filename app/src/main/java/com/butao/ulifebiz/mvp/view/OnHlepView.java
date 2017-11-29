package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.OnLineHelp;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface OnHlepView extends BaseView {
    void getHelpListSuccess(BaseModel<OnLineHelp> model);
    void getHelpSuccess(BaseModel model);
    void getFail(String model);
}

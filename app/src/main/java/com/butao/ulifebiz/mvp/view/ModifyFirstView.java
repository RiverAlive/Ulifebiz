package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;

/**
 * 创建时间 ：2017/10/10.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public interface ModifyFirstView extends BaseView {
    void getModifyFirstSuccess(BaseModel model);
    void getFail(String model);
}

package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;

import java.util.Map;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public interface ContactView extends BaseView {
    void getContactPhoneSuccess(BaseModel<Map<String,String>> model);
    void getContactSuccess(BaseModel model);
    void getFail(String model);
}

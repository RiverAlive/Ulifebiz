package com.butao.ulifebiz.mvp.activity.shop.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.presenter.ModfiyPhonePresenter;
import com.butao.ulifebiz.mvp.view.ModifyPhoneView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/10/10.
 * 编写人 ：bodong
 * 功能描述 ：修改外卖电话
 */

public class ModifyPhoneActivity extends MvpActivity<ModfiyPhonePresenter> implements ModifyPhoneView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_phone)
    EditText edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        txtTitle.setText("修改外卖电话");

    }

    @Override
    protected ModfiyPhonePresenter createPresenter() {
        return new ModfiyPhonePresenter(this);
    }

    @Override
    public void getModifyPhoneSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("修改成功");
            finish();
        }else{
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @OnClick({R.id.ll_back, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_submit:
                String phone = edtPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    ToastUtils.showShortToast("请输入外卖电话号码");
                    edtPhone.requestFocus();
                    return;
                }
                mvpPresenter.loadModifyPhone(phone);
                break;
        }
    }
}

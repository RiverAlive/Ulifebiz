package com.butao.ulifebiz.mvp.activity.shop.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.presenter.ModfiyBoxPresenter;
import com.butao.ulifebiz.mvp.presenter.ModfiyPhonePresenter;
import com.butao.ulifebiz.mvp.view.ModifyBoxView;
import com.butao.ulifebiz.mvp.view.ModifyPhoneView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/10/10.
 * 编写人 ：bodong
 * 功能描述 ：修改外卖电话
 */

public class ModifyBoxActivity extends MvpActivity<ModfiyBoxPresenter> implements ModifyBoxView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_phone)
    EditText edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        txtTitle.setText("修改打包费");
        edtPhone.setHint("请输入打包费");
    }

    @Override
    protected ModfiyBoxPresenter createPresenter() {
        return new ModfiyBoxPresenter(this);
    }

    @Override
    public void getModifyBoxSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("修改成功");
            finish();
        }else{
            ToastUtils.showShortToast(String.valueOf(model.getData()));
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
                String price = edtPhone.getText().toString();
                if(TextUtils.isEmpty(price)){
                    ToastUtils.showShortToast("请输入打包费");
                    edtPhone.requestFocus();
                    return;
                }
                mvpPresenter.loadModifyBoxFee(price);
                break;
        }
    }
}

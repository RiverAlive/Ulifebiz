package com.butao.ulifebiz.mvp.activity;

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
import com.butao.ulifebiz.mvp.dialog.RegisterDetailsDialog;
import com.butao.ulifebiz.mvp.presenter.RegisterPresenter;
import com.butao.ulifebiz.mvp.view.RegisterView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/18.
 * 编写人 ：bodong
 * 功能描述 ：注册
 */
public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterView {
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.txt_getCode)
    TextView txtGetCode;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    @Bind(R.id.edt_repwd)
    EditText edtRrepwd;
    String phone ="",code="",pwd="",repwd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    public void getRegisterSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("注册成功");
            finish();
        }else{
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getCodeSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("验证码已发送到你手机上");
        }else{
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }
//    @OnClick(R.id.txt_datermine)
//    public void onClick() {
//        RegisterDetailsDialog detailsDialog = new RegisterDetailsDialog(RegisterActivity.this);
//        detailsDialog.dialogShow();
//    }

    @OnClick({R.id.txt_getCode, R.id.txt_datermine, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
               finish();
                break;
            case R.id.txt_getCode:
                phone = edtPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    ToastUtils.showShortToast("请输入手机号");
                    edtPhone.requestFocus();
                    return;
                }
                mvpPresenter.loadvalidCode(phone);
                break;
            case R.id.txt_datermine:
                phone = edtPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    ToastUtils.showShortToast("请输入手机号");
                    edtPhone.requestFocus();
                    return;
                }
                code = edtCode.getText().toString();
                if(TextUtils.isEmpty(code)){
                    ToastUtils.showShortToast("请输入验证码");
                    edtCode.requestFocus();
                    return;
                }
                pwd = edtPwd.getText().toString();
                if(TextUtils.isEmpty(pwd)){
                    ToastUtils.showShortToast("请输入密码");
                    edtPwd.requestFocus();
                    return;
                }
                repwd = edtRrepwd.getText().toString();
                if(TextUtils.isEmpty(repwd)){
                    ToastUtils.showShortToast("请输入确认密码");
                    edtRrepwd.requestFocus();
                    return;
                }
                if(pwd.equals(repwd)){
                    mvpPresenter.loadStoreRegister(phone,code,pwd);
                }else{
                    ToastUtils.showShortToast("两次输入的密码不一样，请重新输入");
                    edtPwd.requestFocus();
                    edtPwd.setText("");
                    edtRrepwd.setText("");
                    pwd="";
                    repwd="";
                    return;
                }
                break;
        }
    }
}

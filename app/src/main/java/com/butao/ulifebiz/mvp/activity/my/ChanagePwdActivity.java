package com.butao.ulifebiz.mvp.activity.my;

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
import com.butao.ulifebiz.mvp.activity.MainTabActivity;
import com.butao.ulifebiz.mvp.presenter.ChanagePwdPresenter;
import com.butao.ulifebiz.mvp.view.ChanagePwdView;
import com.butao.ulifebiz.util.ButtonUtils;
import com.butao.ulifebiz.util.InputMethodUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/29.
 * 编写人 ：bodong
 * 功能描述 ：修改密码
 */
public class ChanagePwdActivity extends MvpActivity<ChanagePwdPresenter> implements ChanagePwdView{
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_old_pwd)
    EditText edtOldPwd;
    @Bind(R.id.edt_new_pwd)
    EditText edtNewPwd;
    @Bind(R.id.edt_renew_pwd)
    EditText edtRenewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chagepwd);
        ButterKnife.bind(this);
        txtTitle.setText("修改密码");
    }

    @Override
    protected ChanagePwdPresenter createPresenter() {
        return new ChanagePwdPresenter(this);
    }

    @Override
    public void getChanagePwdSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("密码修改成功");
            finish();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @OnClick({R.id.ll_back, R.id.txt_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_save:
                InputMethodUtil.hideInput(ChanagePwdActivity.this);
                String oldpd = edtOldPwd.getText().toString();
                String newpd = edtNewPwd.getText().toString();
                String renewpd = edtRenewPwd.getText().toString();
                if ("".equals(oldpd) || oldpd == null || oldpd.length() <= 0) {
                    ToastUtils.showShortToast("原密码为空，请输入原密码");
                    edtOldPwd.requestFocus();
                    return;
                }
                if (!"".equals(newpd) && newpd != null && newpd.length() > 0) {
                    if (newpd.length() < 6 && newpd.length() > 16) {
                        ToastUtils.showShortToast("密码长度为6-16位");
                        edtNewPwd.requestFocus();
                        return;
                    }
                } else {
                    edtNewPwd.requestFocus();
                    ToastUtils.showShortToast("新密码为空，请输入新密码");
                    return;
                }
                if (!"".equals(renewpd) && oldpd != null && renewpd.length() > 0) {
                    if (renewpd.length() < 6 && renewpd.length() > 16) {
                        ToastUtils.showShortToast("密码长度为6-16位");
                        edtNewPwd.requestFocus();
                        return;
                    }
                } else {
                    edtNewPwd.requestFocus();
                    ToastUtils.showShortToast("确认密码为空，请输入确认密码");
                    return;
                }
                if (!newpd.equals(renewpd)) {
                    ToastUtils.showShortToast("确认密码与新密码不同，请重新输入！");
                    edtNewPwd.requestFocus();
                    return;
                }
                if (!ButtonUtils.isFastDoubleClick(1000)) {
                    mvpPresenter.loadChanagePwd(oldpd, newpd);
                }
                break;
        }
    }
}

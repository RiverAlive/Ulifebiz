package com.butao.ulifebiz.mvp.activity.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.dialog.PhoneDialog;
import com.butao.ulifebiz.mvp.presenter.ContactPresenter;
import com.butao.ulifebiz.mvp.view.ContactView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/29.
 * 编写人 ：bodong
 * 功能描述 ：联系我们
 */
public class ContactUsActivity extends MvpActivity<ContactPresenter> implements ContactView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    String phone = "";
    @Bind(R.id.edt_remark)
    EditText edtRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        ButterKnife.bind(this);
        txtTitle.setText("联系我们");
        mvpPresenter.loadContactPhone();
    }

    @Override
    protected ContactPresenter createPresenter() {
        return new ContactPresenter(this);
    }

    @Override
    public void getContactPhoneSuccess(BaseModel<Map<String, String>> model) {
        if ("200".equals(model.getCode())) {
            Map<String, String> map = model.getData();
            phone = map.get("managerPhone");
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getContactSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("提交成功");
            finish();
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }



    @OnClick({R.id.ll_back, R.id.txt_submit, R.id.txt_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_submit:
                String remark = edtRemark.getText().toString();
                if(TextUtils.isEmpty(remark)){
                    ToastUtils.showShortToast("请输入提交信息");
                    edtRemark.requestFocus();
                    return;
                }
                mvpPresenter.loadCallus(remark);
                break;
            case R.id.txt_phone:
                if(!TextUtils.isEmpty(phone)) {
                    PhoneDialog phoneDialog = new PhoneDialog(ContactUsActivity.this, phone);
                    phoneDialog.dialogShow();
                }else {
                    ToastUtils.showShortToast("暂无联系电话");
                }
                break;
        }
    }
}

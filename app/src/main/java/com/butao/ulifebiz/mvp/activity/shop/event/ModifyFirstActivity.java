package com.butao.ulifebiz.mvp.activity.shop.event;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.presenter.ModfiyFirstPresenter;
import com.butao.ulifebiz.mvp.presenter.ModfiyPhonePresenter;
import com.butao.ulifebiz.mvp.view.ModifyFirstView;
import com.butao.ulifebiz.mvp.view.ModifyPhoneView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/10/10.
 * 编写人 ：bodong
 * 功能描述 ：修改首次减免
 */

public class ModifyFirstActivity extends MvpActivity<ModfiyFirstPresenter> implements ModifyFirstView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_price)
    EditText edtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_first);
        ButterKnife.bind(this);
        txtTitle.setText("首次减免");
    }

    @Override
    protected ModfiyFirstPresenter createPresenter() {
        return new ModfiyFirstPresenter(this);
    }

    @Override
    public void getModifyFirstSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
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
                String price = edtPrice.getText().toString();
                if(TextUtils.isEmpty(price)){
                    ToastUtils.showShortToast("请输入首次减免金额");
                    edtPrice.requestFocus();
                    return;
                }
                mvpPresenter.loadModifyFirst(price);
                break;
        }
    }
}

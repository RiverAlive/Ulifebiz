package com.butao.ulifebiz.mvp.activity.shop.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.presenter.ModfiyStatePresenter;
import com.butao.ulifebiz.mvp.view.ModifyStateView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ModifyStateActivity extends MvpActivity<ModfiyStatePresenter> implements ModifyStateView{
    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_state);
        ButterKnife.bind(this);
        txtTitle.setText("修改营业状态");
    }

    @Override
    protected ModfiyStatePresenter createPresenter() {
        return  new ModfiyStatePresenter(this);
    }

    @Override
    public void getModifyStateSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("状态修改成功");
            finish();
        }else{
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @OnClick({R.id.ll_back, R.id.txt_normal, R.id.txt_suspend, R.id.txt_rest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_normal:
                mvpPresenter.loadStoreStatus("1");
                break;
            case R.id.txt_suspend:
                mvpPresenter.loadStoreStatus("4");
                break;
            case R.id.txt_rest:
                mvpPresenter.loadStoreStatus("5");
                break;
        }
    }
}

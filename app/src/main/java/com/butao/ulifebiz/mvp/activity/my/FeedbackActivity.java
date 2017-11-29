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
import com.butao.ulifebiz.mvp.presenter.FeedBackPresenter;
import com.butao.ulifebiz.mvp.view.FeedBackView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/29.
 * 编写人 ：bodong
 * 功能描述 ：意见反馈
 */
public class FeedbackActivity extends MvpActivity<FeedBackPresenter> implements FeedBackView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_remark)
    EditText edtRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        txtTitle.setText("意见反馈");
    }

    @Override
    protected FeedBackPresenter createPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    public void getFeedBackSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            ToastUtils.showShortToast("反馈信息提交成功");
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
                String remark = edtRemark.getText().toString();
                if(TextUtils.isEmpty(remark)){
                    ToastUtils.showShortToast("请输入反馈信息");
                    edtRemark.requestFocus();
                    return;
                }
                mvpPresenter.loadFeedBack(remark);
                break;
        }
    }
}

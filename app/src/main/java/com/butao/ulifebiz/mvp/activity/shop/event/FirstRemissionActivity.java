package com.butao.ulifebiz.mvp.activity.shop.event;

import android.os.Bundle;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建时间 ：2017/8/26.
 * 编写人 ：bodong
 * 功能描述 ：首次减免title（新用户券）
 */
public class FirstRemissionActivity extends MvpActivity{
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_remission);
        ButterKnife.bind(this);
        txtTitle.setText("新用户券");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}

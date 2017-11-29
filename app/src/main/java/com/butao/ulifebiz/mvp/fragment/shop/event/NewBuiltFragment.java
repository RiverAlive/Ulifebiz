package com.butao.ulifebiz.mvp.fragment.shop.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.mvp.activity.shop.event.DailySpecialActivity;
import com.butao.ulifebiz.mvp.activity.shop.event.FirstRemissionActivity;
import com.butao.ulifebiz.mvp.activity.shop.event.FullDownEventActivity;
import com.butao.ulifebiz.mvp.dialog.PickTime;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/20.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class NewBuiltFragment extends MvpFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_built, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.txt_full_down, R.id.txt_special, R.id.txt_First})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_full_down://店铺满减
                startActivity(getActivity(), FullDownEventActivity.class);
                break;
            case R.id.txt_special://天天特价
                startActivity(getActivity(), DailySpecialActivity.class);
                break;
            case R.id.txt_First://首次减免
                startActivity(getActivity(), FirstRemissionActivity.class);
                break;
        }
    }
}

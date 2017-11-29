package com.butao.ulifebiz.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * mvp模式基础fragment
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    public MvpFragment() {
        super();
    }

    public MvpFragment(CApplication application, Activity activity,
                       Context context) {
        cApplication = application;
        mActivity = activity;
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = createPresenter();
        cApplication = (CApplication) getActivity().getApplication();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    /**
     * 显示加载狂
     */
    public void showLoading() {
        showProgressDialog();
    }

    /**
     * 隐藏加载狂
     */
    public void hideLoading() {
        dismissProgressDialog();
    }
}

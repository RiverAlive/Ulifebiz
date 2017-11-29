package com.butao.ulifebiz.base;

import android.os.Bundle;


/**
 * mvp模式基础activity
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

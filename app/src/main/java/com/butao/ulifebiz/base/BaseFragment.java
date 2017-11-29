package com.butao.ulifebiz.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.butao.ulifebiz.util.AutoUtils;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Fragment基础类
 */
public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    public CApplication cApplication;
    public Context mContext;

    public BaseFragment() {
        super();
    }

    public BaseFragment(CApplication application, Activity activity,
                        Context context) {
        cApplication = application;
        mActivity = activity;
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        AutoUtils.auto(mActivity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cApplication = (CApplication) getActivity().getApplication();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }

    private CompositeSubscription mCompositeSubscription;


    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void addSubscription(Subscription subscription) {
//        if (mCompositeSubscription == null) {
        mCompositeSubscription = new CompositeSubscription();
//        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Context mContext, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Context mContext, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 显示弹框
     */
    public void showProgressDialog() {
        BaseDialog.show(getActivity());
    }

    /**
     * 显示弹框，并控制点击消失和加载显示的文字
     */
    public void showProgressDialog(boolean cancelable, String message) {
        BaseDialog.showable(getActivity(), message, cancelable);
    }

    /**
     * 关闭弹框
     */
    public void dismissProgressDialog() {
        BaseDialog.dismiss(getActivity());
    }
}

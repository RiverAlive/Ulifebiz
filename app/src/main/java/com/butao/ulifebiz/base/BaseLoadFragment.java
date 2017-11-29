package com.butao.ulifebiz.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
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
public abstract class BaseLoadFragment extends Fragment {
    public Activity mActivity;
    public CApplication cApplication;
    public Context mContext;
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    public BaseLoadFragment() {
        super();
    }
    private View view;
    public BaseLoadFragment(CApplication application, Activity activity,
                            Context context) {
        cApplication = application;
        mActivity = activity;
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        AutoUtils.auto(mActivity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        ButterKnife.bind(this, view);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        AutoUtils.auto(mActivity);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cApplication = (CApplication) getActivity().getApplication();
    }
    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
        onUnsubscribe();
    }

    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShortToast(message);
        }

    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {

        return (T) getContentView().findViewById(id);
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
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

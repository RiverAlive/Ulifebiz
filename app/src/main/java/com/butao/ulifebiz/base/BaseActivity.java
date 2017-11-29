package com.butao.ulifebiz.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.Utils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.toolbar.StatusBarUtil;
import com.butao.ulifebiz.util.AutoUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * activity基础
 */
public abstract class BaseActivity extends AutoLayoutActivity {
    public Activity mActivity;
    private CompositeSubscription mCompositeSubscription;
    private List<Call> calls;
    protected CApplication cApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cApplication = CApplication.getIntstance();
        ActivityManager.getInstance().addActivity(this);
        AutoUtils.auto(this);
        Utils.init(this);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 50);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
        Utils.init(mActivity);
        setStatusBar();
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
        Utils.init(mActivity);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;
        Utils.init(mActivity);
    }


    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        ActivityManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
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
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }
    /**
     * 显示弹框
     */
    public void showProgressDialog() {
        BaseDialog.show(BaseActivity.this);
    }

    /**
     * 显示弹框，并控制点击消失和加载显示的文字
     */
    public void showProgressDialog(boolean cancelable, String message) {
        BaseDialog.showable(BaseActivity.this, message, cancelable);
    }

    /**
     * 关闭弹框
     */
    public void dismissProgressDialog() {
        BaseDialog.dismiss(BaseActivity.this);
    }
    /**
     * @param activity 起始对象
     * @param cls      跳转对象
     * @Title: gotoActivity
     * @Description: 界面跳转
     */
    protected void gotoActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        startActivity(intent);
    }

    /**
     * @param activity 起始对象
     * @param cls      跳转对象
     * @param buld     参数
     * @Title: gotoActivity
     * @Description: 界面跳转
     */
    protected void gotoActivity(Activity activity, Class<?> cls, Bundle buld) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(buld);
        startActivity(intent);
    }
}

package com.butao.ulifebiz.mvp.activity.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.activity.shop.settle.AccountSettleActivity;
import com.butao.ulifebiz.mvp.fragment.order.RefundOrderFragment;
import com.butao.ulifebiz.mvp.fragment.order.RemindOrderFragment;
import com.butao.ulifebiz.mvp.fragment.shop.settle.HasAccountFragment;
import com.butao.ulifebiz.mvp.fragment.shop.settle.WaitAccountFragment;
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/28.
 * 编写人 ：bodong
 * 功能描述 ：订单提醒
 */
public class OrderRemindActivity extends MvpActivity {
    public static OrderRemindActivity remindActivity = null;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_remind)
    TextView txtRemind;
    @Bind(R.id.txt_refund)
    TextView txtRefund;
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    @Bind(R.id.viewpager)
    NoScrollViewPager mViewPager;
    int remindNum = 0;
    int refundNum = 0;
int select=0;
    RemindOrderFragment remindOrderFragment ;
    RefundOrderFragment refundOrderFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_remind);
        ButterKnife.bind(this);
        remindActivity = this;
        txtTitle.setText("订单提醒");
        initFragmentList();
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    public void rfTabTitle(int cui, int tui) {
        if (cui != 0)
            remindNum = cui;
        if (tui != 0)
            refundNum = tui;
        if (cui == 0 && tui == 0) {
            remindNum = cui;
            refundNum = tui;
        }
        String remind = "催单(" + remindNum + ")";
        String refund = "退款(" + refundNum + ")";
        txtRefund.setText(refund);
        txtRemind.setText(remind);
    }

    private void initFragmentList() {
        if (fragments.size() > 0) {
            fragments.clear();
        }
         remindOrderFragment = new RemindOrderFragment();
        refundOrderFragment = new RefundOrderFragment();
        fragments.add(remindOrderFragment);
        fragments.add(refundOrderFragment);
    }


    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.txt_remind, R.id.txt_refund})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_remind:
                select = 0;
                onChangeSelect();
                mViewPager.setCurrentItem(0);
                remindOrderFragment.loadOrder();
                break;
            case R.id.txt_refund:
                select = 1;
                onChangeSelect();
                mViewPager.setCurrentItem(1);
                refundOrderFragment.loadOrder();
                break;
        }
    }

    private void onChangeSelect() {//viewpager切换或者点击按钮时页面上的切换tab效果
        switch (select) {
            case 0:
                txtRemind.setTextColor(getResources().getColor(R.color.white));
                txtRemind.setBackgroundColor(getResources().getColor(R.color.login_mian));
                txtRefund.setTextColor(getResources().getColor(R.color.login_mian));
                txtRefund.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 1:
                txtRefund.setTextColor(getResources().getColor(R.color.white));
                txtRefund.setBackgroundColor(getResources().getColor(R.color.login_mian));
                txtRemind.setTextColor(getResources().getColor(R.color.login_mian));
                txtRemind.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
    }
    public class FragAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;

        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            // TODO Auto-generated constructor stub
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mFragments.size();
        }

    }
}

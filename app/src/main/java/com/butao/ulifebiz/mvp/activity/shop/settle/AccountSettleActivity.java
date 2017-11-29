package com.butao.ulifebiz.mvp.activity.shop.settle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.fragment.shop.event.InBuiltFragment;
import com.butao.ulifebiz.mvp.fragment.shop.event.NewBuiltFragment;
import com.butao.ulifebiz.mvp.fragment.shop.settle.HasAccountFragment;
import com.butao.ulifebiz.mvp.fragment.shop.settle.WaitAccountFragment;
import com.butao.ulifebiz.util.DoubleUtil;
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/6.
 * 编写人 ：bodong
 * 功能描述 ：账户结算
 */
public class AccountSettleActivity extends MvpActivity {
    public static AccountSettleActivity  settleActivity=null;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.txt_wait_account)
    TextView txtWaitAccount;
    @Bind(R.id.txt_status)
    TextView txtStatus;
    @Bind(R.id.txt_price)
    TextView txtPrice;
    @Bind(R.id.txt_has_account)
    TextView txtHasAccount;
    private List<Fragment> fragments = new ArrayList<>();//定义要装fragment的列表
    private int built = 0;  //0 代入帐   1 已入帐
    private final int INBUILT = 0;//代入帐
    private final int NEWBUILT = 1;//已入帐
    WaitAccountFragment accountFragment ;
    HasAccountFragment hasAccountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settle);
        ButterKnife.bind(this);
        settleActivity = this;
        initFragmentList();
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initFragmentList() {
        if (fragments.size() > 0) {
            fragments.clear();
        }
        accountFragment=new WaitAccountFragment();
        hasAccountFragment=new HasAccountFragment();
        fragments.add(accountFragment);
        fragments.add(hasAccountFragment);
    }
    public void setTextView(String text){
     txtPrice.setText(DoubleUtil.KeepTwoDecimal(text)+"£");
    }

    @OnClick({R.id.img_back, R.id.txt_wait_account, R.id.txt_has_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_wait_account:
                built = 0;
                onChangeSelect();
                mViewPager.setCurrentItem(0);
                txtStatus.setText("代入帐");
                accountFragment.loadAccount();
                break;
            case R.id.txt_has_account:
                built = 1;
                onChangeSelect();
                mViewPager.setCurrentItem(1);
                txtStatus.setText("已入帐");
                hasAccountFragment.loadAccount();
                break;
        }
    }

    private void onChangeSelect() {//viewpager切换或者点击按钮时页面上的切换tab效果
        switch (built) {
            case INBUILT:
                txtWaitAccount.setTextColor(getResources().getColor(R.color.white));
                txtWaitAccount.setBackgroundResource(R.drawable.shape_left_event_true);
                txtHasAccount.setTextColor(getResources().getColor(R.color.login_mian));
                txtHasAccount.setBackgroundResource(R.drawable.shape_right_event);
                break;
            case NEWBUILT:
                txtWaitAccount.setTextColor(getResources().getColor(R.color.login_mian));
                txtWaitAccount.setBackgroundResource(R.drawable.shape_left_event);
                txtHasAccount.setTextColor(getResources().getColor(R.color.white));
                txtHasAccount.setBackgroundResource(R.drawable.shape_right_event_true);
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


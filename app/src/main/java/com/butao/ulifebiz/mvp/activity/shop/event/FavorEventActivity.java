package com.butao.ulifebiz.mvp.activity.shop.event;

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
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/24.
 * 编写人 ：bodong
 * 功能描述 ：优惠活动（已创建活动和创建活动）
 */
public class FavorEventActivity extends MvpActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_in_built)
    TextView txtInBuilt;
    @Bind(R.id.txt_new_built)
    TextView txtNewBuilt;
    @Bind(R.id.viewpager)
    NoScrollViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();//定义要装fragment的列表
    private int built = 0;  //0 已建活动   1 新建活动
    private final int INBUILT = 0;//已建活动
    private final int NEWBUILT = 1;//新建活动
    InBuiltFragment inBuiltFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facor_event);
        ButterKnife.bind(this);
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
        inBuiltFragment =new InBuiltFragment();
        fragments.add(inBuiltFragment);
        fragments.add(new NewBuiltFragment());
    }

    @OnClick({R.id.img_back, R.id.txt_in_built, R.id.txt_new_built})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_in_built:
                built = 0;
                onChangeSelect();
                mViewPager.setCurrentItem(0);
                inBuiltFragment.LoadCutList();
                break;
            case R.id.txt_new_built:
                built = 1;
                onChangeSelect();
                mViewPager.setCurrentItem(1);
                break;
        }
    }
    private void onChangeSelect() {//viewpager切换或者点击按钮时页面上的切换tab效果
        switch (built) {
            case INBUILT:
                txtInBuilt.setTextColor(getResources().getColor(R.color.white));
                txtInBuilt.setBackgroundResource(R.drawable.shape_left_event_true);
                txtNewBuilt.setTextColor(getResources().getColor(R.color.login_mian));
                txtNewBuilt.setBackgroundResource(R.drawable.shape_right_event);
                break;
            case NEWBUILT:
                txtInBuilt.setTextColor(getResources().getColor(R.color.login_mian));
                txtInBuilt.setBackgroundResource(R.drawable.shape_left_event);
                txtNewBuilt.setTextColor(getResources().getColor(R.color.white));
                txtNewBuilt.setBackgroundResource(R.drawable.shape_right_event_true);
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

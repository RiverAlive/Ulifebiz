package com.butao.ulifebiz.mvp.activity.shop;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.fragment.order.RefundOrderFragment;
import com.butao.ulifebiz.mvp.fragment.order.RemindOrderFragment;
import com.butao.ulifebiz.mvp.fragment.shop.CustomerReplyFragment;
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/3.
 * 编写人 ：bodong
 * 功能描述 ：顾客回复
 */
public class CustomerReplyActivity extends MvpActivity {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    private TabLayout mTabLayout;
    private String[] TAB_Title = new String[]{"未回复差评//Non response margin", "未回复//Not reply","已回复//Reply"};
    //Fragment 数组
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private MyViewPagerAdapter mAdapter;
    private NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reply);
        ButterKnife.bind(this);
        txtTitle.setText("顾客评价");
        initViews();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_Title);
        initFragmentList();
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mAdapter.setLists(fragments);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        TAB_Title = new String[]{"催单（2）", "退款（3）"};
//        mTabLayout.removeAllTabs();
//        setTabs(mTabLayout, this.getLayoutInflater(), TAB_Title);
    }

    private void initFragmentList() {
        if (fragments.size() > 0) {
            fragments.clear();
        }
        fragments.add(new CustomerReplyFragment().getInstance("1"));
        fragments.add(new CustomerReplyFragment().getInstance("2"));
        fragments.add(new CustomerReplyFragment().getInstance("3"));
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, String[] TAB_Title) {
        for (int i = 0; i < TAB_Title.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_customer, null);
            tab.setCustomView(view);
            TextView txtTab = (TextView) view.findViewById(R.id.txt_title);
            TextView txtContent = (TextView) view.findViewById(R.id.txt_content);
            txtTab.setText(TAB_Title[i].split("//")[0]);
            txtContent.setText(TAB_Title[i].split("//")[1]);
            tabLayout.addTab(tab);

        }
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }


    /**
     * @description: ViewPager 适配器
     */
    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> mList = new ArrayList<>();

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setLists(ArrayList<Fragment> lists) {
            this.mList.addAll(lists);
        }

        public void UpdateList(ArrayList<Fragment> arrayList) {
            this.mList.clear();
            this.mList.addAll(arrayList);

            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

}

package com.butao.ulifebiz.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpLoadFragment;
import com.butao.ulifebiz.mvp.fragment.order.OrderCancelFragment;
import com.butao.ulifebiz.mvp.fragment.order.OrderStatusFragment;
import com.butao.ulifebiz.mvp.fragment.order.OrderTrueFragment;
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class OrderTabFragment extends MvpLoadFragment {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.img_right)
    ImageView imgRight;
    private TabLayout mTabLayout;
    private String[] TAB_Title = new String[]{"已确认", "已完成", "已无效"};
    //Fragment 数组
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private MyViewPagerAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    OrderStatusFragment orderStatusFragment;
    OrderCancelFragment cancelFragment;
    OrderTrueFragment orderTrueFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_tab, null);
        initViews(view, savedInstanceState);
        ButterKnife.bind(this, view);
        txtTitle.setText("订单");
        imgBack.setVisibility(View.GONE);
        imgRight.setVisibility(View.GONE);
        imgRight.setImageResource(R.mipmap.search);
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_order_tab;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initViews(View view, Bundle bundle) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        setTabs(mTabLayout, this.getLayoutInflater(bundle), TAB_Title);
        initFragmentList();
        mAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        mAdapter.setLists(fragments);
        mViewPager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    orderStatusFragment.LoadOrder("11");
//                }else if(tab.getPosition()==1){
//                    orderTrueFragment.LoadOrder("20");
//                }else if(tab.getPosition()==2){
//                    cancelFragment.LoadOrder("-1");
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    private void initFragmentList() {
        if (fragments.size() > 0) {
            fragments.clear();
        }
        orderStatusFragment = new OrderStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type","12");
        orderStatusFragment.setArguments(bundle);
        orderTrueFragment = new OrderTrueFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("type","20");
        orderTrueFragment.setArguments(bundle1);
        cancelFragment = new OrderCancelFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("type","-1");
        cancelFragment.setArguments(bundle2);
        fragments.add(orderStatusFragment);
        fragments.add(orderTrueFragment);
        fragments.add(cancelFragment);
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, String[] TAB_Title) {
        for (int i = 0; i < TAB_Title.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_order, null);
            tab.setCustomView(view);
            TextView txtTab = (TextView) view.findViewById(R.id.txt_title);
            txtTab.setText(TAB_Title[i]);
            tabLayout.addTab(tab);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.ll_right)
    public void onClick() {
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

package com.butao.ulifebiz.mvp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.ActivityManager;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.fragment.HomeFragment;
import com.butao.ulifebiz.mvp.fragment.MyTabFragment;
import com.butao.ulifebiz.mvp.fragment.OrderTabFragment;
import com.butao.ulifebiz.mvp.fragment.ShopFragment;
import com.butao.ulifebiz.toolbar.StatusBarUtil;
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * 创建时间 ：2017/8/20.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MainTabActivity extends MvpActivity {
    private int eId = 0;
    private TabLayout mTabLayout;
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{R.drawable.bg_tab_home, R.drawable.bg_tab_shop, R.drawable.bg_tab_order, R.drawable.bg_tab_my};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{new HomeFragment(), new ShopFragment(), new HomeFragment(), new HomeFragment()};
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    //Tab 数目
    private final int COUNT = TAB_IMGS.length;
    private MyViewPagerAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    int p = 0;
    private String homePage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintab);
        initViews();
    }
    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }



    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_IMGS);
        initFragmentList();
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mAdapter.setLists(fragments);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
        Bundle bundle = getIntent().getExtras();
        int position;
        if (bundle != null) {
            position = bundle.getInt("position");
            eId = bundle.getInt("eId");
            if (position > 0) {
                mViewPager.setCurrentItem(position);
                mTabLayout.getTabAt(position).select();
            }
        }
    }

    private void initFragmentList() {
        if (fragments.size() > 0) {
            fragments.clear();
        }
        fragments.add(new HomeFragment());
        fragments.add(new ShopFragment());
        fragments.add(new OrderTabFragment());
        fragments.add(new MyTabFragment());
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
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

    private long exitTime = 0;

    //重写 onKeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().AppExit(MainTabActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

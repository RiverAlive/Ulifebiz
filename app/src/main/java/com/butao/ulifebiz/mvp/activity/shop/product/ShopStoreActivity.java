package com.butao.ulifebiz.mvp.activity.shop.product;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.fragment.shop.product.ShopEvaluateFragment;
import com.butao.ulifebiz.mvp.fragment.shop.product.ShopProductFragment;
import com.butao.ulifebiz.mvp.model.shop.ShopStoreModel;
import com.butao.ulifebiz.mvp.presenter.ShopStorePresenter;
import com.butao.ulifebiz.mvp.view.ShopStoreView;
import com.butao.ulifebiz.view.NoScrollViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/4.
 * 编写人 ：bodong
 * 功能描述 ：我的门店
 */
public class ShopStoreActivity extends MvpActivity<ShopStorePresenter> implements ShopStoreView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_price_time)
    TextView txtPriceTime;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.img_right)
    ImageView imgRight;
    @Bind(R.id.txt_storename)
    TextView txtStorename;
    @Bind(R.id.img_store)
    ImageView imgStore;
    @Bind(R.id.txt_status)
    TextView txtStatus;
    @Bind(R.id.img_cart)
    ImageView imgCart;
    @Bind(R.id.txt_jian)
    TextView txtJian;
    @Bind(R.id.txt_new)
    TextView txtNew;
    @Bind(R.id.txt_te)
    TextView txtTe;
    private TabLayout mTabLayout;
    private String[] TAB_Title = new String[]{"商品\ncommodity", "评价\nevaluate"};
    //Fragment 数组
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private MyViewPagerAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    ShopStoreModel shopStoreModel = new ShopStoreModel();
    String timeprice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_store);
        ButterKnife.bind(this);
        txtTitle.setText("我的门店");
        txtRightTitle.setVisibility(View.VISIBLE);
        txtRightTitle.setText("商品管理");
        imgRight.setImageResource(R.mipmap.shangpinguanli);
        imgRight.setVisibility(View.VISIBLE);
        initViews();
        txtStorename.setText(cApplication.getStoreName());
        if (!TextUtils.isEmpty(cApplication.getStatus())) {
            if ("1".equals(cApplication.getStatus())) {
                imgCart.setBackgroundResource(R.mipmap.cart_yellow);
                txtStatus.setBackgroundColor(getResources().getColor(R.color.login_mian));
            } else {
                imgCart.setBackgroundResource(R.mipmap.cart_hui);
                txtStatus.setBackgroundColor(getResources().getColor(R.color.bg_app));
                txtStatus.setText("暂不营业\nNo business");
            }
        }
        mvpPresenter.loadShopStore();
    }

    @Override
    protected ShopStorePresenter createPresenter() {
        return new ShopStorePresenter(this);
    }

    @Override
    public void getShopStoreSuccess(BaseModel<ShopStoreModel> model) {
        if ("200".equals(model.getCode())) {
            shopStoreModel = model.getData();
            if (shopStoreModel != null) {
                if (!TextUtils.isEmpty(shopStoreModel.getImgUrl())) {
                    Glide.with(ShopStoreActivity.this).load(shopStoreModel.getImgUrl()).placeholder(R.mipmap.tupian_nei).into(imgStore);
                }else{
                    imgStore.setBackgroundResource(R.mipmap.tupian_nei);
                }
                    if (!TextUtils.isEmpty(shopStoreModel.getStoreName()))
                    txtStorename.setText(shopStoreModel.getStoreName());
                if (!TextUtils.isEmpty(shopStoreModel.getStartPrice()))
                    timeprice = "起送价£" + shopStoreModel.getStartPrice();
                if (!TextUtils.isEmpty(shopStoreModel.getSendPrice()))
                    timeprice = timeprice + "  配送费£" + shopStoreModel.getSendPrice();
                if (!TextUtils.isEmpty(shopStoreModel.getLongTime()))
                    timeprice = timeprice + "  送达时间" + shopStoreModel.getLongTime();
                txtPriceTime.setText(timeprice);
                if (shopStoreModel.getDayCuts() != null) {
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.te);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtTe.setCompoundDrawables(drawable_n, null, null, null);
                    txtTe.setCompoundDrawablePadding(5);
                }else{
                    txtTe.setVisibility(View.GONE);
                }
                if (shopStoreModel.getFullCuts() != null) {
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.jian);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtJian.setCompoundDrawables(drawable_n, null, null, null);
                    txtJian.setCompoundDrawablePadding(5);
                }else{
                    txtJian.setVisibility(View.GONE);
                }
                if (shopStoreModel.getFirstCutPrice() != null) {
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.p_new);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtNew.setCompoundDrawables(drawable_n, null, null, null);
                    txtNew.setCompoundDrawablePadding(5);
                }else{
                    txtNew.setVisibility(View.GONE);
                }
            }
        } else {
            ToastUtils.showLongToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {

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
        fragments.add(new ShopProductFragment().getInstance("未回复"));
        fragments.add(new ShopEvaluateFragment().getInstance("未回复"));
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, String[] TAB_Title) {
        for (int i = 0; i < TAB_Title.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_shop_store, null);
            tab.setCustomView(view);
            TextView txtTab = (TextView) view.findViewById(R.id.txt_title);
            txtTab.setText(TAB_Title[i]);
            tabLayout.addTab(tab);
            setIndicator(tabLayout, 40, 40);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                gotoActivity(ShopStoreActivity.this, ProductSettingActivity.class);
                break;
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

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }
}

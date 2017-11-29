package com.butao.ulifebiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.butao.ulifebiz.R;
import com.butao.ulifebiz.adapter.GuideViewPagerAdapter;
import com.butao.ulifebiz.base.BaseActivity;


public class GuideActivity extends BaseActivity implements OnPageChangeListener {
	private ViewPager vp;
	private GuideViewPagerAdapter vpAdapter;
	private List<View> views;
	private LinearLayout ll;
	// 底部小点图片
	private ImageView[] dots;
	// 记录当前选中位置
	private int currentIndex;

	@SuppressLint("UseSparseArrays")
	private Map<Integer, View> viewMap = new HashMap<Integer, View>();
	// 引导图片资源
	private static final int images_slide[] = { R.mipmap.yindaoye_a,
			R.mipmap.yindaoye_b, R.mipmap.yindaoye_c };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		// 初始化页面
		initViews();

		// 初始化底部小点
		initDots();
	}


	private void initDots() {
		// TODO Auto-generated method stub
		ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[views.size()];
		// 循环取得小点图片
		for (int i = 0; i < images_slide.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			// 下面小点，初始化所有项都使能状态
			dots[i].setEnabled(true);
			// 设置位置tag，方便取出与当前位置对应
			dots[i].setTag(i);
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态。

	}

	private void setCurrentDot(int pos) {
		if (pos < 0 || pos > views.size() - 1 || currentIndex == pos) {
			return;
		}

		dots[pos].setEnabled(false);
		dots[currentIndex].setEnabled(true);
		currentIndex = pos;
	}

	@SuppressWarnings("unused")
	private void setCurrentView(int position) {
		if (position < 0 || position >= images_slide.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	private void initViews() {
		// TODO Auto-generated method stub

		try {
			views = new ArrayList<View>();

			for (int i = 0; i < images_slide.length; i++) {
				View view = getView(i, images_slide[i]);
				views.add(view);
			}

			// 初始化adapter
			vpAdapter = new GuideViewPagerAdapter(views, this);

			vp = (ViewPager) findViewById(R.id.vPager);
			vp.setAdapter(vpAdapter);
			// 绑定回调
			vp.setOnPageChangeListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public View getView(int arg0, int image) {
		if(viewMap == null)
			return null;
		View rowView = this.viewMap.get(arg0);

		try {
			if (rowView == null) {

				LayoutInflater inflater = this.getLayoutInflater();
				rowView = inflater.inflate(R.layout.guide_item, null);
				ImageView image_item = (ImageView) rowView.findViewById(R.id.image);
//				image_item.setBackgroundResource(image);
				Glide.with(GuideActivity.this).load(image).into(image_item);
				viewMap.put(arg0, rowView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowView;
	}

	// 当滑动状态改变时调用
	public void onPageScrollStateChanged(int arg0) {
	}

	// 当当前页面被滑动时调用
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurrentDot(arg0);
	}

}

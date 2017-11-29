package com.butao.ulifebiz.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;



import java.util.List;

import com.butao.ulifebiz.R;


public class GuideViewPagerAdapter extends PagerAdapter {
	// 界面列表
	private List<View> views;
	private Activity activity;
	private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

	public GuideViewPagerAdapter(List<View> vs, Activity ac) {
		// TODO Auto-generated constructor stub
		this.views = vs;
		this.activity = ac;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(views.get(position));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub
		super.finishUpdate(container);
	}

	// 初始化position位置的界面
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub

		((ViewPager) container).addView(views.get(position), 0);
		if (position == views.size() - 1) {
			ImageView image = (ImageView) container.findViewById(R.id.image);
			image.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// 设置已经引导
					setGuide();
					geHome();

				}
			});
		}
		return views.get(position);

	}

	protected void geHome() {
		// 跳转
//		Intent intent = new Intent(activity, LoginActivity.class);
//		activity.startActivity(intent);
//		activity.finish();
	}

	// 设置已经引导过了，下次启动不用再次引导
	protected void setGuide() {
		SharedPreferences settings = activity.getSharedPreferences(
				"AppConfig", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(KEY_GUIDE_ACTIVITY, "false");
		editor.commit();
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}

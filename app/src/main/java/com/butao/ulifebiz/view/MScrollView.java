package com.butao.ulifebiz.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 有弹性的ScrollView 实现下拉弹回和上拉弹回
 */
public class MScrollView extends ScrollView {
	private OnScrollListener onScrollListener;
	/**
	 * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
	 */
	private int lastScrollY;

	/**
	 * 设置滚动接口
	 *
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	/**
	 * 滚动的回调接口
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 */
		void onScroll(int scrollY);
	}

	/**
	 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
	 */
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = MScrollView.this.getScrollY();

			//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
			if (lastScrollY != scrollY) {
				lastScrollY = scrollY;
				handler.sendMessageDelayed(handler.obtainMessage(), 5);
			}
			if (onScrollListener != null) {
				onScrollListener.onScroll(scrollY);
			}

		}

	};

	/**
	 * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
	 * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
	 * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
	 * MyScrollView滑动的距离
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (onScrollListener != null) {
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_UP:
				handler.sendMessageDelayed(handler.obtainMessage(), 20);
				break;
		}
		return super.onTouchEvent(ev);
	}

	private static final float MOVE_FACTOR = 0.4f;
	private View contentView;
	private static final int ANIM_TIME = 400;
	private float startY;
	private Rect originalRect = new Rect();
	private boolean canPullDown = false;
	private boolean canPullUp = false;
	private boolean isMoved = false;
	public boolean scroll = false;
	public MScrollView(Context context) {
		super(context);
	}

	public MScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			contentView = getChildAt(0);
		}
		this.smoothScrollTo(0, 20);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (contentView == null)
			return;
		originalRect.set(contentView.getLeft(), contentView.getTop(), contentView.getRight(), contentView.getBottom());
	}

	/**
	 * 在触摸事件中, 处理上拉和下拉的逻辑
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(!scroll){
			if (contentView == null) {
				return super.dispatchTouchEvent(ev);
			}
			int action = ev.getAction();
			switch (action) {
				case MotionEvent.ACTION_DOWN:
					canPullDown = isCanPullDown();
					canPullUp = isCanPullUp();
					startY = ev.getY();
					break;
				case MotionEvent.ACTION_UP:
					if (!isMoved)
						break;
					TranslateAnimation anim = new TranslateAnimation(0, 0, contentView.getTop(), originalRect.top);
					anim.setDuration(ANIM_TIME);
					contentView.startAnimation(anim);
					contentView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
					canPullDown = false;
					canPullUp = false;
					isMoved = false;
					break;
				case MotionEvent.ACTION_MOVE:
					if (!canPullDown && !canPullUp) {
						startY = ev.getY();
						canPullDown = isCanPullDown();
						canPullUp = isCanPullUp();
						break;
					}
					float nowY = ev.getY();
					int deltaY = (int) (nowY - startY);
					boolean shouldMove = (canPullDown && deltaY > 0)
							|| (canPullUp && deltaY < 0)
							|| (canPullUp && canPullDown);
					if (shouldMove) {
						int offset = (int) (deltaY * MOVE_FACTOR);
						contentView.layout(originalRect.left, originalRect.top + offset, originalRect.right, originalRect.bottom + offset);
						isMoved = true;
					}
					break;
				default:
					break;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/** 监听滚动到顶部和底部的接口 */
	public interface OnBorderListener {
		/** 顶部 **/
		void onTop();
		/** 底部 **/
		void onBottom();
	}
	OnBorderListener onBorderListener;

	public void setOnBorderListener(OnBorderListener onBorderListener) {
		this.onBorderListener = onBorderListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (contentView != null && contentView.getMeasuredHeight() - 100 <= getScrollY() + getHeight()) {
			if (onBorderListener != null) {
				onBorderListener.onBottom();
			}
		} else if (getScrollY() == 0) {
			if (onBorderListener != null) {
				onBorderListener.onTop();
			}
		}
	}



	/**
	 * 判断是否滚动到顶部
	 */
	private boolean isCanPullDown() {
		return getScrollY() == 0 || contentView.getHeight() < getHeight() + getScrollY();
	}

	/**
	 * 判断是否滚动到底部
	 */
	private boolean isCanPullUp() {
		return contentView.getHeight() <= getHeight() + getScrollY();
	}

}
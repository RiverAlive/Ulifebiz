package com.butao.ulifebiz.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * 创建时间 ：2017/5/26.
 * 编写人 ：bodong
 * 功能描述 ：加载更多布局
 */
public class LoadMoreView extends FrameLayout implements IBottomView {
    private ImageView refreshArrow;
    private ImageView loadingView;
    private TextView refreshTextView;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), com.lcodecore.tkrefreshlayout.R.layout.view_sinaheader, null);
        refreshArrow = (ImageView) rootView.findViewById(com.lcodecore.tkrefreshlayout.R.id.iv_arrow);
        refreshTextView = (TextView) rootView.findViewById(com.lcodecore.tkrefreshlayout.R.id.tv);
        loadingView = (ImageView) rootView.findViewById(com.lcodecore.tkrefreshlayout.R.id.iv_loading);
        refreshTextView.setText("");
        refreshArrow.setVisibility(GONE);
        addView(rootView);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText("加载更多...");
        loadingView.setVisibility(VISIBLE);
        ((AnimationDrawable) loadingView.getDrawable()).start();
    }


    @Override
    public void reset() {
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {
    }

    @Override
    public void onFinish() {

    }
}

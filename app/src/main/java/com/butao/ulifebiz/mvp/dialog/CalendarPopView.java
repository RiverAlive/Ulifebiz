package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.butao.ulifebiz.R;
import com.maning.calendarlibrary.MNCalendar;
import com.maning.calendarlibrary.listeners.OnCalendarItemClickListener;
import com.maning.calendarlibrary.model.Lunar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建时间 ：2017/9/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class CalendarPopView extends PopupWindow {
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    View contentView;
    MNCalendar mnCalendar;
    public CalendarPopView() {
    }

    public CalendarPopView(Activity activity) {
        super(activity);
        mActivity = activity;
        initview();
    }
    private void initview() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.dialog_clanerview;   // 布局ID
        contentView = LayoutInflater.from(mActivity).inflate(layoutId, null);
        mnCalendar = (MNCalendar)contentView.findViewById(R.id.mnCalendar);

        /**
         * Item点击监听
         */
        mnCalendar.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {

            @Override
            public void onClick(Date date, Lunar lunar) {
                //阳历转换阴历
//                Lunar solarToLunar = LunarCalendarUtils.solarToLunar(date);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                //Toast日期
//                String launarString = lunar.lunarYear + "-" + lunar.lunarMonth + "-" + lunar.lunarDay;
                CalendarListener.onSelect(sdf2.format(date));
                mPopupWindow.dismiss();
            }

            @Override
            public void onLongClick(Date date) {
            }
        });
    }

    public void showPopupWindow() {
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0,0);
    }
    public interface CalendarListener {
        void onSelect(String time);
    }
    CalendarListener CalendarListener;
    public void setCalendarListener(CalendarListener listener) {
        CalendarListener = listener;
    }
}

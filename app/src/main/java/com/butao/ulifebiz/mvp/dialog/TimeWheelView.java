package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.mvp.activity.shop.setting.AddOperatingTimeActivity;
import com.butao.ulifebiz.view.wheelview.adapter.AbstractWheelTextAdapter;
import com.butao.ulifebiz.view.wheelview.adapter.NumericWheelAdapter;
import com.butao.ulifebiz.view.wheelview.widget.OnWheelChangedListener;
import com.butao.ulifebiz.view.wheelview.widget.OnWheelScrollListener;
import com.butao.ulifebiz.view.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * 创建时间 ：2017/9/7.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class TimeWheelView  {
    private WheelView hours;
    private WheelView mins;
    private  static Activity mActivity;
    //控件
    private CalendarTextAdapter mHourAdapter;
    private CalendarTextAdapter msecondAdapter;
    //变量
    private ArrayList<String> arry_Hours = new ArrayList<String>();
    //变量
    private ArrayList<String> arry_second = new ArrayList<String>();
    private int nowHourId = 0;
    private int nowsecondId = 0;
    private String mHourStr;
    private String msecondStr;
    //常量
    private final int MAX_TEXT_SIZE = 22;
    private final int MIN_TEXT_SIZE = 16;
    boolean flag = false;
    public TimeWheelView(final Activity activity,  WheelView hours,WheelView mins)
    {
        this.mActivity = activity;
        this.hours=hours;
        this.mins=mins;
    }
    public void setTime(int nowHourId,int nowsecondId,boolean flag){
        this.nowHourId = nowHourId;
        this.nowsecondId = nowsecondId;
        this.flag=flag;
        initHours();
        initsecond();
        initListener();
    }
    public void setTime(){
        initHours();
        initsecond();
        initListener();
    }
    public void initsecond(){
        Calendar nowCalendar = Calendar.getInstance();
        int nowsecond = nowCalendar.get(Calendar.MINUTE);
        arry_second.clear();
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                arry_second.add("0" + i+"");
            } else {
                arry_second.add(i + "");
            }
            if (nowsecondId == 0){
                if (nowsecond == i) {
                    nowsecondId = arry_second.size() - 1;
                }
            }
        }

        msecondAdapter = new CalendarTextAdapter(mActivity, arry_second, nowsecondId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mins.setVisibleItems(3);
        mins.setViewAdapter(msecondAdapter);
        mins.setCurrentItem(nowsecondId);
        msecondStr = arry_second.get(nowsecondId);
    }
    /**
     * 初始化年
     */
    private void initHours() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.HOUR_OF_DAY);
        arry_Hours.clear();
        for (int i = 0; i <= 23; i++) {
            if(i<10){
                arry_Hours.add("0"+ i+"");
            }else{
                arry_Hours.add(i + "");
            }
            if (nowHourId == 0) {
                if (nowYear == i) {
                    nowHourId = arry_Hours.size() - 1;
                }
            }
        }

        mHourAdapter = new CalendarTextAdapter(mActivity, arry_Hours, nowHourId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        hours.setVisibleItems(3);
        hours.setViewAdapter(mHourAdapter);
        hours.setCurrentItem(nowHourId);
        mHourStr = arry_Hours.get(nowHourId);
    }
    /**
     * 初始化滚动监听事件
     */
    private void initListener() {
        //小时*****************************
        hours.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mHourAdapter);
                mHourStr = arry_Hours.get(wheel.getCurrentItem()) + "";
                AddOperatingTimeActivity.timeActivity.setStartStopTime(mHourStr+":"+msecondStr);
            }
        });

        hours.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mHourAdapter);
            }
        });
        //分钟*****************************
        mins.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) msecondAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, msecondAdapter);
                msecondStr = arry_second.get(wheel.getCurrentItem()) + "";
                AddOperatingTimeActivity.timeActivity.setStartStopTime(mHourStr+":"+msecondStr);
            }
        });

        mins.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) msecondAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, msecondAdapter);
            }
        });
    }
    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(MAX_TEXT_SIZE);
                textvew.setTextColor(mActivity.getResources().getColor(R.color.login_mian));
            } else {
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(mActivity.getResources().getColor(R.color.login_mian));
            }
        }
    }
    /**
     * 滚轮的adapter
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.wheel_text_item, R.id.txt_wheel, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index) + "";
            return str;
        }
    }

    public String Weektimes(){
        return mHourStr+":"+msecondStr;
    }

}

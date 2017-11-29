package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.butao.ulifebiz.R;

import java.util.Calendar;

import cn.qqtheme.framework.picker.TimePicker;

/**
 * 创建时间 ：2017/9/21.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class PickTime {
    Activity mActivity;
   public PickTime(Activity activity){
        mActivity = activity;
       onTimePicker();
    }
    public void onTimePicker() {
        TimePicker picker = new TimePicker(mActivity, TimePicker.HOUR_24);
        picker.setUseWeight(true);
        picker.setCycleDisable(false);
        picker.setGravity(Gravity.BOTTOM);//弹框居中
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        picker.setTextColor(mActivity.getResources().getColor(R.color.login_mian));
        picker.setCancelTextColor(mActivity.getResources().getColor(R.color.login_mian));
        picker.setSubmitTextColor(mActivity.getResources().getColor(R.color.login_mian));
        picker.setLabelTextColor(mActivity.getResources().getColor(R.color.login_mian));
        picker.setDividerColor(mActivity.getResources().getColor(R.color.login_mian));
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.setTopLineVisible(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                String time=hour + ":" + minute;
                imGlistener.time(time);
            }
        });
        picker.show();
    }
    public interface PickTimelistener{
        void time(String time);
    }
    public  PickTimelistener imGlistener;

    public void setPickTimelistener(PickTimelistener imGlistener) {
        this.imGlistener = imGlistener;
    }
}

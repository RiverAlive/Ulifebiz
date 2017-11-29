package com.maning.calendarlibrary.widget.listener;


import com.maning.calendarlibrary.widget.DateScrollerDialog;

/**
 * 日期设置的监听器
 *
 * @author C.L. Wang
 */
public interface OnDateSetListener {
    void onDateSet(DateScrollerDialog timePickerView, long milliseconds, long milliFinishSeconds);
}

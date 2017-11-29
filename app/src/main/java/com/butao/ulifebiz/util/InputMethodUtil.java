package com.butao.ulifebiz.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by bodong on 2016/8/16.
 */
public class InputMethodUtil {
    public static void hideInput(Activity activity){
        View view = activity.getWindow().peekDecorView();//关闭输入框
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

package com.butao.ulifebiz.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.butao.ulifebiz.R;


/**
 * 创建时间 ：2017/3/22.
 * 编写人 ：gujc
 * 功能描述 ：公共加载框
 */
public class BaseDialog extends Dialog {
    /**
     * LoadDialog
     */
    private static BaseDialog loadDialog;
    /**
     * cancelable, the dialog dimiss or undimiss flag
     */
    private boolean cancelable =true;
    /**
     * if the dialog don't dimiss, what is the tips.
     */
    private String tipMsg;

    /**
     * the LoadDialog constructor
     *
     * @param ctx        Context
     * @param cancelable boolean
     * @param tipMsg     String
     */

    public BaseDialog(final Context ctx, boolean cancelable, String tipMsg) {
        super(ctx);

        this.cancelable = cancelable;
        this.tipMsg = tipMsg;
//android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth
        this.getContext().setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.dialog_loading_layout);
        // 必须放在加载布局后
        setparams();
        TextView tv = (TextView) findViewById(R.id.id_tv_loadingmsg);
        if (!TextUtils.isEmpty(tipMsg)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(tipMsg);
        }
    }

    private void setparams() {
        this.setCancelable(cancelable);
        this.setCanceledOnTouchOutside(cancelable);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // Dialog宽度
//        lp.width = (int) (display.getWidth() * 0.7);
        Window window = getWindow();
        window.setAttributes(lp);
        window.getDecorView().getBackground().setAlpha(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!cancelable) {
                Toast.makeText(getContext(), tipMsg, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * show the dialog
     *
     * @param context
     */
    public static void show(Context context) {
        show(context, null, true);
    }

    /**
     * show the dialog
     *
     * @param context Context
     * @param message String
     */
    public static void show(Context context, String message) {
        show(context, message, true);
    }

    /**
     * show the dialog
     *
     * @param context Context
     * @param message String
     * @param cable boolean
     */
    public static void showable(Context context, String message, boolean cable) {
        show(context, message, cable);
    }
    /**
     * show the dialog
     *
     * @param context    Context
     * @param resourceId resourceId
     */
    public static void show(Context context, int resourceId) {
        show(context, context.getResources().getString(resourceId), true);
    }

    /**
     * show the dialog
     *
     * @param context    Context
     * @param message    String, show the message to user when isCancel is true.
     * @param cancelable boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean cancelable) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new BaseDialog(context, cancelable, message);
        loadDialog.show();
    }

    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }
}


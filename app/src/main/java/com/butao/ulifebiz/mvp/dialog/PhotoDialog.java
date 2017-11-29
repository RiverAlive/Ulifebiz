package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;


/**
 * Created by Administrator on 2016/4/28.
 */
public class PhotoDialog  {
    OnphotoListener onphotoListener;
    private  static Activity mActivity;

    public PhotoDialog(final Activity activity)
    {
        this.mActivity = activity;
        showDialog(activity);
    }
    public void dialogShow()
    {
        if (backdialog != null)
        {
            backdialog.show();
        }
    }
    public void SetOnphotolistener(OnphotoListener onphotoListener) {
        this.onphotoListener = onphotoListener;
    }

    Dialog backdialog = null;
    private void showDialog(final Activity activity) {
        backdialog = new Dialog(activity, R.style.back_dialog);
        backdialog.setContentView(R.layout.dialog_photo);
        backdialog.setCanceledOnTouchOutside(true);
        backdialog.findViewById(R.id.btn_zl_camera).setOnClickListener(l);
        backdialog.findViewById(R.id.btn_zl_photo).setOnClickListener(l);
        backdialog.findViewById(R.id.btn_zl_center).setOnClickListener(l);
    }


    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_zl_camera:
                    if (onphotoListener != null) {
                        onphotoListener.confirm(1);
                    }
                    backdialog.dismiss();
                    break;
                case R.id.btn_zl_photo:
                    if (onphotoListener != null) {
                        onphotoListener.confirm(0);
                    }
                    backdialog.dismiss();
                    break;
                case R.id.btn_zl_center:
                    if (onphotoListener != null) {
                        onphotoListener.confirm(2);
                    }
                    backdialog.dismiss();
                    break;
                default:
                    backdialog.dismiss();
                    break;
            }
        }
    };
}

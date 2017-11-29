package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.butao.ulifebiz.R;


/**
 * Created by Administrator on 2016/4/28.
 */
public class OnPhotoDialog extends PopupWindow {
    OnphotoListener onphotoListener;
    Activity activity1;

    public void SetOnphotolistener(OnphotoListener onphotoListener) {
        this.onphotoListener = onphotoListener;
    }

    public OnPhotoDialog(Activity activity) {
        super(activity);
        activity1 = activity;
        initView(activity);
    }

    private void initView(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_head_shangchuan1, null);
        view.findViewById(R.id.btn_zl_camera).setOnClickListener(l);
        view.findViewById(R.id.btn_zl_photo).setOnClickListener(l);
        view.findViewById(R.id.btn_zl_center).setOnClickListener(l);
        setContentView(view);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        View view1=view.findViewById(R.id.view);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_zl_camera:
                    if (onphotoListener != null) {
                        onphotoListener.confirm(1);
                    }
                    dismiss();
                    break;
                case R.id.btn_zl_photo:
                    if (onphotoListener != null) {
                        onphotoListener.confirm(0);
                    }
                    dismiss();
                    break;
                case R.id.btn_zl_center:
                    if (onphotoListener != null) {
                        onphotoListener.confirm(2);
                    }
                    dismiss();
                    break;
                default:
                    dismiss();
                    break;
            }
        }
    };
}

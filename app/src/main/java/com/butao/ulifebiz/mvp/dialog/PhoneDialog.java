package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import com.butao.ulifebiz.R;

/**
 * 拨打电话
 * Created by bodong on 2017/2/22.
 */
public class PhoneDialog {
    private  static Activity activity;
    String phone ;
    public PhoneDialog(final Activity activity,  String phone)
    {
        this.activity = activity;
        this.phone = phone;
        showDialog(activity);
    }
    public void dialogShow()
    {
        if (backdialog != null)
        {
            backdialog.show();
        }
    }
    Dialog backdialog = null;
    private void showDialog(final Activity activity) {

        backdialog = new Dialog(activity, R.style.back_dialog);
        backdialog.setContentView(R.layout.dialog_phone);
        backdialog.setCanceledOnTouchOutside(true);
        TextView btncall = (TextView) backdialog.findViewById(R.id.btn_call);
        TextView btnback = (TextView) backdialog.findViewById(R.id.btn_back);
        TextView txt_phone = (TextView)backdialog.findViewById(R.id.txt_phone);
        if(!TextUtils.isEmpty(phone)){
            txt_phone.setText(phone);
        }else{
            phone = "400 012 3505";
            txt_phone.setText(phone);
        }
        btncall.setOnClickListener(listener);
        btnback.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    backdialog.dismiss();
                    break;
                case R.id.btn_call:
                    if(!TextUtils.isEmpty(phone)) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        phone = phone.replace(" ", "");
                        Uri data = Uri.parse("tel:" + phone);
                        intent.setData(data);
                        activity.startActivity(intent);
                    }else{
                        ToastUtils.showShortToast("手机号为空");
                    }
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}

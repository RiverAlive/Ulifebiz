package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;

/**
 * 拨打电话
 * Created by bodong on 2017/2/22.
 */
public class ReceiptDialog {
    private  static Activity activity;
    public ReceiptDialog(final Activity activity, String type)
    {
        this.activity = activity;
        showDialog(activity,type);
    }
    public void dialogShow()
    {
        if (backdialog != null)
        {
            backdialog.show();
        }
    }
    Dialog backdialog = null;
    private void showDialog(final Activity activity,String type) {

        backdialog = new Dialog(activity, R.style.back_dialog);
        backdialog.setContentView(R.layout.dialog_phone);
        backdialog.setCanceledOnTouchOutside(true);
        TextView  txt_title = (TextView) backdialog.findViewById(R.id.txt_title);
        TextView btncall = (TextView) backdialog.findViewById(R.id.btn_call);
        TextView btnback = (TextView) backdialog.findViewById(R.id.btn_back);
        TextView txt_phone = (TextView)backdialog.findViewById(R.id.txt_phone);
        txt_phone.setText("是否修改为"+type);
        btnback.setText("确定");
        btncall.setText("取消");
        txt_title.setVisibility(View.VISIBLE);
        btncall.setOnClickListener(listener);
        btnback.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                  refundlistener.Refund();
                    backdialog.dismiss();
                    break;
                case R.id.btn_call:
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    public interface Refundlistener{
        void Refund();
    }
    public  Refundlistener refundlistener;

    public void setRefundlistener(Refundlistener refundlistener) {
        this.refundlistener = refundlistener;
    }
}

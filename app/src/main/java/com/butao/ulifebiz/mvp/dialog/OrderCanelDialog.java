package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;

/**
 * 拨打电话
 * Created by bodong on 2017/2/22.
 */
public class OrderCanelDialog {
    private  static Activity activity;
    String type;
    public OrderCanelDialog(final Activity activity,String type)
    {
        this.activity = activity;
        this.type = type;
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
        backdialog.setContentView(R.layout.dialog_cancel_order);
        backdialog.setCanceledOnTouchOutside(true);
        TextView  txt_title = (TextView) backdialog.findViewById(R.id.txt_title);
        TextView btncall = (TextView) backdialog.findViewById(R.id.btn_call);
        TextView btnback = (TextView) backdialog.findViewById(R.id.btn_back);
        if("Refund".equals(type)){
            txt_title.setText("是否退款！");
        }
        txt_title.setVisibility(View.VISIBLE);
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
                    canellistener.OrderCanel();
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    public interface OrderCanellistener{
        void OrderCanel();
    }
    public  OrderCanellistener canellistener;

    public void setOrderCanellistener(OrderCanellistener canellistener) {
        this.canellistener = canellistener;
    }
}

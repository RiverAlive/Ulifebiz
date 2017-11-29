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
public class RemindDialog {
    private  static Activity activity;
    EditText edt_remark;
    public RemindDialog(final Activity activity)
    {
        this.activity = activity;
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
        backdialog.setContentView(R.layout.dialog_remind);
        backdialog.setCanceledOnTouchOutside(true);
        TextView  txt_title = (TextView) backdialog.findViewById(R.id.txt_title);
        TextView btncall = (TextView) backdialog.findViewById(R.id.btn_call);
        TextView btnback = (TextView) backdialog.findViewById(R.id.btn_back);
        edt_remark = (EditText)backdialog.findViewById(R.id.edt_remark);
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
                    String remark = edt_remark.getText().toString();
                    if(TextUtils.isEmpty(remark)){
                        edt_remark.requestFocus();
                        ToastUtils.showShortToast("请填写催单理由");
                        return;
                    }
                    remindlistener.Remind(remark);
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    public interface Remindlistener{
        void Remind(String remark);
    }
    public  Remindlistener remindlistener;

    public void setRemindlistener(Remindlistener remindlistener) {
        this.remindlistener = remindlistener;
    }
}

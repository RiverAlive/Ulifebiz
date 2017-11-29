package com.butao.ulifebiz.mvp.dialog.update;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.butao.ulifebiz.R;


/**
 * Created by User on 2016/6/28.
 */
public class UpdateDialog {
    private  static Activity activity;
    private  int Type=0;//默认软件更新对话框
    private ProgressBar m_pbSchedule;
    private TextView m_tvSchedule;
    public interface OnListener {
        void queding();
        void quxiao(int type);
    }
    OnListener listener;
    String version;
    String remark ;
    public void setOnListener(OnListener listener) {
        this.listener = listener;
    }
    public UpdateDialog(final Activity activity, int type, String version, String remark)
    {
        this.version = version;
        this.remark = remark;
        Type = type;
        if(Type == 0) {
            showDialog(activity);
        } else {
            showDialog2(activity);
        }
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
        backdialog.setContentView(R.layout.dialog_update);
        backdialog.setCanceledOnTouchOutside(true);
        Button que = (Button) backdialog.findViewById(R.id.btn_update);
        Button fan = (Button) backdialog.findViewById(R.id.btn_back);
        TextView txt_code = (TextView)backdialog.findViewById(R.id.txt_code);
        TextView txt_content = (TextView)backdialog.findViewById(R.id.txt_content);
        if(!TextUtils.isEmpty(version)){
            txt_code.setText("版本更新："+version);
        }
        if(!TextUtils.isEmpty(remark)){
            txt_content.setText(remark);
        }else{
            remark = "1.修复bug";
            txt_content.setText(remark);
        }
        que.setOnClickListener(clickListener);
        fan.setOnClickListener(clickListener);
    }
    private void showDialog2(final Activity activity) {

        backdialog = new Dialog(activity, R.style.back_dialog);
        backdialog.setContentView(R.layout.update_down);
        m_pbSchedule = (ProgressBar)backdialog.findViewById(R.id.pb_down_schedule);
        m_tvSchedule = (TextView)backdialog.findViewById(R.id.tv_down_schedule);
        Button fan = (Button) backdialog.findViewById(R.id.btn_down_back);
        fan.setOnClickListener(clickListener);
    }
    public void setProgress(int progress) {
        if(m_pbSchedule != null)
            m_pbSchedule.setProgress(progress);
        m_tvSchedule.setText(String.valueOf(progress) + "%");
    }
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_update:
                    backdialog.dismiss();
                    listener.queding();
                    break;
                case R.id.btn_back:
                    listener.quxiao(Type);
                    backdialog.dismiss();
                    break;
                case  R.id.btn_down_back:
                    listener.quxiao(Type);
                    backdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}

package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.butao.ulifebiz.R;


/**
 * Created by bodong on 2017/2/7.
 * 退出登录dialog
 */
public class LoginOutDialog {
    View rootView;
    TextView txtOut;
    TextView txtCancel;
    private Activity mActivity;
    private AlertDialog dialog;

    public LoginOutDialog(Activity context) {
        this.mActivity = context;
    }

    public void showDialog() {
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_login_out, (ViewGroup) mActivity.findViewById(R.id.dialog_taste));
        dialog = new AlertDialog.Builder(mActivity, AlertDialog.THEME_HOLO_LIGHT).create();
        dialog.setCanceledOnTouchOutside(true);
        txtOut = (TextView)rootView.findViewById(R.id.txt_out);
        txtCancel = (TextView)rootView.findViewById(R.id.txt_cancel);
        txtOut.setOnClickListener(Listener);
        txtCancel.setOnClickListener(Listener);
        dialog.setView(rootView);
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int mWidth = ScreenUtils.getScreenWidth();
        int mHeight = ScreenUtils.getScreenHeight();
        params.width = mWidth * 580 / 750;
        params.height = (mWidth * 580 / 750)*4/3;
        dialog.getWindow().setAttributes(params);
    }

  View.OnClickListener Listener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
    switch (v.getId()){
        case R.id.txt_out:
            loginOutlistener.LoginOut();
            closeDialog();
            break;
        case R.id.txt_cancel:
            closeDialog();
            break;
    }
      }
  };
    public void closeDialog() {
        dialog.cancel();
    }
    public interface LoginOutlistener{
        void LoginOut();
    }
    public LoginOutlistener loginOutlistener;

    public void setLoginOutlistener(LoginOutlistener loginOutlistener) {
        this.loginOutlistener = loginOutlistener;
    }
}

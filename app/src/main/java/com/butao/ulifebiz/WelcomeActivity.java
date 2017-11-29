package com.butao.ulifebiz;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.butao.ulifebiz.R;

import com.butao.ulifebiz.base.BaseActivity;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.activity.LoginActivity;
import com.butao.ulifebiz.mvp.activity.MainTabActivity;
import com.butao.ulifebiz.permissiongen.PermissionGen;
import com.butao.ulifebiz.util.LocationUtils;
import com.butao.ulifebiz.util.NetUtil;
import com.butao.ulifebiz.util.RegexUtils;


/**
 * 初始化界面
 *
 * @author gujs
 */
public class WelcomeActivity extends BaseActivity {

    /**
     * 自动登录标志
     */
    boolean longg_flag = false;
    private boolean isfirst_login;
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private ImageView imgBiz;
    CApplication app;
    int duration = 0;
    /**
     * 编辑expired_date中存储的数据
     */
    SharedPreferences.Editor editor;
    /**
     * 轻量级的存储类：主要是保存一些常用的配置
     */
    SharedPreferences sharepre;
    /**
     * Called when the activity is first created.
     */
    String sitesNum = "", storesNum = "";
    private TextView txt_site, txt_store;
    private LinearLayout ll_data;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    StartHome();
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharepre = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        editor = sharepre.edit();
        app = (CApplication) getApplication();
        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        String isFirstIn = sharepre.getString(KEY_GUIDE_ACTIVITY, "");
        imgBiz = (ImageView) findViewById(R.id.img_biz);
        txt_site = (TextView) findViewById(R.id.txt_site);
        txt_store = (TextView) findViewById(R.id.txt_store);
        ll_data = (LinearLayout) findViewById(R.id.ll_data);
//        PermissionGen.needPermission(this, 200, Manifest.permission.ACCESS_FINE_LOCATION);
        PermissionGen.needPermission(this, 200, Manifest.permission.READ_EXTERNAL_STORAGE);
        PermissionGen.needPermission(this, 200, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionGen.needPermission(this, 200, Manifest.permission.RECORD_AUDIO);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int mScreenHeight = metric.heightPixels;
        int mScreenWidth = metric.widthPixels;
        layout.setMargins(mScreenWidth * 3 / 20, mScreenHeight * 3 / 5, 0, 0);
        ll_data.setLayoutParams(layout);
        isfirst_login = !"false".equals(isFirstIn);
        ThreadTest threadTest = new ThreadTest();
        new Thread(threadTest).start();
    }


    private void StartHome() {
        boolean flag = NetUtil.NetInfoState(WelcomeActivity.this);
        if (flag) {
            Intent gotoIntent = null;
            if (isfirst_login) {
                SharedPreferences settings = WelcomeActivity.this.getSharedPreferences(
                        "AppConfig", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(KEY_GUIDE_ACTIVITY, "false");
                editor.commit();
                gotoIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            } else {
                if (app.isLogin()) {
                    gotoIntent = new Intent(WelcomeActivity.this, MainTabActivity.class);
                } else {
                    gotoIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
            }
            startActivity(gotoIntent);
            WelcomeActivity.this.finish();
        } else {
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("没有可用的网络连接哦")
                    .setPositiveButton("退出应用", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    }).setNegativeButton("网络设置", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {
                                intent = new Intent(
                                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName component = new ComponentName(
                                        "com.android.settings",
                                        "com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            startActivity(intent);
                        }
                    }).create();
            dialog.show();
            dialog.setCancelable(false);
        }
    }


    private class ThreadTest implements Runnable {

        public void run() {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msgMessage = new Message();
            msgMessage.arg1 = 1;
            handler.sendMessage(msgMessage);
            Log.e("ThreadName", Thread.currentThread().getName());
        }

    }

}

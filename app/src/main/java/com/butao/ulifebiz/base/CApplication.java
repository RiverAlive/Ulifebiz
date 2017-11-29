package com.butao.ulifebiz.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.cockroach.Cockroach;
import com.butao.ulifebiz.util.FileUtil;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;


/**
 * @author gujc
 * @fileName CApplication.java
 * @description 系统程序初始化
 */
public class CApplication extends MultiDexApplication {
    public static CApplication mApplication;
    public static MediaPlayer mediaPlayer;
    /**
     * 编辑expired_date中存储的数据
     */
    SharedPreferences.Editor editor;
    /**
     * 轻量级的存储类：主要是保存一些常用的配置
     */
    SharedPreferences sharepre;
    @Override
    public void onCreate() {
        super.onCreate();
        sharepre = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        editor = sharepre.edit();
        mApplication = this;
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("Cockroach", thread + "\n" + throwable.toString());
                            throwable.printStackTrace();
                            try {
                                FileUtil.writeToSDcard(mApplication.getApplicationContext(),throwable);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        } catch (Throwable e) {
                        }
                    }
                });
            }
        });
        AutoLayoutConifg.getInstance().useDeviceSize();
        mediaPlayer=createLocalMp3();
        // 卸载代码
//        Cockroach.uninstall();
    }
    public MediaPlayer createLocalMp3() {
        /**
         * 创建音频文件的方法：
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
         */
        MediaPlayer mp = MediaPlayer.create(this, R.raw.order);
        mp.stop();
        return mp;
    }

    public static CApplication getIntstance() {
        return mApplication;
    }
    /**
     * 是否登录
     *
     * @return
     */
    public boolean isSound() {
        return sharepre.getBoolean("Sound", false);
    }

    public void setSound(boolean isLogin) {
        editor.putBoolean("Sound", isLogin).commit();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return sharepre.getBoolean("LOGIN", false);
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean("LOGIN", isLogin).commit();
    }
    /**
     * 获取token
     * @return
     */
    public String getToken() {
        return sharepre.getString("TOKEN", null);
    }
    /**
     *保存token
     * @return
     */
    public void setToken(String token) {
        editor.putString("TOKEN", token).commit();
    }
    /**
     * 获取门店ID
     * @return
     */
    public String getStoreId() {
        return sharepre.getString("StoreId", null);
    }
    /**
     *保存门店ID
     * @return
     */
    public void setStoreId(String token) {
        editor.putString("StoreId", token).commit();
    }
    /**
     * 获取状态名称
     * @return
     */
    public String getStoreName() {
        return sharepre.getString("StoreName", null);
    }
    /**
     *保存状态名称
     * @return
     */
    public void setStoreName(String token) {
        editor.putString("StoreName", token).commit();
    }
    /**
     * 获取门店头像
     * @return
     */
    public String getLoginImg() {
        return sharepre.getString("LoginImg", null);
    }
    /**
     *保存门店头像
     * @return
     */
    public void setLoginImg(String token) {
        editor.putString("LoginImg", token).commit();
    }
    /**
     * 获取状态
     * @return
     */
    public String getStatusName() {
        return sharepre.getString("StatusName", null);
    }
    /**
     *保存状态
     * @return
     */
    public void setStatusName(String token) {
        editor.putString("StatusName", token).commit();
    }
    /**
     * 获取状态名称
     * @return
     */
    public String getStatus() {
        return sharepre.getString("Status", null);
    }
    /**
     *保存状态名称
     * @return
     */
    public void setStatus(String token) {
        editor.putString("Status", token).commit();
    }
}

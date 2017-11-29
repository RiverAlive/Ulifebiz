package com.butao.ulifebiz.jpush;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.activity.MainTabActivity;
import com.butao.ulifebiz.util.RegexUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static cn.jpush.android.api.JPushInterface.EXTRA_EXTRA;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[PushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[PushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[PushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[PushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JSONObject Object = null;
            String type = "";
            try {
                Object = new JSONObject(json);
                if (RegexUtils.containsString(String.valueOf(Object), "type")) {
                    type = (String) Object.getString("type");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.showShortToast(e.getMessage());
            }
            if (!TextUtils.isEmpty(type)) {
                if ("1".equals(type)) {
//                    processCustomMessage(context, bundle);//22以上用这个的时候要把这个方法注释掉的东西打开

                     //下面是我先用的方法解决的
                    if (CApplication.getIntstance().isLogin()) {
                        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                            if (!CApplication.getIntstance().isSound()) {
                                Media(true);
                            } else {
                                if (CApplication.getIntstance().isSound() && CApplication.getIntstance().mediaPlayer != null) {
                                    CApplication.getIntstance().setSound(false);
                                    CApplication.getIntstance().mediaPlayer = null;
                                    Media(true);
                                }
                            }
                        } else {
                            processCustomMessage(context, bundle);
                        }
                    }
                }
            }
            context.sendBroadcast(new Intent("Home"));

            Log.d(TAG, "[PushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[PushReceiver] 用户点击打开了通知");
            Media(false);
            Intent i = new Intent(context, MainTabActivity.class);
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[PushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[PushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[PushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(EXTRA_EXTRA)) {
                if (bundle.getString(EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    private void processCustomMessage(Context context, Bundle bundle) {
        NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //第一行内容  通常作为通知栏标题
//        builder.setContentTitle("Ulife商家端");
//        //第二行内容 通常是通知正文
//        builder.setContentText("您有一条新订单请及时处理");
//        //可以点击通知栏的删除按钮删除
//        builder.setAutoCancel(true);
//        //系统状态栏显示的小图标
//        builder.setSmallIcon(R.mipmap.biz_logo);

        Notification notification = null;
        try {
            notification = builder.build();
            notification.sound = Uri.parse("android.resource://com.butao.ulifebiz/" + R.raw.order);
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent clickIntent = new Intent(); //点击通知之后要发送的广播
        int id = (int) (System.currentTimeMillis() / 1000);
        clickIntent.addCategory(context.getPackageName());
        clickIntent.setAction(JPushInterface.ACTION_NOTIFICATION_OPENED);
        clickIntent.putExtra(EXTRA_EXTRA, bundle.getString(EXTRA_EXTRA));
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        manger.notify(id, notification);
    }


    private void Media(boolean flag) {
        MediaPlayer mediaPlayer;
        if (CApplication.getIntstance().mediaPlayer == null) {
            mediaPlayer = CApplication.getIntstance().createLocalMp3();
        } else {
            mediaPlayer = CApplication.getIntstance().mediaPlayer;
        }
        if (flag) {
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //开始播放音频
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();//释放音频资源
                    CApplication.getIntstance().setSound(false);
                    CApplication.getIntstance().mediaPlayer = null;
                }
            });
            CApplication.getIntstance().setSound(true);
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            CApplication.getIntstance().mediaPlayer = null;
            CApplication.getIntstance().setSound(false);
        }
    }


}
package com.butao.ulifebiz.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.mvp.activity.MainTabActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 创建时间 ：2017/10/27.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName();
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 3;   //推送通知最多显示条数

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle =intent.getExtras();
        //
        if(intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
            String title=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content=bundle.getString(JPushInterface.EXTRA_ALERT);
            String extra=bundle.getString(JPushInterface.EXTRA_EXTRA);
            processCustomMessage(context, bundle);
        }else if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
            String message =bundle.getString(JPushInterface.EXTRA_MESSAGE);

        }else if(intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)){
        }
    }

    /**
     * 实现自定义推送声音
     * @param context
     * @param bundle
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.biz_logo);

        Intent mIntent = new Intent(context,MainTabActivity.class);
        mIntent.putExtra("sex", "");
        mIntent.putExtras(bundle);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);

        notification.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText("123")
                .setContentTitle("title")
                .setSmallIcon(R.mipmap.biz_logo)
                .setNumber(NOTIFICATION_SHOW_SHOW_AT_MOST);

        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    String sound = extraJson.getString("type");
                    if("1".equals(sound)){
                        notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.order));
                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_SHOW_SHOW_AT_MOST, notification.build());  //id随意，正好使用定义的常量做id，0除外，0为默认的Notification
    }
}

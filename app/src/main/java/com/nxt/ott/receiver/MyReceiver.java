package com.nxt.ott.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.nxt.ott.R;
import com.nxt.ott.expertUpdate.AnswerList_Activity;
import com.nxt.ott.server.LongRunningService;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by huqiang on 2017/3/28 11:11.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LongRunningService.class);
        String data = intent.getExtras().getString("msg");
        int count = intent.getExtras().getInt("count",0);
        boolean isExperter = intent.getExtras().getBoolean("experter");
        Intent msgIntent = new Intent("com.android.broadcast.MSG_ACTION");
        msgIntent.putExtra("count",count);
        context.sendBroadcast(msgIntent);
        if (count>0){
            showNotification(context,isExperter,count);

        }
        Log.d("huqiang", data);
        context.startService(i);
    }

    private void showNotification(Context context,boolean isExperter,int count){
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
//        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        if (isExperter){
            builder.setContentText("您有"+count+"条新问题哦!");
        }else {
            builder.setContentText("您有"+count+"条新回复哦");
        }
        builder.setContentTitle("江西12316提醒");
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS);
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            hangIntent.setClass(context, AnswerList_Activity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, hangIntent, 0);
        builder.setContentIntent(pendingIntent);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
//        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setFullScreenIntent(hangPendingIntent, true);
        manager.notify(2, builder.build());
    }

}

package com.nxt.ott.server;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.util.JsonUtils;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by huqiang on 2017/3/28 10:51.
 */

public class LongRunningService extends Service {
    private String msg = null;
    private Map<String,String> param = new HashMap<>();
    private boolean isExperter = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //定时访问后台，获取已回复问题数
        final String user = ZPreferenceUtils.getPrefString(Constant.USERNAME,"");
        param.clear();
        if (TextUtils.equals("专家",ZPreferenceUtils.getPrefString(Constant.USER_TYPE,""))){
            param.put("point",user);
            isExperter = true;
        }else {
            param.put("userName",user);
            isExperter = false;
        }
        OkGo.post(Constant.PUSHINFO)
                .params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                       if ("1".equals(JsonUtils.getServerResult(s))){
                            int count = JsonUtils.getUnreadCount(s,user);
                                msg = new Date().toString();
                                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                int time = 10*1000;
                                long triggerAtTime = SystemClock.elapsedRealtime()+time;
                                Intent i = new Intent("com.android.broadcast.RECEIVER_ACTION");
                                i.putExtra("msg",msg);
                                i.putExtra("count",count);
                                i.putExtra("experter",isExperter);
                                PendingIntent pi = PendingIntent.getBroadcast(LongRunningService.this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
                                manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
                            }
                        }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
        return super.onStartCommand(intent, Service.START_FLAG_REDELIVERY, startId);
    }
}

/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nxt.ott.activity.account;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.util.EMLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.DoingActivity;
import com.nxt.ott.activity.titlebottom.AboutUsActivity;
import com.nxt.ott.activity.titlebottom.EwmActivity;
import com.nxt.ott.domain.ExperterInfo;
import com.nxt.ott.expertUpdate.AnswerList_Activity;
import com.nxt.ott.fragment.HomeFragment;
import com.nxt.ott.fragment.MeFragment;
import com.nxt.ott.fragment.WisdomAgricultureFragment;
import com.nxt.ott.fragment.ZhnyFragment;
import com.nxt.ott.server.DownLoadService;
import com.nxt.ott.util.DataCleanManager;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.util.UpdateManager;
import com.nxt.zyl.ui.widget.roundedimageview.CustomImageView;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG = "MainActivity";
    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    private TextView unreadAddressLable;
    private Button[] mTabs;
    private HomeFragment homeFragment;
    private WisdomAgricultureFragment wisdomAgricultureFragment;
    private ZhnyFragment zhnyFragment;
    private MeFragment mMeFragment;
    private Fragment[] fragments;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false, islogin = false;

    private RadioButton homeradio, commradio, personcenterradio, moreradio;

    private int currentindex = 0;
    private MyApplication application;
    private TextView titleview;
    private DrawerLayout drawerLayout;
    private TextView tvDynamic, tvCollection, tvClearcache, tvSubjectrecommend, tvVersionupdate, tvAboutus,
            tvHelp, tvCancellation, tvSet, tvShare;


    private CustomImageView centerHeadAvatar, mainHeadAvatar;//头像
    private TextView tvUserName;//昵称
    public Bitmap bitmap;
    ProgressDialog pd;
    private DataCleanManager dataCleanManager;
    private File cachfile;
    long filesize;
    private Map<String,String> param = new HashMap<>();
//    private boolean isExperter = false;
    private String msg = null;

    /**
     * 检查当前用户是否被删除
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        Log.e("main-136", "tag---------------------------");
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            DemoHelper.getInstance().logout(true, null);
            finish();
            Log.e("main-143", "tag---------------------------");
            startActivity(new Intent(this, MyLoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            finish();
            Log.e("main-150", "tag---------------------------");

            startActivity(new Intent(this, MyLoginActivity.class));
            return;
        }

        setContentView(R.layout.activity_main);
        initView();
        /**
         * 检测账号是否在别处登陆
         */
        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }


        System.out.println("conversationListFragment init refresh-------------->");

        mMeFragment = new MeFragment();
        homeFragment = new HomeFragment();
        zhnyFragment = new ZhnyFragment();
//        questListFragment = new QuestListFragment();
        wisdomAgricultureFragment = new WisdomAgricultureFragment();
//        String userType = ZPreferenceUtils.getPrefString(Constant.USER_TYPE,"");
//        if (TextUtils.equals("专家",userType)){
//            fragments = new Fragment[]{homeFragment, wisdomAgricultureFragment, questListFragment, mMeFragment};
//            // 添加显示第一个fragment
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, questListFragment)
//                    .add(R.id.fragment_container, homeFragment)
//                    .hide(answerListFragment)
//                    .show(homeFragment)
//                    .commit();
//        }else {
            fragments = new Fragment[]{homeFragment, zhnyFragment,wisdomAgricultureFragment, mMeFragment};
            // 添加显示第一个fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, wisdomAgricultureFragment)
                    .add(R.id.fragment_container, homeFragment)
                    .hide(wisdomAgricultureFragment)
                    .show(homeFragment)
                    .commit();
        isRegister();
//        /**
//         * 获取推送信息
//         */
//        getPushInfo();
//        application = MyApplication.getInstance();
//        application.openService();
//        application.openReceiver();

        getExperterList();
    }

    /**
     * 从微信接口判断当前账号是否在微信公众号注册
     */
    private void isRegister() {
        OkGo.get(Constant.ISREGISTER)
                .params("firstphone",ZPreferenceUtils.getPrefString(Constant.UID,""))
                .params("secondtphone",ZPreferenceUtils.getPrefString(Constant.TEL,""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String result = jsonObject.getString("result");
                            if ("ok".equals(result)){
                                ZPreferenceUtils.setPrefBoolean(Constant.WXREGISTER,true);
                                ZPreferenceUtils.setPrefString(Constant.WXPHONE,jsonObject.getString("phone"));
                                ZPreferenceUtils.setPrefString(Constant.PID,jsonObject.getString("id"));
                            }else {
                                ToastUtils.showShort(MainActivity.this,"当前手机号没有在江西农业公众号注册，请前往公众号注册");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void getPushInfo() {
        //定时访问后台，获取已回复问题数
        final String user = ZPreferenceUtils.getPrefString(Constant.USERNAME,"");
        param.clear();
//        if (TextUtils.equals("专家",ZPreferenceUtils.getPrefString(Constant.USER_TYPE,""))){
//            param.put("point",user);
//            isExperter = true;
//        }else {
//            param.put("userName",user);
//            isExperter = false;
//        }
        OkGo.post(Constant.PUSHINFO)
                .params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if ("1".equals(JsonUtils.getServerResult(s))){
                            int count = JsonUtils.getUnreadCount(s,user);  //推送的消息数
                            msg = new Date().toString();
//                            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                            int time = 10*1000;
//                            long triggerAtTime = SystemClock.elapsedRealtime()+time;
//                            Intent i = new Intent("com.android.broadcast.RECEIVER_ACTION");
//                            i.putExtra("msg",msg);
//                            i.putExtra("count",count);
//                            i.putExtra("experter",isExperter);
//                            PendingIntent pi = PendingIntent.getBroadcast(LongRunningService.this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
//                            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
                            if (count>0){
                                ZPreferenceUtils.setPrefInt(Constant.PUSHCOUNT,count);
//                                showNotification(MainActivity.this,isExperter,count);
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
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
        if (manager != null) {
            manager.notify(2, builder.build());
        }
    }
    private void getExperterList() {
            OkGo.post(String.format(com.nxt.ott.Constant.EXPERTER_LIST_URL_ALL,"专家"))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ExperterInfo info = new Gson().fromJson(s,ExperterInfo.class);
                            List<ExperterInfo.RowsBean> infos;
                            infos = info.getRows();
//                            experter.setDataList("experter",infos);
                            MyApplication.getInstance().setInfo(infos);
                        }
                    });

    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        centerHeadAvatar = (CustomImageView) findViewById(R.id.iv_user_photo);
        mainHeadAvatar = (CustomImageView) findViewById(R.id.img_person);
        if (mainHeadAvatar != null) {
            mainHeadAvatar.setOnClickListener(this);
        }
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_lauout);
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        titleview = (TextView) findViewById(R.id.tv_title);

        islogin = ZPreferenceUtils.getPrefBoolean(com.nxt.ott.Constant.IS_LOGIN, false);
        Log.e("main-249", "logintag------------->" + islogin);
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_home);
        mTabs[1] = (Button) findViewById(R.id.btn_more);
        mTabs[2] = (Button) findViewById(R.id.btn_conversation);
        mTabs[3] = (Button) findViewById(R.id.btn_me);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);

        tvDynamic = (TextView) findViewById(R.id.tv_mine_dynamic);//我的动态
        tvCollection = (TextView) findViewById(R.id.tv_mine_collection);//我的收藏
        tvClearcache = (TextView) findViewById(R.id.tv_mine_clear_cache);//缓存管理
        tvSubjectrecommend = (TextView) findViewById(R.id.tv_mine_recommend_subject);//主题推荐
        tvVersionupdate = (TextView) findViewById(R.id.tv_mine_version_update);//检测升级
        tvAboutus = (TextView) findViewById(R.id.tv_mine_about_us);//关于我们
        tvHelp = (TextView) findViewById(R.id.tv_mine_help);//帮助
        tvCancellation = (TextView) findViewById(R.id.tv_mine_cancellation);//注销
        tvSet = (TextView) findViewById(R.id.tv_mine_set);//设置
        tvShare = (TextView) findViewById(R.id.tv_mine_share);//推广

        tvDynamic.setOnClickListener(this);
        tvCollection.setOnClickListener(this);
        tvClearcache.setOnClickListener(this);
        tvSubjectrecommend.setOnClickListener(this);
        tvVersionupdate.setOnClickListener(this);
        tvAboutus.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvCancellation.setOnClickListener(this);
        tvSet.setOnClickListener(this);
        tvShare.setOnClickListener(this);
    }

    /**
     * button点击事件
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_home://主页
                titleview.setText(getString(R.string.title));
                index = 0;
                break;
            case R.id.btn_conversation://消息
                titleview.setText(getString(R.string.experter_ask));
                if (!islogin) {
                    ZToastUtils.showShort(MainActivity.this, "您还没有登陆，请先登录");
                    Log.e("main-334", "tag---------------------------");
                    startActivity(new Intent(this, MyLoginActivity.class));
                } else {
                    index = 2;
                }
                break;
            case R.id.btn_me://个人中心
                titleview.setText(getString(R.string.personcenter));
                index = 3;
                break;
            case R.id.btn_more://专业服务
                titleview.setText(getString(R.string.n_system));
                index = 1;
                break;
                default:
                    break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    /**
     * 个人中心
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mine_dynamic://我的动态
                startActivity(new Intent(this, DoingActivity.class));
                break;

            case R.id.tv_mine_collection://我的收藏  完善资料
                if (islogin) {
//                    startActivity(new Intent(MainActivity.this, UserProfileActivity.class).putExtra("setting", true)
//                            .putExtra("username", EMClient.getInstance().getCurrentUser()));
                } else {
                    ZToastUtils.showShort(this, "您还没有登陆，请先登录");
                    Log.e("main-383", "tag---------------------------");
                    startActivity(new Intent(this, MyLoginActivity.class));
                }
                break;

            case R.id.tv_mine_clear_cache://清理缓存
                pd = new ProgressDialog(this);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("正在清理缓存....");
                pd.show();
                DataCleanManager.cleanApplicationData(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataCleanManager.deleteFolderFile(cachfile.getAbsolutePath(), false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                        }
                        try {
                            if (DataCleanManager.getFolderSize(cachfile) == 0) {
                                handler.sendEmptyMessage(0);
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.tv_mine_recommend_subject://主题推荐
                startActivity(new Intent(this, DoingActivity.class));
                break;

            case R.id.tv_mine_version_update://版本更新
                new UpdateManager(this).checkUpdate();
                break;

            case R.id.tv_mine_about_us://关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;

            case R.id.tv_mine_cancellation://意见反馈
                startActivity(new Intent(this, FeedBackActivity.class));
                break;

            case R.id.tv_mine_help://帮助  分享
                //分享文字
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "江西12316这款应用真是太棒了，" +
                        "在线咨询专家，自主诊断，电商服务，即时农业资讯，丰富的视频库，" +
                        "全面的便民服务等功能一应俱全。我正在用，你也赶快加入吧！" +
                        "戳我下载：http://d.54vc.com/dak");
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;

            case R.id.tv_mine_set://设置
                if (!islogin) {
                    ZToastUtils.showShort(this, "您还没有登陆，请先登录");
                    Log.e("main-448", "tag---------------------------");
                    startActivity(new Intent(this, MyLoginActivity.class));
                } else {
                    startActivity(new Intent(this, SetActivity.class));
//                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                break;

            case R.id.tv_mine_share://推广
                startActivity(new Intent(this, EwmActivity.class));
                break;
                default:
                    break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "释放了" + filesize + "Kb空间", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "清理失败", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        break;
            }
            pd.dismiss();
            super.handleMessage(msg);
        }
    };


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //getMenuInflater().inflate(R.menu.context_tab_contact, menu);
    }

    private long exitTime;

    /**
     * 重写主界面下的返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                    drawerLayout.closeDrawer(Gravity.LEFT);
//                    return false;
//                }
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), getString(R.string.exit_notice),
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    moveTaskToBack(true);
                    ZPreferenceUtils.setPrefInt(Constant.PUSHCOUNT,0);
//                    MyApplication.getInstance().exit();//关闭栈中的activity
//                    System.exit(0);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(MainActivity.this, DownLoadService.class));
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;

    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null) {
                    conflictBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                }
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        finish();
                        Log.e("main-771","tag---------------------------");
                        ZPreferenceUtils.setPrefBoolean(Constant.IS_LOGIN,false);
                        Intent intent = new Intent(MainActivity.this, MyLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }

        }


    }

    /**
     * 帐号被移除的dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null) {
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                }
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
                        ZPreferenceUtils.setPrefBoolean(Constant.IS_LOGIN,false);
                        Log.e("main-810","tag---------------------------");
                        startActivity(new Intent(MainActivity.this, MyLoginActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }
}

package com.hyphenate.easeui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * 根据username获取相应user
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //正常的string路径
                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null){
        		textView.setText(user.getNick());
        	}else{
        		textView.setText(username);
        	}
        }
    }
    /*
    * 设置用户名和类型
    * */
    public static String name;
    public static String userType;
    public static void setUserInfo(final String username, TextView textView){
        if(textView !=null){
            EaseUser user = getUserInfo(username);
            if(user !=null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String url=String.format("http://182.116.57.248:8121/data/hr_employee.ashx?action=form&uid=%s",username);
                            HttpGet httpRequest = new HttpGet(url);
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpResponse httpResponse = httpclient.execute(httpRequest);
                            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                            {
                                //取得返回的字符串
                                String strResult = EntityUtils.toString(httpResponse.getEntity());
                                JSONObject jb=new JSONObject(strResult);
                                name=jb.getString("name");
                                userType=jb.getString("usertype");

                            }
                            else
                            {
                                Log.e("TAG", "请求错误!");
                            }
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                textView.setText(name + "\n(" + userType+")");
            }else {
                textView.setText(username);
            }
        }
    }
    
}

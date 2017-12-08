package com.nxt.ott.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by huqiang on 2016/11/24 15:13.
 */

public class OkhttpHelper {
    private static OkhttpHelper helper;

    private OkhttpHelper() {
    }
    public static synchronized  OkhttpHelper getInstance(){
        if (helper==null){
            helper = new OkhttpHelper();
        }
        return helper;
    }

    //GET
    public void Get(String url, final MyCallBack callBack, final int tag){
        OkHttpUtils.get().url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callBack.onSucces(response,tag);
                    }
                });
    }

    //Post
    public void Get(String url, final MyCallBack callBack, final int tag, Map<String,String> map){
            OkHttpUtils.get()
                    .url(url)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            callBack.onFaile(e,tag);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            callBack.onSucces(response,tag);
                        }

                    });
    }

    public  void Post(String url, final MyCallBack callBack, Map<String,String> map, final int tag){
        OkHttpUtils.post()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFaile(e,tag);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callBack.onSucces(response,tag);
                    }
                });

    }
    public interface MyCallBack{
        void onSucces(String response,int tag);
        void onFaile(Exception errorResponse,int tag);
    }

}

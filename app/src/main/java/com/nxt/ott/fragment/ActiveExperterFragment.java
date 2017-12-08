package com.nxt.ott.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExpertCommentAdapter;
import com.nxt.ott.domain.RecommentExperter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by huqiang on 2017/3/30 16:16.
 */

public class ActiveExperterFragment extends LazyFragment {
    //标志位，标志已初始化完成
    private boolean isPrepared;
    private RecyclerView ry_active;
    private ExpertCommentAdapter adapter;
    private List<RecommentExperter.RowsBean> recommentExperterList = new ArrayList<>();
    private String title,text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args!=null){
            title = args.getString("title");
            text = args.getString("text");
        }
        View view = inflater.inflate(R.layout.fragment_activer_experter,container,false);
        //初始化view的各控件
        ry_active = (RecyclerView) view.findViewById(R.id.ry_not_activie);
        isPrepared = true;
        lazyLoad();
        return view;
    }
    public static ActiveExperterFragment newInstance(String title,String text){
        ActiveExperterFragment fragment = new ActiveExperterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("text",text);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initRecyclerView(String title,String text) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ry_active.setLayoutManager(manager);
        //根据问题标签匹配专家
        OkGo.post(Constant.GET_RECOMMMEND_EXPERTER)
                .params("pageIndex", 1)
                .params("pageSize", 5)
                .params("title", title)
                .params("text", text)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        RecommentExperter experter = new Gson().fromJson(s, RecommentExperter.class);
                        recommentExperterList = experter.getRows();
                        if (recommentExperterList != null) {
                            adapter = new ExpertCommentAdapter(recommentExperterList);
                            ry_active.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared||!isVisible){
            return;
        }
        initRecyclerView(title,text);
    }
}

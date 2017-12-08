package com.nxt.ott.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nxt.ott.R;
import com.nxt.ott.adapter.HotExperterAdapter;
import com.nxt.ott.domain.HotExperter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2017/6/12 10:56.
 */

public class HotExperterDialog extends BaseBottomDialogForV4 {
    private RecyclerView ry_hot;
    private List<HotExperter> hotExperters = new ArrayList<>();

    public HotExperterDialog() {
    }

    public void setHotExperters(List<HotExperter> hotExperters) {
        this.hotExperters = hotExperters;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.hotexperterdialog;
    }

    @Override
    public void bindView(View v) {
        ry_hot = (RecyclerView) v.findViewById(R.id.ry_hot);
        getHotExperters();
    }

    /**
     * 获取今日活跃专家
     */
    private void getHotExperters() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ry_hot.setLayoutManager(manager);
        HotExperterAdapter adapter = new HotExperterAdapter(hotExperters);
//                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        ry_hot.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("tel",hotExperters.get(position).getTel());
                bundle.putString("name", hotExperters.get(position).getName());
                bundle.putString("type", hotExperters.get(position).getType());
                bundle.putString("avator", hotExperters.get(position).getAvator());
                bundle.putString("zixunfuwu", hotExperters.get(position).getZixunfuwu());
                bundle.putString("remarks", hotExperters.get(position).getRemarks());
                bundle.putString("uid",hotExperters.get(position).getUid());
                ExperterInfoDialog dialog = ExperterInfoDialog.newInstance(bundle);
                dialog.show(getFragmentManager());
            }
        });
    }
}

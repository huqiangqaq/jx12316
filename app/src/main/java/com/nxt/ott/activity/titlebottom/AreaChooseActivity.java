package com.nxt.ott.activity.titlebottom;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.adapter.AreaAdapter;
import com.nxt.ott.base.BaseLoadingTitleActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/26 0026.
 */
public class AreaChooseActivity extends BaseLoadingTitleActivity implements AdapterView.OnItemClickListener {
    private GridView areagrid;
    private List<String> arealist;
    private String areastr;
    private TextView nowareatext;
    @Override
    protected void initView() {
        application.addActivity(this);

        initTopbar(this,getString(R.string.area_choose));
        nowareatext= (TextView) findViewById(R.id.tv_nowarea);
        nowareatext.setText(getIntent().getStringExtra("area"));
        areagrid= (GridView) findViewById(R.id.gridview_area);
        arealist= Arrays.asList(getResources().getStringArray(R.array.area_list));
        areagrid.setAdapter(new AreaAdapter(this,arealist));
        areagrid.setOnItemClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_area_choose;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          setResult(RESULT_OK,getIntent().putExtra("area",arealist.get(position)));
          finish();
    }

}

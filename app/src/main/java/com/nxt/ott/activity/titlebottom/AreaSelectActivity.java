package com.nxt.ott.activity.titlebottom;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.adapter.AreaAdapter2;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Area;
import com.nxt.ott.util.CharacterParser;
import com.nxt.ott.view.MySidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/7 0007.
 */
public class AreaSelectActivity extends BaseTitleActivity implements AdapterView.OnItemClickListener{
    private List<Area> areaList=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    private ListView mlistview;
    private MySidebar mySidebar;
    private CharacterParser characterParser;
    private TextView nowareatext;
    @Override
    protected void initView() {
        application.addActivity(this);

        initTopbar(this,getString(R.string.area_choose));
        mlistview= (ListView) findViewById(R.id.listview_area);
        mlistview.setOnItemClickListener(this);
        mySidebar= (MySidebar) findViewById(R.id.sidebar);
        mySidebar.setListView(mlistview);
        nowareatext= (TextView) findViewById(R.id.tv_nowarea);
        nowareatext.setText(getIntent().getStringExtra("area"));
        characterParser=CharacterParser.getInstance();
        list= Arrays.asList(getResources().getStringArray(R.array.area_list));
        for(int i=0;i<list.size();i++){
            Area area=new Area();
            area.setName(list.get(i));
            area.setPinyin(characterParser.getSelling(list.get(i)));
            area.setHeader(characterParser.getSelling(list.get(i)).substring(0,1).toUpperCase());
            areaList.add(area);
        }
        //排序
        Collections.sort(areaList, new Comparator<Area>() {

            @Override
            public int compare(Area lhs, Area rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
        mlistview.setAdapter(new AreaAdapter2(this,0,areaList));


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_area_select;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setResult(RESULT_OK,getIntent().putExtra("area",areaList.get(position).getName()));
        finish();
    }
}

package com.nxt.ott.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ConstellationAdapter;
import com.nxt.ott.adapter.GirdDropDownAdapter;
import com.nxt.ott.adapter.ListDropDownAdapter;
import com.nxt.ott.adapter.PriceAdapter;
import com.nxt.ott.domain.Price;
import com.nxt.ott.view.DropDownMenu;
import com.nxt.zyl.util.ZToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by huqiang on 2017/1/10 14:07.
 */

public class PriceFragment2 extends Fragment {
    private View view;
    private DropDownMenu mDropDownMenu;
    private String headers[] = {"年份", "月份", "类型", "品种"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"不限", "2015年", "2016年", "2017年"};
    private String ages[] = {"不限", "1月上旬","1月下旬","2月上旬","2月下旬","3月上旬","3月下旬","4月上旬","4月下旬",
            "5月上旬","5月下旬","6月上旬","6月下旬","7月上旬","7月下旬","8月上旬","8月下旬","9月上旬",
            "9月下旬","10月上旬","10月下旬","11月上旬","11月下旬","12月上旬","12月下旬",};
    private String sexs[] = {"不限", "粮食", "油料和经济作物","畜牧水产","农资"};
//    private String constellations[] =new String[]{};
    private List<String> constellations= new ArrayList<>();
    private int constellationPosition = 0;
    //记录每个标签选中的结果
    private Map<String,String> map = new HashMap<>();
    private String year = null;
    private List<Price.RowsBean> prices = new ArrayList<>();
    private PriceAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view!=null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent!=null){
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.price2,container,false);
        initView();
        return view;
    }

    private void initView() {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
        //init city menu
        final ListView cityView = new ListView(getActivity());
        cityAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(getActivity());
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout, null);
        final GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(getActivity(), constellations);
        constellation.setAdapter(constellationAdapter);

        final TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations.get(constellationPosition));
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
                if (position!=0){
                    String[] ss = citys[position].split("年");
                    map.put("reportYear",ss[0]);
                    year = citys[position];
                }else {
                    year=null;
                    map.remove("reportYear");
                }
                refreshData();

            }
        });
        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null==year&position!=0){
                    ZToastUtils.showShort(getActivity(),"请先选择年份!");
                    return;
                }
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
                if (position!=0){
                    map.put("reportMonth2",year+ages[position]);
                }else {
                    map.remove("reportMonth2");
                }
                refreshData();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
                switch (position){
                    case 0:
                        map.remove("reportType");
                        break;
                    //粮食
                    case 1:
                        String[] food = new String[]{"不限","早籼稻","中籼稻","晚籼稻","玉米","大豆","早籼米","中晚籼米","标准粉","精粉","玉米面"};
                        constellations.clear();
                        constellations.addAll(Arrays.asList(food));
                        constellationAdapter.notifyDataSetChanged();
                        map.put("reportType","0");
                        break;
                    //油料和经济作物
                    case 2:
                        String[] oil = new String[]{"不限","花生仁","花生油","油菜籽","菜籽油","豆油","皮棉","籽棉","桑蚕茧","白菜","西红柿","黄瓜","青椒","土豆"};
                        constellations.clear();
                        constellations.addAll(Arrays.asList(oil));
                        constellationAdapter.notifyDataSetChanged();
                        map.put("reportType","1");
                        break;
                    //畜牧水产
                    case 3:
                        String[] fish = new String[]{"不限","生猪","仔猪","猪肉","牛肉","羊肉","鸡肉","鸡蛋","原料奶","鲤鱼","鲢鱼","草鱼"};
                        constellations.clear();
                        constellations.addAll(Arrays.asList(fish));
                        constellationAdapter.notifyDataSetChanged();
                        map.put("reportType","2");
                        break;
                    //农资
                    case 4:
                        String[] argi = new String[]{"不限","国产复合肥","国产尿素","碳酸氢铵","国产磷酸二铵","国产氯化钾","过磷酸钙","钙镁磷肥","棚膜","地膜","0号农用柴油","蛋鸡配合饲料","育肥猪配合饲料"};
                        constellations.clear();
                        constellations.addAll(Arrays.asList(argi));
                        constellationAdapter.notifyDataSetChanged();
                        map.put("reportType","3");
                        break;
                }
                refreshData();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    constellationAdapter.setCheckItem(position);
                    constellationPosition = position;
                    if (position!=0){
                        map.put("stext",constellations.get(position));
                    }else {
                        map.remove("stext");
                    }
                    refreshData();
            }
        });
        final ListView contentView = new ListView(getActivity());
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.price_tittle,null,false);
        contentView.addHeaderView(view);
        OkHttpUtils.post()
                .url(Constant.PRICE_UPDATE)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Price price = new Gson().fromJson(response,Price.class);
                        prices = price.getRows();
                        adapter = new PriceAdapter(getActivity(),prices);
                        contentView.setAdapter(adapter);
                    }
                });
        //init context view


//        contentView.setText("内容显示区域");
//        contentView.setGravity(Gravity.CENTER);
//        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    private void refreshData() {
        OkHttpUtils
                .post()
                .url(Constant.PRICE_UPDATE)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Price price = new Gson().fromJson(response,Price.class);
                        prices.clear();
                        prices =price.getRows();
                        adapter.setPrices(prices);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}

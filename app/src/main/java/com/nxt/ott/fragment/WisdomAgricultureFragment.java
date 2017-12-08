package com.nxt.ott.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.DoingActivity;
import com.nxt.ott.activity.SoilActivity;
import com.nxt.ott.activity.SupplyActivity;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.activity.convenience.ConveniencePersonActivity;
import com.nxt.ott.activity.information.AgricultureInfoActivity;
import com.nxt.ott.activity.scan.ProductScanActivity;
import com.nxt.ott.activity.titlebottom.WeatherDetailActivity;
import com.nxt.ott.adapter.AllAdapter;
import com.nxt.ott.view.CustomGridView;
import com.nxt.zyl.ui.fragment.ZBaseFragment;

import java.util.Arrays;

/**
 * Created by zhangyonglu on 2016/2/25 0025.
 * Updated by iwon on 2016/6/22 23:08
 */
public class WisdomAgricultureFragment extends ZBaseFragment implements AdapterView.OnItemClickListener {
    private CustomGridView gv_all;
    private AllAdapter adapter;
    private String[] img_text = new String[]{"益农社","赣农宝","农业气象","配方施肥","农资讯","市场供求","产品追溯","物联网"};
    private Integer[] imgs = new Integer[]{R.drawable.icon_yns, R.drawable.icon_gnb, R.drawable.icon_nyqx,
            R.drawable.icon_ctpf,R.drawable.icon_nzx, R.drawable.icon_scgq,R.drawable.icon_cpzs, R.drawable.icon_wlw};

    @Override
    protected void initView(View view) {
        gv_all = view.findViewById(R.id.gv_all);
        adapter = new AllAdapter(getActivity(), Arrays.asList(img_text),Arrays.asList(imgs));
        gv_all.setAdapter(adapter);
        gv_all.setOnItemClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ynxx;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(getActivity(), ConveniencePersonActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("tag", 1).putExtra("url", Constant.GNB_URL));
                break;
            case 2:
                startActivity(new Intent(getActivity(), WeatherDetailActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), SoilActivity.class));
//                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", getString(R.string.soil_testing_formula)).putExtra("url", Constant.CTPF_URL));
                break;
            case 4:
                startActivity(new Intent(getActivity(), AgricultureInfoActivity.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), SupplyActivity.class));
                break;
            case 6:
                startActivity(new Intent(getActivity(), ProductScanActivity.class));
                break;
            case 7:
                startActivity(new Intent(getActivity(), DoingActivity.class));
                break;
            default:
                break;
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

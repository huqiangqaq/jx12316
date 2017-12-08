package com.nxt.ott.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.zyl.ui.fragment.ZBaseFragment;

/**
 * Updated by iwon on 2016/6/19 21:20.
 */
public class NFragment extends ZBaseFragment implements AdapterView.OnItemClickListener {
    private GridView ngridview;
    private String[] titlelist, urllist;
    private int[] imgids = new int[]{R.mipmap.n1, R.mipmap.n2, R.mipmap.n3, R.mipmap.n4, R.mipmap.n5, R.mipmap.n6, R.mipmap.n7, R.mipmap.n8,
            R.mipmap.n9, R.mipmap.n10, R.mipmap.n11, R.mipmap.n12, R.mipmap.n13, R.mipmap.n14, R.mipmap.n15, R.mipmap.n16, R.mipmap.n17, R.mipmap.n18,
            R.mipmap.n19, R.mipmap.n20, R.mipmap.n21, R.mipmap.n22, R.mipmap.n23, R.mipmap.n24, R.mipmap.n25, R.mipmap.n26,
            R.mipmap.n27, R.mipmap.n28};

    @Override
    protected void initView(View view) {
        ngridview = (GridView) view.findViewById(R.id.gridview_n);
        ngridview.setOnItemClickListener(this);
        urllist = getResources().getStringArray(R.array.n_url_list);
        titlelist = getResources().getStringArray(R.array.n_list);
        ngridview.setAdapter(new MyAdapter());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_n;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemurl = urllist[position];
        if (!TextUtils.isEmpty(itemurl) && itemurl.contains("http")) {
            startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", titlelist[position]).putExtra("url", urllist[position]));
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titlelist.length;
        }

        @Override
        public Object getItem(int position) {
            return titlelist[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.n_grid_list, null);
                holder.nimgview = (ImageView) convertView.findViewById(R.id.img_n);
                holder.ntexttitle = (TextView) convertView.findViewById(R.id.tv_n_title);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();

            }
            holder.ntexttitle.setText(titlelist[position]);
            holder.nimgview.setImageResource(imgids[position]);
            return convertView;
        }

        class Holder {
            ImageView nimgview;
            TextView ntexttitle;
        }
    }
}

package com.nxt.ott.adapter;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nxt.ott.R;

import java.util.ArrayList;


/**
 * Created by koneloong on 14-5-24.
 * 自定义ArrayAdapter
 */
public class MyArrayAdapter extends ArrayAdapter {
    private Activity ctx;
    private ArrayList<String> gradeList;
    private final String TAG="MyArrayAdapter";
    @SuppressWarnings("unchecked")
    public MyArrayAdapter(Activity ctx, int layoutID, ArrayList<String> gradeList) {
        super(ctx, layoutID, gradeList);
        this.ctx = ctx;
        this.gradeList = gradeList;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = ctx.getLayoutInflater().inflate(R.layout.spinner_item_layout,
                null);
        Log.i(TAG, view.toString());
        TextView label = (TextView) view
                .findViewById(R.id.spinner_item_label);
          Log.i(TAG, label.toString());
        label.setText(gradeList.get(position));
       
        return view;
    }

}

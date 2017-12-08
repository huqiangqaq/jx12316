package com.nxt.ott.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;

/**
 * Created by huqiang on 2017/3/30 16:24.
 */

public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;
    protected AVLoadingIndicatorView loadingIndicatorView;
    /**
     * 这里实现Fragment数据的缓加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible(){
        lazyLoad();
    }
    protected abstract void lazyLoad();

    protected void onInvisible(){}

    public void showloading() {
        loadingIndicatorView = new AVLoadingIndicatorView(getActivity());
        loadingIndicatorView.setVisibility(View.VISIBLE);
    }

    public void dismissloading() {
        if (loadingIndicatorView != null)
            loadingIndicatorView.setVisibility(View.GONE);
    }
}

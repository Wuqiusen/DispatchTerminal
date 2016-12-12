package com.zxw.dispatch_driver.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.utils.ToastHelper;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * author：CangJie on 2016/8/30 10:12
 * email：cangjie2016@gmail.com
 */
public class LoadSupView extends RelativeLayout {
    private final Context mContext;
    @Bind(R.id.tv_noline)
    TextView tv_noline;

    @Bind(R.id.rl_normal)
    RelativeLayout rl_normal;
    @Bind(R.id.progress_bar)
    RelativeLayout progress_bar;
    @Bind(R.id.display_container)
    RelativeLayout display_container;
    @Bind(R.id.lv)
    PullToRefreshListView pullToRefreshListView;

    private LoadSupViewReload reloadListener;
    private Handler handler;

    public LoadSupView(Context context) {
        this(context,null);
    }

    public LoadSupView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadSupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View.inflate(context, R.layout.rl_load_sup_view, this);
        ButterKnife.bind(this);
        initListView();
        handler = new Handler(mContext.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pullToRefreshListView.onRefreshComplete();
            }
        };
    }

    private void initListView() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    if (pullToRefreshListView != null) {
                        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                    }
                }
                if (visibleItemCount + firstVisibleItem == totalItemCount) {
                    if (pullToRefreshListView != null) {
                        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                    }
                }
                if (firstVisibleItem == 0 && visibleItemCount + firstVisibleItem == totalItemCount) {
                    if (pullToRefreshListView != null) {
                        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                    }
                }
            }
        });
    }

    public void setOnRefreshListener(PullToRefreshBase.OnRefreshListener2 listener){
        this.pullToRefreshListView.setOnRefreshListener(listener);
    }

    /**
     * 设置reload 监听器
     * @param reloadListener reloadListener 监听器
     */
    public void setReloadListener(LoadSupViewReload reloadListener){
        this.reloadListener = reloadListener;
    }

    /**
     * 访问失败
     */
    public void loadFailed(){
        tv_noline.setText("访问失败 点击重试");
        setVisibility(VISIBLE);
        rl_normal.setVisibility(VISIBLE);
        progress_bar.setVisibility(INVISIBLE);
        if(reloadListener == null)
            throw new RuntimeException("reloadListener can not be null");
        display_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadListener.reload();
            }
        });
        hideListView();
    }

    /**
     * 空数据
     */
    public void loadEmpty(){
        tv_noline.setText("暂无相关信息");
        setVisibility(VISIBLE);
        rl_normal.setVisibility(VISIBLE);
        progress_bar.setVisibility(INVISIBLE);
        display_container.setOnClickListener(null);
        hideListView();
    }
    /**
     * 没有更多数据
     */
    public void noMoreData(){
        handler.sendEmptyMessage(0);
        ToastHelper.showToast(R.string.no_more_data, mContext);
        showListView();
    }

    /**
     * 显示加载中
     */
    public void showLoading(){
        display_container.setVisibility(VISIBLE);
        rl_normal.setVisibility(INVISIBLE);
        progress_bar.setVisibility(VISIBLE);
        pullToRefreshListView.setVisibility(GONE);
    }

    /**
     * 隐藏加载中
     */
    public void hideLoading(){
        display_container.setVisibility(GONE);
        rl_normal.setVisibility(VISIBLE);
        progress_bar.setVisibility(INVISIBLE);
        pullToRefreshListView.setVisibility(VISIBLE);
    }

    /**
     *  设置Adapter
     */
    public void setAdapter(ListAdapter adapter){
        pullToRefreshListView.setAdapter(adapter);
        showListView();
    }

    // 显示列表, 隐藏提示控件
    private void showListView(){
        pullToRefreshListView.setVisibility(VISIBLE);
        display_container.setVisibility(GONE);
    }
    // 隐藏列表, 显示提示控件
    private void hideListView(){
        pullToRefreshListView.setVisibility(GONE);
        display_container.setVisibility(VISIBLE);
    }

    public void loadComplete(){
        pullToRefreshListView.onRefreshComplete();
        showListView();
    }


    public interface LoadSupViewReload{
        void reload();
    }
}

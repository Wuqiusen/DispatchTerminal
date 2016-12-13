package com.zxw.dispatch_driver.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.JourneyAdapter;
import com.zxw.dispatch_driver.adapter.MainPagerAdapter;
import com.zxw.dispatch_driver.adapter.ReceiveAdapter;
import com.zxw.dispatch_driver.presenter.MainPresenter;
import com.zxw.dispatch_driver.presenter.view.MainView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;
import com.zxw.dispatch_driver.view.CustomViewPager;
import com.zxw.dispatch_driver.view.LoadSupView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：CangJie on 2016/12/7 16:45
 * email：cangjie2016@gmail.com
 */
public class MainActivity extends PresenterActivity<MainPresenter> implements MainView {
    @Bind(R.id.ll_receive)
    LinearLayout ll_receive;
    @Bind(R.id.ll_journey)
    LinearLayout ll_journey;
    @Bind(R.id.vp)
    CustomViewPager mViewPager;
    private LoadSupView receive_lsv, journey_lsv;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        hideHeadArea();
        initViewPager();
        mPresenter.jPushComponent();
    }

    private void initViewPager() {
        List<View> mViews = new ArrayList<>();
        List<String> titleList = new ArrayList<String>();
        FrameLayout fl_accept = (FrameLayout) View.inflate(this, R.layout.fragment_accept, null);
        FrameLayout fl_journey = (FrameLayout) View.inflate(this, R.layout.fragment_accept, null);
        receive_lsv = (LoadSupView) fl_accept.findViewById(R.id.load_sup);
        journey_lsv = (LoadSupView) fl_journey.findViewById(R.id.load_sup);
        receive_lsv.setReloadListener(new LoadSupView.LoadSupViewReload() {
            @Override
            public void reload() {
                mPresenter.realLoadReceivePage();
            }
        });
        journey_lsv.setReloadListener(new LoadSupView.LoadSupViewReload() {
            @Override
            public void reload() {
                mPresenter.reloadJourneyList();
            }
        });
        mViews.add(fl_accept);
        mViews.add(fl_journey);

        titleList.add("接单");
        titleList.add("行程");
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(mViews, titleList);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setCurrentItem(0);

        initPullToRefreshListView();
    }

    private void initPullToRefreshListView() {
        receive_lsv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                mPresenter.reloadReceiveList();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                mPresenter.loadReceivePage();
            }
        });
        journey_lsv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                mPresenter.reloadJourneyList();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                mPresenter.loadJourneyPage();
            }
        });
    }

    @OnClick(R.id.ll_receive)
    public void clickReceive(){
        mViewPager.setCurrentItem(0);
        mPresenter.reloadReceiveList();
    }
    @OnClick(R.id.ll_journey)
    public void clickJourney(){
        mViewPager.setCurrentItem(1);
        mPresenter.reloadJourneyList();
    }

    @Override
    public void receivePageLoadFailed() {
        receive_lsv.loadFailed();
    }

    @Override
    public void emptyReceiveData() {
        receive_lsv.loadEmpty();
    }

    @Override
    public void receivePageSetAdapter(ReceiveAdapter mReceiveAdapter) {
        receive_lsv.setAdapter(mReceiveAdapter);
    }

    @Override
    public void receivePageLoadComplete() {
        receive_lsv.loadComplete();
    }

    @Override
    public void noMoreReceiveData() {
        receive_lsv.noMoreData();
    }

    @Override
    public void noMoreJourneyData() {
        journey_lsv.noMoreData();
    }

    @Override
    public void journeyPageLoadFailed() {
        journey_lsv.loadFailed();
    }

    @Override
    public void emptyJourneyData() {
        journey_lsv.loadEmpty();
    }

    @Override
    public void journeyPageSetAdapter(JourneyAdapter mJourneyAdapter) {
        journey_lsv.setAdapter(mJourneyAdapter);
    }

    @Override
    public void journeyPageLoadComplete() {
        journey_lsv.loadComplete();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.reloadJourneyList();
        mPresenter.reloadReceiveList();
        mPresenter.isSetAlias();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterReceiver();
    }
}

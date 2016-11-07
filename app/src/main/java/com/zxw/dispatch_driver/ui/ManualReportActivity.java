package com.zxw.dispatch_driver.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.ServiceWordAdapter;
import com.zxw.dispatch_driver.adapter.ManualReportStationAdapter;
import com.zxw.dispatch_driver.presenter.StationReportPresenter;
import com.zxw.dispatch_driver.presenter.view.StationReportView;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;
import com.zxw.dispatch_driver.utils.DebugLog;
import com.zxw.dispatch_driver.view.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManualReportActivity extends PresenterActivity<StationReportPresenter> implements StationReportView {
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.rv_service_word)
    RecyclerView mRecyclerViewServiceWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_report);

        showTitle("人工报站");
        showBackButton();

        ButterKnife.bind(this);

        int lineId = getIntent().getIntExtra("lineId", 95);
        DebugLog.w(lineId);
        presenter.loadStations(lineId);
        presenter.loadServiceWord();
    }


    @Override
    protected StationReportPresenter createPresenter() {
        return new StationReportPresenter(this, this);
    }

    @Override
    public void setServiceWordAdapter(ServiceWordAdapter serviceWordAdapter) {
        mRecyclerViewServiceWord.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mRecyclerViewServiceWord.setAdapter(serviceWordAdapter);
    }

    @Override
    public void setStationListAdapter(ManualReportStationAdapter manualReportStationAdapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setAdapter(manualReportStationAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));
    }
}

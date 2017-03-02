package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.data.bean.Journey;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.JourneyPresenter;
import com.zxw.dispatch_driver.presenter.view.JourneyView;
import com.zxw.dispatch_driver.service.DogService;
import com.zxw.dispatch_driver.trace.TraceService;
import com.zxw.dispatch_driver.ui.base.PresenterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JourneyActivity extends PresenterActivity<JourneyPresenter> implements JourneyView {
    @Bind(R.id.btn_start)
    Button btn_start;
    @Bind(R.id.btn_normal)
    Button btn_normal;
    @Bind(R.id.btn_error)
    Button btn_error;
    @Bind(R.id.btn_resume_report)
    Button btn_resume_report;
    @Bind(R.id.tv_prompt)
    TextView tv_prompt;
    private Journey journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        ButterKnife.bind(this);
        hideHeadArea();
        initView(journey);
    }

    private void initView(Journey journey) {
        switch (journey.getStatus()){
            case 1:
                btn_start.setVisibility(View.VISIBLE);
                break;
            case 2:
                btn_normal.setVisibility(View.VISIBLE);
                btn_error.setVisibility(View.VISIBLE);
                btn_resume_report.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getString(R.string.journey_status_3));
                break;
            case 4:
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getString(R.string.journey_status_4));
                break;
        }
    }

    @Override
    protected JourneyPresenter createPresenter() {
        journey = (Journey)getIntent().getSerializableExtra("journey");
        return new JourneyPresenter(this, journey);
    }

    @OnClick({R.id.btn_start, R.id.btn_normal, R.id.btn_error})
    public void operator(View view){
        switch (view.getId()){
            case R.id.btn_start:
                mPresenter.start();
                break;
            case R.id.btn_normal:
                mPresenter.normalFinish();
                break;
            case R.id.btn_error:
                mPresenter.errorFinish();
                break;
        }
    }

    @OnClick(R.id.btn_resume_report)
    public void btn_resume_report(){
        SpUtils.setCache(mContext, SpUtils.CURRENT_LINE_ID, String.valueOf(journey.getLineId()));
        startSuccess("重新进入报站界面");
    }

    @Override
    public void startSuccess(String returnInfo) {
        disPlay(returnInfo);
        startTraceService();
        startService(new Intent(this, DogService.class));
        startActivity(new Intent(this, AutoReportActivity.class));
        finish();
    }

    @Override
    public void startFailed(String returnInfo) {
        disPlay(returnInfo);
    }

    @Override
    public void normalFinishSuccess(String returnInfo) {
        orderFinish(returnInfo);
    }

    @Override
    public void normalFinishFailed(String returnInfo) {
        disPlay(returnInfo);
    }

    @Override
    public void errorFinishSuccess(String returnInfo) {
        orderFinish(returnInfo);
    }

    public void orderFinish(String returnInfo){
        SpUtils.setCache(mContext, SpUtils.CURRENT_LINE_ID, "");
        disPlay(returnInfo);
        finish();
    }

    @Override
    public void errorFinishFailed(String returnInfo) {
        disPlay(returnInfo);
    }

    private void startTraceService() {
            Intent intent = new Intent(mContext, TraceService.class);
            intent.putExtra("lineId", journey.getLineId());
            mContext.startService(intent);
    }
}

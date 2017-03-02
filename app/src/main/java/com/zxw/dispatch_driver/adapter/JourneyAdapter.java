package com.zxw.dispatch_driver.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.data.bean.Journey;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.MainPresenter;

import java.util.List;

/**
 * author：CangJie on 2016/12/8 11:56
 * email：cangjie2016@gmail.com
 */
public class JourneyAdapter extends CommonAdapter<Journey> {

    private final MainPresenter mPresenter;

    public JourneyAdapter(Context context, MainPresenter mainPresenter, int layoutId, List<Journey> items) {
        super(context, layoutId, items);
        this.mPresenter = mainPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, int position, final Journey item) {
        TextView tv_station_name = holder.findViewById(R.id.tv_station_name);
        TextView tv_vehicle = holder.findViewById(R.id.tv_vehicle);
        // TextView tv_run_date = holder.findViewById(R.id.tv_run_date);
        TextView tv_run_date_year = holder.findViewById(R.id.tv_run_date_year);// 年
        TextView tv_run_date_month = holder.findViewById(R.id.tv_run_date_month);// 月
        TextView tv_run_date_day = holder.findViewById(R.id.tv_run_date_day);// 日
        TextView tv_run_date_hour = holder.findViewById(R.id.tv_run_date_hour);// 时
        TextView tv_run_date_minutes = holder.findViewById(R.id.tv_run_date_minutes);// 分

        Button btn_start = holder.findViewById(R.id.btn_1);// 开始按钮
        Button btn_normal_finish = holder.findViewById(R.id.btn_2);// 正常按钮
        Button btn_error_finish = holder.findViewById(R.id.btn_3);// 异常按钮

        tv_station_name.setText(item.getLineCode());
        tv_vehicle.setText(item.getVehCode());
        String runDate = String.valueOf(item.getRunDate());
        try{
            tv_run_date_year.setText(runDate.substring(0,4));
            tv_run_date_month.setText(runDate.substring(4,6));
            tv_run_date_day.setText(runDate.substring(6));
            tv_run_date_hour.setText(item.getVehTime().substring(0,2));
            tv_run_date_minutes.setText(item.getVehTime().substring(2));
        }catch (Exception e){

        }

        switch (item.getStatus()){
            case 1:
                btn_start.setEnabled(true);
                btn_normal_finish.setEnabled(false);
                btn_error_finish.setEnabled(false);
                break;
            case 2:
                btn_start.setEnabled(false);
                btn_normal_finish.setEnabled(true);
                btn_error_finish.setEnabled(true);
                break;
            default:
                btn_start.setEnabled(false);
                btn_normal_finish.setEnabled(false);
                btn_error_finish.setEnabled(false);
                break;
        }
//        holder.findViewById(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, JourneyActivity.class);
//                intent.putExtra("journey", item);
//                mContext.startActivity(intent);
//            }
//        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startJourney(item.getScheduleId(), item.getLineId());
            }
        });
        btn_normal_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.normalFinishJourney(item.getScheduleId());
            }
        });
        btn_error_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.errorFinishJourney(item.getScheduleId());
            }
        });
    }

}

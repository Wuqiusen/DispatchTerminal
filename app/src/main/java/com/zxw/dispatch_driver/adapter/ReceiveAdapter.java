package com.zxw.dispatch_driver.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxw.data.bean.Receive;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.presenter.MainPresenter;
import com.zxw.dispatch_driver.ui.ReceiveActivity;

import java.util.List;

/**
 * author：CangJie on 2016/12/8 11:56
 * email：cangjie2016@gmail.com
 */
public class ReceiveAdapter extends CommonAdapter<Receive> {
    private final MainPresenter mPresenter;

    public ReceiveAdapter(Context context, int layoutId, List<Receive> items, MainPresenter mPresenter) {
        super(context, layoutId, items);
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, int position, final Receive item) {
        TextView tv_station_name = holder.findViewById(R.id.tv_station_name);
        //TextView tv_run_date = holder.findViewById(R.id.tv_run_date);
        TextView tv_run_date_year = holder.findViewById(R.id.tv_run_date_year);// 年
        TextView tv_run_date_month = holder.findViewById(R.id.tv_run_date_month);// 月
        TextView tv_run_date_day = holder.findViewById(R.id.tv_run_date_day);// 日
        TextView tv_run_date_hour = holder.findViewById(R.id.tv_run_date_hour);// 时
        TextView tv_run_date_minutes = holder.findViewById(R.id.tv_run_date_minutes);// 分

        TextView tv_prompt = holder.findViewById(R.id.tv_prompt);
        Button btn_refuse = holder.findViewById(R.id.btn_refuse);
        Button btn_confirm = holder.findViewById(R.id.btn_confirm);
        holder.findViewById(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReceiveActivity.class);
                intent.putExtra("receive", item);
                mContext.startActivity(intent);
            }
        });
        tv_station_name.setText(item.getStationName());
        //tv_run_date.setText(item.getRunDate() +" " + item.getProjectTime());
        String runDate = String.valueOf(item.getRunDate());
        try{
            tv_run_date_year.setText(runDate.substring(0,4));
            tv_run_date_month.setText(runDate.substring(4,6));
            tv_run_date_day.setText(runDate.substring(6));
            tv_run_date_hour.setText(item.getProjectTime().substring(0,2));
            tv_run_date_minutes.setText(item.getProjectTime().substring(2));
        }catch (Exception e){

        }


        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.refuse(item.getId());
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirm(item.getId());
            }
        });
        switch(item.getStatus()){
            case 0:
                btn_refuse.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.VISIBLE);
                tv_prompt.setVisibility(View.GONE);
                break;
            case 1:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_1));
                break;
            case 2:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_2));
                break;
            case 3:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_3));
                break;
        }
    }
}

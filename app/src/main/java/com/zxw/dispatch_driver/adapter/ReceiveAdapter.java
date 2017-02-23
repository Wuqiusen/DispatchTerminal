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
        TextView tv_line_code = holder.findViewById(R.id.tv_line_code);
        TextView tv_car_code = holder.findViewById(R.id.tv_car_code);
        TextView tv_run_date = holder.findViewById(R.id.tv_run_date);

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
        tv_line_code.setText(item.lineCode);
        tv_car_code.setText(item.vehCode);
        String runDate = String.valueOf(item.runDate);
        String date = runDate.substring(0,4) + "-" +runDate.substring(4,6) + "-" + runDate.substring(6, 8);
        tv_run_date.setText(date +" " + item.vehTime.substring(0, 2) + ":" + item.vehTime.substring(2, 4));


        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.refuse(item.billId);
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirm(item.billId);
            }
        });
        switch(item.status){
            case 1:
                btn_refuse.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.VISIBLE);
                tv_prompt.setVisibility(View.GONE);
                break;
            case 2:
                btn_refuse.setVisibility(View.GONE);
                btn_confirm.setVisibility(View.GONE);
                tv_prompt.setVisibility(View.VISIBLE);
                tv_prompt.setText(mContext.getResources().getString(R.string.receive_status_1));
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

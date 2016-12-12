package com.zxw.dispatch_driver.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zxw.data.bean.Journey;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.ui.JourneyActivity;

import java.util.List;

/**
 * author：CangJie on 2016/12/8 11:56
 * email：cangjie2016@gmail.com
 */
public class JourneyAdapter extends CommonAdapter<Journey> {

    public JourneyAdapter(Context context, int layoutId, List<Journey> items) {
        super(context, layoutId, items);
    }

    @Override
    protected void convert(ViewHolder holder, int position, final Journey item) {
        TextView tv_station_name = holder.findViewById(R.id.tv_station_name);
        TextView tv_run_date = holder.findViewById(R.id.tv_run_date);
        TextView tv_prompt = holder.findViewById(R.id.tv_prompt);
        tv_station_name.setText(item.getStationName());
        tv_run_date.setText(item.getRunDate() +" " + item.getProjectTime());
        switch (item.getStatus()){
            case 1:
                tv_prompt.setText(mContext.getString(R.string.journey_status_1));
                break;
            case 2:
                tv_prompt.setText(mContext.getString(R.string.journey_status_2));
                break;
            case 3:
                tv_prompt.setText(mContext.getString(R.string.journey_status_3));
                break;
            case 4:
                tv_prompt.setText(mContext.getString(R.string.journey_status_4));
                break;
        }
        holder.findViewById(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JourneyActivity.class);
                intent.putExtra("journey", item);
                mContext.startActivity(intent);
            }
        });

    }
}

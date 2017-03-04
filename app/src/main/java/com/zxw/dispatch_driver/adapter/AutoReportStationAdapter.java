package com.zxw.dispatch_driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxw.data.db.bean.StationBean;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.utils.SpeakUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author：CangJie on 2016/11/3 17:47
 * email：cangjie2016@gmail.com
 */
public class AutoReportStationAdapter extends RecyclerView.Adapter<AutoReportStationAdapter.ViewHolder> {

    private final List<StationBean> mData;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final SpeakUtil mSpeakUtil;
    private List<Integer> arriveList = new ArrayList<>();

    public AutoReportStationAdapter(List<StationBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);

        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_auto_station, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final StationBean currentStation = mData.get(position);
        holder.mStationName.setText(currentStation.getStationName());
        if (arriveList.contains(currentStation.getStationId())){
            holder.mStationName.setTextColor(mContext.getResources().getColor(R.color.round_green));
        }else{
            holder.mStationName.setTextColor(mContext.getResources().getColor(R.color.font_black));
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void reportPositionRecord(int stationId){
        arriveList.add(stationId);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_station_name)
        TextView mStationName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

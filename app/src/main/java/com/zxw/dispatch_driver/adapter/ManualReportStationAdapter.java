package com.zxw.dispatch_driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class ManualReportStationAdapter extends RecyclerView.Adapter<ManualReportStationAdapter.ViewHolder> {

    private final List<StationBean> mData;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final SpeakUtil mSpeakUtil;
    private OnClickStationListener mListener;
    private List<Integer> arriveList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    public ManualReportStationAdapter(List<StationBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_manual_station, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StationBean currentStation = mData.get(position);
        holder.mStationName.setText(currentStation.getStationName());
        if (arriveList.contains(position)){
            holder.mStationName.setTextColor(mContext.getResources().getColor(R.color.round_green));
        }else{
            holder.mStationName.setTextColor(mContext.getResources().getColor(R.color.font_black));
        }
        if (mListener == null){
            holder.mBtnOn.setVisibility(View.INVISIBLE);
            holder.mBtnOff.setVisibility(View.INVISIBLE);
            return;
        }
        if (position < mData.size() - 1){
            holder.mBtnOff.setVisibility(View.VISIBLE);
        }else{
            holder.mBtnOff.setVisibility(View.GONE);
        }

        holder.mBtnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mData.size() - 1){
                    mListener.onClickLastStationOn(currentStation);
                }else{
                    mListener.onClickStationOn(currentStation);
                }
                reportPositionRecord(position, holder);
            }
        });
        holder.mBtnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickStationOff(mData.get(position + 1));
                reportPositionRecord(position, holder);
            }
        });
    }
    private void reportPositionRecord(int position, ViewHolder holder){
        arriveList.add(position);
        holder.mStationName.setTextColor(mContext.getResources().getColor(R.color.round_green));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_station_name)
        TextView mStationName;
        @Bind(R.id.btn_on)
        Button mBtnOn;
        @Bind(R.id.btn_off)
        Button mBtnOff;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setOnClickStationListener(OnClickStationListener listener){
        this.mListener = listener;
    }

    public interface OnClickStationListener{
        void onClickStationOn(StationBean currentBean);
        void onClickLastStationOn(StationBean currentBean);
        void onClickStationOff(StationBean nextBean);
    }
}

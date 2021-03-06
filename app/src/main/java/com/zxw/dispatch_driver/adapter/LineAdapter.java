package com.zxw.dispatch_driver.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxw.data.db.bean.LineBean;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.utils.SpeakUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author：CangJie on 2016/11/3 17:47
 * email：cangjie2016@gmail.com
 */
public class LineAdapter extends RecyclerView.Adapter<LineAdapter.ViewHolder> {

    private final List<LineBean> mData;
    private final Activity mContext;
    private final LayoutInflater mLayoutInflater;
    private final SpeakUtil mSpeakUtil;
    private OnClickLineListener mListener;

    public LineAdapter(List<LineBean> mData, Activity mActivity) {
        this.mData = mData;
        this.mContext = mActivity;
        mLayoutInflater = LayoutInflater.from(mActivity);

        mSpeakUtil = SpeakUtil.getInstance(MyApplication.mContext);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_line, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final LineBean lineBean = mData.get(position);
        String type = lineBean.getType() == 0 ? "上行" : "下行";
        holder.mStationName.setText(lineBean.getLineName() +  "          " + type);
        holder.mStationName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SpUtils.setCache(mContext, SpUtils.CURRENT_LINE_ID, String.valueOf(lineBean.getLineId()));
                SpUtils.setCache(mContext, SpUtils.CURRENT_LINE_NAME, lineBean.getLineName());
                mListener.onClickLine(lineBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_station_name)
        TextView mStationName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setOnClickLineListener(OnClickLineListener listener){
        this.mListener = listener;
    }

    public interface OnClickLineListener {
        void onClickLine(LineBean currentBean);
    }
}

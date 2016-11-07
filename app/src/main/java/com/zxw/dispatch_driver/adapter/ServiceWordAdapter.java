package com.zxw.dispatch_driver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zxw.data.db.bean.ServiceWordBean;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.utils.SpeakUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author：CangJie on 2016/11/7 09:48
 * email：cangjie2016@gmail.com
 */
public class ServiceWordAdapter extends RecyclerView.Adapter<ServiceWordAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<ServiceWordBean> mData;
    private final SpeakUtil mSpeakUtil;

    public ServiceWordAdapter(Context context, List<ServiceWordBean> serviceWordBeen) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = serviceWordBeen;
        mSpeakUtil = SpeakUtil.getInstance(mContext);
        mSpeakUtil.init();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_service_word, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mBtn.setText(mData.get(position).getKeyCode());
        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpeakUtil.playText(mData.get(position).getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.btn_service_word)
        Button mBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

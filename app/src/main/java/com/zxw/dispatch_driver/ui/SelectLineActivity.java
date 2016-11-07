package com.zxw.dispatch_driver.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.zxw.data.db.bean.LineBean;
import com.zxw.data.dao.LineDao;
import com.zxw.data.dao.LineStationDao;
import com.zxw.data.dao.ServiceWordDao;
import com.zxw.data.dao.StationDao;
import com.zxw.data.dao.VoiceCompoundDao;
import com.zxw.data.sp.SpUtils;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.LineAdapter;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.view.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLineActivity extends BaseHeadActivity {
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    @Bind(R.id.et_line_no)
    EditText mLineNo;
    private LineDao mLineDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_line);
        ButterKnife.bind(this);

        mLineDao = new LineDao(MyApplication.mContext);
        StationDao mStationDao = new StationDao(MyApplication.mContext);
        LineStationDao mLineStationDao = new LineStationDao(MyApplication.mContext);
        VoiceCompoundDao mVoiceCompoundDao = new VoiceCompoundDao(MyApplication.mContext);
        ServiceWordDao serviceWordDao = new ServiceWordDao(MyApplication.mContext);
        if(SpUtils.isFirst(MyApplication.mContext)){
            mLineDao.init();
            mStationDao.init();
            mLineStationDao.init();
            mVoiceCompoundDao.init();
            serviceWordDao.initInsert();
            SpUtils.inited(MyApplication.mContext);
        }else{
            mStationDao.initQuery();
            mLineStationDao.initQuery();
        }
    }

    @OnClick(R.id.btn_confirm)
    public void researchLineNo(){

        String str = mLineNo.getText().toString().trim();
        if (TextUtils.isEmpty(str)){
            return;
        }
        List<LineBean> list = mLineDao.queryLine(str);
        if (list == null || list.size() == 0){
            disPlay("数据库中没有该线路");
        }else{
            LineAdapter lineAdapter = new LineAdapter(list, this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(lineAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));

        }
    }
}

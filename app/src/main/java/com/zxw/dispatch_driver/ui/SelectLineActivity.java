package com.zxw.dispatch_driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.zxw.data.dao.LineDao;
import com.zxw.data.db.bean.LineBean;
import com.zxw.dispatch_driver.MyApplication;
import com.zxw.dispatch_driver.R;
import com.zxw.dispatch_driver.adapter.LineAdapter;
import com.zxw.dispatch_driver.ui.base.BaseHeadActivity;
import com.zxw.dispatch_driver.view.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLineActivity extends BaseHeadActivity implements LineAdapter.OnClickLineListener {
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    @Bind(R.id.et_line_no)
    EditText mLineNo;
    private LineDao mLineDao;
    private boolean dog_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_line);
        ButterKnife.bind(this);
        showTitle("选择线路");
        dog_start = getIntent().getBooleanExtra("dog_start", false);
        mLineDao = new LineDao(MyApplication.mContext);
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
            lineAdapter.setOnClickLineListener(this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(lineAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));

        }
    }

    @Override
    public void onClickLine(LineBean currentBean) {
        Intent intent = null;
        if(dog_start){
            intent = new Intent(mContext, DogActivity.class);
        }else{
            intent = new Intent(mContext, AutoReportActivity.class);
        }
        mContext.startActivity(intent);
    }
}

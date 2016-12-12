package com.zxw.dispatch_driver.presenter;

import com.zxw.data.bean.BaseBean;
import com.zxw.data.bean.LineDetail;
import com.zxw.data.bean.Receive;
import com.zxw.data.source.DispatchSource;
import com.zxw.dispatch_driver.presenter.view.ReceiveView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.zxw.dispatch_driver.Constants.SUCCESS;

/**
 * author：CangJie on 2016/12/8 16:24
 * email：cangjie2016@gmail.com
 */
public class ReceivePresenter extends BasePresenter<ReceiveView> {

    private final int mLineId;
    private final DispatchSource mDispatchSource;
    private final int mOrderId;

    public ReceivePresenter(ReceiveView mvpView, Receive receive) {
        super(mvpView);
        this.mLineId = receive.getLineId();
        this.mOrderId = receive.getId();
        this.mDispatchSource = new DispatchSource();
    }


    /**
     * 拒绝工单
     */
    public void refuse() {
        receiveListOperator(1);
    }

    /**
     * 接受工单
     */
    public void confirm() {
        receiveListOperator(3);
    }

    public void receiveListOperator(int status){
        mDispatchSource.receiveOperator(code(), keyCode(), mOrderId, status, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.returnCode == SUCCESS){
                    mvpView.operatorReceiveOrderSuccess();
                }
                mvpView.disPlay(baseBean.returnInfo);
            }
        });
    }

    public void loadLineDetail(){
        mDispatchSource.lineDetail(code(), keyCode(), mLineId, new Subscriber<LineDetail>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LineDetail lineDetail) {

                List<Station> stations = disposeStation(lineDetail);
                mvpView.drawStation(stations);
                mvpView.baseInfo(lineDetail);
            }
        });
    }

    private List<Station> disposeStation(LineDetail lineDetail) {
        List<Station> stationList = new ArrayList<>();
        String stationNames = lineDetail.getStationNames();
        String[] names = stationNames.split(";");
        String stationPoints = lineDetail.getStationPoints();
        String[] points = stationPoints.split(";");
        for (int i = 0; i<= names.length - 1; i++){
            String[] point = points[i].split(",");
            Station station = new Station(names[i], Double.valueOf(point[1]), Double.valueOf(point[0]));
            stationList.add(station);
        }
        return stationList;
    }

    public class Station{
        public String stationName;
        public double lat;
        public double lng;

        public Station(String stationName, double lat, double lng) {
            this.stationName = stationName;
            this.lat = lat;
            this.lng = lng;
        }
    }
}

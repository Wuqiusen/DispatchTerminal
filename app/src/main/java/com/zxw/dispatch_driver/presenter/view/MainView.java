package com.zxw.dispatch_driver.presenter.view;

import com.zxw.dispatch_driver.adapter.JourneyAdapter;
import com.zxw.dispatch_driver.adapter.ReceiveAdapter;

/**
 * author：CangJie on 2016/12/7 17:23
 * email：cangjie2016@gmail.com
 */
public interface MainView extends BaseView {
    void receivePageLoadFailed();

    void emptyReceiveData();

    void receivePageSetAdapter(ReceiveAdapter mReceiveAdapter);

    void receivePageLoadComplete();

    void noMoreReceiveData();

    void noMoreJourneyData();

    void journeyPageLoadFailed();

    void emptyJourneyData();

    void journeyPageSetAdapter(JourneyAdapter mJourneyAdapter);

    void journeyPageLoadComplete();

    void showLoadingJourneyPage();

    void showLoadingReceivePage();
}

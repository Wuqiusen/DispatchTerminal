package com.zxw.dispatch_driver.presenter.view;

import com.zxw.data.db.bean.TbDogLineMain;

import java.util.List;

/**
 * author：CangJie on 2016/11/8 14:56
 * email：cangjie2016@gmail.com
 */
public interface DogView extends BaseView {
    void drawMarker(List<TbDogLineMain> tbDogLineMains);
}

package com.zxw.data;

import com.zxw.data.bean.Login;
import com.zxw.data.http.HttpMethods;
import com.zxw.data.utils.MD5;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import rx.Scheduler;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String p1 = MD5.MD5Encode("123456");
        String s = MD5.MD5Encode(p1 + "1481167453488");
        assertEquals("1", s);
    }
    @Before
    public void setup(){
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }

            @Override
            public Scheduler getNewThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }
    private String code = "902130";
    private String password = "c4ae7bda98376f2a23b9b7f1a4c0f42a";
    private String time = "1480867200000";

    private String result;


    @Test
    public void test_buyTicketNecessaryInfo(){
        HttpMethods.getInstance().login(new Subscriber<Login>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Login s) {
                result = s.toString();
            }
        }, code, password, time);
        Assert.assertEquals("1",result);
    }
}
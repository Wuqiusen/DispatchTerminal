package com.zxw.dispatch_driver.utils;

import android.content.Context;
import android.util.Log;

import com.zxw.data.sp.SpUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.iflytek.cloud.VerifierResult.TAG;

/**
 * author：CangJie on 2016/12/26 15:31
 * email：cangjie2016@gmail.com
 */
public class InitializeAssertFileUtil {
    public static void initialize(Context context){
        boolean isCopyDogSuccessful = copyDatabaseFile(context, "dog.db");
        if (!isCopyDogSuccessful)
            return;
        boolean isCopyStationSuccessful = copyDatabaseFile(context, "stationReport.db");
        if (!isCopyStationSuccessful)
            return;
        copySharePrefenceFile(context, "eastSmartDispatch.xml");
    }

    private static void copySharePrefenceFile(Context context, String filename) {
        try {
            InputStream open = context.getAssets().open(filename);
            XmlPullParserFactory factory;
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
            parser.setInput(open, "UTF-8");
            int event = parser.getEventType();
            while(event!= XmlPullParser.END_DOCUMENT){
                switch(event){
                    case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件

                        break;
                    case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件
                        if("long".equals(parser.getName())){//判断开始标签元素
                            String value = parser.getAttributeValue(0);
                            String value1 = parser.getAttributeValue(1);
                            SpUtils.setCache(context, value, Long.valueOf(value1));
                        }
                        break;
                    case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件
                        break;
                }
                event = parser.next();//进入下一个元素并触发相应事件
            }//end while
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private static boolean copyDatabaseFile(Context context, String filename) {
//只要你拷贝了一次，我就不要你再拷贝了
        try {
            //在指定的目录创建了 database.db文件
            File file = context.getDatabasePath(filename);
            if (file.exists() && file.length() > 0) {
                //正常了，不需要拷贝了
                Log.i(TAG, "正常了，不需要拷贝了");
                return false;
            } else {
                InputStream is = context.getAssets().open(filename);

                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                len = is.read(buffer);
                while (len != -1) {
                    fos.write(buffer, 0, len);
                    len = is.read(buffer);
                }
                is.close();
                fos.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

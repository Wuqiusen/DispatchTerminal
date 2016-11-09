package com.zxw.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.bean.UpdateReportPointBean;
import com.zxw.data.db.bean.InnerReportPointBean;
import com.zxw.data.db.bean.LineStationReportBean;
import com.zxw.data.db.StationReportDBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/7 11:03
 * email：cangjie2016@gmail.com
 */
public class ReportPointDao {

    private StationReportDBOpenHelper mHelper;

    public ReportPointDao(Context context) {
        mHelper = new StationReportDBOpenHelper(context);
    }

    public long add(LineStationReportBean bean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", bean.getId());
        values.put("mainId", bean.getMainId());
        values.put("type", bean.getType());
        values.put("lineId", bean.getLineId());
        values.put("stationId", bean.getStationId());
        values.put("lat", bean.getLat());
        values.put("lng", bean.getLng());
        values.put("keyCode", bean.getKeyCode());
        values.put("isDele", bean.getIsDele());
        values.put("updateTime", bean.getUpdateTimeKey());
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_REPORT_POINT, null, values);
        db.close();
        return rowid;
    }

    //id integer, isDele integer, keyCode varchar(80),lat double, lineId integer, lng double, stationId integer, type integer, updateTime integer,mainId integer
    public List<InnerReportPointBean> queryByLineId(long queryId) {
        List<InnerReportPointBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select a.stationId,a.sortNum,b.lat,b.lng,b.type from tb_line_station a inner join tb_report_point b" +
                " on a.lineId=b.lineId and a.stationId=b.stationId and a.isDele=b.isDele where a.lineId=? and a.isDele=0 order by a.sortNum,b.type;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(queryId)});
        while (cursor.moveToNext()) {
            int stationId = cursor.getInt(0);
            int sortNum = cursor.getInt(1);
            double lat = cursor.getDouble(2);
            double lng = cursor.getDouble(3);
            int type = cursor.getInt(4);
            InnerReportPointBean bean = new InnerReportPointBean();
            bean.setLat(lat);
            bean.setLng(lng);
            bean.setStationId(stationId);
            bean.setType(type);
            bean.setSortNum(sortNum);
            list.add(bean);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void updateReportPoint(UpdateReportPointBean bean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + StationReportDBOpenHelper.TABLE_REPORT_POINT + "(id,isDele,keyCode,lat,lineId,lng,stationId,type,updateTime) values (?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), String.valueOf(bean.getIsDele()), bean.getKeyCode(), String.valueOf(bean.getLat()), String.valueOf(bean.getLineId()),
                String.valueOf(bean.getLng()), String.valueOf(bean.getStationId()), String.valueOf(bean.getType()), String.valueOf(bean.getUpdateTimeKey())});
        db.close();
    }


    public void init() {
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        String[] split = initSql.split("\\n");
//        for (String str : split){
//            db.execSQL(str);
//        }
//        db.close();
    }

    //(id integer, isDele integer, keyCode varchar(80),lat double, lineId integer, lng double, stationId integer, type integer, updateTime integer,mainId integer)
    String initSql = "INSERT INTO tb_report_point VALUES (1, 0, '42_6940_1', 22.59877, 42, 114.31347, 6940, 1, 201611041613, 1);\n" +
            "INSERT INTO tb_report_point VALUES (2, 0, '42_6940_4', 22.59874, 42, 114.31317, 6940, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (3, 0, '42_6944_1', 22.60113, 42, 114.32648, 6944, 1, 201611041613, 1);\n" +
            "INSERT INTO tb_report_point VALUES (4, 0, '42_6944_4', 22.60091, 42, 114.32611, 6944, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (5, 0, '42_520998_1', 22.60272, 42, 114.32708, 520998, 1, 201611041613, 1);\n" +
            "INSERT INTO tb_report_point VALUES (6, 0, '42_6064_1', 22.53456, 42, 114.11893, 6064, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (7, 0, '42_6064_4', 22.53413, 42, 114.11899, 6064, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (8, 0, '42_6087_1', 22.53853, 42, 114.11873, 6087, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (9, 0, '42_6087_4', 22.53836, 42, 114.11874, 6087, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (10, 0, '42_6093_1', 22.53996, 42, 114.11985, 6093, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (11, 0, '42_6093_4', 22.53992, 42, 114.11965, 6093, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (12, 0, '42_6096_1', 22.54064, 42, 114.12245, 6096, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (13, 0, '42_6096_4', 22.54053, 42, 114.12221, 6096, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (14, 0, '42_6100_1', 22.53999, 42, 114.12403, 6100, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (15, 0, '42_6100_4', 22.54008, 42, 114.12392, 6100, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (16, 0, '42_6101_1', 22.54107, 42, 114.12663, 6101, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (17, 0, '42_6101_4', 22.54154, 42, 114.12692, 6101, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (18, 0, '42_6585_1', 22.54416, 42, 114.13093, 6585, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (19, 0, '42_6585_4', 22.54349, 42, 114.13093, 6585, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (20, 0, '42_6592_1', 22.54560, 42, 114.13357, 6592, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (21, 0, '42_6592_4', 22.54548, 42, 114.13281, 6592, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (22, 0, '42_6684_1', 22.54632, 42, 114.13786, 6684, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (23, 0, '42_6684_4', 22.54625, 42, 114.13737, 6684, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (24, 0, '42_6688_1', 22.54864, 42, 114.14235, 6688, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (25, 0, '42_6688_4', 22.54832, 42, 114.14211, 6688, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (26, 0, '42_6770_1', 22.55669, 42, 114.18357, 6770, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (27, 0, '42_6770_4', 22.55694, 42, 114.18320, 6770, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (28, 0, '42_6772_1', 22.56021, 42, 114.16921, 6772, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (29, 0, '42_6772_4', 22.56019, 42, 114.16834, 6772, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (30, 0, '42_6773_1', 22.56025, 42, 114.17273, 6773, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (31, 0, '42_6773_4', 22.56024, 42, 114.17205, 6773, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (32, 0, '42_6803_1', 22.55288, 42, 114.22499, 6803, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (33, 0, '42_6803_4', 22.55315, 42, 114.22469, 6803, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (34, 0, '42_6819_1', 22.55137, 42, 114.22651, 6819, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (35, 0, '42_6819_4', 22.55163, 42, 114.22625, 6819, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (36, 0, '42_6823_1', 22.55034, 42, 114.23030, 6823, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (37, 0, '42_6823_4', 22.55007, 42, 114.22997, 6823, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (38, 0, '42_6825_1', 22.55154, 42, 114.23182, 6825, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (39, 0, '42_6825_4', 22.55122, 42, 114.23139, 6825, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (40, 0, '42_6846_1', 22.55463, 42, 114.23620, 6846, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (41, 0, '42_6846_4', 22.55426, 42, 114.23555, 6846, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (42, 0, '42_6862_1', 22.55915, 42, 114.24200, 6862, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (43, 0, '42_6862_4', 22.55880, 42, 114.24163, 6862, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (44, 0, '42_6864_1', 22.56188, 42, 114.24636, 6864, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (45, 0, '42_6864_4', 22.56147, 42, 114.24597, 6864, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (46, 0, '42_6869_1', 22.56729, 42, 114.25179, 6869, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (47, 0, '42_6869_4', 22.56688, 42, 114.25151, 6869, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (48, 0, '42_6910_1', 22.58593, 42, 114.27000, 6910, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (49, 0, '42_6910_4', 22.58552, 42, 114.26973, 6910, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (50, 0, '42_6915_1', 22.58768, 42, 114.27472, 6915, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (51, 0, '42_6915_4', 22.58765, 42, 114.27405, 6915, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (52, 0, '42_6925_1', 22.59512, 42, 114.30255, 6925, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (53, 0, '42_6925_4', 22.59461, 42, 114.30263, 6925, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (54, 0, '42_6928_1', 22.59196, 42, 114.30419, 6928, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (55, 0, '42_6928_4', 22.59157, 42, 114.30463, 6928, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (56, 0, '42_6942_1', 22.60028, 42, 114.30858, 6942, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (57, 0, '42_6942_4', 22.60034, 42, 114.30837, 6942, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (58, 0, '42_18671_1', 22.59907, 42, 114.30439, 18671, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (59, 0, '42_18671_4', 22.59895, 42, 114.30425, 18671, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (60, 0, '42_19533_1', 22.55443, 42, 114.22165, 19533, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (61, 0, '42_19533_4', 22.55448, 42, 114.22120, 19533, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (62, 0, '42_19563_1', 22.58421, 42, 114.29459, 19563, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (63, 0, '42_19563_4', 22.58441, 42, 114.29398, 19563, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (64, 0, '42_20094_1', 22.56404, 42, 114.24848, 20094, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (65, 0, '42_20094_4', 22.56366, 42, 114.24805, 20094, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (66, 0, '42_21801_1', 22.55268, 42, 114.15105, 21801, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (67, 0, '42_21801_4', 22.55268, 42, 114.15056, 21801, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (68, 0, '42_22027_1', 22.53250, 42, 114.11934, 22027, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (69, 0, '42_24691_1', 22.55638, 42, 114.16003, 24691, 1, 201611041632, 1);\n" +
            "INSERT INTO tb_report_point VALUES (70, 0, '42_24691_4', 22.55602, 42, 114.15953, 24691, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (71, 0, '42_520998_4', 22.60263, 42, 114.32685, 520998, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (72, 0, '42_6580_1', 22.54237, 42, 114.12884, 6580, 1, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (73, 0, '42_6580_4', 22.54234, 42, 114.12807, 6580, 4, 201611041636, 1);\n" +
            "INSERT INTO tb_report_point VALUES (74, 1, '74_46_4467_1', 22.72777, 46, 114.23817, 4467, 1, 201611051424, 2);\n" +
            "INSERT INTO tb_report_point VALUES (75, 1, '75_46_4467_4', 22.72726, 46, 114.23818, 4467, 4, 201611051424, 2);\n";

}
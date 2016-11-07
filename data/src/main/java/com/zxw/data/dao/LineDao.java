package com.zxw.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxw.data.bean.UpdateLineBean;
import com.zxw.data.db.bean.LineBean;
import com.zxw.data.db.StationReportDBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author：CangJie on 2016/11/3 16:52
 * email：cangjie2016@gmail.com
 */
public class LineDao {
    private StationReportDBOpenHelper mHelper;

    public LineDao(Context context) {
        mHelper = new StationReportDBOpenHelper(context);
    }
    public long addLine(int lineId, String lineName){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lineId",lineId);
        values.put("lineName",lineName);
        values.put("isDele",0);
        long rowid = db.insert(StationReportDBOpenHelper.TABLE_LINE, null, values);
        db.close();
        return rowid;
    }

    //
    public long lastUpdateTime(){
        String sql = "SELECT max(updateTime) FROM" + StationReportDBOpenHelper.TABLE_LINE;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        long updateTime = -1;
        if (cursor.moveToNext()){
            updateTime = cursor.getLong(0);
        }
        cursor.close();
        db.close();
        return  updateTime;
    }
//    public List<LineBean> queryLines(){
//        List<LineBean> list = new ArrayList<>();
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        String sql = "select * from" + StationReportDBOpenHelper.TABLE_LINE;
//        Cursor cursor = db.rawQuery(sql, null);
//        while (cursor.moveToNext()){
//            int lineId = cursor.getInt(1);
//            String lineName = cursor.getString(2);
//            list.add(new LineBean(lineId, lineName));
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
    public List<LineBean> queryLine(String str){
        List<LineBean> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from" + StationReportDBOpenHelper.TABLE_LINE + "where lineName=?";
        Cursor cursor = db.rawQuery(sql, new String[]{str});
        while (cursor.moveToNext()){
            int lineId = cursor.getInt(0);
            String lineName = cursor.getString(1);
            int type = cursor.getInt(2);
            String isDele = cursor.getString(3);
            int updateTime = cursor.getInt(4);
            list.add(new LineBean(lineId, lineName, type, isDele, updateTime));
        }
        cursor.close();
        db.close();
        return list;
    }


//replace into student (id, name, sex, email, fenshu, tecid) values ('2','lisi', '*F', '123456@qq.com', '80', '2') where id = '2';
    public void updateLine(UpdateLineBean bean){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "replace into" + StationReportDBOpenHelper.TABLE_LINE + "(lineId,lineName,type,isDele,updateTime) values (?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(bean.getId()), bean.getLineCode(), String.valueOf(bean.getLinePartMark()), String.valueOf(bean.getIsDele()), String.valueOf(bean.getUpdateTimeKey())});
        db.close();
    }

    String strrrrrr = "INSERT INTO line VALUES (1, '303', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (2, '303', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (3, '309', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (4, '309', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (5, '311', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (6, '311', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (7, '312', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (8, '312', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (9, '313', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (10, '313', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (11, '317', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (12, '317', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (13, '317区间线', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (14, '317区间线', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (15, '323', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (16, '323', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (17, '326', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (18, '326', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (19, '327', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (20, '327', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (21, '328', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (22, '328', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (23, '353', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (24, '353', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (25, '366', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (26, '366', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (27, '369', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (28, '369', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (29, '372', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (30, '372', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (31, '377', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (32, '377', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (33, '379', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (34, '379', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (35, '380A', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (36, '380A', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (37, '380B', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (38, '380B', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (39, '385', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (40, '385', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (41, '387', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (42, '387', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (43, '391', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (44, '391', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (45, '398', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (46, '398', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (47, '620', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (48, '620', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (49, '802', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (50, '802', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (51, '810', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (52, '810', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (53, '811', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (54, '811', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (55, '812', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (56, '812', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (57, '821B', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (58, '821B', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (59, '822', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (60, '822', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (61, '833', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (62, '833', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (63, '836', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (64, '836', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (65, '862', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (66, '862', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (67, '866', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (68, '866', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (69, '868', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (70, '868', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (71, '868区间1线', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (72, '868区间1线', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (73, '868区间2线', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (74, '868区间2线', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (75, '907', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (76, '907', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (77, '915', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (78, '915', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (79, 'M434', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (80, 'M434', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (81, '923', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (82, '923', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (83, '926', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (84, '926', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (85, '928', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (86, '928', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (87, '929', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (88, '929', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (89, 'M458', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (90, 'M458', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (91, '935', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (92, '935', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (93, '937', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (94, '937', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (95, '939', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (96, '939', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (97, '941', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (98, '941', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (99, '954', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (100, '954', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (101, '956', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (102, '956', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (103, '963', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (104, '963', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (105, '968B', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (106, '968B', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (107, '977', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (108, '977', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (109, '978', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (110, '978', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (111, '979', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (112, '979', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (113, '982', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (114, '982', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (115, '982B', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (116, '982B', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (117, '987', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (118, '987', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (119, '991', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (120, '991', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (121, 'B666', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (122, 'B666', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (123, 'B668', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (124, 'B668', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (125, 'B669', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (126, 'B669', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (127, 'B671', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (128, 'B671', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (129, 'B673', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (130, 'B673', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (131, 'B675', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (132, 'B675', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (133, 'B679', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (134, 'B679', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (135, 'B723', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (136, 'B723', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (137, 'B724', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (138, 'B724', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (139, 'B738', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (140, 'B742', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (141, 'B742', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (142, 'B742区间', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (143, 'B742区间', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (144, 'B743', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (145, 'B743', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (146, 'B744', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (147, 'B744', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (148, 'B747', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (149, 'B747', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (150, 'B751', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (151, 'B751', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (152, 'B752', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (153, 'B752', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (154, 'B753', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (155, 'B753', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (156, 'B754', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (157, 'B754', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (158, 'B755', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (159, 'B755', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (160, 'M471', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (161, 'M471', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (162, 'B757', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (163, 'B757', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (164, 'B758', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (165, 'B758', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (166, 'B759', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (167, 'B759', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (168, 'B760', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (169, 'B760', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (170, 'B761', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (171, 'B761', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (172, 'B762', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (173, 'B762', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (174, 'B765', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (175, 'B765', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (176, 'B810', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (177, 'B811', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (178, 'B811', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (179, 'M485', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (180, 'M485', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (181, 'B824', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (182, 'B824', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (183, 'B825', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (184, 'B825', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (185, 'B849', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (186, 'B849', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (187, 'B850', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (188, 'B850', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (189, 'B852', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (190, 'B853', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (191, 'B853', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (192, 'B856', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (193, 'B856', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (194, 'B869', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (195, 'B870', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (196, 'B870', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (197, 'B871', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (198, 'B871', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (199, 'E11', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (200, 'E11', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (201, 'E20', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (202, 'E20', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (203, 'E21', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (204, 'E21', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (205, 'E22', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (206, 'E22', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (207, 'E23', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (208, 'E23', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (209, 'E5', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (210, 'E5', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (211, 'E6', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (212, 'E6', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (213, 'E7', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (214, 'E7', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (215, 'M203', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (216, 'M203', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (217, 'M219', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (218, 'M219', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (219, 'M220', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (220, 'M220', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (221, 'M224', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (222, 'M224', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (223, 'M227', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (224, 'M227', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (225, 'M229', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (226, 'M229', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (227, 'M230', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (228, 'M230', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (229, 'M231', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (230, 'M231', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (231, 'M232', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (232, 'M232', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (233, 'M233', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (234, 'M233', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (235, 'M234', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (236, 'M234', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (237, 'M243', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (238, 'M243', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (239, 'M265', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (240, 'M265', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (241, 'M266', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (242, 'M266', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (243, 'M267', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (244, 'M267', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (245, 'M268', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (246, 'M268', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (247, 'M269', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (248, 'M269', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (249, 'M270', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (250, 'M270', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (251, 'M271', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (252, 'M271', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (253, 'M272', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (254, 'M272', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (255, 'M273', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (256, 'M273', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (257, 'M274', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (258, 'M274', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (259, 'M275', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (260, 'M275', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (261, 'M276', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (262, 'M276', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (263, 'M277', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (264, 'M277', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (265, 'M278', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (266, 'M278', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (267, 'M279', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (268, 'M279', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (269, 'M280', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (270, 'M280', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (271, 'M281', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (272, 'M281', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (273, 'M282', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (274, 'M282', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (275, 'M283', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (276, 'M283', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (277, 'M293', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (278, 'M293', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (279, 'M294', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (280, 'M294', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (281, 'M295', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (282, 'M295', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (283, 'M296', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (284, 'M296', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (285, 'M297', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (286, 'M297', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (287, 'M303', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (288, 'M303', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (289, 'M304', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (290, 'M304', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (291, 'M305', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (292, 'M305', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (293, 'M306', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (294, 'M306', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (295, 'M307', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (296, 'M307', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (297, 'M308', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (298, 'M308', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (299, 'M309', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (300, 'M309', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (301, 'M310', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (302, 'M310', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (303, 'M311', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (304, 'M311', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (305, 'M315', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (306, 'M315', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (307, 'M316', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (308, 'M316', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (309, 'M318', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (310, 'M318', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (311, 'M319', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (312, 'M319', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (313, 'M320', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (314, 'M320', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (315, 'M321', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (316, 'M321', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (317, 'M322', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (318, 'M322', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (319, 'M323', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (320, 'M323', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (321, 'M324', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (322, 'M325', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (323, 'M325', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (324, 'M326', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (325, 'M326', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (326, 'M327', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (327, 'M327', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (328, 'M346', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (329, 'M346', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (330, 'M357', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (331, 'M357', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (332, 'M358', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (333, 'M358', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (334, 'M359', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (335, 'M359', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (336, 'M360', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (337, 'M360', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (338, 'M361', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (339, 'M361', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (340, 'E26', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (341, 'E26', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (342, 'M363', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (343, 'M363', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (344, 'M367', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (345, 'M367', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (346, 'M368', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (347, 'M368', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (348, 'M384', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (349, 'M384', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (350, 'M385', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (351, 'M385', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (352, 'M386', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (353, 'M386', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (354, 'M394', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (355, 'M394', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (356, 'M412', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (357, 'M413', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (358, 'M413', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (359, 'M414', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (360, 'M414', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (361, 'M415', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (362, 'M415', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (363, 'M422', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (364, 'M422', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (365, 'M423', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (366, 'M423', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (367, 'M426', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (368, 'M426', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (369, 'M427', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (370, 'M427', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (371, '大鹏半岛假日专线4', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (372, '大鹏半岛假日专线4', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (373, '大鹏假日专线2', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (374, '大鹏假日专线2', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (375, '大鹏假日专线3', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (376, '大鹏假日专线3', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (377, '第三人民医院高峰专线', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (378, '第三人民医院高峰专线', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (379, '高峰专线40号', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (380, '高峰专线40号', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (381, '高峰专线44号', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (382, '高峰专线44号', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (3060, '1234455', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (3061, '1234455', 1, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (3200, '深交【2016】123号', 0, '0', 201610291002);\n" +
            "     INSERT INTO line VALUES (3201, '深交【2016】123号', 1, '0', 201610291002);";
    public void init(){
//        String sql = "INSERT INTO " + StationReportDBOpenHelper.TABLE_LINE + " VALUES (39, '385', 0, '0', 201610291002)";
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String[] split = strrrrrr.split("\\n");
        for (String sql : split){
            db.execSQL(sql);
        }
    }
}

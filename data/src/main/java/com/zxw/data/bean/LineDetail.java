package com.zxw.data.bean;

/**
 * author：CangJie on 2016/12/8 16:30
 * email：cangjie2016@gmail.com
 */
public class LineDetail {
    @Override
    public String toString() {
        return "LineDetail{" +
                "line=" + line +
                ", stationNames='" + stationNames + '\'' +
                ", stationPoints='" + stationPoints + '\'' +
                '}';
    }

    /**
     * companyId : 5
     * companyName : 六公司
     * id : 42
     * isDele : 0
     * lineCode : 387
     * linePartMark : 1
     * orgId : 86
     * orgName : 五分公司五车队
     * saleType : 2
     * updateTimeKey : 201610291002
     * version : 0
     */

    private LineBean line;
    /**
     * line : {"companyId":5,"companyName":"六公司","id":42,"isDele":"0","lineCode":"387","linePartMark":1,"orgId":86,"orgName":"五分公司五车队","saleType":2,"updateTimeKey":201610291002,"version":0}
     * stationNames : 387小梅沙公交总站;小梅沙;梅沙街道办;东部华侨城;万科中心;中兴通讯学院;海滨浴场;中兴发展;盐田;西山吓;海港大厦;周大福大厦;沙头角保税区;盐田汽车站;盐田区政府;沙头角邮局②;三家店;沙头角口岸;沙头角海关;梧桐山隧道口①;长岭;莲塘;聚宝路口;西岭下①;罗湖体育馆①;黄贝岭;黄贝岭地铁站①;冶金大厦;文锦南路;文锦中学;春风万佳①;丽都酒店②;阳光酒店;金光华广场;罗湖小学;京湖酒店;火车站
     * stationPoints : 114.32696,22.6028;114.32628,22.60117;114.31324,22.59887;114.30845,22.60034;114.3042,22.59902;114.30244,22.59494;114.30437,22.5919;114.29429,22.58441;114.27439,22.58775;114.27006,22.58611;114.25159,22.56714;114.24815,22.56386;114.24631,22.56203;114.24189,22.55927;114.2372,22.55541;114.23164,22.55157;114.23001,22.55024;114.2264,22.55165;114.22489,22.5531;114.22137,22.55456;114.18331,22.55688;114.17194,22.56038;114.16863,22.56034;114.16001,22.55656;114.15036,22.55301;114.14198,22.54827;114.13788,22.54647;114.1332,22.54564;114.13094,22.54378;114.12796,22.54245;114.12625,22.54068;114.12423,22.53993;114.12201,22.54064;114.11901,22.53985;114.11867,22.53825;114.11888,22.53484;114.11859,22.53179
     */

    private String stationNames;
    private String stationPoints;

    public LineBean getLine() {
        return line;
    }

    public void setLine(LineBean line) {
        this.line = line;
    }

    public String getStationNames() {
        return stationNames;
    }

    public void setStationNames(String stationNames) {
        this.stationNames = stationNames;
    }

    public String getStationPoints() {
        return stationPoints;
    }

    public void setStationPoints(String stationPoints) {
        this.stationPoints = stationPoints;
    }

    public static class LineBean {
        private int companyId;
        private String companyName;
        private int id;
        private String isDele;
        private String lineCode;
        private int linePartMark;
        private int orgId;
        private String orgName;
        private int saleType;
        private long updateTimeKey;
        private int version;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIsDele() {
            return isDele;
        }

        public void setIsDele(String isDele) {
            this.isDele = isDele;
        }

        public String getLineCode() {
            return lineCode;
        }

        public void setLineCode(String lineCode) {
            this.lineCode = lineCode;
        }

        public int getLinePartMark() {
            return linePartMark;
        }

        public void setLinePartMark(int linePartMark) {
            this.linePartMark = linePartMark;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getSaleType() {
            return saleType;
        }

        public void setSaleType(int saleType) {
            this.saleType = saleType;
        }

        public long getUpdateTimeKey() {
            return updateTimeKey;
        }

        public void setUpdateTimeKey(long updateTimeKey) {
            this.updateTimeKey = updateTimeKey;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
